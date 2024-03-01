package org.chenxw.mes.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

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
@ApiModel(value="Purchase对象", description="")
public class Purchase implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "purchase_id", type = IdType.AUTO)
    private Long purchaseId;

    @TableField(value = "material_code")
    private String materialCode;

    @TableField(value = "material_name")
    private String materialName;

    @TableField(value = "vender")
    // private String supplierName;
    private String vender;

    @TableField(value = "purchase_price")
    private BigDecimal purchasePrice;

    @TableField(value = "purchase_quantity")
    private Integer purchaseQuantity;
}
