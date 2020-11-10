package io.renren.modules.tigger.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.modules.tigger.entity.OrganEntity;
import io.renren.modules.tigger.entity.vo.OragnVo;
import io.renren.modules.tigger.entity.vo.ShopUserVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrganDao extends BaseMapper<OrganEntity> {

    IPage<OragnVo> selectOragnVo(Page<?> page);
}
