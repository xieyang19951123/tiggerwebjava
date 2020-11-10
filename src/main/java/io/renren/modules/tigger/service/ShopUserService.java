package io.renren.modules.tigger.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.tigger.entity.ShopUserEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-08-16 16:52:22
 */
public interface ShopUserService extends IService<ShopUserEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void removeUserById(List<Integer> asList);
}

