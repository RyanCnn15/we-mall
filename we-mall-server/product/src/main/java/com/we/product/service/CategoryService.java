package com.we.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.we.product.entity.Category;
import com.we.product.vo.CategoryVo;

import java.util.List;

/**
 * @author ryan
 * @description 针对表【pms_category(商品三级分类)】的数据库操作Service
 * @createDate 2025-07-29 13:45:26
 */
public interface CategoryService extends IService<Category> {

    /**
     * 以树形结构返回分类数据
     *
     * @return 树形结构分类数据
     */
    List<CategoryVo> listWithTree();
}