package io.renren.modules.tigger.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("ps_user_video")
@Data
public class UserVideo {

    private Integer id;

    private Integer videoId;

    private Integer uId;

    private Integer showStatus;

    private Integer oId;
}
