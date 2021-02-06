package zwf.mymall.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import zwf.mymall.common.utils.PageUtils;
import zwf.mymall.common.utils.Query;

import zwf.mymall.product.dao.PmsBrandDao;
import zwf.mymall.product.entity.PmsBrandEntity;
import zwf.mymall.product.service.PmsBrandService;
import zwf.mymall.product.service.PmsCategoryBrandRelationService;


@Service("pmsBrandService")
public class PmsBrandServiceImpl extends ServiceImpl<PmsBrandDao, PmsBrandEntity> implements PmsBrandService {

    @Autowired
    PmsCategoryBrandRelationService pmsCategoryBrandRelationService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String)params.get("key");
        QueryWrapper<PmsBrandEntity> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(key)){
            wrapper.eq("brand_id",key).or().like("name",key);
        }
        IPage<PmsBrandEntity> page = this.page(
                new Query<PmsBrandEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void updateDetail(PmsBrandEntity pmsBrand) {
        this.updateById(pmsBrand);
        if(!StringUtils.isEmpty(pmsBrand.getName())){

            pmsCategoryBrandRelationService.updateBrand(pmsBrand.getBrandId(),pmsBrand.getName());
            //TODO 更新其他关联
        }
    }

}