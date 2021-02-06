package zwf.mymall.product.vo;

import lombok.Data;

/**
 * @author zwf
 * @create 2020-12-27-17:16
 */
@Data
public class PmsAttrRerspVo extends PmsAttrVo {

    private String catelogName;
    private String groupName;
    private Long[] catelogPath;

}
