package zwf.mymall.member.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import zwf.mymall.common.utils.R;

/**
 * @author zwf
 * @create 2020-12-09-15:49
 */
/*
* 声明式远程调用
* */
@FeignClient("mymall-coupon")
public interface CouponFeginService {
    @RequestMapping("/coupon/coupon/member/list")
    public R membercoppons();
}
