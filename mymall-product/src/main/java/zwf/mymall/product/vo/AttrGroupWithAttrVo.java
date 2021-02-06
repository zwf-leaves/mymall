package zwf.mymall.product.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import zwf.mymall.product.entity.PmsAttrEntity;

import java.util.List;

/**
 * @author zwf
 * @create 2020-12-29-15:39
 */
@Data
public class AttrGroupWithAttrVo {

    private Long attrGroupId;
    /**
     * 组名
     */
    private String attrGroupName;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 描述
     */
    private String descript;
    /**
     * 组图标
     */
    private String icon;
    /**
     * 所属分类id
     */
    private Long catelogId;

    private List<PmsAttrEntity> attrs;
}
