package org.chenxw.mes.domain;

import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author ChenXW
 * @since 2024-02-23
 */
@Data
public class OrderItemInfo {
    private Long id;
    private Long orderId;
    private Long productId;
    private String productName;
    private Long productLabelId;
    private String productLabelDescription;
    private Integer qty;
}
