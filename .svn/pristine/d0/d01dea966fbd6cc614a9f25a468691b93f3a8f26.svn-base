package com.lnmj.data.business.impl;


import com.github.pagehelper.PageInfo;
import com.lnmj.common.Enum.OrderSortStatusEnum;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.data.business.IProductStatisticsService;
import com.lnmj.data.entity.VO.ProductStatisticsVO;
import com.lnmj.data.repository.IProductStatisticsDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: yilihua
 * @Date: 2019/5/6 18:28
 * @Description: 商品品牌service
 */
@Service("productStatisticsService")
public class ProductStatisticsServicelmpl implements IProductStatisticsService {
    @Resource
    private IProductStatisticsDao productStatisticsDao;


    @Override
    public ResponseResult selectByList(int pageNum, int pageSize, String keyWord, String time, String saleStatus, String amountStatus) {
        Map map = new HashMap<>();
        if (StringUtils.isNotBlank(keyWord)) {
            map.put("keyWord", keyWord);
        }
        if (StringUtils.isNotBlank(time)) {
            map.put("time", time);
        }
        if (StringUtils.isNotBlank(saleStatus)|| StringUtils.isNotBlank(amountStatus)) {
            //判断商品销量排序
            if (OrderSortStatusEnum.ORDER_BY_ASC.getDesc() == saleStatus) {
                map.put("salaStatus",OrderSortStatusEnum.ORDER_BY_ASC.getDesc());
            }
            if (OrderSortStatusEnum.ORDER_BY_DESC.getDesc() == saleStatus) {
                map.put("salaStatus",OrderSortStatusEnum.ORDER_BY_DESC.getDesc());
            }
            //判断商品金额排序
            if (OrderSortStatusEnum.ORDER_BY_ASC.getDesc() == amountStatus) {
                map.put("amountStatus",OrderSortStatusEnum.ORDER_BY_ASC.getDesc());
            }
            if (OrderSortStatusEnum.ORDER_BY_DESC.getDesc() == amountStatus) {
                map.put("amountStatus",OrderSortStatusEnum.ORDER_BY_DESC.getDesc());
            }
        }
        List<ProductStatisticsVO> list = productStatisticsDao.selectByList(map);
        PageInfo pageInfo = new PageInfo(list);
        return ResponseResult.success(pageInfo);
    }
}
