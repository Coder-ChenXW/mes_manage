package org.chenxw.mes.controller.prams;

import lombok.Data;
import org.chenxw.mes.entity.Product;
import org.chenxw.mes.entity.ProductCraft;
import org.chenxw.mes.entity.ProductLabel;

import java.util.List;

@Data
public class UpdateProductRequest {
    private Product product;
    private List<ProductLabel> labels;
    private List<ProductCraft> crafts;
}
