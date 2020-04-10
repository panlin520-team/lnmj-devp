package com.lnmj.product.business.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.common.Enum.ProductTypeDistinguishEnum;
import com.lnmj.common.Enum.ProductTypeEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeProductTypeEnum;
import com.lnmj.common.baseController.ExportController;
import com.lnmj.common.baseController.HttpServletRequestWarpper;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.common.utils.MemberUtil;
import com.lnmj.product.business.*;
import com.lnmj.product.entity.*;
import com.lnmj.product.entity.VO.*;
import com.lnmj.product.repository.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @Author: yilihua
 * @Date: 2019/5/6 10:25
 * @Description: 商品分类service
 */
@Service("productTypeService")
public class ProductTypeService implements IProductTypeService {

    @Resource
    private IProductKindDao productKindDao;
    @Resource
    private IProductSiteDao productSiteDao;
    @Resource
    private IProductCategoryDao productCategoryDao;
    @Resource
    private IProductEffectDao productEffectDao;
    @Resource
    private IProductBrandDao productBrandDao;
    @Resource
    private IProductDivisionDao productDivisionDao;
    @Resource
    private IProductEffectService productEffectService;
    @Resource
    private IProductKindService productKindService;
    @Resource
    private IProductSiteService productSiteService;
    @Resource
    private IProductCategoryService productCategoryService;
    @Resource
    private IProductBrandService productBrandService;
    @Resource
    private IProductDivisionService productDivisionService;
    @Resource
    private ExportController exportController;

    /**
     *@Description 根据商品类型查询商品种类,商品产地
     *@Param [productClassifyId]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/5/17
     *@Time 15:04
     */
    @Override
    public ResponseResult selectProductTypeByProductClassifyId(Long productClassifyId) {
        //判断商品类型
        if(productClassifyId.intValue() == ProductTypeEnum.PRODUCT.getCode()){
            //产品种类
            ResponseResult responseResultK = productKindService.selectProductKindByProductClassifyId(productClassifyId);
            //产品产地
            ResponseResult responseResultS = productSiteService.selectProductSiteByProductClassifyId(productClassifyId);
            //产品专区
            ResponseResult responseResultD = productDivisionService.selectProductDivisionByProductClassifyId(productClassifyId);
            ArrayList<Object> result = new ArrayList<>();
            result.add(responseResultS.getResult());
            result.add(responseResultK.getResult());
            result.add(responseResultD.getResult());
            return ResponseResult.success(result);
        }else if(productClassifyId.intValue() == ProductTypeEnum.SERVICE.getCode()){
            //产品专区
            ResponseResult responseResultD = productDivisionService.selectProductDivisionByProductClassifyId(productClassifyId);
            //产品种类
            ResponseResult responseResultK = productKindService.selectProductKindByProductClassifyId(productClassifyId);
            ArrayList<Object> result = new ArrayList<>();
            result.add(responseResultK.getResult());
            result.add(responseResultD.getResult());
            return ResponseResult.success(result);
        }else{
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE.getCode(),
                    ResponseCodeProductTypeEnum.NOT_PRODUCT_TYPE.getDesc()+productClassifyId));
        }
    }
    
    /**
    *@Description 商品分类所有数据
    *@Param []
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/5/15
    *@Time 14:51
    */
    @Transactional
    @Override
    public ResponseResult selectProductTypeList() {
        //商品分类按照商品分类的级别来显示
        //所有商品分类
        Map<String,Map<String,List<ProductType>>> resultMap = new HashMap<>();
        //商品分类产地数据查询
        List<ProductType> productTypeListSite = selectProductType(ProductTypeDistinguishEnum.PRODUCT_SITE.getCode().longValue(), null);
        Map<String,List<ProductType>> productTypeMapSite = new HashMap<String,List<ProductType>>();
        productTypeMapSite.put("productTypeListSite",productTypeListSite);
        resultMap.put("productTypeListSite",productTypeMapSite);
        //商品分类专区数据查询
        List<ProductType> productDivisionList = selectProductType(ProductTypeDistinguishEnum.PRODUCT_DIVISION.getCode().longValue(),null);
        Map<String,List<ProductType>> productTypeMapDivision = new HashMap<String,List<ProductType>>();
        productTypeMapDivision.put("productTypeListDivision",productDivisionList);
        resultMap.put("productTypeListDivision",productTypeMapDivision);
        //商品分类种类数据查询
        List<ProductType> productTypeListKind = selectProductType(ProductTypeDistinguishEnum.PRODUCT_KIND.getCode().longValue(), null);
        Map<String,List<ProductType>> productTypeMapKind = new HashMap<String,List<ProductType>>();
        productTypeMapKind.put("productTypeListKind",productTypeListKind);
        resultMap.put("productTypeListKind",productTypeMapKind);
        //商品分类种类下有哪些数据
        for(int i=0;i<productTypeListKind.size();i++){
            Map<String,List<ProductType>> productTypeMap = new HashMap<>();
            List<ProductType> productTypeListEffect=new ArrayList<>();
            List<ProductType> productTypeListCategory=new ArrayList<>();
            List<ProductType> productTypeListBrand=new ArrayList<>();
            List<ProductEffect> productEffectList =
                    productKindDao.selectProductEffectByProductKindId(productTypeListKind.get(i).getProductTypeId());
            List<ProductCategory> productCategoryList =
                    productKindDao.selectProductCategoryByProductKindId(productTypeListKind.get(i).getProductTypeId());
            List<ProductBrand> productBrandList =
                    productKindDao.selectProductBrandByProductKindId(productTypeListKind.get(i).getProductTypeId());
            for(int j=0;j<productEffectList.size();j++){
                ProductType productType = new ProductType();
                ProductEffect productEffect = productEffectList.get(j);
                try {
                    BeanUtils.copyProperties(productType, productEffect);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                productType.setProductTypeId(productEffect.getProductEffectId());
                productType.setProductTypeName(productEffect.getProductEffectName());
                productType.setProductTypeOrder(productEffect.getProductEffectOrder());
                productType.setProductTypeDistinguishId(ProductTypeDistinguishEnum.PRODUCT_EFFECT.getCode().longValue());
                productType.setProductClassifyId(productTypeListKind.get(i).getProductClassifyId());
                productTypeListEffect.add(productType);
            }
            for(int j=0;j<productCategoryList.size();j++){
                ProductType productType = new ProductType();
                ProductCategory productCategory = productCategoryList.get(j);
                productType.setProductTypeId(productCategory.getProductCategoryId());
                productType.setProductTypeName(productCategory.getProductCategoryName());
                productType.setProductTypeOrder(productCategory.getProductCategoryName());
                productType.setProductTypeDistinguishId(ProductTypeDistinguishEnum.PRODUCT_CATEGORY.getCode().longValue());
                productType.setProductClassifyId(productTypeListKind.get(i).getProductClassifyId());
                try {
                    BeanUtils.copyProperties(productType, productCategory);
                    productTypeListCategory.add(productType);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            for(int j=0;j<productBrandList.size();j++){
                ProductType productType = new ProductType();
                ProductBrand productBrand = productBrandList.get(j);
                productType.setProductTypeId(productBrand.getProductBrandId());
                productType.setProductTypeName(productBrand.getProductBrandName());
                productType.setProductTypeOrder(productBrand.getProductBrandOrder());
                productType.setProductTypeDistinguishId(ProductTypeDistinguishEnum.PRODUCT_BRAND.getCode().longValue());
                productType.setProductClassifyId(productTypeListKind.get(i).getProductClassifyId());
                try {
                    BeanUtils.copyProperties(productType, productBrand);
                    productTypeListBrand.add(productType);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            productTypeMap.put("productTypeListEffect",productTypeListEffect);
            productTypeMap.put("productTypeListCategory",productTypeListCategory);
            productTypeMap.put("productTypeListBrand",productTypeListBrand);
            //kindId为 i时的effect,category,brand
            resultMap.put("productTypeKindId"+productTypeListKind.get(i).getProductTypeId(),productTypeMap);
        }
        return ResponseResult.success(resultMap);
    }

    /**
    *@Description 根据区分id查询数据库
    *@Param [productTypeDistinguishId, keyWord]
    *@Return java.util.List<com.lnmj.product.entity.VO.ProductType>
    *@Author yilihua
    *@Date 2019/5/14
    *@Time 17:52
    */
    public List<ProductType> selectProductType(Long productTypeDistinguishId, String keyWord) {
        List<ProductType> productTypeList = new ArrayList<>();
        if(StringUtils.isBlank(keyWord)){
            if(productTypeDistinguishId.equals(ProductTypeDistinguishEnum.PRODUCT_EFFECT.getCode().longValue())){
                List<ProductEffect> productEffectList = productEffectDao.selectProductEffectList();
                for(int i=0;i<productEffectList.size();i++){
                    ProductType productType = new ProductType();
                    ProductEffect productEffect = productEffectList.get(i);
                    productType.setProductTypeId(productEffect.getProductEffectId());
                    productType.setProductTypeName(productEffect.getProductEffectName());
                    productType.setProductTypeOrder(productEffect.getProductEffectOrder());
                    productType.setProductTypeDistinguishId(ProductTypeDistinguishEnum.PRODUCT_EFFECT.getCode().longValue());
                    try {
                        BeanUtils.copyProperties(productType, productEffect);
                        productTypeList.add(productType);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if(productTypeDistinguishId.equals(ProductTypeDistinguishEnum.PRODUCT_BRAND.getCode().longValue())){
                List<ProductBrand> productBrandList = productBrandDao.selectProductBrandList();
                for(int i=0;i<productBrandList.size();i++){
                    ProductType productType = new ProductType();
                    ProductBrand productBrand = productBrandList.get(i);
                    productType.setProductTypeId(productBrand.getProductBrandId());
                    productType.setProductTypeName(productBrand.getProductBrandName());
                    productType.setProductTypeOrder(productBrand.getProductBrandOrder());
                    productType.setProductTypeDistinguishId(ProductTypeDistinguishEnum.PRODUCT_BRAND.getCode().longValue());
                    try {
                        BeanUtils.copyProperties(productType, productBrand);
                        productTypeList.add(productType);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if(productTypeDistinguishId.equals(ProductTypeDistinguishEnum.PRODUCT_CATEGORY.getCode().longValue())){
                List<ProductCategory> productCategoryList = productCategoryDao.selectProductCategoryList();
                for(int i=0;i<productCategoryList.size();i++){
                    ProductType productType = new ProductType();
                    ProductCategory productCategory = productCategoryList.get(i);
                    productType.setProductTypeId(productCategory.getProductCategoryId());
                    productType.setProductTypeName(productCategory.getProductCategoryName());
                    productType.setProductTypeOrder(productCategory.getProductCategoryOrder());
                    productType.setProductTypeDistinguishId(ProductTypeDistinguishEnum.PRODUCT_CATEGORY.getCode().longValue());
                    try {
                        BeanUtils.copyProperties(productType, productCategory);
                        productTypeList.add(productType);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if(productTypeDistinguishId.equals(ProductTypeDistinguishEnum.PRODUCT_KIND.getCode().longValue())){
                List<ProductKind> productKindList = productKindDao.selectProductKindList();
                for(int i=0;i<productKindList.size();i++){
                    ProductType productType = new ProductType();
                    ProductKind productKind = productKindList.get(i);
                    productType.setProductTypeId(productKind.getProductKindId());
                    productType.setProductTypeName(productKind.getProductKindName());
                    productType.setProductTypeOrder(productKind.getProductKindOrder());
                    productType.setProductTypeDistinguishId(ProductTypeDistinguishEnum.PRODUCT_KIND.getCode().longValue());
                    try {
                        BeanUtils.copyProperties(productType, productKind);
                        productTypeList.add(productType);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if(productTypeDistinguishId.equals(ProductTypeDistinguishEnum.PRODUCT_SITE.getCode().longValue())){
                List<ProductSite> productSiteList = productSiteDao.selectProductSiteList();
                for(int i=0;i<productSiteList.size();i++){
                    ProductType productType = new ProductType();
                    ProductSite productSite = productSiteList.get(i);
                    productType.setProductTypeId(productSite.getProductSiteId());
                    productType.setProductTypeName(productSite.getProductSiteName());
                    productType.setProductTypeOrder(productSite.getProductSiteOrder());
                    productType.setProductTypeDistinguishId(ProductTypeDistinguishEnum.PRODUCT_SITE.getCode().longValue());
                    try {
                        BeanUtils.copyProperties(productType, productSite);
                        productTypeList.add(productType);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if(productTypeDistinguishId.equals(ProductTypeDistinguishEnum.PRODUCT_DIVISION.getCode().longValue())){
                List<ProductDivision> productDivisionList = productDivisionDao.selectProductDivisionList();
                for(int i=0;i<productDivisionList.size();i++){
                    ProductType productType = new ProductType();
                    ProductDivision productDivision = productDivisionList.get(i);
                    productType.setProductTypeId(productDivision.getProductDivisionId());
                    productType.setProductTypeName(productDivision.getProductDivisionName());
                    productType.setProductTypeOrder(productDivision.getProductDivisionOrder());
                    productType.setProductTypeDistinguishId(ProductTypeDistinguishEnum.PRODUCT_DIVISION.getCode().longValue());
                    try {
                        BeanUtils.copyProperties(productType, productDivision);
                        productTypeList.add(productType);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }else{  //keyword不为空
            if(productTypeDistinguishId.equals(ProductTypeDistinguishEnum.PRODUCT_EFFECT.getCode().longValue())){
                List<ProductEffect> productEffectList = productEffectDao.selectProductEffectListByKeyWord(keyWord);
                for(int i=0;i<productEffectList.size();i++){
                    ProductType productType = new ProductType();
                    ProductEffect productEffect = productEffectList.get(i);
                    productType.setProductTypeId(productEffect.getProductEffectId());
                    productType.setProductTypeName(productEffect.getProductEffectName());
                    productType.setProductTypeOrder(productEffect.getProductEffectOrder());
                    productType.setProductTypeDistinguishId(ProductTypeDistinguishEnum.PRODUCT_EFFECT.getCode().longValue());
                    try {
                        BeanUtils.copyProperties(productType, productEffect);
                        productTypeList.add(productType);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if(productTypeDistinguishId.equals(ProductTypeDistinguishEnum.PRODUCT_BRAND.getCode().longValue())){
                List<ProductBrand> productBrandList = productBrandDao.selectProductBrandListByKeyWord(keyWord);
                for(int i=0;i<productBrandList.size();i++){
                    ProductType productType = new ProductType();
                    ProductBrand productBrand = productBrandList.get(i);
                    productType.setProductTypeId(productBrand.getProductBrandId());
                    productType.setProductTypeName(productBrand.getProductBrandName());
                    productType.setProductTypeOrder(productBrand.getProductBrandOrder());
                    productType.setProductTypeDistinguishId(ProductTypeDistinguishEnum.PRODUCT_BRAND.getCode().longValue());
                    try {
                        BeanUtils.copyProperties(productType, productBrand);
                        productTypeList.add(productType);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if(productTypeDistinguishId.equals(ProductTypeDistinguishEnum.PRODUCT_CATEGORY.getCode().longValue())){
                List<ProductCategory> productCategoryList = productCategoryDao.selectProductCategoryListByKeyWord(keyWord);
                for(int i=0;i<productCategoryList.size();i++){
                    ProductType productType = new ProductType();
                    ProductCategory productCategory = productCategoryList.get(i);
                    productType.setProductTypeId(productCategory.getProductCategoryId());
                    productType.setProductTypeName(productCategory.getProductCategoryName());
                    productType.setProductTypeOrder(productCategory.getProductCategoryOrder());
                    productType.setProductTypeDistinguishId(ProductTypeDistinguishEnum.PRODUCT_CATEGORY.getCode().longValue());
                    try {
                        BeanUtils.copyProperties(productType, productCategory);
                        productTypeList.add(productType);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if(productTypeDistinguishId.equals(ProductTypeDistinguishEnum.PRODUCT_KIND.getCode().longValue())){
                List<ProductKind> productKindList = productKindDao.selectProductKindListByKeyWord(keyWord);
                for(int i=0;i<productKindList.size();i++){
                    ProductType productType = new ProductType();
                    ProductKind productKind = productKindList.get(i);
                    productType.setProductTypeId(productKind.getProductKindId());
                    productType.setProductTypeName(productKind.getProductKindName());
                    productType.setProductTypeOrder(productKind.getProductKindOrder());
                    productType.setProductTypeDistinguishId(ProductTypeDistinguishEnum.PRODUCT_KIND.getCode().longValue());
                    try {
                        BeanUtils.copyProperties(productType, productKind);
                        productTypeList.add(productType);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if(productTypeDistinguishId.equals(ProductTypeDistinguishEnum.PRODUCT_SITE.getCode().longValue())){
                List<ProductSite> productSiteList = productSiteDao.selectProductSiteListByKeyWord(keyWord);
                for(int i=0;i<productSiteList.size();i++){
                    ProductType productType = new ProductType();
                    ProductSite productSite = productSiteList.get(i);
                    productType.setProductTypeId(productSite.getProductSiteId());
                    productType.setProductTypeName(productSite.getProductSiteName());
                    productType.setProductTypeOrder(productSite.getProductSiteOrder());
                    productType.setProductTypeDistinguishId(ProductTypeDistinguishEnum.PRODUCT_SITE.getCode().longValue());
                    try {
                        BeanUtils.copyProperties(productType, productSite);
                        productTypeList.add(productType);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if(productTypeDistinguishId.equals(ProductTypeDistinguishEnum.PRODUCT_DIVISION.getCode().longValue())){
                List<ProductDivision> productDivisionList = productDivisionDao.selectProductDivisionListByKeyWord(keyWord);
                for(int i=0;i<productDivisionList.size();i++){
                    ProductType productType = new ProductType();
                    ProductDivision productDivision = productDivisionList.get(i);
                    productType.setProductTypeId(productDivision.getProductDivisionId());
                    productType.setProductTypeName(productDivision.getProductDivisionName());
                    productType.setProductTypeOrder(productDivision.getProductDivisionOrder());
                    productType.setProductTypeDistinguishId(ProductTypeDistinguishEnum.PRODUCT_DIVISION.getCode().longValue());
                    try {
                        BeanUtils.copyProperties(productType, productDivision);
                        productTypeList.add(productType);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return productTypeList;
    }

    /**
     * @Description 商品分类分页显示
     * @Param [pageNum,pageSize]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/6
     * @Time 16:26
     */
    @Transactional
    @Override
    public ResponseResult selectProductTypeList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        //所有商品分类
        List<ProductType> productTypeList = new ArrayList<>();
        productTypeList.addAll(selectProductType(ProductTypeDistinguishEnum.PRODUCT_EFFECT.getCode().longValue(), null));
        productTypeList.addAll(selectProductType(ProductTypeDistinguishEnum.PRODUCT_BRAND.getCode().longValue(), null));
        productTypeList.addAll(selectProductType(ProductTypeDistinguishEnum.PRODUCT_CATEGORY.getCode().longValue(), null));
        productTypeList.addAll(selectProductType(ProductTypeDistinguishEnum.PRODUCT_KIND.getCode().longValue(), null));
        productTypeList.addAll(selectProductType(ProductTypeDistinguishEnum.PRODUCT_SITE.getCode().longValue(), null));
        productTypeList.addAll(selectProductType(ProductTypeDistinguishEnum.PRODUCT_DIVISION.getCode().longValue(), null));
        if (productTypeList.size()!=0) {
            PageInfo<ProductType> pageInfo = new PageInfo(productTypeList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_KIND_NOT_FIND.getCode(),
                ResponseCodeProductTypeEnum.PRODUCT_KIND_NOT_FIND.getDesc()));
    }

    /**
     * @Description 商品分类关键字查询
     * @Param [pageNum, pageSize, keyWord]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/6
     * @Time 16:41
     */
    @Transactional
    @Override
    public ResponseResult selectProductTypeByKeyWord(int pageNum, int pageSize, String keyWord) {
        //没有关键字时返回所有productType数据
        if (StringUtils.isBlank(keyWord) || keyWord == "") {
            selectProductTypeList(pageNum, pageSize);
        }
        PageHelper.startPage(pageNum,pageSize);
        List<ProductType> productTypeList = new ArrayList<>();
        productTypeList.addAll(selectProductType(ProductTypeDistinguishEnum.PRODUCT_EFFECT.getCode().longValue(), keyWord));
        productTypeList.addAll(selectProductType(ProductTypeDistinguishEnum.PRODUCT_BRAND.getCode().longValue(), keyWord));
        productTypeList.addAll(selectProductType(ProductTypeDistinguishEnum.PRODUCT_CATEGORY.getCode().longValue(), keyWord));
        productTypeList.addAll(selectProductType(ProductTypeDistinguishEnum.PRODUCT_KIND.getCode().longValue(), keyWord));
        productTypeList.addAll(selectProductType(ProductTypeDistinguishEnum.PRODUCT_SITE.getCode().longValue(), keyWord));
        productTypeList.addAll(selectProductType(ProductTypeDistinguishEnum.PRODUCT_DIVISION.getCode().longValue(), keyWord));
        if (productTypeList.size()!=0) {
            PageInfo<ProductType> pageInfo = new PageInfo(productTypeList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.PRODUCT_KIND_NOT_FIND.getCode(),
                ResponseCodeProductTypeEnum.PRODUCT_KIND_NOT_FIND.getDesc()));
    }

    /**
     * @Description 商品分类按分类类别查询 productTypeDistinguishId 是商品分类的区分字段
     * @Param [pageNum, pageSize, productTypeDistinguishId]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/6
     * @Time 16:56
     */
    @Transactional
    @Override
    public ResponseResult selectProductTypeByType(int pageNum, int pageSize, Long productTypeDistinguishId) {
        PageHelper.startPage(pageNum,pageSize);
        List<ProductType> productTypeList = selectProductType(productTypeDistinguishId,null);
        PageInfo<ProductType> pageInfo = new PageInfo(productTypeList);
        return ResponseResult.success(pageInfo);
    }



    /**
     * @Description 新增商品分类
     * @Param [productTypeVO]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/8
     * @Time 15:00
     */
    @Override
    public ResponseResult insertProductType(ProductTypeVO productTypeVO) {
        //判断是哪种商品分类的增加
        //商品功效 （新增没有id）
        if(productTypeVO.getProductTypeDistinguishId().equals(ProductTypeDistinguishEnum.PRODUCT_EFFECT.getCode().longValue())){
            ProductEffectVO productEffect = new ProductEffectVO();
            try {
                BeanUtils.copyProperties(productEffect,productTypeVO);
            }catch (Exception e){
                e.printStackTrace();
            }
            productEffect.setProductEffectName(productTypeVO.getProductTypeName());
            productEffect.setProductEffectOrder(productTypeVO.getProductTypeOrder());
            return  productEffectService.insertProductEffect(productEffect);
        }
        //商品品牌
        if(productTypeVO.getProductTypeDistinguishId().equals(ProductTypeDistinguishEnum.PRODUCT_BRAND.getCode().longValue())){
            ProductBrandVO productBrand = new ProductBrandVO();
            try {
                BeanUtils.copyProperties(productBrand,productTypeVO);
            }catch (Exception e){
                e.printStackTrace();
            }
            productBrand.setProductBrandName(productTypeVO.getProductTypeName());
            productBrand.setProductBrandOrder(productTypeVO.getProductTypeOrder());
            return  productBrandService.insertProductBrand(productBrand);
        }
        //商品品类
        if(productTypeVO.getProductTypeDistinguishId().equals(ProductTypeDistinguishEnum.PRODUCT_CATEGORY.getCode().longValue())){
            ProductCategoryVO productCategory = new ProductCategoryVO();
            try {
                BeanUtils.copyProperties(productCategory,productTypeVO);
            }catch (Exception e){
                e.printStackTrace();
            }
            productCategory.setProductCategoryName(productTypeVO.getProductTypeName());
            productCategory.setProductCategoryOrder(productTypeVO.getProductTypeOrder());
            return  productCategoryService.insertProductCategory(productCategory);
        }
        //商品种类
        if(productTypeVO.getProductTypeDistinguishId().equals(ProductTypeDistinguishEnum.PRODUCT_KIND.getCode().longValue())){
            ProductKindVO productKind = new ProductKindVO();
            try {
                BeanUtils.copyProperties(productKind,productTypeVO);
            }catch (Exception e){
                e.printStackTrace();
            }
            productKind.setProductKindName(productTypeVO.getProductTypeName());
            productKind.setProductKindOrder(productTypeVO.getProductTypeOrder());
            return  productKindService.insertProductKind(productKind);
        }
        //商品产地
        if(productTypeVO.getProductTypeDistinguishId().equals(ProductTypeDistinguishEnum.PRODUCT_SITE.getCode().longValue())){
            ProductSiteVO productSite = new ProductSiteVO();
            try {
                BeanUtils.copyProperties(productSite,productTypeVO);
            }catch (Exception e){
                e.printStackTrace();
            }
            productSite.setProductSiteName(productTypeVO.getProductTypeName());
            productSite.setProductSiteOrder(productTypeVO.getProductTypeOrder());
            return productSiteService.insertProductSite(productSite);
        }
        //商品专区
        if(productTypeVO.getProductTypeDistinguishId().equals(ProductTypeDistinguishEnum.PRODUCT_DIVISION.getCode().longValue())){
            ProductDivisionVO productDivision = new ProductDivisionVO();
            try {
                BeanUtils.copyProperties(productDivision,productTypeVO);
            }catch (Exception e){
                e.printStackTrace();
            }
            productDivision.setProductDivisionName(productTypeVO.getProductTypeName());
            productDivision.setProductDivisionOrder(productTypeVO.getProductTypeOrder());
            return productDivisionService.insertProductDivision(productDivision);
        }
        return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.INSERT_PRODUCT_TYPE_FAILED.getCode(),
                ResponseCodeProductTypeEnum.INSERT_PRODUCT_TYPE_FAILED.getDesc()));
    }

    /**
     * @Description 根据id删除商品分类
     * @Param [productTypeId]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/9
     * @Time 19:29
     */
    @Override
    public ResponseResult deleteProductType(Long productTypeId, Long productTypeDistinguishId, String modifyOperator) {
        //判断是哪种商品分类
        //商品功效
        if(productTypeDistinguishId.equals(ProductTypeDistinguishEnum.PRODUCT_EFFECT.getCode().longValue())){
            return  productEffectService.deleteProductEffectById(productTypeId,modifyOperator);
        }
        //商品品牌
        if(productTypeDistinguishId.equals(ProductTypeDistinguishEnum.PRODUCT_BRAND.getCode().longValue())){
            return  productBrandService.deleteProductBrandById(productTypeId,modifyOperator);
        }
        //商品品类
        if(productTypeDistinguishId.equals(ProductTypeDistinguishEnum.PRODUCT_CATEGORY.getCode().longValue())){
            return productCategoryService.deleteProductCategoryById(productTypeId,modifyOperator);
        }
        //商品种类
        if(productTypeDistinguishId.equals(ProductTypeDistinguishEnum.PRODUCT_KIND.getCode().longValue())){
            return  productKindService.deleteProductKindById(productTypeId,modifyOperator);
        }
        //商品产地
        if(productTypeDistinguishId.equals(ProductTypeDistinguishEnum.PRODUCT_SITE.getCode().longValue())){
            return productSiteService.deleteProductSiteById(productTypeId,modifyOperator);
        }
        //商品专区
        if(productTypeDistinguishId.equals(ProductTypeDistinguishEnum.PRODUCT_DIVISION.getCode().longValue())){
            return productDivisionService.deleteProductDivision(productTypeId,modifyOperator);
        }
        return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.UPDATE_PRODUCT_TYPE_FAILED.getCode(),
                ResponseCodeProductTypeEnum.UPDATE_PRODUCT_TYPE_FAILED.getDesc()));
    }

    /**
     * @Description 根据id修改商品分类
     * @Param [productTypeVO]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/10
     * @Time 11:17
     */
    @Override
    public ResponseResult updateProductType(ProductTypeVO productTypeVO) {
        //判断是哪种商品分类的修改
        //商品功效
        if(productTypeVO.getProductTypeDistinguishId().equals(ProductTypeDistinguishEnum.PRODUCT_EFFECT.getCode().longValue())){
            ProductEffectVO productEffect = new ProductEffectVO();
            try {
                BeanUtils.copyProperties(productEffect,productTypeVO);
            }catch (Exception e){
                e.printStackTrace();
            }
            productEffect.setProductEffectId(productTypeVO.getProductTypeId());
            if(!StringUtils.isBlank(productTypeVO.getProductTypeName())){
                productEffect.setProductEffectName(productTypeVO.getProductTypeName());
            }
            if(!StringUtils.isBlank(productTypeVO.getProductTypeOrder())){
                productEffect.setProductEffectOrder(productTypeVO.getProductTypeOrder());
            }
            return  productEffectService.updateProductEffectById(productEffect);
        }
        //商品品牌
        if(productTypeVO.getProductTypeDistinguishId().equals(ProductTypeDistinguishEnum.PRODUCT_BRAND.getCode().longValue())){
            ProductBrandVO productBrand = new ProductBrandVO();
            try {
                BeanUtils.copyProperties(productBrand,productTypeVO);
            }catch (Exception e){
                e.printStackTrace();
            }
            productBrand.setProductBrandId(productTypeVO.getProductTypeId());
            if(!StringUtils.isBlank(productTypeVO.getProductTypeName())){
                productBrand.setProductBrandName(productTypeVO.getProductTypeName());
            }
            if(!StringUtils.isBlank(productTypeVO.getProductTypeOrder())){
                productBrand.setProductBrandOrder(productTypeVO.getProductTypeOrder());
            }
            return  productBrandService.updateProductBrandById(productBrand);
        }
        //商品品类
        if(productTypeVO.getProductTypeDistinguishId().equals(ProductTypeDistinguishEnum.PRODUCT_CATEGORY.getCode().longValue())){
            ProductCategoryVO productCategory = new ProductCategoryVO();
            try {
                BeanUtils.copyProperties(productCategory,productTypeVO);
            }catch (Exception e){
                e.printStackTrace();
            }
            productCategory.setProductCategoryId(productTypeVO.getProductTypeId());
            if(!StringUtils.isBlank(productTypeVO.getProductTypeName())){
                productCategory.setProductCategoryName(productTypeVO.getProductTypeName());
            }
            if(!StringUtils.isBlank(productTypeVO.getProductTypeOrder())){
                productCategory.setProductCategoryOrder(productTypeVO.getProductTypeOrder());
            }
            return  productCategoryService.updateProductCategoryById(productCategory);
        }
        //商品种类
        if(productTypeVO.getProductTypeDistinguishId().equals(ProductTypeDistinguishEnum.PRODUCT_KIND.getCode().longValue())){
            ProductKindVO productKind = new ProductKindVO();
            try {
                BeanUtils.copyProperties(productKind,productTypeVO);
            }catch (Exception e){
                e.printStackTrace();
            }
            productKind.setProductKindId(productTypeVO.getProductTypeId());
            if(!StringUtils.isBlank(productTypeVO.getProductTypeName())){
                productKind.setProductKindName(productTypeVO.getProductTypeName());
            }
            if(!StringUtils.isBlank(productTypeVO.getProductTypeOrder())){
                productKind.setProductKindOrder(productTypeVO.getProductTypeOrder());
            }
            return  productKindService.updateProductKindById(productKind);
        }
        //商品产地
        if(productTypeVO.getProductTypeDistinguishId().equals(ProductTypeDistinguishEnum.PRODUCT_SITE.getCode().longValue())){
            ProductSiteVO productSite = new ProductSiteVO();
            try {
                BeanUtils.copyProperties(productSite,productTypeVO);
            }catch (Exception e){
                e.printStackTrace();
            }
            productSite.setProductSiteId(productTypeVO.getProductTypeId());
            if(!StringUtils.isBlank(productTypeVO.getProductTypeName())){
                productSite.setProductSiteName(productTypeVO.getProductTypeName());
            }
            if(!StringUtils.isBlank(productTypeVO.getProductTypeOrder())){
                productSite.setProductSiteOrder(productTypeVO.getProductTypeOrder());
            }
            return productSiteService.updateProductSiteById(productSite);
        }
        //商品专区
        if(productTypeVO.getProductTypeDistinguishId().equals(ProductTypeDistinguishEnum.PRODUCT_DIVISION.getCode().longValue())){
            ProductDivisionVO productDivision = new ProductDivisionVO();
            try {
                BeanUtils.copyProperties(productDivision,productTypeVO);
            }catch (Exception e){
                e.printStackTrace();
            }
            productDivision.setProductDivisionId(productTypeVO.getProductTypeId());
            if(!StringUtils.isBlank(productTypeVO.getProductTypeName())){
                productDivision.setProductDivisionName(productTypeVO.getProductTypeName());
            }
            if(!StringUtils.isBlank(productTypeVO.getProductTypeOrder())){
                productDivision.setProductDivisionOrder(productTypeVO.getProductTypeOrder());
            }
            return productDivisionService.updateProductDivision(productDivision);
        }
        return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.UPDATE_PRODUCT_TYPE_FAILED.getCode(),
                ResponseCodeProductTypeEnum.UPDATE_PRODUCT_TYPE_FAILED.getDesc()));
    }

    /**
     * @Description 导出商品分类
     * @Param [req, resp]
     * @Return com.lnmj.product.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/5/10
     * @Time 16:10
     */
    @Override
    public ResponseResult exportProductType(HttpServletRequest req, HttpServletResponse resp) {
        //查询需要导出的商品分类数据
        //所有商品分类
        List<ProductType> productTypeList = new ArrayList<>();
        productTypeList.addAll(selectProductType(ProductTypeDistinguishEnum.PRODUCT_EFFECT.getCode().longValue(), null));
        productTypeList.addAll(selectProductType(ProductTypeDistinguishEnum.PRODUCT_BRAND.getCode().longValue(), null));
        productTypeList.addAll(selectProductType(ProductTypeDistinguishEnum.PRODUCT_CATEGORY.getCode().longValue(), null));
        productTypeList.addAll(selectProductType(ProductTypeDistinguishEnum.PRODUCT_KIND.getCode().longValue(), null));
        productTypeList.addAll(selectProductType(ProductTypeDistinguishEnum.PRODUCT_SITE.getCode().longValue(), null));
        productTypeList.addAll(selectProductType(ProductTypeDistinguishEnum.PRODUCT_DIVISION.getCode().longValue(), null));
        //导出商品分类
        String sheetName = "商品分类";
        String[] title = {"编号","分类名称","图片地址","排序"};   //key为title的所有值
        //value处理
        String[][] valueStr = new String[productTypeList.size()][title.length];
        for (int i = 0; i < productTypeList.size(); i++) {
                valueStr[i][0] = Integer.toString(i+1);
                valueStr[i][1] = productTypeList.get(i).getProductTypeName();
                valueStr[i][2] = productTypeList.get(i).getImageUrl();
                valueStr[i][3] = productTypeList.get(i).getProductTypeOrder();
        }
        String fileName = "商品分类" + new SimpleDateFormat("yyyyMMdd").format(new Date())+".xls";
        Map map = new HashMap<>();
        map.put("sheetName",sheetName);
        map.put("title",title);
        map.put("value",valueStr);
        map.put("fileName",fileName);
        HttpServletRequestWarpper requestWarpper = new HttpServletRequestWarpper(req,map);
        exportController.exportExcel(requestWarpper,resp);
        /*//导出商品分类
        try {
            //制作表单
            SXSSFWorkbook workbook = new SXSSFWorkbook();
            SXSSFSheet sheet = workbook.createSheet("商品分类");
            SXSSFRow topRow = sheet.createRow(0);
            topRow.createCell(0).setCellValue("编号");
            topRow.createCell(1).setCellValue("分类名称");
            topRow.createCell(2).setCellValue("图片地址");
            topRow.createCell(3).setCellValue("排序");
            //设置单元格
            sheet.setColumnWidth(0,8);
            sheet.setColumnWidth(1,20);
            sheet.setColumnWidth(2,64);
            sheet.setColumnWidth(3,8);
            for (int i = 0; i < productTypeList.size(); i++) {
                Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(i+1);
                row.createCell(1).setCellValue(productTypeList.get(i).getProductTypeName());
                row.createCell(2).setCellValue(productTypeList.get(i).getImageUrl());
                row.createCell(3).setCellValue(productTypeList.get(i).getProductTypeOrder());
            }
            //文件名
            String destName = "商品分类" + new SimpleDateFormat("yyyyMMdd").format(new Date())+".xls";
            //浏览器判断
            String userAgent = req.getHeader("User-Agent");
            if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
                //IE浏览器处理
                destName = java.net.URLEncoder.encode(destName, "UTF-8");
            } else {
                // 非IE浏览器的处理：
                destName = new String(destName.getBytes("UTF-8"), "ISO-8859-1");
            }
            //表单写出
            resp.reset();// 清空输出流
            resp.setCharacterEncoding("UTF-8");
            resp.setHeader("Content-disposition","attachment;filename=" + destName);// 设定输出文件头
            resp.setContentType("application/vnd.ms-excel;charset=utf-8");// 定义输出类型
            //输出流
            OutputStream out = resp.getOutputStream();
            //输出文件
            BufferedOutputStream bufferedOutPut = new BufferedOutputStream(out);
            bufferedOutPut.flush();
            workbook.write(bufferedOutPut);
            //关闭流，只需要关闭最外层的流
            bufferedOutPut.close();
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.error(new Error(ResponseCodeProductTypeEnum.EXPORT_PRODUCT_TYPE_FAILED.getCode(),
                    ResponseCodeProductTypeEnum.EXPORT_PRODUCT_TYPE_FAILED.getDesc()));
        }*/
        return null;
    }

    @Override
    public ResponseResult selectProductTypeKind() {
        return ResponseResult.success(MemberUtil.getEnumToMap(ProductTypeDistinguishEnum.class));
    }

}
