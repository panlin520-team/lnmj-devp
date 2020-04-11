package com.lnmj.store.business.impl;

import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodeBeauticianEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.store.business.IOrderworkService;
import com.lnmj.store.entity.Beautician;
import com.lnmj.store.entity.Orderwork;
import com.lnmj.store.repository.IBeauticianDao;
import com.lnmj.store.repository.IOrderworkDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 〈〉
 *
 * @Author renqingyun
 * @create 2019/5/8
 */
@Transactional
@Service("orderworkService")
public class OrderWorkService implements IOrderworkService {
    @Resource
    private IOrderworkDao orderworkDao;
    @Resource
    private IBeauticianDao beauticianDao;


    @Override
    public ResponseResult addOrderwork(Long beauticianId,Date orderworkDate,String allTimeNodes,String busyTimeNodes,String restTimeNodes) {
        if (beauticianId==null){
            return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.BEAUTICIAN_ID_NULL.getCode(), ResponseCodeBeauticianEnum.BEAUTICIAN_ID_NULL.getDesc()));
        }
        if (orderworkDate==null){
            return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.ORDERWORKDATE_NULL.getCode(), ResponseCodeBeauticianEnum.ORDERWORKDATE_NULL.getDesc()));
        }
        if (allTimeNodes==null||busyTimeNodes==null||restTimeNodes==null){
            return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.TIMENODES_NULL.getCode(), ResponseCodeBeauticianEnum.TIMENODES_NULL.getDesc()));
        }
        //先查看此美容师是否有排班
        Orderwork orderworkForSelect = new Orderwork();
        orderworkForSelect.setBeauticianId(beauticianId);
        orderworkForSelect.setOrderworkDate(orderworkDate);
        int resultInt = orderworkDao.checkOrderwork(orderworkForSelect);
        if (resultInt>0){
            //如果已经有排班了
            return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.ORDERWORK_EXIST.getCode(), ResponseCodeBeauticianEnum.ORDERWORK_EXIST.getDesc()));
        }

        List<String> allTimeNodesList = Arrays.asList(allTimeNodes.split(","));
        allTimeNodesList = new ArrayList<>(allTimeNodesList);
        List<String> busyTimeNodesList = null;
        if (busyTimeNodes!=null){
            if (busyTimeNodes.indexOf(",")>0){
                busyTimeNodesList = Arrays.asList(busyTimeNodes.split(","));
            }else{
                busyTimeNodesList.add(busyTimeNodes);
            }
            allTimeNodesList.removeAll(busyTimeNodesList);
        }

        List<String> restTimeNodesList = null;
        if (restTimeNodes!=null){
            if (restTimeNodes.indexOf(",")>0){
                restTimeNodesList = Arrays.asList(restTimeNodes.split(","));
            }else{
                restTimeNodesList.add(restTimeNodes);
            }
            allTimeNodesList.removeAll(restTimeNodesList);
        }
        List<String> leisureTimeNodes = allTimeNodesList;
        Orderwork orderwork= new Orderwork();
        orderwork.setBeauticianId(beauticianId);
        orderwork.setOrderworkDate(orderworkDate);
        orderwork.setLeisureTimeNodes(leisureTimeNodes.toString());
        orderwork.setBusyTimeNodes(busyTimeNodesList.toString());
        orderwork.setRestTimeNodes(restTimeNodesList.toString());
        int resultIntAdd =  orderworkDao.addOrderwork(orderwork);
        if (resultIntAdd<=0){
            return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.ORDER_WORK_FAIL.getCode(), ResponseCodeBeauticianEnum.ORDER_WORK_FAIL.getDesc()));
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult selectOrderwork(Long beauticianId, Date orderworkDate) {
        if (beauticianId==null){
            return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.BEAUTICIAN_ID_NULL.getCode(), ResponseCodeBeauticianEnum.BEAUTICIAN_ID_NULL.getDesc()));
        }
        if (orderworkDate==null){
            return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.ORDERWORKDATE_NULL.getCode(), ResponseCodeBeauticianEnum.ORDERWORKDATE_NULL.getDesc()));
        }
        Orderwork orderworkForSelect = new Orderwork();
        orderworkForSelect.setBeauticianId(beauticianId);
        orderworkForSelect.setOrderworkDate(orderworkDate);
        Orderwork orderwork =  orderworkDao.selectOrderwork(orderworkForSelect);
        if (orderwork==null){
            return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.ORDERWORK_NULL.getCode(), ResponseCodeBeauticianEnum.ORDERWORK_NULL.getDesc()));
        }
        return ResponseResult.success(orderwork);
    }

    @Override
    public ResponseResult updateOrderwork(Long beauticianId, Date orderworkDate, String leisureTimeNodes,String busyTimeNodes, String restTimeNodes) {
        if (beauticianId==null){
            return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.BEAUTICIAN_ID_NULL.getCode(), ResponseCodeBeauticianEnum.BEAUTICIAN_ID_NULL.getDesc()));
        }
        if (orderworkDate==null){
            return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.ORDERWORKDATE_NULL.getCode(), ResponseCodeBeauticianEnum.ORDERWORKDATE_NULL.getDesc()));
        }
        if (leisureTimeNodes==null||busyTimeNodes==null||restTimeNodes==null){
            return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.TIMENODES_NULL.getCode(), ResponseCodeBeauticianEnum.TIMENODES_NULL.getDesc()));
        }
        Orderwork orderworkForAdd = new Orderwork();
        orderworkForAdd.setBeauticianId(beauticianId);
        orderworkForAdd.setOrderworkDate(orderworkDate);
        orderworkForAdd.setLeisureTimeNodes(leisureTimeNodes);
        orderworkForAdd.setBusyTimeNodes(busyTimeNodes);
        orderworkForAdd.setRestTimeNodes(restTimeNodes);
        //先查看此美容师是否有排班
        Orderwork orderworkForSelect = new Orderwork();
        orderworkForSelect.setBeauticianId(beauticianId);
        orderworkForSelect.setOrderworkDate(orderworkDate);
        int resultInt = orderworkDao.checkOrderwork(orderworkForSelect);
        if (resultInt<=0){
            //如果没有
            orderworkDao.addOrderwork(orderworkForAdd);
        }else{
            //如果有，就先删除再添加
            orderworkDao.deleteOrderwork(orderworkForSelect);
            orderworkDao.addOrderwork(orderworkForAdd);
        }
        return ResponseResult.success((Orderwork)(this.selectOrderwork(beauticianId,orderworkDate).getResult()));
    }

    @Override
    public ResponseResult deleteOrderwork(Long beauticianId, Date orderworkDate) {
        if (beauticianId==null){
            return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.BEAUTICIAN_ID_NULL.getCode(), ResponseCodeBeauticianEnum.BEAUTICIAN_ID_NULL.getDesc()));
        }
        if (orderworkDate==null){
            return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.ORDERWORKDATE_NULL.getCode(), ResponseCodeBeauticianEnum.ORDERWORKDATE_NULL.getDesc()));
        }


        Orderwork orderworkForSelect = new Orderwork();
        orderworkForSelect.setBeauticianId(beauticianId);
        orderworkForSelect.setOrderworkDate(orderworkDate);
        int resultInt = orderworkDao.checkOrderwork(orderworkForSelect);
        if (resultInt<=0){
            //如果没有
         return ResponseResult.error(new Error(ResponseCodeBeauticianEnum.ORDERWORK_NULL_CANNOT_DELETE.getCode(), ResponseCodeBeauticianEnum.ORDERWORK_NULL_CANNOT_DELETE.getDesc()));

        }
        Orderwork orderworkFordelete = new Orderwork();
        orderworkFordelete.setBeauticianId(beauticianId);
        orderworkFordelete.setOrderworkDate(orderworkDate);
        orderworkDao.deleteOrderwork(orderworkFordelete);

        return ResponseResult.success();
    }

    @Override
    public ResponseResult selectOrderworkAll(Long storeId) {
        //查看此店铺的所有美容师list
        Map map1 = new HashMap();
        map1.put("companyId",storeId);
        map1.put("companyType","3");
        List<Beautician> beauticians = beauticianDao.selectBeauticianByStoreId(map1);
        List<Long> beauticianIds = new ArrayList<>();
        for (Beautician item:beauticians){
            beauticianIds.add(item.getBeauticianId());
        }
        Map map = new HashMap();
        map.put("list",beauticianIds);
        List<Orderwork> orderworks = orderworkDao.selectOrderworkAll(map);

        return ResponseResult.success(orderworks);
    }


}
