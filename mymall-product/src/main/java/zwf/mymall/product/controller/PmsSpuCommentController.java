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

import zwf.mymall.product.entity.PmsSpuCommentEntity;
import zwf.mymall.product.service.PmsSpuCommentService;
import zwf.mymall.common.utils.PageUtils;
import zwf.mymall.common.utils.R;


/**
 * 商品评价
 *
 * @author zwf
 * @email sunlightcs@gmail.com
 * @date 2020-12-08 11:16:02
 */
@RestController
@RequestMapping("product/spucomment")
public class PmsSpuCommentController {
    @Autowired
    private PmsSpuCommentService pmsSpuCommentService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:pmsspucomment:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = pmsSpuCommentService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("product:pmsspucomment:info")
    public R info(@PathVariable("id") Long id) {
        PmsSpuCommentEntity pmsSpuComment = pmsSpuCommentService.getById(id);

        return R.ok().put("pmsSpuComment", pmsSpuComment);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:pmsspucomment:save")
    public R save(@RequestBody PmsSpuCommentEntity pmsSpuComment) {
        pmsSpuCommentService.save(pmsSpuComment);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:pmsspucomment:update")
    public R update(@RequestBody PmsSpuCommentEntity pmsSpuComment) {
        pmsSpuCommentService.updateById(pmsSpuComment);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:pmsspucomment:delete")
    public R delete(@RequestBody Long[] ids) {
        pmsSpuCommentService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
