package io.renren.modules.tigger.service.impl;

import io.renren.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.tigger.dao.CategoryDao;
import io.renren.modules.tigger.entity.CategoryEntity;
import io.renren.modules.tigger.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> selectCategory(Integer type) {
        CategoryEntity categoryEntity1 = new CategoryEntity();
        categoryEntity1.setShowStatus(1);
        categoryEntity1.setAStatus(type);
        List<CategoryEntity> categoryEntities = baseMapper.selectList(new QueryWrapper<>(categoryEntity1));

        List<CategoryEntity> collect = categoryEntities.stream().filter(categoryEntity -> {
            return categoryEntity.getParentId() == 0;
        }).map(muen -> {
            muen.setChidrens(getChildrens(muen, categoryEntities));
            return muen;
        }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public void deletedCategoryByid(List<Integer> ids) {
        categoryDao.deleteCategory(ids);
        baseMapper.deleteBatchIds(ids);
    }

    @Override
    public R queryList() {
        List<CategoryEntity> categoryEntities = categoryDao.selectList(null);
        return R.ok().put("data",categoryEntities);
    }


    public  List<CategoryEntity> getChildrens(CategoryEntity in,List<CategoryEntity> all){
        List<CategoryEntity> collect = all.stream().filter(categoryEntity -> {
            return in.getId() == categoryEntity.getParentId();
        }).map(muen -> {
            muen.setChidrens(getChildrens(muen, all));
            return muen;
        }).collect(Collectors.toList());

        return collect;
    }
}