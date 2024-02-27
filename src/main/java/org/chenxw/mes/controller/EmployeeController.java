package org.chenxw.mes.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.chenxw.mes.entity.Employee;
import org.chenxw.mes.service.EmployeeService;
import org.chenxw.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

        IPage<Employee> page = new Page<>(pageIndex,pageSize);
        QueryWrapper<Employee> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(employeeNo)){
            wrapper.eq("employee_no", employeeNo);
        }
        if (StringUtils.isNotBlank(employeeName)){
            wrapper.likeRight("employee_name", employeeName);
        }
        if (status!=null && (status==0 || status==1)){
            wrapper.eq("status", status);
        }

        wrapper.gt("id",0);
        return Result.generateSuccess(employeeService.page(page, wrapper));

    }

}
