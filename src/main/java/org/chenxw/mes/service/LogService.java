package org.chenxw.mes.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.chenxw.authentication.entity.Log;
import org.chenxw.mes.entity.Purchase;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *    服务类
 * </p>
 *
 * @author ChenXW
 * @since 2024-03-01
 */
public interface LogService  extends IService<Log> {

    List<Log> queryLogsByDate(Date startDate, Date endDate);

}
