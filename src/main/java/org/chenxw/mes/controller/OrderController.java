package org.chenxw.mes.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.chenxw.mes.domain.OrderInfo;
import org.chenxw.mes.entity.Order;
import org.chenxw.mes.service.OrderService;
import org.chenxw.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: ChenXW
 * @Date:2024/2/27 23:28
 * @Description: 订单管理相关
 **/

@RestController
@RequestMapping("/base/order")
@Api(value = "订单管理相关")
public class OrderController {

    @Autowired
    private OrderService orderService;


    /**
     * @description: 分页查询订单
     * @author: ChenXW
     * @date: 2024/2/27 23:31
     */
    @GetMapping("/page")
    @ApiOperation("分页查询订单")
    public Result<IPage<OrderInfo>> queryPageData(@RequestParam("pageIndex") Integer pageIndex,
                                                  @RequestParam("pageSize") Integer pageSize,
                                                  @RequestParam(value = "orderNo", required = false) String orderNo,
                                                  @RequestParam(value = "customerOrderNo", required = false) String customerOrderNo,
                                                  @RequestParam(value = "status", required = false) Integer status){
        IPage<Order> page = new Page<>(pageIndex, pageSize);
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(orderNo)){
            wrapper.eq("order_no", orderNo);
        }
        if (StringUtils.isNotBlank(customerOrderNo)){
            wrapper.eq("customer_order_no", customerOrderNo);
        }
        if (status!=null && (status==0 || status==1 || status==2)){
            wrapper.eq("status", status);
        }
        return Result.generateSuccess(orderService.queryPageData(page, wrapper));
    }

}
