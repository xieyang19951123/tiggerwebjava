package io.renren.modules.tigger.dao;

import io.renren.modules.tigger.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-08-16 16:52:23
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {

    void deleteCategory(List<Integer> list);
	
}
