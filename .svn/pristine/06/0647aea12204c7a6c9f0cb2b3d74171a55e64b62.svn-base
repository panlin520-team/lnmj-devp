package com.lnmj.data.business.impl;

import com.github.pagehelper.PageInfo;
import com.lnmj.common.Enum.OrderSortStatusEnum;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.data.business.IProductVisitStatisticsService;
import com.lnmj.data.entity.VO.ProductVisitVO;
import com.lnmj.data.repository.IProductVisitStatisticsDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈商品访问量统计〉
 *
 * @Author renqingyun
 * @create 2019/6/6
 */


@Service("productVisitStatisticsService")
public class ProductVisitStatisticsServicelmpl implements IProductVisitStatisticsService {

    @Resource
    private IProductVisitStatisticsDao productVisitStatisticsDao;

    @Override
    public ResponseResult selectProductVisitByList(int pageNum, int pageSize, String keyWord, String userVisitStatus, String visitStatus) {
        Map map = new HashMap<>();
        if (StringUtils.isNotBlank(keyWord)) {
            map.put("keyWord", keyWord);
        }
        if (StringUtils.isNotBlank(userVisitStatus)|| StringUtils.isNotBlank(visitStatus)) {
            //判断商品销量排序
            if (OrderSortStatusEnum.ORDER_BY_ASC.getDesc() == userVisitStatus) {
                map.put("userVisitStatus",OrderSortStatusEnum.ORDER_BY_ASC.getDesc());
            }
            if (OrderSortStatusEnum.ORDER_BY_DESC.getDesc() == userVisitStatus) {
                map.put("userVisitStatus",OrderSortStatusEnum.ORDER_BY_DESC.getDesc());
            }
            //判断商品金额排序
            if (OrderSortStatusEnum.ORDER_BY_ASC.getDesc() == visitStatus) {
                map.put("visitStatus",OrderSortStatusEnum.ORDER_BY_ASC.getDesc());
            }
            if (OrderSortStatusEnum.ORDER_BY_DESC.getDesc() == visitStatus) {
                map.put("visitStatus",OrderSortStatusEnum.ORDER_BY_DESC.getDesc());
            }
        }
        List<ProductVisitVO> list = productVisitStatisticsDao.selectProductVisitByList(map);
        PageInfo pageInfo = new PageInfo(list);
        return ResponseResult.success(pageInfo);
    }
}
