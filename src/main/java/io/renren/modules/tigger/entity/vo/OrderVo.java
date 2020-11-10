package io.renren.modules.tigger.entity.vo;

import io.renren.modules.tigger.entity.OrderEntity;
import lombok.Data;

@Data
public class OrderVo  extends OrderEntity {

    private String productName;

    private String userName;

    private String address;

    private String iphone;

    private String adusername;
}
