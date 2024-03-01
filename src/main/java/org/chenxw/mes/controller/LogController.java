package org.chenxw.mes.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.chenxw.authentication.entity.Log;
import org.chenxw.mes.service.LogService;
import org.chenxw.mes.service.PurchaseService;
import org.chenxw.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @Author: ChenXW
 * @Date:2024/3/1 23:16
 * @Description: 登录日志相关
 **/
@Api(value = "登录日志相关")
@RestController
@RequestMapping("/base/log")
public class LogController {


    @Autowired
    private LogService logService;

    /**
     * @description: 查询所有日志
     * @author: ChenXW
     * @date: 2024/3/1 23:21
     */
    @GetMapping
    @ApiOperation("查询所有日志")
    public Result<List<Log>> getAllLogs() {
        List<Log> logs = logService.list();
        return Result.generateSuccess(logs);
    }

    /**
     * @description: 根据起始日期和结束日期查询日志
     * @author: ChenXW
     * @date: 2024/3/1 23:31
     */
    @PostMapping("/query")
    @ApiOperation("根据起始日期和结束日期查询日志")
    public Result<List<Log>> queryLogsByDate(@RequestBody Date startDate, @RequestBody Date endDate) {
        List<Log> logs = logService.queryLogsByDate(startDate, endDate);
        return Result.generateSuccess(logs);
    }

}
