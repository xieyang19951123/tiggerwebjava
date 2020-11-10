package io.renren.modules.tigger.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.tigger.dao.ShopUserDao;
import io.renren.modules.tigger.entity.OrganEntity;
import io.renren.modules.tigger.entity.ShopUserEntity;
import io.renren.modules.tigger.service.OrganService;
import io.renren.modules.tigger.utils.MD5utils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.POST;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tigger/organ")
public class OrganController {

    @Autowired
    private OrganService organService;

    @Autowired
    private ShopUserDao shopUserDao;

    @PostMapping("insertOrgan")
    public R insertOrgan(@RequestBody OrganEntity organEntity){
        if(organEntity==null){
            return R.error("参数为空");
        }
        organEntity.setCid(Arrays.asList(organEntity.getCids()).toString());
        organEntity.setPassword(MD5utils.getMD5Str(organEntity.getPassword()));
        organEntity.setShowStatus(1);

        ShopUserEntity qw = new ShopUserEntity();
        qw.setPsUsername(organEntity.getLoginname());
        qw.setBalance(new BigDecimal(0));
        qw.setCreateTime(new Date());
        qw.setPoint(0);
        qw.setPsPassword(organEntity.getPassword());
        String recommend = dowhileRecommend();
        qw.setRecommend(recommend);
        qw.setShopVip(0);
        qw.setShowStatus(1);
        organService.insertOrgan(organEntity);
        qw.setUserType(organEntity.getId());
        shopUserDao.insert(qw);
        return R.ok();
    }
    //递归查询推荐是否已经存在
    public String dowhileRecommend(){
        String recommend = RandomStringUtils.randomAlphanumeric(6);
        ShopUserEntity shopUserEntity = new ShopUserEntity();
        shopUserEntity.setRecommend(recommend);
        QueryWrapper<ShopUserEntity> shopUserEntityQueryWrapper = new QueryWrapper<>(shopUserEntity);
        ShopUserEntity shopUserEntity1 = shopUserDao.selectOne(shopUserEntityQueryWrapper);
        if(shopUserEntity1!=null){
            dowhileRecommend();
        }
        return  recommend;
    }

    @GetMapping("getAllOrgan")
    public R getAllOrgan(@RequestParam Map<String,Object> params){
        PageUtils page = organService.getAllOrgan(params);

        return R.ok().put("page", page);
    }

    @PostMapping("deleteOrgan")
    public R deleteOrgan(@RequestBody Integer[] ids){
        if(ids == null){
            return  R.error("删除失败");
        }
        organService.deleteOrgan(Arrays.asList(ids));
        return R.ok();
    }

    @PostMapping("editOrgan")
    public R editOrgan(@RequestBody OrganEntity organEntity){
        System.out.println(organEntity);
        if(!StringUtils.isEmpty(organEntity.getPassword())){
            organEntity.setPassword(MD5utils.getMD5Str(organEntity.getPassword()));
            String loginname = organEntity.getLoginname();
            ShopUserEntity shopUserEntity = new ShopUserEntity();
            shopUserEntity.setPsUsername(loginname);
            shopUserEntity.setShowStatus(1);
            ShopUserEntity shopUserEntity1 = shopUserDao.selectOne(new QueryWrapper<>(shopUserEntity));
            if(shopUserEntity1!=null){
                shopUserEntity1.setPsPassword(organEntity.getPassword());
            }
            shopUserDao.updateById(shopUserEntity1);
        }else{

            organEntity.setPassword(null);
        }
        if(organEntity.getCids()!=null){

            organEntity.setCid(Arrays.asList(organEntity.getCids()).toString());
        }
        organEntity.setShowStatus(null);
        organService.editOrgan(organEntity);
        return  R.ok();
    }

    //获取所有的机构
    @GetMapping("getOrgan")
    public R getOrgan(){
        List<OrganEntity> organEntities = organService.getOrgan();
        return R.ok().put("page",organEntities);
    }

    //获取学生的信息
    @GetMapping("getOrganStudent")
    public R getOrganStudent(@RequestParam("id")Integer id){
        if(id == null ){
            return R.error("参数为空");
        }

        return R.ok().put("page",organService.getOrganStudent(id));
    }

}
