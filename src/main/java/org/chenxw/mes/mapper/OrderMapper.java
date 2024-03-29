package org.chenxw.mes.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.chenxw.mes.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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
@Mapper
public interface OrderMapper extends BaseMapper<Order> {


    List<Map<String, Object>> selectCraftReportInfo();

}
