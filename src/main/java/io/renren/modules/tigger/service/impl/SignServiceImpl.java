package io.renren.modules.tigger.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.tigger.dao.SignDao;
import io.renren.modules.tigger.entity.SignEntity;
import io.renren.modules.tigger.service.SignService;


@Service("signService")
public class SignServiceImpl extends ServiceImpl<SignDao, SignEntity> implements SignService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SignEntity> page = this.page(
                new Query<SignEntity>().getPage(params),
                new QueryWrapper<SignEntity>()
        );

        return new PageUtils(page);
    }

}