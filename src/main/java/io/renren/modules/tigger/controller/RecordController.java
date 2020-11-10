package io.renren.modules.tigger.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.tigger.entity.vo.RecordVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.tigger.entity.RecordEntity;
import io.renren.modules.tigger.service.RecordService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-08-16 16:52:23
 */
@RestController
@RequestMapping("tigger/record")
public class RecordController {
    @Autowired
    private RecordService recordService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("tigger:record:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = recordService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("tigger:record:info")
    public R info(@PathVariable("id") Integer id){
		RecordEntity record = recordService.getById(id);

        return R.ok().put("record", record);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("tigger:record:save")
    public R save(@RequestBody RecordEntity record){
		recordService.save(record);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("tigger:record:update")
    public R update(@RequestBody RecordEntity record){
		recordService.updateById(record);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("tigger:record:delete")
    public R delete(@RequestBody Integer[] ids){
		recordService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }


    @PostMapping("updateOrganMessage")
    public R updateOrganMessage(@RequestBody String str){
        if(StringUtils.isEmpty(str)){
            return R.error("参数为空");
        }
        RecordVo student = JSONObject.parseObject(JSON.parseObject(str).getString("student"), RecordVo.class);
        String message = JSON.parseObject(str).getString("message");
        if(student == null || message==null){
            return R.error("参数解析为空");
        }
        BaseMapper<RecordEntity> baseMapper = recordService.getBaseMapper();
        RecordEntity recordEntity = baseMapper.selectById(student.getId());
        recordEntity.setMessage(message);
        baseMapper.updateById(recordEntity);
        return R.ok();
    }
}
