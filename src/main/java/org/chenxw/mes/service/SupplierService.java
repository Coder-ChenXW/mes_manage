package org.chenxw.mes.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.chenxw.authentication.domain.dto.SupplierDto;
import org.chenxw.mes.entity.Supplier;
import org.chenxw.mes.mapper.SupplierMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 *    服务类
 * </p>
 *
 * @author ChenXW
 * @since 2024-03-01
 */
public interface SupplierService extends IService<Supplier> {


    public List<SupplierDto> fetchSuppliers();


}
