package zwf.mymall.ware.dao;

import zwf.mymall.ware.entity.PurchaseEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 采购信息
 * 
 * @author zwf
 * @email sunlightcs@gmail.com
 * @date 2020-12-08 22:03:54
 */
@Mapper
public interface PurchaseDao extends BaseMapper<PurchaseEntity> {
	
}
