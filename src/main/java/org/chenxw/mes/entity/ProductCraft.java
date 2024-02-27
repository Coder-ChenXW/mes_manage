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
 * @since 2024-02-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ProductCraft对象", description="")
public class ProductCraft implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long productId;

    private String name;

    private Double price;

    @TableField(fill = FieldFill.INSERT)
    private Date createTs;


}
