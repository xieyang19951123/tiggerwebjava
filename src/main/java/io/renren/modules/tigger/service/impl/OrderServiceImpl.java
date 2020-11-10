package io.renren.modules.tigger.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.modules.tigger.dao.ProductOrderDao;
import io.renren.modules.tigger.entity.ProductsOrder;
import io.renren.modules.tigger.entity.dto.OrderDTO;
import io.renren.modules.tigger.entity.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.tigger.dao.OrderDao;
import io.renren.modules.tigger.entity.OrderEntity;
import io.renren.modules.tigger.service.OrderService;


@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductOrderDao productOrderDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
//        IPage<OrderEntity> page = this.page(
//                new Query<OrderEntity>().getPage(params),
//                new QueryWrapper<OrderEntity>()
//        );
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setKey((String) params.get("key"));
        if(params.get("payStatus") !=null && !params.get("payStatus").equals("")){
            orderDTO.setPayStatus(Integer.valueOf((String) params.get("payStatus")));
        }
        if(params.get("payType") !=null && !params.get("payType").equals("")){
            orderDTO.setPayType(Integer.valueOf((String)params.get("payType")));
        }
        IPage<OrderVo> page = orderDao.selectOrderVo(new Query<OrderVo>().getMyPage(params),
                orderDTO);

        return new PageUtils(page);
    }

}