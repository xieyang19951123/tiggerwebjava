package io.renren.modules.tigger.entity.vo;

import io.renren.modules.tigger.entity.PayVideoEntity;
import lombok.Data;

import java.util.List;

@Data
public class PayVideoVo extends PayVideoEntity {

    private String aSrc;

    private List<String> name;

    private  String organname;
}
