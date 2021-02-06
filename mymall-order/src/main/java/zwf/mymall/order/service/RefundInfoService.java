package zwf.mymall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import zwf.mymall.common.utils.PageUtils;
import zwf.mymall.order.entity.RefundInfoEntity;

import java.util.Map;

/**
 * 退款信息
 *
 * @author zwf
 * @email sunlightcs@gmail.com
 * @date 2020-12-08 21:17:45
 */
public interface RefundInfoService extends IService<RefundInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

