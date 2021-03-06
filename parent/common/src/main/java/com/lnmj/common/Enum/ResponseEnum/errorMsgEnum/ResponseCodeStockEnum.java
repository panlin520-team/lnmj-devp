package com.lnmj.common.Enum.ResponseEnum.errorMsgEnum;

/**
 * @Auther: panlin
 * @Date: 2019/4/22 13:46
 * @Description:
 */
public enum ResponseCodeStockEnum {
    //商品数量变化
    PRODUCT_NUMBER_NOT_FIND(37, "商品数量没找到"),

    //k3
    SAVE_FAILED(37, "K3保存失败"),
    SUBMIT_FAILED(37, "K3提交失败"),
    VIEW_FAILED(37, "K3查看失败"),
    AUDIT_FAILED(37, "K3审核失败"),
    UNAUDIT_FAILED(37, "K3反审核失败"),
    DELETE_FAILED(37, "K3删除失败"),
    FORBID_FAILED(37, "K3禁用失败"),
    ENABLE_FAILED(37, "K3反禁用失败"),
    INVALID_FAILED(37, "K3作废失败"),
    K3_ERROR(37, "K3错误"),
    LOGIN_ERROR(37, "登录失败"),
    TYPE_ERROR(37, "类型错误"),

    //stock
    STOCK_NULL(37, "仓库为空"),
    STOCK_ID_NOT_NULL(37, "仓库ID不能为空"),
    COMPANY_NOT_NULL(37, "公司不能为空"),
    COMPANY_TYPE_NOT_NULL(37, "公司分类不能为空"),
    STOCK_NOT_FIND(37, "仓库没找到"),
    STOCK_AUDIT_FAILED(37, "审核仓库失败"),
    STOCK_INSERT_FAILED(37, "插入仓库失败"),
    STOCK_UPDATE_FAILED(37, "更新仓库失败"),
    STOCK_DELETE_FAILED(37, "删除仓库失败"),
    STOCK_EXIST(37, "仓库已经存在"),
    //stockstatus
    STOCKSTATUS_NOT_FIND(37, "仓库状态没找到"),
    STOCKSTATUS_ID_NOT_NULL(37, "仓库状态ID不能为空"),
    STOCKSTATUS_AUDIT_FAILED(37, "审核仓库状态失败"),
    STOCKSTATUS_INSERT_FAILED(37, "插入仓库状态失败"),
    STOCKSTATUS_UPDATE_FAILED(37, "更新仓库状态失败"),
    STOCKSTATUS_DELETE_FAILED(37, "删除仓库状态失败"),
    //stockproduct
    STOCKPRODUCT_NOT_FIND(37, "仓库商品没找到"),
    STOCKPRODUCT_ID_NOT_NULL(37, "仓库商品ID不能为空"),
    STOCKPRODUCT_INSERT_FAILED(37, "插入仓库商品失败"),
    STOCKPRODUCT_UPDATE_FAILED(37, "更新仓库商品失败"),
    STOCKPRODUCT_DELETE_FAILED(37, "删除仓库商品失败"),
    STOCKPRODUCT_NOT_ALL(37, "仓库商品库存不足"),
    STOCKPRODUCT_NOT(37, "仓库商品没库存"),
    INSTORAGE_PRODUCT_OUTSTORAGE(37, "入库商品已被出库"),
    //instorage
    INSTORAGE_NOT_FIND(37, "入库没找到"),
    INSTORAGETYPE_NOT_NULL(37, "入库类型不能为空"),
    INVOICESTYPE_NOT_NULL(37, "单据类型不能为空"),
    BUSINESSTYPE_NOT_NULL(37, "业务类型不能为空"),
    BUSINESSTYPE_ERROR(37, "业务类型错误"),
    INSTORAGESTATUS_NOT_NULL(37, "单据状态不能为空"),
    INVOICESTYPE_ERROR(37, "单据类型错误"),
    SHIPPERTYPE_NOT_NULL(37, "货主类型不能为空"),
    SHIPPER_NOT_NULL(37, "货主不能为空"),
    SUPPLIER_ORG_EMPLOYEES_IS_NULL(37, "未选择销售员，无法出库"),
    INVENTORYGROUP_NOT_NULL(37, "库存组织不能为空"),
    RECEIVEGROUP_NOT_NULL(37, "接收组织不能为空"),
    NEEDGROUP_NOT_NULL(37, "需求组织不能为空"),
    PURCHASEGROUP_NOT_NULL(37, "采购组织不能为空"),
    SETTLEMENTGROUP_NOT_NULL(37, "结算组织不能为空"),
    SETTLEMENTCURRENCY_NOT_NULL(37, "结算币别不能为空"),
    PROVIDER_NOT_NULL(37, "供应商不能为空"),
    INVENTORYWAY_NOT_NULL(37, "库存方向不能为空"),
    INVENTORYWAY_ERROR(37, "库存方向错误"),
    INSTORAGEDATE_NOT_NULL(37, "入库日期不能为空"),
    INSTORAGE_ID_NOT_NULL(37, "入库ID不能为空"),
    PRODUCTCODE_NOT_NULL(37, "商品不能为空"),
    UNIT_NOT_NULL(37, "单位不能为空"),
    RECEIVEDNUMBER_NOT_NULL(37, "入库数量不能为空"),
    STOCK_NOT_NULL(37, "仓库不能为空"),
    STOCKSTATUS_NOT_NULL(37, "仓库状态不能为空"),
    INSTORAGE_INSERT_FAILED(37, "插入入库失败"),
    INSTORAGE_UPDATE_FAILED(37, "更新入库失败"),
    INSTORAGE_DELETE_FAILED(37, "删除入库失败"),
    INSTORAGE_AUDITOR_FAILED(37, "审核入库单失败"),
    DEFAULT_CUST_IS_NOT_FIND(37, "操作失败，找不到默认客户,请联系管理员添加"),
    DEFAULT_DEPT_IS_NOT_FIND(37, "操作失败，找不到默认部门,请联系管理员添加"),
    //outstorage
    OUTSTORAGE_NOT_FIND(37, "出库没找到"),
    OUTSTORAGE_ID_NOT_NULL(37, "出库ID不能为空"),
    OUTSTORAGETYPE_NOT_NULL(37, "出库类型不能为空"),
    OUTSTORAGEPRODUCT_NOT_NULL(37, "出库商品不能为空"),
    OUTSTORAGEDATE_NOT_NULL(37, "出库日期不能为空"),
    MARKETGROUP_NOT_NULL(37, "销售组织不能为空"),
    CLIENT_NOT_NULL(37, "客户不能为空"),
    SHIPMENTGROUP_NOT_NULL(37, "发货组织不能为空"),
    SENDNUMBER_NOT_NULL(37, "销售数量不能为空"),
    RECEIVEGROUP1_NOT_NULL(37, "领用组织不能为空"),
    OUTSTORAGE_INSERT_FAILED(37, "插入出库失败"),
    OUTSTORAGE_UPDATE_FAILED(37, "更新出库失败"),
    OUTSTORAGE_DELETE_FAILED(37, "删除出库失败"),
    OUTSTORAGE_ORG_PRODUCT_NULL(37, "存在出库商品在出库组织中不存在，无法出库"),
    NAME_ERROR(37, "Name错误"),
    CREATE_OPERATOR_NULL(37, "创建人不能为空"),
    MODIFY_OPERATOR_NULL(37, "修改人不能为空"),
    ;
    private final int code;
    private final String desc;

    ResponseCodeStockEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    public int getCode(){
        return code;
    }
    public String getDesc(){
        return desc;
    }

}
