package org.chenxw.mes.service;

import org.chenxw.mes.domain.EmployeeReportInfo;
import org.chenxw.mes.domain.OrderReportInfo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *    服务类
 * </p>
 *
 * @author ChenXW
 * @since 2024-02-23
 */
public interface ReportService{



    OrderReportInfo getOrderReportInfo(Long orderId);


    List<Map<String, Object>> getCraftReportInfo();


}
