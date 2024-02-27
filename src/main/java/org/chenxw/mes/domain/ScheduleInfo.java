package org.chenxw.mes.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author ChenXW
 * @since 2024-02-23
 */
@Data
public class ScheduleInfo implements Serializable {

    private Long id;
    private Long orderId;
    private String orderNo;
    private Long productId;
    private String productName;
    private Long productCraftId;
    private String productCraftName;
    private Long employeeId;
    private String employeeName;
    private Integer qty;
    private Integer status;
    private Date createTs;
    private Long createBy;
    private String createByName;
    private Date updateTs;
    private Long updateBy;
    private String updateByName;


}
