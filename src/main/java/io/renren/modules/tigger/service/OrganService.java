package io.renren.modules.tigger.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.tigger.entity.OrganEntity;
import io.renren.modules.tigger.entity.vo.RecordVo;

import java.util.List;
import java.util.Map;

public interface OrganService extends IService<OrganEntity> {

    void insertOrgan(OrganEntity organEntity);

    PageUtils getAllOrgan(Map<String, Object> params);

    void deleteOrgan(List<Integer> asList);

    void editOrgan(OrganEntity organEntity);

    List<OrganEntity> getOrgan();

    List<RecordVo> getOrganStudent(Integer id);

}
