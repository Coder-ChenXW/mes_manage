package org.chenxw.mes.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.chenxw.mes.domain.ScheduleInfo;
import org.chenxw.mes.entity.Schedule;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *    服务类
 * </p>
 *
 * @author ChenXW
 * @since 2024-02-23
 */
public interface ScheduleService extends IService<Schedule> {

    List<Schedule> getAllByOrderId(Long orderId);


}
