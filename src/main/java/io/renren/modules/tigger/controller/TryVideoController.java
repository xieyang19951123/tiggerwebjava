package io.renren.modules.tigger.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.alibaba.fastjson.JSON;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.fdfs.ThumbImageConfig;
import io.renren.common.utils.FastDFSClient;
import io.renren.modules.tigger.dao.AccessoryDao;
import io.renren.modules.tigger.dao.TryVideoDao;
import io.renren.modules.tigger.entity.AccessoryEntity;
import io.renren.modules.tigger.entity.PayVideoEntity;
import io.renren.modules.tigger.service.PayVideoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.renren.modules.tigger.entity.TryVideoEntity;
import io.renren.modules.tigger.service.TryVideoService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2020-08-29 16:27:53
 */
@Slf4j
@RestController
@RequestMapping("tigger/tryvideo")
public class TryVideoController {
    @Autowired
    private TryVideoService tryVideoService;

    @Autowired
    private FastDFSClient fastDFSClient;

    @Autowired
    private AccessoryDao accessoryDao;

    @Autowired
    private PayVideoService payVideoService;

    @Autowired
    private TryVideoDao tryVideoDao;

    @Autowired
    private ThumbImageConfig thumbImageConfig;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("tigger:tryvideo:list")
    public R list(@RequestParam Map<String, Object> params){
        //PageUtils page = tryVideoService.queryPage(params);
        PageUtils page = tryVideoService.selectTryVideoVo(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("tigger:tryvideo:info")
    public R info(@PathVariable("id") Integer id){
		TryVideoEntity tryVideo = tryVideoService.getById(id);

        return R.ok().put("tryVideo", tryVideo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("tigger:tryvideo:save")
    public R save(@RequestBody TryVideoEntity tryVideo){
		tryVideoService.save(tryVideo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("tigger:tryvideo:update")
    public R update(@RequestBody TryVideoEntity tryVideo){
		tryVideoService.updateById(tryVideo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("tigger:tryvideo:delete")
    public R delete(@RequestBody Integer[] ids){
		tryVideoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    @RequestMapping(value = "/uploadVodie" ,method = RequestMethod.POST)
    public R uploadVodie(@RequestParam("files") MultipartFile[] files,
                         @RequestParam("introduce") String introduce,
                         @RequestParam("cid")Integer[] cid,
                         @RequestParam("homeShow")Integer homeShow,
                         @RequestParam("id")Integer id,
                         @RequestParam("videoName")String videoName,
                         HttpServletRequest request
    ) {
        String video = null;
        Integer imageId = null;

        //System.out.println(introduce + "=====================");
        for (int i = 0; i < files.length; i++) {
            String name = files[i].getOriginalFilename();//获取文件名
            if (name.contains("png") || name.contains("jpg")) {//若是图片文件
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
                    //
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (name.contains("mp4")) {//视频文件
                try {
                    video = payVideoService.uploadVodie(files[i]);
                    log.info(video + "视频上传后的地址");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        if(id == 0){
            if (imageId == null || video == null || video.equals("") || imageId.equals("")) {
                return R.error("上传失败");
            }
        }
        TryVideoEntity tryVideoEntity = new TryVideoEntity();
        tryVideoEntity.setIntroduce(introduce);
        tryVideoEntity.setHomeShow(homeShow);
        tryVideoEntity.setShowStatus(1);
        tryVideoEntity.setFjId(imageId);
        tryVideoEntity.setCid(Arrays.asList(cid).toString());
        tryVideoEntity.setVideoId(video);
        tryVideoEntity.setVideoName(videoName);
        tryVideoEntity.setCareatTime(new Date());
        if(id!= 0){
            tryVideoEntity.setId(id);
            tryVideoDao.updateById(tryVideoEntity);
            return R.ok();
        }
        tryVideoDao.insert(tryVideoEntity);
        return R.ok();
    }


    @RequestMapping("deletedVideo")
    public R deletedVideo(@RequestBody String str){
        if(StringUtils.isEmpty(str)){
            return R.error("删除失败");
        }
        List<String> videoIds = JSON.parseArray(JSON.parseObject(str).getString("videoIds"), String.class);
        List<Integer> ids = JSON.parseArray(JSON.parseObject(str).getString("ids"), Integer.class);
        tryVideoService.deleteTryVideo(videoIds,ids);
        return R.ok();
    }

    @RequestMapping("updateShowStatus")
    public R updateShowStatus(@RequestParam("id")Integer id,
                              @RequestParam("status") Integer status){
        tryVideoService.updateShowStatus(id,status);
        return R.ok();
    }


    @RequestMapping(value = "getTryvideoAll",method = RequestMethod.GET)
    public R getTryvideoAll(){
        return R.ok().put("page",tryVideoDao.selectList(null));
    }
}
