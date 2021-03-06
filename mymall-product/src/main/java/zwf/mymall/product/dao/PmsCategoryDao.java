package zwf.mymall.product.dao;

import zwf.mymall.product.entity.PmsCategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 *
 * @author zwf
 * @email sunlightcs@gmail.com
 * @date 2020-12-08 11:16:02
 */
@Mapper
public interface PmsCategoryDao extends BaseMapper<PmsCategoryEntity> {

}
