package io.renren.modules.tigger.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.modules.tigger.entity.PayVideoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.tigger.entity.vo.PayVideoVo;
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
public interface PayVideoDao extends BaseMapper<PayVideoEntity> {

    IPage<PayVideoVo> selectPayVideoVo(@Param("page") Page<?> page);

    List<String>  selectCategroy(String cid);

    void updateVideoStatus(@Param("id") Integer id, @Param("status") Integer status);

    //获取文件地址
    String getFileUrl(Integer id);

    Integer getFileId(Integer id);
}
