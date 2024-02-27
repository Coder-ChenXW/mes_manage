package org.chenxw.mes.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@ApiModel(value="ProcessTask对象", description="")
public class ProcessTask implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long orderId;

    private Long productId;

    private Long productCraftId;

    private String taskNo;

    private String taskDesc;

    private Integer qty;

    private Long operatorId;

    private Long approverId;

    private Integer status;

    private Date createTs;

    private Long createBy;

    private Long updateTs;

    private Long updateBy;


}
