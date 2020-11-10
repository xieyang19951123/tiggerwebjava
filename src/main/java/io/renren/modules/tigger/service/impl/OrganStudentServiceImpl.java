package io.renren.modules.tigger.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.modules.tigger.dao.OrganStudentDao;
import io.renren.modules.tigger.entity.OrganStudent;
import io.renren.modules.tigger.service.OrganStudentService;
import org.springframework.stereotype.Service;

@Service
public class OrganStudentServiceImpl extends ServiceImpl<OrganStudentDao, OrganStudent> implements OrganStudentService {
}
