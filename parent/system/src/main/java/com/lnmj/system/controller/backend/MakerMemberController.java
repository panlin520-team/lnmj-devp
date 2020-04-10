package com.lnmj.system.controller.backend;


import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeMakerEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.system.business.IMakerMemberService;
import com.lnmj.system.entity.MakerMemberLevel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: yilihua
 * @Date: 2019/8/24 15:24
 * @Description:  创客等级
 */
@Api(description = "创客等级")
@RestController
@RequestMapping("/manage/maker/member")
public class MakerMemberController {
    @Resource
    private IMakerMemberService makerMemberService;

    /**
    *@Description 创客等级分页显示
    *@Param [pageNum, pageSize, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/8/24
    *@Time 15:38
    */
    @ApiOperation(value = "创客等级分页显示",notes = "创客等级分页显示")
    @RequestMapping(value = "/selectMakerMemberList",method = RequestMethod.POST)
    public ResponseResult selectMakerMemberList(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                               @RequestParam(value = "pageSize",defaultValue = "10")int pageSize, String access_token) {
        return makerMemberService.selectMakerMemberList(pageNum,pageSize);
    }

    /**
    *@Description 创客等级查询
    *@Param [pageNum, pageSize, makerMemberLevel, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/8/24
    *@Time 15:49
    */
    @ApiOperation(value = "创客等级查询",notes = "创客等级查询")
    @RequestMapping(value = "/selectMakerMemberByCondition",method = RequestMethod.POST)
    public ResponseResult selectMakerMemberByCondition(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                @RequestParam(value = "pageSize",defaultValue = "10")int pageSize
                                                       ,MakerMemberLevel makerMemberLevel,String access_token) {
        return makerMemberService.selectMakerMemberByCondition(pageNum,pageSize,makerMemberLevel);
    }

    /**
    *@Description 新增创客等级
    *@Param [makerMemberLevel, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/8/24
    *@Time 15:38
    */
    @ApiOperation(value = "新增创客等级",notes = "新增创客等级")
    @RequestMapping(value = "/insertMakerMember",method = RequestMethod.POST)
    public ResponseResult insertMakerMember(MakerMemberLevel makerMemberLevel, String access_token) {
        return makerMemberService.insertMakerMember(makerMemberLevel);
    }

    /**
    *@Description 修改创客等级
    *@Param [makerMemberLevel, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/8/24
    *@Time 15:39
    */
    @ApiOperation(value = "修改创客等级",notes = "修改创客等级")
    @RequestMapping(value = "/updateMakerMember",method = RequestMethod.POST)
    public ResponseResult updateMakerMember(MakerMemberLevel makerMemberLevel, String access_token) {
        if (makerMemberLevel.getMakerMemberLevelId() == null) {
            return ResponseResult.error(new Error(ResponseCodeMakerEnum.MAKER_MEMBER_ID_NULL.getCode(),
                    ResponseCodeMakerEnum.MAKER_MEMBER_ID_NULL.getDesc()));
        }
        if(StringUtils.isBlank(makerMemberLevel.getModifyOperator())){
            return ResponseResult.error(new Error(ResponseCodeMakerEnum.NOT_UPDATE_OPPERATOR.getCode(),
                    ResponseCodeMakerEnum.NOT_UPDATE_OPPERATOR.getDesc()));
        }
        return makerMemberService.updateMakerMember(makerMemberLevel);
    }
    /**
    *@Description 删除创客等级
    *@Param [makerMemberLevelId, modifyOperator, access_token]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/8/24
    *@Time 15:48
    */
    @ApiOperation(value = "删除创客等级",notes = "删除创客等级")
    @RequestMapping(value = "/deleteMakerMember",method = RequestMethod.POST)
    public ResponseResult deleteMakerMember(Long makerMemberLevelId,String modifyOperator, String access_token) {
        if (makerMemberLevelId == null) {
            return ResponseResult.error(new Error(ResponseCodeMakerEnum.MAKER_MEMBER_ID_NULL.getCode(),
                    ResponseCodeMakerEnum.MAKER_MEMBER_ID_NULL.getDesc()));
        }
        if(StringUtils.isBlank(modifyOperator)){
            return ResponseResult.error(new Error(ResponseCodeMakerEnum.NOT_UPDATE_OPPERATOR.getCode(),
                    ResponseCodeMakerEnum.NOT_UPDATE_OPPERATOR.getDesc()));
        }
        return makerMemberService.deleteMakerMember(makerMemberLevelId,modifyOperator);
    }

}
