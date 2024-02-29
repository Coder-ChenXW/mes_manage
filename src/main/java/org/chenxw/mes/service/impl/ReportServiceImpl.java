package org.chenxw.mes.service.impl;

import org.chenxw.mes.domain.OrderReportInfo;
import org.chenxw.mes.entity.*;
import org.chenxw.mes.mapper.OrderMapper;
import org.chenxw.mes.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Transactional
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ProductCraftService craftService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private OrderMapper orderMapper;



    @Override
    public OrderReportInfo getOrderReportInfo(Long orderId) {
        Order order = orderService.getById(orderId);
        Product product = productService.getById(order.getProductId());
        List<ProductCraft> crafts = craftService.getByProductId(order.getProductId());
        List<Schedule> schedules = scheduleService.getAllByOrderId(orderId);
        List<Employee> employees = schedules.stream().map(Schedule::getEmployeeId).distinct().map(employeeService::getById).collect(Collectors.toList());
        OrderReportInfo info = new OrderReportInfo();
        info.setOrderId(order.getId());
        info.setOrderNo(order.getOrderNo());
        info.setCustomerOrderNo(order.getCustomerOrderNo());
        info.setOrderQty(order.getQty());
        info.setOrderName(order.getName());
        info.setProductId(product.getId());
        info.setProductNo(product.getProductNo());
        info.setProductName(product.getProductName());
        info.setEmployees(employees);


        List<Map<String, Object>> items = crafts.stream().map(c -> {
            Map<String, Object> item = new HashMap<>();
            item.put("craftName", c.getName());
            employees.forEach(e -> {
                String key = "emp_" + e.getId();
                item.put(key, 0);
            });

            item.put("qty", order.getQty());
            item.put("totalQty", order.getQty());
            return item;
        }).collect(Collectors.toList());


        info.setItems(items);
        return info;
    }

    @Override
    public List<Map<String, Object>> getCraftReportInfo() {
        return orderMapper.selectCraftReportInfo();
    }


}
