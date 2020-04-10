package com.lnmj.common.generator.custom_project_user_refuse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomProjectUserRefuseExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CustomProjectUserRefuseExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("Id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("Id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("Id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("Id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("Id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("Id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("Id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("Id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("Id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("Id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("Id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("Id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andCustomProjectUserIdIsNull() {
            addCriterion("CustomProjectUserId is null");
            return (Criteria) this;
        }

        public Criteria andCustomProjectUserIdIsNotNull() {
            addCriterion("CustomProjectUserId is not null");
            return (Criteria) this;
        }

        public Criteria andCustomProjectUserIdEqualTo(Long value) {
            addCriterion("CustomProjectUserId =", value, "customProjectUserId");
            return (Criteria) this;
        }

        public Criteria andCustomProjectUserIdNotEqualTo(Long value) {
            addCriterion("CustomProjectUserId <>", value, "customProjectUserId");
            return (Criteria) this;
        }

        public Criteria andCustomProjectUserIdGreaterThan(Long value) {
            addCriterion("CustomProjectUserId >", value, "customProjectUserId");
            return (Criteria) this;
        }

        public Criteria andCustomProjectUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("CustomProjectUserId >=", value, "customProjectUserId");
            return (Criteria) this;
        }

        public Criteria andCustomProjectUserIdLessThan(Long value) {
            addCriterion("CustomProjectUserId <", value, "customProjectUserId");
            return (Criteria) this;
        }

        public Criteria andCustomProjectUserIdLessThanOrEqualTo(Long value) {
            addCriterion("CustomProjectUserId <=", value, "customProjectUserId");
            return (Criteria) this;
        }

        public Criteria andCustomProjectUserIdIn(List<Long> values) {
            addCriterion("CustomProjectUserId in", values, "customProjectUserId");
            return (Criteria) this;
        }

        public Criteria andCustomProjectUserIdNotIn(List<Long> values) {
            addCriterion("CustomProjectUserId not in", values, "customProjectUserId");
            return (Criteria) this;
        }

        public Criteria andCustomProjectUserIdBetween(Long value1, Long value2) {
            addCriterion("CustomProjectUserId between", value1, value2, "customProjectUserId");
            return (Criteria) this;
        }

        public Criteria andCustomProjectUserIdNotBetween(Long value1, Long value2) {
            addCriterion("CustomProjectUserId not between", value1, value2, "customProjectUserId");
            return (Criteria) this;
        }

        public Criteria andRefuseTimesIsNull() {
            addCriterion("RefuseTimes is null");
            return (Criteria) this;
        }

        public Criteria andRefuseTimesIsNotNull() {
            addCriterion("RefuseTimes is not null");
            return (Criteria) this;
        }

        public Criteria andRefuseTimesEqualTo(Integer value) {
            addCriterion("RefuseTimes =", value, "refuseTimes");
            return (Criteria) this;
        }

        public Criteria andRefuseTimesNotEqualTo(Integer value) {
            addCriterion("RefuseTimes <>", value, "refuseTimes");
            return (Criteria) this;
        }

        public Criteria andRefuseTimesGreaterThan(Integer value) {
            addCriterion("RefuseTimes >", value, "refuseTimes");
            return (Criteria) this;
        }

        public Criteria andRefuseTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("RefuseTimes >=", value, "refuseTimes");
            return (Criteria) this;
        }

        public Criteria andRefuseTimesLessThan(Integer value) {
            addCriterion("RefuseTimes <", value, "refuseTimes");
            return (Criteria) this;
        }

        public Criteria andRefuseTimesLessThanOrEqualTo(Integer value) {
            addCriterion("RefuseTimes <=", value, "refuseTimes");
            return (Criteria) this;
        }

        public Criteria andRefuseTimesIn(List<Integer> values) {
            addCriterion("RefuseTimes in", values, "refuseTimes");
            return (Criteria) this;
        }

        public Criteria andRefuseTimesNotIn(List<Integer> values) {
            addCriterion("RefuseTimes not in", values, "refuseTimes");
            return (Criteria) this;
        }

        public Criteria andRefuseTimesBetween(Integer value1, Integer value2) {
            addCriterion("RefuseTimes between", value1, value2, "refuseTimes");
            return (Criteria) this;
        }

        public Criteria andRefuseTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("RefuseTimes not between", value1, value2, "refuseTimes");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("Status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("Status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("Status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("Status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("Status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("Status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("Status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("Status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("Status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("Status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("Status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("Status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorIsNull() {
            addCriterion("CreateOperator is null");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorIsNotNull() {
            addCriterion("CreateOperator is not null");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorEqualTo(String value) {
            addCriterion("CreateOperator =", value, "createOperator");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorNotEqualTo(String value) {
            addCriterion("CreateOperator <>", value, "createOperator");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorGreaterThan(String value) {
            addCriterion("CreateOperator >", value, "createOperator");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorGreaterThanOrEqualTo(String value) {
            addCriterion("CreateOperator >=", value, "createOperator");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorLessThan(String value) {
            addCriterion("CreateOperator <", value, "createOperator");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorLessThanOrEqualTo(String value) {
            addCriterion("CreateOperator <=", value, "createOperator");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorLike(String value) {
            addCriterion("CreateOperator like", value, "createOperator");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorNotLike(String value) {
            addCriterion("CreateOperator not like", value, "createOperator");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorIn(List<String> values) {
            addCriterion("CreateOperator in", values, "createOperator");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorNotIn(List<String> values) {
            addCriterion("CreateOperator not in", values, "createOperator");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorBetween(String value1, String value2) {
            addCriterion("CreateOperator between", value1, value2, "createOperator");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorNotBetween(String value1, String value2) {
            addCriterion("CreateOperator not between", value1, value2, "createOperator");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("CreateTime is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("CreateTime is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("CreateTime =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("CreateTime <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("CreateTime >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("CreateTime >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("CreateTime <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("CreateTime <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("CreateTime in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("CreateTime not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("CreateTime between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("CreateTime not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andModifyOperatorIsNull() {
            addCriterion("ModifyOperator is null");
            return (Criteria) this;
        }

        public Criteria andModifyOperatorIsNotNull() {
            addCriterion("ModifyOperator is not null");
            return (Criteria) this;
        }

        public Criteria andModifyOperatorEqualTo(String value) {
            addCriterion("ModifyOperator =", value, "modifyOperator");
            return (Criteria) this;
        }

        public Criteria andModifyOperatorNotEqualTo(String value) {
            addCriterion("ModifyOperator <>", value, "modifyOperator");
            return (Criteria) this;
        }

        public Criteria andModifyOperatorGreaterThan(String value) {
            addCriterion("ModifyOperator >", value, "modifyOperator");
            return (Criteria) this;
        }

        public Criteria andModifyOperatorGreaterThanOrEqualTo(String value) {
            addCriterion("ModifyOperator >=", value, "modifyOperator");
            return (Criteria) this;
        }

        public Criteria andModifyOperatorLessThan(String value) {
            addCriterion("ModifyOperator <", value, "modifyOperator");
            return (Criteria) this;
        }

        public Criteria andModifyOperatorLessThanOrEqualTo(String value) {
            addCriterion("ModifyOperator <=", value, "modifyOperator");
            return (Criteria) this;
        }

        public Criteria andModifyOperatorLike(String value) {
            addCriterion("ModifyOperator like", value, "modifyOperator");
            return (Criteria) this;
        }

        public Criteria andModifyOperatorNotLike(String value) {
            addCriterion("ModifyOperator not like", value, "modifyOperator");
            return (Criteria) this;
        }

        public Criteria andModifyOperatorIn(List<String> values) {
            addCriterion("ModifyOperator in", values, "modifyOperator");
            return (Criteria) this;
        }

        public Criteria andModifyOperatorNotIn(List<String> values) {
            addCriterion("ModifyOperator not in", values, "modifyOperator");
            return (Criteria) this;
        }

        public Criteria andModifyOperatorBetween(String value1, String value2) {
            addCriterion("ModifyOperator between", value1, value2, "modifyOperator");
            return (Criteria) this;
        }

        public Criteria andModifyOperatorNotBetween(String value1, String value2) {
            addCriterion("ModifyOperator not between", value1, value2, "modifyOperator");
            return (Criteria) this;
        }

        public Criteria andDataChange_LastTimeIsNull() {
            addCriterion("DataChange_LastTime is null");
            return (Criteria) this;
        }

        public Criteria andDataChange_LastTimeIsNotNull() {
            addCriterion("DataChange_LastTime is not null");
            return (Criteria) this;
        }

        public Criteria andDataChange_LastTimeEqualTo(Date value) {
            addCriterion("DataChange_LastTime =", value, "dataChange_LastTime");
            return (Criteria) this;
        }

        public Criteria andDataChange_LastTimeNotEqualTo(Date value) {
            addCriterion("DataChange_LastTime <>", value, "dataChange_LastTime");
            return (Criteria) this;
        }

        public Criteria andDataChange_LastTimeGreaterThan(Date value) {
            addCriterion("DataChange_LastTime >", value, "dataChange_LastTime");
            return (Criteria) this;
        }

        public Criteria andDataChange_LastTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("DataChange_LastTime >=", value, "dataChange_LastTime");
            return (Criteria) this;
        }

        public Criteria andDataChange_LastTimeLessThan(Date value) {
            addCriterion("DataChange_LastTime <", value, "dataChange_LastTime");
            return (Criteria) this;
        }

        public Criteria andDataChange_LastTimeLessThanOrEqualTo(Date value) {
            addCriterion("DataChange_LastTime <=", value, "dataChange_LastTime");
            return (Criteria) this;
        }

        public Criteria andDataChange_LastTimeIn(List<Date> values) {
            addCriterion("DataChange_LastTime in", values, "dataChange_LastTime");
            return (Criteria) this;
        }

        public Criteria andDataChange_LastTimeNotIn(List<Date> values) {
            addCriterion("DataChange_LastTime not in", values, "dataChange_LastTime");
            return (Criteria) this;
        }

        public Criteria andDataChange_LastTimeBetween(Date value1, Date value2) {
            addCriterion("DataChange_LastTime between", value1, value2, "dataChange_LastTime");
            return (Criteria) this;
        }

        public Criteria andDataChange_LastTimeNotBetween(Date value1, Date value2) {
            addCriterion("DataChange_LastTime not between", value1, value2, "dataChange_LastTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}