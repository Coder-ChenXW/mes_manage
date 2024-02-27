package org.chenxw.mes.controller.prams;

import lombok.Data;
import org.chenxw.mes.entity.*;

import java.util.List;

@Data
public class UpdateOrderRequest {
    private Order order;
    private List<OrderItem> items;
}
