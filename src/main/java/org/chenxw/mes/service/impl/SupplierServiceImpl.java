package org.chenxw.mes.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.chenxw.mes.entity.Employee;
import org.chenxw.mes.entity.Supplier;
import org.chenxw.mes.mapper.EmployeeMapper;
import org.chenxw.mes.mapper.SupplierMapper;
import org.chenxw.mes.service.EmployeeService;
import org.chenxw.mes.service.SupplierService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ChenXW
 * @since 2024-02-23
 */
@Service
@Transactional
public class SupplierServiceImpl extends ServiceImpl<SupplierMapper, Supplier> implements SupplierService {

}
