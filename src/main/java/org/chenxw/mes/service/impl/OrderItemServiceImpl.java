package org.chenxw.mes.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.chenxw.mes.mapper.OrderItemMapper;
import org.chenxw.mes.entity.OrderItem;
import org.chenxw.mes.service.OrderItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

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
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {

    @Override
    public List<OrderItem> getItemsByOrderId(Long orderId) {
        return getBaseMapper().selectByMap(Collections.singletonMap("order_id", orderId));
    }
}
