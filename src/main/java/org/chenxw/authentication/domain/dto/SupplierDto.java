package org.chenxw.authentication.domain.dto;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * @Author: ChenXW
 * @Date:2024/3/4 15:18
 * @Description:
 **/
@Data
public class SupplierDto implements Serializable {

    @Column(name = "supplier_id")
    private String supplierId;

    @Column(name = "supplier_name")
    private String supplierName;

}
