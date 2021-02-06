package zwf.mymall.ware.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import zwf.mymall.common.constant.WareConstant;
import zwf.mymall.common.utils.PageUtils;
import zwf.mymall.common.utils.Query;

import zwf.mymall.ware.dao.PurchaseDao;
import zwf.mymall.ware.entity.PurchaseDetailEntity;
import zwf.mymall.ware.entity.PurchaseEntity;
import zwf.mymall.ware.service.PurchaseDetailService;
import zwf.mymall.ware.service.PurchaseService;
import zwf.mymall.ware.service.WareSkuService;
import zwf.mymall.ware.vo.MergeVo;
import zwf.mymall.ware.vo.PurchaseDoneVo;
import zwf.mymall.ware.vo.PurchaseItemVo;


@Service("purchaseService")
public class PurchaseServiceImpl extends ServiceImpl<PurchaseDao, PurchaseEntity> implements PurchaseService {


    @Autowired
    WareSkuService wareSkuService;
    @Autowired
    PurchaseDetailService purchaseDetailService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                new QueryWrapper<PurchaseEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageUnreceivePurchase(Map<String, Object> params) {
        QueryWrapper<PurchaseEntity> wrapper = new QueryWrapper<>();
//        String status = (String)params.get("status");
        wrapper.eq("status",0).or().eq("status",1);
        IPage<PurchaseEntity> page = this.page(
                new Query<PurchaseEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void mergePurchase(MergeVo vo) {
        Long purchaseId = vo.getPurchaseId();
        if(purchaseId==null){
            PurchaseEntity purchaseEntity = new PurchaseEntity();
            purchaseEntity.setCreateTime(new Date());
            purchaseEntity.setUpdateTime(new Date());
            purchaseEntity.setStatus(WareConstant.CREATED.getCode());
            this.save(purchaseEntity);
            purchaseId=purchaseEntity.getId();
        }
        //TODO 确认采购单是0 或 1才可以和并
        List<Long> items = vo.getItems();
        Long finalPurchaseId = purchaseId;
        List<PurchaseDetailEntity> purchaseDetailEntities = items.stream().map(item -> {
            PurchaseDetailEntity detailEntity = new PurchaseDetailEntity();
            detailEntity.setId(item);
            detailEntity.setPurchaseId(finalPurchaseId);
            detailEntity.setStatus(WareConstant.PurchaseDetialConstant.ASSIGNED.getCode());
            return detailEntity;
        }).collect(Collectors.toList());
        purchaseDetailService.updateBatchById(purchaseDetailEntities);
        PurchaseEntity purchaseEntity = new PurchaseEntity();
        //purchaseEntity.setCreateTime(new Date());
        purchaseEntity.setUpdateTime(new Date());
        purchaseEntity.setId(purchaseId);
        this.updateById(purchaseEntity);

    }

    @Transactional
    @Override
    public void recevied(List<Long> ids) {
        //1,确认当前采购单是新建后已分配状态
        List<PurchaseEntity> purchaseEntities = ids.stream().map(i -> {
            PurchaseEntity purchaseEntity = this.getById(i);
            return purchaseEntity;
        }).filter(item -> {
            return item.getStatus() == WareConstant.CREATED.getCode() || item.getStatus() == WareConstant.ASSIGNED.getCode();
        }).map(item -> {
            item.setStatus(WareConstant.RECEIVE.getCode());
            //item.setCreateTime(new Date());
            item.setUpdateTime(new Date());
            return item;
        }).collect(Collectors.toList());

        //2,改变采购单的状态

        this.updateBatchById(purchaseEntities);
        //3,改变采购项的状态

        purchaseEntities.forEach( item-> {
            List<PurchaseDetailEntity> purchaseDetailEntities =purchaseDetailService.listDetialByPurchaseId(item.getId());
            System.out.println(purchaseDetailEntities);
            List<PurchaseDetailEntity> collect = purchaseDetailEntities.stream().map(item1 -> {
                PurchaseDetailEntity purchaseDetailEntity = new PurchaseDetailEntity();
                purchaseDetailEntity.setId(item1.getId());
                purchaseDetailEntity.setStatus(WareConstant.PurchaseDetialConstant.BUYING.getCode());
                return purchaseDetailEntity;
            }).collect(Collectors.toList());

            purchaseDetailService.updateBatchById(collect);
        });
    }

    @Transactional
    @Override
    public void done(PurchaseDoneVo vo) {

        //改采购项状态
        Boolean flag=true;
        List<PurchaseItemVo> items = vo.getItems();
        ArrayList<PurchaseDetailEntity> update = new ArrayList<>();
        for(PurchaseItemVo item:items){
            PurchaseDetailEntity purchaseDetailEntity = new PurchaseDetailEntity();
            if(item.getStatus() == WareConstant.EXCEPTION.getCode()){
                flag=false;
                purchaseDetailEntity.setStatus(item.getStatus());
            }else{
                purchaseDetailEntity.setStatus(WareConstant.PurchaseDetialConstant.FINISH.getCode());
                //将成功采购的入库
                PurchaseDetailEntity detailEntity = purchaseDetailService.getById(item.getItemId());
                wareSkuService.addStock(detailEntity.getSkuId(),detailEntity.getWareId(),detailEntity.getSkuNum());
            }
            purchaseDetailEntity.setId(item.getItemId());
            update.add(purchaseDetailEntity);
        }
        purchaseDetailService.updateBatchById(update);

        //改采购单状态
        PurchaseEntity purchaseEntity = this.getById(vo.getId());
        purchaseEntity.setStatus(flag?WareConstant.FINISH.getCode():WareConstant.EXCEPTION.getCode());
        purchaseEntity.setUpdateTime(new Date());
        this.updateById(purchaseEntity);


    }

}