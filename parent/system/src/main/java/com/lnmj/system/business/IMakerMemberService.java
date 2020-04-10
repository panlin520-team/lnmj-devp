package com.lnmj.system.business;

import com.lnmj.common.response.ResponseResult;
import com.lnmj.system.entity.MakerMemberLevel;
import org.springframework.stereotype.Service;

/**
 * @Author: yilihua
 * @Date: 2019/8/24 15:50
 * @Description: 创客等级
 */
@Service("iMakerMemberService")
public interface IMakerMemberService {

    ResponseResult selectMakerMemberList(int pageNum, int pageSize);

    ResponseResult selectMakerMemberByCondition(int pageNum, int pageSize, MakerMemberLevel makerMemberLevel);

    ResponseResult insertMakerMember(MakerMemberLevel makerMemberLevel);

    ResponseResult updateMakerMember(MakerMemberLevel makerMemberLevel);

    ResponseResult deleteMakerMember(Long makerMemberLevelId, String modifyOperator);
}
