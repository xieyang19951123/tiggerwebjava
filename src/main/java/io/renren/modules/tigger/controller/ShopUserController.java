package io.renren.modules.tigger.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.tigger.utils.MD5utils;
import io.renren.modules.tigger.utils.TransOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.tigger.entity.ShopUserEntity;
import io.renren.modules.tigger.service.ShopUserService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-08-16 16:52:22
 */
@RestController
@RequestMapping("tigger/shopuser")
public class ShopUserController {
    @Autowired
    private ShopUserService shopUserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("tigger:shopuser:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = shopUserService.queryPage(params);

        return R.ok().put("page", page);
}


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("tigger:shopuser:info")
    public R info(@PathVariable("id") Integer id){
		ShopUserEntity shopUser = shopUserService.getById(id);

        return R.ok().put("shopUser", shopUser);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("tigger:shopuser:save")
    public R save(@RequestBody ShopUserEntity shopUser){
		shopUserService.save(shopUser);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("tigger:shopuser:update")
    public R update(@RequestBody ShopUserEntity shopUser){
		shopUserService.updateById(shopUser);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("tigger:shopuser:delete")
    public R delete(@RequestBody Integer[] ids){
		//shopUserService.removeByIds(Arrays.asList(ids));
        shopUserService.removeUserById(Arrays.asList(ids));
        return R.ok();
    }


    @RequestMapping(value = "switchChange" ,method = RequestMethod.POST)
    public R switchChange(
           @RequestBody Map<String, Object> map
    ){
        System.out.println(map);
        String recommend = TransOrder.getRecommend();
        String md5Str = MD5utils.getMD5Str(recommend);
        ShopUserEntity shopUserEntity = new ShopUserEntity();
        shopUserEntity.setId((Integer) map.get("id"));
        shopUserEntity.setUserRock((Integer) map.get("rock"));
        if((Integer) map.get("rock") == 1){

            shopUserEntity.setPsPassword(md5Str);

        }
        BaseMapper<ShopUserEntity> baseMapper = shopUserService.getBaseMapper();
        baseMapper.updateById(shopUserEntity);
        return R.ok().put("password",recommend);
    }

}
