package com.lnmj.k3cloud.business;


import com.lnmj.common.response.ResponseResult;
import org.springframework.stereotype.Service;

/**
 * @Author: yilihua
 * @Date: 2019-11-08 15:09
 * @Description:  金蝶service
 */
@Service("iDataCentreService")
public interface IDataCentreService {
     ResponseResult selectK3DataCentreList(int pageNum,int pageSize,String dataCentreName);
     ResponseResult selectK3LegalPersonNumber(String dataCentreId);

}
