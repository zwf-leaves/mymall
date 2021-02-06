package zwf.mymall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import zwf.mymall.common.utils.PageUtils;
import zwf.mymall.ware.entity.PurchaseEntity;
import zwf.mymall.ware.vo.MergeVo;
import zwf.mymall.ware.vo.PurchaseDoneVo;

import java.util.List;
import java.util.Map;

/**
 * 采购信息
 *
 * @author zwf
 * @email sunlightcs@gmail.com
 * @date 2020-12-08 22:03:54
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageUnreceivePurchase(Map<String, Object> params);

    void mergePurchase(MergeVo vo);

    void recevied(List<Long> ids);

    void done(PurchaseDoneVo vo);

}

