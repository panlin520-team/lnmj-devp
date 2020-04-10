package com.lnmj.product.business;

import com.lnmj.common.response.ResponseResult;
import org.springframework.stereotype.Service;

@Service("iMerchantSourceService")
public interface IMerchantSourceService {
    ResponseResult selectMerchantSourceList(int pageNum, int pageSize);
}
