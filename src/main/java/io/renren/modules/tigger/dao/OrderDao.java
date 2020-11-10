package io.renren.modules.tigger.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.modules.tigger.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.tigger.entity.dto.OrderDTO;
import io.renren.modules.tigger.entity.vo.OrderVo;
import lombok.Data;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-08-16 16:52:23
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {

    IPage<OrderVo> selectOrderVo(@Param("page") Page<?> page,@Param("orderDTO") OrderDTO orderDTO);
	
}
