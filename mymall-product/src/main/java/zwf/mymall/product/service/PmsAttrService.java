package zwf.mymall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import zwf.mymall.common.utils.PageUtils;
import zwf.mymall.product.entity.PmsAttrEntity;
import zwf.mymall.product.vo.AttrGroupRelationVo;
import zwf.mymall.product.vo.PmsAttrRerspVo;
import zwf.mymall.product.vo.PmsAttrVo;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author zwf
 * @email sunlightcs@gmail.com
 * @date 2020-12-08 11:16:02
 */
public interface PmsAttrService extends IService<PmsAttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttr(PmsAttrVo pmsAttr);

    PageUtils queryBasePage(Map<String, Object> params, Long catalogId, String attrType);

    PmsAttrRerspVo attrInfo(Long attrId);

    void updateAttr(PmsAttrVo pmsAttr);

    List<PmsAttrEntity> getRelationAttr(Long attrgroupId);

    void deleteRelation(AttrGroupRelationVo[] relationVo);

    PageUtils getNoRelationAttr(Map<String, Object> params, Long attrgroupId);


}

