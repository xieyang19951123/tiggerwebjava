package io.renren.modules.tigger.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.tigger.dao.*;
import io.renren.modules.tigger.entity.AccessoryEntity;
import io.renren.modules.tigger.entity.OrganEntity;
import io.renren.modules.tigger.entity.UserVideo;
import io.renren.modules.tigger.entity.vo.OragnVo;
import io.renren.modules.tigger.entity.vo.RecordVo;
import io.renren.modules.tigger.service.OrganService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrganServiceImpl extends ServiceImpl<OrganDao, OrganEntity> implements OrganService {

    @Autowired
    private OrganDao organDao;

    @Autowired
    private RecordDao recordDao;

    @Autowired
    private PayVideoDao payVideoDao;

    @Autowired
    private AccessoryDao accessoryDao;
    @Override
    public void insertOrgan(OrganEntity organEntity) {
        organDao.insert(organEntity);
    }

    @Override
    public PageUtils getAllOrgan(Map<String, Object> params) {

       // IPage<OrganEntity> page = this.page(new Query<OrganEntity>().getPage(params), new QueryWrapper<OrganEntity>());

        IPage<OragnVo> oragnVoIPage = organDao.selectOragnVo(new Query<OragnVo>().getMyPage(params));
        List<OragnVo> collect = oragnVoIPage.getRecords().stream().map(item -> {
            if(item.getCid()!=null){
                String s = item.getCid().replaceAll("\\[", "(").replaceAll("]", ")");
                if(!s.equals("()")){
                    List<String> list = payVideoDao.selectCategroy(s);
                    item.setName(list);
                    String[] s1 = item.getCid().replaceAll(" ", "").replaceAll("\\[", "").replaceAll("]", "").split(",");

                    Integer[] integers = new Integer[s1.length];
                    for (int i = 0; i <s1.length ; i++) {
                        integers[i] = Integer.parseInt(s1[i]);
                    }
                    item.setCids(integers);
                }
            }
            return item;
        }).collect(Collectors.toList());
        oragnVoIPage.setRecords(collect);
        return new PageUtils(oragnVoIPage);
    }

    @Override
    public void deleteOrgan(List<Integer> asList) {
        organDao.deleteBatchIds(asList);
    }

    @Override
    public void editOrgan(OrganEntity organEntity) {
        organDao.updateById(organEntity);
    }

    @Override
    public List<OrganEntity> getOrgan() {
        List<OrganEntity> organEntities = organDao.selectList(null);
        organEntities.stream().map(item->{
            item.setPassword(null);
            return item;
        }).collect(Collectors.toList());
        return organEntities;
    }

    @Override
    public List<RecordVo> getOrganStudent(Integer id) {
        List<RecordVo> recordVos = recordDao.getOrganStudent(id);
        return  recordVos;
    }
}
