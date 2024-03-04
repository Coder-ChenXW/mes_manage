package org.chenxw.mes.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.chenxw.mes.controller.prams.CreateOrderRequest;
import org.chenxw.mes.controller.prams.UpdateOrderRequest;
import org.chenxw.mes.controller.prams.UpdateQtyRequest;
import org.chenxw.mes.domain.OrderInfo;
import org.chenxw.mes.entity.Order;
import org.chenxw.mes.service.OrderService;
import org.chenxw.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
                                                  @RequestParam(value = "status", required = false) Integer status) {
        IPage<Order> page = new Page<>(pageIndex, pageSize);
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(orderNo)) {
            wrapper.eq("order_no", orderNo);
        }
        if (StringUtils.isNotBlank(customerOrderNo)) {
            wrapper.eq("customer_order_no", customerOrderNo);
        }
        if (status != null && (status == 0 || status == 1 || status == 2)) {
            wrapper.eq("status", status);
        }
        return Result.generateSuccess(orderService.queryPageData(page, wrapper));
    }

    /**
     * @description: 根据id查询订单
     * @author: ChenXW
     * @date: 2024/2/28 11:01
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询订单")
    public Result<Order> getOrderById(@PathVariable("id") Long id) {
        Order order = orderService.getById(id);
        return Result.generateSuccess(order);
    }

    /**
     * @description: 获取裁床创建前的数据
     * @author: ChenXW
     * @date: 2024/2/28 11:04
     */
    @GetMapping("/created")
    @ApiOperation("获取裁床创建前的数据")
    public Result<List<OrderInfo>> getCreatedOrders() {
        List<OrderInfo> orders = orderService.getCreatedOrders();
        return Result.generateSuccess(orders);
    }

    /**
     * @description: 获取已经完成裁床订单数据
     * @author: ChenXW
     * @date: 2024/2/28 12:50
     */
    @ApiOperation("获取已经完成裁床订单数据")
    @GetMapping("/cutting")
    public Result<List<OrderInfo>> getStepOrders() {
        List<OrderInfo> orders = orderService.getCuttingOrders();
        return Result.generateSuccess(orders);
    }

    /**
     * @description: 订单进程
     * @author: ChenXW
     * @date: 2024/2/28 12:57
     */
    @GetMapping("/processing")
    @ApiOperation("订单进程")
    public Result<List<OrderInfo>> getProcessingOrders() {
        List<OrderInfo> orders = orderService.getProcessingOrders();
        return Result.generateSuccess(orders);
    }

    /**
     * @description: 获取订单的进程数据
     * @author: ChenXW
     * @date: 2024/2/28 13:12
     */
    @ApiOperation("获取订单的进程数据")
    @GetMapping("/{orderId}/{craftId}/waitProcessQty")
    public Result<Integer> getWaitProcessQty(@PathVariable("orderId") Long orderId, @PathVariable("craftId") Long craftId) {
        Integer qty = orderService.getWaitProcessQty(orderId, craftId);
        return Result.generateSuccess(qty);
    }


    /**
     * @description: 修改订单数据
     * @author: ChenXW
     * @date: 2024/2/28 13:23
     */
    @PutMapping("/{id}")
    @ApiOperation("修改订单数据")
    public Result<Void> update(@PathVariable("id") Long id, @RequestBody UpdateOrderRequest request) {
        request.getOrder().setId(id);
        orderService.update(request.getOrder(), request.getItems());
        return Result.generateSuccess(null);
    }

    /**
     * @description: 创建新订单
     * @author: ChenXW
     * @date: 2024/2/28 14:07
     */
    @PostMapping
    @ApiOperation("创建新订单")
    public Result<Order> create(@RequestBody CreateOrderRequest request) {
        Order order = orderService.create(request.getOrder(), request.getItems());
        return Result.generateSuccess(order);
    }

    /**
     * @description: 根据id删除订单
     * @author: ChenXW
     * @date: 2024/2/28 14:23
     */
    @DeleteMapping("/{id}")
    @ApiOperation("根据id删除订单")
    public Result<Void> delete(@PathVariable("id") Long id) {
        orderService.removeById(id);
        return Result.generateSuccess(null);
    }

    /**
     * @description: 裁床加工裁剪
     * @author: ChenXW
     * @date: 2024/2/28 15:54
     */
    @ApiOperation("裁床加工裁剪")
    @PatchMapping("/{id}:cutting")
    public Result<Void> cutting(@PathVariable("id") Long id, @RequestBody UpdateQtyRequest request){
        orderService.cuttingOrder(id, request.getQty());
        return Result.generateSuccess(null);
    }

    /**
     * @description: 员工缝纫完成订单
     * @author: ChenXW
     * @date: 2024/2/28 16:05
     */
    @ApiOperation("员工缝纫完成订单")
    @PatchMapping("/{id}:finish")
    public Result<Void> finish(@PathVariable("id") Long id, @RequestBody UpdateQtyRequest request){
        orderService.finishOrder(id, request.getQty());
        return Result.generateSuccess(null);
    }


}
