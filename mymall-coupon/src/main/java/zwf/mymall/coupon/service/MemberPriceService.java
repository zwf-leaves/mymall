package zwf.mymall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import zwf.mymall.common.utils.PageUtils;
import zwf.mymall.coupon.entity.MemberPriceEntity;

import java.util.Map;

/**
 * 商品会员价格
 *
 * @author zwf
 * @email sunlightcs@gmail.com
 * @date 2020-12-08 20:47:45
 */
public interface MemberPriceService extends IService<MemberPriceEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

