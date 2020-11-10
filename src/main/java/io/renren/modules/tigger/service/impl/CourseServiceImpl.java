package io.renren.modules.tigger.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.tigger.dao.CourseDao;
import io.renren.modules.tigger.dao.PayVideoDao;
import io.renren.modules.tigger.entity.CourseEntity;
import io.renren.modules.tigger.entity.vo.CourseVo;
import io.renren.modules.tigger.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseDao, CourseEntity> implements CourseService {

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private PayVideoDao payVideoDao;
    @Override
    public PageUtils selectCourse(Map<String, Object> params) {
        IPage<CourseVo>  page = courseDao.selectCourse(new Query<CourseVo>().getMyPage(params));
        page.getRecords().stream().map(item->{
            String s = item.getCid().replaceAll("\\[", "(").replaceAll("]", ")");
            if( !s.equals("()")){
                List<String> list = payVideoDao.selectCategroy(s);
                item.setName(list);
            }
            return item;
        }).collect(Collectors.toList());
        return new PageUtils(page);
    }
}
