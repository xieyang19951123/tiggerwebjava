package io.renren.modules.tigger.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.tigger.entity.AccessoryEntity;
import io.renren.modules.tigger.service.AccessoryService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import org.springframework.web.multipart.MultipartFile;


/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-08-16 16:52:23
 */
@RestController
@RequestMapping("tigger/accessory")
public class AccessoryController {
    @Autowired
    private AccessoryService accessoryService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("tigger:accessory:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = accessoryService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("tigger:accessory:info")
    public R info(@PathVariable("id") Integer id){
		AccessoryEntity accessory = accessoryService.getById(id);

        return R.ok().put("accessory", accessory);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("tigger:accessory:save")
    public R save(@RequestBody AccessoryEntity accessory){
		accessoryService.save(accessory);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("tigger:accessory:update")
    public R update(@RequestBody AccessoryEntity accessory){
		accessoryService.updateById(accessory);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("tigger:accessory:delete")
    public R delete(@RequestBody Integer[] ids){
		accessoryService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }


    //上传图片
    @RequestMapping(value = "uploadImage" ,method = RequestMethod.POST)
    public R uploadImage(@RequestParam("file") MultipartFile[] multipartFile){
        List<AccessoryEntity> accessoryEntities = new ArrayList<>();
        for (int i = 0; i < multipartFile.length; i++) {
            AccessoryEntity accessoryEntity = accessoryService.uploadImage(multipartFile[i]);
            if(accessoryEntity!=null){

                accessoryEntities.add(accessoryEntity);
            }
        }
        return R.ok().put("page",accessoryEntities);
    }

    @RequestMapping(value = "deletedImage",method = RequestMethod.POST)
    public R deletedImage( @RequestBody Integer id){
       // System.out.println(id);

        accessoryService.deletedImage(id);
        return  R.ok();
    }

}
