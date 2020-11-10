package io.renren.modules.tigger.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.tigger.entity.CourseEntity;
import io.renren.modules.tigger.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("tigger/course")
public class CourseController {

    @Autowired
    private CourseService courseService;


    @RequestMapping(value = "insertCourse",method = RequestMethod.POST)
    public R insertCourse(@RequestBody CourseEntity courseEntity){
        if(courseEntity==null){
            return R.error("参数为空");
        }
        courseEntity.setCid(Arrays.asList(courseEntity.getCids()).toString());
        courseService.save(courseEntity);
        return R.ok();

    }

    @RequestMapping(value = "selectCourse",method = RequestMethod.GET)
    public R selectCourse(@RequestParam Map<String, Object> params){

        PageUtils page =  courseService.selectCourse(params);
        return R.ok().put("page",page);
    }

    @RequestMapping(value = "getCourseId/{id}")
    public R getCourseId(@PathVariable Integer id){
        CourseEntity courseEntity = courseService.getById(id);
        String[] s = courseEntity.getCid().replaceAll(" ", "").replaceAll("\\[", "").replaceAll("]", "").split(",");

        Integer[] integers = new Integer[s.length];
        for (int i = 0; i <s.length ; i++) {
            integers[i] = Integer.parseInt(s[i]);

        }
        courseEntity.setCids(integers);

        return R.ok().put("page",courseEntity);

    }

    @RequestMapping(value = "editCourse",method = RequestMethod.POST)
    public R editCourse(@RequestBody CourseEntity courseEntity){

        courseEntity.setCid(Arrays.asList(courseEntity.getCids()).toString());
        courseService.updateById(courseEntity);

        return R.ok();
    }


    @RequestMapping(value = "deletedCourse",method = RequestMethod.POST)
    public R deletedCourse(@RequestBody Integer[] ids){

        courseService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }
}
