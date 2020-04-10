package com.lnmj.k3cloud.business;


import com.lnmj.common.response.ResponseResult;
import org.springframework.stereotype.Service;

/**
 * @Author: panlin
 * @Date: 2019-11-08 15:09
 * @Description:  金蝶service
 */
@Service("iProductService")
public interface IProductService {
    //物资
    ResponseResult productSave(String acctId, String dataCentreUserName, String dataCentrePassword, String fname, Integer id, Boolean isautosubmitandaudit);
    ResponseResult productDelete(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids);
    ResponseResult productView(String acctId,String dataCentreUserName,String dataCentrePassword,String number, String id);
    ResponseResult productSubimt(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids);
    ResponseResult productAudit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids);
    ResponseResult productUnAudit(String acctId,String dataCentreUserName,String dataCentrePassword,String numbers, String ids);
    ResponseResult productAllocate(String acctId, String dataCentreUserName, String dataCentrePassword,String PkIds, String TOrgIds);
    ResponseResult batchAdd(String acctId, String dataCentreUserName, String dataCentrePassword, String productArr);

}
