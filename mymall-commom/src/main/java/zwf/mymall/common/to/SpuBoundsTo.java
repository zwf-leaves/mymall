package zwf.mymall.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zwf
 * @create 2020-12-30-16:19
 */
@Data
public class SpuBoundsTo {
    private Long spuId;
    private BigDecimal buyBounds;
    private BigDecimal growBounds;

}
