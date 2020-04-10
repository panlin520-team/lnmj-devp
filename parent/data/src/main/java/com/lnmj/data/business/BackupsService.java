package com.lnmj.data.business;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: panlin
 * @Date: 2019/8/21 10:25
 * @Description: 优惠券
 */
@Service("dBService")
public interface BackupsService {
    void saveProductData(List list);

    void saveServiceProductData(List list);

    void saveSupplierData(List list);

    void saveMember(Map map);

    void saveMemberWallet(Map map2);
}
