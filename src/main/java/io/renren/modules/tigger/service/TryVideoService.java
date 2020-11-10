package io.renren.modules.tigger.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.tigger.entity.TryVideoEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-08-29 16:27:53
 */
public interface TryVideoService extends IService<TryVideoEntity> {

    PageUtils queryPage(Map<String, Object> params);


    PageUtils selectTryVideoVo(Map<String,Object> params);


    void deleteTryVideo(List<String> videoIds,List<Integer> ids);

    void updateShowStatus(Integer id,Integer status);
}

