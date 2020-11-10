package io.renren.modules.tigger.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.tigger.entity.SignEntity;
import io.renren.modules.tigger.service.SignService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-08-16 16:52:24
 */
@RestController
@RequestMapping("tigger/sign")
public class SignController {
    @Autowired
    private SignService signService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("tigger:sign:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = signService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("tigger:sign:info")
    public R info(@PathVariable("id") Integer id){
		SignEntity sign = signService.getById(id);

        return R.ok().put("sign", sign);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("tigger:sign:save")
    public R save(@RequestBody SignEntity sign){
		signService.save(sign);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("tigger:sign:update")
    public R update(@RequestBody SignEntity sign){
		signService.updateById(sign);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("tigger:sign:delete")
    public R delete(@RequestBody Integer[] ids){
		signService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
