package zwf.mymall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import zwf.mymall.common.utils.PageUtils;
import zwf.mymall.member.entity.MemberStatisticsInfoEntity;

import java.util.Map;

/**
 * 会员统计信息
 *
 * @author zwf
 * @email sunlightcs@gmail.com
 * @date 2020-12-08 21:53:29
 */
public interface MemberStatisticsInfoService extends IService<MemberStatisticsInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

