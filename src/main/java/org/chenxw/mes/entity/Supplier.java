package org.chenxw.mes.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;


/**
 * <p>
 *
 * </p>
 *
 * @author ChenXW
 * @since 2024-03-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Supplier对象", description="")
public class Supplier implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "supplier_id", type = IdType.AUTO)
    private Long supplierId;

    @TableField(value = "supplier_name")
    private String supplierName;

    @TableField(value = "contact_person")
    private String contactPerson;

    private String phone;

    @TableField(value = "supplier_address")
    private String supplierAddress;

    @TableField(value = "manage_range")
    private String manageRange;

}
