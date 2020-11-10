package io.renren.modules.tigger.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.R;
import io.renren.modules.tigger.entity.MessageEntity;
import io.renren.modules.tigger.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("tigger/message")
@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;


    @RequestMapping("get")
    public  R get(Integer pid){
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setPid(pid);
        MessageEntity one = messageService.getOne(new QueryWrapper<>(messageEntity));

        return R.ok().put("page",one);
    }

    @RequestMapping("save")
    public R save(@RequestBody MessageEntity messageEntity){
        try{
            messageService.save(messageEntity);
            if(messageEntity.getId() == null){
                return R.error("新增失败");
            }
            return R.ok().put("page",messageEntity);
        }catch (Exception e){
            return R.error("数据过大");
        }

    }

    @RequestMapping("edit")
    public R edit(@RequestBody MessageEntity messageEntity){
        try{
            messageService.updateById(messageEntity);
            return R.ok().put("page",messageEntity);
        }catch (Exception e){
            return R.error("数据过大");
        }

    }
}
