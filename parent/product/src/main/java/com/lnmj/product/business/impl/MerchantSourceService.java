package com.lnmj.product.business.impl;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.common.Enum.ProductTypeDistinguishEnum;
import com.lnmj.common.Enum.ResponseCodePMerchantSourceEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeProductTypeEnum;
import com.lnmj.common.baseController.FtpFileUploadController;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.product.business.IMerchantSourceService;
import com.lnmj.product.business.IProductBrandService;
import com.lnmj.product.entity.*;
import com.lnmj.product.entity.VO.ProductBrandVO;
import com.lnmj.product.entity.VO.ProductType;
import com.lnmj.product.repository.IMerchantSourceDao;
import com.lnmj.product.repository.IProductBrandDao;
import com.lnmj.product.repository.IProductKindDao;
import com.lnmj.product.repository.IProductTypeDisplayDao;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/5/6 18:28
 * @Description: 商品品牌service
 */
@Service("merchantSourceService")
public class MerchantSourceService implements IMerchantSourceService {
    @Resource
    private IMerchantSourceDao merchantSourceDao;


    @Override
    public ResponseResult selectMerchantSourceList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Merchantsource> list = merchantSourceDao.selectMerchantSourceList();
        if (list.size()!=0) {
            PageInfo<Merchantsource> pageInfo = new PageInfo(list);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodePMerchantSourceEnum.MERCHANTSOURCE_ISNULL.getCode(),
                ResponseCodePMerchantSourceEnum.MERCHANTSOURCE_ISNULL.getDesc()));

    }
}
