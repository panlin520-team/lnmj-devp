package com.lnmj.store.repository.impl;

import com.lnmj.common.baseDao.impl.BaseDao;
import com.lnmj.store.entity.*;
import com.lnmj.store.repository.IBeauticianDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 〈〉
 *
 * @Author panlin
 * @create 2019/5/28
 */

@Repository
public class BeauticianDaoImpl extends BaseDao implements IBeauticianDao {
    @Override
    public List<Beautician> selectBeauticiantList(Beautician beautician) {
        return super.selectList("beautician.selectBeauticiantList",beautician);
    }

    @Override
    public List<Beautician> selectBeauticianByStoreId(Map map) {
        return selectList("beautician.selectBeauticianByStoreId",map);
    }

    @Override
    public List<Beautician> selectBeauticianByStoreIdArray(Map mapList) {
        return selectList("beautician.selectBeauticianByStoreIdArray",mapList);
    }

    @Override
    public Beautician selectBeauticianById(Long beauticianId) {
        return super.select("beautician.selectBeauticianById",beauticianId);
    }

    @Override
    public Beautician selectBeauticianByCode(String staffNumber) {
        return super.select("beautician.selectBeauticianByCode",staffNumber);
    }

    @Override
    public int insertBeautician(Beautician beautician) {
        return super.insert("beautician.insertBeautician",beautician);
    }

    @Override
    public int updateBeautician(Beautician beautician) {
        return super.update("beautician.updateBeautician",beautician);
    }

    @Override
    public int deleteBeautician(Map map) {
        return super.update("beautician.deleteBeautician",map);
    }

    @Override
    public int insertProjectToBeautician(BeauticianProject beauticianProject) {
        return super.insert("beauticianProject.insertProjectToBeautician",beauticianProject);
    }

    @Override
    public BeauticianProject selectProject(Map map) {
        return super.select("beauticianProject.selectProject",map);
    }

    @Override
    public int checkProduct(Map map) {
        return super.select("beauticianProject.checkProduct",map);
    }

    @Override
    public int deleteProject(Map map) {
        return super.delete("beauticianProject.deleteProject",map);
    }

    @Override
    public List<Group> selectGroup(Group group) {
        return super.selectList("beautician.selectGroup",group);
    }

    @Override
    public int addGroup(Group group) {
        return super.insert("beautician.addGroup",group);
    }

    @Override
    public List<Post> selectPost(Map map) {
        return super.selectList("beautician.selectPost",map);
    }

    @Override
    public List<Long> selectPostByCategoryId(Long postCategoryId) {
        return super.selectList("beautician.selectPostByCategoryId",postCategoryId);
    }

    @Override
    public int addPost(Post post) {
        return super.insert("beautician.addPost",post);
    }

    @Override
    public int deletePost(Long postId) {
        return super.delete("beautician.deletePost",postId);
    }

    @Override
    public int updatePost(Post post) {
        return super.update("beautician.updatePost",post);
    }

    @Override
    public int updateGroup(Group group) {
        return super.update("beautician.updateGroup",group);
    }

    @Override
    public int checkMobile(Beautician beautician) {
        return super.select("beautician.checkMobile",beautician);
    }

    @Override
    public int checkPostUse(Long postId) {
        return super.select("beautician.checkPostUse",postId);
    }

    @Override
    public int checkGroupUse(Long groupId) {
        return super.select("beautician.checkGroupUse",groupId);
    }

    @Override
    public int deleteGroup(Long groupId) {
        return super.delete("beautician.deleteGroup",groupId);
    }

    @Override
    public Post selectPostById(Long postId) {
        return super.select("beautician.selectPostById",postId);
    }

    @Override
    public int addPostLevel(PostLevel postLevel) {
        return super.insert("beautician.addPostLevel",postLevel);
    }

    @Override
    public int updatePostLevel(PostLevel postLevel) {
        return super.update("beautician.updatePostLevel",postLevel);
    }

    @Override
    public int deletePostLevel(PostLevel postLevel) {
        return super.delete("beautician.deletePostLevel",postLevel);
    }

    @Override
    public List<PostLevel> selectPostLevel(Map map) {
        return super.selectList("beautician.selectPostLevel",map);
    }

    @Override
    public List<Beautician> selectBeauticianByStoreIdAndPostId(Map map) {
        return super.selectList("beautician.selectBeauticianByStoreIdAndPostId",map);
    }

    @Override
    public List<PostCategory> selectPostCategoryList(Map map) {
        return super.selectList("beautician.selectPostCategoryList",map);
    }

    @Override
    public int insertPostCategory(PostCategory postCategory) {
        return super.insert("beautician.insertPostCategory",postCategory);
    }

    @Override
    public int updatePostCategory(PostCategory postCategory) {
        return super.update("beautician.updatePostCategory",postCategory);
    }

    @Override
    public int deletePostCategory(PostCategory postCategory) {
        return super.delete("beautician.deletePostCategory",postCategory);
    }

    @Override
    public int checkGroupName(Group group) {
        return super.select("beautician.checkGroupName",group);
    }

    @Override
    public int checkStaffName(Beautician beautician) {
        return super.select("beautician.checkStaffName",beautician);
    }

    @Override
    public List<Beautician> selectGroupMember(Beautician beautician) {
        return super.selectList("beautician.selectGroupMember",beautician);
    }


}
