package zwf.mymall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import zwf.mymall.common.utils.PageUtils;
import zwf.mymall.order.entity.OrderEntity;

import java.util.Map;

/**
 * 订单
 *
 * @author zwf
 * @email sunlightcs@gmail.com
 * @date 2020-12-08 21:17:45
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

