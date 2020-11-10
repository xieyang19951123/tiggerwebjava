package io.renren.modules.tigger.entity.vo;

import io.renren.modules.tigger.entity.AccessoryEntity;
import io.renren.modules.tigger.entity.CommentEntity;
import lombok.Data;

import java.util.List;

@Data
public class CommentVo extends CommentEntity {


    private String aSrc;

    private String coverSrc;

    private List<String> name;

    private List<String> accessoryEntities;
}
