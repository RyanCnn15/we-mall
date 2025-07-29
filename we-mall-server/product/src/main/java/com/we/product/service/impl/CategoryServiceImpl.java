package com.we.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.we.product.entity.Category;
import com.we.product.mapper.CategoryMapper;
import com.we.product.service.CategoryService;
import com.we.product.vo.CategoryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 针对表【pms_category(商品三级分类)】的数据库操作Service实现
 *
 * @author ryan
 * @createDate 2025-07-29 13:45:26
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
        implements CategoryService {

    @Override
    public List<CategoryVo> listWithTree() {
        // 1. 查询所有分类数据
        List<Category> categories = this.list();

        // 2. 转换为VO对象
        List<CategoryVo> categoryVos = categories.stream().map(category -> {
            CategoryVo categoryVo = new CategoryVo();
            BeanUtils.copyProperties(category, categoryVo);
            return categoryVo;
        }).collect(Collectors.toList());

        // 3. 构建成树形结构并按sort排序
        return categoryVos.stream()
                .filter(categoryVo -> categoryVo.getParentCid() == 0)  // 找到所有一级分类
                .peek(categoryVo -> categoryVo.setChildren(getChildren(categoryVo, categoryVos)))  // 为每个一级分类设置子分类
                .sorted((v1, v2) -> (v1.getSort() == null ? 0 : v1.getSort()) - (v2.getSort() == null ? 0 : v2.getSort())) // 按sort字段排序
                .collect(Collectors.toList());
    }

    /**
     * 递归获取子分类
     *
     * @param root 当前分类
     * @param all  所有分类
     * @return 子分类列表
     */
    private List<CategoryVo> getChildren(CategoryVo root, List<CategoryVo> all) {
        return all.stream()
                .filter(categoryVo -> categoryVo.getParentCid().equals(root.getCatId()))  // 找到当前分类的子分类
                .peek(categoryVo -> categoryVo.setChildren(getChildren(categoryVo, all)))  // 为子分类设置子分类（递归）
                .sorted((v1, v2) -> (v1.getSort() == null ? 0 : v1.getSort()) - (v2.getSort() == null ? 0 : v2.getSort()))  // 按sort字段排序
                .collect(Collectors.toList());
    }
}