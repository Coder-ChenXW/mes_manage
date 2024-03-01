package org.chenxw.mes.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.chenxw.authentication.entity.Log;
import org.chenxw.mes.entity.Purchase;
import org.chenxw.mes.mapper.LogMapper;
import org.chenxw.mes.mapper.PurchaseMapper;
import org.chenxw.mes.service.LogService;
import org.chenxw.mes.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ChenXW
 * @since 2024-03-01
 */
@Service
@Transactional
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {


    @Autowired
    private LogMapper logMapper;

    @Override
    public List<Log> queryLogsByDate(Date startDate, Date endDate) {
        return logMapper.queryLogsByDate(startDate, endDate);
    }
}
