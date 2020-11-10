package io.renren.modules.tigger.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.tigger.entity.CategoryEntity;
import io.renren.modules.tigger.service.CategoryService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-08-16 16:52:23
 */
@RestController
@RequestMapping("tigger/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("tigger:category:list")
    public R  list(@RequestParam("type")Integer type){
        //PageUtils page = categoryService.queryPage(params);
        List<CategoryEntity> lists = categoryService.selectCategory(type);
        return R.ok().put("page", lists);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("tigger:category:info")
    public R info(@PathVariable("id") Integer id){
		CategoryEntity category = categoryService.getById(id);

        return R.ok().put("category", category);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("tigger:category:save")
    public R save(@RequestBody CategoryEntity category){
		categoryService.save(category);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("tigger:category:update")
    public R update(@RequestBody CategoryEntity category){
		categoryService.updateById(category);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("tigger:category:delete")
    public R delete(@RequestBody Integer[] ids){
		//categoryService.removeByIds(Arrays.asList(ids));
        categoryService.deletedCategoryByid(Arrays.asList(ids));
        System.out.println(Arrays.asList(ids));
        return R.ok();
    }


    //查询所有的分类
    @RequestMapping("/queryList")
    public R queryList(){
        return  categoryService.queryList();
    }
}
