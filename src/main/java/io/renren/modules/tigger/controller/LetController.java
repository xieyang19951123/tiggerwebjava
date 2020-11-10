package io.renren.modules.tigger.controller;

import io.renren.common.utils.R;
import io.renren.modules.tigger.dao.LetDao;
import io.renren.modules.tigger.entity.LetEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("shop/let")
@RestController
public class LetController {



    @Autowired
    private LetDao letDao;

    @RequestMapping(value = "inval" ,method = RequestMethod.POST)
    public  R inval( @RequestBody String inval){
        inval = inval.replaceAll("\"","");
        LetEntity letEntity = new LetEntity();
        letEntity.setId(1);
        letEntity.setVal(inval);
        letDao.updateById(letEntity);
        return R.ok();
    }
    @RequestMapping("select")
    public R  selectLet(){
        LetEntity letEntity = letDao.selectById(1);
        return R.ok().put("page",letEntity);
    }
}
