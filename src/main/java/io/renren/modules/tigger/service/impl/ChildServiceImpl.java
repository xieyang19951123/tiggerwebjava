package io.renren.modules.tigger.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.modules.tigger.dao.ChildDao;
import io.renren.modules.tigger.entity.Child;
import io.renren.modules.tigger.service.ChildService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ChildServiceImpl extends ServiceImpl<ChildDao, Child> implements ChildService {

    @Autowired
    private  ChildDao childDao;

}
