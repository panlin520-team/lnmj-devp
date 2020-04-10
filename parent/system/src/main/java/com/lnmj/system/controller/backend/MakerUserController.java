package com.lnmj.system.controller.backend;


import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeMakerEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.system.business.IMakerUserService;
import com.lnmj.system.entity.MakerUser;
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
 * @Description:  创客用户
 */
@Api(description = "创客用户")
@RestController
@RequestMapping("/manage/maker/user")
public class MakerUserController {
    @Resource
    private IMakerUserService makerUserService;


    /**
     *@Description 条件查询创客用户
     *@Param [pageNum, pageSize, access_token]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/8/26
     *@Time 9:48
     */
    @ApiOperation(value = "条件查询创客用户",notes = "条件查询创客用户")
    @RequestMapping(value = "/selectMakerUserByCondition",method = RequestMethod.POST)
    public ResponseResult selectMakerUserByCondition(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                      @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,
                                                      MakerUser makerUser, String access_token) {
        return makerUserService.selectMakerUserByCondition(pageNum,pageSize,makerUser);
    }


    /**
     *@Description 修改创客用户
     *@Param [makerUser, access_token]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/8/26
     *@Time 9:55
     */
    @ApiOperation(value = "修改创客用户",notes = "修改创客用户")
    @RequestMapping(value = "/updateMakerUser",method = RequestMethod.POST)
    public ResponseResult updateMakerUser(MakerUser makerUser, String access_token) {
        if(makerUser.getMakerUserId()==null){
            return ResponseResult.error(new Error(ResponseCodeMakerEnum.MAKER_USER_ID_NULL.getCode(),
                    ResponseCodeMakerEnum.MAKER_USER_ID_NULL.getDesc()));
        }
        if(StringUtils.isBlank(makerUser.getModifyOperator())){
            return ResponseResult.error(new Error(ResponseCodeMakerEnum.NOT_UPDATE_OPPERATOR.getCode(),
                    ResponseCodeMakerEnum.NOT_UPDATE_OPPERATOR.getDesc()));
        }
        return makerUserService.updateMakerUser(makerUser);
    }

    /**
     *@Description 新增创客用户
     *@Param [makerUser, access_token]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/8/26
     *@Time 9:55
     */
    /*@ApiOperation(value = "新增创客用户",notes = "条件查询创客用户")
    @RequestMapping(value = "/insertMakerUser",method = RequestMethod.POST)
    public ResponseResult insertMakerUser(MakerUser makerUser, String access_token) {
        return makerUserService.insertMakerUser(makerUser);
    }*/

    /**
     *@Description 删除创客用户
     *@Param [makerUserId, modifyOperator, access_token]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/8/26
     *@Time 9:57
     */
    /*@ApiOperation(value = "删除创客用户",notes = "删除创客用户")
    @RequestMapping(value = "/deleteMakerUser",method = RequestMethod.POST)
    public ResponseResult deleteMakerUser(Long makerUserId,String modifyOperator, String access_token) {
        if(makerUserId==null){
            return ResponseResult.error(new Error(ResponseCodeMakerEnum.MAKER_USER_ID_NULL.getCode(),
                    ResponseCodeMakerEnum.MAKER_USER_ID_NULL.getDesc()));
        }
        if(StringUtils.isBlank(modifyOperator)){
            return ResponseResult.error(new Error(ResponseCodeMakerEnum.NOT_UPDATE_OPPERATOR.getCode(),
                    ResponseCodeMakerEnum.NOT_UPDATE_OPPERATOR.getDesc()));
        }
        return makerUserService.deleteMakerUser(makerUserId,modifyOperator);
    }*/

    /**
     *@Description 查询创客用户
     *@Param [pageNum, pageSize, access_token]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/8/26
     *@Time 9:48
     */
    /*@ApiOperation(value = "查询创客用户",notes = "查询创客用户")
    @RequestMapping(value = "/selectMakerUserList",method = RequestMethod.POST)
    public ResponseResult selectMakerUserList(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                               @RequestParam(value = "pageSize",defaultValue = "10")int pageSize, String access_token) {
        return makerUserService.selectMakerUserList(pageNum,pageSize);
    }*/

}
