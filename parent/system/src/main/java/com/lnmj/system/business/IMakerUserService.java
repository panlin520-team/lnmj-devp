package com.lnmj.system.business;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.system.entity.MakerUser;
import org.springframework.stereotype.Service;

/**
 * @Author: yilihua
 * @Date: 2019/8/26 10:00
 * @Description: 创客用户
 */
@Service("iMakerUserService")
public interface IMakerUserService {

    ResponseResult deleteMakerUser(Long makerUserId, String modifyOperator);

    ResponseResult updateMakerUser(MakerUser makerUser);

    ResponseResult insertMakerUser(MakerUser makerUser);

    ResponseResult selectMakerUserByCondition(int pageNum, int pageSize, MakerUser makerUser);

    ResponseResult selectMakerUserList(int pageNum, int pageSize);
}
