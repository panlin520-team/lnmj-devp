package com.lnmj.store.business.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeBeauticianEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.common.utils.ListPageUntil;
import com.lnmj.common.utils.NumberUtils;
import com.lnmj.common.utils.PhoneUtils;
import com.lnmj.store.business.IBeauticianService;
import com.lnmj.store.entity.*;
import com.lnmj.store.entity.VO.AppointmentVO;
import com.lnmj.store.repository.*;
import com.lnmj.store.serviceProxy.K3Api_organization;
import com.lnmj.store.serviceProxy.OrderApi;
import com.lnmj.store.serviceProxy.ProductApi;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 〈〉
 *
 * @Author renqingyun
 * @create 2019/5/8
 */
@Transactional
@Service("beauticianService")
public class BeauticianService implements IBeauticianService {
    @Resource
    private IBeauticianDao beauticianDao;
    @Resource
    private ProductApi productApi;
    @Resource
    private IIndustryDao iIndustryDao;
    @Resource
    private OrderApi orderApi;
    @Resource
    private K3Api_organization k3Api;
    @Resource
    private ICompanyDao CompanyDaoImpl;
    @Resource
    private IDepartmentDao departmentDao;
    @Resource
    private ICompanyDao iCompanyDao;
    @Resource
    private IStoreDao iStoreDao;

    @Override
    public ResponseResult selectBeauticianList(int pageNum, int pageSize, String storeId, String keyWordStaffNumber, String keyWordName, String nursingDate, Long companyType, Long companyId, Integer isSaleMan) {
        /*PageHelper.startPage(pageNum, pageSize);*/
        Beautician beautician = new Beautician();
        if (StringUtils.isNotBlank(keyWordStaffNumber)) {
            beautician.setStaffNumber(Long.valueOf(keyWordStaffNumber));
        }
        if (StringUtils.isNotBlank(keyWordName)) {
            beautician.setName(keyWordName);
        }

        if (companyType != null && companyId != null) {
            beautician.setCompanyType(companyType.toString());
            beautician.setCompanyId(companyId.toString());
        }

        if (isSaleMan != null) {
            beautician.setIsSaleMan(isSaleMan);
        }
        List<Beautician> beauticianList = beauticianDao.selectBeauticiantList(beautician);
        //查询分组名称
        Group group = new Group();
        List<Group> groupList = beauticianDao.selectGroup(group);
        List<Department> departmentList = departmentDao.selectDepartmentList(new HashMap());
        List<PostCategory> postCategoryList = beauticianDao.selectPostCategoryList(new HashMap());
        for (Beautician beauticianItem : beauticianList) {
            for (Group groupItem : groupList) {
                if (beauticianItem.getGroupId() == Long.parseLong(groupItem.getGroupId().toString())) {
                    beauticianItem.setGroupName(groupItem.getName());
                }
            }
            for (Department department : departmentList) {
                if (beauticianItem.getDepartmentId() == Long.parseLong(department.getId().toString())) {
                    beauticianItem.setDepartmentName(department.getName());
                }
            }
            for (PostCategory postCategory : postCategoryList) {
                if (beauticianItem.getPostCategoryId() == Long.parseLong(postCategory.getPostCategoryId().toString())) {
                    beauticianItem.setPostCategoryName(postCategory.getName());
                }
            }
            for (PostCategory postCategory : postCategoryList) {
                if (beauticianItem.getPartTimePostCategoryId() == Long.parseLong(postCategory.getPostCategoryId().toString())) {
                    beauticianItem.setParTimePostCategoryName(postCategory.getName());
                }
            }
        }
        //查询职位名称-及兼职
        List<Post> postList = beauticianDao.selectPost(new HashMap());
        for (Beautician itemBeautician : beauticianList) {
            for (Post itemPost : postList) {
                if (itemBeautician.getPostId() == itemPost.getPostId()) {
                    itemBeautician.setPostName(itemPost.getName());
                    itemBeautician.setPostLevel(itemPost.getPostLevel());
                }
                if (itemBeautician.getPartTimePostId() == itemPost.getPostId()) {
                    itemBeautician.setParTimePostName(itemPost.getName());

                }
            }
        }

        //查询职位等级
        List<PostLevel> postLevelList = beauticianDao.selectPostLevel(new HashMap());
        for (Beautician itemBeautician : beauticianList) {
            Long postLevelId = null;
            for (Post itemPost : postList) {
                if (itemBeautician.getPostId() == itemPost.getPostId()) {
                    postLevelId = itemPost.getPostLevel();
                }
            }
            for (PostLevel itemPostLevel : postLevelList) {
                if (postLevelId == itemPostLevel.getPostLevelId()) {
                    itemBeautician.setPostLevelName(itemPostLevel.getPostLevelName());
                }
            }
        }


        if (StringUtils.isNotBlank(nursingDate)) {
            //查询员工预约列表
            AppointmentVO appointment = new AppointmentVO();
            appointment.setNursingDate(nursingDate);
            ResponseResult responseResult = orderApi.selectAppointmentOrderListByDate(/*nursingDate, */Long.parseLong(storeId));
            if (responseResult.getResponseStatusType().getError().getErrorCode() == null) {
                //如果有预约
                List<Map> appointmentOrderList = (List<Map>) responseResult.getResult();
                for (Beautician itemBeautician : beauticianList) {
                    List<Map> mapList1 = new ArrayList<>();
                    for (Map map : appointmentOrderList) {
                        List<Map> mapList2 = (List<Map>) map.get("productOrderList");
                        for (Map map2 : mapList2) {
                            String bookingBeauticianIdsStr = map2.get("bookingBeauticianIds").toString();
                            if (bookingBeauticianIdsStr.indexOf(itemBeautician.getStaffNumber().toString()) != -1) {
                                JSONArray jsonArr = JSON.parseArray(bookingBeauticianIdsStr);
                                for (int i = 0; i < jsonArr.size(); i++) {
                                    if (jsonArr.getJSONObject(i).getString("beauticianId").equals(itemBeautician.getStaffNumber().toString())
                                            && jsonArr.getJSONObject(i).getString("nursingDate").split(" ")[0].equals(nursingDate)) {
                                        map.put("nursingDate", jsonArr.getJSONObject(i).getString("nursingDate"));
                                        map.put("beauticianName", jsonArr.getJSONObject(i).getString("beauticianName"));
                                        ((Map) map.get("appointmentOrder")).put("duration", jsonArr.getJSONObject(i).getString("duration"));
                                        map.put("beauticians", jsonArr);
                                        map.put("nursingTime", jsonArr.getJSONObject(i).getString("nursingDate"));
                                        mapList1.add(map);
                                        itemBeautician.setAppointmentList(mapList1);
                                    }
                                }
                                continue;
                            }
                        }
                    }

                }

            } else {
                //如果这天没有一个预约
                for (Beautician itemBeautician : beauticianList) {
                    itemBeautician.setAppointmentList(new ArrayList<>());
                }
            }
        }

        for (int m = 0; m < beauticianList.size(); m++) {
            List<Map> mapList = beauticianList.get(m).getAppointmentList();
            if (mapList != null) {
                for (int i = 0; i < mapList.size(); i++) {
                    List<Map> mapList1 = (List<Map>) mapList.get(i).get("beauticians");
                    for (int j = 0; j < mapList1.size(); j++) {

                        mapList1.get(j).put("appointmentStatus", ((Map) (mapList.get(i).get("appointmentOrder"))).get("appointmentStatus"));
                        mapList1.get(j).put("orderLink", mapList.get(i).get("orderLink"));
                        mapList1.get(j).put("appointmentOrder", mapList.get(i).get("appointmentOrder"));

                        String a = "";
                        for (Map beauticians : (List<Map>) mapList.get(i).get("beauticians")) {
                            a = a + "," + beauticians.get("beauticianName");
                        }
                        mapList1.get(j).put("beauticianNames", a);
                        mapList1.get(j).put("orderNumber", mapList.get(i).get("orderNumber"));
                        mapList1.get(j).put("mobile", mapList.get(i).get("mobile"));
                        mapList1.get(j).put("productOrderList", mapList.get(i).get("productOrderList"));
                        mapList1.get(j).put("nursingDate", mapList.get(i).get("nursingDate"));
                        mapList1.get(j).put("cardNumber", mapList.get(i).get("cardNumber"));
                        mapList1.get(j).put("totalPrice", mapList.get(i).get("totalPrice"));
                        mapList1.get(j).put("totalDiscount", mapList.get(i).get("totalDiscount"));
                        mapList1.get(j).put("remark", mapList.get(i).get("remark"));
                        for (int n = 0; n < beauticianList.size(); n++) {
                            if (beauticianList.get(n).getStaffNumber().toString().equals(mapList1.get(j).get("beauticianId").toString())) {
                                mapList1.get(j).put("sortIndex", n);
                            }
                        }

                    }
                }

            }

        }

        Map mapRsultPage = ListPageUntil.listPage(beauticianList, pageSize, pageNum);
        Map mapResult = new HashMap();
        if (((List) mapRsultPage.get("list")).size() > 0) {
            mapResult.put("list", mapRsultPage.get("list"));
            mapResult.put("total", mapRsultPage.get("total"));
            return ResponseResult.success(mapResult);
        }
        return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.BEAUTICIAN_LIST_NULL.getCode(), ResponseCodeBeauticianEnum.BEAUTICIAN_LIST_NULL.getDesc()));
    }

    @Override
    public ResponseResult selectBeauticianListNoPage2(String storeId, String keyWordStaffNumber, String keyWordName, String nursingDate, Long companyType, Long companyId, Integer isSaleMan) {
        /*PageHelper.startPage(pageNum, pageSize);*/
        Beautician beautician = new Beautician();
        if (StringUtils.isNotBlank(keyWordStaffNumber)) {
            beautician.setStaffNumber(Long.valueOf(keyWordStaffNumber));
        }
        if (StringUtils.isNotBlank(keyWordName)) {
            beautician.setName(keyWordName);
        }

        if (companyType != null && companyId != null) {
            beautician.setCompanyType(companyType.toString());
            beautician.setCompanyId(companyId.toString());
        }

        if (isSaleMan != null) {
            beautician.setIsSaleMan(isSaleMan);
        }
        List<Beautician> beauticianList = beauticianDao.selectBeauticiantList(beautician);
        //查询分组名称
        Group group = new Group();
        List<Group> groupList = beauticianDao.selectGroup(group);
        List<Department> departmentList = departmentDao.selectDepartmentList(new HashMap());
        List<PostCategory> postCategoryList = beauticianDao.selectPostCategoryList(new HashMap());
        for (Beautician beauticianItem : beauticianList) {
            for (Group groupItem : groupList) {
                if (beauticianItem.getGroupId() == Long.parseLong(groupItem.getGroupId().toString())) {
                    beauticianItem.setGroupName(groupItem.getName());
                }
            }
            for (Department department : departmentList) {
                if (beauticianItem.getDepartmentId() == Long.parseLong(department.getId().toString())) {
                    beauticianItem.setDepartmentName(department.getName());
                }
            }
            for (PostCategory postCategory : postCategoryList) {
                if (beauticianItem.getPostCategoryId() == Long.parseLong(postCategory.getPostCategoryId().toString())) {
                    beauticianItem.setPostCategoryName(postCategory.getName());
                }
            }
            for (PostCategory postCategory : postCategoryList) {
                if (beauticianItem.getPartTimePostCategoryId() == Long.parseLong(postCategory.getPostCategoryId().toString())) {
                    beauticianItem.setParTimePostCategoryName(postCategory.getName());
                }
            }
        }
        //查询职位名称-及兼职
        List<Post> postList = beauticianDao.selectPost(new HashMap());
        for (Beautician itemBeautician : beauticianList) {
            for (Post itemPost : postList) {
                if (itemBeautician.getPostId() == itemPost.getPostId()) {
                    itemBeautician.setPostName(itemPost.getName());
                    itemBeautician.setPostLevel(itemPost.getPostLevel());
                }
                if (itemBeautician.getPartTimePostId() == itemPost.getPostId()) {
                    itemBeautician.setParTimePostName(itemPost.getName());

                }
            }
        }

        //查询职位等级
        List<PostLevel> postLevelList = beauticianDao.selectPostLevel(new HashMap());
        for (Beautician itemBeautician : beauticianList) {
            Long postLevelId = null;
            for (Post itemPost : postList) {
                if (itemBeautician.getPostId() == itemPost.getPostId()) {
                    postLevelId = itemPost.getPostLevel();
                }
            }
            for (PostLevel itemPostLevel : postLevelList) {
                if (postLevelId == itemPostLevel.getPostLevelId()) {
                    itemBeautician.setPostLevelName(itemPostLevel.getPostLevelName());
                }
            }
        }


        if (StringUtils.isNotBlank(nursingDate)) {
            //查询员工预约列表
            AppointmentVO appointment = new AppointmentVO();
            appointment.setNursingDate(nursingDate);
            ResponseResult responseResult = orderApi.selectAppointmentOrderListByDate(/*nursingDate, */Long.parseLong(storeId));
            if (responseResult.getResponseStatusType().getError().getErrorCode() == null) {
                //如果有预约
                List<Map> appointmentOrderList = (List<Map>) responseResult.getResult();
                for (Beautician itemBeautician : beauticianList) {
                    List<Map> mapList1 = new ArrayList<>();
                    for (Map map : appointmentOrderList) {
                        List<Map> mapList2 = (List<Map>) map.get("productOrderList");
                        for (Map map2 : mapList2) {
                            String bookingBeauticianIdsStr = map2.get("bookingBeauticianIds").toString();
                            if (bookingBeauticianIdsStr.indexOf(itemBeautician.getStaffNumber().toString()) != -1) {
                                JSONArray jsonArr = JSON.parseArray(bookingBeauticianIdsStr);
                                for (int i = 0; i < jsonArr.size(); i++) {
                                    if (jsonArr.getJSONObject(i).getString("beauticianId").equals(itemBeautician.getStaffNumber().toString())
                                            && jsonArr.getJSONObject(i).getString("nursingDate").split(" ")[0].equals(nursingDate)) {
                                        map.put("nursingDate", jsonArr.getJSONObject(i).getString("nursingDate"));
                                        map.put("beauticianName", jsonArr.getJSONObject(i).getString("beauticianName"));
                                        ((Map) map.get("appointmentOrder")).put("duration", jsonArr.getJSONObject(i).getString("duration"));
                                        map.put("beauticians", jsonArr);
                                        map.put("nursingTime", jsonArr.getJSONObject(i).getString("nursingDate"));
                                        mapList1.add(map);
                                        itemBeautician.setAppointmentList(mapList1);
                                    }
                                }
                                continue;
                            }
                        }
                    }

                }

            } else {
                //如果这天没有一个预约
                for (Beautician itemBeautician : beauticianList) {
                    itemBeautician.setAppointmentList(new ArrayList<>());
                }
            }
        }

        for (int m = 0; m < beauticianList.size(); m++) {
            List<Map> mapList = beauticianList.get(m).getAppointmentList();
            if (mapList != null) {
                for (int i = 0; i < mapList.size(); i++) {
                    List<Map> mapList1 = (List<Map>) mapList.get(i).get("beauticians");
                    for (int j = 0; j < mapList1.size(); j++) {

                        mapList1.get(j).put("appointmentStatus", ((Map) (mapList.get(i).get("appointmentOrder"))).get("appointmentStatus"));
                        mapList1.get(j).put("orderLink", mapList.get(i).get("orderLink"));
                        mapList1.get(j).put("appointmentOrder", mapList.get(i).get("appointmentOrder"));

                        String a = "";
                        for (Map beauticians : (List<Map>) mapList.get(i).get("beauticians")) {
                            a = a + "," + beauticians.get("beauticianName");
                        }
                        mapList1.get(j).put("beauticianNames", a);
                        mapList1.get(j).put("orderNumber", mapList.get(i).get("orderNumber"));
                        mapList1.get(j).put("mobile", mapList.get(i).get("mobile"));
                        mapList1.get(j).put("productOrderList", mapList.get(i).get("productOrderList"));
                        mapList1.get(j).put("nursingDate", mapList.get(i).get("nursingDate"));
                        mapList1.get(j).put("cardNumber", mapList.get(i).get("cardNumber"));
                        mapList1.get(j).put("totalPrice", mapList.get(i).get("totalPrice"));
                        mapList1.get(j).put("totalDiscount", mapList.get(i).get("totalDiscount"));
                        mapList1.get(j).put("remark", mapList.get(i).get("remark"));
                        for (int n = 0; n < beauticianList.size(); n++) {
                            if (beauticianList.get(n).getStaffNumber().toString().equals(mapList1.get(j).get("beauticianId").toString())) {
                                mapList1.get(j).put("sortIndex", n);
                            }
                        }

                    }
                }

            }

        }

        return ResponseResult.success(beauticianList);
    }

    @Override
    public ResponseResult selectBeauticianListNoPage(String companyType, String companyId, Integer isSaleMan) {
        Beautician beautician = new Beautician();
        if (companyId != null) {
            String[] storeIdArray = companyId.replaceAll("]", "").replace("[", "").split(",");
            beautician.setList(Arrays.asList(storeIdArray));
        }
        beautician.setCompanyType(companyType);
        if (isSaleMan != null) {
            beautician.setIsSaleMan(isSaleMan);
        }
        List<Beautician> beauticianList = beauticianDao.selectBeauticiantList(beautician);
        //查看所有的职位
        List<Post> postList = beauticianDao.selectPost(new HashMap());
        for (Beautician beauticianItem : beauticianList) {
            for (Post post : postList) {
                if (beauticianItem.getPostId() == post.getPostId()) {
                    beauticianItem.setPostCategoryId(post.getPostCategoryId());
                }
            }
        }

        if (beauticianList.size() > 0) {
            return ResponseResult.success(beauticianList);
        }
        return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.BEAUTICIAN_LIST_NULL.getCode(), ResponseCodeBeauticianEnum.BEAUTICIAN_LIST_NULL.getDesc()));
    }

    @Override
    public ResponseResult selectBeauticianByStoreId(String companyId, String companyType) {
        Map map = new HashMap();
        map.put("companyId", companyId);
        map.put("companyType", companyType);
        List<Beautician> beauticianList = beauticianDao.selectBeauticianByStoreId(map);
        if (beauticianList.size() > 0) {
            return ResponseResult.success(beauticianList);
        }
        return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.BEAUTICIAN_LIST_NULL.getCode(), ResponseCodeBeauticianEnum.BEAUTICIAN_LIST_NULL.getDesc()));
    }

    @Override
    public ResponseResult selectBeauticianByStoreIdArray(String storeId) {
        String[] storeIdArray = storeId.replaceAll("]", "").replace("[", "").split(",");
        if (storeIdArray[0].equals("")) {
            storeIdArray = null;
        }
        Map mapList = new HashMap();
        mapList.put("list", storeIdArray);
        mapList.put("companyType", "3");
        List<Beautician> beauticianList = beauticianDao.selectBeauticianByStoreIdArray(mapList);
        if (beauticianList.size() > 0) {
            return ResponseResult.success(beauticianList);
        }
        return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.BEAUTICIAN_LIST_NULL.getCode(), ResponseCodeBeauticianEnum.BEAUTICIAN_LIST_NULL.getDesc()));
    }

    @Override
    public ResponseResult selectPostById(Long postId) {
        return ResponseResult.success(beauticianDao.selectPostById(postId));
    }

    @Override
    public ResponseResult selectPostLevel(int pageNum, int pageSize, String postLevelNameKeyword, Long companyId) {
        PageHelper.startPage(pageNum, pageSize);
        Map map = new HashMap();
        map.put("postLevelNameKeyword", postLevelNameKeyword);
        map.put("companyId", companyId);
        List<PostLevel> postLevelList = beauticianDao.selectPostLevel(map);
        if (postLevelList.size() > 0) {
            PageInfo pageInfo = new PageInfo(postLevelList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.POST_LEVEL_LIST_NULL.getCode(), ResponseCodeBeauticianEnum.POST_LEVEL_LIST_NULL.getDesc()));
    }

    @Override
    public ResponseResult addPostLevel(PostLevel postLevel) {
        beauticianDao.addPostLevel(postLevel);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult updatePostLevel(PostLevel postLevel) {
        beauticianDao.updatePostLevel(postLevel);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult deletePostLevel(PostLevel postLevel) {
        beauticianDao.deletePostLevel(postLevel);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult selectPostLevelNoPage() {
        List<PostLevel> postLevelList = beauticianDao.selectPostLevel(new HashMap());
        if (postLevelList.size() > 0) {
            return ResponseResult.success(postLevelList);
        }
        return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.POST_LEVEL_LIST_NULL.getCode(), ResponseCodeBeauticianEnum.POST_LEVEL_LIST_NULL.getDesc()));
    }

    @Override
    public ResponseResult selectBeauticianByStoreIdAndPostId(Long storeId, Long postId) {
        Map map = new HashMap();
        map.put("companyId", storeId);
        map.put("companyType", "3");
        map.put("postId", postId);
        List<Beautician> beauticianList = beauticianDao.selectBeauticianByStoreIdAndPostId(map);
        return ResponseResult.success(beauticianList);
    }


    @Override
    public ResponseResult selectBeauticianById(Long beauticianId) {
        if (beauticianId == null) {
            return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.BEAUTICIAN_ID_NULL.getCode(), ResponseCodeBeauticianEnum.BEAUTICIAN_ID_NULL.getDesc()));
        }
        Beautician beautician = beauticianDao.selectBeauticianByCode(beauticianId.toString());

        //查询职位等级
        List<PostLevel> postLevelList = beauticianDao.selectPostLevel(new HashMap());
        List<Department> deptDept = departmentDao.selectDepartmentList(new HashMap());
        List<Post> postList = beauticianDao.selectPost(new HashMap());
        Long postLevelId = null;
        for (Post itemPost : postList) {
            if (beautician.getPostId() == itemPost.getPostId()) {
                postLevelId = itemPost.getPostLevel();
            }
        }
        for (Department department : deptDept) {
            if (beautician.getDepartmentId() == department.getId()) {
                beautician.setDepartmentName(department.getName());
            }
        }

        for (PostLevel itemPostLevel : postLevelList) {
            if (postLevelId == itemPostLevel.getPostLevelId()) {
                beautician.setPostLevelName(itemPostLevel.getPostLevelName());
                beautician.setPostLevel(itemPostLevel.getPostLevelId());
            }
        }
        return ResponseResult.success(beautician);
    }

    @Override
    public ResponseResult selectBeauticianByCode(String staffNumber) {
        Beautician beautician = beauticianDao.selectBeauticianByCode(staffNumber);

        return ResponseResult.success(beautician);
    }

    @Override
    public ResponseResult insertBeautician(String imgUrl, Beautician beautician, String FCreateOrgId, String FUseOrgId, String companyType, String companyId) {
        if (companyType != null && companyType.equals("3")) {
            //查看员工职位的业绩方式
            Post postResult = beauticianDao.selectPostById(beautician.getPostId());
            Integer postAchievement = postResult.getPostAchievement();
            if (postAchievement == 2) {
                //如果所属的职位 为分组业绩 判断是否选择分组
                if (beautician.getGroupId() == null) {
                    return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.GROUPID_NULL_FENZUYEJI.getCode(), ResponseCodeBeauticianEnum.GROUPID_NULL_FENZUYEJI.getDesc()));
                }
            }
        }
        if ((beautician.getPartTimePostId() != null && beautician.getPartTimePostId() == 0) ||
                (beautician.getPartTimePostCategoryId() != null && beautician.getPartTimePostCategoryId() == 0)) {
            beautician.setPartTimePostId(0l);
            beautician.setPartTimePostCategoryId(0l);
        }

        if (beautician.getIsPartTime() != null) {
            if (beautician.getIsPartTime() == 1 && beautician.getIsBasicSalary() == 1) {
                //如果是兼职 还计算底薪
                return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.POST_PART_NO_BASIC_SALARY.getCode(), ResponseCodeBeauticianEnum.POST_PART_NO_BASIC_SALARY.getDesc()));
            }
        }

        if (beautician.getPostId() == beautician.getPartTimePostId()) {
            return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.POST_PART_TIME_POST_SAME.getCode(), ResponseCodeBeauticianEnum.POST_PART_TIME_POST_SAME.getDesc()));
        }
        //查看员工名称是否存在
        int resultIntName = beauticianDao.checkStaffName(beautician);
        if (resultIntName > 0) {
            return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.STAFF_NAME_IS_EXIST.getCode(), ResponseCodeBeauticianEnum.STAFF_NAME_IS_EXIST.getDesc()));
        }

        //非空判断
        if (StringUtils.isBlank(beautician.getEntryTime())) {
            Date currentTime = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = formatter.format(currentTime);
            beautician.setEntryTime(dateString);
        }
        if (StringUtils.isBlank(beautician.getMobile())) {
            return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.MOBILE_NULL.getCode(), ResponseCodeBeauticianEnum.MOBILE_NULL.getDesc()));
        } else {
            if (!PhoneUtils.isMobileNO(beautician.getMobile())) {
                return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.MOBILE_ILLEGAL.getCode(), ResponseCodeBeauticianEnum.MOBILE_ILLEGAL.getDesc()));
            }
            //查看手机号是否已经存在
            int resultInt = beauticianDao.checkMobile(beautician);
            if (resultInt > 0) {
                return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.MOBILE_EXIST.getCode(), ResponseCodeBeauticianEnum.MOBILE_EXIST.getDesc()));
            }
        }

        //TODO openid设置
        beautician.setStaffNumber(Long.parseLong(NumberUtils.getRandomCouponsNo()));

        //查找职位分类
        if (beautician.getPostId() != null) {
            beautician.setPostCategoryId(beauticianDao.selectPostById(beautician.getPostId()).getPostCategoryId());
        }
        beautician.setHeadUrl(imgUrl);
        int resultInt = beauticianDao.insertBeautician(beautician);
        if (resultInt <= 0) {
            return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.BEAUTICIAN_ADD_FAIL.getCode(), ResponseCodeBeauticianEnum.BEAUTICIAN_ADD_FAIL.getDesc()));
        }
        HashMap<String, String> userNameAndPassword = getUserNameAndPassword(null);
        String dataCentre = userNameAndPassword.get("dataCentre");
        String userName = userNameAndPassword.get("userName");
        String password = userNameAndPassword.get("password");
        //k3操作
        //拿到部门的k3number
        long departmentId = beautician.getDepartmentId();
        Map<Object, Object> map = new HashMap<>();
        map.put("id", departmentId);
        List<Department> departments = departmentDao.selectDepartmentList(map);
        Department department = departments.get(0);
        //拿到职位的k3number
        Long postId = beautician.getPostId();
        Post post = beauticianDao.selectPostById(postId);
        Store store = null;
        if (beautician.getCompanyType().equals("3")) {
            store = iStoreDao.selectStoreById(Long.parseLong(beautician.getCompanyId()));
            FCreateOrgId = store.getK3Number();
            FUseOrgId = store.getK3Number();
        }
        ResponseResult result = k3Api.saveEmployees(dataCentre, userName, password, beautician.getName(), FCreateOrgId, FUseOrgId, beautician.getStaffNumber().toString(), department.getK3Number(), post.getK3Number());
        if (result.isSuccess()) {
            Map resultHashMap = (HashMap<String, Object>) result.getResult();
            Map resulut = (HashMap<String, Object>) resultHashMap.get("Result");
            Map resultStatus = (HashMap<String, Object>) resulut.get("ResponseStatus");
            Boolean isSuccess = (Boolean) resultStatus.get("IsSuccess");
            if (isSuccess) {
                //获取保存成功后的number和id
                String id = String.valueOf(resulut.get("Id"));
                String number = String.valueOf(resulut.get("Number"));
                Beautician beautician1 = new Beautician();
                beautician1.setBeauticianId(beautician.getBeauticianId());
                beautician1.setK3Id(id);
                beautician1.setK3Number(number);
                beauticianDao.updateBeautician(beautician1);
                JSONObject object = new JSONObject();
                object.put("message", "添加美容师成功");
                return ResponseResult.success(object);
            }
        }


        return ResponseResult.success();
/*
        return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.BEAUTICIAN_ADD_FAIL.getCode(), ResponseCodeBeauticianEnum.BEAUTICIAN_ADD_FAIL.getDesc()));
*/
    }

    @Override
    public ResponseResult updateBeautician(Beautician beautician) {
        if ((beautician.getPartTimePostId() != null && beautician.getPartTimePostId() == 0) ||
                (beautician.getPartTimePostCategoryId() != null && beautician.getPartTimePostCategoryId() == 0)) {
            beautician.setPartTimePostId(0l);
            beautician.setPartTimePostCategoryId(0l);
        }
        if (beautician.getIsPartTime() != null && beautician.getIsBasicSalary() != null) {
            if (beautician.getIsPartTime() == 1 && beautician.getIsBasicSalary() == 1) {
                //如果是兼职 还计算底薪
                return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.POST_PART_NO_BASIC_SALARY.getCode(), ResponseCodeBeauticianEnum.POST_PART_NO_BASIC_SALARY.getDesc()));
            }
        }

        if (beautician.getPartTimePostId() != null && beautician.getPostId() == beautician.getPartTimePostId()) {
            return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.POST_PART_TIME_POST_SAME.getCode(), ResponseCodeBeauticianEnum.POST_PART_TIME_POST_SAME.getDesc()));
        }

//        if (StringUtils.isBlank(beautician.getEntryTime())) {
//            Date currentTime = new Date();
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String dateString = formatter.format(currentTime);
//            beautician.setEntryTime(dateString);
//        }
        //非空判断
        if (beautician.getMobile() != null) {
            if (!PhoneUtils.isMobileNO(beautician.getMobile())) {
                return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.MOBILE_ILLEGAL.getCode(), ResponseCodeBeauticianEnum.MOBILE_ILLEGAL.getDesc()));
            }
            //查看手机号是否已经存在
            int resultInt = beauticianDao.checkMobile(beautician);
            if (resultInt > 0) {
                return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.MOBILE_EXIST.getCode(), ResponseCodeBeauticianEnum.MOBILE_EXIST.getDesc()));

            }
        }
        beautician.setHeadUrl(beautician.getHeadUrl());
        //根据职位分类和职位等级确定职位id
/*        List<Post> postList = beauticianDao.selectPost(new HashMap());
        for (Post post : postList) {
            if (post.getPostCategoryId() == beautician.getPostCategoryId() && post.getPostLevel() == beautician.getPostLevel()) {
                beautician.setPostId(post.getPostId());
            }
        }*/


        Company company = null;
        Subsidiary subsidiary = null;
        Store store = null;
        if (beautician.getCompanyType().equals("2")) {
            subsidiary = iCompanyDao.selectSubsidiaryByID(Long.parseLong(beautician.getCompanyId()));
            company = iCompanyDao.selectCompanyByID(subsidiary.getParentCompany());
        } else if (beautician.getCompanyType().equals("3")) {
            store = iStoreDao.selectStoreById(Long.parseLong(beautician.getCompanyId()));
            subsidiary = iCompanyDao.selectSubsidiaryByID(store.getSubCompanyId());
            company = iCompanyDao.selectCompanyByID(subsidiary.getParentCompany());
        }
        ResponseResult resultK3 = null;
        if (beautician.getIsSaleMan() != null) {
            //如果是修改是否为销售员就要接k3
            if (beautician.getIsSaleMan() == 1) {
                if (beautician.getCompanyType().toString().equals("2")) {
                    resultK3 = k3Api.saveYeWuYuan(company.getDataCentre(), company.getYewuDataCentreUserName(), company.getYewuDataCentrePassword(), subsidiary.getK3Number(), beautician.getK3Number());
                } else {
                    resultK3 = k3Api.saveYeWuYuan(company.getDataCentre(), company.getYewuDataCentreUserName(), company.getYewuDataCentrePassword(), store.getK3Number(), beautician.getK3Number());
                }

                String k3IdYeWuYuan = (((Map) (((Map) resultK3.getResult()).get("Result")))).get("Id").toString();
                beautician.setYeWuYuanK3Id(k3IdYeWuYuan);
            } else if (beautician.getIsSaleMan() == 0) {
                resultK3 = k3Api.delYeWuYuan(company.getDataCentre(), company.getYewuDataCentreUserName(), company.getYewuDataCentrePassword(), beautician.getYeWuYuanK3Id());
                beautician.setYeWuYuanK3Id("");
            }
            if (resultK3.isSuccess()) {
                if (resultK3.getResult().equals("暂未登录")) {
                    return ResponseResult.error(new Error(1, "k3暂未登录"));
                }
                Map resultHashMap = (HashMap<String, Object>) resultK3.getResult();
                Map resulut = (HashMap<String, Object>) resultHashMap.get("Result");
                Map resultStatus = (HashMap<String, Object>) resulut.get("ResponseStatus");
                Boolean isSuccess = (Boolean) resultStatus.get("IsSuccess");
                if (isSuccess) {
                    int result = beauticianDao.updateBeautician(beautician);
                    if (result <= 0) {
                        return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.UPDATE_BEAUTICIAN_FAIL.getCode(), ResponseCodeBeauticianEnum.UPDATE_BEAUTICIAN_FAIL.getDesc()));
                    }
                }
            } else {
                return ResponseResult.success("添加失败！,K3未保存成功");
            }
        } else {
            int result = beauticianDao.updateBeautician(beautician);
            if (result <= 0) {
                return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.UPDATE_BEAUTICIAN_FAIL.getCode(), ResponseCodeBeauticianEnum.UPDATE_BEAUTICIAN_FAIL.getDesc()));
            }
        }
        return ResponseResult.success((Beautician) (this.selectBeauticianById(beautician.getStaffNumber()).getResult()));
    }

    @Override
    public ResponseResult deleteBeautician(String beauticianIds, String modifyOperator) {
        if (beauticianIds == null) {
            return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.BEAUTICIAN_ID_NULL.getCode(), ResponseCodeBeauticianEnum.BEAUTICIAN_ID_NULL.getDesc()));
        }
        List beauticianIdList = new ArrayList();
        if (beauticianIds.indexOf(",") == 0) {
            //如果只有一个
            beauticianIdList.add(beauticianIds);
        } else {
            for (String item : beauticianIds.split(",")) {
                beauticianIdList.add(item);
            }
        }
        Map map = new HashMap();
        map.put("list", beauticianIdList);
        map.put("modifyOperator", modifyOperator);
        int resultInt = beauticianDao.deleteBeautician(map);
        if (resultInt <= 0) {
            return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.DELETE_BEAUTICIAN_FAIL.getCode(), ResponseCodeBeauticianEnum.DELETE_BEAUTICIAN_FAIL.getDesc()));

        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult insertProjectToBeautician(Long postId, Integer postLevel, JSONArray projects) {
        if (postId == null) {
            return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.POST_ID_NULL.getCode(), ResponseCodeBeauticianEnum.POST_ID_NULL.getDesc()));
        }
        if (postLevel == null) {
            return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.POST_LEVEL_ID_NULL.getCode(), ResponseCodeBeauticianEnum.POST_LEVEL_ID_NULL.getDesc()));
        }
        //查看此美容师是否有授权项目
        Map map = new HashMap();
        map.put("postId", postId);
        map.put("postLevel", postLevel);
        int resultIntCheck = beauticianDao.checkProduct(map);
        BeauticianProject beauticianProject = new BeauticianProject();
        if (projects.size() != 0) {
            beauticianProject.setPostId(postId);
            beauticianProject.setPostLevel(postLevel);
            beauticianProject.setProjuects(projects.toString());
        }

        if (resultIntCheck <= 0) {
            //如果没有授权项目，直接插入
            if (projects.size() != 0) {
                beauticianDao.insertProjectToBeautician(beauticianProject);
            } else {
                beauticianDao.deleteProject(map);
            }
        } else {
            //如果有授权项目，先删除再插入
            beauticianDao.deleteProject(map);
            if (projects.size() != 0) {
                //如果不传任何授权项目，就只删除不插入了
                beauticianDao.insertProjectToBeautician(beauticianProject);
            }
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult selectProject(int pageNum, int pageSize, Long postId, Integer postLevel, String keyWordProductCode, String keyWordProductName) {
        PageHelper.startPage(pageNum, pageSize);
        if (postId == null) {
            return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.POST_ID_NULL.getCode(), ResponseCodeBeauticianEnum.POST_ID_NULL.getDesc()));
        }
        if (postLevel == null) {
            return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.POST_LEVEL_ID_NULL.getCode(), ResponseCodeBeauticianEnum.POST_LEVEL_ID_NULL.getDesc()));
        }
        //查询所有的项目
        ResponseResult responseResult = productApi.selectServiceVOList(pageNum, pageSize, keyWordProductCode, keyWordProductName);
        Map mappost = new HashMap();
        mappost.put("postId", postId);
        mappost.put("postLevel", postLevel);
        BeauticianProject beauticianProject = beauticianDao.selectProject(mappost);
        if (beauticianProject != null) {
            JSONArray projects = JSONArray.parseArray(beauticianProject.getProjuects());//1，2

            Map resultList = (Map) responseResult.getResult();
            List<Map> listAll = (List) resultList.get("list");
            for (Map map : listAll) {
                map.put("isChecked", 0);
                for (Object item : projects) {
                    if ((Integer) map.get("serviceProductId") == Integer.parseInt(item.toString().trim())) {
                        map.put("isChecked", 1);
                    }
                }
            }
            resultList.put("beauticianProjectId", beauticianProject.getProjuects());
            resultList.put("postId", beauticianProject.getPostId());
            resultList.put("postLevel", beauticianProject.getPostLevel());
        }
        return responseResult;
    }

    @Override
    public ResponseResult selectProjectNoPage(Long postId, Integer postLevel, String keyWordProductCode, String keyWordProductName) {
        if (postId == null) {
            return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.POST_ID_NULL.getCode(), ResponseCodeBeauticianEnum.POST_ID_NULL.getDesc()));
        }
        if (postLevel == null) {
            return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.POST_LEVEL_ID_NULL.getCode(), ResponseCodeBeauticianEnum.POST_LEVEL_ID_NULL.getDesc()));
        }
        //查询所有的项目
        ResponseResult responseResult = productApi.selectServiceVOListNoPage(keyWordProductCode, keyWordProductName);
        Map mappost = new HashMap();
        mappost.put("postId", postId);
        mappost.put("postLevel", postLevel);
        BeauticianProject beauticianProject = beauticianDao.selectProject(mappost);
        if (beauticianProject != null) {
            JSONArray projects = JSONArray.parseArray(beauticianProject.getProjuects());//1，2
            List<Map> resultList = (List<Map>) responseResult.getResult();
            for (Map map : resultList) {
                map.put("isChecked", 0);
                for (Object item : projects) {
                    if ((Integer) map.get("serviceProductId") == Integer.parseInt(item.toString().trim())) {
                        map.put("isChecked", 1);
                    }
                }
            }

            return ResponseResult.success(resultList);
        }
        return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.POST_NO_PROJECTS.getCode(), ResponseCodeBeauticianEnum.POST_NO_PROJECTS.getDesc()));

    }

    @Override
    public ResponseResult selectGroup(int pageNum, int pageSize, Long storeId, String keyWord) {
        PageHelper.startPage(pageNum, pageSize);
        Group group = new Group();
        group.setStoreId(storeId);
        group.setName(keyWord);
        List<Group> groups = beauticianDao.selectGroup(group);
        //查询所有的员工
        Map map = new HashMap();
        map.put("companyType", "3");
        map.put("companyId", storeId);

        List<Beautician> beauticianList = beauticianDao.selectBeauticianByStoreId(map);
        for (Group groupItem : groups) {
            for (Beautician beauticianItem : beauticianList) {
                if (groupItem.getGroupLeaderId().equals(beauticianItem.getBeauticianId())) {
                    groupItem.setGroupLeaderName(beauticianItem.getName());
                }
            }
        }
        if (groups != null && groups.size() != 0) {
            PageInfo pageInfo = new PageInfo(groups);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.SELECT_GROUP_FAIL.getCode(), ResponseCodeBeauticianEnum.SELECT_GROUP_FAIL.getDesc()));
    }

    @Override
    public ResponseResult addGroup(Group group) {
        //查看门店分组名称是否存在
        int resultInt = beauticianDao.checkGroupName(group);
        if (resultInt > 0) {
            return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.GROUP_NAME_IS_EXIST.getCode(), ResponseCodeBeauticianEnum.GROUP_NAME_IS_EXIST.getDesc()));
        }

        beauticianDao.addGroup(group);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult deleteGroup(Long groupId) {
        //判断是否有美容师在使用此职位
        int resultInt = beauticianDao.checkGroupUse(groupId);
        if (resultInt > 0) {
            return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.DELETE_FAIL_USING_GROUP.getCode(), ResponseCodeBeauticianEnum.DELETE_FAIL_USING_GROUP.getDesc()));
        }
        beauticianDao.deleteGroup(groupId);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult selectPost(int pageNum, int pageSize, String postIndustryIDSearch, String postCategoryId, Long companyId, Long companyType, String postNameKeyword, String departmentId) {
        PageHelper.startPage(pageNum, pageSize);
        Map map = new HashMap();
        map.put("postIndustryIDSearch", postIndustryIDSearch);
        map.put("postCategoryId", postCategoryId);
        map.put("companyId", companyId);
        map.put("companyType", companyType);
        map.put("postNameKeyword", postNameKeyword);
        map.put("departmentId", departmentId);
        List<Post> posts = beauticianDao.selectPost(map);
        List<PostLevel> postLevels = beauticianDao.selectPostLevel(new HashMap());
        List<PostCategory> postCategories = beauticianDao.selectPostCategoryList(new HashMap());
        List<Industry> industries = iIndustryDao.selectList(new HashMap());
        for (Post itemPost : posts) {
            for (PostLevel itemPostLevel : postLevels) {
                if (itemPost.getPostLevel() == itemPostLevel.getPostLevelId()) {
                    itemPost.setPostLevelName(itemPostLevel.getPostLevelName());
                }
            }
        }

        for (Post itemPost : posts) {
            for (PostCategory itemPostCategory : postCategories) {
                if (itemPost.getPostCategoryId() == itemPostCategory.getPostCategoryId()) {
                    itemPost.setPostCategoryName(itemPostCategory.getName());
                }
            }
        }

        for (Post itemPost : posts) {
            for (Industry industry : industries) {
                if (itemPost.getPostIndustryID() == industry.getIndustryID()) {
                    itemPost.setPostIndustryName(industry.getIndustryName());
                }
            }
        }

        if (posts != null && posts.size() != 0) {
            PageInfo pageInfo = new PageInfo(posts);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.SELECT_POST_FAIL.getCode(), ResponseCodeBeauticianEnum.SELECT_POST_FAIL.getDesc()));
    }

    @Override
    public ResponseResult selectPostCategoryNoPage(String postIndustryIDSearch) {
        Map map = new HashMap();
        if (postIndustryIDSearch != null && postIndustryIDSearch.equals("0")) {
            postIndustryIDSearch = null;
        }
        map.put("postIndustryIDSearch", postIndustryIDSearch);
        List<PostCategory> postCategories = beauticianDao.selectPostCategoryList(map);
        List<Industry> mapList = iIndustryDao.selectList(new HashMap());
        for (PostCategory postCategory : postCategories) {
            for (Industry industry : mapList) {
                if (postCategory.getIndustryID() == industry.getIndustryID()) {
                    postCategory.setIndustryName(industry.getIndustryName());
                }
            }
        }
        if (postCategories != null && postCategories.size() != 0) {
            return ResponseResult.success(postCategories);
        }
        return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.SELECT_POST_FAIL.getCode(), ResponseCodeBeauticianEnum.SELECT_POST_FAIL.getDesc()));
    }


    @Override
    public ResponseResult addPost(Post post, String fCreateOrgId, String fUseOrgId) {
        //根据职位分类查看职位行业
        Map map = new HashMap();
        map.put("postCategoryId", post.getPostCategoryId());
        List<PostCategory> postCategoryList = beauticianDao.selectPostCategoryList(map);
        post.setPostIndustryID(postCategoryList.get(0).getIndustryID());
        int i = beauticianDao.addPost(post);
        Long postId = post.getPostId();
        if (i > 0) {
            HashMap<String, String> userNameAndPassword = getUserNameAndPassword(null);
            String dataCentre = userNameAndPassword.get("dataCentre");
            String userName = userNameAndPassword.get("userName");
            String password = userNameAndPassword.get("password");
            Long departmentId = post.getDepartmentId();
            map.put("id", departmentId);
            List<Department> departmentList = departmentDao.selectDepartmentList(map);
            Department department = departmentList.get(0);
            Store store = null;
            if (post.getCompanyType().toString().equals("3")) {
                store = iStoreDao.selectStoreById(post.getCompanyId());
                fCreateOrgId = store.getK3Number();
                fUseOrgId = store.getK3Number();
            }
            ResponseResult result = k3Api.savePost(dataCentre, userName, password, post.getName(), fCreateOrgId, fUseOrgId, department.getK3Number());
            //登录成功----》进行判断保存提交审核是否成功
            if (result.isSuccess()) {
                if (result.getResult().equals("暂未登录")) {
                    return ResponseResult.error(new Error(1, "k3暂未登录"));
                }
                Map resultHashMap = (HashMap<String, Object>) result.getResult();
                Map resulut = (HashMap<String, Object>) resultHashMap.get("Result");
                Map resultStatus = (HashMap<String, Object>) resulut.get("ResponseStatus");
                Boolean isSuccess = (Boolean) resultStatus.get("IsSuccess");
                if (isSuccess) {
                    //获取保存成功后的number和id
                    Integer id = Integer.valueOf(resulut.get("Id").toString());
                    String number = String.valueOf(resulut.get("Number"));
                    post = new Post();
                    post.setPostId(postId);
                    post.setK3Id(id.toString());
                    post.setK3Number(number);
                    beauticianDao.updatePost(post);
                    return ResponseResult.success("k3保存成功！");
                }
            }
        }
        return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.ADD_POST_FAIL.getCode(), ResponseCodeBeauticianEnum.ADD_POST_FAIL.getDesc()));
    }

    @Override
    public ResponseResult deletePost(Long postId) {
        //判断是否有美容师在使用此职位
        int resultInt = beauticianDao.checkPostUse(postId);
        if (resultInt > 0) {
            return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.DELETE_FAIL_USING_POST.getCode(), ResponseCodeBeauticianEnum.DELETE_FAIL_USING_POST.getDesc()));
        }
        beauticianDao.deletePost(postId);
        HashMap<String, String> userNameAndPassword = getUserNameAndPassword(null);
        String dataCentre = userNameAndPassword.get("dataCentre");
        String userName = userNameAndPassword.get("userName");
        String password = userNameAndPassword.get("password");
        Post post = beauticianDao.selectPostById(postId);
//        ResponseResult result = k3Api.savePost(dataCentre, userName, password, post.getName(), fCreateOrgId, fUseOrgId, department.getK3Number());
//        //登录成功----》进行判断保存提交审核是否成功
//        if (result.isSuccess()) {
//            Map resultHashMap = (HashMap<String, Object>) result.getResult();
//            Map resulut = (HashMap<String, Object>) resultHashMap.get("Result");
//            Map resultStatus = (HashMap<String, Object>) resulut.get("ResponseStatus");
//            Boolean isSuccess = (Boolean) resultStatus.get("IsSuccess");
//            if (isSuccess) {
//                //获取保存成功后的number和id
//                Integer id = Integer.valueOf(resulut.get("Id").toString());
//                String number = String.valueOf(resulut.get("Number"));
//                Post post1 = new Post();
//                post1.setPostId(post.getPostId());
//                post1.setK3Id(id);
//                post1.setK3Number(number);
//                beauticianDao.updatePost(post);
//                return ResponseResult.success("添加成功！");
//            }
//        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult updatePost(Post post, String fCreateOrgId, String fUseOrgId) {
        //根据职位查看职位的行业
        Map map = new HashMap();
        map.put("postCategoryId", post.getPostCategoryId());
        PostCategory postCategory = beauticianDao.selectPostCategoryList(map).get(0);
        post.setPostIndustryID(postCategory.getIndustryID());
        int i = beauticianDao.updatePost(post);
        if (post.getCompanyType() != null && i > 0) {
            HashMap<String, String> userNameAndPassword = getUserNameAndPassword(null);
            String dataCentre = userNameAndPassword.get("dataCentre");
            String userName = userNameAndPassword.get("userName");
            String password = userNameAndPassword.get("password");
            Long departmentId = post.getDepartmentId();
            map.put("id", departmentId);
            List<Department> departmentList = departmentDao.selectDepartmentList(map);
            Department department = departmentList.get(0);
            if (post.getCompanyType().toString().equals("3")) {
                Store store = iStoreDao.selectStoreById(post.getCompanyId());
                fCreateOrgId = store.getK3Number();
                fUseOrgId = store.getK3Number();
            }
            ResponseResult result = k3Api.savePost(dataCentre, userName, password, post.getName(), fCreateOrgId, fUseOrgId, department.getK3Number());
            //登录成功----》进行判断保存提交审核是否成功
            if (result.isSuccess()) {
                Map resultHashMap = (HashMap<String, Object>) result.getResult();
                Map resulut = (HashMap<String, Object>) resultHashMap.get("Result");
                Map resultStatus = (HashMap<String, Object>) resulut.get("ResponseStatus");
                Boolean isSuccess = (Boolean) resultStatus.get("IsSuccess");
                if (isSuccess) {
                    //获取保存成功后的number和id
                    Integer id = Integer.valueOf(resulut.get("Id").toString());
                    String number = String.valueOf(resulut.get("Number"));
                    Post post1 = new Post();
                    post1.setPostId(post.getPostId());
                    post1.setK3Id(id.toString());
                    post1.setK3Number(number);
                    beauticianDao.updatePost(post);
                    return ResponseResult.success("添加成功！");
                }
            }
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult updateGroup(Group group) {
        beauticianDao.updateGroup(group);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult selectPostCategoryList(int pageNum, int pageSize, String postCategoryNameKeyword, String postIndustryIDSearch, Long companyType, Long companyId) {
        PageHelper.startPage(pageNum, pageSize);

        Map map = new HashMap();
        map.put("postCategoryNameKeyword", postCategoryNameKeyword);
        map.put("postIndustryIDSearch", postIndustryIDSearch);
        map.put("companyId", companyId);
        List<PostCategory> postCategories = beauticianDao.selectPostCategoryList(map);
        //查看所有行业
        List<Industry> mapList = iIndustryDao.selectList(new HashMap());
        for (PostCategory postCategory : postCategories) {
            for (Industry industry : mapList) {
                if (postCategory.getIndustryID() == industry.getIndustryID()) {
                    postCategory.setIndustryName(industry.getIndustryName());
                }
            }
        }
        if (postCategories != null && postCategories.size() != 0) {
            PageInfo pageInfo = new PageInfo(postCategories);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.SELECT_POST_CATEGORY_FAIL.getCode(), ResponseCodeBeauticianEnum.SELECT_POST_CATEGORY_FAIL.getDesc()));

    }

    @Override
    public ResponseResult selectPostNoPage(String postIndustryIDSearch, String postCategoryId, String companyType, String companyId, String departmentId) {
        Map map = new HashMap();
        map.put("postIndustryIDSearch", postIndustryIDSearch);
        map.put("postCategoryId", postCategoryId);
        map.put("companyType", companyType);
        map.put("companyId", companyId);
        map.put("departmentId", departmentId);
        List<Post> posts = beauticianDao.selectPost(map);
        List<PostLevel> postLevels = beauticianDao.selectPostLevel(new HashMap());
        List<PostCategory> postCategories = beauticianDao.selectPostCategoryList(new HashMap());
        for (Post itemPost : posts) {
            for (PostLevel itemPostLevel : postLevels) {
                if (itemPost.getPostLevel() == itemPostLevel.getPostLevelId()) {
                    itemPost.setPostLevelName(itemPostLevel.getPostLevelName());
                }
            }
        }

        for (Post itemPost : posts) {
            for (PostCategory itemPostCategory : postCategories) {
                if (itemPost.getPostCategoryId() == itemPostCategory.getPostCategoryId()) {
                    itemPost.setPostCategoryName(itemPostCategory.getName());
                }
            }
        }

        if (posts != null && posts.size() != 0) {
            return ResponseResult.success(posts);
        }
        return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.SELECT_POST_FAIL.getCode(), ResponseCodeBeauticianEnum.SELECT_POST_FAIL.getDesc()));
    }

    @Override
    public ResponseResult insertPostCategory(PostCategory postCategory) {
        beauticianDao.insertPostCategory(postCategory);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult updatePostCategory(PostCategory postCategory) {
        beauticianDao.updatePostCategory(postCategory);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult deletePostCategory(PostCategory postCategory) {
        beauticianDao.deletePostCategory(postCategory);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult selectGroupMember(Beautician beautician) {
        List<Beautician> beauticianList = beauticianDao.selectGroupMember(beautician);
        return ResponseResult.success(beauticianList);
    }

    //获取数据中心、用户名和密码
    public HashMap<String, String> getUserNameAndPassword(Long companyId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("dataCentre", null);
        map.put("userName", null);
        map.put("password", null);
        Company company = CompanyDaoImpl.selectCompanyByID(1L);
        if (company != null) {
            String dataCentre = company.getDataCentre();
            String dataCentreUserName = company.getDataCentreUserName();
            String dataCentrePassword = company.getDataCentrePassword();
            map.put("dataCentre", dataCentre);
            map.put("userName", dataCentreUserName);
            map.put("password", dataCentrePassword);
        }
        return map;
    }


}
