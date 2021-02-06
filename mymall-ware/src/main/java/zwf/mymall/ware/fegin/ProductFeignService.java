package zwf.mymall.ware.fegin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import zwf.mymall.common.utils.R;

/**
 * @author zwf
 * @create 2021-01-01-18:45
 */
@FeignClient("mymall-product")
public interface ProductFeignService {
    @RequestMapping("/product/skuinfo/info/{skuId}")
    public R info(@PathVariable("skuId") Long skuId);
}
