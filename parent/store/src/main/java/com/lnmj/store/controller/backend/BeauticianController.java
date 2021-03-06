package com.lnmj.store.controller.backend;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.lnmj.common.baseController.FtpFileUploadController;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.store.business.IBeauticianService;
import com.lnmj.store.entity.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @Auther: panlin
 * @Date: 2019/5/28 11:32
 * @Description:
 */
@Api(description = "美容师")
@RestController
@RequestMapping("/manage/beautician")
public class BeauticianController {
    @Resource(name = "beauticianService")
    private IBeauticianService beauticianService;
    @Resource
    private FtpFileUploadController ftpFileUploadController;



    /**
    /**
    *@Description 上传美容师头像图片
    *@Param [file]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/7/4
    *@Time 10:56
    */
    @ApiOperation(value = "上传美容师头像图片",notes = "上传美容师头像图片")
    @RequestMapping(value = "/uploadBeauticianImg",method = RequestMethod.POST)
    public ResponseResult uploadBeauticianImg(MultipartFile file) throws IOException {
        String imageType = "beauticianHeadType";
        ResponseResult responseResult = ftpFileUploadController.uploadImg(file,imageType);
        return  responseResult;

    }

    /**
     *@Description 根据门店Id查询员工
     *@Param [pageNum, pageSize, storeId, access_token]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author yilihua
     *@Date 2019/7/1
     *@Time 12:29
     */
    @ApiOperation(value = "根据门店Id查询员工",notes = "根据门店Id查询员工")
    @RequestMapping(value = "/selectBeauticianByStoreId",method = RequestMethod.POST)
    public ResponseResult selectBeauticianByStoreId(String companyId,String companyType) {
        //TODO 根据条件查询
        return beauticianService.selectBeauticianByStoreId( companyId, companyType);
    }

    /**
    *@Description 分页查看美容师列表
    *@Param [pageNum, pageSize] 页码数量 页码大小
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/5/28
    *@Time 13:39
    */
    @ApiOperation(value = "分页查看美容师列表",notes = "分页查看美容师列表")
    @RequestMapping(value = "/selectBeauticianList",method = RequestMethod.POST)
    public ResponseResult<List<Beautician>> selectBeauticianList(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                                 @RequestParam(value = "pageSize",defaultValue = "10")int pageSize, String storeId , String keyWordStaffNumber, String keyWordName, String nursingDate,Long companyType,Long companyId,Integer isSaleMan) {

        return beauticianService.selectBeauticianList(pageNum,pageSize,storeId,keyWordStaffNumber,keyWordName,nursingDate, companyType, companyId,isSaleMan);
    }

    /**
     *@Description 分页查看美容师列表
     *@Param [pageNum, pageSize] 页码数量 页码大小
     *@Return com.lnmj.common.response.ResponseResult
     *@Author panlin
     *@Date 2019/5/28
     *@Time 13:39
     */
    @ApiOperation(value = "分页查看美容师列表",notes = "分页查看美容师列表")
    @RequestMapping(value = "/selectBeauticianListNoPage2",method = RequestMethod.POST)
    public ResponseResult<List<Beautician>> selectBeauticianListNoPage2(String storeId , String keyWordStaffNumber, String keyWordName, String nursingDate,Long companyType,Long companyId,Integer isSaleMan) {

        return beauticianService.selectBeauticianListNoPage2(storeId,keyWordStaffNumber,keyWordName,nursingDate, companyType, companyId,isSaleMan);
    }

    @ApiOperation(value = "查看美容师列表",notes = "查看美容师列表")
    @RequestMapping(value = "/selectBeauticianListNoPage",method = RequestMethod.POST)
    public ResponseResult selectBeauticianListNoPage(String companyType,String companyId,Integer isSaleMan) {
        return beauticianService.selectBeauticianListNoPage(companyType,companyId,isSaleMan);
    }

    /**
    *@Description 查看美容师详情
    *@Param [beauticianId] 美容师id
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/5/28
    *@Time 13:48
    */
    @ApiOperation(value = "查看美容师详情",notes = "查看美容师详情" )
    @RequestMapping(value = "/selectBeauticianById",method = RequestMethod.POST)
    public ResponseResult selectBeauticianById(@RequestParam(value = "beauticianId") Long beauticianId) {
        return beauticianService.selectBeauticianById(beauticianId);
    }

    @ApiOperation(value = "查看美容师编号",notes = "查看美容师编号" )
    @RequestMapping(value = "/selectBeauticianByCode",method = RequestMethod.POST)
    public ResponseResult selectBeauticianByCode(@RequestParam(value = "staffNumber") String staffNumber) {
        return beauticianService.selectBeauticianByCode(staffNumber);
    }

    /**
    *@Description 添加美容师
    *@Param [beautician, bindingResult] 美容师实体，验证绑定
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/5/28
    *@Time 14:00
    */
    @ApiOperation(value = "添加美容师",notes = "添加美容师")
    @RequestMapping(value = "/insertBeautician",method = RequestMethod.POST)
    public ResponseResult insertBeautician(String headUrl, Beautician beautician,String FCreateOrgId,String FUseOrgId,String companyType,String companyId) {

        return beauticianService.insertBeautician(headUrl,beautician,FCreateOrgId,FUseOrgId,companyType,companyId);
    }

    /**
    *@Description 修改美容师
    *@Param [beautician, bindingResult] 美容师实体 绑定数据
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/5/29
    *@Time 17:55
    */
    @ApiOperation(value = "修改美容师",notes = "修改美容师")
    @RequestMapping(value = "/updateBeautician",method = RequestMethod.POST)
    public ResponseResult updateBeautician( Beautician beautician) {

        return beauticianService.updateBeautician(beautician);
    }

    /**
    *@Description 删除美容师
    *@Param [beauticianIds]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/5/30
    *@Time 11:14
    */
    @ApiOperation(value = "删除美容师",notes = "删除美容师")
    @RequestMapping(value = "/deleteBeautician",method = RequestMethod.POST)
    public ResponseResult deleteBeautician(String beauticianIds,String modifyOperator) {
        return beauticianService.deleteBeautician(beauticianIds,modifyOperator);
    }

    /**
    *@Description 授权美容师项目（包括修改）
    *@Param [beauticianId, projects] 美容师 项目ids
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/5/30
    *@Time 16:46
    */
    @ApiOperation(value = "授权美容师项目",notes = "授权美容师项目")
    @RequestMapping(value = "/insertProjectToBeautician",method = RequestMethod.POST)
    public ResponseResult insertProjectToBeautician(Long postId,Integer postLevel, String projects) {
        JSONArray projectsArray = JSON.parseArray(projects);
        return beauticianService.insertProjectToBeautician(postId,postLevel,projectsArray);
    }
    /**
    *@Description
    *@Param [beauticianId] 美容师id
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/5/30
    *@Time 16:47
    */
    @ApiOperation(value = "查看美容师拥有的项目",notes = "查看美容师拥有的项目")
    @RequestMapping(value = "/selectProject",method = RequestMethod.POST)
    public ResponseResult selectProject(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                        @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,Long postId,Integer postLevel,String keyWordProductCode,String keyWordProductName) {
        return beauticianService.selectProject( pageNum,pageSize,postId, postLevel,keyWordProductCode,keyWordProductName);
    }
    /**
     *@Description
     *@Param [beauticianId] 美容师id
     *@Return com.lnmj.common.response.ResponseResult
     *@Author panlin
     *@Date 2019/5/30
     *@Time 16:47
     */
    @ApiOperation(value = "查看美容师拥有的项目",notes = "查看美容师拥有的项目")
    @RequestMapping(value = "/selectProjectNoPage",method = RequestMethod.POST)
    public ResponseResult selectProjectNoPage(Long postId,Integer postLevel,String keyWordProductCode,String keyWordProductName) {
        return beauticianService.selectProjectNoPage(postId, postLevel,keyWordProductCode,keyWordProductName);
    }


    /**
    *@Description 查看美容师分组
    *@Param [beauticianId]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/7/2
    *@Time 16:11
    */
    @ApiOperation(value = "查看美容师分组",notes = "查看美容师分组")
    @RequestMapping(value = "/selectGroup",method = RequestMethod.POST)
    public ResponseResult selectGroup(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                      @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,Long storeId,String keyWord) {
        return beauticianService.selectGroup(pageNum,pageSize,storeId, keyWord);
    }

    /**
    *@Description 添加分组
    *@Param []
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/7/24
    *@Time 11:35
    */
    @ApiOperation(value = "添加分组",notes = "添加分组")
    @RequestMapping(value = "/addGroup",method = RequestMethod.POST)
    public ResponseResult addGroup(Group group) {
        return beauticianService.addGroup(group);
    }

    /**
    *@Description 删除分组
    *@Param [groupId]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/7/24
    *@Time 11:44
    */
    @ApiOperation(value = "删除分组",notes = "删除分组")
    @RequestMapping(value = "/deleteGroup",method = RequestMethod.POST)
    public ResponseResult deleteGroup(Long groupId) {
        return beauticianService.deleteGroup(groupId);
    }


    /**
    *@Description 修改分组
    *@Param [post]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/7/24
    *@Time 11:44
    */
    @ApiOperation(value = "修改分组",notes = "修改分组")
    @RequestMapping(value = "/updateGroup",method = RequestMethod.POST)
    public ResponseResult updateGroup(Group group) {
        return beauticianService.updateGroup(group);
    }

    /**
     *@Description 查看组员
     *@Param [post]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author panlin
     *@Date 2019/7/24
     *@Time 11:44
     */
    @ApiOperation(value = "查看组员",notes = "查看组员")
    @RequestMapping(value = "/selectGroupMember",method = RequestMethod.POST)
    public ResponseResult selectGroupMember(Beautician beautician) {
        return beauticianService.selectGroupMember(beautician);
    }

    /**
     *@Description 查看美容师职位
     *@Param []
     *@Return com.lnmj.common.response.ResponseResult
     *@Author panlin
     *@Date 2019/7/2
     *@Time 17:15
     */
    @ApiOperation(value = "查看美容师职位",notes = "查看美容师职位")
    @RequestMapping(value = "/selectPost",method = RequestMethod.POST)
    public ResponseResult selectPost(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                     @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,String postIndustryIDSearch,String postCategoryId,Long companyId,Long companyType,String postNameKeyword,String departmentId) {
        return beauticianService.selectPost(pageNum,pageSize,postIndustryIDSearch,postCategoryId,companyId,companyType,postNameKeyword,departmentId);
    }

    /**
     *@Description 查看美容师职位
     *@Param []
     *@Return com.lnmj.common.response.ResponseResult
     *@Author panlin
     *@Date 2019/7/2
     *@Time 17:15
     */
    @ApiOperation(value = "查看美容师职位",notes = "查看美容师职位")
    @RequestMapping(value = "/selectPostNoPage",method = RequestMethod.POST)
    public ResponseResult selectPostNoPage(String postIndustryIDSearch,String postCategoryId,String companyType,String companyId,String departmentId) {
        return beauticianService.selectPostNoPage(postIndustryIDSearch,postCategoryId,companyType,companyId,departmentId);
    }


    /**
    *@Description 根据ID查看职位
    *@Param [postId]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/9/9
    *@Time 10:24
    */
    @ApiOperation(value = "根据ID查看职位",notes = "根据ID查看职位")
    @RequestMapping(value = "/selectPostById",method = RequestMethod.POST)
    public ResponseResult selectPostById(Long postId) {
        return beauticianService.selectPostById(postId);
    }

    /**
    *@Description 添加职位
    *@Param [post]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/7/23
    *@Time 17:59
    */
    @ApiOperation(value = "添加职位",notes = "添加职位")
    @RequestMapping(value = "/addPost",method = RequestMethod.POST)
    public ResponseResult addPost(Post post,String fCreateOrgId,String fUseOrgId) {
        return beauticianService.addPost(post,fCreateOrgId,fUseOrgId);
    }

    /**
    *@Description 删除职位
    *@Param [postId]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/7/23
    *@Time 18:00
    */
    @ApiOperation(value = "删除职位",notes = "删除职位")
    @RequestMapping(value = "/deletePost",method = RequestMethod.POST)
    public ResponseResult deletePost(Long postId) {
        return beauticianService.deletePost(postId);
    }

    /**
    *@Description 修改职位
    *@Param [postId]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/7/24
    *@Time 10:30
    */
    @ApiOperation(value = "修改职位",notes = "修改职位")
    @RequestMapping(value = "/updatePost",method = RequestMethod.POST)
    public ResponseResult updatePost(Post post,String fCreateOrgId,String fUseOrgId) {
        return beauticianService.updatePost(post,fCreateOrgId,fUseOrgId);
    }


    /**
     *@Description 修改职位
     *@Param [postId]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author panlin
     *@Date 2019/7/24
     *@Time 10:30
     */
    @ApiOperation(value = "查看职位等级",notes = "查看职位等级")
    @RequestMapping(value = "/selectPostLevel",method = RequestMethod.POST)
    public ResponseResult selectPostLevel(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                          @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,String postLevelNameKeyword,Long companyId) {
        return beauticianService.selectPostLevel(pageNum,pageSize,postLevelNameKeyword,companyId);
    }



    @ApiOperation(value = "添加职位等级",notes = "添加职位等级")
    @RequestMapping(value = "/addPostLevel",method = RequestMethod.POST)
    public ResponseResult addPostLevel(PostLevel postLevel) {
        return beauticianService.addPostLevel(postLevel);
    }

    @ApiOperation(value = "修改职位等级",notes = "修改职位等级")
    @RequestMapping(value = "/updatePostLevel",method = RequestMethod.POST)
    public ResponseResult updatePostLevel(PostLevel postLevel) {
        return beauticianService.updatePostLevel(postLevel);
    }

    @ApiOperation(value = "修改职位等级",notes = "修改职位等级")
    @RequestMapping(value = "/deletePostLevel",method = RequestMethod.POST)
    public ResponseResult deletePostLevel(PostLevel postLevel) {
        return beauticianService.deletePostLevel(postLevel);
    }


    /**
     *@Description 修改职位
     *@Param [postId]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author panlin
     *@Date 2019/7/24
     *@Time 10:30
     */
    @ApiOperation(value = "查看职位等级",notes = "查看职位等级")
    @RequestMapping(value = "/selectPostLevelNoPage",method = RequestMethod.POST)
    public ResponseResult selectPostLevelNoPage() {
        return beauticianService.selectPostLevelNoPage();
    }


    /**
    *@Description 根据职位及门店查看美容师
    *@Param [storeId, postId]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/9/25
    *@Time 22:05
    */
    @ApiOperation(value = "根据职位及门店查看美容师",notes = "根据职位及门店查看美容师")
    @RequestMapping(value = "/selectBeauticianByStoreIdAndPostId",method = RequestMethod.POST)
    public ResponseResult selectBeauticianByStoreIdAndPostId(Long storeId,Long postId) {
        return beauticianService.selectBeauticianByStoreIdAndPostId(storeId,postId);
    }

    /**
    *@Description 查看所属职位分类
    *@Param [storeId, postId]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author panlin
    *@Date 2019/10/11
    *@Time 11:29
    */
    @ApiOperation(value = "查看员工职位分类",notes = "查看员工职位分类")
    @RequestMapping(value = "/selectPostCategoryList",method = RequestMethod.POST)
    public ResponseResult selectPostCategoryList(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                 @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,String postCategoryNameKeyword,String postIndustryIDSearch,Long companyType,Long companyId) {
        return beauticianService.selectPostCategoryList(pageNum,pageSize,postCategoryNameKeyword,postIndustryIDSearch,companyType,companyId);
    }




    /**
     *@Description 查看所属职位分类
     *@Param [storeId, postId]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author panlin
     *@Date 2019/10/11
     *@Time 11:29
     */
    @ApiOperation(value = "查看员工职位分类不分页",notes = "查看员工职位分类不分页")
    @RequestMapping(value = "/selectPostCategoryNoPage",method = RequestMethod.POST)
    public ResponseResult selectPostCategoryNoPage(String postIndustryIDSearch) {
        return beauticianService.selectPostCategoryNoPage(postIndustryIDSearch);
    }


    /**
     *@Description 添加职位分类
     *@Param [storeId, postId]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author panlin
     *@Date 2019/10/11
     *@Time 11:29
     */
    @ApiOperation(value = "添加职位分类",notes = "添加职位分类")
    @RequestMapping(value = "/insertPostCategory",method = RequestMethod.POST)
    public ResponseResult insertPostCategory(PostCategory postCategory) {
        return beauticianService.insertPostCategory(postCategory);
    }

    /**
     *@Description 修改职位分类
     *@Param [storeId, postId]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author panlin
     *@Date 2019/10/11
     *@Time 11:29
     */
    @ApiOperation(value = "修改职位分类",notes = "修改职位分类")
    @RequestMapping(value = "/updatePostCategory",method = RequestMethod.POST)
    public ResponseResult updatePostCategory(PostCategory postCategory) {
        return beauticianService.updatePostCategory(postCategory);
    }

    /**
     *@Description 删除职位分类
     *@Param [storeId, postId]
     *@Return com.lnmj.common.response.ResponseResult
     *@Author panlin
     *@Date 2019/10/11
     *@Time 11:29
     */
    @ApiOperation(value = "删除职位分类",notes = "删除职位分类")
    @RequestMapping(value = "/deletePostCategory",method = RequestMethod.POST)
    public ResponseResult deletePostCategory(PostCategory postCategory) {
        return beauticianService.deletePostCategory(postCategory);
    }

}
