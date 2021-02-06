package zwf.mymall.product.controller;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//import org.apache.shiro.authz.annotation.RequiresPermissions;import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import zwf.mymall.product.entity.PmsAttrAttrgroupRelationEntity;
import zwf.mymall.product.entity.PmsAttrEntity;
import zwf.mymall.product.entity.PmsAttrGroupEntity;
import zwf.mymall.product.service.PmsAttrAttrgroupRelationService;
import zwf.mymall.product.service.PmsAttrGroupService;
import zwf.mymall.common.utils.PageUtils;
import zwf.mymall.common.utils.R;
import zwf.mymall.product.service.PmsAttrService;
import zwf.mymall.product.service.PmsCategoryService;
import zwf.mymall.product.vo.AttrGroupRelationVo;
import zwf.mymall.product.vo.AttrGroupWithAttrVo;


/**
 * 属性分组
 *
 * @author zwf
 * @email sunlightcs@gmail.com
 * @date 2020-12-08 11:16:02
 */
@RestController
@RequestMapping("product/attrgroup")
public class PmsAttrGroupController {
    @Autowired
    private PmsAttrGroupService pmsAttrGroupService;

    @Autowired
    private PmsCategoryService pmsCategoryService;
    @Autowired
    PmsAttrService pmsAttrService;
    @Autowired
    PmsAttrAttrgroupRelationService relationService;

    /**
     * 获取分组并带上其分组的属性
     * @param catelogId
     * @return
     */
    @GetMapping("/{catelogId}/withattr")
    public R getAttrGroupWithAttr(@PathVariable("catelogId") Long catelogId){

        List<AttrGroupWithAttrVo> attrGroupWithAttrVo=pmsAttrGroupService.getAttrGroupWithAttrByCateligId(catelogId);
        return R.ok().put("data",attrGroupWithAttrVo);
    }
    /**
     * 属性分组和属性关联保存
     * @param relationVo
     * @return
     */
    @PostMapping("/attr/relation")
    public R addRelation(@RequestBody List<AttrGroupRelationVo> relationVo){
        relationService.saveBatch(relationVo);
        return R.ok();
    }

    /**
     * 关联添加时获取没有关联的属性你
     * @param attrgroupId
     * @param params
     * @return
     */
    @GetMapping("/{attrgroupId}/noattr/relation")
    public R attrNoRelation(@PathVariable("attrgroupId") Long attrgroupId ,
                            @RequestParam Map<String, Object> params ){
        PageUtils page=pmsAttrService.getNoRelationAttr(params,attrgroupId);

        return R.ok().put("page",page);
    }

    /**
     * 关联删除
     * @param relationVo
     * @return
     */
    @PostMapping("/attr/relation/delete")
    public R deleteRelation(@RequestBody AttrGroupRelationVo[] relationVo){
        pmsAttrService.deleteRelation(relationVo);
        return R.ok();
    }


    /**
     * 回去分组所关联的属性
     * @param attrgroupId
     * @return
     */
    @GetMapping("/{attrgroupId}/attr/relation")
    public R attrRelation(@PathVariable("attrgroupId")Long attrgroupId ){
        List<PmsAttrEntity> pmsAttrEntities= pmsAttrService.getRelationAttr(attrgroupId);

        return R.ok().put("data",pmsAttrEntities);
    }

    /**
     * 列表
     */
    @RequestMapping("/list/{catelogId}")
    //@RequiresPermissions("product:pmsattrgroup:list")
    public R list(@RequestParam Map<String, Object> params ,
                  @PathVariable("catelogId") Long catelogId) {
//        PageUtils page = pmsAttrGroupService.queryPage(params);

        PageUtils page = pmsAttrGroupService.queryPage(params,catelogId);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    //@RequiresPermissions("product:pmsattrgroup:info")
    public R info(@PathVariable("attrGroupId") Long attrGroupId) {
        PmsAttrGroupEntity pmsAttrGroup = pmsAttrGroupService.getById(attrGroupId);
        Long catelogId = pmsAttrGroup.getCatelogId();
        Long [] catelogPath=pmsCategoryService.findCatelogPath(catelogId);

        pmsAttrGroup.setCatelogPath(catelogPath);
        return R.ok().put("attrGroup", pmsAttrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:pmsattrgroup:save")
    public R save(@RequestBody PmsAttrGroupEntity pmsAttrGroup) {
        pmsAttrGroupService.save(pmsAttrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:pmsattrgroup:update")
    public R update(@RequestBody PmsAttrGroupEntity pmsAttrGroup) {
        pmsAttrGroupService.updateById(pmsAttrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:pmsattrgroup:delete")
    public R delete(@RequestBody Long[] attrGroupIds) {
        pmsAttrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

}
