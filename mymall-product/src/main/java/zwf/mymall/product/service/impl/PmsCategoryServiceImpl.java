package zwf.mymall.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import zwf.mymall.common.utils.PageUtils;
import zwf.mymall.common.utils.Query;

import zwf.mymall.product.dao.PmsCategoryDao;
import zwf.mymall.product.entity.PmsCategoryEntity;
import zwf.mymall.product.service.PmsCategoryBrandRelationService;
import zwf.mymall.product.service.PmsCategoryService;


@Service("pmsCategoryService")
public class PmsCategoryServiceImpl extends ServiceImpl<PmsCategoryDao, PmsCategoryEntity> implements PmsCategoryService {

    @Autowired
    PmsCategoryBrandRelationService pmsCategoryBrandRelationService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PmsCategoryEntity> page = this.page(
                new Query<PmsCategoryEntity>().getPage(params),
                new QueryWrapper<PmsCategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<PmsCategoryEntity> listWithTree() {
        List<PmsCategoryEntity> entities = baseMapper.selectList(null);
        return entities;
    }

    @Override
    public void removeMenusById(List<Long> asList) {
        //TODO  检查要删除的菜单是否在别处引用
        baseMapper.deleteBatchIds(asList);
    }

    @Override
    public Long[] findCatelogPath(Long catelogId) {
        List<Long> paths=new ArrayList<>();
        List<Long> parentPath = findparentPath(catelogId, paths);
        Collections.reverse(parentPath);
        return (Long[]) parentPath.toArray(new Long[parentPath.size()]);
    }

    @Transactional
    @Override
    public void updateCascade(PmsCategoryEntity pmsCategory) {
        this.updateById(pmsCategory);
        pmsCategoryBrandRelationService.updateCategory(pmsCategory.getCatId(),pmsCategory.getName());

    }

    private List<Long> findparentPath(Long catelogId,List<Long> paths){
        paths.add(catelogId);
        PmsCategoryEntity byId = this.getById(catelogId);
        if(byId.getParentCid()!=0){
            findparentPath(byId.getParentCid(),paths);
        }
        return paths;
    }

}