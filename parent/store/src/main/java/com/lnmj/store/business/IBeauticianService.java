package com.lnmj.store.business;

import com.alibaba.fastjson.JSONArray;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.store.entity.*;
import org.springframework.stereotype.Service;

@Service("beauticianService")
public interface IBeauticianService {
    ResponseResult selectBeauticianList(int pageNum, int pageSize,String storeId,String keyWordStaffNumber,String keyWordName,String nursingDate,Long companyType,Long companyId,Integer isSaleMan);
    ResponseResult selectBeauticianListNoPage2(String storeId,String keyWordStaffNumber,String keyWordName,String nursingDate,Long companyType,Long companyId,Integer isSaleMan);
    ResponseResult selectBeauticianListNoPage(String companyType, String companyId, Integer isSaleMan);
    ResponseResult selectBeauticianById(Long beauticianId);
    ResponseResult selectBeauticianByCode(String staffNumber);
    ResponseResult insertBeautician(String headUrl, Beautician beautician, String FCreateOrgId, String FUseOrgId, String companyType, String companyId);
    ResponseResult updateBeautician(Beautician beautician);
    ResponseResult deleteBeautician(String beauticianIds,String modifyOperator);
    ResponseResult insertProjectToBeautician(Long postId,Integer postLevel,JSONArray projectsArray);
    ResponseResult selectProject(int pageNum,int pageSize,Long postId,Integer postLevel,String keyWordProductCode,String keyWordProductName);
    ResponseResult selectProjectNoPage(Long postId,Integer postLevel,String keyWordProductCode,String keyWordProductName);
    ResponseResult selectGroup(int pageNum, int pageSize,Long storeId,String keyWord);
    ResponseResult addGroup(Group group);
    ResponseResult deleteGroup(Long groupId);
    ResponseResult selectPost(int pageNum, int pageSize,String postIndustryIDSearch,String postCategoryId,Long companyId,Long companyType,String postNameKeyword,String departmentId);
    ResponseResult selectPostCategoryNoPage(String postIndustryIDSearch);
    ResponseResult addPost(Post post, String fCreateOrgId, String fUseOrgId);
    ResponseResult deletePost(Long postId);
    ResponseResult updatePost(Post post, String fCreateOrgId, String fUseOrgId);
    ResponseResult updateGroup(Group group);
    ResponseResult selectBeauticianByStoreId(String companyId,String companyType);
    ResponseResult selectBeauticianByStoreIdArray(String storeId);
    ResponseResult selectPostById(Long postId);
    ResponseResult selectPostLevel(int pageNum, int pageSize,String postLevelNameKeyword,Long companyId);
    ResponseResult addPostLevel(PostLevel postLevel);
    ResponseResult updatePostLevel(PostLevel postLevel);
    ResponseResult deletePostLevel(PostLevel postLevel);
    ResponseResult selectPostLevelNoPage();
    ResponseResult selectBeauticianByStoreIdAndPostId(Long storeId, Long postId);
    ResponseResult selectPostCategoryList(int pageNum, int pageSize, String postCategoryNameKeyword, String postIndustryIDSearch, Long companyType, Long companyId);
    ResponseResult selectPostNoPage(String postIndustryIDSearch, String postCategoryId, String companyType, String companyId, String departmentId);
    ResponseResult insertPostCategory(PostCategory postCategory);
    ResponseResult updatePostCategory(PostCategory postCategory);
    ResponseResult deletePostCategory(PostCategory postCategory);
    ResponseResult selectGroupMember(Beautician beautician);
}
