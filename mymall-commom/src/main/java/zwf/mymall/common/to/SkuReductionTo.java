package zwf.mymall.common.to;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zwf
 * @create 2020-12-30-16:39
 */
@Data
public class SkuReductionTo {

    private Long skuId;
    private int fullCount;
    private BigDecimal discount;
    private int countStatus;
    private BigDecimal fullPrice;
    private BigDecimal reducePrice;
    private int priceStatus;

    private List<MemberPrice> memberPrice;
}
