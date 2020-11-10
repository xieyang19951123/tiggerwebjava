package io.renren.modules.tigger.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.tigger.entity.CommentEntity;
import io.renren.modules.tigger.entity.vo.CommentVo;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-08-16 16:52:24
 */
public interface CommentService extends IService<CommentEntity> {

    PageUtils queryPage(Map<String, Object> params);


    PageUtils getProduct(Map<String, Object> params);

    void deleteProduct(List<CommentVo> products);

    void updateShowStatus(Integer id, Integer status);
}

