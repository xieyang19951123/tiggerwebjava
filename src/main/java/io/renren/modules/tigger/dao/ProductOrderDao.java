package io.renren.modules.tigger.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.tigger.entity.OrderEntity;
import io.renren.modules.tigger.entity.ProductsOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductOrderDao extends BaseMapper<ProductsOrder> {

    List<OrderEntity> selectOrders(String ids);
}
