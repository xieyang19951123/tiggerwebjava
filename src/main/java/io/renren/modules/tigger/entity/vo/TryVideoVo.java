package io.renren.modules.tigger.entity.vo;

import io.renren.modules.tigger.entity.TryVideoEntity;
import lombok.Data;

import java.util.List;

@Data
public class TryVideoVo  extends TryVideoEntity {

    private String aSrc ;

    private List<String> name;
}
