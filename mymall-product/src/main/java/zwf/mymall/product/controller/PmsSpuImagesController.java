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

import zwf.mymall.product.entity.PmsSpuImagesEntity;
import zwf.mymall.product.service.PmsSpuImagesService;
import zwf.mymall.common.utils.PageUtils;
import zwf.mymall.common.utils.R;


/**
 * spu图片
 *
 * @author zwf
 * @email sunlightcs@gmail.com
 * @date 2020-12-08 11:16:02
 */
@RestController
@RequestMapping("product/spuimages")
public class PmsSpuImagesController {
    @Autowired
    private PmsSpuImagesService pmsSpuImagesService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:pmsspuimages:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = pmsSpuImagesService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("product:pmsspuimages:info")
    public R info(@PathVariable("id") Long id) {
        PmsSpuImagesEntity pmsSpuImages = pmsSpuImagesService.getById(id);

        return R.ok().put("pmsSpuImages", pmsSpuImages);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:pmsspuimages:save")
    public R save(@RequestBody PmsSpuImagesEntity pmsSpuImages) {
        pmsSpuImagesService.save(pmsSpuImages);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:pmsspuimages:update")
    public R update(@RequestBody PmsSpuImagesEntity pmsSpuImages) {
        pmsSpuImagesService.updateById(pmsSpuImages);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:pmsspuimages:delete")
    public R delete(@RequestBody Long[] ids) {
        pmsSpuImagesService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
