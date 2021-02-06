package zwf.mymall.ware.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.util.StringUtils;
import zwf.mymall.common.utils.PageUtils;
import zwf.mymall.common.utils.Query;

import zwf.mymall.common.utils.R;
import zwf.mymall.ware.dao.WareSkuDao;
import zwf.mymall.ware.entity.WareSkuEntity;
import zwf.mymall.ware.fegin.ProductFeignService;
import zwf.mymall.ware.service.WareSkuService;


@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

    @Autowired
    ProductFeignService productFeignService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<WareSkuEntity> wrapper = new QueryWrapper<>();

        String skuId = (String) params.get("skuId");
        if(!StringUtils.isEmpty(skuId)){
            wrapper.eq("sku_id",skuId);
        }
        String wareId = (String) params.get("wareId");
        if(!StringUtils.isEmpty(wareId)){
            wrapper.eq("wareId",wareId);
        }
        IPage<WareSkuEntity> page = this.page(
                new Query<WareSkuEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    @Override
    public void addStock(Long skuId, Long wareId, Integer skuNum) {
        //如果有库存更新，没有新建
        List<WareSkuEntity> wareSkuEntities = this.baseMapper.selectList(new QueryWrapper<WareSkuEntity>().eq("sku_id", skuId).eq("ware_id", wareId));
        if(wareSkuEntities==null||wareSkuEntities.size()==0){
            WareSkuEntity wareSkuEntity = new WareSkuEntity();
            wareSkuEntity.setSkuId(skuId);
            wareSkuEntity.setStock(skuNum);
            wareSkuEntity.setWareId(wareId);
            wareSkuEntity.setStockLocked(0);
            //TODO 远程查询sku的名字,如果失败整个事务不需要回滚
            try {
                R info = productFeignService.info(skuId);
                Map<String,Object> map= (Map<String, Object>) info.get("pmsSkuInfo");
                if(info.getCode()==0){
                    wareSkuEntity.setSkuName((String) map.get("skuName"));
                }

            } catch (Exception e) {

            }
            this.baseMapper.insert(wareSkuEntity);
        }else{
            this.baseMapper.addStock(skuId,wareId,skuNum);
        }

    }

}