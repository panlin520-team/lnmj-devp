package com.lnmj.product.business.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.common.Enum.DeliveryMethodEnum;
import com.lnmj.common.Enum.ProductStatusEnum;
import com.lnmj.common.Enum.ProductTypeEnum;
import com.lnmj.common.Enum.ResponseEnum.ImageTypeEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeProductEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeStockEnum;
import com.lnmj.common.baseController.ExportController;
import com.lnmj.common.baseController.FtpFileUploadController;
import com.lnmj.common.baseController.HttpServletRequestWarpper;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.common.utils.ArrayToString;
import com.lnmj.common.utils.CodeUtils;
import com.lnmj.common.utils.EnumUtil;
import com.lnmj.common.utils.ListPageUntil;
import com.lnmj.product.business.IProductService;
import com.lnmj.product.entity.*;
import com.lnmj.product.entity.VO.*;
import com.lnmj.product.repository.*;
import com.lnmj.product.serviceProxy.DataApi;
import com.lnmj.product.serviceProxy.K3CLOUDApi;
import com.lnmj.product.serviceProxy.StoreApi;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.ELState;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @Author renqingyun
 * @Date: 2019/5/9 18:12
 * @Description:
 */
@Transactional
@Service("productService")
public class ProductService implements IProductService {

    @Resource
    private IProductDao productDao;
    @Resource
    private IProductPromotionTypeDao productPromotionDao;
    @Resource
    private FtpFileUploadController ftpFileUploadController;
    @Resource
    private IProductImageDao productImageDao;
    @Resource
    private IProductActivityImageDao productActivityImageDao;
    @Resource
    private IServiceProductDao serviceProductDao;
    @Resource
    private ExportController exportController;
    @Resource
    private StoreApi storeApi;
    @Resource
    private DataApi dataApi;
    @Resource
    private IStockDao iStockDao;
    @Resource
    private IStockProductDao iStockProductDao;
    @Resource
    private K3CLOUDApi k3CLOUDApi;
    @Resource
    private IProductKindDao iProductKindDao;
    @Resource
    private IProductCategoryDao iProductCategoryDao;
    @Resource
    private IProductBrandDao iProductBrandDao;
    @Resource
    private IProductEffectDao iProductEffectDao;
    @Resource
    private IUnitDao iUnitDao;

    @Override
    public ResponseResult selectProductById(Long productId) {
        Product product = productDao.selectById(productId);
        String imageList = product.getImageList();
        String activityImageList = product.getActivityImageList();
        if (StringUtils.isNotBlank(imageList)) {
            String[] imagearr = imageList.split(",");
            List<Long> imageIdList = new ArrayList();
            List<ProductImage> productImageList = null;
            for (String id : imagearr) {
                imageIdList.add(Long.parseLong(id));
            }
            Map<String, Object> mapids = new HashMap();
            mapids.put("ids", imageIdList);
            mapids.put("imageType", ImageTypeEnum.PRODUCT.getCode());
            productImageList = productImageDao.selectProductImageByIds(mapids);
            product.setProductImageList(productImageList);
        }

        if (StringUtils.isNotBlank(activityImageList)) {
            String[] imagearr = activityImageList.split(",");
            List<Long> imageIdList = new ArrayList();
            List<ProductActivityImage> productImageList = null;
            for (String id : imagearr) {
                imageIdList.add(Long.parseLong(id));
            }
            Map<String, Object> mapids = new HashMap();
            mapids.put("ids", imageIdList);
            mapids.put("imageType", ImageTypeEnum.PRODUCTACTIVITYIMAGE.getCode());
            productImageList = productImageDao.selectProductActivityImageByIds(mapids);
            product.setProductActivityImageList(productImageList);
        }


        if (product != null) {
            return ResponseResult.success(product);
        }
        return ResponseResult.error(new Error(ResponseCodeProductEnum.PRODUCT_NOT_FOUND.getCode(), ResponseCodeProductEnum.PRODUCT_NOT_FOUND.getDesc()));
    }

    @Override
    public ResponseResult selectProductByCode(String productCode) {
        Product product = productDao.selectProductByCode(productCode);
        return ResponseResult.success(product);
    }

    @Override
    public ResponseResult selectServiceProductByCode(String productCode) {
        ServiceProduct serviceProduct = serviceProductDao.selectProductByCode(productCode);
        return ResponseResult.success(serviceProduct);
    }


    @Override
    public ResponseResult selectProductAndServiceList(Integer pageNum, Integer pageSize, String postType, String keyWord, String type, String productStatus, Integer status, Integer kindId, Integer brandId, Integer effectId, String companyId, String subsidiaryId, String checkedProductCode,
                                                      String storeId, Long subClassId, Long companyType, Integer isHoutai) {
        /* Page p = PageHelper.startPage(pageNum, pageSize);*/
        if (keyWord == "") {
            keyWord = null;
        }
        if (null == kindId || kindId == 0) {
            kindId = null;
        }
        if (null == brandId || brandId == 0) {
            brandId = null;
        }
        if (null == effectId || effectId == 0) {
            effectId = null;
        }
        Map map = new HashMap();
        map.put("kindId", kindId);
        map.put("brandId", brandId);
        map.put("effectId", effectId);
        map.put("keyWord", keyWord);
        map.put("status", status);
        //获取总公司id
        if (companyType == 1) {
            //如果是总公司
            map.put("companyId", companyId);
        } else if (companyType == 2) {
            //先搜索他的总公司
            Map mapSubcomapny = (Map) storeApi.selectSubsidiaryByID(Long.parseLong(companyId)).getResult();
            map.put("companyId", mapSubcomapny.get("parentCompany").toString());
        } else if (companyType == 3) {
            //先搜索他的子公司 - 门店查看子公司
            Long storeIdIn = Long.parseLong(companyId);
            Map mapStore = (Map) storeApi.selectStoreById(storeIdIn).getResult();
            Map mapSubcomapny = (Map) storeApi.selectSubsidiaryByID(Long.parseLong(mapStore.get("subCompanyId").toString())).getResult();
            map.put("companyId", mapSubcomapny.get("parentCompany").toString());

        }

        if (StringUtils.isNotBlank(productStatus)) {
            if (Integer.parseInt(productStatus) == ProductStatusEnum.ON.getCode()) {
                //已上架
                map.put("productStatus", ProductStatusEnum.ON.getCode());
            } else if (Integer.parseInt(productStatus) == ProductStatusEnum.DOWN.getCode()) {
                //已下架
                map.put("productStatus", ProductStatusEnum.DOWN.getCode());
            } else if (Integer.parseInt(productStatus) == ProductStatusEnum.DELETE.getCode()) {
                //已删除
                map.put("productStatus", ProductStatusEnum.DELETE.getCode());
            }
        }
        List<Product> result = null;
        List<ServiceProduct> resultService = null;
        if (type != null && ProductTypeEnum.PRODUCT.getCode() == Integer.parseInt(type)) {
            //查询产品
            if (companyType == 1) {
                //如果是总公司
                result = productDao.selectProductList(map);
            } else if (companyType == 2) {
                //如果是子公司，还要匹配授权中间表
                Map map1 = new HashMap();
                map1.put("subcompanyId", companyId);
                if (productStatus.equals("1")) {//如果是上架
                    map1.put("upOrDown", 1);
                } else if (productStatus.equals("2")) {//如果是下架
                    map1.put("upOrDown", 0);
                }
                map1.put("companyId", map.get("companyId"));
                map1.put("keyWord", keyWord);
                result = productDao.selectProductSubcompany(map1);
            } else if (companyType == 3) {
                //如果是分公司，还要匹配授权中间表
                Map map1 = new HashMap();
                map1.put("storeId", companyId);
                map1.put("subClassID", subClassId);
                if (productStatus.equals("1")) {//如果是上架
                    map1.put("upOrDown", 1);
                } else if (productStatus.equals("2")) {//如果是下架
                    map1.put("upOrDown", 0);
                }
                map1.put("companyId", map.get("companyId"));
                map1.put("keyWord", keyWord);
                result = productDao.selectProductStore(map1);
            }

            //匹配关联数据
            List<ProductKind> productKindList = iProductKindDao.selectProductKindList();
            List<ProductCategory> productCategoryList = iProductCategoryDao.selectProductCategoryList();
            List<ProductBrand> productBrandList = iProductBrandDao.selectProductBrandList();
            List<ProductEffect> productEffectList = iProductEffectDao.selectProductEffectList();
            List<Unit> unitList = iUnitDao.selectUnitList(new HashMap());
            List<Map> industryMapList = (List<Map>) storeApi.selectListIndustryNoPage().getResult();
            List<Map> commodityTypeMapList = (List<Map>) dataApi.selectCommodityTypeListNoPage().getResult();
            List<Map> subclassMapList = (List<Map>) dataApi.selectSubclassListNoPage().getResult();
            List<Map> performancePostMapList = (List<Map>) dataApi.selectPerformancePostListAll().getResult();
            List<Map> performanceMapList = (List<Map>) dataApi.selectPerformanceListNoPage().getResult();

            for (Product product : result) {
                for (ProductKind productKind : productKindList) {
                    if (product.getProductKind()!=null&&product.getProductKind().toString().equals(productKind.getProductKindId().toString())) {
                        product.setProductKindName(productKind.getProductKindName());
                    }
                }
                for (ProductCategory productCategory : productCategoryList) {
                    if (product.getProductCategory() != null && product.getProductCategory().toString().equals(productCategory.getProductCategoryId().toString())) {
                        product.setProductCategoryName(productCategory.getProductCategoryName());
                    }
                }
                for (ProductBrand productBrand : productBrandList) {
                    if (product.getProductBrand() != null && product.getProductBrand().toString().equals(productBrand.getProductBrandId().toString())) {
                        product.setProductBrandName(productBrand.getProductBrandName());
                    }
                }
                for (ProductEffect productEffect : productEffectList) {
                    if (product.getProductEffect() != null && product.getProductEffect().toString().equals(productEffect.getProductEffectId().toString())) {
                        product.setProductEffectName(productEffect.getProductEffectName());
                    }
                }
                for (Unit unit : unitList) {
                    if (product.getUnitId() != null && product.getUnitId().toString().equals(unit.getUnitId().toString())) {
                        product.setProductUnitName(unit.getUnitName());
                    }
                }
                if (industryMapList != null && product.getIndustryId() != null) {
                    for (Map map1 : industryMapList) {
                        if (product.getIndustryId().toString().equals(map1.get("industryID").toString())) {
                            product.setProductIndustryName(map1.get("industryName").toString());
                        }
                    }
                }
                if (commodityTypeMapList != null && product.getCommodityTypeID() != null) {
                    for (Map map1 : commodityTypeMapList) {
                        if (product.getCommodityTypeID().toString().equals(map1.get("commodityTypeID").toString())) {
                            product.setProductCommoditytypeName(map1.get("commodityTypeName").toString());
                        }
                    }
                }
                if (subclassMapList != null && product.getSubClassID() != null) {
                    for (Map map1 : subclassMapList) {
                        if (product.getSubClassID().toString().equals(map1.get("subclassID").toString())) {
                            product.setProductSubclassName(map1.get("subclassName").toString());
                        }
                    }
                }
                if (performancePostMapList != null && product.getAchievementPostId() != null) {
                    for (Map map1 : performancePostMapList) {
                        if (product.getAchievementPostId().toString().equals(map1.get("id").toString())) {
                            product.setProductPerformancePostName(map1.get("achievementPostName").toString());
                        }
                    }
                }

                if (performanceMapList != null && product.getAchievementId() != null) {
                    for (Map map1 : performanceMapList) {
                        if (product.getAchievementId().toString().equals(map1.get("achievementID").toString())) {
                            product.setAchievementName(map1.get("achievementName").toString());
                        }
                    }
                }
            }

            //获取图片
            for (Product product : result) {
                //处理商品图片
                String imageList = product.getImageList();
                if (StringUtils.isNotBlank(imageList)) {
                    String[] imagearr = imageList.split(",");
                    List<Long> imageIdList = new ArrayList(Arrays.asList(imagearr));
                    map.put("ids", imageIdList);
                    map.put("imageType", ImageTypeEnum.PRODUCT.getCode());
                    List<ProductImage> productImageList = productImageDao.selectProductImageByIds(map);
                    product.setProductImageList(productImageList);
                }

                //处理商品活动图片
                String activityImageList = product.getActivityImageList();
                if (StringUtils.isNotBlank(activityImageList)) {
                    String[] imagearr = activityImageList.split(",");
                    List<Long> activeimageIdList = new ArrayList(Arrays.asList(imagearr));
                    map.put("ids", activeimageIdList);
                    map.put("imageType", ImageTypeEnum.PRODUCTACTIVITYIMAGE.getCode());
                    List<ProductActivityImage> activityproductImageList = productActivityImageDao.selectProductActivityImageByIds(map);
                    product.setProductActivityImageList(activityproductImageList);
                }
            }

            //查看库存数量
            //查找库存
            if (isHoutai == 0) {
                //如果不是后台
                if (companyType != 0) {
                    Stock stock = new Stock();
                    stock.setCompanyType(companyType);
                    stock.setCompanyId(Long.parseLong(companyId));
                    List<Stock> stockList = iStockDao.selectStockByID(stock);
                    List<StockProduct> stockProductList = null;
                    if (stockList.size() == 0) {
                        return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCK_NOT_FIND.getCode(), ResponseCodeStockEnum.STOCK_NOT_FIND.getDesc()));
                    } else {
                        stockProductList = iStockProductDao.selectStockProductByStockID(stockList.get(0).getStockCode());
                    }
                    for (Product product : result) {
                        Integer stockNum = 0;
                        for (StockProduct stockProduct : stockProductList) {
                            if (product.getProductCode().equals(stockProduct.getProductCode())) {
                                stockNum = stockNum + stockProduct.getAveailableNumber();
                            }
                        }
                        product.setStockNum(product.getStockQuantity() + stockNum);
                        product.setProductType(ProductTypeEnum.PRODUCT.getCode());
                    }
                }
            }
            if (result != null && result.size() > 0) {
                Map map1 = new HashMap();
                Map mapRsultPage = ListPageUntil.listPage(result, pageSize, pageNum);
                map1.put("list", mapRsultPage.get("list"));
                map1.put("total", mapRsultPage.get("total"));
                return ResponseResult.success(map1);
            }
        } else if (type != null && ProductTypeEnum.SERVICE.getCode() == Integer.parseInt(type)) {
            //查询服务
            map.put("subClassID", subClassId);
            if (companyType == 1) {
                //如果是总公司
                resultService = serviceProductDao.selectProductList(map);
            } else if (companyType == 2) {
                //如果是子公司，还要匹配授权中间表
                Map map1 = new HashMap();
                map1.put("subcompanyId", companyId);
                if (productStatus.equals("1")) {//如果是上架
                    map1.put("upOrDown", 1);
                } else if (productStatus.equals("2")) {//如果是下架
                    map1.put("upOrDown", 0);
                }
                map1.put("companyId", map.get("companyId"));
                map1.put("keyWord", keyWord);
                resultService = serviceProductDao.selectServiceProductSubcompany(map1);

            } else if (companyType == 3) {
                //如果是分公司，还要匹配授权中间表
                Map map1 = new HashMap();
                map1.put("storeId", companyId);
                map1.put("subClassID", subClassId);
                if (productStatus.equals("1")) {//如果是上架
                    map1.put("upOrDown", 1);
                } else if (productStatus.equals("2")) {//如果是下架
                    map1.put("upOrDown", 0);
                }
                map1.put("companyId", map.get("companyId"));
                map1.put("keyWord", keyWord);
                resultService = serviceProductDao.selectServiceProductStore(map1);
            }

            //匹配关联数据
            List<ProductKind> productKindList = iProductKindDao.selectProductKindList();
            List<ProductCategory> productCategoryList = iProductCategoryDao.selectProductCategoryList();
            List<ProductBrand> productBrandList = iProductBrandDao.selectProductBrandList();
            List<ProductEffect> productEffectList = iProductEffectDao.selectProductEffectList();
            List<Unit> unitList = iUnitDao.selectUnitList(new HashMap());
            List<Map> industryMapList = (List<Map>) storeApi.selectListIndustryNoPage().getResult();
            List<Map> commodityTypeMapList = (List<Map>) dataApi.selectCommodityTypeListNoPage().getResult();
            List<Map> subclassMapList = (List<Map>) dataApi.selectSubclassListNoPage().getResult();
            for (ServiceProduct product : resultService) {
                for (ProductKind productKind : productKindList) {
                    if (product.getProductKind()!=null&&product.getProductKind().toString().equals(productKind.getProductKindId().toString())) {
                        product.setProductKindName(productKind.getProductKindName());
                    }
                }
                for (ProductCategory productCategory : productCategoryList) {
                    if (product.getProductCategory() != null && product.getProductCategory().toString().equals(productCategory.getProductCategoryId().toString())) {
                        product.setProductCategoryName(productCategory.getProductCategoryName());
                    }
                }
                for (ProductBrand productBrand : productBrandList) {
                    if (product.getProductBrand() != null && product.getProductBrand().toString().equals(productBrand.getProductBrandId().toString())) {
                        product.setProductBrandName(productBrand.getProductBrandName());
                    }
                }
                for (ProductEffect productEffect : productEffectList) {
                    if (product.getProductEffect() != null && product.getProductEffect().toString().equals(productEffect.getProductEffectId().toString())) {
                        product.setProductEffectName(productEffect.getProductEffectName());
                    }
                }
                for (Unit unit : unitList) {
                    if (product.getUnitId() != null && product.getUnitId().toString().equals(unit.getUnitId().toString())) {
                        product.setProductUnitName(unit.getUnitName());
                    }
                }
                if (industryMapList != null && product.getIndustryId() != null) {
                    for (Map map1 : industryMapList) {
                        if (product.getIndustryId().toString().equals(map1.get("industryID").toString())) {
                            product.setProductIndustryName(map1.get("industryName").toString());
                        }
                    }
                }
                if (commodityTypeMapList != null && product.getCommodityTypeID() != null) {
                    for (Map map1 : commodityTypeMapList) {
                        if (product.getCommodityTypeID().toString().equals(map1.get("commodityTypeID").toString())) {
                            product.setProductCommoditytypeName(map1.get("commodityTypeName").toString());
                        }
                    }
                }
                if (subclassMapList != null && product.getSubClassID() != null) {
                    for (Map map1 : subclassMapList) {
                        if (product.getSubClassID().toString().equals(map1.get("subclassID").toString())) {
                            product.setProductSubclassName(map1.get("subclassName").toString());
                        }
                    }
                }

            }

            //获取图片
            for (ServiceProduct serviceProduct : resultService) {
                if (StringUtils.equals(serviceProduct.getProductCode(), checkedProductCode)) {
                    serviceProduct.setIsChecked(1);
                }
                String imageList = serviceProduct.getImageList();
                if (StringUtils.isNotBlank(imageList)) {
                    String[] imagearr = imageList.split(",");
                    List<Long> imageIdList = new ArrayList(Arrays.asList(imagearr));
                    map.put("ids", imageIdList);
                    map.put("imageType", ImageTypeEnum.SERVICEPRODUCT.getCode());
                    List<ProductImage> productImageList = productImageDao.selectProductImageByIds(map);
                    serviceProduct.setServiceProductImageList(productImageList);
                }

                //处理商品活动图片
                String activityImageList = serviceProduct.getActivityImageList();
                if (StringUtils.isNotBlank(activityImageList)) {
                    String[] imagearr = activityImageList.split(",");
                    List<Long> activeimageIdList = new ArrayList(Arrays.asList(imagearr));
                    map.put("ids", activeimageIdList);
                    map.put("imageType", ImageTypeEnum.SERVICEPRODUCTACTIVITYIMAGE.getCode());
                    List<ProductActivityImage> activityproductImageList = productActivityImageDao.selectProductActivityImageByIds(map);
                    serviceProduct.setServiceProductActivityImageList(activityproductImageList);
                }
            }
            if (isHoutai == 0) {
                if (companyType != 0) {
                    //查看库存数量
                    //查找库存
                    Stock stock = new Stock();
                    stock.setCompanyType(companyType);
                    stock.setCompanyId(Long.parseLong(companyId));
                    List<Stock> stockList = iStockDao.selectStockByID(stock);
                    List<StockProduct> stockProductList = null;
                    if (stockList.size() == 0) {
                        return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCK_NOT_FIND.getCode(), ResponseCodeStockEnum.STOCK_NOT_FIND.getDesc()));
                    } else {
                        stockProductList = iStockProductDao.selectStockProductByStockID(stockList.get(0).getStockCode());
                    }
                    for (ServiceProduct serviceProduct : resultService) {
                        Integer stockNum = 0;
                        for (StockProduct stockProduct : stockProductList) {
                            if (serviceProduct.getProductCode().equals(stockProduct.getProductCode())) {
                                stockNum = stockNum + stockProduct.getAveailableNumber();
                            }
                        }
                        serviceProduct.setStockNum(stockNum);
                        serviceProduct.setProductType(ProductTypeEnum.SERVICE.getCode());
                    }
                }
            }
            if (resultService != null && resultService.size() > 0) {
                Map map1 = new HashMap();
                Map mapRsultPage = ListPageUntil.listPage(resultService, pageSize, pageNum);
                map1.put("list", mapRsultPage.get("list"));
                map1.put("total", mapRsultPage.get("total"));
                return ResponseResult.success(map1);
            }
        } else if (type == null) {
            map.put("keyWord", keyWord);
            map.put("subClassID", subClassId);
            //查询产品
            if (companyType == 1) {
                //如果是总公司
                result = productDao.selectProductList(map);
            } else if (companyType == 2) {
                //如果是子公司，还要匹配授权中间表
                Map map1 = new HashMap();
                map1.put("subcompanyId", companyId);
                if (productStatus.equals("1")) {//如果是上架
                    map1.put("upOrDown", 1);
                } else if (productStatus.equals("2")) {//如果是下架
                    map1.put("upOrDown", 0);
                }
                map1.put("companyId", map.get("companyId"));
                result = productDao.selectProductSubcompany(map1);
            } else if (companyType == 3) {
                //如果是分公司，还要匹配授权中间表
                Map map1 = new HashMap();
                map1.put("storeId", companyId);
                map1.put("subClassID", subClassId);
                if (productStatus.equals("1")) {//如果是上架
                    map1.put("upOrDown", 1);
                } else if (productStatus.equals("2")) {//如果是下架
                    map1.put("upOrDown", 0);
                }
                map1.put("companyId", map.get("companyId"));
                map1.put("keyWord", keyWord);
                result = productDao.selectProductStore(map1);

            }
            //匹配关联数据
            List<ProductKind> productKindList = iProductKindDao.selectProductKindList();
            List<ProductCategory> productCategoryList = iProductCategoryDao.selectProductCategoryList();
            List<ProductBrand> productBrandList = iProductBrandDao.selectProductBrandList();
            List<ProductEffect> productEffectList = iProductEffectDao.selectProductEffectList();
            List<Unit> unitList = iUnitDao.selectUnitList(new HashMap());
            List<Map> industryMapList = (List<Map>) storeApi.selectListIndustryNoPage().getResult();
            List<Map> commodityTypeMapList = (List<Map>) dataApi.selectCommodityTypeListNoPage().getResult();
            List<Map> subclassMapList = (List<Map>) dataApi.selectSubclassListNoPage().getResult();
            List<Map> performancePostMapList = (List<Map>) dataApi.selectPerformancePostListAll().getResult();
            List<Map> performanceMapList = (List<Map>) dataApi.selectPerformanceListNoPage().getResult();
            for (Product product : result) {
                for (ProductKind productKind : productKindList) {
                    if (product.getProductKind()!=null&&product.getProductKind().toString().equals(productKind.getProductKindId().toString())) {
                        product.setProductKindName(productKind.getProductKindName());
                    }
                }
                for (ProductCategory productCategory : productCategoryList) {
                    if (product.getProductCategory() != null && product.getProductCategory().toString().equals(productCategory.getProductCategoryId().toString())) {
                        product.setProductCategoryName(productCategory.getProductCategoryName());
                    }
                }
                for (ProductBrand productBrand : productBrandList) {
                    if (product.getProductBrand() != null && product.getProductBrand().toString().equals(productBrand.getProductBrandId().toString())) {
                        product.setProductBrandName(productBrand.getProductBrandName());
                    }
                }
                for (ProductEffect productEffect : productEffectList) {
                    if (product.getProductEffect() != null && product.getProductEffect().toString().equals(productEffect.getProductEffectId().toString())) {
                        product.setProductEffectName(productEffect.getProductEffectName());
                    }
                }
                for (Unit unit : unitList) {
                    if (product.getUnitId() != null && product.getUnitId().toString().equals(unit.getUnitId().toString())) {
                        product.setProductUnitName(unit.getUnitName());
                    }
                }
                if (industryMapList != null && product.getIndustryId() != null) {
                    for (Map map1 : industryMapList) {
                        if (product.getIndustryId().toString().equals(map1.get("industryID").toString())) {
                            product.setProductIndustryName(map1.get("industryName").toString());
                        }
                    }
                }
                if (commodityTypeMapList != null && product.getCommodityTypeID() != null) {
                    for (Map map1 : commodityTypeMapList) {
                        if (product.getCommodityTypeID().toString().equals(map1.get("commodityTypeID").toString())) {
                            product.setProductCommoditytypeName(map1.get("commodityTypeName").toString());
                        }
                    }
                }
                if (subclassMapList != null && product.getSubClassID() != null) {
                    for (Map map1 : subclassMapList) {
                        if (product.getSubClassID().toString().equals(map1.get("subclassID").toString())) {
                            product.setProductSubclassName(map1.get("subclassName").toString());
                        }
                    }
                }
                if (performancePostMapList != null && product.getAchievementPostId() != null) {
                    for (Map map1 : performancePostMapList) {
                        if (product.getAchievementPostId().toString().equals(map1.get("id").toString())) {
                            product.setProductPerformancePostName(map1.get("achievementPostName").toString());
                        }
                    }
                }

                if (performanceMapList != null && product.getAchievementId() != null) {
                    for (Map map1 : performanceMapList) {
                        if (product.getAchievementId().toString().equals(map1.get("achievementID").toString())) {
                            product.setAchievementName(map1.get("achievementName").toString());
                        }
                    }
                }
            }


            //获取图片
            for (Product product : result) {
                //处理商品图片
                String imageList = product.getImageList();
                if (StringUtils.isNotBlank(imageList)) {
                    String[] imagearr = imageList.split(",");
                    List<Long> imageIdList = new ArrayList(Arrays.asList(imagearr));
                    map.put("ids", imageIdList);
                    map.put("imageType", ImageTypeEnum.PRODUCT.getCode());
                    List<ProductImage> productImageList = productImageDao.selectProductImageByIds(map);
                    product.setProductImageList(productImageList);
                }

                //处理商品活动图片
                String activityImageList = product.getActivityImageList();
                if (StringUtils.isNotBlank(activityImageList)) {
                    String[] imagearr = activityImageList.split(",");
                    List<Long> activeimageIdList = new ArrayList(Arrays.asList(imagearr));
                    map.put("ids", activeimageIdList);
                    map.put("imageType", ImageTypeEnum.PRODUCTACTIVITYIMAGE.getCode());
                    List<ProductActivityImage> activityproductImageList = productActivityImageDao.selectProductActivityImageByIds(map);
                    product.setProductActivityImageList(activityproductImageList);
                }
            }

            //查看库存数量
            //查找库存
            if (isHoutai == 0) {
                //如果不是后台
                if (companyType != 0) {
                    Stock stock = new Stock();
                    stock.setCompanyType(companyType);
                    stock.setCompanyId(Long.parseLong(companyId));
                    List<Stock> stockList = iStockDao.selectStockByID(stock);
                    List<StockProduct> stockProductList = null;
                    if (stockList.size() == 0) {
                        return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCK_NOT_FIND.getCode(), ResponseCodeStockEnum.STOCK_NOT_FIND.getDesc()));
                    } else {
                        stockProductList = iStockProductDao.selectStockProductByStockID(stockList.get(0).getStockCode());
                    }
                    for (Product product : result) {
                        Integer stockNum = 0;
                        for (StockProduct stockProduct : stockProductList) {
                            if (product.getProductCode().equals(stockProduct.getProductCode())) {
                                stockNum = stockNum + stockProduct.getAveailableNumber();
                            }
                        }
                        product.setStockNum(stockNum + product.getStockQuantity());
                        product.setProductType(ProductTypeEnum.PRODUCT.getCode());
                    }
                }
            }


            //查询服务
            map.put("subClassID", subClassId);
            map.put("keyWord", keyWord);
            if (companyType == 1) {
                //如果是总公司
                resultService = serviceProductDao.selectProductList(map);
            } else if (companyType == 2) {
                //如果是子公司，还要匹配授权中间表
                Map map1 = new HashMap();
                map1.put("subcompanyId", companyId);
                if (productStatus.equals("1")) {//如果是上架
                    map1.put("upOrDown", 1);
                } else if (productStatus.equals("2")) {//如果是下架
                    map1.put("upOrDown", 0);
                }
                map1.put("companyId", map.get("companyId"));
                map1.put("keyWord", keyWord);
                resultService = serviceProductDao.selectServiceProductSubcompany(map1);


            } else if (companyType == 3) {
                //如果是分公司，还要匹配授权中间表
                Map map1 = new HashMap();
                map1.put("storeId", companyId);
                map1.put("subClassID", subClassId);
                if (productStatus.equals("1")) {//如果是上架
                    map1.put("upOrDown", 1);
                } else if (productStatus.equals("2")) {//如果是下架
                    map1.put("upOrDown", 0);
                }
                map1.put("companyId", map.get("companyId"));
                map1.put("keyWord", keyWord);
                resultService = serviceProductDao.selectServiceProductStore(map1);
            }


            //匹配关联数据
            for (ServiceProduct product : resultService) {
                for (ProductKind productKind : productKindList) {
                    if (product.getProductKind()!=null&&product.getProductKind().toString().equals(productKind.getProductKindId().toString())) {
                        product.setProductKindName(productKind.getProductKindName());
                    }
                }
                for (ProductCategory productCategory : productCategoryList) {
                    if (product.getProductCategory() != null && product.getProductCategory().toString().equals(productCategory.getProductCategoryId().toString())) {
                        product.setProductCategoryName(productCategory.getProductCategoryName());
                    }
                }
                for (ProductBrand productBrand : productBrandList) {
                    if (product.getProductBrand() != null && product.getProductBrand().toString().equals(productBrand.getProductBrandId().toString())) {
                        product.setProductBrandName(productBrand.getProductBrandName());
                    }
                }
                for (ProductEffect productEffect : productEffectList) {
                    if (product.getProductEffect() != null && product.getProductEffect().toString().equals(productEffect.getProductEffectId().toString())) {
                        product.setProductEffectName(productEffect.getProductEffectName());
                    }
                }
                for (Unit unit : unitList) {
                    if (product.getUnitId() != null && product.getUnitId().toString().equals(unit.getUnitId().toString())) {
                        product.setProductUnitName(unit.getUnitName());
                    }
                }
                if (industryMapList != null && product.getIndustryId() != null) {
                    for (Map map1 : industryMapList) {
                        if (product.getIndustryId().toString().equals(map1.get("industryID").toString())) {
                            product.setProductIndustryName(map1.get("industryName").toString());
                        }
                    }
                }
                if (commodityTypeMapList != null && product.getCommodityTypeID() != null) {
                    for (Map map1 : commodityTypeMapList) {
                        if (product.getCommodityTypeID().toString().equals(map1.get("commodityTypeID").toString())) {
                            product.setProductCommoditytypeName(map1.get("commodityTypeName").toString());
                        }
                    }
                }
                if (subclassMapList != null && product.getSubClassID() != null) {
                    for (Map map1 : subclassMapList) {
                        if (product.getSubClassID().toString().equals(map1.get("subclassID").toString())) {
                            product.setProductSubclassName(map1.get("subclassName").toString());
                        }
                    }
                }

            }


            //获取图片
            for (ServiceProduct serviceProduct : resultService) {
                if (StringUtils.equals(serviceProduct.getProductCode(), checkedProductCode)) {
                    serviceProduct.setIsChecked(1);
                }
                String imageList = serviceProduct.getImageList();
                if (StringUtils.isNotBlank(imageList)) {
                    String[] imagearr = imageList.split(",");
                    List<Long> imageIdList = new ArrayList(Arrays.asList(imagearr));
                    map.put("ids", imageIdList);
                    map.put("imageType", ImageTypeEnum.SERVICEPRODUCT.getCode());
                    List<ProductImage> productImageList = productImageDao.selectProductImageByIds(map);
                    serviceProduct.setServiceProductImageList(productImageList);
                }

                //处理商品活动图片
                String activityImageList = serviceProduct.getActivityImageList();
                if (StringUtils.isNotBlank(activityImageList)) {
                    String[] imagearr = activityImageList.split(",");
                    List<Long> activeimageIdList = new ArrayList(Arrays.asList(imagearr));
                    map.put("ids", activeimageIdList);
                    map.put("imageType", ImageTypeEnum.SERVICEPRODUCTACTIVITYIMAGE.getCode());
                    List<ProductActivityImage> activityproductImageList = productActivityImageDao.selectProductActivityImageByIds(map);
                    serviceProduct.setServiceProductActivityImageList(activityproductImageList);
                }
            }
            if (isHoutai == 0) {
                if (companyType != 0) {
                    //查看库存数量
                    //查找库存
                    Stock stock = new Stock();
                    stock.setCompanyType(companyType);
                    stock.setCompanyId(Long.parseLong(companyId));
                    List<Stock> stockList = iStockDao.selectStockByID(stock);
                    List<StockProduct> stockProductList = null;
                    if (stockList.size() == 0) {
                        return ResponseResult.error(new Error(ResponseCodeStockEnum.STOCK_NOT_FIND.getCode(), ResponseCodeStockEnum.STOCK_NOT_FIND.getDesc()));
                    } else {
                        stockProductList = iStockProductDao.selectStockProductByStockID(stockList.get(0).getStockCode());
                    }
                    for (ServiceProduct serviceProduct : resultService) {
                        Integer stockNum = 0;
                        for (StockProduct stockProduct : stockProductList) {
                            if (serviceProduct.getProductCode().equals(stockProduct.getProductCode())) {
                                stockNum = stockNum + stockProduct.getAveailableNumber();
                            }
                        }
                        serviceProduct.setStockNum(stockNum);
                        serviceProduct.setProductType(ProductTypeEnum.SERVICE.getCode());
                    }
                }
            }

            ProductAndServiceNameVO vo = new ProductAndServiceNameVO();
            vo.setProductList(result);
            vo.setProductServiceList(resultService);

            List<AllProductVO> allProductVOList = new ArrayList<>();


            for (Product product : result) {
                AllProductVO allProductVOItem = new AllProductVO();
                allProductVOItem.setProductCode(product.getProductCode());
                allProductVOItem.setRetailPrice(product.getRetailPrice());
                allProductVOItem.setProductName(product.getProductName());
                allProductVOItem.setStockNum(product.getStockNum());
                allProductVOItem.setUnitId(product.getUnitId());
                allProductVOItem.setProductType(product.getProductType());
                allProductVOItem.setProductSpecification(product.getProductSpecification());
                allProductVOItem.setOutstorageProfit(product.getOutstorageProfit());
                allProductVOItem.setInstoragePrice(product.getInstoragePrice());
                allProductVOItem.setOutstoragePrice(product.getOutstoragePrice());
                allProductVOItem.setSubClassId(product.getSubClassID());
                allProductVOItem.setProductSubordinate(product.getProductSubordinate());
                allProductVOList.add(allProductVOItem);
            }

            for (ServiceProduct serviceProduct : resultService) {
                AllProductVO allProductVOItem = new AllProductVO();
                allProductVOItem.setProductCode(serviceProduct.getProductCode());
                allProductVOItem.setRetailPrice(serviceProduct.getRetailPrice());
                allProductVOItem.setProductName(serviceProduct.getProductName());
                allProductVOItem.setStockNum(serviceProduct.getStockNum());
                allProductVOItem.setUnitId(serviceProduct.getUnitId());
                allProductVOItem.setProductType(serviceProduct.getProductType());
                allProductVOItem.setProductSpecification("");
                allProductVOItem.setOutstorageProfit(serviceProduct.getOutstorageProfit());
                allProductVOItem.setInstoragePrice(serviceProduct.getInstoragePrice());
                allProductVOItem.setOutstoragePrice(serviceProduct.getOutstoragePrice());
                allProductVOItem.setSubClassId(serviceProduct.getSubClassID());
                allProductVOItem.setProductSubordinate("0");
                allProductVOList.add(allProductVOItem);
            }

            Map map1 = new HashMap();
            Map mapRsultPage = ListPageUntil.listPage(allProductVOList, pageSize, pageNum);
            map1.put("list", mapRsultPage.get("list"));
            map1.put("total", mapRsultPage.get("total"));
            return ResponseResult.success(map1);
        }

        Map map1 = new HashMap();
        map1.put("list", new ArrayList<>());
        map1.put("total", 0);
        return ResponseResult.success(map1);
    }

    @Override
    public ResponseResult selectProductAndServiceListNoPage(String postType, String keyWord, String type, String productStatus, Integer status, Integer kindId, Integer brandId, Integer effectId, String checkedProductCode, Long companyId, Long companyType) {
        Map map = new HashMap();
        map.put("keyWord", keyWord);
        map.put("status", status);
        map.put("postType", postType);
        if (null == kindId || kindId == 0) {
            kindId = null;
        }
        if (null == brandId || brandId == 0) {
            brandId = null;
        }
        if (null == effectId || effectId == 0) {
            effectId = null;
        }
        map.put("kindId", kindId);
        map.put("brandId", brandId);
        map.put("effectId", effectId);
        if (companyType == 1) {
            map.put("companyId", companyId);
        } else if (companyType == 2) {
            //先搜索他的总公司  - 子公司查看总公司
            Map mapSubcomapny = (Map) storeApi.selectSubsidiaryByID(companyId).getResult();
            map.put("companyId", mapSubcomapny.get("parentCompany").toString());
        } else if (companyType == 3) {
            //先搜索他的子公司 - 门店查看子公司 - 再找总公司
            Long storeId = companyId;
            Map mapStore = (Map) storeApi.selectStoreById(storeId).getResult();
            Map mapSubcomapny = (Map) storeApi.selectSubsidiaryByID(Long.parseLong(mapStore.get("subCompanyId").toString())).getResult();
            map.put("companyId", mapSubcomapny.get("parentCompany").toString());
            map.put("subsidiaryId", mapSubcomapny.get("subsidiaryId").toString());
            map.put("industryId", mapStore.get("industryID").toString());
        }
        if (StringUtils.isNotBlank(productStatus)) {
            if (Integer.parseInt(productStatus) == ProductStatusEnum.ON.getCode()) {
                //已上架
                map.put("productStatus", ProductStatusEnum.ON.getCode());
            } else if (Integer.parseInt(productStatus) == ProductStatusEnum.DOWN.getCode()) {
                //已下架
                map.put("productStatus", ProductStatusEnum.DOWN.getCode());
            } else if (Integer.parseInt(productStatus) == ProductStatusEnum.DELETE.getCode()) {
                //已删除
                map.put("productStatus", ProductStatusEnum.DELETE.getCode());
            }
        }
        Map<String, Object> mapids = new HashMap();
        if (ProductTypeEnum.PRODUCT.getCode() == Integer.parseInt(type)) {
            List<Product> result = productDao.selectProductList(map);
            List<Product> resultLink = new ArrayList<>();
            if (companyType == 3) {
                //如果是分公司，还要匹配授权中间表
                Map map1 = new HashMap();
                map1.put("subcompanyId", map.get("subsidiaryId"));
                if (productStatus.equals("1")) {//如果是上架
                    map1.put("upOrDown", 1);
                } else if (productStatus.equals("2")) {//如果是下架
                    map1.put("upOrDown", 0);
                }
                map1.put("companyId", map.get("companyId"));
                map1.put("keyWord", keyWord);

                List<Product> producSubcompanyList = productDao.selectProductSubcompany(map1);

                List<Product> producSubcompanyList2 = new ArrayList<>();
                for (Product product : producSubcompanyList) {
                    if (product.getIndustryId().toString().equals(map.get("industryId").toString())) {
                        producSubcompanyList2.add(product);
                    }
                }
                resultLink.addAll(producSubcompanyList2);
            } else {
                resultLink.addAll(result);
            }


            //获取图片
            for (Product product : resultLink) {
                //处理商品图片
                String imageList = product.getImageList();
                if (StringUtils.isNotBlank(imageList)) {
                    String[] imagearr = imageList.split(",");
                    List<Long> imageIdList = new ArrayList(Arrays.asList(imagearr));
                    mapids.put("ids", imageIdList);
                    mapids.put("imageType", ImageTypeEnum.PRODUCT.getCode());
                    List<ProductImage> productImageList = productImageDao.selectProductImageByIds(mapids);
                    product.setProductImageList(productImageList);
                }

                //处理商品活动图片
                String activityImageList = product.getActivityImageList();
                if (StringUtils.isNotBlank(activityImageList)) {
                    String[] imagearr = activityImageList.split(",");
                    List<Long> activeimageIdList = new ArrayList(Arrays.asList(imagearr));
                    mapids.put("ids", activeimageIdList);
                    mapids.put("imageType", ImageTypeEnum.PRODUCTACTIVITYIMAGE.getCode());
                    List<ProductActivityImage> activityproductImageList = productActivityImageDao.selectProductActivityImageByIds(mapids);
                    product.setProductActivityImageList(activityproductImageList);
                }
            }

            if (companyType == 2) {
                //如果是子公司，还要匹配授权中间表
                Map map1 = new HashMap();
                map1.put("subcompanyId", map.get("subsidiaryId"));
                if (productStatus.equals("1")) {//如果是上架
                    map1.put("upOrDown", 1);
                } else if (productStatus.equals("2")) {//如果是下架
                    map1.put("upOrDown", 0);
                }
                map1.put("companyId", map.get("companyId"));
                map1.put("keyWord", keyWord);
                List<Product> producSubcompanyList = productDao.selectProductSubcompany(map1);
                //匹配中间表
                for (Product product : resultLink) {
                    for (Product product_mid : producSubcompanyList) {
                        if (product.getProductCode().equals(product_mid.getProductCode())) {
                            product.setLAY_CHECKED("true");
                        }
                    }
                }
            }

            //给产品池的行业名称赋值
            //查询所有行业
            ResponseResult responseResult = storeApi.selectListIndustryNoPage();
            List<Map> result1 = (List<Map>) responseResult.getResult();

            resultLink.forEach(product -> {
                for (Map map1 : result1) {
                    Long industryID = Long.parseLong(map1.get("industryID").toString());
                    if (industryID.equals(product.getIndustryId())) {
                        product.setIndustryName((String) map1.get("industryName"));
                    }
                }
            });

            if (resultLink != null && resultLink.size() > 0) {
                //方式数据到前端首字母变小写
                TypeUtils.compatibleWithJavaBean = true;
                return ResponseResult.success(resultLink);
            }
            return ResponseResult.error(new Error(ResponseCodeProductEnum.PRODUCT_NOT_FOUND.getCode(), ResponseCodeProductEnum.PRODUCT_NOT_FOUND.getDesc()));
        }

        if (ProductTypeEnum.SERVICE.getCode() == Integer.parseInt(type)) {
            //查询出服务
            List<ServiceProduct> serviceProductList = serviceProductDao.selectProductList(map);
            List<ServiceProduct> resultLink = new ArrayList<>();
            if (companyType == 3) {
                //如果是子公司，还要匹配授权中间表
                Map map1 = new HashMap();
                map1.put("subcompanyId", map.get("subsidiaryId"));
                if (productStatus.equals("1")) {//如果是上架
                    map1.put("upOrDown", 1);
                } else if (productStatus.equals("2")) {//如果是下架
                    map1.put("upOrDown", 0);
                }
                map1.put("companyId", map.get("companyId"));
                List<ServiceProduct> producSubcompanyList = serviceProductDao.selectServiceProductSubcompany(map1);

                List<ServiceProduct> producSubcompanyList2 = new ArrayList<>();
                for (ServiceProduct product : producSubcompanyList) {
                    if (product.getIndustryId().toString().equals(map.get("industryId").toString())) {
                        producSubcompanyList2.add(product);
                    }
                }


                resultLink.addAll(producSubcompanyList2);
            } else {
                resultLink.addAll(serviceProductList);
            }

            //获取图片
            for (ServiceProduct serviceProduct : resultLink) {
                if (StringUtils.equals(serviceProduct.getProductCode(), checkedProductCode)) {
                    serviceProduct.setIsChecked(1);
                }
                String imageList = serviceProduct.getImageList();
                if (StringUtils.isNotBlank(imageList)) {
                    String[] imagearr = imageList.split(",");
                    List<Long> imageIdList = new ArrayList(Arrays.asList(imagearr));
                    mapids.put("ids", imageIdList);
                    mapids.put("imageType", ImageTypeEnum.SERVICEPRODUCT.getCode());
                    List<ProductImage> productImageList = productImageDao.selectProductImageByIds(mapids);
                    serviceProduct.setServiceProductImageList(productImageList);
                }

                //处理商品活动图片
                String activityImageList = serviceProduct.getActivityImageList();
                if (StringUtils.isNotBlank(activityImageList)) {
                    String[] imagearr = activityImageList.split(",");
                    List<Long> activeimageIdList = new ArrayList(Arrays.asList(imagearr));
                    mapids.put("ids", activeimageIdList);
                    mapids.put("imageType", ImageTypeEnum.SERVICEPRODUCTACTIVITYIMAGE.getCode());
                    List<ProductActivityImage> activityproductImageList = productActivityImageDao.selectProductActivityImageByIds(mapids);
                    serviceProduct.setServiceProductActivityImageList(activityproductImageList);
                }
            }

            if (companyType == 2) {
                //如果是子公司，还要匹配授权中间表
                Map map1 = new HashMap();
                map1.put("subcompanyId", map.get("subsidiaryId"));
                if (productStatus.equals("1")) {//如果是上架
                    map1.put("upOrDown", 1);
                } else if (productStatus.equals("2")) {//如果是下架
                    map1.put("upOrDown", 0);
                }
                map1.put("companyId", map.get("companyId"));
                List<ServiceProduct> producSubcompanyList = serviceProductDao.selectServiceProductSubcompany(map1);
                //匹配中间表
                for (ServiceProduct product : resultLink) {
                    for (ServiceProduct product_mid : producSubcompanyList) {
                        if (product.getProductCode().equals(product_mid.getProductCode())) {
                            product.setLAY_CHECKED("true");
                        }
                    }
                }
            }


            //给产品池的行业名称赋值
            //查询所有行业
            ResponseResult responseResult = storeApi.selectListIndustryNoPage();
            List<Map> result1 = (List<Map>) responseResult.getResult();

            resultLink.forEach(product -> {
                for (Map map1 : result1) {
                    Long industryID = Long.parseLong(map1.get("industryID").toString());
                    if (industryID.equals(product.getIndustryId())) {
                        product.setIndustryName((String) map1.get("industryName"));
                    }
                }
            });
            if (resultLink != null && resultLink.size() > 0) {
                return ResponseResult.success(resultLink);
            }
            return ResponseResult.error(new Error(ResponseCodeProductEnum.PRODUCT_NOT_FOUND.getCode(), ResponseCodeProductEnum.PRODUCT_NOT_FOUND.getDesc()));

        }
        return ResponseResult.error(new Error(ResponseCodeProductEnum.PRODUCT_TYPE_NULL.getCode(), ResponseCodeProductEnum.PRODUCT_TYPE_NULL.getDesc()));
    }

    @Transactional
    @Override
    public ResponseResult insertProduct(ProductVO productvo) throws InvocationTargetException, IllegalAccessException {
        Product product = new Product();
        //处理配送方式
        String deliveryMethod = productvo.getDeliveryMethod();
        if (deliveryMethod == DeliveryMethodEnum.ZERO.getCode().toString()) {
            productvo.setDeliveryMethod(DeliveryMethodEnum.ZERO.getDesc());
        }
        if (deliveryMethod == DeliveryMethodEnum.ONE.getCode().toString()) {
            productvo.setDeliveryMethod(DeliveryMethodEnum.ONE.getDesc());
        }
        if (deliveryMethod == DeliveryMethodEnum.TWO.getCode().toString()) {
            productvo.setDeliveryMethod(DeliveryMethodEnum.TWO.getDesc());
        }
        //处理禁止购买
        if (productvo.getBarredBuying() != null) {
            String[] barredBuyingArr = productvo.getBarredBuying();
            StringBuffer barredBuying = new StringBuffer();
            barredBuying = loadArr(barredBuyingArr, barredBuying);
            product.setBarredBuying(barredBuying.toString());
        }

        //处理禁止支付方式
        if (productvo.getBarredPayMethod() != null) {
            String[] barredPayMethodArr = productvo.getBarredPayMethod();
            StringBuffer barredPayMethod = new StringBuffer();
            barredPayMethod = loadArr(barredPayMethodArr, barredPayMethod);
            product.setBarredPayMethod(barredPayMethod.toString());
        }

        BeanUtils.copyProperties(product, productvo);
        String productCode = CodeUtils.getCharAndNumr(8);
        if (productvo.getCompanyType() == 1) {
            product.setCompanyId(productvo.getCompanyId());
        } else if (productvo.getCompanyType() == 2) {
            Map map1 = (Map) storeApi.selectSubsidiaryByID(Long.parseLong(productvo.getCompanyId())).getResult();
            product.setCompanyId(map1.get("parentCompany").toString());
            //同时需要建立子公司与这个商品的关联
            ProducSubcompany producSubcompany = new ProducSubcompany();
            producSubcompany.setProductCode(productCode);
            producSubcompany.setSubcompanyId(Long.parseLong(productvo.getCompanyId()));
            List<ProducSubcompany> producSubcompanyList = new ArrayList<>();
            producSubcompanyList.add(producSubcompany);
            Map map = new HashMap();
            map.put("list", producSubcompanyList);
            productDao.addProductSubcompany(map);
            //如果是子公司加一个标识
            product.setNewCreateCompanyId(productvo.getCompanyId());
            product.setNewCreateCompanyType(productvo.getCompanyType().toString());
        }
        //商品图片
        product.setImageList(product.getImageList());
        //活动商品图片
        product.setActivityImageList(product.getActivityImageList());

        product.setProductCode(productCode);
        if (productvo.getProductKind() != null) {
            product.setProductKind(productvo.getProductKind());
        }
        if (productvo.getProductEffect() != null) {
            product.setProductEffect(productvo.getProductEffect());
        }
        if (productvo.getProductBrand() != null) {
            product.setProductBrand(Long.parseLong(productvo.getProductBrand()));
        }
        if (productvo.getProductCategory() != null) {
            product.setProductCategory(productvo.getProductCategory());
        }
        if (productvo.getMerchantSource() != null) {
            product.setMerchantSource(productvo.getMerchantSource());
        }
        product.setOutstorageProfit(product.getOutstorageProfit().divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
        int result = productDao.insertProduct(product);
        /*---------------------k3操作---------------------*/
        Long temp = product.getProductId();
        Integer id = null;
        String productcode = product.getProductCode();
        String number;
        HashMap<String, Object> resultHashMap;
        HashMap<String, Object> resulut;
        HashMap<String, Object> resultStatus;
        Boolean isSuccess;
        //存放调用k3失败后的参数
        Map paramsmap = new HashMap<>();
        if (result > 0) {
            //添加产品到k3
            HashMap<String, String> userNameAndPassword = getUserNameAndPassword(null);
            String dataCentre = userNameAndPassword.get("dataCentre");
            String userName = userNameAndPassword.get("userName");
            String password = userNameAndPassword.get("password");
            String fname = productvo.getProductName();
            ResponseResult save = k3CLOUDApi.saveProduct(dataCentre, userName, password, fname, id, true);

            //登录失败
            if (save.getResult().equals("暂未登录")) {
                ResponseResult nosave = new ResponseResult();
                paramsmap = new HashMap<>();
                ResponseResult error = error(dataCentre, userName, password, productvo, null, temp, "nosave", paramsmap, save);
                return error;
            }
            //登录成功----》进行判断保存提交审核是否成功
            if (save.isSuccess()) {
                resultHashMap = (HashMap<String, Object>) save.getResult();
                resulut = (HashMap<String, Object>) resultHashMap.get("Result");
                resultStatus = (HashMap<String, Object>) resulut.get("ResponseStatus");
                isSuccess = (Boolean) resultStatus.get("IsSuccess");
                if (isSuccess) {
                    //获取保存成功后的number和id
                    id = (Integer) resulut.get("Id");
                    number = (String) resulut.get("Number");

                    //成功后将k3的id和number保存在数据库
                    product = new Product();
                    product.setK3Id(id.toString());
                    product.setK3Number(number);
                    product.setProductCode(productcode);
                    productDao.updateProduct(product);

                    //如果当前是子公司新增，保存成功后，自动k3分配
                    if (productvo.getCompanyType() == 2) {
                        //k3分配接入
                        String PkIds = id.toString();
                        //如果是子公司
                        Map subCompanyMap = (Map) (storeApi.selectSubsidiaryByID(Long.parseLong(productvo.getCompanyId())).getResult());
                        String TOrgIds = subCompanyMap.get("k3Id").toString();
                        Map companyMap = (Map) (storeApi.selectCompanyByID(Long.parseLong(subCompanyMap.get("parentCompany").toString())).getResult());
                        ResponseResult responseResult = k3CLOUDApi.allocate(
                                companyMap.get("dataCentre").toString(),
                                companyMap.get("yewuDataCentreUserName").toString(),
                                companyMap.get("yewuDataCentrePassword").toString(),
                                PkIds,
                                TOrgIds
                        );
                        if (responseResult.getResult().equals("暂未登录")) {
                            return ResponseResult.success("商品生成成功，K3生成失败，K3用户密码错误");
                        }
                        if (!((Map) (((Map) (((Map) (responseResult.getResult())).get("Result"))).get("ResponseStatus"))).get("IsSuccess").toString().equals("true")) {
                            return ResponseResult.success("商品生成成功，K3生成未成功:" + ((Map) (((List) (((Map) (((Map) (((Map) (responseResult.getResult())).get("Result"))).get("ResponseStatus"))).get("Errors"))).get(0))).get("Message").toString());
                        }
                    }

                    return ResponseResult.success("商品生成成功，K3分配成功");
                }
            }
            //保存失败
            paramsmap = new HashMap<>();
            ResponseResult error = error(dataCentre, userName, password, productvo, null, temp, "save", paramsmap, save);
            return error;
        } else {
            return ResponseResult.error(new Error(ResponseCodeProductEnum.ADD_FAIL.getCode(), ResponseCodeProductEnum.ADD_FAIL.getDesc()));
        }
    }


    @Transactional
    @Override
    public ResponseResult insertServiceProduct(ServiceProductVO serviceProductvo, ProductNursing productNursing) throws InvocationTargetException, IllegalAccessException {
        ServiceProduct serviceProduct = new ServiceProduct();
        //商品图片
        BeanUtils.copyProperties(serviceProduct, serviceProductvo);
        //活动商品图片
        serviceProduct.setImageList(serviceProductvo.getImageList());
        serviceProduct.setActivityImageList(serviceProductvo.getActivityImageList());
        String productCode = CodeUtils.getCharAndNumr(8);
        if (serviceProductvo.getCompanyType() == 1) {
            serviceProduct.setCompanyId(serviceProductvo.getCompanyId());
        } else if (serviceProductvo.getCompanyType() == 2) {
            Map map = (Map) storeApi.selectSubsidiaryByID(Long.parseLong(serviceProductvo.getCompanyId())).getResult();
            serviceProduct.setCompanyId(map.get("parentCompany").toString());
            //同时需要建立子公司与这个商品的关联
            ProducSubcompany producSubcompany = new ProducSubcompany();
            producSubcompany.setProductCode(productCode);
            producSubcompany.setSubcompanyId(Long.parseLong(serviceProductvo.getCompanyId()));
            List<ProducSubcompany> producSubcompanyList = new ArrayList<>();
            producSubcompanyList.add(producSubcompany);
            Map map1 = new HashMap();
            map1.put("list", producSubcompanyList);
            productDao.addProductSubcompany(map1);
        }
        serviceProduct.setProductCode(productCode);
        if (serviceProductvo.getPostType() != null) {
            serviceProduct.setPostType(ArrayToString.ArrayToString(serviceProductvo.getPostType()));
        }
        int resultInt = serviceProductDao.insertServiceProduct(serviceProduct);
        /*---------------------k3操作---------------------*/
        Long temp = serviceProduct.getServiceProductId();
        Integer id = null;
        String number;
        HashMap<String, Object> resultHashMap;
        HashMap<String, Object> resulut;
        HashMap<String, Object> resultStatus;
        Boolean isSuccess;
        //存放调用k3失败后的参数
        Map paramsmap = new HashMap<>();
        if (resultInt > 0) {
            //添加产品到k3
            HashMap<String, String> userNameAndPassword = getUserNameAndPassword(null);
            String dataCentre = userNameAndPassword.get("dataCentre");
            String userName = userNameAndPassword.get("userName");
            String password = userNameAndPassword.get("password");
            String fname = serviceProduct.getProductName();
            ResponseResult save = k3CLOUDApi.saveProduct(dataCentre, userName, password, fname, id, true);

            //登录失败
            if (save.getResult().equals("暂未登录")) {
                ResponseResult nosave = new ResponseResult();
                paramsmap = new HashMap<>();
                ResponseResult error = error(dataCentre, userName, password, null, serviceProductvo, temp, "nosave", paramsmap, nosave);
                return error;
            }
            //登录成功----》进行判断保存提交审核是否成功
            if (save.isSuccess()) {
                resultHashMap = (HashMap<String, Object>) save.getResult();
                resulut = (HashMap<String, Object>) resultHashMap.get("Result");
                resultStatus = (HashMap<String, Object>) resulut.get("ResponseStatus");
                isSuccess = (Boolean) resultStatus.get("IsSuccess");
                if (isSuccess) {
                    //获取保存成功后的number和id
                    id = (Integer) resulut.get("Id");
                    number = (String) resulut.get("Number");

                    //成功后将k3的id和number保存在数据库
//                    serviceProduct = new ServiceProduct();
                    serviceProduct.setK3Id(id.toString());
                    serviceProduct.setK3Number(number);
                    serviceProduct.setServiceProductId(temp);
                    serviceProductDao.updateServiceProduct(serviceProduct);
                    //如果当前是子公司新增，保存成功后，自动k3分配
                    if (serviceProductvo.getCompanyType() == 2) {
                        //k3分配接入
                        String PkIds = id.toString();
                        //如果是子公司
                        Map subCompanyMap = (Map) (storeApi.selectSubsidiaryByID(Long.parseLong(serviceProductvo.getCompanyId())).getResult());
                        String TOrgIds = subCompanyMap.get("k3Id").toString();
                        Map companyMap = (Map) (storeApi.selectCompanyByID(Long.parseLong(subCompanyMap.get("parentCompany").toString())).getResult());
                        ResponseResult responseResult = k3CLOUDApi.allocate(
                                companyMap.get("dataCentre").toString(),
                                companyMap.get("yewuDataCentreUserName").toString(),
                                companyMap.get("yewuDataCentrePassword").toString(),
                                PkIds,
                                TOrgIds
                        );
                        if (responseResult.getResult().equals("暂未登录")) {
                            return ResponseResult.success("商品生成成功，K3生成失败，K3用户密码错误");
                        }
                        if (!((Map) (((Map) (((Map) (responseResult.getResult())).get("Result"))).get("ResponseStatus"))).get("IsSuccess").toString().equals("true")) {
                            return ResponseResult.success("商品生成成功，K3生成未成功:" + ((Map) (((List) (((Map) (((Map) (((Map) (responseResult.getResult())).get("Result"))).get("ResponseStatus"))).get("Errors"))).get(0))).get("Message").toString());
                        }
                    }
                    return ResponseResult.success("商品生成成功，K3生成成功");
                }
            }
            //保存失败
            paramsmap = new HashMap<>();
            ResponseResult error = error(dataCentre, userName, password, null, serviceProductvo, temp, "save", paramsmap, save);
            return error;
        } else {
            return ResponseResult.error(new Error(ResponseCodeProductEnum.ADD_FAIL.getCode(), ResponseCodeProductEnum.ADD_FAIL.getDesc()));
        }
    }


    @Override
    public ResponseResult updateProduct(ProductVO productvo) throws InvocationTargetException, IllegalAccessException {
        Product product = new Product();
        HashMap<Object, Object> map = new HashMap<>();
        //判断商品是否存在
        map.put("productCode", productvo.getProductCode());
        product = productDao.checkByCondition(map);
        if (product == null) {
            return ResponseResult.error(new Error(ResponseCodeProductEnum.PRODUCTP_IS_NOTEXIT.getCode(), ResponseCodeProductEnum.PRODUCTP_IS_NOTEXIT.getDesc()));
        }
        //处理禁止购买
        if (productvo.getBarredBuying() != null) {
            String[] barredBuyingArr = productvo.getBarredBuying();
            StringBuffer barredBuying = new StringBuffer();
            barredBuying = loadArr(barredBuyingArr, barredBuying);
            product.setBarredBuying(barredBuying.toString());
        }

        //处理禁止支付方式
        if (productvo.getBarredPayMethod() != null) {
            String[] barredPayMethodArr = productvo.getBarredPayMethod();
            StringBuffer barredPayMethod = new StringBuffer();
            barredPayMethod = loadArr(barredPayMethodArr, barredPayMethod);
            product.setBarredPayMethod(barredPayMethod.toString());
        }

        BeanUtils.copyProperties(product, productvo);
        //商品图片
        product.setImageList(product.getImageList());
        //活动商品图片
        product.setActivityImageList(product.getActivityImageList());

        int resultInt = productDao.updateProduct(product);

        if (resultInt > 0) {
            //通过商品code拿到k3的id和number
            product = productDao.selectProductByCode(product.getProductCode());
            Integer k3Id = Integer.parseInt(product.getK3Id());
            String k3Number = product.getK3Number();
            String productCode = product.getProductCode();

            HashMap<String, String> userNameAndPassword = getUserNameAndPassword(null);
            String dataCentre = userNameAndPassword.get("dataCentre");
            String userName = userNameAndPassword.get("userName");
            String password = userNameAndPassword.get("password");

            //反审核
            ResponseResult unAudit = k3CLOUDApi.unAuditProduct(dataCentre, userName, password, k3Number, k3Id.toString());

            Long temp = product.getProductId();
            HashMap<String, Object> resultHashMap;
            HashMap<String, Object> resulut;
            HashMap<String, Object> resultStatus;
            Boolean isSuccess;
            Map paramsmap;
            Integer id;
            String number;
            String fname = productvo.getProductName();

            //登录失败
            if (unAudit.getResult().equals("暂未登录")) {
                ResponseResult nosave = new ResponseResult();
                paramsmap = new HashMap<>();
                ResponseResult error = error(dataCentre, userName, password, productvo, null, temp, "unAudit", paramsmap, unAudit);
                return error;
            }
            //登录成功----》进行判断反交审核是否成功
            if (unAudit.isSuccess()) {
                resultHashMap = (HashMap<String, Object>) unAudit.getResult();
                resulut = (HashMap<String, Object>) resultHashMap.get("Result");
                resultStatus = (HashMap<String, Object>) resulut.get("ResponseStatus");
                isSuccess = (Boolean) resultStatus.get("IsSuccess");
                if (isSuccess) {
                    //反审核成功后进行保存
                    id = product.getProductId().intValue();
                    ResponseResult save = k3CLOUDApi.saveProduct(dataCentre, userName, password, fname, k3Id, true);
                    if (save.isSuccess()) {
                        resultHashMap = (HashMap<String, Object>) save.getResult();
                        resulut = (HashMap<String, Object>) resultHashMap.get("Result");
                        resultStatus = (HashMap<String, Object>) resulut.get("ResponseStatus");
                        isSuccess = (Boolean) resultStatus.get("IsSuccess");
                        if (isSuccess) {
                            //获取保存成功后的number和id
                            id = (Integer) resulut.get("Id");
                            number = (String) resulut.get("Number");

                            //成功后将k3的id和number保存在数据库
                            product = new Product();
                            product.setK3Id(id.toString());
                            product.setK3Number(number);
                            product.setProductCode(productCode);
                            productDao.updateProduct(product);
                            return ResponseResult.success();
                        }
                        //保存失败
                        paramsmap = new HashMap<>();
                        ResponseResult error = error(dataCentre, userName, password, productvo, null, temp, "save", paramsmap, unAudit);
                        return error;
                    }
                } else {
                    //反审核失败
                    paramsmap = new HashMap<>();
                    ResponseResult error = error(dataCentre, userName, password, productvo, null, temp, "unaudit", paramsmap, unAudit);
                    return error;
                }
            }
            //反审核失败
            paramsmap = new HashMap<>();
            ResponseResult error = error(dataCentre, userName, password, productvo, null, temp, "unAudit", paramsmap, unAudit);
            return error;
        } else {
            return ResponseResult.error(new Error(ResponseCodeProductEnum.UPDATE_FAIL.getCode(), ResponseCodeProductEnum.UPDATE_FAIL.getDesc()));
        }
    }

    @Override
    public ResponseResult updateProductStoreIds(ProductVO productvo) {
        Product product = new Product();
        product.setProductId(productvo.getProductId());
        product.setStoreIds(productvo.getStoreIds());
        int resultInt = productDao.updateProduct(product);
        return ResponseResult.success();
    }


    @Override
    public ResponseResult updateServiceProduct(ServiceProductVO serviceProductVO) throws InvocationTargetException, IllegalAccessException {
        ServiceProduct serviceProduct = new ServiceProduct();
        HashMap<Object, Object> map = new HashMap<>();
        //判断服务商品是否存在
        map.put("productCode", serviceProductVO.getProductCode());
        serviceProduct = serviceProductDao.checkByCondition(map);
        if (serviceProduct == null) {
            return ResponseResult.error(new Error(ResponseCodeProductEnum.SERVICEPRODUCTP_IS_NOTEXIT.getCode(), ResponseCodeProductEnum.SERVICEPRODUCTP_IS_NOTEXIT.getDesc()));
        }

        BeanUtils.copyProperties(serviceProduct, serviceProductVO);
        int resultInt = serviceProductDao.updateServiceProduct(serviceProduct);
        if (resultInt > 0) {
            //通过商品code拿到k3的id和number
            serviceProduct = serviceProductDao.selectProductByCode(serviceProduct.getProductCode());
            Integer k3Id = Integer.parseInt(serviceProduct.getK3Id());
            String k3Number = serviceProduct.getK3Number();
            HashMap<String, String> userNameAndPassword = getUserNameAndPassword(null);
            String dataCentre = userNameAndPassword.get("dataCentre");
            String userName = userNameAndPassword.get("userName");
            String password = userNameAndPassword.get("password");

            //反审核
            ResponseResult unAudit = k3CLOUDApi.unAuditProduct(dataCentre, userName, password, k3Number, k3Id.toString());

            Long temp = serviceProduct.getServiceProductId();
            HashMap<String, Object> resultHashMap;
            HashMap<String, Object> resulut;
            HashMap<String, Object> resultStatus;
            Boolean isSuccess;
            Map paramsmap;
            Integer id;
            String number;
            String fname = serviceProductVO.getProductName();
            //登录失败
            if (unAudit.getResult().equals("暂未登录")) {
                ResponseResult nosave = new ResponseResult();
                paramsmap = new HashMap<>();
                ResponseResult error = error(dataCentre, userName, password, null, serviceProductVO, temp, "unAudit", paramsmap, unAudit);
                return error;
            }
            //登录成功----》进行判断反交审核是否成功
            if (unAudit.isSuccess()) {
                resultHashMap = (HashMap<String, Object>) unAudit.getResult();
                resulut = (HashMap<String, Object>) resultHashMap.get("Result");
                resultStatus = (HashMap<String, Object>) resulut.get("ResponseStatus");
                isSuccess = (Boolean) resultStatus.get("IsSuccess");
                if (isSuccess) {
                    //反审核成功后进行保存
                    id = serviceProduct.getServiceProductId().intValue();
                    ResponseResult save = k3CLOUDApi.saveProduct(dataCentre, userName, password, fname, k3Id, true);
                    if (save.isSuccess()) {
                        resultHashMap = (HashMap<String, Object>) save.getResult();
                        resulut = (HashMap<String, Object>) resultHashMap.get("Result");
                        resultStatus = (HashMap<String, Object>) resulut.get("ResponseStatus");
                        isSuccess = (Boolean) resultStatus.get("IsSuccess");
                        if (isSuccess) {
                            //获取保存成功后的number和id
                            id = (Integer) resulut.get("Id");
                            number = (String) resulut.get("Number");

                            //成功后将k3的id和number保存在数据库
                            serviceProduct = new ServiceProduct();
                            serviceProduct.setK3Id(id.toString());
                            serviceProduct.setK3Number(number);
                            serviceProduct.setServiceProductId(temp);
                            serviceProductDao.updateServiceProduct(serviceProduct);
                            return ResponseResult.success();

                        }
                        //保存失败
                        paramsmap = new HashMap<>();
                        ResponseResult error = error(dataCentre, userName, password, null, serviceProductVO, temp, "save", paramsmap, unAudit);
                        return error;
                    }
                } else {
                    //反审核失败
                    paramsmap = new HashMap<>();
                    ResponseResult error = error(dataCentre, userName, password, null, serviceProductVO, temp, "unaudit", paramsmap, unAudit);
                    return error;
                }
            }
            //反审核失败
            paramsmap = new HashMap<>();
            ResponseResult error = error(dataCentre, userName, password, null, serviceProductVO, temp, "unAudit", paramsmap, unAudit);
            return error;
        } else {
            return ResponseResult.error(new Error(ResponseCodeProductEnum.UPDATE_FAIL.getCode(), ResponseCodeProductEnum.UPDATE_FAIL.getDesc()));
        }
    }

    @Override
    public ResponseResult updateServiceProductStoreIds(ServiceProductVO serviceProductVO) {
        ServiceProduct serviceProduct = new ServiceProduct();
        serviceProduct.setServiceProductId(serviceProductVO.getServiceProductId());
        serviceProduct.setStoreIds(serviceProductVO.getStoreIds());
        int resultInt = serviceProductDao.updateServiceProduct(serviceProduct);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult deleteProduct(String productCode, String modifyOperator, Integer type) throws InvocationTargetException, IllegalAccessException {
        Map map = new HashMap<>();
        HashMap<String, String> userNameAndPassword = getUserNameAndPassword(null);
        String dataCentre = userNameAndPassword.get("dataCentre");
        String userName = userNameAndPassword.get("userName");
        String password = userNameAndPassword.get("password");
        if (ProductTypeEnum.PRODUCT.getCode() == type) {
            map.put("productCode", productCode);
            map.put("modifyOperator", modifyOperator);
            int deleteProductResult = productDao.deleteProduct(map);
            if (deleteProductResult > 0) {
                //通过商品code拿到k3的id和number
                Product product = productDao.selectProductByCode(productCode);
                ProductVO productVO = new ProductVO();
                BeanUtils.copyProperties(productVO, product);
                String k3Id = product.getK3Id();
                String k3Number = product.getK3Number();

                //反审核
                ResponseResult unAudit = k3CLOUDApi.unAuditProduct(dataCentre, userName, password, k3Number, k3Id);

                Long temp = product.getProductId();
                HashMap<String, Object> resultHashMap;
                HashMap<String, Object> resulut;
                HashMap<String, Object> resultStatus;
                Boolean isSuccess;
                Map paramsmap;
                Integer id;
                String number;
                //登录失败
                if (unAudit.getResult().equals("暂未登录")) {
                    ResponseResult nosave = new ResponseResult();
                    paramsmap = new HashMap<>();
                    ResponseResult error = error(dataCentre, userName, password, productVO, null, temp, "unAudit", paramsmap, unAudit);
                    return error;
                }
                //登录成功----》进行判断反交审核是否成功
                if (unAudit.isSuccess()) {
                    resultHashMap = (HashMap<String, Object>) unAudit.getResult();
                    resulut = (HashMap<String, Object>) resultHashMap.get("Result");
                    resultStatus = (HashMap<String, Object>) resulut.get("ResponseStatus");
                    isSuccess = (Boolean) resultStatus.get("IsSuccess");
                    if (isSuccess) {
                        //反审核成功后进行删除
                        ResponseResult delete = k3CLOUDApi.deleteProduct(dataCentre, userName, password, k3Number, k3Id);
//                        ResponseResult save = k3CLOUDApi.saveProduct(dataCentre,userName,password,productVO.getProductName(), "100", "100", "[{\"FEntryID\":0,\"FCodeType_CMK\":\"\",\"FUnitId_CMK\":{\"FNUMBER\":\"\"}}]", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "[{\"FEntryID\":0,\"FInvPtyId\":{\"FNumber\":\"\"},\"FIsEnable\":\"false\",\"FIsAffectPrice\":\"false\",\"FIsAffectPlan\":\"false\",\"FIsAffectCost\":\"false\"}]");
                        if (delete.isSuccess()) {
                            resultHashMap = (HashMap<String, Object>) delete.getResult();
                            resulut = (HashMap<String, Object>) resultHashMap.get("Result");
                            resultStatus = (HashMap<String, Object>) resulut.get("ResponseStatus");
                            isSuccess = (Boolean) resultStatus.get("IsSuccess");
                            if (isSuccess) {
                                //k3删除成功 响应页面
                                return ResponseResult.success();
                            }
                            //保存失败
                            paramsmap = new HashMap<>();
                            ResponseResult error = error(dataCentre, userName, password, productVO, null, temp, "save", paramsmap, unAudit);
                            return error;
                        }
                    } else {
                        //反审核失败
                        paramsmap = new HashMap<>();
                        ResponseResult error = error(dataCentre, userName, password, productVO, null, temp, "unaudit", paramsmap, unAudit);
                        return error;
                    }
                }
                //反审核失败
                paramsmap = new HashMap<>();
                ResponseResult error = error(dataCentre, userName, password, productVO, null, temp, "unAudit", paramsmap, unAudit);
                return error;
            } else {
                return ResponseResult.error(new Error(ResponseCodeProductEnum.UPDATE_FAIL.getCode(), ResponseCodeProductEnum.UPDATE_FAIL.getDesc()));
            }
        } else {
            map.put("productCode", productCode);
            map.put("modifyOperator", modifyOperator);
            int deleteServiceProductResult = serviceProductDao.deleteServiceProduct(map);
            if (deleteServiceProductResult > 0) {
                //通过商品code拿到k3的id和number
                ServiceProduct serviceProduct = serviceProductDao.selectProductByCode(productCode);
                ServiceProductVO serviceProductVO = new ServiceProductVO();
                BeanUtils.copyProperties(serviceProductVO, serviceProduct);
                String k3Id = serviceProduct.getK3Id();
                String k3Number = serviceProduct.getK3Number();
                //反审核
                ResponseResult unAudit = k3CLOUDApi.unAuditProduct(dataCentre, userName, password, k3Number, k3Id);

                Long temp = serviceProduct.getServiceProductId();
                HashMap<String, Object> resultHashMap;
                HashMap<String, Object> resulut;
                HashMap<String, Object> resultStatus;
                Boolean isSuccess;
                Map paramsmap;
                Integer id;
                String number;
                //登录失败
                if (unAudit.getResult().equals("暂未登录")) {
                    ResponseResult nosave = new ResponseResult();
                    paramsmap = new HashMap<>();
                    ResponseResult error = error(dataCentre, userName, password, null, serviceProductVO, temp, "unAudit", paramsmap, unAudit);
                    return error;
                }
                //登录成功----》进行判断反交审核是否成功
                if (unAudit.isSuccess()) {
                    resultHashMap = (HashMap<String, Object>) unAudit.getResult();
                    resulut = (HashMap<String, Object>) resultHashMap.get("Result");
                    resultStatus = (HashMap<String, Object>) resulut.get("ResponseStatus");
                    isSuccess = (Boolean) resultStatus.get("IsSuccess");
                    if (isSuccess) {
                        //反审核成功后进行保存
//                        ResponseResult save = k3CLOUDApi.saveProduct(dataCentre,userName,password,serviceProductVO.getProductName(), "100", "100", "[{\"FEntryID\":0,\"FCodeType_CMK\":\"\",\"FUnitId_CMK\":{\"FNUMBER\":\"\"}}]", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "[{\"FEntryID\":0,\"FInvPtyId\":{\"FNumber\":\"\"},\"FIsEnable\":\"false\",\"FIsAffectPrice\":\"false\",\"FIsAffectPlan\":\"false\",\"FIsAffectCost\":\"false\"}]");
                        ResponseResult save = k3CLOUDApi.deleteProduct(dataCentre, userName, password, k3Number, k3Id);
                        if (save.isSuccess()) {
                            resultHashMap = (HashMap<String, Object>) save.getResult();
                            resulut = (HashMap<String, Object>) resultHashMap.get("Result");
                            resultStatus = (HashMap<String, Object>) resulut.get("ResponseStatus");
                            isSuccess = (Boolean) resultStatus.get("IsSuccess");
                            if (isSuccess) {
                                //获取保存成功后的number和id
                                return ResponseResult.success();
                            }
                            //保存失败
                            paramsmap = new HashMap<>();
                            ResponseResult error = error(dataCentre, userName, password, null, serviceProductVO, temp, "save", paramsmap, unAudit);
                            return error;
                        }
                    } else {
                        //反审核失败
                        paramsmap = new HashMap<>();
                        ResponseResult error = error(dataCentre, userName, password, null, serviceProductVO, temp, "unaudit", paramsmap, unAudit);
                        return error;
                    }
                }
                //反审核失败
                paramsmap = new HashMap<>();
                ResponseResult error = error(dataCentre, userName, password, null, serviceProductVO, temp, "unAudit", paramsmap, unAudit);
                return error;
            } else {
                return ResponseResult.error(new Error(ResponseCodeProductEnum.UPDATE_FAIL.getCode(), ResponseCodeProductEnum.UPDATE_FAIL.getDesc()));
            }
        }
    }


    @Override
    public ResponseResult deleteServiceProduct(Long serviceProductId, String modifyOperator) {
        Map<Object, Object> map = new HashMap<>();
        map.put("serviceProductId", serviceProductId);
        map.put("modifyOperator", modifyOperator);
        return ResponseResult.success(serviceProductDao.deleteServiceProduct(map));
    }

    @Override
    public ResponseResult putOffOrOnProduct(String productCode, int productStatus, Integer type, Long companyType, Long companyId) {
        if (companyType == 1) {//总公司下架
            int resultInt = 0;
            if (ProductTypeEnum.PRODUCT.getCode() == type) {
                Product product = new Product();
                product.setProductCode(productCode);
                product.setProductStatus(productStatus);
                resultInt = productDao.updateProductByStatus(product);
            } else {
                ServiceProduct serviceProduct = new ServiceProduct();
                serviceProduct.setProductCode(productCode);
                serviceProduct.setProductStatus(productStatus);
                resultInt = serviceProductDao.updateProductByStatus(serviceProduct);
            }
            if (resultInt < 0) {
                return ResponseResult.error(new Error(ResponseCodeProductEnum.PUTORDOWN_FAIL.getCode(), ResponseCodeProductEnum.PUTORDOWN_FAIL.getDesc()));
            }
        } else if (companyType == 2) {//子公司下架
            ProducSubcompany producSubcompany = new ProducSubcompany();
            producSubcompany.setSubcompanyId(companyId);
            producSubcompany.setProductCode(productCode);
            if (productStatus == 1) {//上架
                producSubcompany.setUpOrDown(1);
            } else if (productStatus == 2) {//下架
                producSubcompany.setUpOrDown(0);
            }
            productDao.onDownProductBySubcompany(producSubcompany);
        } else if (companyType == 3) {
            ProducStore producStore = new ProducStore();
            producStore.setStoreId(companyId);
            producStore.setProductCode(productCode);
            if (productStatus == 1) {//上架
                producStore.setUpOrDown(1);
            } else if (productStatus == 2) {//下架
                producStore.setUpOrDown(0);
            }
            productDao.onDownProductByStore(producStore);
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult exportProduct(HttpServletRequest req, HttpServletResponse resp, Integer status, Integer productStatus, Integer type) {
//        final String userAgent = req.getHeader("USER-AGENT");
        Map map = new HashMap<>();
        map.put("status", status);
        map.put("productStatus", productStatus);
        //导出商品分类
        String sheetName = "";
        String namepre = "";
        String[] title = {};   //key为title的所有值
        String[][] valueStr = null;
        if (ProductTypeEnum.PRODUCT.getCode() == type) {
            List<ExportVO> productListresult = productDao.selectProductListByExport(map);
//            title = new String[]{"商品编码", "商品名称", "类型", "种类", "净含量", "原价", "现价", "详细内容"};
            title = new String[]{"商品编码", "商品名称", "类型", "净含量", "原价", "现价", "详细内容"};
            //value处理
            valueStr = new String[productListresult.size()][title.length];
            for (int i = 0; i < productListresult.size(); i++) {
                valueStr[i][0] = productListresult.get(i).getProductCode();
                valueStr[i][1] = productListresult.get(i).getProductName();
                valueStr[i][2] = "产品";
//                valueStr[i][3] = productListresult.get(i).getProductKindName();
                valueStr[i][3] = productListresult.get(i).getNetContent();
                valueStr[i][4] = productListresult.get(i).getProductOriginalPrice().toString();
                valueStr[i][5] = productListresult.get(i).getRetailPrice().toString();
                valueStr[i][6] = productListresult.get(i).getProductDetails();
            }
            namepre = "商品列表";
            sheetName = "商品";
        } else {
            List<ExportVO> serviceListresult = productDao.selectServiceListByExport(map);
//            title = new String[]{"商品代码", "商品名称", "类型", "种类", "原价", "现价", "详细内容"};   //key为title的所有值
            title = new String[]{"商品代码", "商品名称", "类型", "原价", "现价", "详细内容"};   //key为title的所有值
            //value处理
            valueStr = new String[serviceListresult.size()][title.length];
            for (int i = 0; i < serviceListresult.size(); i++) {
                for (int j = 0; j < title.length; j++) {
                    valueStr[i][0] = serviceListresult.get(i).getProductCode();
                    valueStr[i][1] = serviceListresult.get(i).getProductName();
                    valueStr[i][2] = "服务";
//                    valueStr[i][3] = serviceListresult.get(i).getProductKindName();
                    valueStr[i][3] = serviceListresult.get(i).getProductOriginalPrice().toString();
                    valueStr[i][4] = serviceListresult.get(i).getRetailPrice().toString();
                    valueStr[i][5] = serviceListresult.get(i).getProductDetails();
                }
            }
            namepre = "服务商品列表";
            sheetName = "服务商品";
        }
        String fileName = namepre + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".xls";

        map.put("sheetName", sheetName);
        map.put("title", title);
        map.put("value", valueStr);
        map.put("fileName", fileName);
        HttpServletRequestWarpper requestWarpper = new HttpServletRequestWarpper(req, map);
        exportController.exportExcel(requestWarpper, resp);
        return ResponseResult.success();
    }


    @Override
    public ResponseResult selectServiceVOList(int pageNum, int pageSize, String keyWordProductCode, String keyWordProductName) {
        PageHelper.startPage(pageNum, pageSize);
        Map map = new HashMap();
        map.put("keyWordProductCode", keyWordProductCode);
        map.put("keyWordProductName", keyWordProductName);
        List<ProductServiceVO> productServiceNameList = serviceProductDao.selectServiceVOList(map);
        PageInfo<ProductServiceVO> pageInfo = new PageInfo<>(productServiceNameList);
        return ResponseResult.success(pageInfo);
    }

    @Override
    public ResponseResult selectServiceVOListNoPage(String keyWordProductCode, String keyWordProductName) {
        Map map = new HashMap();
        map.put("keyWordProductCode", keyWordProductCode);
        map.put("keyWordProductName", keyWordProductName);
        List<ProductServiceVO> productServiceNameList = serviceProductDao.selectServiceVOList(map);
        return ResponseResult.success(productServiceNameList);
    }


    @Override
    public ResponseResult selectServiceListById(int pageNum, int pageSize, String serviceProductIds) {
        PageHelper.startPage(pageNum, pageSize);
        Map mapkey = new HashMap();
        mapkey.put("keyWordProductCode", "");
        mapkey.put("keyWordProductName", "");
        Map map = new HashMap();
        String[] listArray = serviceProductIds.split(",");
        List<String> listId = Arrays.asList(listArray);
        map.put("list", listId);
        List<ServiceProduct> serviceProducts = serviceProductDao.selectServiceListById(map);
        PageInfo pageInfo = new PageInfo(serviceProducts);
        if (serviceProducts == null || serviceProducts.size() == 0) {
            return ResponseResult.error(new Error(ResponseCodeProductEnum.SERVICEPRODUCTP_IS_NOTEXIT.getCode(), ResponseCodeProductEnum.SERVICEPRODUCTP_IS_NOTEXIT.getDesc()));
        }
        return ResponseResult.success(pageInfo);
    }

    @Override
    public ResponseResult selectPorductListById(int pageNum, int pageSize, String productIds) {
        PageHelper.startPage(pageNum, pageSize);
        Map mapkey = new HashMap();
        mapkey.put("keyWordProductCode", "");
        mapkey.put("keyWordProductName", "");
        Map map = new HashMap();
        String[] listArray = productIds.split(",");
        List<String> listId = Arrays.asList(listArray);
        map.put("list", listId);
        List<Product> products = serviceProductDao.selectProductListById(map);
        PageInfo pageInfo = new PageInfo(products);
        if (products == null || products.size() == 0) {
            return ResponseResult.error(new Error(ResponseCodeProductEnum.PRODUCTP_IS_NOTEXIT.getCode(), ResponseCodeProductEnum.PRODUCTP_IS_NOTEXIT.getDesc()));
        }
        return ResponseResult.success(pageInfo);
    }

    @Override
    public ResponseResult selectPorductListByIdNoPage(String productIds) {
        Map mapkey = new HashMap();
        mapkey.put("keyWordProductCode", "");
        mapkey.put("keyWordProductName", "");
        Map map = new HashMap();
        String[] listArray = productIds.split(",");
        List<String> listId = Arrays.asList(listArray);
        map.put("list", listId);
        List<Product> products = serviceProductDao.selectProductListById(map);
        if (products == null || products.size() == 0) {
            return ResponseResult.error(new Error(ResponseCodeProductEnum.PRODUCTP_IS_NOTEXIT.getCode(), ResponseCodeProductEnum.PRODUCTP_IS_NOTEXIT.getDesc()));
        }
        return ResponseResult.success(products);
    }

    @Override
    public ResponseResult check(Map map) {
        if ("productName".equals(map.get("productName"))) {
            Product product = productDao.checkByCondition(map);
            if (product != null) {
                return ResponseResult.error(new Error(ResponseCodeProductEnum.PRODUCTP_NAME_IS_EXIT.getCode(), ResponseCodeProductEnum.PRODUCTP_NAME_IS_EXIT.getDesc()));
            }
        }
        if ("serviceProductName".equals(map.get("serviceProductName"))) {
            ServiceProduct serviceProduct = serviceProductDao.checkByCondition(map);
            if (serviceProduct != null) {
                return ResponseResult.error(new Error(ResponseCodeProductEnum.SERVICEPRODUCTP_NAME_IS_EXIT.getCode(), ResponseCodeProductEnum.SERVICEPRODUCTP_NAME_IS_EXIT.getDesc()));
            }
        }
        return null;
    }

    @Override
    public ResponseResult selectProductAndServiceNameList(int pageNum, int pageSize) {
        Map map = new HashMap<>();
        List<ProductNameVO> productName = productDao.selectProductNameList(map);
        List<ProductServiceVO> serviceNameList = serviceProductDao.selectServiceVOList(map);
        ProductAndServiceNameVO vo = new ProductAndServiceNameVO();
        vo.setProductNameVOList(productName);
        vo.setProductServiceNameVOList(serviceNameList);
        if (vo != null) {
            return ResponseResult.success(vo);
        }
        return ResponseResult.error(new Error(ResponseCodeProductEnum.FIND_NAME_ERROR.getCode(), ResponseCodeProductEnum.FIND_NAME_ERROR.getDesc()));
    }

    public static ResponseResult ProductCheck(@Validated ProductVO product, BindingResult bindingResult) {
        int count = bindingResult.getErrorCount();
        HashMap<Object, Object> map = new HashMap<>();
        if (count > 0) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (int i = 0; i < count; i++) {
                FieldError error = fieldErrors.get(i);
                map.put(error.getField(), error.getDefaultMessage());
            }
        }
        return ResponseResult.error(new Error(1, map));
    }

    @Override
    public ResponseResult selectProductByCondition(Long storeId, Long subClassID, Long postId, Integer postLevel) {
        Map<Object, Object> map = new HashMap<>();
        List<ServiceProduct> serviceProductListResult = new ArrayList<>();
        if (StringUtils.isNotBlank(subClassID.toString())) {
            map.put("subClassID", subClassID);
            serviceProductListResult = serviceProductDao.selectProductList(map);
        }
        List<ServiceProduct> serviceProductList = new ArrayList<>();
        for (ServiceProduct serviceProduct : serviceProductListResult) {
            if (serviceProduct.getStoreIds().indexOf(storeId.toString()) != -1) {
                serviceProductList.add(serviceProduct);
            }
        }
        List<Map> resutlMap = (List<Map>) storeApi.selectProjectNoPage(postId, postLevel, null, null).getResult();
        List<ServiceProduct> newProject = new ArrayList<>();
        if (resutlMap == null) {
            newProject = serviceProductList;
            return ResponseResult.success(serviceProductList);
        }
        for (ServiceProduct serviceProduct : serviceProductList) {
            for (Map postProject : resutlMap) {
                if (serviceProduct.getServiceProductId().toString().equals(postProject.get("serviceProductId").toString()) && postProject.get("isChecked").toString().equals("1")) {
                    serviceProductList.add(serviceProduct);
                }
            }
        }


        return ResponseResult.success(serviceProductList);
    }


    @Override
    public ResponseResult selectDeliveryMethod() {
        Map<Integer, String> enumToMap = EnumUtil.getEnumToMap(DeliveryMethodEnum.class);
        return ResponseResult.success(enumToMap);
    }

    @Override
    public ResponseResult selectProductBySubClass(Long subClassID) {
        Map<Object, Object> map = new HashMap<>();
        map.put("subClassID", subClassID);
        List<Product> productList = productDao.selectProductBySubClass(map);
        return ResponseResult.success(productList);
    }

    @Override
    public ResponseResult selectAllProduct() {
        Map map = new HashMap();
        List<Product> productList = productDao.selectProductList(map);
        List<ServiceProduct> productServiceList = serviceProductDao.selectProductList(map);
        List<ProductAndServicVO> productAndServicVOList = new ArrayList<>();
        for (ServiceProduct serviceProduct : productServiceList) {
            ProductAndServicVO productAndServicVO = new ProductAndServicVO();
            productAndServicVO.setProductCode(serviceProduct.getProductCode());
            productAndServicVO.setProductName(serviceProduct.getProductName());
            productAndServicVO.setRetailPrice(serviceProduct.getRetailPrice());
            productAndServicVO.setStoreIds(serviceProduct.getStoreIds());
            productAndServicVO.setSubClassID(serviceProduct.getSubClassID());
            productAndServicVO.setProductType(ProductTypeEnum.SERVICE.getCode());
            productAndServicVO.setUnitId(serviceProduct.getUnitId());
            productAndServicVO.setProductSubordinate(0);
            productAndServicVOList.add(productAndServicVO);
        }
        for (Product product : productList) {
            ProductAndServicVO productAndServicVO = new ProductAndServicVO();
            productAndServicVO.setProductCode(product.getProductCode());
            productAndServicVO.setProductName(product.getProductName());
            productAndServicVO.setRetailPrice(product.getRetailPrice());
            productAndServicVO.setSubClassID(product.getSubClassID());
            productAndServicVO.setStoreIds(product.getStoreIds());
            productAndServicVO.setProductType(ProductTypeEnum.PRODUCT.getCode());
            productAndServicVO.setUnitId(product.getUnitId());
            productAndServicVO.setProductSubordinate(Integer.parseInt(product.getProductSubordinate()));
            productAndServicVOList.add(productAndServicVO);
        }
        return ResponseResult.success(productAndServicVOList);
    }

    @Override
    public ResponseResult selectProductTypeEnum() {
        Map map = EnumUtil.getEnumToMap(ProductTypeEnum.class);

        return ResponseResult.success(map);
    }

    @Override
    public ResponseResult uploadPic(MultipartFile[] productFiles, MultipartFile[] activityProductFiles, MultipartFile file, String type) {
        String imageUrl;
        String imageType;
        Integer imageWidth;
        Integer imageHeight;
        String picCode = "";
        String picAicCode = "";
        //存放图片id
        Map picListMap = new HashMap<>();
        if ("1".equals(type)) {
            //产品
            picCode = ImageTypeEnum.PRODUCT.getCode();
            picAicCode = ImageTypeEnum.PRODUCTACTIVITYIMAGE.getCode();
        } else if ("2".equals(type)) {
            picCode = ImageTypeEnum.SERVICEPRODUCT.getCode();
            picAicCode = ImageTypeEnum.SERVICEPRODUCTACTIVITYIMAGE.getCode();
        } else {
            picCode = ImageTypeEnum.FUWENBENPIC.getCode();
        }
        if (productFiles != null && productFiles.length > 0) {
            List imageIdlist = new ArrayList<>();
            try {
                for (MultipartFile productFile : productFiles) {
                    ProductImage productImage = new ProductImage();
                    ResponseResult responseResult = ftpFileUploadController.uploadImg(productFile, picCode);
                    imageUrl = ((JSONObject) responseResult.getResult()).getString("imageUrl");
                    imageType = ((JSONObject) responseResult.getResult()).getString("imageType");
                    imageWidth = ((JSONObject) responseResult.getResult()).getInteger("imageWidth");
                    imageHeight = ((JSONObject) responseResult.getResult()).getInteger("imageHeight");
                    productImage.setImageUrl(imageUrl);
                    productImage.setImageHeight(imageHeight);
                    productImage.setImageType(imageType);
                    productImage.setImageWidth(imageWidth);
                    productImageDao.insertProductImage(productImage);
                    String imageId = String.valueOf(productImage.getProductImageId());
                    imageIdlist.add(imageId);
                }
                picListMap.put("image", imageIdlist);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //活动商品图片
        if (activityProductFiles != null && activityProductFiles.length > 0) {
            List actimageIdlist = new ArrayList<>();
            try {
                for (MultipartFile activityProductFile : activityProductFiles) {
                    ProductActivityImage productActivityImage = new ProductActivityImage();
                    ResponseResult responseResult = ftpFileUploadController.uploadImg(activityProductFile, picAicCode);
                    imageUrl = ((JSONObject) responseResult.getResult()).getString("imageUrl");
                    imageType = ((JSONObject) responseResult.getResult()).getString("imageType");
                    imageWidth = ((JSONObject) responseResult.getResult()).getInteger("imageWidth");
                    imageHeight = ((JSONObject) responseResult.getResult()).getInteger("imageHeight");
                    //给商品活动图对象赋值
                    productActivityImage.setImageUrl(imageUrl);
                    productActivityImage.setImageHeight(imageHeight);
                    productActivityImage.setImageType(imageType);
                    productActivityImage.setImageWidth(imageWidth);
                    productActivityImageDao.insertProductActivity(productActivityImage);
                    String activityImageId = String.valueOf(productActivityImage.getActivityImageId());
                    actimageIdlist.add(activityImageId);
                }
                picListMap.put("actimage", actimageIdlist);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //富文本上传的图片
        if (file != null) {
            picListMap = new HashMap<>();
            try {
                ProductImage productImage = new ProductImage();
                ResponseResult responseResult = ftpFileUploadController.uploadImg(file, picCode);
                imageUrl = ((JSONObject) responseResult.getResult()).getString("imageUrl");
                imageType = ((JSONObject) responseResult.getResult()).getString("imageType");
                imageWidth = ((JSONObject) responseResult.getResult()).getInteger("imageWidth");
                imageHeight = ((JSONObject) responseResult.getResult()).getInteger("imageHeight");
                productImage.setImageUrl(imageUrl);
                productImage.setImageHeight(imageHeight);
                productImage.setImageType(imageType);
                productImage.setImageWidth(imageWidth);
                productImageDao.insertProductImage(productImage);
                String imageId = String.valueOf(productImage.getProductImageId());

                FuwenbenVO fuwenbenVO = new FuwenbenVO();
                fuwenbenVO.setTitle("富文本");
                fuwenbenVO.setSrc("http://" + imageUrl);
//                String data = JSON.toJSONString(fuwenbenVO);
                picListMap.put("code", 0);
                picListMap.put("msg", "success");
                picListMap.put("data", fuwenbenVO);
            } catch (IOException e) {
                e.printStackTrace();
                FuwenbenVO fuwenbenVO = new FuwenbenVO();
                picListMap.put("code", -1);
                picListMap.put("msg", "上传失败");
                picListMap.put("data", fuwenbenVO);
                ResponseResult.success(picListMap);
            }
        }
        return ResponseResult.success(picListMap);
    }

    @Override
    public ResponseResult addProductSubcompany(String productCode, Long subCompanyId, Integer type) throws InvocationTargetException, IllegalAccessException {
        JSONArray jsonArray = JSON.parseArray(productCode);
        List<ProducSubcompany> productSubcompanyList = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            ProducSubcompany producSubcompany = new ProducSubcompany();
            producSubcompany.setSubcompanyId(subCompanyId);
            producSubcompany.setProductCode(jsonArray.getString(i));
            productSubcompanyList.add(producSubcompany);
        }
        //判断提取的商品子公司是否存在
        //查询子公司和商品池中间表
        //先搜索他的总公司  - 子公司查看总公司
        Map mapSubcomapny = (Map) storeApi.selectSubsidiaryByID(subCompanyId).getResult();
        Map map1 = new HashMap();
        map1.put("companyId", mapSubcomapny.get("parentCompany").toString());
        map1.put("subcompanyId", subCompanyId);
        List<ProducSubcompany> producSubcompanylistAdd = new ArrayList<>();
        List<String> producSubcompanylistExist = new ArrayList<>();
        if (type == 1) {
            List<Product> productlistAll = productDao.selectProductSubcompany(map1);
            for (ProducSubcompany producSubcompanyItem : productSubcompanyList) {
                boolean flag = true;
                for (Product producAllItem : productlistAll) {
                    if (producSubcompanyItem.getProductCode().equals(producAllItem.getProductCode())) {
                        //如果已经有了addProductSubcompany
                        flag = false;
                    }
                }

                if (flag == false) {
                    producSubcompanylistExist.add(producSubcompanyItem.getProductCode());
                } else {
                    producSubcompanylistAdd.add(producSubcompanyItem);
                }
            }

        } else if (type == 2) {

            List<ServiceProduct> serviceProductListAll = serviceProductDao.selectServiceProductSubcompany(map1);
            for (ProducSubcompany producSubcompanyItem : productSubcompanyList) {
                boolean flag = true;
                for (ServiceProduct serviceProducAllItem : serviceProductListAll) {
                    if (producSubcompanyItem.getProductCode().equals(serviceProducAllItem.getProductCode())) {
                        //如果已经有了
                        flag = false;
                    }
                }

                if (flag == false) {
                    producSubcompanylistExist.add(producSubcompanyItem.getProductCode());
                } else {
                    producSubcompanylistAdd.add(producSubcompanyItem);
                }
            }

        }

        if (producSubcompanylistAdd.size() != 0) {
            Map map = new HashMap();
            map.put("list", producSubcompanylistAdd);
            int result = productDao.addProductSubcompany(map);
            String k3Id;
            String k3Number;
            Map paramsmap;
            Map resultHashMap;
            Map resulut;
            Map resultStatus;
            Boolean isSuccess;
            Integer id = null;
            String number = null;
            Long temp = 0L;
            /*if (result > 0) {
                //用户名密码
                HashMap<String, String> userNameAndPassword = getUserNameAndPassword(null);
                String dataCentre = userNameAndPassword.get("dataCentre");
                String userName = userNameAndPassword.get("userName");
                String password = userNameAndPassword.get("password");
                for (ProducSubcompany producSubcompany : producSubcompanylistAdd) {
                    productCode = producSubcompany.getProductCode();
                    if (type == 1) {
                        //产品
                        Product product = productDao.selectProductByCode(productCode);
                        k3Id = product.getK3Id();
                        k3Number = product.getK3Number();
                        ProductVO productvo = new ProductVO();
                        BeanUtils.copyProperties(product, productvo);
                        //反审核
                        ResponseResult unAudit = k3CLOUDApi.unAuditProduct(dataCentre,userName,password,k3Number, k3Id);
                        //登录失败
                        if (unAudit.getResult().equals("暂未登录")) {
                            ResponseResult nosave = new ResponseResult();
                            paramsmap = new HashMap<>();
                            ResponseResult error = error(dataCentre,userName,password,productvo, null, temp, "unAudit", paramsmap, unAudit);
                            return error;
                        }
                        //登录成功----》进行判断反审核是否成功
                        if (unAudit.isSuccess()) {
                            resultHashMap = (HashMap<String, Object>) unAudit.getResult();
                            resulut = (HashMap<String, Object>) resultHashMap.get("Result");
                            resultStatus = (HashMap<String, Object>) resulut.get("ResponseStatus");
                            isSuccess = (Boolean) resultStatus.get("IsSuccess");
                            if (isSuccess) {
                                //获取审核成功后的number和id
                                id = (Integer) resulut.get("Id");
                                number = (String) resulut.get("Number");
                                //审核成功后进行分配
                                ResponseResult allocate = k3CLOUDApi.allocate(0, "");
                                if (unAudit.isSuccess()) {
                                    resultHashMap = (HashMap<String, Object>) unAudit.getResult();
                                    resulut = (HashMap<String, Object>) resultHashMap.get("Result");
                                    resultStatus = (HashMap<String, Object>) resulut.get("ResponseStatus");
                                    isSuccess = (Boolean) resultStatus.get("IsSuccess");
                                    if (isSuccess) {
                                        return ResponseResult.success();
                                    }
                                    //分配失败
                                    paramsmap = new HashMap<>();
                                    ResponseResult error = error(dataCentre,userName,password,productvo, null, temp, "save", paramsmap, allocate);
                                    return error;
                                }
                                //分配失败
                                paramsmap = new HashMap<>();
                                ResponseResult error = error(dataCentre,userName,password,productvo, null, temp, "save", paramsmap, allocate);
                                return error;
                            }
                            //反审核失败
                            paramsmap = new HashMap<>();
                            paramsmap.put("id", id);
                            paramsmap.put("number", number);
                            ResponseResult error = error(dataCentre,userName,password, productvo, null, temp, "unAudit", paramsmap, unAudit);
                            return error;
                        }
                        //审核失败
                        paramsmap = new HashMap<>();
                        ResponseResult error = error(dataCentre,userName,password,productvo, null, temp, "unAudit", paramsmap, unAudit);
                        return error;
                    } else {
                        ServiceProduct serviceProduct = serviceProductDao.selectProductByCode(productCode);
                        k3Id = serviceProduct.getK3Id();
                        k3Number = serviceProduct.getK3Number();
                        ServiceProductVO serviceProductVO = new ServiceProductVO();
                        BeanUtils.copyProperties(serviceProduct, serviceProductVO);
                        //反审核
                        ResponseResult unAudit = k3CLOUDApi.unAuditProduct(dataCentre,userName,password,k3Number, k3Id);
                        //登录失败
                        if (unAudit.getResult().equals("暂未登录")) {
                            ResponseResult nosave = new ResponseResult();
                            paramsmap = new HashMap<>();
                            ResponseResult error = error(dataCentre,userName,password,null, serviceProductVO, temp, "unAudit", paramsmap, unAudit);
                            return error;
                        }
                        //登录成功----》进行判断反审核是否成功
                        if (unAudit.isSuccess()) {
                            resultHashMap = (HashMap<String, Object>) unAudit.getResult();
                            resulut = (HashMap<String, Object>) resultHashMap.get("Result");
                            resultStatus = (HashMap<String, Object>) resulut.get("ResponseStatus");
                            isSuccess = (Boolean) resultStatus.get("IsSuccess");
                            if (isSuccess) {
                                //获取审核成功后的number和id
                                id = (Integer) resulut.get("Id");
                                number = (String) resulut.get("Number");
                                //审核成功后进行分配
                                ResponseResult allocate = k3CLOUDApi.allocate(0, "");
                                if (unAudit.isSuccess()) {
                                    resultHashMap = (HashMap<String, Object>) unAudit.getResult();
                                    resulut = (HashMap<String, Object>) resultHashMap.get("Result");
                                    resultStatus = (HashMap<String, Object>) resulut.get("ResponseStatus");
                                    isSuccess = (Boolean) resultStatus.get("IsSuccess");
                                    if (isSuccess) {
                                        return ResponseResult.success();
                                    }
                                    //分配失败
                                    paramsmap = new HashMap<>();
                                    ResponseResult error = error(dataCentre,userName,password,null, serviceProductVO, temp, "allocate", paramsmap, allocate);
                                    return error;
                                }
                                //分配失败
                                paramsmap = new HashMap<>();
                                ResponseResult error = error(dataCentre,userName,password,null, serviceProductVO, temp, "allocate", paramsmap, allocate);
                                return error;
                            }
                            //反审核失败
                            paramsmap = new HashMap<>();
                            paramsmap.put("id", id);
                            paramsmap.put("number", number);
                            ResponseResult error = error(dataCentre,userName,password,null, serviceProductVO, temp, "unAudit", paramsmap, unAudit);
                            return error;
                        }
                        //审核失败
                        paramsmap = new HashMap<>();
                        ResponseResult error = error(dataCentre,userName,password,null, serviceProductVO, temp, "unAudit", paramsmap, unAudit);
                        return error;
                    }
                }
            }*/
        }

        if (producSubcompanylistExist.size() == 0) {
            producSubcompanylistExist = null;
        }
        return ResponseResult.success(producSubcompanylistExist);
    }

    @Override
    public ResponseResult addProductStore(String productCode, Long storeId, Integer type) {
        JSONArray jsonArray = JSON.parseArray(productCode);
        List<ProducStore> producStoreList = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            ProducStore producStore = new ProducStore();
            producStore.setStoreId(storeId);
            producStore.setProductCode(jsonArray.getString(i));
            producStoreList.add(producStore);
        }
        //判断提取的商品子公司是否存在
        //查询分公司和商品池中间表
        Map map1 = new HashMap();
        //先搜索他的子公司 - 门店查看子公司
        Long storeIdIn = storeId;
        Map mapStore = (Map) storeApi.selectStoreById(storeIdIn).getResult();
        Map mapSubcomapny = (Map) storeApi.selectSubsidiaryByID(Long.parseLong(mapStore.get("subCompanyId").toString())).getResult();
        map1.put("companyId", mapSubcomapny.get("parentCompany").toString());
        map1.put("storeId", storeId);
        List<ProducStore> producStorelistAdd = new ArrayList<>();
        List<String> producStorelistExist = new ArrayList<>();
        if (type == 1) {
            List<Product> produclistAll = productDao.selectProductStore(map1);
            for (ProducStore producStoreItem : producStoreList) {
                boolean flag = true;
                for (Product producAllItem : produclistAll) {
                    if (producStoreItem.getProductCode().equals(producAllItem.getProductCode())) {
                        //如果已经有了
                        flag = false;
                    }
                }
                if (flag == false) {
                    producStorelistExist.add(producStoreItem.getProductCode());
                } else {
                    producStorelistAdd.add(producStoreItem);
                }
            }
        } else if (type == 2) {
            List<ServiceProduct> serviceProduclistAll = serviceProductDao.selectServiceProductStore(map1);
            for (ProducStore producStoreItem : producStoreList) {
                boolean flag = true;
                for (ServiceProduct serivceProducAllItem : serviceProduclistAll) {
                    if (producStoreItem.getProductCode().equals(serivceProducAllItem.getProductCode())) {
                        //如果已经有了
                        flag = false;
                    }
                }
                if (flag == false) {
                    producStorelistExist.add(producStoreItem.getProductCode());
                } else {
                    producStorelistAdd.add(producStoreItem);
                }
            }
        }


        if (producStorelistAdd.size() != 0) {
            Map map = new HashMap();
            map.put("list", producStoreList);
            productDao.addProductStore(map);
        }
        if (producStorelistExist.size() == 0) {
            producStorelistExist = null;
        }
        return ResponseResult.success(producStorelistExist);
    }

    @Override
    public ResponseResult updateProductSales(String productCode, Integer type, String upOrDown) {
        Map map = new HashMap();
        map.put("productCode", productCode);
        map.put("upOrDown", upOrDown);
        if (type == ProductTypeEnum.PRODUCT.getCode()) {
            //如果是实体商品
            productDao.updateProductSales(map);
        } else if (type == ProductTypeEnum.SERVICE.getCode()) {
            //如果是护理商品
            serviceProductDao.updateProductSales(map);
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult updateStockQuantity(String productCode, String upOrDown) {
        Map map = new HashMap();
        map.put("productCode", productCode);
        map.put("upOrDown", upOrDown);
        productDao.updateStockQuantity(map);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult batchAllocationSubCompany(String companyId, String companyType, String productArrayStr, String subcompanyIdArrayStr) {
        //本系统商品分配
        JSONArray productArrary = JSON.parseArray(productArrayStr);
        JSONArray subcompanyIdArray = JSON.parseArray(subcompanyIdArrayStr);
        List<Map> mapList = new ArrayList<>();
        for (int i = 0; i < subcompanyIdArray.size(); i++) {
            ProducSubcompany producSubcompany = new ProducSubcompany();
            for (int j = 0; j < productArrary.size(); j++) {
                List<ProducSubcompany> producSubcompanylistAdd = new ArrayList<>();
                producSubcompany.setProductCode(productArrary.getJSONObject(j).getString("productCode"));
                producSubcompany.setSubcompanyId(subcompanyIdArray.getJSONObject(i).getLong("orgId"));
                //查看是否已经分配
                Map mapCheck = new HashMap();
                mapCheck.put("productCode", productArrary.getJSONObject(j).getString("productCode"));
                mapCheck.put("subcompanyId", subcompanyIdArray.getJSONObject(i).getLong("orgId"));
                int resultInt = productDao.checkProductSubCompany(mapCheck);
                if (resultInt > 0) {
                    Map mapResult = new HashMap();
                    //根据商品编号查看商品名称
                    mapResult.put("商品名称", productArrary.getJSONObject(j).getString("productName"));
                    mapResult.put("组织名称", subcompanyIdArray.getJSONObject(i).getString("orgName"));
                    mapList.add(mapResult);
                } else {
                    producSubcompanylistAdd.add(producSubcompany);
                    Map map = new HashMap();
                    map.put("list", producSubcompanylistAdd);
                    productDao.addProductSubcompany(map);
                }
            }
        }


        //k3分配接入
        String PkIds = "";
        String TOrgIds = "";
        for (int i = 0; i < productArrary.size(); i++) {
            PkIds = PkIds + "," + productArrary.getJSONObject(i).getString("productK3Id");
        }
        for (int i = 0; i < subcompanyIdArray.size(); i++) {
            TOrgIds = TOrgIds + "," + subcompanyIdArray.getJSONObject(i).getString("k3Id");
        }
        PkIds = PkIds.substring(1);
        TOrgIds = TOrgIds.substring(1);
        Map companyMap = new HashMap();
        if (companyType.equals("1")) {
            //如果是总公司
            companyMap = (Map) (storeApi.selectCompanyByID(Long.parseLong(companyId)).getResult());
        } else if (companyType.equals("2")) {
            //如果是子公司
            Map subCompanyMap = (Map) (storeApi.selectSubsidiaryByID(Long.parseLong(companyId)).getResult());
            companyMap = (Map) (storeApi.selectCompanyByID(Long.parseLong(subCompanyMap.get("parentCompany").toString())).getResult());
        }


        ResponseResult responseResult = k3CLOUDApi.allocate(
                companyMap.get("dataCentre").toString(),
                companyMap.get("yewuDataCentreUserName").toString(),
                companyMap.get("yewuDataCentrePassword").toString(),
                PkIds,
                TOrgIds
        );
        if (responseResult.getResult().equals("暂未登录")) {
            return ResponseResult.success("K3组织未生成，K3用户密码错误");
        }
        if (!((Map) (((Map) (((Map) (responseResult.getResult())).get("Result"))).get("ResponseStatus"))).get("IsSuccess").toString().equals("true")) {
            return ResponseResult.success("商品分配成功，K3分配未成功:" + ((Map) (((List) (((Map) (((Map) (((Map) (responseResult.getResult())).get("Result"))).get("ResponseStatus"))).get("Errors"))).get(0))).get("Message").toString());
        }
        if (mapList.size() != 0) {
            return ResponseResult.success("分配成功,k3分配成功,但以下商品已经分配组织" + mapList);
        } else {
            return ResponseResult.success("分配成功,k3分配成功");
        }

    }

    @Override
    public ResponseResult batchAllocationStore(String companyId, String companyType, String productArrayStr, String storeIdArrayStr) {
        //本系统商品分配
        JSONArray productArrary = JSON.parseArray(productArrayStr);
        JSONArray storeIdArray = JSON.parseArray(storeIdArrayStr);

        List<Map> mapList = new ArrayList<>();
        for (int i = 0; i < storeIdArray.size(); i++) {
            ProducStore producStore = new ProducStore();
            for (int j = 0; j < productArrary.size(); j++) {
                List<ProducStore> producStorelistAdd = new ArrayList<>();
                producStore.setProductCode(productArrary.getJSONObject(j).getString("productCode"));
                producStore.setStoreId(storeIdArray.getJSONObject(i).getLong("orgId"));
                //查看是否已经分配
                Map mapCheck = new HashMap();
                mapCheck.put("productCode", productArrary.getJSONObject(j).getString("productCode"));
                mapCheck.put("storeId", storeIdArray.getJSONObject(i).getLong("orgId"));
                int resultInt = productDao.checkProductStore(mapCheck);
                if (resultInt > 0) {
                    Map mapResult = new HashMap();
                    mapResult.put("商品名称", productArrary.getJSONObject(j).getString("productName"));
                    mapResult.put("组织名称", storeIdArray.getJSONObject(i).getString("orgName"));
                    mapList.add(mapResult);
                } else {
                    producStorelistAdd.add(producStore);
                    Map map = new HashMap();
                    map.put("list", producStorelistAdd);
                    productDao.addProductStore(map);
                }
            }
        }
        //k3分配接入
        String PkIds = "";
        String TOrgIds = "";
        for (int i = 0; i < productArrary.size(); i++) {
            PkIds = PkIds + "," + productArrary.getJSONObject(i).getString("productK3Id");
        }
        for (int i = 0; i < storeIdArray.size(); i++) {
            TOrgIds = TOrgIds + "," + storeIdArray.getJSONObject(i).getString("k3Id");
        }
        PkIds = PkIds.substring(1);
        TOrgIds = TOrgIds.substring(1);
        Map companyMap = new HashMap();
        if (companyType.equals("1")) {
            //如果是总公司
            companyMap = (Map) (storeApi.selectCompanyByID(Long.parseLong(companyId)).getResult());
        } else if (companyType.equals("2")) {
            //如果是子公司
            Map subCompanyMap = (Map) (storeApi.selectSubsidiaryByID(Long.parseLong(companyId)).getResult());
            companyMap = (Map) (storeApi.selectCompanyByID(Long.parseLong(subCompanyMap.get("parentCompany").toString())).getResult());
        }


        ResponseResult responseResult = k3CLOUDApi.allocate(
                companyMap.get("dataCentre").toString(),
                companyMap.get("yewuDataCentreUserName").toString(),
                companyMap.get("yewuDataCentrePassword").toString(),
                PkIds,
                TOrgIds
        );
        if (responseResult.getResult().equals("暂未登录")) {
            return ResponseResult.success("K3组织未生成，K3用户密码错误");
        }
        if (!((Map) (((Map) (((Map) (responseResult.getResult())).get("Result"))).get("ResponseStatus"))).get("IsSuccess").toString().equals("true")) {
            return ResponseResult.success("商品分配成功，K3分配未成功:" + ((Map) (((List) (((Map) (((Map) (((Map) (responseResult.getResult())).get("Result"))).get("ResponseStatus"))).get("Errors"))).get(0))).get("Message").toString());
        }
        if (mapList.size() != 0) {
            return ResponseResult.success("分配成功,k3分配成功,但以下商品已经分配组织" + mapList);
        } else {
            return ResponseResult.success("分配成功,k3分配成功");
        }
    }

    @Override
    public ResponseResult batchCancelAllocationSubCompany(String companyId, String companyType, String productArrayStr, String subcompanyIdArrayStr) {
        //本系统商品取消分配
        JSONArray productArrary = JSON.parseArray(productArrayStr);
        JSONArray subcompanyIdArray = JSON.parseArray(subcompanyIdArrayStr);
        for (int i = 0; i < subcompanyIdArray.size(); i++) {
            for (int j = 0; j < productArrary.size(); j++) {
                Map map = new HashMap();
                map.put("productCode", productArrary.getJSONObject(j).getString("productCode"));
                map.put("subcompanyId", subcompanyIdArray.getJSONObject(i).getLong("orgId"));
                productDao.deleteProductSubcompany(map);
            }
        }
        return ResponseResult.success("取消分配成功,请登陆k3系统进行手动物料取消分配");
    }

    @Override
    public ResponseResult batchCancelAllocationStore(String companyId, String companyType, String productArrayStr, String storeIdArrayStr) {
        //本系统商品取消分配
        JSONArray productArrary = JSON.parseArray(productArrayStr);
        JSONArray storeIdArray = JSON.parseArray(storeIdArrayStr);
        for (int i = 0; i < storeIdArray.size(); i++) {
            for (int j = 0; j < productArrary.size(); j++) {
                Map map = new HashMap();
                map.put("productCode", productArrary.getJSONObject(j).getString("productCode"));
                map.put("storeId", storeIdArray.getJSONObject(i).getLong("orgId"));
                productDao.deleteProductStore(map);
            }
        }
        return ResponseResult.success("取消分配成功,请登陆k3系统进行手动物料取消分配");
    }


    public static StringBuffer loadArr(String[] strs, StringBuffer str) {
        for (int i = 0; i < strs.length; i++) {
            str.append(strs[i]);
            if (i != strs.length - 1) {
                str.append(",");
            }
        }
        return str;
    }


    //获取数据中心、用户名和密码
    public HashMap<String, String> getUserNameAndPassword(Long companyId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("dataCentre", null);
        map.put("userName", null);
        map.put("password", null);
        ResponseResult responseResult = storeApi.selectCompanyByID(1L);
        if (responseResult.isSuccess()) {
            HashMap<String, Object> result = (HashMap<String, Object>) responseResult.getResult();
            String dataCentre = String.valueOf(result.get("dataCentre"));
            String dataCentreUserName = String.valueOf(result.get("dataCentreUserName"));
            String dataCentrePassword = String.valueOf(result.get("dataCentrePassword"));
            map.put("dataCentre", dataCentre);
            map.put("userName", dataCentreUserName);
            map.put("password", dataCentrePassword);
        }
        return map;
    }


    public ResponseResult error(String acctId, String userName, String password, ProductVO productvo, ServiceProductVO serviceProductVO, Long temp, String methodName, Map map, ResponseResult result) {
        String remark;
        String method;
        String insJ = new String();
        String excJ = new String();
        ResponseResult responseResult = new ResponseResult();

        if (productvo != null) {
            if (methodName.equals("nosave")) {
                remark = "k3的登录失败！";
                method = "login";
                insJ = "{\"fName\":" + productvo.getProductName() + "," + "\"fNumberCreateOrg\":\"100" + "," + "\"fNumberUseOrg\":\"100" + "\"fbarcodeentityCmkListStr\":\"[{\"FEntryID\":0,\"FCodeType_CMK\":\"\",\"FUnitId_CMK\":{\"FNUMBER\":\"\"}}]\"" + "\"fErpClsID\":\"1\"" + "\"fBaseUnitId\":\"1\"" + "\"fCategoryID\":\"1\""
                        + "\"fSuite\":\"1\"" + "\"fStoreUnitID\":\"1\"" + "\"fCurrencyId\":\"1\"" + "\"fUnitConvertDir\":\"1\"" + "\"fSNGenerateTime\":\"1\"" + "\"fSNManageType\":\"1\"" + "\"fSalePriceUnitId\":\"1\"" + "\"fSaleUnitId\":\"1\"" + "\"fPurchaseUnitId\":\"1\"" + "\"fPurchasePriceUnitId\":\"1\"" + "\"fQuotaType\":\"1\""
                        + "\"fPlanningStrategy\":\"1\"" + "\"fOrderPolicy\":\"1\"" + "\"fFixLeadTimeType\":\"1\"" + "\"fVarLeadTimeType\":\"1\"" + "\"fCheckLeadTimeType\":\"1\"" + "\"fOrderIntervalTimeType\":\"1\"" + "\"fReserveType\":\"1\"" + "\"fPlanOffsetTimeType\":\"1\"" + "\"fIssueType\":\"1\"" + "\"fOverControlMode\":\"1\""
                        + "\"fMinIssueUnitId\":\"1\"" + "\"fStandHourUnitId\":\"1\"" + "\"fBackFlushType\":\"1\"" + "\"fEntityInvPtyListStr\":\"[{\"FEntryID\":0,\"FInvPtyId\":{\"FNumber\":\"\"},\"FIsEnable\":\"false\",\"FIsAffectPrice\":\"false\",\"FIsAffectPlan\":\"false\",\"FIsAffectCost\":\"false\"}]\"" + "}";

                excJ = "{\"fName\":" + productvo.getProductName() + "," + "\"fNumberCreateOrg\":\"100" + "," + "\"fNumberUseOrg\":\"100" + "\"fbarcodeentityCmkListStr\":\"[{\"FEntryID\":0,\"FCodeType_CMK\":\"\",\"FUnitId_CMK\":{\"FNUMBER\":\"\"}}]\"" + "\"fErpClsID\":\"1\"" + "\"fBaseUnitId\":\"1\"" + "\"fCategoryID\":\"1\""
                        + "\"fSuite\":\"1\"" + "\"fStoreUnitID\":\"1\"" + "\"fCurrencyId\":\"1\"" + "\"fUnitConvertDir\":\"1\"" + "\"fSNGenerateTime\":\"1\"" + "\"fSNManageType\":\"1\"" + "\"fSalePriceUnitId\":\"1\"" + "\"fSaleUnitId\":\"1\"" + "\"fPurchaseUnitId\":\"1\"" + "\"fPurchasePriceUnitId\":\"1\"" + "\"fQuotaType\":\"1\""
                        + "\"fPlanningStrategy\":\"1\"" + "\"fOrderPolicy\":\"1\"" + "\"fFixLeadTimeType\":\"1\"" + "\"fVarLeadTimeType\":\"1\"" + "\"fCheckLeadTimeType\":\"1\"" + "\"fOrderIntervalTimeType\":\"1\"" + "\"fReserveType\":\"1\"" + "\"fPlanOffsetTimeType\":\"1\"" + "\"fIssueType\":\"1\"" + "\"fOverControlMode\":\"1\""
                        + "\"fMinIssueUnitId\":\"1\"" + "\"fStandHourUnitId\":\"1\"" + "\"fBackFlushType\":\"1\"" + "\"fEntityInvPtyListStr\":\"[{\"FEntryID\":0,\"FInvPtyId\":{\"FNumber\":\"\"},\"FIsEnable\":\"false\",\"FIsAffectPrice\":\"false\",\"FIsAffectPlan\":\"false\",\"FIsAffectCost\":\"false\"}]\"" + "}";


                return ResponseResult.error(new Error(ResponseCodeStockEnum.LOGIN_ERROR.getCode(),
                        ResponseCodeStockEnum.LOGIN_ERROR.getDesc() + result.getResult() + "添加产品失败"));
            }
            if (methodName.equals("save")) {
                remark = "k3的提交未调用！";
                method = "save";
                insJ = "{\"fName\":" + productvo.getProductName() + "," + "\"fNumberCreateOrg\":\"100" + "," + "\"fNumberUseOrg\":\"100" + "\"fbarcodeentityCmkListStr\":\"[{\"FEntryID\":0,\"FCodeType_CMK\":\"\",\"FUnitId_CMK\":{\"FNUMBER\":\"\"}}]\"" + "\"fErpClsID\":\"1\"" + "\"fBaseUnitId\":\"1\"" + "\"fCategoryID\":\"1\""
                        + "\"fSuite\":\"1\"" + "\"fStoreUnitID\":\"1\"" + "\"fCurrencyId\":\"1\"" + "\"fUnitConvertDir\":\"1\"" + "\"fSNGenerateTime\":\"1\"" + "\"fSNManageType\":\"1\"" + "\"fSalePriceUnitId\":\"1\"" + "\"fSaleUnitId\":\"1\"" + "\"fPurchaseUnitId\":\"1\"" + "\"fPurchasePriceUnitId\":\"1\"" + "\"fQuotaType\":\"1\""
                        + "\"fPlanningStrategy\":\"1\"" + "\"fOrderPolicy\":\"1\"" + "\"fFixLeadTimeType\":\"1\"" + "\"fVarLeadTimeType\":\"1\"" + "\"fCheckLeadTimeType\":\"1\"" + "\"fOrderIntervalTimeType\":\"1\"" + "\"fReserveType\":\"1\"" + "\"fPlanOffsetTimeType\":\"1\"" + "\"fIssueType\":\"1\"" + "\"fOverControlMode\":\"1\""
                        + "\"fMinIssueUnitId\":\"1\"" + "\"fStandHourUnitId\":\"1\"" + "\"fBackFlushType\":\"1\"" + "\"fEntityInvPtyListStr\":\"[{\"FEntryID\":0,\"FInvPtyId\":{\"FNumber\":\"\"},\"FIsEnable\":\"false\",\"FIsAffectPrice\":\"false\",\"FIsAffectPlan\":\"false\",\"FIsAffectCost\":\"false\"}]\"" + "}";

                excJ = "{\"fName\":" + productvo.getProductName() + "," + "\"fNumberCreateOrg\":\"100" + "," + "\"fNumberUseOrg\":\"100" + "\"fbarcodeentityCmkListStr\":\"[{\"FEntryID\":0,\"FCodeType_CMK\":\"\",\"FUnitId_CMK\":{\"FNUMBER\":\"\"}}]\"" + "\"fErpClsID\":\"1\"" + "\"fBaseUnitId\":\"1\"" + "\"fCategoryID\":\"1\""
                        + "\"fSuite\":\"1\"" + "\"fStoreUnitID\":\"1\"" + "\"fCurrencyId\":\"1\"" + "\"fUnitConvertDir\":\"1\"" + "\"fSNGenerateTime\":\"1\"" + "\"fSNManageType\":\"1\"" + "\"fSalePriceUnitId\":\"1\"" + "\"fSaleUnitId\":\"1\"" + "\"fPurchaseUnitId\":\"1\"" + "\"fPurchasePriceUnitId\":\"1\"" + "\"fQuotaType\":\"1\""
                        + "\"fPlanningStrategy\":\"1\"" + "\"fOrderPolicy\":\"1\"" + "\"fFixLeadTimeType\":\"1\"" + "\"fVarLeadTimeType\":\"1\"" + "\"fCheckLeadTimeType\":\"1\"" + "\"fOrderIntervalTimeType\":\"1\"" + "\"fReserveType\":\"1\"" + "\"fPlanOffsetTimeType\":\"1\"" + "\"fIssueType\":\"1\"" + "\"fOverControlMode\":\"1\""
                        + "\"fMinIssueUnitId\":\"1\"" + "\"fStandHourUnitId\":\"1\"" + "\"fBackFlushType\":\"1\"" + "\"fEntityInvPtyListStr\":\"[{\"FEntryID\":0,\"FInvPtyId\":{\"FNumber\":\"\"},\"FIsEnable\":\"false\",\"FIsAffectPrice\":\"false\",\"FIsAffectPlan\":\"false\",\"FIsAffectCost\":\"false\"}]\"" + "}";


                return ResponseResult.error(new Error(ResponseCodeStockEnum.SAVE_FAILED.getCode(),
                        ResponseCodeStockEnum.SAVE_FAILED.getDesc() + result.getResult() + "添加产品失败"));
            }
            if (methodName.equals("submit")) {
                remark = "k3的提交未调用！";
                method = "submit";
                Integer id = (Integer) map.get("id");
                String number = (String) map.get("number");
                insJ = "{\"id\":" + id + "," + "\"number\":" + number + "}";
                excJ = "{\"id\":" + id + "," + "\"number\":" + number + "}";

                return ResponseResult.error(new Error(ResponseCodeStockEnum.SUBMIT_FAILED.getCode(),
                        ResponseCodeStockEnum.SAVE_FAILED.getDesc() + result.getResult() + "添加产品失败"));
            }
            if (methodName.equals("audit")) {
                remark = "k3的审核未调用！";
                method = "audit";
                Integer id = (Integer) map.get("id");
                String number = (String) map.get("number");
                insJ = "{\"id\":" + id + "," + "\"number\":" + number + "}";
                excJ = "{\"id\":" + id + "," + "\"number\":" + number + "}";

                return ResponseResult.error(new Error(ResponseCodeStockEnum.AUDIT_FAILED.getCode(),
                        ResponseCodeStockEnum.SAVE_FAILED.getDesc() + result.getResult() + "添加产品失败"));
            }
        } else if (serviceProductVO != null) {
            if (methodName.equals("nosave")) {
                remark = "k3的登录失败！";
                method = "login";
                insJ = "{\"fName\":" + serviceProductVO.getProductName() + "," + "\"fNumberCreateOrg\":\"100" + "," + "\"fNumberUseOrg\":\"100" + "\"fbarcodeentityCmkListStr\":\"[{\"FEntryID\":0,\"FCodeType_CMK\":\"\",\"FUnitId_CMK\":{\"FNUMBER\":\"\"}}]\"" + "\"fErpClsID\":\"1\"" + "\"fBaseUnitId\":\"1\"" + "\"fCategoryID\":\"1\""
                        + "\"fSuite\":\"1\"" + "\"fStoreUnitID\":\"1\"" + "\"fCurrencyId\":\"1\"" + "\"fUnitConvertDir\":\"1\"" + "\"fSNGenerateTime\":\"1\"" + "\"fSNManageType\":\"1\"" + "\"fSalePriceUnitId\":\"1\"" + "\"fSaleUnitId\":\"1\"" + "\"fPurchaseUnitId\":\"1\"" + "\"fPurchasePriceUnitId\":\"1\"" + "\"fQuotaType\":\"1\""
                        + "\"fPlanningStrategy\":\"1\"" + "\"fOrderPolicy\":\"1\"" + "\"fFixLeadTimeType\":\"1\"" + "\"fVarLeadTimeType\":\"1\"" + "\"fCheckLeadTimeType\":\"1\"" + "\"fOrderIntervalTimeType\":\"1\"" + "\"fReserveType\":\"1\"" + "\"fPlanOffsetTimeType\":\"1\"" + "\"fIssueType\":\"1\"" + "\"fOverControlMode\":\"1\""
                        + "\"fMinIssueUnitId\":\"1\"" + "\"fStandHourUnitId\":\"1\"" + "\"fBackFlushType\":\"1\"" + "\"fEntityInvPtyListStr\":\"[{\"FEntryID\":0,\"FInvPtyId\":{\"FNumber\":\"\"},\"FIsEnable\":\"false\",\"FIsAffectPrice\":\"false\",\"FIsAffectPlan\":\"false\",\"FIsAffectCost\":\"false\"}]\"" + "}";

                excJ = "{\"fName\":" + serviceProductVO.getProductName() + "," + "\"fNumberCreateOrg\":\"100" + "," + "\"fNumberUseOrg\":\"100" + "\"fbarcodeentityCmkListStr\":\"[{\"FEntryID\":0,\"FCodeType_CMK\":\"\",\"FUnitId_CMK\":{\"FNUMBER\":\"\"}}]\"" + "\"fErpClsID\":\"1\"" + "\"fBaseUnitId\":\"1\"" + "\"fCategoryID\":\"1\""
                        + "\"fSuite\":\"1\"" + "\"fStoreUnitID\":\"1\"" + "\"fCurrencyId\":\"1\"" + "\"fUnitConvertDir\":\"1\"" + "\"fSNGenerateTime\":\"1\"" + "\"fSNManageType\":\"1\"" + "\"fSalePriceUnitId\":\"1\"" + "\"fSaleUnitId\":\"1\"" + "\"fPurchaseUnitId\":\"1\"" + "\"fPurchasePriceUnitId\":\"1\"" + "\"fQuotaType\":\"1\""
                        + "\"fPlanningStrategy\":\"1\"" + "\"fOrderPolicy\":\"1\"" + "\"fFixLeadTimeType\":\"1\"" + "\"fVarLeadTimeType\":\"1\"" + "\"fCheckLeadTimeType\":\"1\"" + "\"fOrderIntervalTimeType\":\"1\"" + "\"fReserveType\":\"1\"" + "\"fPlanOffsetTimeType\":\"1\"" + "\"fIssueType\":\"1\"" + "\"fOverControlMode\":\"1\""
                        + "\"fMinIssueUnitId\":\"1\"" + "\"fStandHourUnitId\":\"1\"" + "\"fBackFlushType\":\"1\"" + "\"fEntityInvPtyListStr\":\"[{\"FEntryID\":0,\"FInvPtyId\":{\"FNumber\":\"\"},\"FIsEnable\":\"false\",\"FIsAffectPrice\":\"false\",\"FIsAffectPlan\":\"false\",\"FIsAffectCost\":\"false\"}]\"" + "}";


                return ResponseResult.error(new Error(ResponseCodeStockEnum.LOGIN_ERROR.getCode(),
                        ResponseCodeStockEnum.LOGIN_ERROR.getDesc() + result.getResult() + "添加服务产品失败"));
            }
            if (methodName.equals("save")) {
                remark = "k3的提交未调用！";
                method = "save";
                insJ = "{\"fName\":" + serviceProductVO.getProductName() + "," + "\"fNumberCreateOrg\":\"100" + "," + "\"fNumberUseOrg\":\"100" + "\"fbarcodeentityCmkListStr\":\"[{\"FEntryID\":0,\"FCodeType_CMK\":\"\",\"FUnitId_CMK\":{\"FNUMBER\":\"\"}}]\"" + "\"fErpClsID\":\"1\"" + "\"fBaseUnitId\":\"1\"" + "\"fCategoryID\":\"1\""
                        + "\"fSuite\":\"1\"" + "\"fStoreUnitID\":\"1\"" + "\"fCurrencyId\":\"1\"" + "\"fUnitConvertDir\":\"1\"" + "\"fSNGenerateTime\":\"1\"" + "\"fSNManageType\":\"1\"" + "\"fSalePriceUnitId\":\"1\"" + "\"fSaleUnitId\":\"1\"" + "\"fPurchaseUnitId\":\"1\"" + "\"fPurchasePriceUnitId\":\"1\"" + "\"fQuotaType\":\"1\""
                        + "\"fPlanningStrategy\":\"1\"" + "\"fOrderPolicy\":\"1\"" + "\"fFixLeadTimeType\":\"1\"" + "\"fVarLeadTimeType\":\"1\"" + "\"fCheckLeadTimeType\":\"1\"" + "\"fOrderIntervalTimeType\":\"1\"" + "\"fReserveType\":\"1\"" + "\"fPlanOffsetTimeType\":\"1\"" + "\"fIssueType\":\"1\"" + "\"fOverControlMode\":\"1\""
                        + "\"fMinIssueUnitId\":\"1\"" + "\"fStandHourUnitId\":\"1\"" + "\"fBackFlushType\":\"1\"" + "\"fEntityInvPtyListStr\":\"[{\"FEntryID\":0,\"FInvPtyId\":{\"FNumber\":\"\"},\"FIsEnable\":\"false\",\"FIsAffectPrice\":\"false\",\"FIsAffectPlan\":\"false\",\"FIsAffectCost\":\"false\"}]\"" + "}";

                excJ = "{\"fName\":" + serviceProductVO.getProductName() + "," + "\"fNumberCreateOrg\":\"100" + "," + "\"fNumberUseOrg\":\"100" + "\"fbarcodeentityCmkListStr\":\"[{\"FEntryID\":0,\"FCodeType_CMK\":\"\",\"FUnitId_CMK\":{\"FNUMBER\":\"\"}}]\"" + "\"fErpClsID\":\"1\"" + "\"fBaseUnitId\":\"1\"" + "\"fCategoryID\":\"1\""
                        + "\"fSuite\":\"1\"" + "\"fStoreUnitID\":\"1\"" + "\"fCurrencyId\":\"1\"" + "\"fUnitConvertDir\":\"1\"" + "\"fSNGenerateTime\":\"1\"" + "\"fSNManageType\":\"1\"" + "\"fSalePriceUnitId\":\"1\"" + "\"fSaleUnitId\":\"1\"" + "\"fPurchaseUnitId\":\"1\"" + "\"fPurchasePriceUnitId\":\"1\"" + "\"fQuotaType\":\"1\""
                        + "\"fPlanningStrategy\":\"1\"" + "\"fOrderPolicy\":\"1\"" + "\"fFixLeadTimeType\":\"1\"" + "\"fVarLeadTimeType\":\"1\"" + "\"fCheckLeadTimeType\":\"1\"" + "\"fOrderIntervalTimeType\":\"1\"" + "\"fReserveType\":\"1\"" + "\"fPlanOffsetTimeType\":\"1\"" + "\"fIssueType\":\"1\"" + "\"fOverControlMode\":\"1\""
                        + "\"fMinIssueUnitId\":\"1\"" + "\"fStandHourUnitId\":\"1\"" + "\"fBackFlushType\":\"1\"" + "\"fEntityInvPtyListStr\":\"[{\"FEntryID\":0,\"FInvPtyId\":{\"FNumber\":\"\"},\"FIsEnable\":\"false\",\"FIsAffectPrice\":\"false\",\"FIsAffectPlan\":\"false\",\"FIsAffectCost\":\"false\"}]\"" + "}";


                return ResponseResult.error(new Error(ResponseCodeStockEnum.SAVE_FAILED.getCode(),
                        ResponseCodeStockEnum.SAVE_FAILED.getDesc() + result.getResult() + "添加服务产品失败"));
            }
            if (methodName.equals("submit")) {
                remark = "k3的提交未调用！";
                method = "submit";
                Integer id = (Integer) map.get("id");
                String number = (String) map.get("number");
                insJ = "{\"id\":" + id + "," + "\"number\":" + number + "}";
                excJ = "{\"id\":" + id + "," + "\"number\":" + number + "}";

                return ResponseResult.error(new Error(ResponseCodeStockEnum.SUBMIT_FAILED.getCode(),
                        ResponseCodeStockEnum.SAVE_FAILED.getDesc() + result.getResult() + "添加服务产品失败"));
            }
            if (methodName.equals("audit")) {
                remark = "k3的审核未调用！";
                method = "audit";
                Integer id = (Integer) map.get("id");
                String number = (String) map.get("number");
                insJ = "{\"id\":" + id + "," + "\"number\":" + number + "}";
                excJ = "{\"id\":" + id + "," + "\"number\":" + number + "}";

                return ResponseResult.error(new Error(ResponseCodeStockEnum.AUDIT_FAILED.getCode(),
                        ResponseCodeStockEnum.SAVE_FAILED.getDesc() + result.getResult() + "添加服务产品失败"));
            }
        }

//        HashMap<String, Object> result = (HashMap) responseResult.getResult();
//        Integer incidentId = (Integer) result.get("incidentId");
//        insJ = "{\"numbers\":" + temp + "," + "\"ids\":" + temp + "}";
//        excJ = "{\"numbers\":" + temp + "," + "\"ids\":" + temp + "}";
//        if (methodName.equals("save") || methodName.equals("nosave")) {
//            //保存操作未调用
//            insertIncident(insJ, "submit", excJ, incidentId.longValue(), temp, "k3的保存未调用！");
//            //提交操作未调用
//            insertIncident(insJ, "submit", excJ, incidentId.longValue(), temp, "k3的提交未调用！");
//            //审核操作未调用
//            insertIncident(insJ, "submit", excJ, incidentId.longValue(), temp, "k3的审核未调用！");
//
//        }else if (methodName.equals("submit")) {
//            //保存操作未调用
//            insertIncident(insJ, "submit", excJ, incidentId.longValue(), temp, "k3的保存未调用！");
//            //提交操作未调用
//            insertIncident(insJ, "submit", excJ, incidentId.longValue(), temp, "k3的提交未调用！");
//            //审核操作未调用
//            insertIncident(insJ, "submit", excJ, incidentId.longValue(), temp, "k3的审核未调用！");
//
//        }else if (methodName.equals("submit")) {
//            //提交操作未调用
//            insertIncident(insJ, "submit", excJ, incidentId.longValue(), temp, "k3的提交未调用！");
//            //审核操作未调用
//            insertIncident(insJ, "submit", excJ, incidentId.longValue(), temp, "k3的审核未调用！");
//
//        } else {
//            //审核操作未调用
//            insertIncident(insJ, "submit", excJ, incidentId.longValue(), temp, "k3的审核未调用！");
//
//        }
        return responseResult;
    }

    @Override
    public Map uploadFuwenbenPic(MultipartFile file) {
        String imageUrl;
        String imageType;
        Integer imageWidth;
        Integer imageHeight;
        String picCode = "";
        picCode = ImageTypeEnum.FUWENBENPIC.getCode();
        Map picListMap = new HashMap();

        //富文本上传的图片
        if (file != null) {
            picListMap = new HashMap<>();
            try {
                ProductImage productImage = new ProductImage();
                ResponseResult responseResult = ftpFileUploadController.uploadImg(file, picCode);
                imageUrl = ((JSONObject) responseResult.getResult()).getString("imageUrl");
                imageType = ((JSONObject) responseResult.getResult()).getString("imageType");
                imageWidth = ((JSONObject) responseResult.getResult()).getInteger("imageWidth");
                imageHeight = ((JSONObject) responseResult.getResult()).getInteger("imageHeight");
                productImage.setImageUrl(imageUrl);
                productImage.setImageHeight(imageHeight);
                productImage.setImageType(imageType);
                productImage.setImageWidth(imageWidth);
                productImageDao.insertProductImage(productImage);
                String imageId = String.valueOf(productImage.getProductImageId());

                FuwenbenVO fuwenbenVO = new FuwenbenVO();
                fuwenbenVO.setSrc("http://" + imageUrl);

                picListMap.put("code", 0);
                picListMap.put("msg", "success");
                picListMap.put("data", fuwenbenVO);
            } catch (IOException e) {
                e.printStackTrace();
                FuwenbenVO fuwenbenVO = new FuwenbenVO();
                picListMap.put("code", -1);
                picListMap.put("msg", "上传失败");
                picListMap.put("data", fuwenbenVO);
                return picListMap;
            }
        }
        return picListMap;
    }

    @Override
    public ResponseResult calculation(String instoragePrice, String outstoragePrice, String outstorageProfit, String type, String productCode) {

        BigDecimal in;
        BigDecimal out;
        BigDecimal lirun;
        HashMap<Object, Object> map = new HashMap<>();
        //出库价*（1-毛利率）= 入库价

        if (StringUtils.isBlank(productCode)) {
            return ResponseResult.error(new Error(ResponseCodeProductEnum.PRODUCTCODE_ISNULL.getCode(),
                    ResponseCodeProductEnum.PRODUCTCODE_ISNULL.getDesc()));
        } else if (StringUtils.isNotBlank(instoragePrice) && StringUtils.isNotBlank(outstoragePrice)) {
            //计算出库利润
            in = new BigDecimal(instoragePrice);
            out = new BigDecimal(outstoragePrice);
            lirun = new BigDecimal(0);

            //1-入库价/出库价
            lirun = new BigDecimal(1).subtract(in.divide(out, 2, RoundingMode.HALF_UP));
            map.put("in", in);
            map.put("out", out);
            map.put("lirun", lirun);
            map.put("productCode", productCode);
        } else if (StringUtils.isNotBlank(instoragePrice) && StringUtils.isNotBlank(outstorageProfit)) {
            //计算出库价格
            in = new BigDecimal(instoragePrice);
            out = new BigDecimal(0);
            lirun = new BigDecimal(outstorageProfit);

            //入库价/（1-毛利率）
            out = in.divide(new BigDecimal(1).subtract(lirun), 2, RoundingMode.HALF_UP);
            map.put("in", in);
            map.put("out", out);
            map.put("lirun", lirun);
            map.put("productCode", productCode);
        } else if (StringUtils.isNotBlank(outstoragePrice) && StringUtils.isNotBlank(outstorageProfit)) {
            //计算入库价格
            in = new BigDecimal(0);
            out = new BigDecimal(outstoragePrice);
            lirun = new BigDecimal(outstorageProfit);
            //出库价*（1-毛利率）
            in = out.multiply(new BigDecimal(1).subtract(lirun));
            map.put("in", in);
            map.put("out", out);
            map.put("lirun", lirun);
            map.put("productCode", productCode);
        } else {
            return ResponseResult.success("请检查出入库价格以及出库利润！");
        }
        int result;
        if (type.equals("1")) {
            result = productDao.calculation(map);
        } else {
            result = serviceProductDao.calculation(map);
        }

        if (result == 0) {
            return ResponseResult.error(new Error(ResponseCodeProductEnum.UPDATE_IN_OUT_FAIL.getCode(),
                    ResponseCodeProductEnum.UPDATE_IN_OUT_FAIL.getDesc()));
        }
        return ResponseResult.success("此商品入库价为：" + in + ",出库价为：" + out + ",利润率为:" + lirun);
    }
}
