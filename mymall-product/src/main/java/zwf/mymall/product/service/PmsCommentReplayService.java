package zwf.mymall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import zwf.mymall.common.utils.PageUtils;
import zwf.mymall.product.entity.PmsCommentReplayEntity;

import java.util.Map;

/**
 * 商品评价回复关系
 *
 * @author zwf
 * @email sunlightcs@gmail.com
 * @date 2020-12-08 11:16:02
 */
public interface PmsCommentReplayService extends IService<PmsCommentReplayEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

