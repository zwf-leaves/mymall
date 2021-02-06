package zwf.mymall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import zwf.mymall.common.utils.PageUtils;
import zwf.mymall.member.entity.GrowthChangeHistoryEntity;

import java.util.Map;

/**
 * 成长值变化历史记录
 *
 * @author zwf
 * @email sunlightcs@gmail.com
 * @date 2020-12-08 21:53:30
 */
public interface GrowthChangeHistoryService extends IService<GrowthChangeHistoryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

