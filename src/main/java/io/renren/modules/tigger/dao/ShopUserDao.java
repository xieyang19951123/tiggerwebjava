package io.renren.modules.tigger.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.modules.tigger.entity.ShopUserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.tigger.entity.vo.ShopUserVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-08-16 16:52:22
 */
@Mapper
public interface ShopUserDao extends BaseMapper<ShopUserEntity> {

    public IPage<ShopUserVo> selectShopUserVo(Page<?> page);
}
