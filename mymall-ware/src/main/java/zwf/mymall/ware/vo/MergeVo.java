package zwf.mymall.ware.vo;

import lombok.Data;

import java.util.List;

/**
 * @author zwf
 * @create 2020-12-31-18:12
 */
@Data
public class MergeVo {
    private Long purchaseId;
    private List<Long> items;
}
