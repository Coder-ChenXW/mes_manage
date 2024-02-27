package org.chenxw.mes.entity;

import com.baomidou.mybatisplus.annotation.*;
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
@TableName(value = "`order`")
@ApiModel(value="Order对象", description="")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long productId;

    private String productNo;

    private String productName;

    private String orderNo;

    private String customerOrderNo;

    private String name;

    private Integer qty;

    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private Date createTs;

    private Long createBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTs;

    private Long updateBy;


}
