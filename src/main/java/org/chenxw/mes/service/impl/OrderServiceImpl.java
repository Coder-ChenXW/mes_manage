package org.chenxw.mes.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.chenxw.authentication.entity.User;
import org.chenxw.authentication.service.AuthService;
import org.chenxw.mes.mapper.OrderMapper;
import org.chenxw.mes.domain.OrderInfo;
import org.chenxw.mes.domain.OrderItemInfo;
import org.chenxw.mes.entity.*;
import org.chenxw.mes.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *    服务实现类
 * </p>
 *
 * @author ChenXW
 * @since 2024-02-23
 */

@Service
@Transactional
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private OrderItemService itemService;

    @Autowired
    private ProductLabelService productLabelService;

    @Autowired
    private ProductCraftService productCraftService;

    @Autowired
    private ScheduleService scheduleService;

    @Override
    public List<Order> getByProductId(Long productId) {
        return getBaseMapper().selectByMap(Collections.singletonMap("product_id", productId));
    }

    @Override
    public IPage<OrderInfo> queryPageData(IPage<Order> pageRequest, QueryWrapper<Order> wrapper) {
        IPage<Order> page = getBaseMapper().selectPage(pageRequest, wrapper);
        IPage<OrderInfo> result = new Page<>();
        BeanUtils.copyProperties(page, result);
        List<OrderInfo> records = page.getRecords().stream().map(this::transToInfo).collect(Collectors.toList());
        result.setRecords(records);
        return result;
    }

    private OrderInfo transToInfo(Order order){
        OrderInfo info = new OrderInfo();
        BeanUtils.copyProperties(order, info);

        List<OrderItem> items = itemService.getItemsByOrderId(order.getId());
        List<OrderItemInfo> infoItems = items.stream().map(i -> {
            OrderItemInfo itemInfo = new OrderItemInfo();
            BeanUtils.copyProperties(i, itemInfo);
            ProductLabel label = productLabelService.getById(i.getProductLabelId());
            itemInfo.setProductName(order.getProductName());
            itemInfo.setProductLabelDescription(label.getDescription());
            return itemInfo;
        }).collect(Collectors.toList());
        info.setItems(infoItems);

        Long createBy = order.getCreateBy();
        Long updateBy = order.getUpdateBy();
        Employee creator = employeeService.getById(createBy);
        Employee updater = employeeService.getById(updateBy);

        info.setCreateByName(creator.getEmployeeName());
        info.setUpdateByName(updater.getEmployeeName());

        List<Schedule> schedules = scheduleService.getAllByOrderId(order.getId());
        int finishedQty = schedules.stream().mapToInt(Schedule::getQty).sum();
        info.setFinishedQty(finishedQty);

        List<ProductCraft> crafts = productCraftService.getByProductId(order.getProductId());

        info.setTotalNeedProcessQty(order.getQty() * crafts.size());
        return info;
    }


}
