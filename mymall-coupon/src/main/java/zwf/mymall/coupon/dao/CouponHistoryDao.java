package zwf.mymall.coupon.dao;

import zwf.mymall.coupon.entity.CouponHistoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券领取历史记录
 *
 * @author zwf
 * @email sunlightcs@gmail.com
 * @date 2020-12-08 20:47:45
 */
@Mapper
public interface CouponHistoryDao extends BaseMapper<CouponHistoryEntity> {

}
