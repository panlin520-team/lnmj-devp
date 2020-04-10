package com.lnmj.store.business.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lnmj.common.Enum.OrderTypeEnum;
import com.lnmj.common.Enum.PresentationStatusEnum;
import com.lnmj.common.Enum.ResponseEnum.errorMsgEnum.ResponseCodePresentationEnum;
import com.lnmj.common.response.Error;
import com.lnmj.common.response.ResponseResult;
import com.lnmj.common.utils.MemberUtil;
import com.lnmj.store.business.IPresentationService;
import com.lnmj.store.entity.Presentation;
import com.lnmj.store.repository.IPresentationDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

/**
 * @Author: yilihua
 * @Date: 2019/5/22 11:29
 * @Description: 赠送service
 */
@Transactional
@Service("presentationService")
public class PresentationService implements IPresentationService {
    @Resource
    private IPresentationDao presentationDao;
    /**
    *@Description 分页显示
    *@Param [pageNum, pageSize]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/5/22
    *@Time 14:34
    */
    @Transactional
    @Override
    public ResponseResult selectPresentationList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Presentation> presentations = presentationDao.selectPresentationList();
        if(presentations.size()!=0){
            PageInfo pageInfo = new PageInfo(presentations);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodePresentationEnum.PRESENTATION_NOT_FIND.getCode(),
                ResponseCodePresentationEnum.PRESENTATION_NOT_FIND.getDesc()));
    }
    /**
    *@Description 条件查询
    *@Param [pageNum, pageSize, presentation]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/5/22
    *@Time 14:34
    */
    @Transactional
    @Override
    public ResponseResult selectPresentationByWhere(int pageNum, int pageSize, Presentation presentation) {
        PageHelper.startPage(pageNum,pageSize);
        List<Presentation> presentations = presentationDao.selectPresentationByWhere(presentation);
        if(presentations.size()!=0){
            PageInfo pageInfo = new PageInfo(presentations);
            return ResponseResult.success(pageInfo);
        }
        return ResponseResult.error(new Error(ResponseCodePresentationEnum.PRESENTATION_NOT_FIND.getCode(),
                ResponseCodePresentationEnum.PRESENTATION_NOT_FIND.getDesc()));
    }
    /**
    *@Description 删除赠送
    *@Param [presentationId]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/5/22
    *@Time 14:35
    */
    @Transactional
    @Override
    public ResponseResult deletePresentation(Long presentationId, String modifyOperator) {
        //查询赠送是否存在
        Presentation presentationTemp = new Presentation();
        presentationTemp.setPresentationId(presentationId);
        List<Presentation> presentations = presentationDao.selectPresentationByWhere(presentationTemp);
        if(presentations.size()==0){
            //不存在直接返回不存在赠送记录
            return ResponseResult.error(new Error(ResponseCodePresentationEnum.PRESENTATION_NOT_FIND.getCode(),
                    ResponseCodePresentationEnum.PRESENTATION_NOT_FIND.getDesc()+presentationId+
                            "无法完成预约。"));
        }
        Presentation presentation = new Presentation();
        presentation.setPresentationId(presentationId);
        presentation.setModifyOperator(modifyOperator);
        return ResponseResult.success(presentationDao.deletePresentation(presentation));
    }
    /**
    *@Description 添加赠送
    *@Param [presentation]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/5/22
    *@Time 14:33
    */
    @Transactional
    @Override
    public ResponseResult insertPresentation(Presentation presentation) {
        //赠送时间
        if(presentation.getPresentationTime()==null){
            presentation.setPresentationTime(new Timestamp(System.currentTimeMillis()));
        }
        //业务状态
        presentation.setPresentationStatus(PresentationStatusEnum.ADD.getCode());
        //创建人
        if(StringUtils.isBlank(presentation.getCreateOperator())){
            presentation.setCreateOperator(presentation.getOrderLink());
        }
        //修改人
        presentation.setModifyOperator(presentation.getCreateOperator());
        //插入数据
        presentationDao.insertPresentation(presentation);
        return ResponseResult.success(presentation);
    }
    /**
    *@Description 预约
    *@Param [presentation]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/5/22
    *@Time 14:36
    */
    @Transactional
    @Override
    public ResponseResult appointmentPresentation(Presentation presentation) {
        //查询赠送是否存在
        Presentation presentationTemp = new Presentation();
        presentationTemp.setPresentationId(presentation.getPresentationId());
        List<Presentation> presentations = presentationDao.selectPresentationByWhere(presentationTemp);
        if(presentations.size()==0){
            //不存在直接返回不存在赠送记录
            return ResponseResult.error(new Error(ResponseCodePresentationEnum.PRESENTATION_NOT_FIND.getCode(),
                    ResponseCodePresentationEnum.PRESENTATION_NOT_FIND.getDesc()+presentation.getPresentationId()+
                    "无法完成预约。"));
        }
        //添加赠送后才能预约
        int i = PresentationStatusEnum.ADD.getCode().compareTo(presentations.get(0).getPresentationStatus());
        if( i !=0 ){
            return ResponseResult.error(new Error(ResponseCodePresentationEnum.FAILD_APPOINTMENT.getCode(),
                    ResponseCodePresentationEnum.FAILD_APPOINTMENT.getDesc()));
        }
        //业务状态
        presentation.setPresentationStatus(PresentationStatusEnum.APPOINTMENT.getCode());
        return ResponseResult.success(presentationDao.updatePresentation(presentation));
    }
    /**
    *@Description 退货
    *@Param [presentation]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/5/22
    *@Time 15:23
    */
    @Transactional
    @Override
    public ResponseResult returnPresentation(Presentation presentation) {
        //查询赠送是否存在
        Presentation presentationTemp = new Presentation();
        presentationTemp.setPresentationId(presentation.getPresentationId());
        List<Presentation> presentations = presentationDao.selectPresentationByWhere(presentationTemp);
        if(presentations.size()==0){
            //不存在直接返回不存在赠送记录
            return ResponseResult.error(new Error(ResponseCodePresentationEnum.PRESENTATION_NOT_FIND.getCode(),
                    ResponseCodePresentationEnum.PRESENTATION_NOT_FIND.getDesc()+presentation.getPresentationId()+
                            "无法完成退货。"));
        }
        //预约后才能确认、取消、退货
        int i = PresentationStatusEnum.APPOINTMENT.getCode().compareTo(presentations.get(0).getPresentationStatus());
        if( i !=0 ){
            return ResponseResult.error(new Error(ResponseCodePresentationEnum.FAILD_RETURN.getCode(),
                    ResponseCodePresentationEnum.FAILD_RETURN.getDesc()));
        }
        //退货会删除预约的数据
        //业务状态
        presentation.setPresentationStatus(PresentationStatusEnum.RETURN.getCode());
        return ResponseResult.success(presentationDao.returnPresentation(presentation));
    }
    /**
    *@Description 确认
    *@Param [presentation]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/5/22
    *@Time 15:58
    */
    @Transactional
    @Override
    public ResponseResult confirmPresentation(Presentation presentation) {
        //查询赠送是否存在
        Presentation presentationTemp = new Presentation();
        presentationTemp.setPresentationId(presentation.getPresentationId());
        List<Presentation> presentations = presentationDao.selectPresentationByWhere(presentationTemp);
        if(presentations.size()==0){
            //不存在直接返回不存在赠送记录
            return ResponseResult.error(new Error(ResponseCodePresentationEnum.PRESENTATION_NOT_FIND.getCode(),
                    ResponseCodePresentationEnum.PRESENTATION_NOT_FIND.getDesc()+presentation.getPresentationId()+
                            "无法完成退货。"));
        }
        //预约后才能确认、取消、退货
        int i = PresentationStatusEnum.APPOINTMENT.getCode().compareTo(presentations.get(0).getPresentationStatus());
        if( i !=0 ){
            return ResponseResult.error(new Error(ResponseCodePresentationEnum.FAILD_CONFIRM.getCode(),
                    ResponseCodePresentationEnum.FAILD_CONFIRM.getDesc()));
        }
        //业务状态
        presentation.setPresentationStatus(PresentationStatusEnum.CONFIRM.getCode());
        return ResponseResult.success(presentationDao.updatePresentation(presentation));
    }
    /**
    *@Description 取消
    *@Param [presentation]
    *@Return com.lnmj.common.response.ResponseResult
    *@Author yilihua
    *@Date 2019/5/22
    *@Time 15:59
    */
    @Transactional
    @Override
    public ResponseResult cancelPresentation(Presentation presentation) {
        //查询赠送是否存在
        Presentation presentationTemp = new Presentation();
        presentationTemp.setPresentationId(presentation.getPresentationId());
        List<Presentation> presentations = presentationDao.selectPresentationByWhere(presentationTemp);
        if(presentations.size()==0){
            //不存在直接返回不存在赠送记录
            return ResponseResult.error(new Error(ResponseCodePresentationEnum.PRESENTATION_NOT_FIND.getCode(),
                    ResponseCodePresentationEnum.PRESENTATION_NOT_FIND.getDesc()+presentation.getPresentationId()+
                            "无法完成退货。"));
        }
        //预约后才能确认、取消、退货
        int i = PresentationStatusEnum.APPOINTMENT.getCode().compareTo(presentations.get(0).getPresentationStatus());
        if( i !=0 ){
            return ResponseResult.error(new Error(ResponseCodePresentationEnum.FAILD_CANCEL.getCode(),
                    ResponseCodePresentationEnum.FAILD_CANCEL.getDesc()));
        }
        //业务状态
        presentation.setPresentationStatus(PresentationStatusEnum.CANCEL.getCode());
        return ResponseResult.success(presentationDao.updatePresentation(presentation));
    }

    @Override
    public ResponseResult selectPresentationEnumName(String name) {
        if ("OrderTypeEnum".equals(name)) {
            return ResponseResult.success(MemberUtil.getEnumToMap(OrderTypeEnum.class));
        }
        if ("PresentationStatusEnum".equals(name)) {
            return ResponseResult.success(MemberUtil.getEnumToMap(PresentationStatusEnum.class));
        }
        return ResponseResult.error(new Error(ResponseCodePresentationEnum.ENUM_NAME_ERROR.getCode(),
                ResponseCodePresentationEnum.ENUM_NAME_ERROR.getDesc()));
    }
}
