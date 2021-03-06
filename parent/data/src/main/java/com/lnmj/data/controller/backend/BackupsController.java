package com.lnmj.data.controller.backend;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.common.utils.CodeUtils;
import com.lnmj.data.business.BackupsService;
import com.lnmj.data.entity.VO.*;
import com.lnmj.data.serviceProxy.K3CLOUDApi;
import com.lnmj.data.serviceProxy.StoreApi;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.ss.usermodel.Workbook;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import javax.annotation.Resource;

@RestController
@RequestMapping("/backups")
public class BackupsController {

    @Resource
    private BackupsService backupsService;
    @Resource
    private K3CLOUDApi k3CLOUDApi;
    @Resource
    private StoreApi storeApi;


    /**
     * @Author renqingyun
     * @Date: 2020/3/17 11:17
     * @Description: 商品导入
     */
    @ResponseBody
    @RequestMapping(value = "/backupsProduct", method = RequestMethod.POST)
    public synchronized ResponseResult upload(MultipartFile file) throws Exception {
        File backupfile = ReadExcel2003_2007.multipartFileToFile(file);
        //转换成csv的数据
        List<String[]> list = ReadExcel2003_2007.getRecords(backupfile, 26);
        Map map = handleProduct(list);
        List k3arrayList = (List) map.get("k3arrayList");
        List backProductVOS = (List) map.get("productlist");

//        List product = this.getExcelInfo(file, "product");
        //拿到读取数据，做数据批量处理，具体根据具体需求，进行判断
        //TODO:导入的数据也需要导入到k3
        JSONArray array = JSONArray.parseArray(JSON.toJSONString(k3arrayList));

        HashMap<String, String> userNameAndPassword = getUserNameAndPassword(null);
        String dataCentre = userNameAndPassword.get("dataCentre");
        String userName = userNameAndPassword.get("userName");
        String password = userNameAndPassword.get("password");
        String productArr = array.toString();
        ResponseResult batchAdd = k3CLOUDApi.batchAdd(dataCentre, userName, password, productArr);
        //拿到返回的id和number
        LinkedHashMap result = (LinkedHashMap) batchAdd.getResult();
        LinkedHashMap result1 = (LinkedHashMap) result.get("Result");
        LinkedHashMap responseStatus = (LinkedHashMap) result1.get("ResponseStatus");
        List successEntitys = (List) responseStatus.get("SuccessEntitys");
        for (int i = 0; i < backProductVOS.size(); i++) {
            LinkedHashMap obj = (LinkedHashMap) successEntitys.get(i);
            Object number = obj.get("Number");
            Object Id = obj.get("Id");
            BackProductVO backProductVO = (BackProductVO) backProductVOS.get(i);
            backProductVO.setK3Id(Id.toString());
            backProductVO.setK3Number(number.toString());
        }
        backupsService.saveProductData(backProductVOS);
        return ResponseResult.success();
    }


    /**
     * @Author renqingyun
     * @Date: 2020/3/17 11:18
     * @Description: 服务商品导入
     */
    @ResponseBody
    @RequestMapping(value = "/backupsServiceProduct", method = RequestMethod.POST)
    public synchronized ResponseResult backupsServiceProduct(@RequestParam(value = "file") MultipartFile file) throws Exception {
        File backupfile = ReadExcel2003_2007.multipartFileToFile(file);
        //转换成csv的数据
        List<String[]> list = ReadExcel2003_2007.getRecords(backupfile, 13);
        Map map = handleServiceProduct(list);
        List k3arrayList = (List) map.get("k3arrayList");
        List backServiceProductVOS = (List) map.get("serviceproductlist");
        //TODO:导入的数据也需要导入到k3
        JSONArray array = JSONArray.parseArray(JSON.toJSONString(k3arrayList));

        HashMap<String, String> userNameAndPassword = getUserNameAndPassword(null);
        String dataCentre = userNameAndPassword.get("dataCentre");
        String userName = userNameAndPassword.get("userName");
        String password = userNameAndPassword.get("password");
        String productArr = array.toString();
        ResponseResult batchAdd = k3CLOUDApi.batchAdd(dataCentre, userName, password, productArr);
        //拿到返回的id和number
        LinkedHashMap result = (LinkedHashMap) batchAdd.getResult();
        LinkedHashMap result1 = (LinkedHashMap) result.get("Result");
        LinkedHashMap responseStatus = (LinkedHashMap) result1.get("ResponseStatus");
        List successEntitys = (List) responseStatus.get("SuccessEntitys");
        for (int i = 0; i < backServiceProductVOS.size(); i++) {
            LinkedHashMap obj = (LinkedHashMap) successEntitys.get(i);
            Object number = obj.get("Number");
            Object Id = obj.get("Id");
            BackServiceProductVO backServiceProductVO = (BackServiceProductVO) backServiceProductVOS.get(i);
            backServiceProductVO.setK3Id(Id.toString());
            backServiceProductVO.setK3Number(number.toString());
        }
        backupsService.saveServiceProductData(backServiceProductVOS);
        return ResponseResult.success();
    }


    /**
     * @Author renqingyun
     * @Date: 2020/3/17 11:18
     * @Description: 供应商导入
     */
    @ResponseBody
    @RequestMapping(value = "/backupsSupplier", method = RequestMethod.POST)
    public synchronized ResponseResult backupsSupplier(@RequestParam(value = "file") MultipartFile file) throws Exception {
        File backupfile = ReadExcel2003_2007.multipartFileToFile(file);
        //转换成csv的数据
        List<String[]> list = ReadExcel2003_2007.getRecords(backupfile, 8);
        Map map = handleSupplier(list);
        List k3arrayList = (List) map.get("k3arrayList");
        List backSupplierVOS = (List) map.get("backSupplierVOS");

        JSONArray array = JSONArray.parseArray(JSON.toJSONString(k3arrayList));

        HashMap<String, String> userNameAndPassword = getUserNameAndPassword(null);
        String dataCentre = userNameAndPassword.get("dataCentre");
        String userName = userNameAndPassword.get("userName");
        String password = userNameAndPassword.get("password");
        String Arr = array.toString();

        ResponseResult batchsaveSupplier = k3CLOUDApi.batchsaveSupplier(dataCentre, userName, password, Arr);
        //拿到返回的id和number
        LinkedHashMap result = (LinkedHashMap) batchsaveSupplier.getResult();
        LinkedHashMap result1 = (LinkedHashMap) result.get("Result");
        LinkedHashMap responseStatus = (LinkedHashMap) result1.get("ResponseStatus");
        List successEntitys = (List) responseStatus.get("SuccessEntitys");
        for (int i = 0; i < backSupplierVOS.size(); i++) {
            LinkedHashMap obj = (LinkedHashMap) successEntitys.get(i);
            Object number = obj.get("Number");
            Object Id = obj.get("Id");
            BackSupplierVO backSupplierVO = (BackSupplierVO) backSupplierVOS.get(i);
            backSupplierVO.setK3Id(Id.toString());
            backSupplierVO.setK3Number(number.toString());
        }
//        List supplier = this.getExcelInfo(file, "supplier");
        //拿到读取数据，做数据批量处理，具体根据具体需求，进行判断
        //TODO:导入的数据也需要导入到k3
        backupsService.saveSupplierData(backSupplierVOS);
//        k3CLOUDApi.batchsaveSupplier(dataCentre,userName,password,)
        return ResponseResult.success();
    }


    /**
     * @Author renqingyun
     * @Date: 2020/3/17 11:18
     * @Description: 会员导入
     */
    @ResponseBody
    @RequestMapping(value = "/backupsMember", method = RequestMethod.POST)
    public synchronized ResponseResult<String> backupsMember(@RequestParam(value = "file") MultipartFile file) throws Exception {
        File backupfile = ReadExcel2003_2007.multipartFileToFile(file);
        //转换成csv的数据
        List<String[]> list = ReadExcel2003_2007.getRecords(backupfile, 13);
        Map map = handleMember(list);
        List<BackMemberVO> backMemberVOS = (List) map.get("backMemberVOS");
        List<BackWalletVO> backWalletVOS = (List) map.get("backWalletVOS");

        Map map1 = new HashMap();
        map1.put("list", backMemberVOS);
        backupsService.saveMember(map1);
        //导入用户的钱包
        Map map2 = new HashMap();
        map2.put("list", backWalletVOS);
        backupsService.saveMemberWallet(map2);
        //查询出所有账户类型
        List<AmountTyp> amountTypList = backupsService.selectAmountType();

        //查询出所有门店
        List<StoreVO> storesList = backupsService.selectStores();

        //账户余额
        List walletAmountList;
        //更新用户账户类型余额
        for (BackWalletVO backWalletVO : backWalletVOS) {
            walletAmountList = new ArrayList<>();
            if (backWalletVO.getRechargeBalance() != "0" && backWalletVO.getRechargeBalance() != null) {
                WalletAmount walletAmount = new WalletAmount();
                walletAmount.setWalletId(backWalletVO.getWalletId());
                walletAmount.setAccountTypeId(amountTypList.get(0).getAccountTypeId());
                walletAmount.setAmount(new BigDecimal(backWalletVO.getRechargeBalance()));
                walletAmountList.add(walletAmount);
            }
            if (backWalletVO.getRebateBalance() != "0" && backWalletVO.getRebateBalance() != null) {
                WalletAmount walletAmount = new WalletAmount();
                walletAmount.setWalletId(backWalletVO.getWalletId());
                walletAmount.setAccountTypeId(amountTypList.get(2).getAccountTypeId());
                walletAmount.setAmount(new BigDecimal(backWalletVO.getRebateBalance()));
                walletAmountList.add(walletAmount);
            }
            if (backWalletVO.getGiveBalance() != "0" && backWalletVO.getGiveBalance() != null) {
                WalletAmount walletAmount = new WalletAmount();
                walletAmount.setWalletId(backWalletVO.getWalletId());
                walletAmount.setAccountTypeId(amountTypList.get(1).getAccountTypeId());
                walletAmount.setAmount(new BigDecimal(backWalletVO.getGiveBalance()));
                walletAmountList.add(walletAmount);
            }
            if (backWalletVO.getTokerBalance() != "0" && backWalletVO.getTokerBalance() != null) {
                WalletAmount walletAmount = new WalletAmount();
                walletAmount.setWalletId(backWalletVO.getWalletId());
                walletAmount.setAccountTypeId(amountTypList.get(3).getAccountTypeId());
                walletAmount.setAmount(new BigDecimal(backWalletVO.getTokerBalance()));
                walletAmountList.add(walletAmount);
            }
            backupsService.updateAmount(walletAmountList);
        }
        return ResponseResult.success();
    }


    /**
     * @return List<T>
     * @Title: getExcelInfo
     * @Description: 读取Excel信息
     * @author fun
     * @date 2018年9月7日
     */
    public List getExcelInfo(MultipartFile mFile, String type) {
        String fileName = mFile.getOriginalFilename();// 获取文件名
        List list = new ArrayList<>();
        Map map = new HashMap<>();
        List<String> error = new ArrayList<>();
        try {
            if (!validateExcel(fileName)) {// 验证文件名是否合格
                return null;
            }
            boolean isExcel2003 = true;// 根据文件名判断文件是2003版本还是2007版本
            if (isExcel2007(fileName)) {
                isExcel2003 = false;
            }
            list = createExcel(mFile.getInputStream(), isExcel2003, error, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * @return boolean
     * @Title: validateExcel
     * @Description: 验证EXCEL文件
     * @author fun
     * @date 2018年9月10日
     */
    public boolean validateExcel(String filePath) {
        if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))) {
            String errorMsg = "文件名不是excel格式";
            return false;
        }
        return true;
    }


    // 是否是2003的excel，返回true是2003
    public static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    // 是否是2007的excel，返回true是2007
    public static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }


    /**
     * @return List<T>
     * @Title: createExcel
     * @Description: 根据excel里面的内容读取信息
     * @author fun
     * @date 2018年9月10日
     */
    public List createExcel(InputStream is, boolean isExcel2003, List<String> error, String type) {
        List list = new ArrayList<>();
//        Map map = new HashMap<>();
        try {
            Workbook wb = null;
            if (isExcel2003) {// 当excel是2003时,创建excel2003
                wb = new HSSFWorkbook(is);
            } else {// 当excel是2007时,创建excel2007
                wb = new XSSFWorkbook(is);
            }
//            map = readExcelValue(wb, error, type);// 读取Excel里面客户的信息
            list = readExcelValue(wb, error, type);// 读取Excel里面客户的信息
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * @return List<T>
     * @Title: readExcelValue
     * @Description: 读取Excel信息
     * @author fun
     * @date 2018年9月5日
     */
    public List readExcelValue(Workbook wb, List<String> error, String type) {
        int totalRows = 0;
        int totalCells = 0;
        // 得到第一个shell
        Sheet sheet = wb.getSheetAt(0);
        // 得到Excel的行数
        totalRows = sheet.getPhysicalNumberOfRows();
        // 得到Excel的列数(前提是有行数)
        if (totalRows > 1 && sheet.getRow(0) != null) {
            totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
        }

        List<ProductDBVO> productDBVOList = new ArrayList<>();
        List<ServiceProductDBVO> serviceProductDBVOList = new ArrayList<>();
        List<SupplierDBVO> supplierDBVOList = new ArrayList<>();

        // 循环Excel行数
        for (int r = 1; r < totalRows; r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            T model = new T();
            // 循环Excel的列
            ProductDBVO productDBVO = new ProductDBVO();
            ServiceProductDBVO serviceProductDBVO = new ServiceProductDBVO();
            SupplierDBVO supplierDBVO = new SupplierDBVO();
            for (int c = 0; c < totalCells; c++) {
                Cell cell = row.getCell(c);
                cell.setCellType(CellType.STRING);
                if (null != cell) {
                    if (type.equals("product")) {
                        if (c == 0) {
                            productDBVO.setProductName(cell.getStringCellValue());
                        } else if (c == 1) {
                            productDBVO.setProductSubordinate(cell.getStringCellValue());
                        } else if (c == 2) {
                            productDBVO.setCommodityTypeID(cell.getStringCellValue());
                        } else if (c == 3) {
                            productDBVO.setSubClassID(cell.getStringCellValue());
                        } else if (c == 4) {
                            productDBVO.setBarredBuying(cell.getStringCellValue());
                        } else if (c == 5) {
                            productDBVO.setBarredPayMethod(cell.getStringCellValue());
                        } else if (c == 6) {
                            productDBVO.setProvinceId(cell.getStringCellValue());
                        } else if (c == 7) {
                            productDBVO.setCityId(cell.getStringCellValue());
                        } else if (c == 8) {
                            productDBVO.setCountyId(cell.getStringCellValue());
                        } else if (c == 9) {
                            productDBVO.setProductKind(cell.getStringCellValue());
                        } else if (c == 10) {
                            productDBVO.setProductEffect(cell.getStringCellValue());
                        } else if (c == 11) {
                            productDBVO.setProductBrand(cell.getStringCellValue());
                        } else if (c == 12) {
                            productDBVO.setProductCategory(cell.getStringCellValue());
                        } else if (c == 13) {
                            productDBVO.setProductSpecification(cell.getStringCellValue());
                        } else if (c == 14) {
                            productDBVO.setProductOriginalPrice(cell.getStringCellValue());
                        } else if (c == 15) {
                            productDBVO.setRetailPrice(cell.getStringCellValue());
                        } else if (c == 16) {
                            productDBVO.setActivityRetailPrice(cell.getStringCellValue());
                        } else if (c == 17) {
                            productDBVO.setIsDiscount(cell.getStringCellValue());
                        } else if (c == 18) {
                            productDBVO.setNetContent(cell.getStringCellValue());
                        } else if (c == 19) {
                            productDBVO.setProductSales(cell.getStringCellValue());
                        } else if (c == 20) {
                            productDBVO.setUnitId(cell.getStringCellValue());
                        } else if (c == 21) {
                            productDBVO.setInstoragePrice(cell.getStringCellValue());
                        } else if (c == 22) {
                            productDBVO.setOutstoragePrice(cell.getStringCellValue());
                        } else if (c == 23) {
                            productDBVO.setOutstorageProfit(cell.getStringCellValue());
                        } else if (c == 24) {
                            productDBVO.setMoreContent(cell.getStringCellValue());
                        }
                    } else if (type.equals("serviceproduct")) {
                        if (c == 0) {
                            serviceProductDBVO.setProductName(cell.getStringCellValue());
                        } else if (c == 1) {
                            serviceProductDBVO.setIndustry(cell.getStringCellValue());
                        } else if (c == 2) {
                            serviceProductDBVO.setCommodityTypeID(cell.getStringCellValue());
                        } else if (c == 3) {
                            serviceProductDBVO.setSubClassID(cell.getStringCellValue());
                        } else if (c == 4) {
                            serviceProductDBVO.setProductOriginalPrice(cell.getStringCellValue());
                        } else if (c == 5) {
                            serviceProductDBVO.setRetailPrice(cell.getStringCellValue());
                        } else if (c == 6) {
                            serviceProductDBVO.setActivityRetailPrice(cell.getStringCellValue());
                        } else if (c == 7) {
                            serviceProductDBVO.setIsDiscount(cell.getStringCellValue());
                        } else if (c == 8) {
                            serviceProductDBVO.setProductSales(cell.getStringCellValue());
                        } else if (c == 9) {
                            serviceProductDBVO.setUnitId(cell.getStringCellValue());
                        } else if (c == 10) {
                            serviceProductDBVO.setInstoragePrice(cell.getStringCellValue());
                        } else if (c == 11) {
                            serviceProductDBVO.setOutstoragePrice(cell.getStringCellValue());
                        } else if (c == 12) {
                            serviceProductDBVO.setOutstorageProfit(cell.getStringCellValue());
                        } else if (c == 13) {
                            serviceProductDBVO.setMoreContent(cell.getStringCellValue());
                        }
                    } else if (type.equals("supplier")) {
                        if (c == 0) {
                            supplierDBVO.setSupplierName(cell.getStringCellValue());
                        } else if (c == 1) {
                            supplierDBVO.setShortName(cell.getStringCellValue());
                        } else if (c == 2) {
                            supplierDBVO.setSupplierCategoryId(cell.getStringCellValue());
                        } else if (c == 3) {
                            supplierDBVO.setBankDeposit(cell.getStringCellValue());
                        } else if (c == 4) {
                            supplierDBVO.setCreditCardNum(cell.getStringCellValue());
                        } else if (c == 5) {
                            supplierDBVO.setLinkMan(cell.getStringCellValue());
                        }
                    }
                }
            }
            if (type.equals("product")) {
                productDBVOList.add(productDBVO);
            } else if (type.equals("serviceproduct")) {

            } else if (type.equals("supplier")) {
                supplierDBVOList.add(supplierDBVO);
            }
        }
//        Map map = new HashMap<>();
//        if (type.equals("product")) {
//            map.put("list", productDBVOList);
//            return map;
//        } else if (type.equals("serviceproduct")) {
//            map.put("list", serviceProductDBVOList);
//            return map;
//        } else {
//            map.put("list", supplierDBVOList);
//            return map;
//        }

        if (type.equals("product")) {
            return productDBVOList;
        } else if (type.equals("serviceproduct")) {
            return serviceProductDBVOList;
        } else {
            return supplierDBVOList;
        }
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


    /**
     * 处理物料--产品导入数据
     *
     * @param list
     * @return
     */
    public Map handleProduct(List<String[]> list) {
        List<BackProductVO> productlist = new ArrayList<>();
        List<ProductParamVO> k3arrayList = new ArrayList<>();

        for (int i = 1; i < list.size(); i++) {
            BackProductVO backProductVO = new BackProductVO();
            ProductParamVO productParamVO = new ProductParamVO();
            String[] obj = list.get(i);
            //循环每个对象
            for (int j = 0; j < obj.length; j++) {
                if (j == 0) {
                    productParamVO.setFName(obj[j]);
                    k3arrayList.add(productParamVO);
                    backProductVO.setProductName(obj[j]);
                } else if (j == 1) {
                    backProductVO.setProductSubordinate(obj[j]);
                } else if (j == 2) {
                    backProductVO.setCommodityTypeID(Long.parseLong(obj[j]));
                } else if (j == 3) {
                    backProductVO.setSubClassID(Long.parseLong(obj[j]));
                } else if (j == 4) {
                    backProductVO.setAchievementId(Long.parseLong(obj[j]));
                } else if (j == 5) {
                    backProductVO.setBarredBuying(obj[j]);
                } else if (j == 6) {
                    backProductVO.setBarredPayMethod(obj[j]);
                } else if (j == 7) {
                    backProductVO.setProvinceId(Long.parseLong(obj[j]));
                } else if (j == 8) {
                    backProductVO.setCityId(Long.parseLong(obj[j]));
                } else if (j == 9) {
                    backProductVO.setCountyId(Long.parseLong(obj[j]));
                } else if (j == 10) {
                    backProductVO.setProductKind(Long.parseLong(obj[j]));
                } else if (j == 11) {
                    backProductVO.setProductEffect(Long.parseLong(obj[j]));
                } else if (j == 12) {
                    backProductVO.setProductBrand(Long.parseLong(obj[j]));
                } else if (j == 13) {
                    backProductVO.setProductCategory(Long.parseLong(obj[j]));
                } else if (j == 14) {
                    backProductVO.setProductSpecification(obj[j]);
                } else if (j == 15) {
                    backProductVO.setProductOriginalPrice(new BigDecimal(obj[j]));
                } else if (j == 16) {
                    backProductVO.setRetailPrice(new BigDecimal(obj[j]));
                } else if (j == 17) {
                    backProductVO.setActivityRetailPrice(new BigDecimal(obj[j]));
                } else if (j == 18) {
                    backProductVO.setIsDiscount(Integer.valueOf(obj[j]));
                } else if (j == 19) {
                    backProductVO.setNetContent(obj[j]);
                } else if (j == 20) {
                    backProductVO.setProductSales(Integer.valueOf(obj[j]));
                } else if (j == 21) {
                    backProductVO.setUnitId(Integer.valueOf(obj[j]));
                } else if (j == 22) {
                    backProductVO.setInstoragePrice(new BigDecimal(obj[j]));
                } else if (j == 23) {
                    backProductVO.setOutstoragePrice(new BigDecimal(obj[j]));
                } else if (j == 24) {
                    backProductVO.setOutstorageProfit(new BigDecimal(obj[j]));
                } else if (j == 25) {
                    backProductVO.setMoreContent(obj[j]);
                }
            }
            String productCode = CodeUtils.getCharAndNumr(8);
            backProductVO.setProductCode(productCode);
            productlist.add(backProductVO);
        }
        Map<Object, Object> map = new HashMap<>();
        map.put("productlist", productlist);
        map.put("k3arrayList", k3arrayList);
        return map;
    }


    /**
     * 处理物料--服务导入数据
     *
     * @param list
     * @return
     */
    public Map handleServiceProduct(List<String[]> list) {
        List<BackServiceProductVO> serviceproductlist = new ArrayList<>();
        List<ProductParamVO> k3arrayList = new ArrayList<>();

        for (int i = 1; i < list.size(); i++) {
            BackServiceProductVO serviceProductVO = new BackServiceProductVO();
            ProductParamVO productParamVO = new ProductParamVO();
            String[] obj = list.get(i);
            //循环每个对象
            for (int j = 0; j < obj.length; j++) {
                if (j == 0) {
                    productParamVO.setFName(obj[j]);
                    k3arrayList.add(productParamVO);
                    serviceProductVO.setProductName(obj[j]);
                } else if (j == 1) {
                    serviceProductVO.setIndustryId(Long.parseLong(obj[j]));
                } else if (j == 2) {
                    serviceProductVO.setSubClassID(Long.parseLong(obj[j]));
                } else if (j == 3) {
                    serviceProductVO.setProductOriginalPrice(new BigDecimal(obj[j]));
                } else if (j == 4) {
                    serviceProductVO.setRetailPrice(new BigDecimal(obj[j]));
                } else if (j == 5) {
                    serviceProductVO.setActivityRetailPrice(new BigDecimal(obj[j]));
                } else if (j == 6) {
                    serviceProductVO.setIsDiscount(Integer.valueOf(obj[j]));
                } else if (j == 7) {
                    serviceProductVO.setProductSales(Integer.valueOf(obj[j]));
                } else if (j == 8) {
                    serviceProductVO.setUnitId(Integer.valueOf(obj[j]));
                } else if (j == 9) {
                    serviceProductVO.setInstoragePrice(new BigDecimal(obj[j]));
                } else if (j == 10) {
                    serviceProductVO.setOutstoragePrice(new BigDecimal(obj[j]));
                } else if (j == 11) {
                    serviceProductVO.setOutstorageProfit(new BigDecimal(obj[j]));
                } else if (j == 12) {
                    serviceProductVO.setMoreContent(obj[j]);
                }
            }
            serviceproductlist.add(serviceProductVO);
        }
        Map<Object, Object> map = new HashMap<>();
        map.put("serviceproductlist", serviceproductlist);
        map.put("k3arrayList", k3arrayList);
        return map;
    }


    /**
     * 处理供应商导入数据
     *
     * @param list
     * @return
     */
    public Map handleSupplier(List<String[]> list) {
        List<BackSupplierVO> backSupplierVOS = new ArrayList<>();
        List<ProductParamVO> k3arrayList = new ArrayList<>();

        for (int i = 1; i < list.size(); i++) {
            BackSupplierVO backSupplierVO = new BackSupplierVO();
            ProductParamVO productParamVO = new ProductParamVO();
            String[] obj = list.get(i);
            //循环每个对象
            for (int j = 0; j < obj.length; j++) {
                if (j == 0) {
                    productParamVO.setFName(obj[j]);
                    k3arrayList.add(productParamVO);
                    backSupplierVO.setSupplierName(obj[j]);
                } else if (j == 1) {
                    backSupplierVO.setShortName(obj[j]);
                } else if (j == 2) {
                    backSupplierVO.setSupplierCategoryId(Long.parseLong(obj[j]));
                } else if (j == 3) {
                    backSupplierVO.setBankDeposit(obj[j]);
                } else if (j == 4) {
                    backSupplierVO.setCreditCardNum(obj[j]);
                } else if (j == 5) {
                    backSupplierVO.setLinkMan(obj[j]);
                } else if (j == 6) {
                    backSupplierVO.setLinkPhone(obj[j]);
                } else if (j == 7) {
                    backSupplierVO.setAddress(obj[j]);
                }
            }
            backSupplierVOS.add(backSupplierVO);
        }
        Map<Object, Object> map = new HashMap<>();
        map.put("backSupplierVOS", backSupplierVOS);
        map.put("k3arrayList", k3arrayList);
        return map;
    }


    /**
     * 处理会员导入数据
     *
     * @param list
     * @return
     */
    public Map handleMember(List<String[]> list) {
        List<BackMemberVO> backMemberVOS = new ArrayList<>();
        List<BackWalletVO> backWalletVOS = new ArrayList<>();
        //查询出所有门店
        List<StoreVO> returnstoresList = backupsService.selectStores();
        for (int i = 1; i < list.size(); i++) {
            BackMemberVO backMemberVO = new BackMemberVO();
            BackWalletVO backWalletVO = new BackWalletVO();
            String[] obj = list.get(i);
            //循环每个对象
            for (int j = 0; j < obj.length; j++) {
                if (j == 0) {
                    backMemberVO.setUserName(obj[j]);
                    backWalletVO.setCardNumber(obj[j]);
                } else if (j == 1) {
                    backMemberVO.setName(obj[j]);
                } else if (j == 2) {
                    backMemberVO.setMobile(obj[j]);
                } else if (j == 3) {
                    backMemberVO.setMemberNum(obj[j]);
                } else if (j == 4) {
                    backMemberVO.setMembershipLevelId(obj[j]);
                } else if (j == 5) {
                    backMemberVO.setMembershipLevelName(obj[j]);
                } else if (j == 6) {
                    backMemberVO.setWxOpenId(obj[j]);
                } else if (j == 7) {
                    backMemberVO.setHeadImgUrl(obj[j]);
                } else if (j == 8) {
                    for (StoreVO storeVO : returnstoresList) {
                        if (storeVO.getName().equals(obj[j])) {
                            backMemberVO.setStoreId(storeVO.getStoreId());
                        }
                    }
                } else if (j == 9) {
                    backWalletVO.setRechargeBalance(obj[j]);
                } else if (j == 10) {
                    backWalletVO.setRebateBalance(obj[j]);
                } else if (j == 11) {
                    backWalletVO.setGiveBalance(obj[j]);
                } else if (j == 12) {
                    backWalletVO.setTokerBalance(obj[j]);
                }
            }
            backMemberVOS.add(backMemberVO);
            backWalletVOS.add(backWalletVO);
        }
        Map<Object, Object> map = new HashMap<>();
        map.put("backMemberVOS", backMemberVOS);
        map.put("backWalletVOS", backWalletVOS);
        return map;
    }
}
