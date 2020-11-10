package io.renren.modules.tigger.service.impl;

import io.renren.common.utils.FastDFSClient;
import io.renren.common.utils.R;
import io.renren.modules.tigger.dao.AccessoryDao;
import io.renren.modules.tigger.entity.AccessoryEntity;
import io.renren.modules.tigger.entity.vo.PayVideoVo;
import io.renren.modules.tigger.utils.UploadVideoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.tigger.dao.PayVideoDao;
import io.renren.modules.tigger.entity.PayVideoEntity;
import io.renren.modules.tigger.service.PayVideoService;
import org.springframework.web.multipart.MultipartFile;


@Service("payVideoService")
public class PayVideoServiceImpl extends ServiceImpl<PayVideoDao, PayVideoEntity> implements PayVideoService {

    @Autowired
    private FastDFSClient fastDFSClient;

    @Autowired
    private UploadVideoUtils uploadVideoUtils;

    @Autowired
    private PayVideoDao payVideoDao;

    @Autowired
    private AccessoryDao accessoryDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PayVideoEntity> page = this.page(
                new Query<PayVideoEntity>().getPage(params),
                new QueryWrapper<PayVideoEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public String uploadVodie(MultipartFile multipartFile) {
        try {
            //获取上传id
           return uploadVideoUtils.uploadVideo(multipartFile.getInputStream(), multipartFile.getOriginalFilename(), multipartFile.getOriginalFilename());
            //

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PageUtils getVideo(Map<String, Object> params) {
        IPage<PayVideoVo> payVideoVoIPage = payVideoDao.selectPayVideoVo(new Query<PayVideoVo>().getMyPage(params));
        List<PayVideoVo> collect = payVideoVoIPage.getRecords().stream().map(item -> {
            String s = item.getCid().replaceAll("\\[", "(").replaceAll("]", ")");
            List<String> list = payVideoDao.selectCategroy(s);
            item.setName(list);
            return item;
        }).collect(Collectors.toList());
        payVideoVoIPage.setRecords(collect);
        return new PageUtils(payVideoVoIPage);
    }

    @Override
    public String getVideoVoucher(String videoId) {
        String videoVoucher = uploadVideoUtils.getVideoVoucher(videoId);
        return videoVoucher;
    }

    @Override
    public void updateVideoStatus(Integer id, Integer status) {
        payVideoDao.updateVideoStatus(id,status);
    }

    @Override
    public void deletedVideo(List<String> videoId,List<Integer> id) {
        //删除视频
        String s = videoId.toString();
        String substring = s.substring(1, s.length()).substring(0,s.length()-2);
         id.forEach(item ->{
             Integer fileId = payVideoDao.getFileId(item);
             try{
                 String fileUrl = payVideoDao.getFileUrl(item);
                 fastDFSClient.deleteFile(fileUrl);

             }catch (Exception e){

             }finally {

                 accessoryDao.deleteBatchIds(Arrays.asList(fileId));
             }
         });

        //System.out.println(substring);
        uploadVideoUtils.deleteVideo(substring);
        payVideoDao.deleteBatchIds(id);
    }

}