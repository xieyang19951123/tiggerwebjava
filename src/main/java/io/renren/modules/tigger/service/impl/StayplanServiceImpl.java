package io.renren.modules.tigger.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.modules.tigger.dao.StayplanDao;
import io.renren.modules.tigger.entity.StayplanEntity;
import io.renren.modules.tigger.service.StayplanService;
import org.springframework.stereotype.Service;

@Service
public class StayplanServiceImpl extends ServiceImpl<StayplanDao, StayplanEntity> implements StayplanService {
}
