package io.renren.modules.tigger.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.R;
import io.renren.modules.tigger.entity.Rule;
import io.renren.modules.tigger.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.GET;
import javax.ws.rs.POST;

@RestController
@RequestMapping("tigger/rlue")
public class RuleController {

    @Autowired
    private RuleService ruleService;

    @GetMapping("list")
    public R list(){

        return R.ok().put("page",ruleService.list());
    }

    @PostMapping("edit")
    public R edit(@RequestBody Rule rule){
        boolean update = ruleService.updateById(rule);

        return R.ok();
    }
}
