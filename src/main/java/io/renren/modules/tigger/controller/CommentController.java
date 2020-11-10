package io.renren.modules.tigger.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.fdfs.ThumbImageConfig;
import io.renren.common.utils.FastDFSClient;
import io.renren.modules.tigger.dao.AccessoryDao;
import io.renren.modules.tigger.dao.CommentDao;
import io.renren.modules.tigger.entity.AccessoryEntity;
import io.renren.modules.tigger.entity.vo.CommentVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.tigger.entity.CommentEntity;
import io.renren.modules.tigger.service.CommentService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import org.springframework.web.multipart.MultipartFile;


/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-08-16 16:52:24
 */
@RestController
@RequestMapping("tigger/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private FastDFSClient fastDFSClient;

    @Autowired
    private ThumbImageConfig thumbImageConfig;

    @Autowired
    private AccessoryDao accessoryDao;

    @Autowired
    private CommentDao commentDao;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("tigger:comment:list")
    public R list(@RequestParam Map<String, Object> params){
        //PageUtils page = commentService.queryPage(params);
        PageUtils page = commentService.getProduct(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("tigger:comment:info")
    public R info(@PathVariable("id") Integer id){
		CommentEntity comment = commentService.getById(id);

        return R.ok().put("comment", comment);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("tigger:comment:save")
    public R save(@RequestBody CommentEntity comment){
		commentService.save(comment);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("tigger:comment:update")
    public R update(@RequestBody CommentEntity comment){
		commentService.updateById(comment);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("tigger:comment:delete")
    public R delete(@RequestBody Integer[] ids){
		commentService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }


    @RequestMapping(value = "insertProduct",method = RequestMethod.POST)
    public R insertProduct(@RequestParam("file")MultipartFile[] file,
                           @RequestParam("cid")Integer[] cid,
                           @RequestParam("point")BigDecimal point,
                           @RequestParam("price")BigDecimal price,
                           @RequestParam("comName")String comName,
                           @RequestParam("cover")MultipartFile[] cover,
                           @RequestParam("homeShow")Integer homeShow,
                           @RequestParam("postage")BigDecimal postage,
                           @RequestParam("id")Integer id,
                           @RequestParam("sharePoint")Integer sharePoint,
                           @RequestParam("byPoint")Integer byPoint,
                           @RequestParam("byPointFather") Integer byPointFather,
                           @RequestParam("address")String address


                           ){

        Integer coverId  = null;
        //上传封面
        if(cover.length >0){
            try {
                StorePath storePath = fastDFSClient.uploadFile3(cover[0]);
                String thumbImagePath = thumbImageConfig.getThumbImagePath(storePath.getPath());
                String thumbnail = "http://106.75.162.179:8888/"+storePath.getGroup()+"/"+thumbImagePath;//获取缩略图的地址
                String coverPath =  "http://106.75.162.179:8888/"+storePath.getGroup()+"/"+ storePath.getPath();//图片原始地址
                AccessoryEntity accessoryEntity = new AccessoryEntity();
                accessoryEntity.setFileName(cover[0].getOriginalFilename());
                accessoryEntity.setShowStatus(1);
                accessoryEntity.setAStatus(1);
                accessoryEntity.setASrc(thumbnail);
                accessoryEntity.setCreateTime(new Date());
                accessoryEntity.setCoverSrc(coverPath);
                accessoryDao.insert(accessoryEntity);
                coverId = accessoryEntity.getId();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        List<Integer> imageIds = new ArrayList<>();
        for (int i=0; i<file.length;i++){
            try {
                StorePath storePath = fastDFSClient.uploadFile3(file[i]);
                String thumbImagePath = thumbImageConfig.getThumbImagePath(storePath.getPath());//获取缩略图的地址
                String thumbImageUrl = "http://106.75.162.179:8888/"+storePath.getGroup()+"/"+thumbImagePath;
                String coverPath = "http://106.75.162.179:8888/"+storePath.getGroup()+"/"+storePath.getPath();
                AccessoryEntity accessoryEntity = new AccessoryEntity();
                accessoryEntity.setFileName(file[i].getOriginalFilename());
                accessoryEntity.setShowStatus(1);
                accessoryEntity.setAStatus(1);
                accessoryEntity.setASrc(thumbImageUrl);
                accessoryEntity.setCreateTime(new Date());
                accessoryEntity.setCoverSrc(coverPath);
                accessoryDao.insert(accessoryEntity);
                imageIds.add(accessoryEntity.getId());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        CommentEntity commentEntity = new CommentEntity();
        if(id == 0){
            if( imageIds .size()<1|| coverId==null){
                return R.error("上传失败");
            }

        }
        if(imageIds .size()>=1){
            commentEntity.setFjId(imageIds.toString());
        }
        commentEntity.setCid(Arrays.asList(cid).toString());
        commentEntity.setComName(comName);
        commentEntity.setPoint(point);
        commentEntity.setPrice(price);
        commentEntity.setCover(coverId);
        commentEntity.setHomeShow(homeShow);
        commentEntity.setShowStatus(1);
        commentEntity.setPostage(postage);
        commentEntity.setSharePoint(sharePoint);
        commentEntity.setByPoint(byPoint);
        commentEntity.setByPointFather(byPointFather);
        commentEntity.setAddress(address);
        if(id!=0){
            commentEntity.setId(id);
            commentDao.updateById(commentEntity);
            return R.ok();
        }
        commentDao.insert(commentEntity);
        return R.ok();
    }

    @RequestMapping("product")
    public R tableSelectList(@RequestBody String product){
        if(StringUtils.isEmpty(product)){
            return R.error("删除失败");
        }
        List<CommentVo> products = JSON.parseArray(JSON.parseObject(product).getString("product"), CommentVo.class);
        commentService.deleteProduct(products);
        return R.ok();
    }

    @RequestMapping("updateShowStatus")
    public R updateShowStatus(@RequestBody String str

                              ){
        if(StringUtils.isEmpty(str)){
            return R.error("更新失败");
        }
        Integer id = JSON.parseObject(str).getInteger("id");
        Integer status = JSON.parseObject(str).getInteger("status");
        commentService.updateShowStatus(id,status);
        return  R.ok();
    }

}
