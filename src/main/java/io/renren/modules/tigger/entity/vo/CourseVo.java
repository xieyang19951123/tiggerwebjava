package io.renren.modules.tigger.entity.vo;

import io.renren.modules.tigger.entity.CourseEntity;
import lombok.Data;

import java.util.List;

@Data
public class CourseVo extends CourseEntity {

    private String videoName;

    private String username;

    private String coverSrc;

    private String aSrc;

    private List<String> name;
}
