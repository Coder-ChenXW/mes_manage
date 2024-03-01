package org.chenxw.mes.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.chenxw.authentication.entity.Log;
import org.chenxw.mes.entity.Purchase;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author ChenXW
 * @since 2024-03-01
 */
@Mapper
public interface LogMapper extends BaseMapper<Log> {

    List<Log> queryLogsByDate(Date startDate, Date endDate);

}
