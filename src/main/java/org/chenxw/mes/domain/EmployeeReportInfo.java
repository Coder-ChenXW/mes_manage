package org.chenxw.mes.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author ChenXW
 * @since 2024-02-23
 */
@Data
public class EmployeeReportInfo implements Serializable {

    private Long employeeId;
    private String employeeName;
    private Long orderId;
    private String orderNo;
    private String customerOrderNo;
    private String orderName;
    private Long productId;
    private String productNo;
    private String productName;
    private Double totalAmount;
    private List<EmployeeReportInfoItem> items;

    @Data
    public static class EmployeeReportInfoItem {
        private Long productCraftId;
        private String productCraftName;
        private Double productCraftPrice;
        private Integer qty;
        private Double productCraftPriceTotal;
    }

}
