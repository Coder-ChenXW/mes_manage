package org.chenxw.mes.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.chenxw.authentication.domain.dto.SupplierDto;
import org.chenxw.mes.entity.Employee;
import org.chenxw.mes.entity.Supplier;
import org.chenxw.mes.mapper.EmployeeMapper;
import org.chenxw.mes.mapper.SupplierMapper;
import org.chenxw.mes.service.EmployeeService;
import org.chenxw.mes.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class SupplierServiceImpl extends ServiceImpl<SupplierMapper, Supplier> implements SupplierService {

    @Autowired
    private SupplierMapper supplierMapper;

    @Override
    public List<SupplierDto> fetchSuppliers() {
        return supplierMapper.fetchSuppliers();
    }
}
