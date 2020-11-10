package io.renren.modules.tigger.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.fdfs.ThumbImageConfig;
import io.renren.common.utils.Constant;
import io.renren.common.utils.FastDFSClient;
import io.renren.modules.tigger.dao.AccessoryDao;
import io.renren.modules.tigger.dao.PayVideoDao;
import io.renren.modules.tigger.entity.AccessoryEntity;
import io.renren.modules.tigger.utils.UploadVideoUtils;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.tigger.entity.PayVideoEntity;
import io.renren.modules.tigger.service.PayVideoService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-08-29 16:27:53
 */
@Slf4j
@RestController
@RequestMapping("tigger/payvideo")
public class PayVideoController {
    @Autowired
    private PayVideoService payVideoService;

    @Autowired
    private UploadVideoUtils uploadVideoUtils;

    @Autowired
    private FastDFSClient fastDFSClient;


    @Autowired
    private AccessoryDao accessoryDao;

    @Autowired
    private PayVideoDao payVideoDao;

    @Autowired
    private ThumbImageConfig thumbImageConfig;
    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("tigger:payvideo:list")
    public R list(@RequestParam Map<String, Object> params){
        //PageUtils page = payVideoService.queryPage(params);
        PageUtils page = payVideoService.getVideo(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("tigger:payvideo:info")
    public R info(@PathVariable("id") Integer id){
		PayVideoEntity payVideo = payVideoService.getById(id);

        return R.ok().put("payVideo", payVideo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("tigger:payvideo:save")
    public R save(@RequestBody PayVideoEntity payVideo){
		payVideoService.save(payVideo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("tigger:payvideo:update")
    public R update(@RequestBody PayVideoEntity payVideo){
		payVideoService.updateById(payVideo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("tigger:payvideo:delete")
    public R delete(@RequestBody Integer[] ids){
		payVideoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    //上传文件
    @RequestMapping(value = "/uploadVodie" ,method = RequestMethod.POST)
    public R uploadVodie(@RequestParam("files") MultipartFile[] files,
                     @RequestParam("introduce") String introduce,
                     @RequestParam("price")BigDecimal price,
                     @RequestParam("cid")Integer[] cid,
                     @RequestParam("homeShow")Integer homeShow,
                     @RequestParam("point")BigDecimal point,
                     @RequestParam("oid") Integer oid,
                     @RequestParam("id")Integer id,
                     @RequestParam("sharePoint")Integer sharePoint,
                     @RequestParam("byPoint")Integer byPoint,
                     @RequestParam("byPointFather")Integer byPointFather,

                     HttpServletRequest request
                     ){
        String video = null;
        Integer  imageId = null;

        //System.out.println(introduce+"=====================");
        for (int i =0 ;i<files.length;i++){
            String name = files[i].getOriginalFilename();//获取文件名
            if(name.contains("png")||name.contains("jpg")){//若是图片文件
                try {
                    StorePath storePath = fastDFSClient.uploadFile3(files[i]);
                    String thumbImagePath = thumbImageConfig.getThumbImagePath(storePath.getPath());
                    String thumbnail = "http://106.75.162.179:8888/"+storePath.getGroup()+"/"+thumbImagePath;//获取缩略图的地址
                    String coverPath =  "http://106.75.162.179:8888/"+storePath.getGroup()+"/"+ storePath.getPath();//图片原始地址
                    AccessoryEntity accessoryEntity = new AccessoryEntity();
                    accessoryEntity.setFileName(files[i].getOriginalFilename());
                    accessoryEntity.setShowStatus(1);
                    accessoryEntity.setAStatus(1);
                    accessoryEntity.setASrc(thumbnail);
                    accessoryEntity.setCreateTime(new Date());
                    accessoryEntity.setCoverSrc(coverPath);
                    accessoryDao.insert(accessoryEntity);
                    imageId = accessoryEntity.getId();
                    //log.info("图片上传后的地址"+imageUrl);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(name.contains("mp4")){//视频文件
                try {
                    video  = payVideoService.uploadVodie( files[i]);
                    log.info(video+"视频上传后的地址");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }
        System.out.println("===============");
        System.out.println(id);
        if(id == 0){
            if(imageId == null || video ==null || video.equals("")|| imageId.equals("")){
                return R.error("上传失败");
            }
        }
        PayVideoEntity payVideoEntity = new PayVideoEntity();
        payVideoEntity.setPrice(price);
        payVideoEntity.setIntroduce(introduce);
        payVideoEntity.setHomeShow(homeShow);
        payVideoEntity.setPoint(point);
        payVideoEntity.setShowStatus(1);
        payVideoEntity.setFjId(imageId);
        payVideoEntity.setOid(oid);
        payVideoEntity.setCid(Arrays.asList(cid).toString());
        payVideoEntity.setVideoId(video);
        payVideoEntity.setCareatTime(new Date());
        payVideoEntity.setSharePoint(sharePoint);
        payVideoEntity.setByPoint(byPoint);
        payVideoEntity.setByPointFather(byPointFather);
        if(id != 0){
            payVideoEntity.setId(id);
            payVideoDao.updateById(payVideoEntity);
            return R.ok();
        }
        payVideoDao.insert(payVideoEntity);
        return R.ok();
    }

    //获取视频播放凭证
    @RequestMapping("/getVideoVoucher")
    public R getVideoVoucher(@RequestParam("videoId")String videoId){
        if(videoId.equals("")){
            return  R.error("参数为空");
        }
        String videoVoucher = payVideoService.getVideoVoucher(videoId);
        if(videoVoucher.equals("")){
            return R.error("获取视频播放凭证");
        }
        return  R.ok(videoVoucher);
    }

    //改变是否显示主页
    @RequestMapping("/updateShowStatus")
    public R updateVideoStatus(@RequestParam("id")Integer id,
                               @RequestParam("status") Integer status
                               ){

        payVideoService.updateVideoStatus(id,status);
        return R.ok();
    }


    @RequestMapping("deletedVideo")
    public R deletedVideo(@RequestBody String str
                          ){

        if(str.equals("")){
            return R.error("删除失败");
        }
        List<Integer> ids = JSON.parseArray(JSON.parseObject(str).getString("ids"),Integer.class) ;
        List<String> videoIds = JSON.parseArray(JSON.parseObject(str).getString("videoIds"), String.class);
        payVideoService.deletedVideo(videoIds,ids);
        return R.ok();
    }



}
