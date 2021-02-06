package zwf.mymall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//import org.apache.shiro.authz.annotation.RequiresPermissions;import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import zwf.mymall.product.entity.PmsCategoryEntity;
import zwf.mymall.product.service.PmsCategoryService;
import zwf.mymall.common.utils.R;


/**
 * 商品三级分类
 *
 * @author zwf
 * @email sunlightcs@gmail.com
 * @date 2020-12-08 11:16:02
 */
@RestController
@RequestMapping("product/category")
public class PmsCategoryController {
    @Autowired
    private PmsCategoryService pmsCategoryService;

    /**
     * 所有分类及子分类，以树型列结构封装起来
     */
    @RequestMapping("/list/tree")
    //@RequiresPermissions("product:pmscategory:list")
    public R list() {
       List<PmsCategoryEntity> entities= pmsCategoryService.listWithTree();
        List<PmsCategoryEntity> levelMenus = entities.stream().filter(pmsCategoryEntity ->
                pmsCategoryEntity.getParentCid() == 0
        ).map((menu)->{
            menu.setChildren(getChildrens(menu,entities));
            return menu;
        }).sorted((menu1,menu2)->{
            return (menu1.getSort()==null?0:menu1.getSort())-(menu2.getSort()==null?0:menu2.getSort());
        }).collect(Collectors.toList());



        return R.ok().put("data", levelMenus);
    }

    //递归所有菜单的子菜单
    private List<PmsCategoryEntity> getChildrens(PmsCategoryEntity root, List<PmsCategoryEntity> all){
        List<PmsCategoryEntity> children = all.stream().filter(pmsCategoryEntity -> {
            return pmsCategoryEntity.getParentCid() == root.getCatId();
        }).map(pmsCategoryEntity -> {
            //找子菜单
            pmsCategoryEntity.setChildren(getChildrens(pmsCategoryEntity,all));
            return pmsCategoryEntity;
        }).sorted((menu1,menu2)->{
            return (menu1.getSort()==null?0:menu1.getSort())-(menu2.getSort()==null?0:menu2.getSort());
        }).collect(Collectors.toList());
        return children;

    }

    /**
     * 信息
     */
    @RequestMapping("/info/{catId}")
    //@RequiresPermissions("product:pmscategory:info")
    public R info(@PathVariable("catId") Long catId) {
        PmsCategoryEntity pmsCategory = pmsCategoryService.getById(catId);

        return R.ok().put("data", pmsCategory);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:pmscategory:save")
    public R save(@RequestBody PmsCategoryEntity pmsCategory) {
        pmsCategoryService.save(pmsCategory);

        return R.ok();
    }

    /**
     * 批量修改
     */
    @RequestMapping("/update/sort")
    //@RequiresPermissions("product:pmscategory:update")
    public R updateSort(@RequestBody PmsCategoryEntity[] pmsCategory) {
        pmsCategoryService.updateBatchById(Arrays.asList(pmsCategory));

        return R.ok();
    }
    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:pmscategory:update")
    public R update(@RequestBody PmsCategoryEntity pmsCategory) {
        pmsCategoryService.updateCascade(pmsCategory);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:pmscategory:delete")
    public R delete(@RequestBody Long[] catIds) {
        pmsCategoryService.removeByIds(Arrays.asList(catIds));

        pmsCategoryService.removeMenusById(Arrays.asList(catIds));
        return R.ok();
    }

}
