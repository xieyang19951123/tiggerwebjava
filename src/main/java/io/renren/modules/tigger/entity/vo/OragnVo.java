package io.renren.modules.tigger.entity.vo;

import io.renren.modules.tigger.entity.OrganEntity;
import lombok.Data;

import java.util.List;

@Data
public class OragnVo extends OrganEntity {


    private String image;
    private List<String> name;
}
