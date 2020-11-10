package io.renren.modules.tigger.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("ps_let")
@Data
public class LetEntity {

    private Integer id;


    private String val;
}
