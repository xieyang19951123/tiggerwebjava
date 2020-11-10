package io.renren.modules.tigger.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("ps_rule")
public class Rule {

    private  Integer id;

    private  String ruleName;

    private BigDecimal parentPoint;

    private BigDecimal point;

    @TableLogic(value = "1",delval = "0")
    private Integer showStatus;
}
