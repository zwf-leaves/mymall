package zwf.mymall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import zwf.mymall.common.utils.PageUtils;
import zwf.mymall.common.utils.Query;

import zwf.mymall.product.dao.PmsBrandDao;
import zwf.mymall.product.dao.PmsCategoryBrandRelationDao;
import zwf.mymall.product.dao.PmsCategoryDao;
import zwf.mymall.product.entity.PmsBrandEntity;
import zwf.mymall.product.entity.PmsCategoryBrandRelationEntity;
import zwf.mymall.product.entity.PmsCategoryEntity;
import zwf.mymall.product.service.PmsBrandService;
import zwf.mymall.product.service.PmsCategoryBrandRelationService;

import javax.annotation.Resource;


@Service("pmsCategoryBrandRelationService")
public class PmsCategoryBrandRelationServiceImpl extends ServiceImpl<PmsCategoryBrandRelationDao, PmsCategoryBrandRelationEntity> implements PmsCategoryBrandRelationService {

    @Autowired
    @Resource
    PmsBrandDao pmsBrandDao;
    @Autowired
    @Resource
    PmsCategoryDao pmsCategoryDao;

    @Autowired
    PmsBrandService pmsBrandService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PmsCategoryBrandRelationEntity> page = this.page(
                new Query<PmsCategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<PmsCategoryBrandRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveDetial(PmsCategoryBrandRelationEntity pmsCategoryBrandRelation) {
        Long brandId = pmsCategoryBrandRelation.getBrandId();
        Long catelogId = pmsCategoryBrandRelation.getCatelogId();
        PmsBrandEntity pmsBrandEntity = pmsBrandDao.selectById(brandId);
        PmsCategoryEntity pmsCategoryEntity = pmsCategoryDao.selectById(catelogId);
        pmsCategoryBrandRelation.setBrandName(pmsBrandEntity.getName());
        pmsCategoryBrandRelation.setCatelogName(pmsCategoryEntity.getName());
        this.save(pmsCategoryBrandRelation);

    }

    @Override
    public void updateBrand(Long brandId, String name) {
        PmsCategoryBrandRelationEntity entity = new PmsCategoryBrandRelationEntity();
        entity.setBrandName(name);
        entity.setBrandId(brandId);
        this.update(entity,new UpdateWrapper<PmsCategoryBrandRelationEntity>().eq("brand_id",brandId));
    }

    @Override
    public void updateCategory(Long catId, String name) {
        this.baseMapper.updateCategory(catId,name);
    }

    @Override
    public List<PmsBrandEntity> getBrandsByCatId(Long catId) {

        List<PmsCategoryBrandRelationEntity> relationEntities = this.baseMapper.selectList(new QueryWrapper<PmsCategoryBrandRelationEntity>().eq("catelog_id", catId));
        List<PmsBrandEntity> brandEntities = relationEntities.stream().map((item) -> {
            return pmsBrandService.getById(item.getBrandId());
        }).collect(Collectors.toList());
        return brandEntities;
    }

}