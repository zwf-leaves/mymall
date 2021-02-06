package zwf.mymall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//import org.apache.shiro.authz.annotation.RequiresPermissions;import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import zwf.mymall.product.entity.PmsBrandEntity;
import zwf.mymall.product.entity.PmsCategoryBrandRelationEntity;
import zwf.mymall.product.service.PmsCategoryBrandRelationService;
import zwf.mymall.common.utils.PageUtils;
import zwf.mymall.common.utils.R;
import zwf.mymall.product.vo.BrandVo;


/**
 * 品牌分类关联
 *
 * @author zwf
 * @email sunlightcs@gmail.com
 * @date 2020-12-08 11:16:02
 */
@RestController
@RequestMapping("product/categorybrandrelation")
public class PmsCategoryBrandRelationController {
    @Autowired
    private PmsCategoryBrandRelationService pmsCategoryBrandRelationService;

    @GetMapping("/brands/list")
    public R relationBrandList(@RequestParam(value = "catId",required = true) Long catId){
        List<PmsBrandEntity> brandEntities=pmsCategoryBrandRelationService.getBrandsByCatId(catId);
        List<BrandVo> vos = brandEntities.stream().map((item) -> {
            BrandVo brandVo = new BrandVo();
            brandVo.setBrandId(item.getBrandId());
            brandVo.setBrandName(item.getName());
            return brandVo;
        }).collect(Collectors.toList());

        return R.ok().put("data",vos);
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:pmscategorybrandrelation:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = pmsCategoryBrandRelationService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     *关联所有品牌分类列表
     */
    @GetMapping("/catelog/list")
    //@RequiresPermissions("product:pmscategorybrandrelation:list")
    public R catelogList(@RequestParam("brandId") Long brandId) {
        List<PmsCategoryBrandRelationEntity> list =
                pmsCategoryBrandRelationService.list(new QueryWrapper<PmsCategoryBrandRelationEntity>()
                .eq("brand_id",brandId));

        return R.ok().put("data", list);
    }
    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("product:pmscategorybrandrelation:info")
    public R info(@PathVariable("id") Long id) {
        PmsCategoryBrandRelationEntity pmsCategoryBrandRelation = pmsCategoryBrandRelationService.getById(id);

        return R.ok().put("pmsCategoryBrandRelation", pmsCategoryBrandRelation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:pmscategorybrandrelation:save")
    public R save(@RequestBody PmsCategoryBrandRelationEntity pmsCategoryBrandRelation) {
        pmsCategoryBrandRelationService.saveDetial(pmsCategoryBrandRelation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:pmscategorybrandrelation:update")
    public R update(@RequestBody PmsCategoryBrandRelationEntity pmsCategoryBrandRelation) {
        pmsCategoryBrandRelationService.updateById(pmsCategoryBrandRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:pmscategorybrandrelation:delete")
    public R delete(@RequestBody Long[] ids) {
        pmsCategoryBrandRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
