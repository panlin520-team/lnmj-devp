package com.lnmj.common.Enum.ResponseEnum.errorMsgEnum;

/**
 * @Auther: panlin
 * @Date: 2019/4/22 13:46
 * @Description:
 */
public enum ResponseCodeProductEnum {

    //商品分类
    PRODUCT_TYPE_NOT_FIND(3,"没有找到商品分类"),

    //商品(
    PRODUCT_NOT_FOUND(3,"没有找到商品"),
    PRODUCTID_ISNULL(3,"商品ID不能为空"),
    PRODUCT_TYPE_ISNULL(3,"商品类型不能为空"),
    SERVICE_PRODUCTID_ISNULL(3,"服务商品ID不能为空"),
    PRODUCTSUBORDINATE_ERROR(3,"请选择正确的商品所属"),
    PRODUCTSTOCKQUANTITY_ERROR(3,"请填写正确的商品库存"),
    PRODUCTCODE_ISNULL(3,"商品编码不能为空"),
    PRODUCTNAME_ISNULL(3,"商品名称不能为空"),
    PRODUCTFORWORDDESCRIPTION_ISNULL(3,"商品转发描述不能为空"),
    PRODUCTPOSCATEGORY_ERROR(3,"请选择正确的POS分类"),
    PRODUCTPROMOTION_ERROR(3,"请选择正确的商品促销"),
    DELIVERYMETHOD_ERROR(3,"请选择正确的商品配送方式"),
    DELIVERYSTORE_ERROR(3,"请选择一个到店门店"),
    CODEBUY_ERROR(3,"请选择正确的验证码购买方式"),
    PRODUCT_KIND_NOT_FIND(3,"没有找到商品种类"),
    UPDATE_FAIL(3,"修改商品失败"),
    ADD_FAIL(3,"添加商品失败"),
    PRODUCT_TYPE_NULL(3,"商品类型不存在"),
    MODIFYOPERATOR_ISNULL(3,"修改人不能为空"),
    PRODUCTP_IS_NOTEXIT(3,"商品不存在"),
    SERVICEPRODUCTP_IS_NOTEXIT(3,"商品不存在"),
    PRODUCTP_NAME_IS_EXIT(3,"商品名称已存在"),
    SERVICEPRODUCTP_NAME_IS_EXIT(3,"服务商品名称已存在"),
    RODUCTP_NAME_IS_EXIT(3,"服务商品名称已存在"),
    TYPE_IS_EXIT(3,"请传表明产品的类型type参数"),
    FIND_NAME_ERROR(3,"查询产品和服务名字错误"),
    //商量促销
    PRODUCTPROMOTIONINFO_NOT_FOUND(3,"没有找到商品促销信息"),
    PRODUCTPROMOTIONNAME_ISNULL(3,"商品促销名称不能为空"),
    PRODUCTPROMOTIONID_ISNULL(3,"商品促销ID不能为空"),
    PRODUCTPROMOTIONNAME_ISEXIST(3,"商品促销名称已存在"),
    PRODUCTPROMOTION_MODIFY_OPERATOR_ISNULL(3,"商品促销修改人为空"),
    PRODUCTROYALTYMONEY_ERROR(9,"商品提成金额参数有误"),
    NOT_PRODUCTPROMOTIONINFO(3,"没有商品促销信息"),
    MODIFYOPERATOR_IS_ERROR(3,"ACCOUNT_NOT_FOUND"),
    PRODUCTIDS_IS_NULL(3,"商品ids不能为空"),
    PRODUCTID_IS_NULL(3,"商品id不能为空"),
    PRODUCTID_CODE_NULL(3,"商品id不能为空"),
    PRODUCTPROMOTION_UPDATE_FAIL(3,"促销修改失败"),
    //商品护理方式
    ADD_PRODUCTNURSING_FAIL(3,"添加商品护理方式失败"),
    PRODUCTNURSING_IDS_ISNULL(3,"商品护理方式id不能为空"),
    PRODUCTNURSING_NAME_ISNULL(3,"商品护理方式名称不能为空"),
    PRODUCTNURSING_FREQUENCY_ERROR(3,"商品护理方式次数不能为空"),
    PRODUCTNURSING_DISCOUNT_ERROR(3,"商品护理方式折扣不能为空"),
    //其他参数
    PAGE_PARAM_ERROR(3,"商品护理方式id不能为空"),
    COMPANY_AND_SUBSIDIARY(3,"请传入正确的总公司和子公司"),
    UPDATE_IN_OUT_FAIL(3,"修改出入库价格以及利润失败"),


    //服务商品
    PUTORDOWN_FAIL(3,"上下架失败");







    private final int code;
    private final String desc;

    ResponseCodeProductEnum(int code, String desc) {
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
