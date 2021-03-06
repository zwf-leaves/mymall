package zwf.mymall.product.dao;

import zwf.mymall.product.entity.PmsCommentReplayEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品评价回复关系
 *
 * @author zwf
 * @email sunlightcs@gmail.com
 * @date 2020-12-08 11:16:02
 */
@Mapper
public interface PmsCommentReplayDao extends BaseMapper<PmsCommentReplayEntity> {

}
