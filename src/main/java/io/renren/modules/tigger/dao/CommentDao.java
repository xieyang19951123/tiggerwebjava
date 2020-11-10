package io.renren.modules.tigger.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.modules.tigger.entity.AccessoryEntity;
import io.renren.modules.tigger.entity.CommentEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.tigger.entity.vo.CommentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-08-16 16:52:24
 */
@Mapper
public interface CommentDao extends BaseMapper<CommentEntity> {

    IPage<CommentVo> getProduct(Page<?> page);

    List<String> getFjsrc(String id);

    void updateShowStatus(@Param("id") Integer id, @Param("status") Integer status);
}
