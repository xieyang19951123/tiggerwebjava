package io.renren.modules.tigger.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.tigger.entity.CourseEntity;

import java.util.Map;

public interface CourseService extends IService<CourseEntity> {
    PageUtils selectCourse(Map<String, Object> params);
}
