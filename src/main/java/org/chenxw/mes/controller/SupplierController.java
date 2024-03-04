package org.chenxw.mes.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.chenxw.authentication.domain.dto.SupplierDto;
import org.chenxw.mes.controller.prams.CreateEmployeeRequest;
import org.chenxw.mes.entity.Employee;
import org.chenxw.mes.entity.Supplier;
import org.chenxw.mes.service.EmployeeService;
import org.chenxw.mes.service.SupplierService;
import org.chenxw.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: ChenXW
 * @Date:2024/3/01 14:41
 * @Description: 供应商管理控制器
 **/
@Api(value = "供应商管理相关")
@RestController
@RequestMapping("/base/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    /**
     * @description: 分页查询供应商信息
     * @author: ChenXW
     * @date: 2024/3/1 14:42
     */
    @GetMapping("/page")
    @ApiOperation("分页查询供应商信息")
    public Result<IPage<Supplier>> queryPageData(@RequestParam("pageIndex") Integer pageIndex,
                                                 @RequestParam("pageSize") Integer pageSize,
                                                 @RequestParam(value = "supplierName", required = false) String supplierName,
                                                 @RequestParam(value = "manageRange", required = false) String manageRange) {
        IPage<Supplier> page = new Page<>(pageIndex, pageSize);
        QueryWrapper<Supplier> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(supplierName)) {
            wrapper.like("supplier_name", supplierName);
        }
        if (StringUtils.isNotBlank(manageRange)) {
            wrapper.like("manage_range", manageRange);
        }
        wrapper.gt("supplier_id", 0);

        return Result.generateSuccess(supplierService.page(page, wrapper));
    }

    /**
     * @description: 添加供应商
     * @author: ChenXW
     * @date: 2024/3/1 15:13
     */
    @PostMapping
    @ApiOperation("添加供应商")
    public Result<Supplier> createSupplier(@RequestBody Supplier supplier) {
        boolean success = supplierService.save(supplier);
        if (success) {
            return Result.generateSuccess(supplier);
        } else {
            return Result.generateError(500, "创建供应商失败");
        }
    }

    /**
     * @description: 根据Id获取供应商信息
     * @author: ChenXW
     * @date: 2024/3/1 15:28
     */
    @GetMapping("/{id}")
    @ApiOperation("根据Id获取供应商信息")
    public Result<Supplier> getSupplierById(@PathVariable("id") Long id) {
        Supplier supplier = supplierService.getById(id);
        if (supplier != null) {
            return Result.generateSuccess(supplier);
        } else {
            return Result.generateError(404, "未找到对应的供应商");
        }
    }

    /**
     * @description: 更新供应商信息
     * @author: ChenXW
     * @date: 2024/3/1 15:41
     */
    @PutMapping("/{id}")
    @ApiOperation("更新供应商信息")
    public Result<Supplier> updateSupplier(@PathVariable("id") Long id, @RequestBody Supplier supplier) {
        supplier.setSupplierId(id); // 设置供应商ID
        supplierService.updateById(supplier);
        return Result.generateSuccess(null);
    }


    /**
     * @description: 删除供应商
     * @author: ChenXW
     * @date: 2024/3/1 15:54
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除供应商")
    public Result<Void> delete(@PathVariable("id") Long id){
        supplierService.removeById(id);
        return Result.generateSuccess(null);
    }

    @GetMapping
    public ResponseEntity<Result<List<SupplierDto>>> fetchSuppliers() {
        try {
            List<SupplierDto> suppliers = supplierService.fetchSuppliers();
            return ResponseEntity.ok().body(Result.generateSuccess(suppliers));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Result.generateError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
        }
    }



}
