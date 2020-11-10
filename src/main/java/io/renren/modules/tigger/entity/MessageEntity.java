package io.renren.modules.tigger.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("ps_message")
public class MessageEntity {

    private Integer id;

    private String message;

    private Integer pid;
}
