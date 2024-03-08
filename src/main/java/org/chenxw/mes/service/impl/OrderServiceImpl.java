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
    private AuthService authService;

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

    @Override
    public List<OrderInfo> getCreatedOrders() {
        List<Order> orders = getBaseMapper().selectByMap(Collections.singletonMap("status", ORDER_STATUS_CREATED));
        return orders.stream().map(this::transToInfo).collect(Collectors.toList());
    }

    @Override
    public List<OrderInfo> getCuttingOrders() {
        List<Order> orders = getBaseMapper().selectByMap(Collections.singletonMap("status", ORDER_STATUS_STEPED));
        return orders.stream().map(this::transToInfo).collect(Collectors.toList());
    }

    @Override
    public List<OrderInfo> getProcessingOrders() {
        QueryWrapper<Order> wrapper = Wrappers.query();
        wrapper.in("status", Arrays.asList(ORDER_STATUS_STEPED, ORDER_STATUS_FINISHED));
        List<Order> orders = getBaseMapper().selectList(wrapper);
        return orders.stream().map(this::transToInfo).collect(Collectors.toList());
    }

    @Override
    public Integer getWaitProcessQty(Long orderId, Long craftId) {
        Order order = baseMapper.selectById(orderId);
        Map<String, Object> params = new HashMap<>();
        params.put("order_id", orderId);
        params.put("product_craft_id", craftId);

        return order.getQty();
    }

    @Override
    public void update(Order order, List<OrderItem> items) {
        checkExists(order);
        Order entity = getBaseMapper().selectById(order.getId());
        if (entity.getStatus() == ORDER_STATUS_FINISHED){
            throw new RuntimeException("订单已完成,无法更改");
        }
        if (!entity.getProductId().equals(order.getProductId())){
            itemService.removeItemsByOrderId(order.getId());
            items.stream().peek(i->i.setOrderId(order.getId())).forEach(itemService::save);
        } else {
            items.forEach(itemService::updateById);
        }
        getBaseMapper().updateById(order);

    }

    @Override
    public Order create(Order order, List<OrderItem> items) {
        checkExists(order);

        User currentUser = authService.getCurrentUser();
        Employee employee = employeeService.getByUserId(currentUser.getId());

        if (employee == null){
            throw new RuntimeException("system error");
        }
        order.setStatus(ORDER_STATUS_CREATED);
        order.setCreateBy(employee.getId());
        order.setUpdateBy(employee.getId());
        getBaseMapper().insert(order);
        items.stream().peek(i->i.setOrderId(order.getId())).forEach(itemService::save);
        return order;
    }

    @Override
    public void cuttingOrder(Long id, Integer qty) {
        Order entity = getById(id);
        if (entity.getStatus()!=OrderService.ORDER_STATUS_CREATED){
            throw new RuntimeException("订单已裁出");
        }
        if (entity.getQty().equals(qty)){
            entity.setStatus(ORDER_STATUS_STEPED);
            getBaseMapper().updateById(entity);
        }
    }

    @Override
    public void finishOrder(Long id, Integer qty) {
        Order entity = getById(id);
        if (entity.getStatus()!=OrderService.ORDER_STATUS_STEPED){
            throw new RuntimeException("订单未开始生产");
        }
        if (entity.getQty().equals(qty)){
            entity.setStatus(ORDER_STATUS_FINISHED);
            getBaseMapper().updateById(entity);
        }
    }

    private void checkExists(Order order){
        QueryWrapper<Order> wrapper = Wrappers.query();
        wrapper.eq("order_no", order.getOrderNo());
        if (order.getId()!=null){
            wrapper.ne("id", order.getId());
        }
        List<Order> orders = getBaseMapper().selectList(wrapper);
        if (orders.size()!=0){
            throw new RuntimeException("订单号重复");
        }
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

        List<ProductCraft> crafts = productCraftService.getByProductId(order.getProductId());

        info.setTotalNeedProcessQty(order.getQty() * crafts.size());
        return info;
    }


}
