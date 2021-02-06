package zwf.mymall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import zwf.mymall.product.entity.PmsAttrEntity;
import zwf.mymall.product.entity.PmsProductAttrValueEntity;
import zwf.mymall.product.service.PmsAttrService;
import zwf.mymall.common.utils.PageUtils;
import zwf.mymall.common.utils.R;
import zwf.mymall.product.service.PmsProductAttrValueService;
import zwf.mymall.product.vo.PmsAttrRerspVo;
import zwf.mymall.product.vo.PmsAttrVo;


/**
 * 商品属性
 *
 * @author zwf
 * @email sunlightcs@gmail.com
 * @date 2020-12-08 11:16:02
 */
@RestController
@RequestMapping("product/attr")
public class PmsAttrController {
    @Autowired
    private PmsAttrService pmsAttrService;

    @Autowired
    PmsProductAttrValueService productAttrValueService;


    @PostMapping("/update/{spuId}")
    //@RequiresPermissions("product:pmsattr:update")
    public R updateSpuAttr(@PathVariable("spuId")Long spuId,
           @RequestBody List<PmsProductAttrValueEntity> list){
        productAttrValueService.updateSpuAttr(spuId,list);

        return R.ok();
    }
    ///product/attr/base/listforspu/{spuId}
    @GetMapping("/base/listforspu/{spuId}")
    public R baseListforspu(@PathVariable("spuId") Long spuId){

        List<PmsProductAttrValueEntity> pmsProductAttrValueEntities= productAttrValueService.baseListforspu(spuId);

        return R.ok().put("data",pmsProductAttrValueEntities);
    }
    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:pmsattr:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = pmsAttrService.queryPage(params);

        return R.ok().put("page", page);
    }
    /**
     * base/list
     */
    @GetMapping("/{attrType}/list/{catelogId}")
    public R baseAttrList(@RequestParam Map<String, Object> params,
                          @PathVariable("catelogId") Long catelogId,
                          @PathVariable("attrType") String attrType){

        PageUtils page=pmsAttrService.queryBasePage(params,catelogId,attrType);


        return R.ok().put("page",page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrId}")
    //@RequiresPermissions("product:pmsattr:info")
    public R info(@PathVariable("attrId") Long attrId) {
        //PmsAttrEntity pmsAttr = pmsAttrService.getById(attrId);

        PmsAttrRerspVo rerspVo=pmsAttrService.attrInfo(attrId);
        return R.ok().put("attr", rerspVo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:pmsattr:save")
    public R save(@RequestBody PmsAttrVo pmsAttr) {
        pmsAttrService.saveAttr(pmsAttr);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:pmsattr:update")
    public R update(@RequestBody PmsAttrVo pmsAttr) {

        pmsAttrService.updateAttr(pmsAttr);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:pmsattr:delete")
    public R delete(@RequestBody Long[] attrIds) {
        pmsAttrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

}
