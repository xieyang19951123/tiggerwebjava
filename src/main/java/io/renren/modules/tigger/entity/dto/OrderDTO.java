package io.renren.modules.tigger.entity.dto;

import lombok.Data;

@Data
public class OrderDTO extends Mypage{



    private String key;

    private Integer payType;

    private Integer payStatus;

    private String ids;
}
