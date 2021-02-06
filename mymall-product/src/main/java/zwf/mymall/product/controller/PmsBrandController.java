package zwf.mymall.product.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import zwf.mymall.common.valid.AddGroup;
import zwf.mymall.common.valid.UpdataStatus;
import zwf.mymall.common.valid.UpdateGroup;
import zwf.mymall.product.entity.PmsBrandEntity;
import zwf.mymall.product.service.PmsBrandService;
import zwf.mymall.common.utils.PageUtils;
import zwf.mymall.common.utils.R;

import javax.validation.Valid;


/**
 * 品牌
 *
 * @author zwf
 * @email sunlightcs@gmail.com
 * @date 2020-12-08 11:16:02
 */
@RestController
@RequestMapping("product/brand")
public class PmsBrandController {
    @Autowired
    private PmsBrandService pmsBrandService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:pmsbrand:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = pmsBrandService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{brandId}")
    //@RequiresPermissions("product:pmsbrand:info")
    public R info(@PathVariable("brandId") Long brandId) {
        PmsBrandEntity pmsBrand = pmsBrandService.getById(brandId);

        return R.ok().put("data", pmsBrand);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:pmsbrand:save")
    public R save(@Validated(value = {AddGroup.class}) @RequestBody PmsBrandEntity pmsBrand/*BindingResult result*/) {
//        Map<String,String> map=new HashMap();
//        if(result.hasErrors()){
//            List<FieldError> fieldErrors = result.getFieldErrors();
//            fieldErrors.forEach((item)->{
//                map.put(item.getField(),item.getDefaultMessage());
//            });
//            return R.error(400,"提交de数据不合法").put("data",map);
//        }else {
//            pmsBrandService.save(pmsBrand);
//            System.out.println(pmsBrand + "oooo");
//            return R.ok();
//        }
        pmsBrandService.save(pmsBrand);

        return R.ok();
    }

    /**
     *修改状态
     */

    @RequestMapping("/update/status")
    //@RequiresPermissions("product:pmsbrand:update")
    public R updateStatus(@Validated(value = {UpdataStatus.class}) @RequestBody PmsBrandEntity pmsBrand) {
        pmsBrandService.updateById(pmsBrand);
        return R.ok();
    }
    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:pmsbrand:update")
    public R update(@Validated(value = {UpdateGroup.class}) @RequestBody PmsBrandEntity pmsBrand) {
        pmsBrandService.updateDetail(pmsBrand);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:pmsbrand:delete")
    public R delete(@RequestBody Long[] brandIds) {
        pmsBrandService.removeByIds(Arrays.asList(brandIds));

        return R.ok();
    }

}
