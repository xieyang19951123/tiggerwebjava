package io.renren.modules.tigger.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.modules.tigger.entity.vo.ShopUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.tigger.dao.ShopUserDao;
import io.renren.modules.tigger.entity.ShopUserEntity;
import io.renren.modules.tigger.service.ShopUserService;


@Service("shopUserService")
public class ShopUserServiceImpl extends ServiceImpl<ShopUserDao, ShopUserEntity> implements ShopUserService {

    @Autowired
    private ShopUserDao shopUserDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        //;
        //Page<ShopUserVo> ShopUserVo = new Page<>(1,10);
        IPage<ShopUserVo> page = shopUserDao.selectShopUserVo(new Query<>().getMyPage(params));

        return new PageUtils(page);
    }

    @Override
    public void removeUserById(List<Integer> asList) {
        shopUserDao.deleteBatchIds(asList);
    }

}