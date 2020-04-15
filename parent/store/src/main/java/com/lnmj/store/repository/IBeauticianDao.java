package com.lnmj.store.repository;

import com.lnmj.store.entity.*;

import java.util.List;
import java.util.Map;


public interface IBeauticianDao {
    List<Beautician> selectBeauticiantList(Beautician beautician);

    List<Beautician> selectBeauticianByStoreId(Map map);

    List<Beautician> selectBeauticianByStoreIdArray(Map mapList);

    Beautician selectBeauticianById(Long beauticianId);

    Beautician selectBeauticianByCode(String staffNumber);

    int insertBeautician(Beautician beautician);

    int updateBeautician(Beautician beautician);

    int deleteBeautician(Map map);

    int insertProjectToBeautician(BeauticianProject beauticianProject);

    BeauticianProject selectProject(Map map);

    int checkProduct(Map map);

    int deleteProject(Map map);

    List<Group> selectGroup(Group group);

    int addGroup(Group group);

    List<Post> selectPost(Map map);

    List<Long> selectPostByCategoryId(Long postCategoryId);

    int addPost(Post post);

    int deletePost(Long postId);

    int updatePost(Post post);

    int updateGroup(Group group);

    int checkMobile(Beautician beautician);

    int checkPostUse(Long postId);

    int checkGroupUse(Long postId);

    int deleteGroup(Long groupId);

    Post selectPostById(Long postId);

    int  addPostLevel(PostLevel postLevel);

    int  updatePostLevel(PostLevel postLevel);

    int  deletePostLevel(PostLevel postLevel);

    List<PostLevel>  selectPostLevel(Map map);

    List<Beautician>  selectBeauticianByStoreIdAndPostId(Map map);

    List<PostCategory> selectPostCategoryList(Map map);

    int insertPostCategory(PostCategory postCategory);

    int updatePostCategory(PostCategory postCategory);

    int deletePostCategory(PostCategory postCategory);

    int checkGroupName(Group group);

    int checkGroupLeader(Group group);

    int checkStaffName(Beautician beautician);

    List<Beautician> selectGroupMember(Beautician beautician);
}