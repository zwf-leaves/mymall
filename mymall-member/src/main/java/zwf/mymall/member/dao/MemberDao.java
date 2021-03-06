package zwf.mymall.member.dao;

import zwf.mymall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author zwf
 * @email sunlightcs@gmail.com
 * @date 2020-12-08 21:53:30
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
