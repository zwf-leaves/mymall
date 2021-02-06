package zwf.mymall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import zwf.mymall.common.utils.PageUtils;
import zwf.mymall.product.entity.PmsSpuInfoDescEntity;
import zwf.mymall.product.entity.PmsSpuInfoEntity;
import zwf.mymall.product.vo.SupSaveVo;

import java.util.Map;

/**
 * spu信息
 *
 * @author zwf
 * @email sunlightcs@gmail.com
 * @date 2020-12-08 11:16:02
 */
public interface PmsSpuInfoService extends IService<PmsSpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSpuInfo(SupSaveVo vo);

    void saveBaseSpuInfo(PmsSpuInfoEntity spuInfoEntity);


    PageUtils queryPageByCondition(Map<String, Object> params);

}

