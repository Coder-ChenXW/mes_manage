package org.chenxw.mes.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.chenxw.mes.entity.Purchase;
import org.chenxw.mes.mapper.PurchaseMapper;
import org.chenxw.mes.service.PurchaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ChenXW
 * @since 2024-03-01
 */
@Service
@Transactional
public class PurchaseServiceImpl extends ServiceImpl<PurchaseMapper, Purchase> implements PurchaseService {
}
