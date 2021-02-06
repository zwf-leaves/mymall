package zwf.mymall.product.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import zwf.mymall.common.utils.PageUtils;
import zwf.mymall.common.utils.Query;

import zwf.mymall.product.dao.PmsProductAttrValueDao;
import zwf.mymall.product.entity.PmsProductAttrValueEntity;
import zwf.mymall.product.service.PmsProductAttrValueService;


@Service("pmsProductAttrValueService")
public class PmsProductAttrValueServiceImpl extends ServiceImpl<PmsProductAttrValueDao, PmsProductAttrValueEntity> implements PmsProductAttrValueService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PmsProductAttrValueEntity> page = this.page(
                new Query<PmsProductAttrValueEntity>().getPage(params),
                new QueryWrapper<PmsProductAttrValueEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveProductAttr(List<PmsProductAttrValueEntity> productAttrValueEntities) {
        this.saveBatch(productAttrValueEntities);
    }

    @Override
    public List<PmsProductAttrValueEntity> baseListforspu(Long spuId) {
        List<PmsProductAttrValueEntity> entityList = this.baseMapper.selectList(new QueryWrapper<PmsProductAttrValueEntity>().eq("spu_id", spuId));
        return entityList;
    }

    @Transactional
    @Override
    public void updateSpuAttr(Long spuId, List<PmsProductAttrValueEntity> list) {
        this.baseMapper.delete(new QueryWrapper<PmsProductAttrValueEntity>().eq("spu_id",spuId));
        List<PmsProductAttrValueEntity> collect = list.stream().map(item -> {
            item.setSpuId(spuId);
            return item;
        }).collect(Collectors.toList());
        this.saveBatch(collect);
    }

}