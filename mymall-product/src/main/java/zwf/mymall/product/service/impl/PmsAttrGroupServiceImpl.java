package zwf.mymall.product.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.util.StringUtils;
import zwf.mymall.common.utils.PageUtils;
import zwf.mymall.common.utils.Query;

import zwf.mymall.product.dao.PmsAttrGroupDao;
import zwf.mymall.product.entity.PmsAttrAttrgroupRelationEntity;
import zwf.mymall.product.entity.PmsAttrEntity;
import zwf.mymall.product.entity.PmsAttrGroupEntity;
import zwf.mymall.product.service.PmsAttrAttrgroupRelationService;
import zwf.mymall.product.service.PmsAttrGroupService;
import zwf.mymall.product.service.PmsAttrService;
import zwf.mymall.product.vo.AttrGroupWithAttrVo;


@Service("pmsAttrGroupService")
public class PmsAttrGroupServiceImpl extends ServiceImpl<PmsAttrGroupDao, PmsAttrGroupEntity> implements PmsAttrGroupService {

    @Autowired
    PmsAttrService pmsAttrService;
    @Autowired
    PmsAttrAttrgroupRelationService relationService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PmsAttrGroupEntity> page = this.page(
                new Query<PmsAttrGroupEntity>().getPage(params),
                new QueryWrapper<PmsAttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long catelogId) {
        String key = (String)params.get("key");
        QueryWrapper<PmsAttrGroupEntity> wrapper = new QueryWrapper<PmsAttrGroupEntity>();
        if(!StringUtils.isEmpty(key)){
            wrapper.and((obj)->{
                obj.eq("attr_group_id",key).or().like("attr_group_name",key);
            });
        }
        if(catelogId==0){
            IPage<PmsAttrGroupEntity> page = this.page(
                    new Query<PmsAttrGroupEntity>().getPage(params),
                    wrapper
                    //new QueryWrapper<PmsAttrGroupEntity>()
            );

            return new PageUtils(page);
        }else{
            wrapper.eq("catelog_id", catelogId);
            IPage<PmsAttrGroupEntity> page = this.page(
                    new Query<PmsAttrGroupEntity>().getPage(params),
                    wrapper
            );
            return new PageUtils(page);
        }

    }

    @Override
    public List<AttrGroupWithAttrVo> getAttrGroupWithAttrByCateligId(Long catelogId) {
        List<PmsAttrGroupEntity> groupEntities = this.list(new QueryWrapper<PmsAttrGroupEntity>().eq("catelog_id", catelogId));
        List<AttrGroupWithAttrVo> groupWithAttrVos = groupEntities.stream().map((item) -> {
            AttrGroupWithAttrVo attrGroupWithAttrVo = new AttrGroupWithAttrVo();
            BeanUtils.copyProperties(item, attrGroupWithAttrVo);
            List<PmsAttrEntity> relationAttr = pmsAttrService.getRelationAttr(item.getAttrGroupId());
            attrGroupWithAttrVo.setAttrs(relationAttr);
            return attrGroupWithAttrVo;
        }).collect(Collectors.toList());
        return groupWithAttrVos;
    }
}