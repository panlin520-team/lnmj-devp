package com.lnmj.paySettlement.business.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.lnmj.common.Enum.OrderStatusEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponsePayEnum;
import com.lnmj.common.config.AlipayConfig;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.paySettlement.business.IPaySettlementService;
import com.lnmj.paySettlement.config.OurWxPayConfig;
import com.lnmj.paySettlement.config.WxpayParam;
import com.lnmj.paySettlement.entity.PayType;
import com.lnmj.paySettlement.repository.IPaySettlementDao;
import com.lnmj.paySettlement.serviceProxy.OrderApi;
import com.lnmj.paySettlement.serviceProxy.WalletApi;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈〉
 *
 * @Author panlin
 * @create 2019/6/4
 */

@Service("paySettlementService")
public class paySettlementService implements IPaySettlementService {
    @Resource
    private IPaySettlementDao iPaySettlementDao;
    @Resource
    private OrderApi orderApi;
    @Resource
    private WalletApi walletApi;

    @Override
    public ResponseResult alipay(Long orderNumber) {
        // 商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = orderNumber.toString();
        // TODO 根据订单编号查看订单信息
        // 订单名称，必填
        String subject = "护理订单";
        System.out.println(subject);
        // 付款金额，必填
        String total_amount = "0.01";
        // 商品描述，可空
        String body = "";
        // 超时时间 可空
        String timeout_express = "2m";
        // 销售产品码 必填
        String product_code = "QUICK_WAP_WAY";
        /**********************/
        // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
        //调用RSA签名方式
        AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
        AlipayTradeWapPayRequest alipay_request = new AlipayTradeWapPayRequest();

        // 封装请求支付信息
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setOutTradeNo(out_trade_no);
        model.setSubject(subject);
        model.setTotalAmount(total_amount);
        model.setBody(body);
        model.setTimeoutExpress(timeout_express);
        model.setProductCode(product_code);
        alipay_request.setBizModel(model);
        // 设置异步通知地址
        alipay_request.setNotifyUrl(AlipayConfig.notify_url);
        // 设置同步地址
        alipay_request.setReturnUrl(AlipayConfig.return_url);
        // form表单生产
        String form = "";
        try {
            // 调用SDK生成表单
            form = client.pageExecute(alipay_request).getBody();
            return ResponseResult.success(form);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseResult walletPay(Long orderNumber, Long userId) {
        // TODO 根据订单编号查看订单信息
        BigDecimal total_amount = new BigDecimal(1.00);
        ;
        // TODO 根据用户id查看用户钱包
        BigDecimal rechargeAmount = new BigDecimal(2.00);
        if (rechargeAmount.compareTo(total_amount) == -1) {
            //充值余额小于订单金额
            ResponseResult.error(new Error(ResponsePayEnum.RECHARGEAMOUNT_NOT_ENOUGH.getCode(), ResponsePayEnum.RECHARGEAMOUNT_NOT_ENOUGH.getDesc()));
        }
        // 修改订单状态为已经支付
        orderApi.updateOrderStatus(orderNumber, OrderStatusEnum.PAID.getCode());
        // 修改钱包充值金额
        walletApi.updateRechargeAmount(userId, total_amount.negate());
        return ResponseResult.success();
    }

    @Override
    public ResponseResult selectPayTypeList(String memberNum, String subClassId, String industryId, String showAll) {
        List<PayType> payTypeList = iPaySettlementDao.selectPayTypeList();
        //查看根据小类能够使用的支付方式
        List<PayType> payTypeListResult = new ArrayList<>();
        if (subClassId != null && industryId != null) { //subClassId : "1","2"
            String[] subClassIdList = subClassId.split(",");
            //匹配允许的小类和行业
            for (PayType payType : payTypeList) {
                JSONArray subClassArray = JSON.parseArray(payType.getSubclass());
                JSONArray industryArray = JSON.parseArray(payType.getIndustry());
                if ((subClassArray.contains(subClassIdList) && industryArray.indexOf(industryId) != -1) ||
                        (subClassArray.size() == 0 && industryArray.size() == 0) ||
                        (subClassArray.size() == 0 && industryArray.indexOf(industryId) != -1) ||
                        (subClassArray.contains(subClassIdList) && industryArray.size() == 0)) {
                    payTypeListResult.add(payType);
                }
            }
        } else {
            payTypeListResult.addAll(payTypeList);
        }

        if (StringUtils.isNotBlank(memberNum) && !memberNum.equals("0")) {//如果是0 说明是后台查看
            //查看会员拥有的支付方式
            Long walletId = Long.parseLong(walletApi.selectWalletByCarNum(memberNum).getResult().toString());
            List<Map> walletAmountList = (List<Map>) walletApi.selectAccountAmountByWalletId(walletId).getResult();
            for (PayType payType : payTypeListResult) {
                for (Map map : walletAmountList) {
                    if (payType.getAccountType() != null && payType.getAccountType().toString().equals(map.get("accountTypeId").toString())) {
                        payType.setAccountTypeAmount(Double.parseDouble(map.get("amount").toString()));
                    }
                }
            }
        }

        List<PayType> payTypeListResult2 = new ArrayList<>();
        if (StringUtils.isBlank(showAll)) {
            if (StringUtils.isBlank(memberNum)) {
                //如果不是会员只显示现金支付方式
                for (PayType payType : payTypeListResult) {
                    if (payType.getPayTypeCategory() == 1) {
                        payTypeListResult2.add(payType);
                    }
                }
            } else {
                for (PayType payType : payTypeListResult) {
                    payTypeListResult2.add(payType);
                }
            }
        } else  {
            //显示所有
            payTypeListResult2.addAll(payTypeListResult);
        }

        if (payTypeListResult != null && payTypeListResult.size() != 0) {
            return ResponseResult.success(payTypeListResult2);
        }
        return ResponseResult.error(new Error(ResponsePayEnum.PAY_TYPE_NULL.getCode(), ResponsePayEnum.PAY_TYPE_NULL.getDesc()));

    }

    @Override
    public ResponseResult selectPayTypeListAll() {
        List<PayType> payTypeList = iPaySettlementDao.selectPayTypeList();
        return ResponseResult.success(payTypeList);
    }

    @Override
    public ResponseResult selectPayTypeById(Long payTypeId) {
        PayType payType = iPaySettlementDao.selectPayTypeById(payTypeId);
        return ResponseResult.success(payType);
    }

    @Override
    public ResponseResult selectPayTypeByAccountType(Long accountType) {
        PayType payType = iPaySettlementDao.selectPayTypeByAccountType(accountType);
        return ResponseResult.success(payType);
    }

    @Override
    public ResponseResult deletePayType(Long payTypeId) {
        if (payTypeId == null) {
            return ResponseResult.error(new Error(ResponsePayEnum.PAY_TYPE_ID_NULL.getCode(), ResponsePayEnum.PAY_TYPE_ID_NULL.getDesc()));
        }
        PayType payTypeById = iPaySettlementDao.selectPayTypeById(payTypeId);
        if (payTypeById == null) {
            return ResponseResult.error(new Error(ResponsePayEnum.PAY_TYPE_NULL.getCode(),
                    ResponsePayEnum.PAY_TYPE_NULL.getDesc()));
        }
        int deleteResult = iPaySettlementDao.deletePayType(payTypeId);
        if (deleteResult > 0) {
            return ResponseResult.success();
        } else {
            return ResponseResult.error(new Error(ResponsePayEnum.PAY_TYPE_DELETE_FAIL.getCode(), ResponsePayEnum.PAY_TYPE_DELETE_FAIL.getDesc()));

        }

    }

    @Override
    public ResponseResult insertPayType(PayType payType) {
        payType.setPaymentRatioOriginal(payType.getPaymentRatioOriginal() / 100);
        iPaySettlementDao.insertPayType(payType);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult alipayFacePay(Long orderNumber) {
        // 商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = orderNumber.toString();
        // TODO 根据订单编号查看订单信息
        // 订单名称，必填
        String subject = "护理订单";
        // 付款金额，必填
        String total_amount = "0.01";
        // 商品描述，可空
        String body = "";
        // 超时时间 可空
        String timeout_express = "2m";
        /**********************/
        // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
        //调用RSA签名方式
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        model.setOutTradeNo(out_trade_no);//必须
        /* model.setSellerId();//可选*/
        model.setTotalAmount(total_amount);//必选
      /*  model.setDiscountableAmount();//可选
        model.setUndiscountableAmount();//可选*/
        model.setSubject(subject);//必选
        /*model.setGoodsDetail();//可选
        model.setBody();//可选
        model.setOperatorId();//可选
        model.setStoreId();//可选
        model.setDisablePayChannels();//可选
        model.setEnablePayChannels();//可选
        model.setTerminalId();//可选
        model.setExtendParams();//可选
        model.setTimeoutExpress();//可选
        model.setSettleInfo();//可选
        model.setBusinessParams();//可选
        model.setQrCodeTimeoutExpress();//可选*/
        request.setBizModel(model);
        // 设置异步通知地址
        request.setNotifyUrl(AlipayConfig.notify_url);
        // 设置同步地址
        request.setReturnUrl(AlipayConfig.return_url);
        AlipayTradePrecreateResponse response = null;
        try {
            response = alipayClient.execute(request);
            return ResponseResult.success(response.getQrCode());
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
        return null;
    }

    @Override
    public ResponseResult wxFacePay(Long orderNumber) throws Exception {
        WxpayParam wxpayParam = new WxpayParam();
        String notifyUrl = "www.baidu.com";  //我这里的回调地址是随便写的，到时候需要换成处理业务的回调接口

        OurWxPayConfig ourWxPayConfig = new OurWxPayConfig();
        WXPay wxPay = new WXPay(ourWxPayConfig);

        //根据微信支付api来设置
        Map<String, String> data = new HashMap<>();
        data.put("appid", ourWxPayConfig.getAppID());
        data.put("mch_id", ourWxPayConfig.getMchID());         //商户号
        data.put("trade_type", "APP");                         //支付场景 APP 微信app支付 JSAPI 公众号支付  NATIVE 扫码支付
        data.put("notify_url", notifyUrl);                     //回调地址
        data.put("spbill_create_ip", "127.0.0.1");             //终端ip
        data.put("total_fee", wxpayParam.getTotalFee());       //订单总金额
        data.put("fee_type", "CNY");                           //默认人民币
        data.put("out_trade_no", wxpayParam.getOutTradeNo());   //交易号
        data.put("body", wxpayParam.getBody());
        data.put("nonce_str", "bfrhncjkfdkfd");   // 随机字符串小于32位
        String s = WXPayUtil.generateSignature(data, ourWxPayConfig.getKey());  //签名
        data.put("sign", s);

        /** wxPay.unifiedOrder 这个方法中调用微信统一下单接口 */
        Map<String, String> respData = wxPay.unifiedOrder(data);
        if (respData.get("return_code").equals("SUCCESS")) {

            //返回给APP端的参数，APP端再调起支付接口
            Map<String, String> repData = new HashMap<>();
            repData.put("appid", ourWxPayConfig.getAppID());
            repData.put("mch_id", ourWxPayConfig.getMchID());
            repData.put("prepayid", respData.get("prepay_id"));
            repData.put("package", "WXPay");
            repData.put("noncestr", respData.get("nonce_str"));
            repData.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
            String sign = WXPayUtil.generateSignature(repData, ourWxPayConfig.getKey()); //签名
            respData.put("sign", sign);
            respData.put("timestamp", repData.get("timestamp"));
            respData.put("package", "WXPay");

            return ResponseResult.success(respData);
        }
        throw new Exception(respData.get("return_msg"));
    }

    @Override
    public ResponseResult updatePayType(PayType payType) {
        PayType payTypeById = iPaySettlementDao.selectPayTypeById(payType.getPayTypeId());
        if (payTypeById == null) {
            return ResponseResult.error(new Error(ResponsePayEnum.PAY_TYPE_NULL.getCode(),
                    ResponsePayEnum.PAY_TYPE_NULL.getDesc()));
        }
        iPaySettlementDao.updatePayType(payType);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult selectPayTypeListBySubClass(String subclass, String industry) {
//        List<PayType> payTypeList = iPaySettlementDao.selectPayTypeList();

        List<PayType> payTypeList = iPaySettlementDao.selectPayTypeListBySubClass();
        List<PayType> list = new ArrayList<>();
        for (PayType type : payTypeList) {
            if (type.getIndustry() != null && type.getSubclass() != null) {
                if ((type.getSubclass().indexOf(subclass)) != -1 && (type.getIndustry().indexOf(industry)) != -1) {
                    list.add(type);
                }
            }
        }
        if (list.size() == 0) {
//            return ResponseResult.error(new Error(ResponsePayEnum.PAY_TYPE_NULL.getCode(),
//                    ResponsePayEnum.PAY_TYPE_NULL.getDesc()));
            return ResponseResult.success(payTypeList);
        } else {
            return ResponseResult.success(list);
        }
    }
}
