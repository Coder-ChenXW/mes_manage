package org.chenxw.mes.domain;

import lombok.Data;
import org.chenxw.mes.entity.Employee;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author ChenXW
 * @since 2024-02-23
 */
@Data
public class OrderReportInfo implements Serializable {

    private Long orderId;
    private String orderNo;
    private String customerOrderNo;
    private Integer orderQty;
    private String orderName;
    private Long productId;
    private String productNo;
    private String productName;
    private List<Employee> employees;
    private List<Map<String, Object>> items;

}
