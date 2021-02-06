package zwf.mymall.product.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import zwf.mymall.common.to.SkuReductionTo;
import zwf.mymall.common.to.SpuBoundsTo;
import zwf.mymall.common.utils.PageUtils;
import zwf.mymall.common.utils.Query;

import zwf.mymall.common.utils.R;
import zwf.mymall.product.dao.PmsSpuInfoDao;
import zwf.mymall.product.entity.*;
import zwf.mymall.product.fegin.ConponFeginService;
import zwf.mymall.product.service.*;
import zwf.mymall.product.vo.*;


@Service("pmsSpuInfoService")
public class PmsSpuInfoServiceImpl extends ServiceImpl<PmsSpuInfoDao, PmsSpuInfoEntity> implements PmsSpuInfoService {


    @Autowired
    PmsSpuInfoDescService spuInfoDescService;
    @Autowired
    PmsSpuImagesService spuImagesService;
    @Autowired
    PmsAttrService attrService;
    @Autowired
    PmsProductAttrValueService productAttrValueService;
    @Autowired
    PmsSkuInfoService skuInfoService;
    @Autowired
    PmsSkuImagesService skuImagesService;
    @Autowired
    PmsSkuSaleAttrValueService skuSaleAttrValueService;
    @Autowired
    ConponFeginService conponFeginService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PmsSpuInfoEntity> page = this.page(
                new Query<PmsSpuInfoEntity>().getPage(params),
                new QueryWrapper<PmsSpuInfoEntity>()
        );

        return new PageUtils(page);
    }


    /**
     * TODO 更多的只是高级部分讲
     * @param vo
     */
    @Transactional
    @Override
    public void saveSpuInfo(SupSaveVo vo) {
        //1,保存spu基本信息 pms_spu_info`
        PmsSpuInfoEntity spuInfoEntity = new PmsSpuInfoEntity();
        BeanUtils.copyProperties(vo,spuInfoEntity);
        spuInfoEntity.setCreateTime(new Date());
        spuInfoEntity.setUpdateTime(new Date());
        this.saveBaseSpuInfo(spuInfoEntity);
        //2,保存spu描述信息图片 `pms_spu_info_desc`
        List<String> decript = vo.getDecript();
        PmsSpuInfoDescEntity spuInfoDescEntity = new PmsSpuInfoDescEntity();
        spuInfoDescEntity.setSpuId(spuInfoEntity.getId());
        spuInfoDescEntity.setDecript(String.join(",",decript));
        spuInfoDescService.saveSpuInfoDesc(spuInfoDescEntity);
        //3,保存spu的图片集 pms_spu_images`
        List<String> images = vo.getImages();
        spuImagesService.saveImages(spuInfoEntity.getId(),images);
        //4,保存spu规格参数，`pms_product_attr_value`
        List<BaseAttrs> baseAttrs = vo.getBaseAttrs();
        List<PmsProductAttrValueEntity> productAttrValueEntities= baseAttrs.stream().map((item) -> {
            PmsProductAttrValueEntity productAttrValueEntity = new PmsProductAttrValueEntity();
            productAttrValueEntity.setAttrId(item.getAttrId());
            PmsAttrEntity attrEntity = attrService.getById(item.getAttrId());
            productAttrValueEntity.setAttrName(attrEntity.getAttrName());
            productAttrValueEntity.setAttrValue(item.getAttrValues());
            productAttrValueEntity.setQuickShow(item.getShowDesc());
            productAttrValueEntity.setSpuId(spuInfoEntity.getId());
            return productAttrValueEntity;
        }).collect(Collectors.toList());
        productAttrValueService.saveProductAttr(productAttrValueEntities);

        //5,保存spu的积分信息  `mymall_sms`->`sms_spu_bounds`
        Bounds bounds = vo.getBounds();
        SpuBoundsTo spuBoundsTo = new SpuBoundsTo();
        BeanUtils.copyProperties(bounds,spuBoundsTo);
        spuBoundsTo.setSpuId(spuInfoEntity.getId());
        R r = conponFeginService.saveSpuBounds(spuBoundsTo);

        if(r.getCode()!=0){
            log.error("远程保存spu积分信息失败");
        }
        //6,保存当前spu对应的所有sku信息

        List<Skus> skus = vo.getSkus();
        if(skus!=null&&skus.size()>0){
            skus.forEach((item)->{
                String defaultImage="";
                for(Images image:item.getImages()){
                    if(image.getDefaultImg()==1){
                        defaultImage=image.getImgUrl();
                    }
                }
                PmsSkuInfoEntity skuInfoEntity = new PmsSkuInfoEntity();
                BeanUtils.copyProperties(item,skuInfoEntity);
                skuInfoEntity.setBrandId(spuInfoEntity.getBrandId());
                skuInfoEntity.setCatalogId(spuInfoEntity.getCatalogId());
                skuInfoEntity.setSaleCount(0L);
                skuInfoEntity.setSpuId(spuInfoEntity.getId());
                skuInfoEntity.setSkuDefaultImg(defaultImage);
                //6.1),保存sku的基本信息 pms_sku_info
                skuInfoService.saveSkuInfo(skuInfoEntity);

                Long skuId = skuInfoEntity.getSkuId();

                List<PmsSkuImagesEntity> imagesEntities = item.getImages().stream().map((img) -> {
                    PmsSkuImagesEntity skuImagesEntity = new PmsSkuImagesEntity();
                    skuImagesEntity.setSkuId(skuId);
                    skuImagesEntity.setImgUrl(img.getImgUrl());
                    skuImagesEntity.setDefaultImg(img.getDefaultImg());
                    return skuImagesEntity;
                }).filter(entity -> {
                    return  !StringUtils.isEmpty(entity.getImgUrl());
                }).collect(Collectors.toList());
                //6.2）,保存sku图片信息 `pms_sku_images`
                //TODO 没有图片路径的不保存
                skuImagesService.saveBatch(imagesEntities);

                List<Attr> attr = item.getAttr();
                List<PmsSkuSaleAttrValueEntity> valueEntities = attr.stream().map((attr1) -> {
                    PmsSkuSaleAttrValueEntity skuSaleAttrValueEntity = new PmsSkuSaleAttrValueEntity();
                    BeanUtils.copyProperties(attr1, skuSaleAttrValueEntity);
                    skuSaleAttrValueEntity.setSkuId(skuId);
                    return skuSaleAttrValueEntity;
                }).collect(Collectors.toList());
                //6.3),保存sku的销售信息pms_sku_sale_attr_value``
                skuSaleAttrValueService.saveBatch(valueEntities);
                //6.4）sku的满减优惠等信息 `mymall_sms`->`sms_sku_full_reduction`\`sms_sku_ladder`\`sms_member_price`
                SkuReductionTo skuReductionTo = new SkuReductionTo();
                BeanUtils.copyProperties(item,skuReductionTo);
                skuReductionTo.setSkuId(skuId);
                if(skuReductionTo.getFullCount()>0||skuReductionTo.getFullPrice().compareTo(new BigDecimal("0"))==1){
                    R r1 = conponFeginService.saveSkuReducation(skuReductionTo);

                    if(r1.getCode()!=0){
                        log.error("远程保存sku优惠信息失败");
                    }
                }
            });
        }
    }

    @Override
    public void saveBaseSpuInfo(PmsSpuInfoEntity spuInfoEntity) {
        this.baseMapper.insert(spuInfoEntity);
    }

    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {
        QueryWrapper<PmsSpuInfoEntity> wrapper = new QueryWrapper<>();
        String key =(String) params.get("key");
        if(!StringUtils.isEmpty(key)){
            wrapper.and((w)->{
                w.eq("id",key).or().like("spu_name",key);
            });
        }
        String status =(String) params.get("status");
        if(!StringUtils.isEmpty(status)){
            wrapper.eq("publish_status",status);
        }
        String brandId =(String) params.get("brandId");
        if(!StringUtils.isEmpty(brandId)&&"0".equalsIgnoreCase(brandId)){
            wrapper.eq("brand_id",brandId);
        }
        String catelogId =(String) params.get("catelogId");
        if(!StringUtils.isEmpty(catelogId)&&"0".equalsIgnoreCase(catelogId)){
            wrapper.eq("catalog_id",catelogId);
        }
        IPage<PmsSpuInfoEntity> page = this.page(
                new Query<PmsSpuInfoEntity>().getPage(params),
               wrapper
        );
        return new PageUtils(page);
    }


}