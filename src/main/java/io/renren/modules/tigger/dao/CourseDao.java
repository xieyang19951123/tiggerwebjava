package io.renren.modules.tigger.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.modules.tigger.entity.CourseEntity;
import io.renren.modules.tigger.entity.vo.CourseVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CourseDao extends BaseMapper<CourseEntity> {
    IPage<CourseVo> selectCourse(Page<?> page);
}
