package com.lnmj.account.controller.backend;

import com.lnmj.account.business.IMemberService;
import com.lnmj.account.entity.MMemberamountrecord;
import com.lnmj.account.entity.MMembershipLevel;
import com.lnmj.account.entity.MMembershipLevelRecords;
import com.lnmj.account.entity.VO.UpdateAccountMemberShipLevelRequestVO;
import com.lnmj.account.entity.VO.WalletRechargeRecordRequestVO;
import com.lnmj.common.Enum.ErpNameEnum;
import com.lnmj.common.Enum.MemberLevelUpgringTypeEnum;
import com.lnmj.common.Enum.MembershiplevelEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeAccountEnum;
import com.lnmj.common.Enum.UpgradingWaysEnum;
import com.lnmj.common.Enum.base.BaseEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.common.utils.MemberUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @Author: yilihua
 * @Date: 2019/4/18 11:34
 * @Description: 会员体系controller
 */
@Api(description = "用户会员体系管理")
@RestController
@RequestMapping("/manage/member")
public class MemberManageController {
    @Resource
    private IMemberService memberService;

    //会员升级
    @ApiOperation(value = "会员升级", notes = "会员升级")
    @RequestMapping(value = "/memberUserUpdate", method = RequestMethod.POST)
    public ResponseResult<MMembershipLevelRecords> memberUserUpdate(String memberNumber) {
        if(StringUtils.isBlank(memberNumber)){
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.USERID_NULL.getCode(),
                    ResponseCodeAccountEnum.USERID_NULL.getDesc()));
        }
        return this.memberService.memberUserUpdate(memberNumber);
    }

    @ApiOperation(value = "查询用户升级记录", notes = "查询用户升级记录")
    @RequestMapping(value = "/selectMemberShipLevelUpgradeList", method = RequestMethod.POST)
    public ResponseResult<MMembershipLevelRecords> selectMemberShipLevelUpgradeList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                                                    @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                                                    MMembershipLevelRecords mMembershipLevelRecords, String access_token) {
        //根据条件直接从会员升级记录表查询记录（暂时没有判断输入参数的正确性）
        //根据用户Id直接从会员等级升级记录表查询
        //判断membershiplevelrecordsType是否合法
        Integer membershiplevelrecordsType = mMembershipLevelRecords.getMembershiplevelrecordsType();
        if (membershiplevelrecordsType != null) {
            Map<Integer, String> enumMap = MemberUtil.getEnumToMap(MemberLevelUpgringTypeEnum.class);
            Boolean flag = false;
            for (Map.Entry entry : enumMap.entrySet()) {
                if (membershiplevelrecordsType.equals(entry.getKey())) {
                    flag = true;
                }
            }
            if (!flag) {
                return ResponseResult.error(new Error(ResponseCodeAccountEnum.MEMBER_SHIP_LEVEL_TYPE_WRONGFUL.getCode(),
                        ResponseCodeAccountEnum.MEMBER_SHIP_LEVEL_TYPE_WRONGFUL.getDesc() + ":" + membershiplevelrecordsType));
            }
        }
        return this.memberService.selectMemberShipLevelUpgradeList(pageNum, pageSize, mMembershipLevelRecords);
    }

    @ApiOperation(value = "显示所有enum内容", notes = "显示所有enum内容")
    @RequestMapping(value = "/selectAllErpName", method = RequestMethod.POST)
    public ResponseResult<BaseEnum> selectAllErpName(String name, String access_token) {
        //ENUM字段名字不能为空
        if (StringUtils.isBlank(name) || name == "") {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.ENUM_NAME_NULL.getCode(), ResponseCodeAccountEnum.ENUM_NAME_NULL.getDesc()));
        }
        if ("ErpNameEnum".equals(name) || "UpgradingWaysEnum".equals(name) || "MemberLevelTypeEnum".equals(name) ||
                "ScopeOfUseEnum".equals(name) || "AmountMethodEnum".equals(name) ) {
            return this.memberService.selectAllErpName(name);
        } else {      //ENUM字段名字错误
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.ENUM_NAME_ERROR.getCode(), ResponseCodeAccountEnum.ENUM_NAME_ERROR.getDesc()));
        }
    }

    @ApiOperation(value = "显示所有正常等级", notes = "显示所有正常等级")
    @RequestMapping(value = "/selectAllNormalMemberList", method = RequestMethod.POST)
    public ResponseResult<MMembershipLevel> selectAllNormalMemberList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                                      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, String access_token) {
        return this.memberService.selectAllNormalMemberList(pageNum,pageSize);
    }

    @ApiOperation(value = "显示所有正常等级", notes = "显示所有正常等级")
    @RequestMapping(value = "/selectAllNormalMemberListNoPage", method = RequestMethod.POST)
    public ResponseResult<MMembershipLevel> selectAllNormalMemberListNoPage() {
        return this.memberService.selectAllNormalMemberListNoPage();
    }


    @ApiOperation(value = "新增等级", notes = "新增等级")
    @RequestMapping(value = "/insertMemberShipLevel", method = RequestMethod.POST)
    public ResponseResult insertMemberShipLevel(MMembershipLevel mMembershipLevel, String accessToken) {
        //对必须传入的参数进行判断是否为空
        if (StringUtils.isBlank(mMembershipLevel.getMembershipLevelName())) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.MEMBER_SHIP_LEVEL_NAME_NULL.getCode(), ResponseCodeAccountEnum.MEMBER_SHIP_LEVEL_NAME_NULL.getDesc()));
        }
        if (mMembershipLevel.getAutoUpgrade() == null) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.AUTO_UPGRADE_NULL.getCode(), ResponseCodeAccountEnum.AUTO_UPGRADE_NULL.getDesc()));
        }
        if (mMembershipLevel.getStandardAmount() == null) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.STANDARD_AMOUNT_NULL.getCode(), ResponseCodeAccountEnum.STANDARD_AMOUNT_NULL.getDesc()));
        }
        if (mMembershipLevel.getBestieIntroduceAmount() == null) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.BESTIE_INTRODUCE_AMOUNT_NULL.getCode(), ResponseCodeAccountEnum.BESTIE_INTRODUCE_AMOUNT_NULL.getDesc()));
        }
        if (mMembershipLevel.getUpgradeStandardAmount() == null) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.UPGRADE_STANDARD_AMOUNT_NULL.getCode(), ResponseCodeAccountEnum.UPGRADE_STANDARD_AMOUNT_NULL.getDesc()));
        }
        if (mMembershipLevel.getUpgradeStandardFans() == null) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.UPGRADE_STANDARD_FANS_NULL.getCode(), ResponseCodeAccountEnum.UPGRADE_STANDARD_FANS_NULL.getDesc()));
        }
        if(StringUtils.isBlank(mMembershipLevel.getUpgradingWays())){
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.UPGRADE_WAYS_NULL.getCode(),
                    ResponseCodeAccountEnum.UPGRADE_WAYS_NULL.getDesc()));
        }
        if (mMembershipLevel.getIsDivision() == null) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.ISDIVISION_NULL.getCode(), ResponseCodeAccountEnum.ISDIVISION_NULL.getDesc()));
        }
        if (StringUtils.isBlank(mMembershipLevel.getScopeOfUse())) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.SCOPE_OF_USE_NULL.getCode(), ResponseCodeAccountEnum.SCOPE_OF_USE_NULL.getDesc()));
        }
        if (StringUtils.isBlank(mMembershipLevel.getCreateOperator()) ) {      //创建人
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.CREATE_OPERATOR_NULL.getCode(), ResponseCodeAccountEnum.CREATE_OPERATOR_NULL.getDesc()));
        }
        //判断参数
        //判断修改的字段erpname是否合法,新增时必须有erpname
//        String erpName = mMembershipLevel.getErpName();
//        Map<Integer, String> enumMap = MemberUtil.getEnumToMap(ErpNameEnum.class);
//        Boolean flag = false;
//        for (Map.Entry entry : enumMap.entrySet()) {
//            System.out.println(entry.getValue());
//            if (erpName.equals(entry.getValue())) {
//                flag = true;
//            }
//        }
//        if (!flag) {
//            return ResponseResult.error(new Error(ResponseCodeAccountEnum.ERP_NAME_WRONGFUL.getCode(),
//                    ResponseCodeAccountEnum.ERP_NAME_WRONGFUL.getDesc() + ":" + erpName));
//        }
        //判断upgradingways是否合法
        String upgradingWays = mMembershipLevel.getUpgradingWays();
        if (!StringUtils.isBlank(upgradingWays)) {
            String[] split = upgradingWays.trim().replace("[", "").replace("]", "").
                    replace("{", "").replace("}", "").split(",");
            Map<Integer, String> enumMap1 = MemberUtil.getEnumToMap(UpgradingWaysEnum.class);
            Boolean flag = false;
            for (String s : split) {
                boolean b = enumMap1.containsKey(Integer.valueOf(s));
                if(!b){
                    flag = b;
                }
            }
            if(flag){
                return ResponseResult.error(new Error(ResponseCodeAccountEnum.UPGRADING_WAYS_WRONGFUL.getCode(),
                        ResponseCodeAccountEnum.UPGRADING_WAYS_WRONGFUL.getDesc() + ":" + upgradingWays));
            }
        }
        return this.memberService.insertMemberShipLevel(mMembershipLevel);
    }


    @ApiOperation(value = "关键字查询", notes = "关键字查询")
    @RequestMapping(value = "/selectMemberShipLevelListByKeyWord", method = RequestMethod.POST)
    public ResponseResult<MMembershipLevel> selectMemberShipLevelListByKeyWord(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                                               String keyWord, String access_token) {
        return this.memberService.selectMemberShipLevelListByKeyWord(pageNum, pageSize, keyWord);
    }

    @ApiOperation(value = "根据会员等级查询会员", notes = "根据会员等级查询会员")
    @RequestMapping(value = "/selectUserByMember", method = RequestMethod.POST)
    public ResponseResult selectUserByMember(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                             @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                             Long membershipLevelId, String access_token) {
        return this.memberService.selectUserByMember(pageNum,pageSize,membershipLevelId);
    }



    @ApiOperation(value = "根据会员id，修改会员等级", notes = "根据会员id，修改会员等级")
    @RequestMapping(value = "/updateWalletAccountMemberShipLevelById", method = RequestMethod.POST)
    public ResponseResult updateWalletAccountMemberShipLevelById(UpdateAccountMemberShipLevelRequestVO updateWalletAccountMemberShipLevelRequestVO, String access_token) {
        if (updateWalletAccountMemberShipLevelRequestVO.getMemberNum() == null) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.USERID_NULL.getCode(), ResponseCodeAccountEnum.USERID_NULL.getDesc()));
        }
        if (updateWalletAccountMemberShipLevelRequestVO.getMembershipLevelId() == null) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.MEMBER_SHIP_LEVEL_ID_NULL.getCode(), ResponseCodeAccountEnum.MEMBER_SHIP_LEVEL_ID_NULL.getDesc()));
        }
        if (StringUtils.isBlank(updateWalletAccountMemberShipLevelRequestVO.getModifyOperator())) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.MODIFY_OPERATOR_NULL.getCode(), ResponseCodeAccountEnum.MODIFY_OPERATOR_NULL.getDesc()));
        }
        return this.memberService.updateWalletAccountMemberShipLevelById(updateWalletAccountMemberShipLevelRequestVO);
    }


    @ApiOperation(value = "根据Id修改等级信息", notes = "根据Id修改等级信息")
    @RequestMapping(value = "/updateMemberShipLevelById", method = RequestMethod.POST)
    public ResponseResult updateMemberShipLevelById(MMembershipLevel mMembershipLevel, String access_token) {
        //根据等级id修改等级
        if (mMembershipLevel.getMembershipLevelId() == null) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.MEMBER_SHIP_LEVEL_ID_NULL.getCode(), ResponseCodeAccountEnum.MEMBER_SHIP_LEVEL_ID_NULL.getDesc()));
        }
        //修改人不能为空
        if (StringUtils.isBlank(mMembershipLevel.getModifyOperator())) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.MODIFY_OPERATOR_NULL.getCode(), ResponseCodeAccountEnum.MODIFY_OPERATOR_NULL.getDesc()));
        }
        //判断修改的字段erpname是否合法
//        String erpName = mMembershipLevel.getErpName();
//        if (!StringUtils.isBlank(erpName)) {
//            Map<Integer, String> enumMap = MemberUtil.getEnumToMap(ErpNameEnum.class);
//            Boolean flag = false;
//            for (Map.Entry entry : enumMap.entrySet()) {
//                if (erpName.equals(entry.getValue())) {
//                    flag = true;
//                }
//            }
//            if (!flag) {
//                return ResponseResult.error(new Error(ResponseCodeAccountEnum.ERP_NAME_WRONGFUL.getCode(),
//                        ResponseCodeAccountEnum.ERP_NAME_WRONGFUL.getDesc() + ":" + erpName));
//            }
//        }
        //判断upgradingways是否合法
        String upgradingWays = mMembershipLevel.getUpgradingWays();
        if (!StringUtils.isBlank(upgradingWays)) {
            String[] split = upgradingWays.trim().replace("[", "").replace("]", "").
                    replace("{", "").replace("}", "").split(",");
            Map<Integer, String> enumMap1 = MemberUtil.getEnumToMap(UpgradingWaysEnum.class);
            Boolean flag = false;
            for (String s : split) {
                boolean b = enumMap1.containsKey(Integer.valueOf(s));
                if(!b){
                    flag = b;
                }
            }
            if(flag){
                return ResponseResult.error(new Error(ResponseCodeAccountEnum.UPGRADING_WAYS_WRONGFUL.getCode(),
                        ResponseCodeAccountEnum.UPGRADING_WAYS_WRONGFUL.getDesc() + ":" + upgradingWays));
            }
        }
        return this.memberService.updateMemberShipLevelById(mMembershipLevel);
    }




    @ApiOperation(value = "删除等级", notes = "删除等级")
    @RequestMapping(value = "/deleteMemberShipLevelById", method = RequestMethod.POST)
    public ResponseResult deleteMemberShipLevelById(MMembershipLevel mMembershipLevel, String access_token) {
        //根据等级id删除等级
        if (mMembershipLevel.getMembershipLevelId() == null) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.MEMBER_SHIP_LEVEL_ID_NULL.getCode(), ResponseCodeAccountEnum.MEMBER_SHIP_LEVEL_ID_NULL.getDesc()));
        }
        //修改人不能为空
        if (StringUtils.isBlank(mMembershipLevel.getModifyOperator())) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.MODIFY_OPERATOR_NULL.getCode(), ResponseCodeAccountEnum.MODIFY_OPERATOR_NULL.getDesc()));
        }
        return this.memberService.deleteMemberShipLevelById(mMembershipLevel);
    }



    /**
    *@Description 插入等级会员金额记录
    *@Param [memberamountrecord, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/9/16
    *@Time 16:25
    */
    @ApiOperation(value = "插入等级会员金额记录", notes = "插入等级会员金额记录")
    @RequestMapping(value = "/insertMemberAmountRecord", method = RequestMethod.POST)
    public ResponseResult insertMemberAmountRecord(MMemberamountrecord memberamountrecord, String access_token) {
        return this.memberService.insertMemberAmountRecord(memberamountrecord);
    }
   /**
    *@Description 查询等级会员金额记录
    *@Param [pageNum, pageSize, memberamountrecord, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/9/16
    *@Time 16:26
    */
    @ApiOperation(value = "查询等级会员金额记录", notes = "查询等级会员金额记录")
    @RequestMapping(value = "/selectMemberAmountRecordByCondition", method = RequestMethod.POST)
    public ResponseResult selectMemberAmountRecordByCondition(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                              @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                              MMemberamountrecord memberamountrecord, String access_token) {
        return this.memberService.selectMemberAmountRecordByCondition(pageNum,pageSize,memberamountrecord);
    }

    /**
     * @Description 显示所有等级
     * @Param [pageNum, pageSize]
     * @Return com.lnmj.account.common.response.ResponseResult<com.lnmj.account.entity.MMembershipLevel>
     * @Author yilihua
     * @Date 2019/4/26
     * @Time 17:21
     */
    @PreAuthorize("hasAuthority('sys:menu:show')")
    @ApiOperation(value = "显示所有等级", notes = "显示所有等级")
    @RequestMapping(value = "/selectAllMemberList", method = RequestMethod.POST)
    public ResponseResult<MMembershipLevel> selectAllMemberList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize, String access_token) {
        return this.memberService.selectAllMemberList(pageNum, pageSize);
    }

    /**
     * @Description 根据条件查询等级
     * @Param [pageNum, pageSize,mMembershipLevel]
     * @Return com.lnmj.account.common.response.ResponseResult
     * @Author yilihua
     * @Date 2019/4/22
     * @Time 9:35
     */
    @PreAuthorize("hasAuthority('sys:menu:show')")
    @ApiOperation(value = "根据条件查询等级", notes = "根据条件查询等级")
    @RequestMapping(value = "/selectMemberShipLevelList", method = RequestMethod.POST)
    public ResponseResult<MMembershipLevel> selectMemberShipLevelList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                                      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                                                      MMembershipLevel mMembershipLevel, String access_token) {
        //判断参数
        //判断字段erpname是否合法
//        String erpName = mMembershipLevel.getErpName();
//        if (!StringUtils.isBlank(erpName)) {
//            Map<Integer, String> enumMap = MemberUtil.getEnumToMap(ErpNameEnum.class);
//            Boolean flag = false;
//            for (Map.Entry entry : enumMap.entrySet()) {
//                if (erpName.equals(entry.getValue())) {
//                    flag = true;
//                }
//            }
//            if (!flag) {
//                return ResponseResult.error(new Error(ResponseCodeAccountEnum.ERP_NAME_WRONGFUL.getCode(),
//                        ResponseCodeAccountEnum.ERP_NAME_WRONGFUL.getDesc() + ":" + erpName));
//            }
//        }
        //判断upgradingways是否合法
        String upgradingWays = mMembershipLevel.getUpgradingWays();
        if (!StringUtils.isBlank(upgradingWays)) {
            String[] split = upgradingWays.trim().replace("[", "").replace("]", "").
                    replace("{", "").replace("}", "").split(",");
            Map<Integer, String> enumMap1 = MemberUtil.getEnumToMap(UpgradingWaysEnum.class);
            Boolean flag = false;
            for (String s : split) {
                boolean b = enumMap1.containsKey(Integer.valueOf(s));
                if(!b){
                    flag = b;
                }
            }
            if(flag){
                return ResponseResult.error(new Error(ResponseCodeAccountEnum.UPGRADING_WAYS_WRONGFUL.getCode(),
                        ResponseCodeAccountEnum.UPGRADING_WAYS_WRONGFUL.getDesc() + ":" + upgradingWays));
            }
        }
        return this.memberService.selectMemberShipLevelList(pageNum, pageSize, mMembershipLevel);
    }

    /**
     * @Description 用户一次性充值升级
     * @Param [request]
     * @Return com.lnmj.core.common.data.ResponseResult
     * @Author yilihua
     * @Date 2019/4/22
     * @Time 9:38
     */
    @PreAuthorize("hasAuthority('sys:menu:update')")
    @ApiOperation(value = "用户一次性充值升级", notes = "用户一次性充值升级")
    @RequestMapping(value = "/userRechargeUpgrade", method = RequestMethod.POST)
    public ResponseResult userRechargeUpgrade(WalletRechargeRecordRequestVO walletRechargeRecordVO, String access_token) {
        //充值对象：
        // memberNum;//用户id
        // rechargeAmount;//充值总金额
        if (StringUtils.isBlank(walletRechargeRecordVO.getMemberNum())) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.USERID_NULL.getCode(), ResponseCodeAccountEnum.USERID_NULL.getDesc()));
        }
        if (walletRechargeRecordVO.getRechargeAmount() == null) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.RECHARGE_AMOUNT_NULL.getCode(), ResponseCodeAccountEnum.RECHARGE_AMOUNT_NULL.getDesc()));
        }
        if (walletRechargeRecordVO.getRechargeAmount().compareTo(BigDecimal.ZERO) < 0) { //充值总金额小于0
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.RECHARGE_AMOUNT_LESS_ZERO.getCode(), ResponseCodeAccountEnum.RECHARGE_AMOUNT_LESS_ZERO.getDesc()));
        }
        return this.memberService.userRechargeUpgrade(walletRechargeRecordVO);
    }
    /**
    *@Description 用户累计充值升级
    *@Param [walletRechargeRecordVO, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/6/3
    *@Time 15:38
    */
    @ApiOperation(value = "用户累计充值升级", notes = "用户累计充值升级")
    @RequestMapping(value = "/userRechargeUpgradeAdd", method = RequestMethod.POST)
    public ResponseResult userRechargeUpgradeAdd(WalletRechargeRecordRequestVO walletRechargeRecordVO, String access_token) {
        //充值对象：
        // memberNum;//用户id
        // rechargeAmount;//充值总金额
        if (StringUtils.isBlank(walletRechargeRecordVO.getMemberNum())) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.USERID_NULL.getCode(), ResponseCodeAccountEnum.USERID_NULL.getDesc()));
        }
        if (walletRechargeRecordVO.getRechargeAmount() == null) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.RECHARGE_AMOUNT_NULL.getCode(), ResponseCodeAccountEnum.RECHARGE_AMOUNT_NULL.getDesc()));
        }
        if (walletRechargeRecordVO.getRechargeAmount().compareTo(BigDecimal.ZERO) < 0) { //充值总金额小于0
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.RECHARGE_AMOUNT_LESS_ZERO.getCode(), ResponseCodeAccountEnum.RECHARGE_AMOUNT_LESS_ZERO.getDesc()));
        }
        return this.memberService.userRechargeUpgradeAdd(walletRechargeRecordVO);
    }

    /**
     *@Description 用户累计消费升级
     *@Param [walletRechargeRecordVO, access_token]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/6/3
     *@Time 15:38
     */
    @ApiOperation(value = "用户累计消费升级", notes = "用户累计消费升级")
    @RequestMapping(value = "/consumptionUpgrade", method = RequestMethod.POST)
    public ResponseResult consumptionUpgrade(WalletRechargeRecordRequestVO walletRechargeRecordVO, String access_token) {
        //充值对象：
        // memberNum;//用户id
        // rechargeAmount;//充值总金额
        if (StringUtils.isBlank(walletRechargeRecordVO.getMemberNum())) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.USERID_NULL.getCode(), ResponseCodeAccountEnum.USERID_NULL.getDesc()));
        }
        if (walletRechargeRecordVO.getRechargeAmount() == null) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.RECHARGE_AMOUNT_NULL.getCode(), ResponseCodeAccountEnum.RECHARGE_AMOUNT_NULL.getDesc()));
        }
        if (walletRechargeRecordVO.getRechargeAmount().compareTo(BigDecimal.ZERO) < 0) { //充值总金额小于0
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.RECHARGE_AMOUNT_LESS_ZERO.getCode(), ResponseCodeAccountEnum.RECHARGE_AMOUNT_LESS_ZERO.getDesc()));
        }
        return this.memberService.consumptionUpgrade(walletRechargeRecordVO);
    }

    /**
    *@Description 粉丝升级
    *@Param [memberNum, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/8/16
    *@Time 15:51
    */
    @ApiOperation(value = "粉丝升级", notes = "粉丝升级")
    @RequestMapping(value = "/fansUpgrade", method = RequestMethod.POST)
    public ResponseResult fansUpgrade(String memberNum, String access_token) {
        if (StringUtils.isBlank(memberNum)) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.USERID_NULL.getCode(), ResponseCodeAccountEnum.USERID_NULL.getDesc()));
        }
        return memberService.fansUpgrade(memberNum);
    }
    /**
    *@Description 退款降级(消费的退款)
    *@Param [walletRechargeRecordVO, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/8/16
    *@Time 16:30
    */
    @ApiOperation(value = "退款降级", notes = "退款降级")
    @RequestMapping(value = "/returnLevel", method = RequestMethod.POST)
    public ResponseResult returnLevel(WalletRechargeRecordRequestVO walletRechargeRecordVO, String access_token) {
        if (StringUtils.isBlank(walletRechargeRecordVO.getMemberNum())) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.USERID_NULL.getCode(), ResponseCodeAccountEnum.USERID_NULL.getDesc()));
        }
        if (walletRechargeRecordVO.getRechargeAmount() == null) {
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.RECHARGE_AMOUNT_NULL.getCode(), ResponseCodeAccountEnum.RECHARGE_AMOUNT_NULL.getDesc()));
        }
        if (walletRechargeRecordVO.getRechargeAmount().compareTo(BigDecimal.ZERO) < 0) { //退款总金额小于0
            return ResponseResult.error(new Error(ResponseCodeAccountEnum.RECHARGE_AMOUNT_LESS_ZERO.getCode(), ResponseCodeAccountEnum.RECHARGE_AMOUNT_LESS_ZERO.getDesc()));
        }
        return memberService.returnLevel(walletRechargeRecordVO);
    }


}
