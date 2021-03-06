package com.lnmj.data.serviceProxy;

import com.lnmj.common.response.ResponseResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "lnmj-store")
public interface StoreApi {
    @RequestMapping(value = "/manage/beautician/selectBeauticianById", method = RequestMethod.POST)
    ResponseResult selectBeauticianById(@RequestParam("beauticianId") Long beauticianId);

    @RequestMapping(value = "/manage/beautician/selectBeauticianByCode", method = RequestMethod.POST)
    ResponseResult selectBeauticianByCode(@RequestParam("staffNumber") String staffNumber);

    @RequestMapping(value = "/manage/store/selectStoreById", method = RequestMethod.POST)
    ResponseResult selectStoreById(@RequestParam("storeId") Long storeId);

    @RequestMapping(value = "/manage/beautician/selectPostById", method = RequestMethod.POST)
    ResponseResult selectPostById(@RequestParam("postId") Long postId);

    @RequestMapping(value = "/api/manage/industry/selectListIndustryNoPage", method = RequestMethod.POST)
    ResponseResult selectListIndustryNoPage();

    @RequestMapping(value = "/api/manage/store/selectStoreListNoPage", method = RequestMethod.POST)
    ResponseResult selectStoreListNoPage();

    @RequestMapping(value = "/api/manage/beautician/selectPostCategoryNoPage", method = RequestMethod.POST)
    ResponseResult selectPostCategoryNoPage();

    @RequestMapping(value = "/manage/beautician/selectPost", method = RequestMethod.POST)
    ResponseResult selectPost();

    @RequestMapping(value = "/manage/beautician/selectPostNoPage", method = RequestMethod.POST)
    ResponseResult selectPostNoPage();

    @RequestMapping(value = "/manage/beautician/selectPostLevelNoPage", method = RequestMethod.POST)
    ResponseResult selectPostLevelNoPage();

    @RequestMapping(value = "/api/manage/beautician/selectBeauticianByStoreIdArray", method = RequestMethod.POST)
    ResponseResult selectBeauticianByStoreIdArray(@RequestParam("storeId") String storeId);

    @RequestMapping(value = "/api/manage/beautician/selectBeauticianListNoPage", method = RequestMethod.POST)
    ResponseResult selectBeauticianListNoPage();

    @RequestMapping(value = "/manage/experienceCard/selectExperienceCardByCardId", method = RequestMethod.POST)
    ResponseResult selectExperienceCardByCardId(@RequestParam("cardId") Long cardId);

    //根据ID查询总公司（获取用户名和密码）
    @RequestMapping(value = "/manage/company/selectCompanyByID", method = RequestMethod.POST)
    ResponseResult selectCompanyByID(@RequestParam("companyId") Long companyId);

    @RequestMapping(value = "/manage/industry/selectListIndustryById", method = RequestMethod.POST)
    ResponseResult selectListIndustryById(@RequestParam("industryID") Long industryID);

    @ApiOperation(value = "查看组员", notes = "查看组员")
    @RequestMapping(value = "/manage/beautician/selectGroupMember", method = RequestMethod.POST)
    ResponseResult selectGroupMember(@RequestParam("groupId") Long groupId,
                                     @RequestParam("beauticianId") Long beauticianId);
}

