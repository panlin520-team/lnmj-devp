package com.lnmj.system.repository;

import com.lnmj.system.entity.MakerMemberLevel;

import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/8/24 15:57
 * @Description:  创客等级
 */
public interface IMakerMemberDao {

    List<MakerMemberLevel> selectMakerMemberList();

    List<MakerMemberLevel> selectMakerMemberByCondition(MakerMemberLevel makerMemberLevel);

    int insertMakerMember(MakerMemberLevel makerMemberLevel);

    int updateMakerMember(MakerMemberLevel makerMemberLevel);

    int deleteMakerMember(MakerMemberLevel makerMemberLevel);
}
