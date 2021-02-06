package zwf.mymall.order.dao;

import zwf.mymall.order.entity.OrderItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单项信息
 *
 * @author zwf
 * @email sunlightcs@gmail.com
 * @date 2020-12-08 21:17:45
 */
@Mapper
public interface OrderItemDao extends BaseMapper<OrderItemEntity> {

}
