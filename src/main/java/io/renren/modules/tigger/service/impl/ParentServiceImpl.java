package io.renren.modules.tigger.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.modules.tigger.dao.ParentDao;
import io.renren.modules.tigger.entity.ParentEntity;
import io.renren.modules.tigger.service.ParentService;
import org.springframework.stereotype.Service;

@Service
public  class ParentServiceImpl extends ServiceImpl<ParentDao, ParentEntity> implements ParentService {
}
