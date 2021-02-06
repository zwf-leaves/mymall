package zwf.mymall.ware.dao;

import zwf.mymall.ware.entity.WareInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 仓库信息
 * 
 * @author zwf
 * @email sunlightcs@gmail.com
 * @date 2020-12-08 22:03:54
 */
@Mapper
public interface WareInfoDao extends BaseMapper<WareInfoEntity> {
	
}
