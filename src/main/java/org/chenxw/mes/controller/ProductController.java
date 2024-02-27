package org.chenxw.mes.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.chenxw.mes.controller.prams.CreateProductRequest;
import org.chenxw.mes.controller.prams.UpdateProductRequest;
import org.chenxw.mes.domain.ProductInfo;
import org.chenxw.mes.entity.Product;
import org.chenxw.mes.service.ProductService;
import org.chenxw.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: ChenXW
 * @Date:2024/2/27 17:43
 * @Description: 产品管理控制器
 **/

@RestController
@RequestMapping("/base/product")
@Api(value = "产品管理相关")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * @description: 分页查询产品
     * @author: ChenXW
     * @date: 2024/2/27 17:50
     */
    @ApiOperation("分页查询产品")
    @GetMapping("/page")
    public Result<IPage<ProductInfo>> queryPageData(@RequestParam("pageIndex") Integer pageIndex,
                                                    @RequestParam("pageSize") Integer pageSize,
                                                    @RequestParam(value = "productNo", required = false) String productNo,
                                                    @RequestParam(value = "productName", required = false) String productName,
                                                    @RequestParam(value = "status", required = false) Integer status) {

        IPage<Product> page = new Page<>(pageIndex, pageSize);
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(productNo)) {
            wrapper.eq("product_no", productNo);
        }
        if (StringUtils.isNotBlank(productName)) {
            wrapper.likeRight("product_name", productName);
        }
        if (status != null && (status == 0 || status == 1 || status == 2)) {
            wrapper.eq("status", status);
        }
        return Result.generateSuccess(productService.queryPageData(page, wrapper));
    }

    /**
     * @description: 根据id查询产品
     * @author: ChenXW
     * @date: 2024/2/27 18:23
     */
    @ApiOperation("根据id查询产品")
    @GetMapping("/{id}")
    public Result<ProductInfo> getProductById(@PathVariable("id") Long id) {
        ProductInfo info = productService.getInfoById(id);
        return Result.generateSuccess(info);
    }

    /**
     * @description: 获取产品信息
     * @author: ChenXW
     * @date: 2024/2/27 18:31
     */
    @GetMapping("/activated")
    @ApiOperation("获取产品信息")
    public Result<List<ProductInfo>> getActivatedProducts(){
        IPage<Product> page = new Page<>(1, 999999999);
        IPage<ProductInfo> result = productService.queryPageData(page, null);
        return Result.generateSuccess(result.getRecords());
    }

    /**
     * @description: 修改产品
     * @author: ChenXW
     * @date: 2024/2/27 18:45
     */
    @ApiOperation("修改产品")
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable("id") Long id, @RequestBody UpdateProductRequest request){
        request.getProduct().setId(id);
        productService.update(request.getProduct(), request.getLabels(), request.getCrafts());
        return Result.generateSuccess(null);
    }

    /**
     * @description: 新增产品
     * @author: ChenXW
     * @date: 2024/2/27 22:00
     */
    @ApiOperation("新增产品")
    @PostMapping
    public Result<Product> create(@RequestBody CreateProductRequest request){
        Product product = productService.create(request.getProduct(), request.getLabels(), request.getCrafts());
        return Result.generateSuccess(product);
    }

    /**
     * @description: 删除产品
     * @author: ChenXW
     * @date: 2024/2/27 22:13
     */
    @ApiOperation("删除产品")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable("id") Long id){
        productService.removeById(id);
        return Result.generateSuccess(null);
    }

    /**
     * @description: 停用产品
     * @author: ChenXW
     * @date: 2024/2/27 22:16
     */
    @PatchMapping("/{id}:inactive")
    public Result<Void> inactive(@PathVariable("id") Long id){
        productService.inactive(id);
        return Result.generateSuccess(null);
    }


    /**
     * @description: 启用产品
     * @author: ChenXW
     * @date: 2024/2/27 22:19
     */
    @PatchMapping("/{id}:active")
    public Result<Void> active(@PathVariable("id") Long id){
        productService.active(id);
        return Result.generateSuccess(null);
    }



}
