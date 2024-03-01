package org.chenxw.mes.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.chenxw.mes.entity.Purchase;
import org.chenxw.mes.entity.Supplier;
import org.chenxw.mes.service.PurchaseService;
import org.chenxw.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: ChenXW
 * @Date:2024/3/1 19:07
 * @Description: 采购管理控制器
 **/

@Api(value = "采购管理相关")
@RestController
@RequestMapping("/base/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;


    /**
     * @description: 分页查询采购数据
     * @author: ChenXW
     * @date: 2024/3/1 19:08
     */
    @GetMapping("/page")
    @ApiOperation("分页查询采购数据")
    public Result<IPage<Purchase>> queryPageData(@RequestParam("pageIndex") Long pageIndex,
                                                 @RequestParam("pageSize") Long pageSize,
                                                 @RequestParam(value = "materialCode", required = false) String materialCode,
                                                 @RequestParam(value = "supplierName", required = false) String supplierName) {
        // 创建分页对象
        Page<Purchase> page = new Page<>(pageIndex, pageSize);
        // 创建查询条件构造器
        QueryWrapper<Purchase> queryWrapper = new QueryWrapper<>();
        // 如果材料编号不为空，则添加材料编号的查询条件
        if (materialCode != null && !materialCode.isEmpty()) {
            queryWrapper.like("material_code", materialCode);
        }
        // 如果供应商名称不为空，则添加供应商名称的查询条件
        if (supplierName != null && !supplierName.isEmpty()) {
            queryWrapper.like("supplier_name", supplierName);
        }
        // 调用service层方法进行查询
        IPage<Purchase> purchasePage = purchaseService.page(page, queryWrapper);
        // 返回查询结果
        return Result.generateSuccess(purchasePage);
    }

    /**
     * @description: 修改采购记录
     * @author: ChenXW
     * @date: 2024/3/1 20:25
     */
    @PutMapping("/{id}")
    @ApiOperation("修改采购记录")
    public Result<Purchase> updateSupplier(@PathVariable("id") Long id, @RequestBody Purchase purchase) {
        purchase.setPurchaseId(id);
        purchaseService.updateById(purchase);
        return Result.generateSuccess(null);
    }

    /**
     * @description: 删除采购记录
     * @author: ChenXW
     * @date: 2024/3/1 20:29
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除采购记录")
    public Result<Void> delete(@PathVariable("id") Long id){
        purchaseService.removeById(id);
        return Result.generateSuccess(null);
    }

    /**
     * @description: 采购入库
     * @author: ChenXW
     * @date: 2024/3/1 20:43
     */
    @PostMapping
    @ApiOperation("采购入库")
    public Result<Purchase> createPurchase(@RequestBody Purchase purchase) {
        boolean success = purchaseService.save(purchase);
        if (success) {
            return Result.generateSuccess(purchase);
        } else {
            return Result.generateError(500, "采购入库失败");
        }
    }





}
