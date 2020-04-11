package com.lnmj.system.business.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeMakerEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.system.business.IMakerUserService;
import com.lnmj.system.entity.MakerUser;
import com.lnmj.system.repository.IMakerUserDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/8/26 10:04
 * @Description: 创客用户
 */
@Service("makerUserService")
public class MakerUserService implements IMakerUserService {
    @Resource
    IMakerUserDao makerUserDao;

    @Override
    public ResponseResult deleteMakerUser(Long makerUserId, String modifyOperator) {
        MakerUser makerUser = new MakerUser();
        makerUser.setMakerUserId(makerUserId);
        makerUser.setModifyOperator(modifyOperator);
        return ResponseResult.success(makerUserDao.deleteMakerUser(makerUser));
    }

    @Override
    public ResponseResult updateMakerUser(MakerUser makerUser) {
        makerUserDao.updateMakerUser(makerUser);
        return ResponseResult.success(makerUser);
    }

    @Override
    public ResponseResult insertMakerUser(MakerUser makerUser) {
        makerUserDao.insertMakerUser(makerUser);
        return ResponseResult.success(makerUser);
    }

    @Override
    public ResponseResult selectMakerUserByCondition(int pageNum, int pageSize, MakerUser makerUser) {
        PageHelper.startPage(pageNum,pageSize);
        List<MakerUser> makerUserList = makerUserDao.selectMakerUserByCondition(makerUser);
        if(makerUserList.size()>0){
            PageInfo<MakerUser> pageInfo = new PageInfo<>(makerUserList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeMakerEnum.MAKER_USER_NULL.getCode(),
                ResponseCodeMakerEnum.MAKER_USER_NULL.getDesc()));
    }

    @Override
    public ResponseResult selectMakerUserList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<MakerUser> makerUserList = makerUserDao.selectMakerUserList();
        if(makerUserList.size()>0){
            PageInfo<MakerUser> pageInfo = new PageInfo<>(makerUserList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeMakerEnum.MAKER_USER_NULL.getCode(),
                ResponseCodeMakerEnum.MAKER_USER_NULL.getDesc()));
    }
}
