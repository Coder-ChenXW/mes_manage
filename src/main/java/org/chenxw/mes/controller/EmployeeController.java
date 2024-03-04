package org.chenxw.mes.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.chenxw.mes.controller.prams.CreateEmployeeRequest;
import org.chenxw.mes.entity.Employee;
import org.chenxw.mes.service.EmployeeService;
import org.chenxw.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: ChenXW
 * @Date:2024/2/27 12:43
 * @Description: 员工管理控制器
 **/
@Api(value = "员工管理相关")
@RestController
@RequestMapping("/base/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * @description: 分页查询员工
     * @author: ChenXW
     * @date: 2024/2/27 12:49
     */
    @GetMapping("/page")
    @ApiOperation("分页查询")
    public Result<IPage<Employee>> queryPageData(@RequestParam("pageIndex") Integer pageIndex,
                                                 @RequestParam("pageSize") Integer pageSize,
                                                 @RequestParam(value = "employeeNo", required = false) String employeeNo,
                                                 @RequestParam(value = "employeeName", required = false) String employeeName,
                                                 @RequestParam(value = "status", required = false) Integer status) {

        IPage<Employee> page = new Page<>(pageIndex, pageSize);
        QueryWrapper<Employee> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(employeeNo)) {
            wrapper.eq("employee_no", employeeNo);
        }
        if (StringUtils.isNotBlank(employeeName)) {
            wrapper.likeRight("employee_name", employeeName);
        }
        if (status != null && (status == 0 || status == 1)) {
            wrapper.eq("status", status);
        }

        wrapper.gt("id", 0);
        return Result.generateSuccess(employeeService.page(page, wrapper));

    }


    /**
     * @description: 根据id查询员工
     * @author: ChenXW
     * @date: 2024/2/27 15:04
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询员工")
    public Result<Employee> getEmployeeById(@PathVariable("id") Long id) {
        Employee employee = employeeService.getById(id);
        return Result.generateSuccess(employee);
    }

    /**
     * @description: 获取员工状态
     * @author: ChenXW
     * @date: 2024/2/27 15:09
     */
    @GetMapping("/activated")
    @ApiOperation("获取员工状态")
    public Result<List<Employee>> getActivatedEmployees() {
        List<Employee> employees = employeeService.getActivatedEmployees();
        return Result.generateSuccess(employees);
    }

    /**
     * @description: 根据id修改员工信息
     * @author: ChenXW
     * @date: 2024/2/27 15:18
     */
    @ApiOperation("根据id修改员工信息")
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable("id") Long id, @RequestBody Employee employee){
        employee.setId(id);
        employeeService.update(employee);
        return Result.generateSuccess(null);
    }

    @PostMapping
    public Result<Employee> create(@RequestBody CreateEmployeeRequest request){
        Employee employee = employeeService.create(request.getEmployee(), request.getUser(), request.getRole());
        return Result.generateSuccess(employee);
    }

    /**
     * @description: 删除员工
     * @author: ChenXW
     * @date: 2024/2/27 15:56
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除员工")
    public Result<Void> delete(@PathVariable("id") Long id){
        employeeService.removeById(id);
        return Result.generateSuccess(null);
    }

    /**
     * @description: 停用员工
     * @author: ChenXW
     * @date: 2024/2/27 16:00
     */
    @PatchMapping("/{id}:onTheJob")
    @ApiOperation("停用员工")
    public Result<Void> onTheJob(@PathVariable("id") Long id){
        employeeService.onTheJob(id);
        return Result.generateSuccess(null);
    }

    /**
     * @description: 启用员工
     * @author: ChenXW
     * @date: 2024/2/27 16:09
     */
    @PatchMapping("/{id}:leaveTheJob")
    @ApiOperation("启用员工")
    public Result<Void> leaveTheJob(@PathVariable("id") Long id){
        employeeService.leaveTheJob(id);
        return Result.generateSuccess(null);
    }


}
