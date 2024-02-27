package org.chenxw.mes.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.chenxw.mes.domain.OrderInfo;
import org.chenxw.mes.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import org.chenxw.mes.entity.OrderItem;

import java.util.List;

/**
 * <p>
 *    服务类
 * </p>
 *
 * @author ChenXW
 * @since 2024-02-23
 */
public interface OrderService extends IService<Order> {

    int ORDER_STATUS_CREATED = 0;
    int ORDER_STATUS_STEPED = 1;
    int ORDER_STATUS_FINISHED = 2;

    List<Order> getByProductId(Long id);

    IPage<OrderInfo> queryPageData(IPage<Order> pageRequest, QueryWrapper<Order> wrapper);

}
