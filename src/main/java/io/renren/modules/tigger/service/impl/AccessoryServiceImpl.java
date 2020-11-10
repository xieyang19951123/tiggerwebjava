package io.renren.modules.tigger.service.impl;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.fdfs.ThumbImageConfig;
import io.renren.common.utils.FastDFSClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.tigger.dao.AccessoryDao;
import io.renren.modules.tigger.entity.AccessoryEntity;
import io.renren.modules.tigger.service.AccessoryService;
import org.springframework.web.multipart.MultipartFile;


@Service("accessoryService")
public class AccessoryServiceImpl extends ServiceImpl<AccessoryDao, AccessoryEntity> implements AccessoryService {

    @Autowired
    private FastDFSClient fastDFSClient;

    @Autowired
    private ThumbImageConfig thumbImageConfig;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AccessoryEntity> page = this.page(
                new Query<AccessoryEntity>().getPage(params),
                new QueryWrapper<AccessoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public AccessoryEntity uploadImage(MultipartFile multipartFile) {
        AccessoryEntity accessoryEntity = null;
        try {
            StorePath storePath = fastDFSClient.uploadFile3(multipartFile);
            String path = "http://106.75.162.179:8888//"+storePath.getGroup() + "/"+  storePath.getPath();
            String thumbImagePath = "http://106.75.162.179:8888//"+storePath.getGroup()+"/"+thumbImageConfig.getThumbImagePath(storePath.getPath());//缩略图
            accessoryEntity = new AccessoryEntity();
            accessoryEntity.setCreateTime(new Date());
            accessoryEntity.setCoverSrc(path);
            accessoryEntity.setASrc(thumbImagePath);
            accessoryEntity.setShowStatus(1);
            accessoryEntity.setFileName(multipartFile.getOriginalFilename());
            accessoryEntity.setAStatus(1);
            baseMapper.insert(accessoryEntity);
        }catch (Exception e){}finally {
            return accessoryEntity;
        }


    }

    @Override
    public void deletedImage(Integer id) {

        if(id != null){

            AccessoryEntity accessoryEntity = baseMapper.selectById(id);

            try {
                fastDFSClient.deleteFile(accessoryEntity.getASrc());
                fastDFSClient.deleteFile(accessoryEntity.getCoverSrc());
            }catch (Exception e){

            }finally {
                baseMapper.deleteBatchIds(Arrays.asList(id));
            }
        }

    }

}