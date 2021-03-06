package com.lnmj.product.business;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.product.entity.ProductNursing;
import com.lnmj.product.entity.VO.ProductVO;
import com.lnmj.product.entity.VO.ServiceProductVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;


@Service("iProductService")
public interface IProductService {
    ResponseResult selectProductById(Long productId);

    ResponseResult selectProductByCode(String productCode);

    ResponseResult selectServiceProductByCode(String productCode);

    ResponseResult selectProductAndServiceList(Integer pageNum, Integer pageSize, String postType, String keyWord, String type, String productStatus, Integer status, Integer kindId, Integer brandId, Integer effectId, String companyId, String subsidiaryId, String checkedProductCode,String storeId,Long subClassId,Long companyType,Integer isHoutai);

    ResponseResult selectProductAndServiceListNoPage(String postType, String keyWord, String type, String productStatus, Integer status,Integer kindId, Integer brandId, Integer effectId,String checkedProductCode,Long companyId,Long companyType);

    ResponseResult insertProduct(ProductVO productvo) throws InvocationTargetException, IllegalAccessException;

    ResponseResult insertServiceProduct(ServiceProductVO serviceProductVO, ProductNursing productNursing) throws InvocationTargetException, IllegalAccessException;

    ResponseResult updateProduct(ProductVO productvo) throws InvocationTargetException, IllegalAccessException;

    ResponseResult updateProductStoreIds(ProductVO productvo);

    ResponseResult putOffOrOnProduct(String productCode, int productStatus, Integer type, Long companyType, Long companyId);

    ResponseResult deleteProduct(String id, String modifyOperator, Integer type) throws InvocationTargetException, IllegalAccessException;

    ResponseResult exportProduct(HttpServletRequest req, HttpServletResponse resp,Integer status, Integer productStatus, Integer type);

    ResponseResult deleteServiceProduct(Long serviceProductId, String modifyOperator);

    ResponseResult updateServiceProduct(ServiceProductVO serviceProductVO) throws InvocationTargetException, IllegalAccessException;

    ResponseResult updateServiceProductStoreIds(ServiceProductVO serviceProductVO);

    ResponseResult check(Map map);

    ResponseResult selectServiceVOList(int pageNum, int pageSize, String keyWordProductCode, String keyWordProductName);

    ResponseResult selectServiceVOListNoPage(String keyWordProductCode, String keyWordProductName);

    ResponseResult selectServiceListById(int pageNum,int pageSize,String serviceProductIds);

    ResponseResult selectPorductListById(int pageNum,int pageSize,String productIds);

    ResponseResult selectPorductListByIdNoPage(String productIds);

    ResponseResult selectProductAndServiceNameList(int pageNum, int pageSize);

    ResponseResult selectProductByCondition(Long storeId,Long subClassID, Long postId, Integer postLevel);

    ResponseResult selectDeliveryMethod();

    ResponseResult selectProductBySubClass(Long subClassID);

    ResponseResult selectAllProduct();

    ResponseResult selectProductTypeEnum();

    ResponseResult uploadPic(MultipartFile[] productFiles, MultipartFile[] activityProductFiles,MultipartFile file,String type);

    ResponseResult addProductSubcompany(String productCode,Long subCompanyId,Integer type) throws InvocationTargetException, IllegalAccessException;

    ResponseResult addProductStore(String productCode,Long storeId,Integer type);

    ResponseResult updateProductSales(String productCode, Integer type, String upOrDown);

    ResponseResult updateStockQuantity(String productCode,String upOrDown);


    ResponseResult batchAllocationSubCompany(String companyId,String companyType,String productArrayStr,String subcompanyIdArrayStr);

    ResponseResult batchAllocationStore(String companyId,String companyType,String productArrayStr,String storeIdArrayStr);

    ResponseResult batchCancelAllocationSubCompany(String companyId,String companyType,String productArrayStr,String subcompanyIdArrayStr);

    ResponseResult batchCancelAllocationStore(String companyId,String companyType,String productArrayStr,String storeIdArrayStr);

    Map uploadFuwenbenPic(MultipartFile file);

    ResponseResult calculation(String instoragePrice, String outstoragePrice, String outstorageProfit, String type, String productCode);
}
