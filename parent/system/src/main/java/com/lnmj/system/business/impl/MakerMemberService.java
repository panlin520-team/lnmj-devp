package com.lnmj.system.business.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeMakerEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.system.business.IMakerMemberService;
import com.lnmj.system.entity.MakerMemberLevel;
import com.lnmj.system.repository.IMakerMemberDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/8/24 15:52
 * @Description:  创客等级
 */
@Service("makerMemberService")
public class MakerMemberService implements IMakerMemberService {

    @Resource
    private IMakerMemberDao makerMemberDao;

    @Override
    public ResponseResult selectMakerMemberList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<MakerMemberLevel> memberLevelList = makerMemberDao.selectMakerMemberList();
        if(memberLevelList.size()>0){
            PageInfo<MakerMemberLevel> pageInfo = new PageInfo<>(memberLevelList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeMakerEnum.MAKER_MEMBER_NULL.getCode(),
                ResponseCodeMakerEnum.MAKER_MEMBER_NULL.getDesc()));
    }

    @Override
    public ResponseResult selectMakerMemberByCondition(int pageNum, int pageSize, MakerMemberLevel makerMemberLevel) {
        PageHelper.startPage(pageNum,pageSize);
        List<MakerMemberLevel> memberLevelList = makerMemberDao.selectMakerMemberByCondition(makerMemberLevel);
        if(memberLevelList.size()>0){
            PageInfo<MakerMemberLevel> pageInfo = new PageInfo<>(memberLevelList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeMakerEnum.MAKER_MEMBER_NULL.getCode(),
                ResponseCodeMakerEnum.MAKER_MEMBER_NULL.getDesc()));
    }

    @Override
    public ResponseResult insertMakerMember(MakerMemberLevel makerMemberLevel) {
        makerMemberDao.insertMakerMember(makerMemberLevel);
        return ResponseResult.success(makerMemberLevel);
    }

    @Override
    public ResponseResult updateMakerMember(MakerMemberLevel makerMemberLevel) {
        makerMemberDao.updateMakerMember(makerMemberLevel);
        return ResponseResult.success(makerMemberLevel);
    }

    @Override
    public ResponseResult deleteMakerMember(Long makerMemberLevelId, String modifyOperator) {
        MakerMemberLevel makerMemberLevel = new MakerMemberLevel();
        makerMemberLevel.setMakerMemberLevelId(makerMemberLevelId);
        makerMemberLevel.setModifyOperator(modifyOperator);
        return ResponseResult.success(makerMemberDao.deleteMakerMember(makerMemberLevel));
    }
}
