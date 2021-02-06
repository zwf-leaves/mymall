package zwf.mymall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import zwf.mymall.common.constant.ProductConstant;
import zwf.mymall.common.utils.PageUtils;
import zwf.mymall.common.utils.Query;

import zwf.mymall.product.dao.PmsAttrAttrgroupRelationDao;
import zwf.mymall.product.dao.PmsAttrDao;
import zwf.mymall.product.dao.PmsAttrGroupDao;
import zwf.mymall.product.dao.PmsCategoryDao;
import zwf.mymall.product.entity.PmsAttrAttrgroupRelationEntity;
import zwf.mymall.product.entity.PmsAttrEntity;
import zwf.mymall.product.entity.PmsAttrGroupEntity;
import zwf.mymall.product.entity.PmsCategoryEntity;
import zwf.mymall.product.service.PmsAttrService;
import zwf.mymall.product.service.PmsCategoryService;
import zwf.mymall.product.vo.AttrGroupRelationVo;
import zwf.mymall.product.vo.PmsAttrRerspVo;
import zwf.mymall.product.vo.PmsAttrVo;

import javax.annotation.Resource;


@Service("pmsAttrService")
public class PmsAttrServiceImpl extends ServiceImpl<PmsAttrDao, PmsAttrEntity> implements PmsAttrService {


    @Autowired
    @Resource
    PmsAttrAttrgroupRelationDao relationDao;
    @Resource
    PmsAttrGroupDao groupDao;
    @Resource
    PmsCategoryDao categoryDao;
    @Resource
    PmsCategoryService categoryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PmsAttrEntity> page = this.page(
                new Query<PmsAttrEntity>().getPage(params),
                new QueryWrapper<PmsAttrEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void saveAttr(PmsAttrVo pmsAttr) {
        PmsAttrEntity attrEntity = new PmsAttrEntity();
        BeanUtils.copyProperties(pmsAttr,attrEntity);
        this.save(attrEntity);
        if(pmsAttr.getAttrType()== ProductConstant.AttrEnue.ATTR_TYPE_BASE.getCode()&&pmsAttr.getAttrGroupId()!=null){
            PmsAttrAttrgroupRelationEntity entity = new PmsAttrAttrgroupRelationEntity();
            entity.setAttrGroupId(pmsAttr.getAttrGroupId());
            entity.setAttrId(attrEntity.getAttrId());
            relationDao.insert(entity);
        }

    }



    @Override
    public PageUtils queryBasePage(Map<String, Object> params, Long catalogId,String attrType) {
        String key = (String)params.get("key");
        QueryWrapper<PmsAttrEntity> wrapper = new QueryWrapper<PmsAttrEntity>().eq("attr_type","base".equalsIgnoreCase(attrType)?
                ProductConstant.AttrEnue.ATTR_TYPE_BASE.getCode():ProductConstant.AttrEnue.ATTY_TYPE_SALE.getCode());
        if(catalogId!=0){
            wrapper.eq("catelog_id",catalogId);
        }
        if(!StringUtils.isEmpty(key)){
            wrapper.and((obj)->{
               obj.like("attr_id",key).or().like("attr_name",key);
            });
        }
        IPage<PmsAttrEntity> page = this.page(
                new Query<PmsAttrEntity>().getPage(params),
                wrapper
        );
        PageUtils pageUtils = new PageUtils(page);
        List<PmsAttrEntity> records = page.getRecords();
        List<PmsAttrRerspVo> rerspVos = records.stream().map((item) -> {
            PmsAttrRerspVo vo = new PmsAttrRerspVo();
            BeanUtils.copyProperties(item, vo);

            if("base".equalsIgnoreCase(attrType)){
                PmsAttrAttrgroupRelationEntity entity = relationDao.selectOne(new UpdateWrapper<PmsAttrAttrgroupRelationEntity>().eq("attr_id", item.getAttrId()));
                if (entity != null&&entity.getAttrGroupId()!=null) {
                    PmsAttrGroupEntity pmsAttrGroupEntity = groupDao.selectById(entity.getAttrGroupId());
                    if(pmsAttrGroupEntity!=null){
                        vo.setGroupName(pmsAttrGroupEntity.getAttrGroupName());
                    }

                }
            }

            PmsCategoryEntity pmsCategoryEntity = categoryDao.selectById(item.getCatelogId());
            if (pmsCategoryEntity != null) {
                vo.setCatelogName(pmsCategoryEntity.getName());
            }
            return vo;
        }).collect(Collectors.toList());
        pageUtils.setList(rerspVos);
        return pageUtils;
    }

    @Override
    public PmsAttrRerspVo attrInfo(Long attrId) {
        PmsAttrRerspVo rerspVo = new PmsAttrRerspVo();
        PmsAttrEntity attrEntity = this.getById(attrId);
        BeanUtils.copyProperties(attrEntity,rerspVo);
        if(attrEntity.getAttrType()==ProductConstant.AttrEnue.ATTR_TYPE_BASE.getCode()){
            PmsAttrAttrgroupRelationEntity relationEntity = relationDao.selectOne(new UpdateWrapper<PmsAttrAttrgroupRelationEntity>().eq("attr_id", attrId));
            if(relationEntity!=null){
                rerspVo.setAttrGroupId(relationEntity.getAttrGroupId());
                PmsAttrGroupEntity groupEntity = groupDao.selectById(relationEntity.getAttrGroupId());
                if(groupEntity!=null){
                    rerspVo.setGroupName(groupEntity.getAttrGroupName());
                }
            }
        }

        Long catelogId = attrEntity.getCatelogId();

        Long[] catelogPath = categoryService.findCatelogPath(catelogId);
        rerspVo.setCatelogPath(catelogPath);

        PmsCategoryEntity pmsCategoryEntity = categoryDao.selectById(catelogId);
        if(pmsCategoryEntity!=null){
            rerspVo.setCatelogName(pmsCategoryEntity.getName());
        }

        return rerspVo;
    }

    @Transactional
    @Override
    public void updateAttr(PmsAttrVo pmsAttr) {
        PmsAttrEntity attrEntity = new PmsAttrEntity();
        BeanUtils.copyProperties(pmsAttr,attrEntity);
        this.updateById(attrEntity);
        if(attrEntity.getAttrType()==ProductConstant.AttrEnue.ATTR_TYPE_BASE.getCode()){
            PmsAttrAttrgroupRelationEntity pmsAttrAttrgroupRelationEntity = new PmsAttrAttrgroupRelationEntity();
            pmsAttrAttrgroupRelationEntity.setAttrId(pmsAttr.getAttrId());
            pmsAttrAttrgroupRelationEntity.setAttrGroupId(pmsAttr.getAttrGroupId());
            Integer count = relationDao.selectCount(new QueryWrapper<PmsAttrAttrgroupRelationEntity>().eq("attr_id", pmsAttr.getAttrId()));
            if(count>0){
                relationDao.update(pmsAttrAttrgroupRelationEntity,new UpdateWrapper<PmsAttrAttrgroupRelationEntity>().eq("attr_id",pmsAttr.getAttrId()));
            }else{
                relationDao.insert(pmsAttrAttrgroupRelationEntity);
            }
        }

    }

    /**
     * 根据分组id查找所有属性
     * @param attrgroupId
     * @return
     */
    @Override
    public List<PmsAttrEntity> getRelationAttr(Long attrgroupId) {
        List<PmsAttrAttrgroupRelationEntity> relationEntityList = relationDao.selectList(new QueryWrapper<PmsAttrAttrgroupRelationEntity>().eq("attr_group_id", attrgroupId));
        List<Long> collect = relationEntityList.stream().map((attr) -> {
            return attr.getAttrId();
        }).collect(Collectors.toList());

        if(collect==null||collect.size()==0){
            return null;
        }
        List<PmsAttrEntity> pmsAttrEntities = (List<PmsAttrEntity>) this.listByIds(collect);
        return pmsAttrEntities;
    }

    @Override
    public void deleteRelation(AttrGroupRelationVo[] relationVo) {
        //relationDao.delete(new QueryWrapper<>().eq("attr_id",1L).eq("attr_group_id",1L));
        List<PmsAttrAttrgroupRelationEntity> collect = Arrays.asList(relationVo).stream().map((item) -> {
            PmsAttrAttrgroupRelationEntity entity = new PmsAttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(item, entity);
            return entity;
        }).collect(Collectors.toList());
        relationDao.deleteBanchRelation(collect);
    }

    @Override
    public PageUtils getNoRelationAttr(Map<String, Object> params, Long attrgroupId) {
        //当前分组只能关联自己所属的分类里面的所有属性
        PmsAttrGroupEntity pmsAttrGroupEntity = groupDao.selectById(attrgroupId);
        Long catelogId = pmsAttrGroupEntity.getCatelogId();
        //当前分组只能关联别的分组么没有关联的属性
        List<PmsAttrGroupEntity> entityList = groupDao.selectList(new QueryWrapper<PmsAttrGroupEntity>().eq("catelog_id ", catelogId));
        List<Long> collect = entityList.stream().map((item) -> {
            return item.getAttrGroupId();
        }).collect(Collectors.toList());

        List<PmsAttrAttrgroupRelationEntity> relationEntities = relationDao.selectList(new QueryWrapper<PmsAttrAttrgroupRelationEntity>().in("attr_group_id", collect));

        List<Long> collect1 = relationEntities.stream().map((item) -> {
            return item.getAttrId();
        }).collect(Collectors.toList());
        System.out.println(collect1);
        String key = (String)params.get("key");
        QueryWrapper<PmsAttrEntity> wrapper = new QueryWrapper<PmsAttrEntity>().eq("catelog_id", catelogId).eq("attr_type",ProductConstant.AttrEnue.ATTR_TYPE_BASE.getCode());
        if(collect1!=null&&collect1.size()>0){
            wrapper.notIn("attr_id", collect1);
        }
        if(!StringUtils.isEmpty(key)){
            wrapper.and((item)->{
                item.eq("attr_id",key).or().like("attr_name",key);
            });
        }
        IPage<PmsAttrEntity> page = this.page(new Query<PmsAttrEntity>().getPage(params), wrapper);

        PageUtils pageUtils = new PageUtils(page);
        return pageUtils;
    }

}