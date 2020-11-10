package io.renren.modules.tigger.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("ps_organ_student")
public class OrganStudent {

    private Integer id;

    private Integer organId;

    private Integer studentId;

    private  Integer videoId;

    private Integer showStatus;

    private String  studyPlan;
}
