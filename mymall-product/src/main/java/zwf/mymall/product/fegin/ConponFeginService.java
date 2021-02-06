package zwf.mymall.product.fegin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import zwf.mymall.common.to.SkuReductionTo;
import zwf.mymall.common.to.SpuBoundsTo;
import zwf.mymall.common.utils.R;

/**
 * @author zwf
 * @create 2020-12-30-15:58
 */
@FeignClient("mymall-coupon")
public interface ConponFeginService {
    @PostMapping("/coupon/spubounds/save")
    R saveSpuBounds(@RequestBody SpuBoundsTo spuBoundsTo);

    @PostMapping("/coupon/skufullreduction/saveinfo")
    R saveSkuReducation(@RequestBody SkuReductionTo skuReductionTo);
}
