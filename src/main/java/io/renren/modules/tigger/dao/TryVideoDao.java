package io.renren.modules.tigger.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.renren.modules.tigger.entity.TryVideoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.tigger.entity.vo.TryVideoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-08-29 16:27:53
 */
@Mapper
public interface TryVideoDao extends BaseMapper<TryVideoEntity> {

	IPage<TryVideoVo> selectTryVideoVo(@Param("page") IPage<?> page);

	Integer selectTryVideoImageId(Integer id);

	void updateShowStatus(@Param("id") Integer id,@Param("status") Integer status);
}
