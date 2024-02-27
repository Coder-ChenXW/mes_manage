package org.chenxw.mes.domain;

import lombok.Data;
import org.chenxw.mes.entity.ProductCraft;
import org.chenxw.mes.entity.ProductLabel;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author ChenXW
 * @since 2024-02-23
 */
@Data
public class ProductInfo {
    private Long id;
    private String productNo;
    private String productName;
    private Integer status;
    private List<ProductLabel> labels;
    private List<ProductCraft> crafts;
    private Date createTs;
    private Long createBy;
    private String createByName;
    private Date updateTs;
    private Long updateBy;
    private String updateByName;
}
