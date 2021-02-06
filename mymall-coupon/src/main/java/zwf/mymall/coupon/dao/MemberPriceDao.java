package zwf.mymall.coupon.dao;

import zwf.mymall.coupon.entity.MemberPriceEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品会员价格
 *
 * @author zwf
 * @email sunlightcs@gmail.com
 * @date 2020-12-08 20:47:45
 */
@Mapper
public interface MemberPriceDao extends BaseMapper<MemberPriceEntity> {

}
