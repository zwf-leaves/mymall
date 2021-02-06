package zwf.mymall.product.dao;

import org.apache.ibatis.annotations.Param;
import zwf.mymall.product.entity.PmsAttrAttrgroupRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 属性&属性分组关联
 *
 * @author zwf
 * @email sunlightcs@gmail.com
 * @date 2020-12-08 11:16:02
 */
@Mapper
public interface PmsAttrAttrgroupRelationDao extends BaseMapper<PmsAttrAttrgroupRelationEntity> {

    void deleteBanchRelation(@Param("collect") List<PmsAttrAttrgroupRelationEntity> collect);

}
