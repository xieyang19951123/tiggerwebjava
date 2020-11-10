package io.renren.modules.tigger.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.tigger.entity.Rule;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RuleDao extends BaseMapper<Rule> {
}
