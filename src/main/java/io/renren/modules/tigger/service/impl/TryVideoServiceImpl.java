package io.renren.modules.tigger.service.impl;

import io.renren.common.utils.FastDFSClient;
import io.renren.common.utils.PageUtils;
import io.renren.modules.tigger.dao.AccessoryDao;
import io.renren.modules.tigger.dao.PayVideoDao;
import io.renren.modules.tigger.entity.vo.TryVideoVo;
import io.renren.modules.tigger.utils.UploadVideoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.renren.common.utils.Query;

import io.renren.modules.tigger.dao.TryVideoDao;
import io.renren.modules.tigger.entity.TryVideoEntity;
import io.renren.modules.tigger.service.TryVideoService;


@Service("tryVideoService")
public class TryVideoServiceImpl extends ServiceImpl<TryVideoDao, TryVideoEntity> implements TryVideoService {

    @Autowired
    private TryVideoDao tryVideoDao;

    @Autowired
    private PayVideoDao payVideoDao;

    @Autowired
    private FastDFSClient fastDFSClient;

    @Autowired
    private UploadVideoUtils uploadVideoUtils;

    @Autowired
    private AccessoryDao accessoryDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TryVideoEntity> page = this.page(
                new Query<TryVideoEntity>().getPage(params),
                new QueryWrapper<TryVideoEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils selectTryVideoVo(Map<String, Object> params) {
        IPage<TryVideoVo> tryVideoVoIPage = tryVideoDao.selectTryVideoVo(new Query<TryVideoVo>().getMyPage(params));
        List<TryVideoVo> collect = tryVideoVoIPage.getRecords().stream().map(item -> {
            String s = item.getCid().replaceAll("\\[", "(").replaceAll("]", ")");
            List<String> list = payVideoDao.selectCategroy(s);
            item.setName(list);
            return item;
        }).collect(Collectors.toList());
        tryVideoVoIPage.setRecords(collect);

        return new PageUtils(tryVideoVoIPage);
    }

    @Override
    public void deleteTryVideo(List<String> videoIds, List<Integer> ids) {
        //删除视频
        String s = videoIds.toString();
        String substring = s.substring(1, s.length()).substring(0,s.length()-2);
        ids.forEach(item->{
            String fileUrl = payVideoDao.getFileUrl(item);
            System.out.println(fileUrl);
            fastDFSClient.deleteFile(fileUrl);
            //
            Integer integer = tryVideoDao.selectTryVideoImageId(item);
            accessoryDao.deleteBatchIds(Arrays.asList(integer));
            tryVideoDao.deleteBatchIds(Arrays.asList(item));
        });
        uploadVideoUtils.deleteVideo(substring);
    }

    @Override
    public void updateShowStatus(Integer id, Integer status) {
        tryVideoDao.updateShowStatus(id,status);
    }

}