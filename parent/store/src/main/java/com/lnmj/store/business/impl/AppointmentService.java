package com.lnmj.store.business.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.common.Enum.AppointmentStatusEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeAppointmentEnum;
import com.lnmj.common.baseController.SMSController;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.common.utils.EnumUtil;
import com.lnmj.store.business.IAppointmentService;
import com.lnmj.store.entity.*;
import com.lnmj.store.entity.VO.AppointmentTimeVO;
import com.lnmj.store.entity.VO.AppointmentVO;
import com.lnmj.store.repository.IAppointmentDao;
import com.lnmj.store.repository.IBeauticianDao;
import com.lnmj.store.serviceProxy.DataApi;
import com.lnmj.store.serviceProxy.ProductApi;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: yilihua
 * @Date: 2019/5/23 15:32
 * @Description: 预约service
 */
@Transactional
@Service("appointmentService")
public class AppointmentService implements IAppointmentService {
    @Resource
    private IAppointmentDao appointmentDao;
    @Resource
    private SMSController smsController;
    @Resource
    private ProductApi productApi;
    @Resource
    private DataApi dataApi;
    @Resource
    private IBeauticianDao beauticianDao;

    @Transactional
    @Override
    public ResponseResult checkOptionValue(Long userId, Long optionId){
        OptionValue optionValue = new OptionValue();
        optionValue.setUserId(userId);
        optionValue.setOptionId(optionId);
        List<Long> i = appointmentDao.checkOptionValue(optionValue);
        return ResponseResult.success(i);
    }

    @Transactional
    @Override
    public ResponseResult selectOptionValueList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<OptionValue>optionValueList =  appointmentDao.selectOptionValueList();
        if(optionValueList.size()!= 0){
            PageInfo pageInfo = new PageInfo(optionValueList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.NOT_FIND_OPTIONVALUE.getCode(),
                ResponseCodeAppointmentEnum.NOT_FIND_OPTIONVALUE.getDesc()));
    }
    @Transactional
    @Override
    public ResponseResult insertOptionValue(OptionValue optionValue) {
        if(StringUtils.isBlank(optionValue.getModifyOperator())){
            optionValue.setModifyOperator(optionValue.getCreateOperator());
        }
        return ResponseResult.success(appointmentDao.insertOptionValue(optionValue));
    }
    @Transactional
    @Override
    public ResponseResult updateOptionValue(OptionValue optionValue) {
        //判断option是否存在
        return ResponseResult.success(appointmentDao.updateOptionValue(optionValue));
    }
    @Transactional
    @Override
    public ResponseResult deleteOptionValue(Long userOptionId, String modifyOperator) {
        OptionValue optionValue = new OptionValue();
        optionValue.setUserOptionId(userOptionId);
        //判断option是否存在
        optionValue.setModifyOperator(modifyOperator);
        optionValue.setStatus(0);
        return ResponseResult.success(appointmentDao.deleteOptionValue(optionValue));
    }

    @Transactional
    @Override
    public ResponseResult selectOptionList(int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Option> optionList =  appointmentDao.selectOptionList();
        if(optionList.size()!= 0){
            PageInfo pageInfo = new PageInfo(optionList);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.NOT_FIND_OPTION.getCode(),
                ResponseCodeAppointmentEnum.NOT_FIND_OPTION.getDesc()));
    }

    @Transactional
    @Override
    public ResponseResult insertOption(Option option){
        if(StringUtils.isBlank(option.getModifyOperator())){
            option.setModifyOperator(option.getCreateOperator());
        }
       return ResponseResult.success(appointmentDao.insertOption(option));
    }

    @Transactional
    @Override
    public ResponseResult updateOption(Option option){
        //判断option是否存在
        return ResponseResult.success(appointmentDao.updateOption(option));
    }

    @Transactional
    @Override
    public ResponseResult deleteOption(Long optionId, String modifyOperator){
        Option option = new Option();
        option.setOptionId(optionId);
        //判断option是否存在
        option.setModifyOperator(modifyOperator);
        option.setStatus(0);
        return ResponseResult.success(appointmentDao.deleteOption(option));
    }


    private void splitProduct(List<Appointment> appointments){
        //解析商品名称
        for(Appointment appointment:appointments){
            //{[1:洗发],[2:特色禅洗]}
            String product = appointment.getProduct();
            String[] strings = product.trim().replace("{", "").replace("}", "")
                    .trim().split(",");
            String productName = "";
            for(String s:strings){
                String[] split = s.trim().replaceAll("]", "").replace("[", "")
                        .split(":");
                if("".equals(productName)){
                    productName = split[1];
                }else {
                    productName = productName + "," + split[1];
                }
            }
            appointment.setProductName(productName);
            // 2,3,5
            String employee = "";
            String employeeCode = appointment.getEmployeeCode();
            String[] employCodeS = employeeCode.trim().replaceAll("]", "").replace("[", "").split(",");
            for(String employCode:employCodeS){
                Beautician beautician = beauticianDao.selectBeauticianById(Long.valueOf(employCode));
                String name = beautician.getName();
                if("".equals(employee)){
                    employee = name;
                }else {
                    employee = employee + "," + name;
                }
            }
            appointment.setEmployee(employee);
        }
    }

    @Transactional
    @Override
    public ResponseResult selectAppointmentList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Appointment> appointments = appointmentDao.selectAppointmentList();
        splitProduct(appointments);
        if(appointments.size()!= 0){
            PageInfo<Appointment> pageInfo = new PageInfo(appointments);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.NOT_FIND_APPOINTMENT.getCode(),
                ResponseCodeAppointmentEnum.NOT_FIND_APPOINTMENT.getDesc()));
    }
    
    @Transactional
    @Override
    public ResponseResult selectAppointmentByDate(String nursingDate, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = new Date();
        try {
            parse = simpleDateFormat.parse(nursingDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Appointment appointment = new Appointment();
        appointment.setNursingTime(parse);

        List<Appointment> appointments = appointmentDao.selectAppointmentByWhere(appointment);
        splitProduct(appointments);
        if(appointments.size() != 0){
            PageInfo<Appointment> pageInfo = new PageInfo(appointments);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.NOT_FIND_APPOINTMENT.getCode(),
                ResponseCodeAppointmentEnum.NOT_FIND_APPOINTMENT.getDesc()));
    }

    @Transactional
    @Override
    public ResponseResult selectAppointmentByBeautician(String employeeCode, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        Appointment appointment = new Appointment();
        appointment.setEmployeeCode(employeeCode);
        List<Appointment> appointments = appointmentDao.selectAppointmentByWhere(appointment);
        splitProduct(appointments);
        if(appointments.size() != 0){
            PageInfo<Appointment> pageInfo = new PageInfo(appointments);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.NOT_FIND_APPOINTMENT.getCode(),
                ResponseCodeAppointmentEnum.NOT_FIND_APPOINTMENT.getDesc()));
    }

    @Transactional
    @Override
    public ResponseResult selectAppointmentByBeauticianAndDate(String employeeCode, String nursingDate, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = new Date();
        try {
            parse = simpleDateFormat.parse(nursingDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Appointment appointment = new Appointment();
        appointment.setNursingTime(parse);
        appointment.setEmployeeCode(employeeCode);
        List<Appointment> appointments = appointmentDao.selectAppointmentByWhere(appointment);
        splitProduct(appointments);
        if(appointments.size() != 0){
            PageInfo<Appointment> pageInfo = new PageInfo(appointments);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.NOT_FIND_APPOINTMENT.getCode(),
                ResponseCodeAppointmentEnum.NOT_FIND_APPOINTMENT.getDesc()));
    }

    @Transactional
    @Override
    public ResponseResult selectAppointmentByWhere(AppointmentVO appointmentVO, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        //复制属性
        Appointment appointment = new Appointment();
        try {
            BeanUtils.copyProperties(appointment,appointmentVO);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        if(appointmentVO.getNursingDate()!=null){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parse = new Date();
            try {
                parse = simpleDateFormat.parse(appointmentVO.getNursingDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            appointment.setNursingTime(parse);
        }
        List<Appointment> appointments = appointmentDao.selectAppointmentByWhere(appointment);
        splitProduct(appointments);
        if(appointments.size() != 0){
            PageInfo<Appointment> pageInfo = new PageInfo(appointments);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.NOT_FIND_APPOINTMENT.getCode(),
                ResponseCodeAppointmentEnum.NOT_FIND_APPOINTMENT.getDesc()));
    }

    private HashMap<String,String> splitProduct(String product){
        HashMap<String,String> map = new HashMap<>();
        //{[1:洗发],[2:特色禅洗]}
        String[] strings = product.trim().replace("{", "").replace("}", "")
                .trim().split(",");
        for(String s:strings){
            String[] split = s.trim().replaceAll("]", "").replace("[", "")
                    .split(":");
            map.put(split[0],split[1]);
        }
        return map;
    }

    @Transactional
    @Override
    public ResponseResult insertAppointment(AppointmentVO appointmentVO){
        //复制属性
        Appointment appointment = new Appointment();
        try {
            BeanUtils.copyProperties(appointment,appointmentVO);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = new Date();
        try {
            parse = simpleDateFormat.parse(appointmentVO.getNursingDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        appointment.setNursingTime(parse);
        //获取商品
        String product = appointmentVO.getProduct();
        HashMap<String, String> productMap = splitProduct(product);
        //获取商品小类
        List<String> keylist =new ArrayList<>(productMap.keySet());
        System.out.println(keylist.toString());
        ResponseResult productResult = productApi.selectServiceListById(String.join(",", keylist));
        List<HashMap<String,Object>> productList = new ArrayList<>();
        if(productResult!=null && "Success".equals(productResult.getResponseStatusType().getMessage())
            && productResult.getResult()!=null){
            HashMap<String,Object> productR = (HashMap<String,Object>)productResult.getResult();
            productList = (List<HashMap<String,Object>>)productR.get("list");
        }
        ResponseResult subclassResult = dataApi.selectSubclassList();
        List<HashMap<String,Object>> subclassList = new ArrayList<>();
        if(subclassResult!=null && "Success".equals(subclassResult.getResponseStatusType().getMessage())
                && subclassResult.getResult()!=null){
            HashMap<String,Object> subclassR = (HashMap<String,Object>)subclassResult.getResult();
            subclassList = (List<HashMap<String,Object>>)subclassR.get("list");
        }
        //小类获取职位分类
        List<String> postCategoryIDLists = new ArrayList<>();
        if(productList!=null && productList.size()>0){
            for(HashMap<String, Object> map: productList){
                Integer subClassID = (Integer) map.get("subClassID");
                for(HashMap<String,Object> mapSub:subclassList){
                    Integer subclassID = (Integer)mapSub.get("subclassID");
                    if(subClassID.equals(subclassID)){
                        String postCategoryIds = (String) mapSub.get("postCategoryIds");
                        JSONArray postCategoryIdsArray = JSONArray.parseArray(postCategoryIds);
                        for (int i = 0; i < postCategoryIdsArray.size(); i++) {
                            postCategoryIDLists.add(postCategoryIdsArray.getJSONObject(i).getString("postCategoryId"));
                        }
                    }
                }
            }
        }
        //获取员工信息
        Long storeId = appointmentVO.getStoreId();
        String employeeCode = appointmentVO.getEmployeeCode();  //[1,2,3]
        String[] employeeIDList = employeeCode.trim().replaceAll("]", "").replace("[", "")
                .trim().split(",");
        //获取员工职位
        List<String> postCategoryIDListE = new ArrayList<>();

        for(String employeeID:employeeIDList){
            //员工是否被预约
            String duration = appointmentVO.getDuration();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parse);
            calendar.add(GregorianCalendar.MINUTE,Integer.parseInt(duration));
            Date endNursingTime = calendar.getTime();
            HashMap<String ,Object> checkMap= new HashMap<>();
            checkMap.put("employeeID",employeeID);
            checkMap.put("parse",parse);
            checkMap.put("endNursingTime",endNursingTime);
            List<Appointment> appointments = appointmentDao.selectAppointmentByEmployssNursingTime(checkMap);
            if(appointments!=null&&appointments.size()>0){
                return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.APPOINTMENT_BEAUTICIAN_REPEAT.getCode(),
                        ResponseCodeAppointmentEnum.APPOINTMENT_BEAUTICIAN_REPEAT.getDesc()+":"+employeeID));
            }
            Beautician beautician = beauticianDao.selectBeauticianById(Long.valueOf(employeeID));
            //门店
            Long storeIdB = beautician.getStoreId();
            if(!storeId.equals(storeIdB)){
                return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.APPOINTMENT_BEAUTICIAN_STORE_ERROR.getCode(),
                        ResponseCodeAppointmentEnum.APPOINTMENT_BEAUTICIAN_STORE_ERROR.getDesc()));
            }
            String postId = beautician.getPostId().toString();
            Post post = beauticianDao.selectPostById(Long.parseLong(postId));
            postCategoryIDListE.add(post.getPostCategoryId().toString());
        }

        //去重
        Set set = new HashSet();
        set.addAll(postCategoryIDLists);
        postCategoryIDLists.clear();
        postCategoryIDLists.addAll(set);
        set.clear();
        set.addAll(postCategoryIDListE);
        postCategoryIDListE.clear();
        postCategoryIDListE.addAll(set);
        //判断小类职位分类是否全部有员工
        if(!postCategoryIDLists.containsAll(postCategoryIDListE)){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.APPOINTMENT_PRODUCT_ERROR.getCode(),
                    ResponseCodeAppointmentEnum.APPOINTMENT_PRODUCT_ERROR.getDesc()));
        }
        if(postCategoryIDListE.size()!=postCategoryIDLists.size()){
            //所需职位员工没有完全选择
            boolean b = postCategoryIDLists.removeAll(postCategoryIDListE);
            if(b){
                List<Beautician> beauticianList = new ArrayList<>();
                //获取职位分类上的员工
                for(String postCategoryID:postCategoryIDLists){
                    //职位
                    List<Long> postList = beauticianDao.selectPostByCategoryId(Long.parseLong(postCategoryID));
                    for (Long postID : postList) {
                        //员工
                        Beautician beautician = new Beautician();
                        beautician.setStoreId(storeId);
                        beautician.setPostId(Long.valueOf(postID));
                        beauticianList.addAll(beauticianDao.selectBeauticiantList(beautician));
                    }
                }
                return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.APPOINTMENT_BEAUTICIAN_ERROR.getCode(),
                        ResponseCodeAppointmentEnum.APPOINTMENT_BEAUTICIAN_ERROR.getDesc()+",请选择:"+beauticianList));
            }
        }
        //预约状态
        appointment.setAppointmentStatus(AppointmentStatusEnum.APPOINTMENT.getCode());
        //修改人
        appointment.setModifyOperator(appointmentVO.getCreateOperator());
        appointmentDao.insertAppointment(appointment);
        return ResponseResult.success(appointment);
    }

    @Transactional
    @Override
    public ResponseResult updateAppointment(AppointmentVO appointmentVO) {
        //判断预约是否存在
        Appointment appointmentTemp = new Appointment();
        appointmentTemp.setAppointmentId(appointmentVO.getAppointmentId());
        List<Appointment> appointments = appointmentDao.selectAppointmentByWhere(appointmentTemp);
        if(appointments.size()==0){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.NOT_FIND_APPOINTMENT.getCode(),
                    ResponseCodeAppointmentEnum.NOT_FIND_APPOINTMENT.getDesc()));
        }
        //复制属性
        try {
            BeanUtils.copyProperties(appointmentTemp,appointmentVO);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        if(appointmentVO.getNursingDate()!=null){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date parse = new Date();
            try {
                parse = simpleDateFormat.parse(appointmentVO.getNursingDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            appointmentTemp.setNursingTime(parse);
        }
        appointmentDao.updateAppointment(appointmentTemp);
        return ResponseResult.success(appointmentTemp);
    }

    @Transactional
    @Override
    public ResponseResult pushAppointment(Long appointmentId, Integer appointmentStatus, String modifyOperator) {
        //判断预约是否存在
        Appointment appointmentTemp = new Appointment();
        appointmentTemp.setAppointmentId(appointmentId);
        List<Appointment> appointments = appointmentDao.selectAppointmentByWhere(appointmentTemp);
        if(appointments.size()==0){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.NOT_FIND_APPOINTMENT.getCode(),
                    ResponseCodeAppointmentEnum.NOT_FIND_APPOINTMENT.getDesc()));
        }
        appointmentTemp.setModifyOperator(modifyOperator);
        //根据预约状态判断操作
        if(appointmentStatus.compareTo(AppointmentStatusEnum.APPOINTMENT.getCode())==0){
            //TODO..发送预约消息给顾客
/*            String phone = "17740188684";
            String message = getMessage();
            ResponseResult responseResult = ResponseResult.error(null);
            try {
                responseResult = smsController.sendMessage(phone, message);
            } catch (Exception e) {
                e.printStackTrace();
            }
            HashMap result = (HashMap)responseResult.getResult();
//            header  statusCode  result
//            result.get("header");
//            result.get("statusCode");
//            result.get("result");
*/
            appointmentTemp.setPushAppoinmentStatus(true);
            appointmentDao.updateAppointment(appointmentTemp);
            return ResponseResult.success(appointmentTemp); // result
        }
        if(appointmentStatus.compareTo(AppointmentStatusEnum.PAY.getCode())==0){
            //TODO..发送支付消息给顾客
            appointmentTemp.setPushPayStatus(true);
            appointmentDao.updateAppointment(appointmentTemp);
            return ResponseResult.success(appointmentTemp);
        }
        return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.PUSH_FAILD.getCode(),
                ResponseCodeAppointmentEnum.PUSH_FAILD.getDesc()));
    }

    private String getMessage() {
        String code = new Random().nextInt(1000000) + "";
        if (code.length() != 6) {
            return getMessage();//不够6位，再次调用自己的方法，递归
        } else {
//            return "【俪凝美聚】"+code;
            return "验证码:"+code;
        }
    }

    @Transactional
    @Override
    public ResponseResult cancelAppointment(Long appointmentId, String modifyOperator) {
        return deleteAppointment(appointmentId,modifyOperator);
    }

    @Override
    public ResponseResult selectAppointmentTimeByDateAndEmployeeAndStatus(String employeeCode, String nursingDate, Integer appointmentStatus) {
        //状态是否有误
        Map<Integer, String> enumToMap = EnumUtil.getEnumToMap(AppointmentStatusEnum.class);
        if(!enumToMap.containsKey(appointmentStatus)){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.APPOINTMENTSTATUS_ERROR.getCode(),
                    ResponseCodeAppointmentEnum.APPOINTMENTSTATUS_ERROR.getDesc()));
        }
        //解析时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //获取当前时间
        Date date = new Date();
        Date parse;
        try {
            parse = sdf.parse(nursingDate);
        } catch (ParseException e) {
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.APPOINTMENT_DATE_ERROR.getCode(),
                    ResponseCodeAppointmentEnum.APPOINTMENT_DATE_ERROR.getDesc()));
        }
        int i = parse.compareTo(date);
        if(i<=0){   //小于的日期，返回当日的
            Calendar calendar = Calendar.getInstance();
            //前一天日期
            calendar.setTime(date);
            calendar.add(Calendar.DATE,-1);
            Date yesterday = calendar.getTime();
            if(parse.compareTo(yesterday)<=0){
                return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.APPOINTMENT_DATE_OVERDUE.getCode(),
                        ResponseCodeAppointmentEnum.APPOINTMENT_DATE_OVERDUE.getDesc()));
            }else{
               parse = date;
            }
        }
        HashMap<String,Object> selectMap = new HashMap();
        selectMap.put("employeeCode",employeeCode);
        selectMap.put("nursingDate",parse);
        selectMap.put("appointmentStatus",appointmentStatus);
        List<AppointmentTimeVO> appointmentTimeVOList = appointmentDao.
                selectAppointmentTimeByDateAndEmpAndSta(selectMap);
        if(appointmentTimeVOList!=null && appointmentTimeVOList.size()>0){
            for(AppointmentTimeVO appointmentTimeVO:appointmentTimeVOList){
                List<String> nusingTime = new ArrayList<>();
                //截取时间
                Date nursingTime = appointmentTimeVO.getNursingTime();
                Date nursingEndTime = appointmentTimeVO.getNursingEndTime();
                //获取时,分
                int hoursN = nursingTime.getHours();
                int minutesN = nursingTime.getMinutes();
                int hoursE = nursingEndTime.getHours();
                int minutesE = nursingEndTime.getMinutes();
                if(hoursN == hoursE){
                    if(minutesE>=minutesN){
                        if(0 == minutesN){
                            nusingTime.add(hoursN+":"+"00");
                            if(30 <= minutesE && minutesE <60){
                                nusingTime.add(hoursN+":"+"30");
                            }
                        }
                        if(0<minutesN && minutesN <=30){
                            nusingTime.add(hoursN+":"+"30");
                        }
                    }
                }else if(hoursN < hoursE){
                    while (hoursN <= hoursE){
                        if(hoursN == hoursE){
                            nusingTime.add(hoursN+":"+"00");
                            if(30 <= minutesE && minutesE < 60){
                                nusingTime.add(hoursN+":"+"30");
                            }
                        }else {
                            nusingTime.add(hoursN + ":" + "00");
                            nusingTime.add(hoursN + ":" + "30");
                        }
                        hoursN++;
                    }
                }
                appointmentTimeVO.setNusingTime(nusingTime);
            }
            return ResponseResult.success(appointmentTimeVOList);
        }
        return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.NOT_FIND_APPOINTMENT.getCode(),
                ResponseCodeAppointmentEnum.NOT_FIND_APPOINTMENT.getDesc()));
    }

    @Transactional
    @Override
    public ResponseResult deleteAppointment(Long appointmentId, String modifyOperator) {
        //判断预约是否存在
        Appointment appointmentTemp = new Appointment();
        appointmentTemp.setAppointmentId(appointmentId);
        List<Appointment> appointments = appointmentDao.selectAppointmentByWhere(appointmentTemp);
        if(appointments.size()==0){
            return ResponseResult.error(new Error(ResponseCodeAppointmentEnum.NOT_FIND_APPOINTMENT.getCode(),
                    ResponseCodeAppointmentEnum.NOT_FIND_APPOINTMENT.getDesc()));
        }
        appointmentTemp.setModifyOperator(modifyOperator);
//        appointmentTemp.setStatus(0);//设置预约删除
        appointmentDao.deleteAppointment(appointmentTemp);
        return ResponseResult.success(appointmentTemp);
    }

}
