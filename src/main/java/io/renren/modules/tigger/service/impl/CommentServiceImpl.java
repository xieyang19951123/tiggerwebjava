package io.renren.modules.tigger.service.impl;

import io.renren.common.utils.FastDFSClient;
import io.renren.modules.tigger.dao.AccessoryDao;
import io.renren.modules.tigger.dao.PayVideoDao;
import io.renren.modules.tigger.entity.AccessoryEntity;
import io.renren.modules.tigger.entity.vo.CommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.tigger.dao.CommentDao;
import io.renren.modules.tigger.entity.CommentEntity;
import io.renren.modules.tigger.service.CommentService;


@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentDao, CommentEntity> implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private PayVideoDao payVideoDao;

    @Autowired
    private AccessoryDao accessoryDao;

    @Autowired
    private FastDFSClient fastDFSClient;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CommentEntity> page = this.page(
                new Query<CommentEntity>().getPage(params),
                new QueryWrapper<CommentEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils getProduct(Map<String, Object> params) {
        IPage<CommentVo> product = commentDao.getProduct(new Query<CommentVo>().getMyPage(params));
        product.getRecords().stream().map(item->{
            String s = item.getCid().replaceAll("\\[", "(").replaceAll("]", ")");
            List<String> list = payVideoDao.selectCategroy(s);
            item.setName(list);
            String id = item.getFjId().replaceAll("\\[", "(").replaceAll("]", ")");
            List<String> accessoryEntities = commentDao.getFjsrc(id);
            item.setAccessoryEntities(accessoryEntities);
            return item;
        }).collect(Collectors.toList());
        return new PageUtils(product);
    }

    @Override
    public void deleteProduct(List<CommentVo> products) {
        products.forEach(product->{
            List<String> accessoryEntities = product.getAccessoryEntities();


            try{
                accessoryEntities.forEach(imageurl->{
                    fastDFSClient.deleteFile(imageurl);//删除图片
                });
                fastDFSClient.deleteFile(product.getCoverSrc());
                fastDFSClient.deleteFile(product.getASrc());
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                String fjId = product.getFjId().replaceAll("\\[", "(").replaceAll("]", ")");
                accessoryDao.deletedImage(fjId);
                commentDao.deleteBatchIds(Arrays.asList(product.getId()));
                accessoryDao.deleteBatchIds(Arrays.asList(product.getCover()));
            }



        });
    }

    @Override
    public void updateShowStatus(Integer id, Integer status) {
        commentDao.updateShowStatus(id,status);
    }


}