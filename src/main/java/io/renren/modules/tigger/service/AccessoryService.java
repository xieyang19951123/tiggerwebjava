package io.renren.modules.tigger.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.tigger.entity.AccessoryEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-08-16 16:52:23
 */
public interface AccessoryService extends IService<AccessoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    AccessoryEntity uploadImage(MultipartFile multipartFile);

    void deletedImage(Integer id);

}

