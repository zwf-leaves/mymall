package zwf.mymall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import org.hibernate.validator.constraints.URL;
import org.springframework.validation.annotation.Validated;
import zwf.mymall.common.valid.AddGroup;
import zwf.mymall.common.valid.ListValue;
import zwf.mymall.common.valid.UpdataStatus;
import zwf.mymall.common.valid.UpdateGroup;

import javax.validation.constraints.*;

/**
 * 品牌
 *
 * @author zwf
 * @email sunlightcs@gmail.com
 * @date 2020-12-08 11:16:02
 */
@Data
@TableName("pms_brand")
public class PmsBrandEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 品牌id
     */
    @NotNull(message = "id不能为空",groups = {UpdateGroup.class})
    @Null(message = "id要为空", groups = {AddGroup.class})
    @TableId
    private Long brandId;
    /**
     * 品牌名
     */
    @NotBlank(message = "品牌名称不能为空",groups = {UpdateGroup.class,AddGroup.class})
    private String name;
    /**
     * 品牌logo地址
     */
    @NotBlank(groups = {AddGroup.class})
    @URL(message = "logo必须是一个合法的URL",groups = {UpdateGroup.class,AddGroup.class})
    private String logo;
    /**
     * 介绍
     */

    private String descript;
    /**
     * 显示状态[0-不显示；1-显示]
     */
    @NotNull(groups = {UpdataStatus.class,UpdataStatus.class})
    @ListValue(vals={0,1},groups = {UpdataStatus.class,UpdataStatus.class},message = "必须为指定值~~")
    private Integer showStatus;
    /**
     * 检索首字母
     */
    @NotEmpty(groups = {AddGroup.class})
    @Pattern(regexp = "^[a-zA-Z]$",message = "请输入一个合法的首字母",groups = {UpdateGroup.class,AddGroup.class})
    private String firstLetter;
    /**
     * 排序
     */
    @NotNull(groups = {AddGroup.class})
    @Min(value = 0,message = "大于0",groups = {UpdateGroup.class,AddGroup.class})
    private Integer sort;

}
