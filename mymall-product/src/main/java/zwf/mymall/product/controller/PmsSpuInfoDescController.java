package zwf.mymall.product.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import zwf.mymall.product.entity.PmsSpuInfoDescEntity;
import zwf.mymall.product.service.PmsSpuInfoDescService;
import zwf.mymall.common.utils.PageUtils;
import zwf.mymall.common.utils.R;


/**
 * spu信息介绍
 *
 * @author zwf
 * @email sunlightcs@gmail.com
 * @date 2020-12-08 11:16:02
 */
@RestController
@RequestMapping("product/spuinfodesc")
public class PmsSpuInfoDescController {
    @Autowired
    private PmsSpuInfoDescService pmsSpuInfoDescService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:pmsspuinfodesc:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = pmsSpuInfoDescService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{spuId}")
    //@RequiresPermissions("product:pmsspuinfodesc:info")
    public R info(@PathVariable("spuId") Long spuId) {
        PmsSpuInfoDescEntity pmsSpuInfoDesc = pmsSpuInfoDescService.getById(spuId);

        return R.ok().put("pmsSpuInfoDesc", pmsSpuInfoDesc);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:pmsspuinfodesc:save")
    public R save(@RequestBody PmsSpuInfoDescEntity pmsSpuInfoDesc) {
        pmsSpuInfoDescService.save(pmsSpuInfoDesc);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:pmsspuinfodesc:update")
    public R update(@RequestBody PmsSpuInfoDescEntity pmsSpuInfoDesc) {
        pmsSpuInfoDescService.updateById(pmsSpuInfoDesc);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:pmsspuinfodesc:delete")
    public R delete(@RequestBody Long[] spuIds) {
        pmsSpuInfoDescService.removeByIds(Arrays.asList(spuIds));

        return R.ok();
    }

}
