package com.we.product.controller;

import com.we.product.entity.Category;
import com.we.product.service.CategoryService;
import com.we.product.vo.CategoryVo;
import jakarta.annotation.Resource;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品三级分类(Category)表控制层
 *
 * @author ryan
 * @since 2025-07-29 11:44:41
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    /**
     * 服务对象
     */
    @Resource
    private CategoryService categoryService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/{id}")
    public Category selectOne(@PathVariable("id") Long id) {
        return this.categoryService.getById(id);
    }

    /**
     * 以树形结构返回所有分类数据
     *
     * @return 树形结构分类数据
     */
    @GetMapping("/tree")
    public List<CategoryVo> listWithTree() {
        return categoryService.listWithTree();
    }

    /**
     * 新增分类
     *
     * @param category 分类实体
     * @return 新增结果
     */
    @PostMapping
    public boolean save(@RequestBody Category category) {
        return categoryService.save(category);
    }

    /**
     * 修改分类
     *
     * @param category 分类实体
     * @return 修改结果
     */
    @PutMapping
    public boolean update(@RequestBody Category category) {
        return categoryService.updateById(category);
    }

    /**
     * 删除分类
     *
     * @param ids 分类ID列表
     * @return 删除结果
     */
    @DeleteMapping
    public boolean delete(@RequestBody List<Long> ids) throws NotFoundException {
        return categoryService.removeByIds(ids);
    }
}