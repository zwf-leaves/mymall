package zwf.mymall.product.dao;

import org.apache.ibatis.annotations.Param;
import zwf.mymall.product.entity.PmsCategoryBrandRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 品牌分类关联
 *
 * @author zwf
 * @email sunlightcs@gmail.com
 * @date 2020-12-08 11:16:02
 */
@Mapper
public interface PmsCategoryBrandRelationDao extends BaseMapper<PmsCategoryBrandRelationEntity> {

    void updateCategory(@Param("catId") Long catId,@Param("name") String name);
}
