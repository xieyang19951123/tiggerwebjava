package io.renren.modules.tigger.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.tigger.entity.PayVideoEntity;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-08-29 16:27:53
 */
public interface PayVideoService extends IService<PayVideoEntity> {

    PageUtils queryPage(Map<String, Object> params);


    String uploadVodie(MultipartFile multipartFile);

    PageUtils getVideo(Map<String,Object> params );

    String getVideoVoucher(String videoId);

    void updateVideoStatus(Integer id, Integer status);

    void deletedVideo(List<String> videoId,List<Integer> id);
}

