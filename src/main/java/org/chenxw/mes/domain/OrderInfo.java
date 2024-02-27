package org.chenxw.mes.domain;

import lombok.Data;

import java.util.Date;
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
public class OrderInfo {
    private Long id;
    private Long productId;
    private String productNo;
    private String productName;
    private String orderNo;
    private String customerOrderNo;
    private String name;
    private Integer qty;
    private Integer finishedQty;
    private Integer totalNeedProcessQty;
    private Integer status;
    private Date createTs;
    private Long createBy;
    private String createByName;
    private Date updateTs;
    private Long updateBy;
    private String updateByName;
    private List<OrderItemInfo> items;
}
