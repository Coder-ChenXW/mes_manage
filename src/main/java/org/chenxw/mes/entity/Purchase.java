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
@ApiModel(value="Purchase对象", description="")
public class Purchase implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "purchase_id", type = IdType.AUTO)
    private Integer purchaseId;

    @TableField(value = "user_id")
    private Integer userId;

    @TableField(value = "purchase_date", fill = FieldFill.INSERT)
    private Date purchaseDate;

    @TableField(value = "supplier_id")
    private Integer supplierId;
}
