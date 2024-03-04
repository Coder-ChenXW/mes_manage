package org.chenxw.mes.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.chenxw.authentication.domain.dto.SupplierDto;
import org.chenxw.mes.entity.Supplier;

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
public interface SupplierMapper extends BaseMapper<Supplier> {

    @Select("SELECT supplier_id,supplier_name FROM supplier;")
    List<SupplierDto> fetchSuppliers();

}
