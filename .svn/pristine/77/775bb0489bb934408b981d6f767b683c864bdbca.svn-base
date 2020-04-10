package com.lnmj.store.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lnmj.store.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@ApiModel(value = "返回数据实体")
public class Beautician extends BaseEntity {
    @ApiModelProperty(notes = "美容师id")
    private Long beauticianId;
    @NotBlank(message = "美容师名字能为空")
    @ApiModelProperty(notes = "姓名")
    private String k3Id;
    private String k3Number;
    private String name;
    private String yeWuYuanK3Id;

    @ApiModelProperty(notes = "性别")
    private Integer gender;


    @ApiModelProperty(notes = "是否跳过轮班")
    private Integer isSkipTurn;


    @ApiModelProperty(notes = "在职状态")
    private Integer workingState;


    @ApiModelProperty(notes = "是否兼职")
    private Integer isPartTime;

    /*@Past(message = "入职时间必须为当前时间之前")*/
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd ")
    @ApiModelProperty(notes = "入职时间")
    private String entryTime;//不能为空
    @ApiModelProperty(notes = "部门id")
    private Long departmentId;//不能为空
    @ApiModelProperty(notes = "分组id")
    private Long groupId;//不能为空
    @ApiModelProperty(notes = "职位id")
    private Long postId;//不能为空
    @ApiModelProperty(notes = "职位分类id")
    private Long postCategoryId;//不能为空

    @ApiModelProperty(notes = "兼职职位id")
    private Long partTimePostId;
    @ApiModelProperty(notes = "兼职职位分类id")
    private Long partTimePostCategoryId;

    @ApiModelProperty(notes = "排序")
    private Integer sort;//不能为空
    @ApiModelProperty(notes = "员工等级")
    private Integer gradeId;//不能为空
    private String companyType;//不能为空
    private String companyId;//不能为空
    @ApiModelProperty(notes = "门店id")
    private Long storeId;//不能为空
    @ApiModelProperty(notes = "openId")
    private String openId;//自己生成
    @ApiModelProperty(notes = "员工号")
    private Long staffNumber;//自己生成
    @ApiModelProperty(notes = "技能评分")
    private Integer skillGrade;
    @ApiModelProperty(notes = "手法评分")
    private Integer tactGrade;
    @ApiModelProperty(notes = "累计客户")
    private Integer cumulativeCustomer;
    @ApiModelProperty(notes = "头像")
    private String headUrl;
    @ApiModelProperty(notes = "简介")
    private String introduction;
    @ApiModelProperty(notes = "服务时间")
    private String nursingDate;
    @ApiModelProperty(notes = "电话")
    private String mobile;
    @ApiModelProperty(notes = "是否计算底薪")
    private Integer isBasicSalary;
    private Integer isSaleMan;
    @Transient
    @ApiModelProperty(notes = "职位名称")
    private String postName;
    @Transient
    @ApiModelProperty(notes = "目前预约")
    private List<Map> appointmentList;
    @Transient
    @ApiModelProperty(notes = "分组名称")
    private String groupName;
    @Transient
    private List<String> list;
    @Transient
    private Long postLevel;
    @Transient
    private String postLevelName;
    @Transient
    private String departmentName;
    @Transient
    private String postCategoryName;
    @Transient
    private String parTimePostCategoryName;
    @Transient
    private String parTimePostName;
}