package org.chenxw.mes.controller.prams;

import lombok.Data;
import org.chenxw.mes.entity.Order;
import org.chenxw.mes.entity.OrderItem;

import java.util.List;

@Data
public class CreateOrderRequest {
    private Order order;
    private List<OrderItem> items;
}
