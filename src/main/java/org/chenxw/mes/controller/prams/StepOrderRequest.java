package org.chenxw.mes.controller.prams;


import lombok.Data;

import java.util.List;

@Data
public class StepOrderRequest {

    private Long orderId;
    private List<StepOrderItem> items;

    @Data
    public static final class StepOrderItem{
        private String packageNo;
        private Integer qty;
    }
}
