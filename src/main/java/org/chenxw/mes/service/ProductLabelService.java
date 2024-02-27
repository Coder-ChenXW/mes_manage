package org.chenxw.mes.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.chenxw.mes.entity.ProductLabel;

import java.util.List;

/**
 * <p>
 *    服务类
 * </p>
 *
 * @author ChenXW
 * @since 2024-02-23
 */
public interface ProductLabelService extends IService<ProductLabel> {


    int LABEL_STATUS_NORMAL = 1;

    List<ProductLabel> getByProductId(Long productId);


}
