package com.lpcode.modules.blsp.entity;

import java.util.ArrayList;
import java.util.List;

public class FormHbJsxmGyxmSbExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table form_hb_jsxm_gyxm_sb
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table form_hb_jsxm_gyxm_sb
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table form_hb_jsxm_gyxm_sb
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    protected List<Criteria> oredCriteria;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table form_hb_jsxm_gyxm_sb
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    protected int start = -1;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table form_hb_jsxm_gyxm_sb
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    protected int pageSize = -1;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm_sb
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    public FormHbJsxmGyxmSbExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm_sb
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm_sb
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm_sb
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm_sb
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm_sb
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm_sb
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm_sb
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm_sb
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm_sb
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm_sb
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm_sb
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    public void setStart(int start) {
        this.start=start;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm_sb
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    public int getStart() {
        return start;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm_sb
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    public void setPageSize(int pageSize) {
        this.pageSize=pageSize;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_hb_jsxm_gyxm_sb
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table form_hb_jsxm_gyxm_sb
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
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

        public Criteria andEIdIsNull() {
            addCriterion("E_ID is null");
            return (Criteria) this;
        }

        public Criteria andEIdIsNotNull() {
            addCriterion("E_ID is not null");
            return (Criteria) this;
        }

        public Criteria andEIdEqualTo(Long value) {
            addCriterion("E_ID =", value, "eId");
            return (Criteria) this;
        }

        public Criteria andEIdNotEqualTo(Long value) {
            addCriterion("E_ID <>", value, "eId");
            return (Criteria) this;
        }

        public Criteria andEIdGreaterThan(Long value) {
            addCriterion("E_ID >", value, "eId");
            return (Criteria) this;
        }

        public Criteria andEIdGreaterThanOrEqualTo(Long value) {
            addCriterion("E_ID >=", value, "eId");
            return (Criteria) this;
        }

        public Criteria andEIdLessThan(Long value) {
            addCriterion("E_ID <", value, "eId");
            return (Criteria) this;
        }

        public Criteria andEIdLessThanOrEqualTo(Long value) {
            addCriterion("E_ID <=", value, "eId");
            return (Criteria) this;
        }

        public Criteria andEIdIn(List<Long> values) {
            addCriterion("E_ID in", values, "eId");
            return (Criteria) this;
        }

        public Criteria andEIdNotIn(List<Long> values) {
            addCriterion("E_ID not in", values, "eId");
            return (Criteria) this;
        }

        public Criteria andEIdBetween(Long value1, Long value2) {
            addCriterion("E_ID between", value1, value2, "eId");
            return (Criteria) this;
        }

        public Criteria andEIdNotBetween(Long value1, Long value2) {
            addCriterion("E_ID not between", value1, value2, "eId");
            return (Criteria) this;
        }

        public Criteria andHIdIsNull() {
            addCriterion("H_ID is null");
            return (Criteria) this;
        }

        public Criteria andHIdIsNotNull() {
            addCriterion("H_ID is not null");
            return (Criteria) this;
        }

        public Criteria andHIdEqualTo(Long value) {
            addCriterion("H_ID =", value, "hId");
            return (Criteria) this;
        }

        public Criteria andHIdNotEqualTo(Long value) {
            addCriterion("H_ID <>", value, "hId");
            return (Criteria) this;
        }

        public Criteria andHIdGreaterThan(Long value) {
            addCriterion("H_ID >", value, "hId");
            return (Criteria) this;
        }

        public Criteria andHIdGreaterThanOrEqualTo(Long value) {
            addCriterion("H_ID >=", value, "hId");
            return (Criteria) this;
        }

        public Criteria andHIdLessThan(Long value) {
            addCriterion("H_ID <", value, "hId");
            return (Criteria) this;
        }

        public Criteria andHIdLessThanOrEqualTo(Long value) {
            addCriterion("H_ID <=", value, "hId");
            return (Criteria) this;
        }

        public Criteria andHIdIn(List<Long> values) {
            addCriterion("H_ID in", values, "hId");
            return (Criteria) this;
        }

        public Criteria andHIdNotIn(List<Long> values) {
            addCriterion("H_ID not in", values, "hId");
            return (Criteria) this;
        }

        public Criteria andHIdBetween(Long value1, Long value2) {
            addCriterion("H_ID between", value1, value2, "hId");
            return (Criteria) this;
        }

        public Criteria andHIdNotBetween(Long value1, Long value2) {
            addCriterion("H_ID not between", value1, value2, "hId");
            return (Criteria) this;
        }

        public Criteria andEIsDeleteIsNull() {
            addCriterion("E_IS_DELETE is null");
            return (Criteria) this;
        }

        public Criteria andEIsDeleteIsNotNull() {
            addCriterion("E_IS_DELETE is not null");
            return (Criteria) this;
        }

        public Criteria andEIsDeleteEqualTo(String value) {
            addCriterion("E_IS_DELETE =", value, "eIsDelete");
            return (Criteria) this;
        }

        public Criteria andEIsDeleteNotEqualTo(String value) {
            addCriterion("E_IS_DELETE <>", value, "eIsDelete");
            return (Criteria) this;
        }

        public Criteria andEIsDeleteGreaterThan(String value) {
            addCriterion("E_IS_DELETE >", value, "eIsDelete");
            return (Criteria) this;
        }

        public Criteria andEIsDeleteGreaterThanOrEqualTo(String value) {
            addCriterion("E_IS_DELETE >=", value, "eIsDelete");
            return (Criteria) this;
        }

        public Criteria andEIsDeleteLessThan(String value) {
            addCriterion("E_IS_DELETE <", value, "eIsDelete");
            return (Criteria) this;
        }

        public Criteria andEIsDeleteLessThanOrEqualTo(String value) {
            addCriterion("E_IS_DELETE <=", value, "eIsDelete");
            return (Criteria) this;
        }

        public Criteria andEIsDeleteLike(String value) {
            addCriterion("E_IS_DELETE like", value, "eIsDelete");
            return (Criteria) this;
        }

        public Criteria andEIsDeleteNotLike(String value) {
            addCriterion("E_IS_DELETE not like", value, "eIsDelete");
            return (Criteria) this;
        }

        public Criteria andEIsDeleteIn(List<String> values) {
            addCriterion("E_IS_DELETE in", values, "eIsDelete");
            return (Criteria) this;
        }

        public Criteria andEIsDeleteNotIn(List<String> values) {
            addCriterion("E_IS_DELETE not in", values, "eIsDelete");
            return (Criteria) this;
        }

        public Criteria andEIsDeleteBetween(String value1, String value2) {
            addCriterion("E_IS_DELETE between", value1, value2, "eIsDelete");
            return (Criteria) this;
        }

        public Criteria andEIsDeleteNotBetween(String value1, String value2) {
            addCriterion("E_IS_DELETE not between", value1, value2, "eIsDelete");
            return (Criteria) this;
        }

        public Criteria andENameIsNull() {
            addCriterion("E_NAME is null");
            return (Criteria) this;
        }

        public Criteria andENameIsNotNull() {
            addCriterion("E_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andENameEqualTo(String value) {
            addCriterion("E_NAME =", value, "eName");
            return (Criteria) this;
        }

        public Criteria andENameNotEqualTo(String value) {
            addCriterion("E_NAME <>", value, "eName");
            return (Criteria) this;
        }

        public Criteria andENameGreaterThan(String value) {
            addCriterion("E_NAME >", value, "eName");
            return (Criteria) this;
        }

        public Criteria andENameGreaterThanOrEqualTo(String value) {
            addCriterion("E_NAME >=", value, "eName");
            return (Criteria) this;
        }

        public Criteria andENameLessThan(String value) {
            addCriterion("E_NAME <", value, "eName");
            return (Criteria) this;
        }

        public Criteria andENameLessThanOrEqualTo(String value) {
            addCriterion("E_NAME <=", value, "eName");
            return (Criteria) this;
        }

        public Criteria andENameLike(String value) {
            addCriterion("E_NAME like", value, "eName");
            return (Criteria) this;
        }

        public Criteria andENameNotLike(String value) {
            addCriterion("E_NAME not like", value, "eName");
            return (Criteria) this;
        }

        public Criteria andENameIn(List<String> values) {
            addCriterion("E_NAME in", values, "eName");
            return (Criteria) this;
        }

        public Criteria andENameNotIn(List<String> values) {
            addCriterion("E_NAME not in", values, "eName");
            return (Criteria) this;
        }

        public Criteria andENameBetween(String value1, String value2) {
            addCriterion("E_NAME between", value1, value2, "eName");
            return (Criteria) this;
        }

        public Criteria andENameNotBetween(String value1, String value2) {
            addCriterion("E_NAME not between", value1, value2, "eName");
            return (Criteria) this;
        }

        public Criteria andESpecificateIsNull() {
            addCriterion("E_SPECIFICATE is null");
            return (Criteria) this;
        }

        public Criteria andESpecificateIsNotNull() {
            addCriterion("E_SPECIFICATE is not null");
            return (Criteria) this;
        }

        public Criteria andESpecificateEqualTo(String value) {
            addCriterion("E_SPECIFICATE =", value, "eSpecificate");
            return (Criteria) this;
        }

        public Criteria andESpecificateNotEqualTo(String value) {
            addCriterion("E_SPECIFICATE <>", value, "eSpecificate");
            return (Criteria) this;
        }

        public Criteria andESpecificateGreaterThan(String value) {
            addCriterion("E_SPECIFICATE >", value, "eSpecificate");
            return (Criteria) this;
        }

        public Criteria andESpecificateGreaterThanOrEqualTo(String value) {
            addCriterion("E_SPECIFICATE >=", value, "eSpecificate");
            return (Criteria) this;
        }

        public Criteria andESpecificateLessThan(String value) {
            addCriterion("E_SPECIFICATE <", value, "eSpecificate");
            return (Criteria) this;
        }

        public Criteria andESpecificateLessThanOrEqualTo(String value) {
            addCriterion("E_SPECIFICATE <=", value, "eSpecificate");
            return (Criteria) this;
        }

        public Criteria andESpecificateLike(String value) {
            addCriterion("E_SPECIFICATE like", value, "eSpecificate");
            return (Criteria) this;
        }

        public Criteria andESpecificateNotLike(String value) {
            addCriterion("E_SPECIFICATE not like", value, "eSpecificate");
            return (Criteria) this;
        }

        public Criteria andESpecificateIn(List<String> values) {
            addCriterion("E_SPECIFICATE in", values, "eSpecificate");
            return (Criteria) this;
        }

        public Criteria andESpecificateNotIn(List<String> values) {
            addCriterion("E_SPECIFICATE not in", values, "eSpecificate");
            return (Criteria) this;
        }

        public Criteria andESpecificateBetween(String value1, String value2) {
            addCriterion("E_SPECIFICATE between", value1, value2, "eSpecificate");
            return (Criteria) this;
        }

        public Criteria andESpecificateNotBetween(String value1, String value2) {
            addCriterion("E_SPECIFICATE not between", value1, value2, "eSpecificate");
            return (Criteria) this;
        }

        public Criteria andECountIsNull() {
            addCriterion("E_COUNT is null");
            return (Criteria) this;
        }

        public Criteria andECountIsNotNull() {
            addCriterion("E_COUNT is not null");
            return (Criteria) this;
        }

        public Criteria andECountEqualTo(String value) {
            addCriterion("E_COUNT =", value, "eCount");
            return (Criteria) this;
        }

        public Criteria andECountNotEqualTo(String value) {
            addCriterion("E_COUNT <>", value, "eCount");
            return (Criteria) this;
        }

        public Criteria andECountGreaterThan(String value) {
            addCriterion("E_COUNT >", value, "eCount");
            return (Criteria) this;
        }

        public Criteria andECountGreaterThanOrEqualTo(String value) {
            addCriterion("E_COUNT >=", value, "eCount");
            return (Criteria) this;
        }

        public Criteria andECountLessThan(String value) {
            addCriterion("E_COUNT <", value, "eCount");
            return (Criteria) this;
        }

        public Criteria andECountLessThanOrEqualTo(String value) {
            addCriterion("E_COUNT <=", value, "eCount");
            return (Criteria) this;
        }

        public Criteria andECountLike(String value) {
            addCriterion("E_COUNT like", value, "eCount");
            return (Criteria) this;
        }

        public Criteria andECountNotLike(String value) {
            addCriterion("E_COUNT not like", value, "eCount");
            return (Criteria) this;
        }

        public Criteria andECountIn(List<String> values) {
            addCriterion("E_COUNT in", values, "eCount");
            return (Criteria) this;
        }

        public Criteria andECountNotIn(List<String> values) {
            addCriterion("E_COUNT not in", values, "eCount");
            return (Criteria) this;
        }

        public Criteria andECountBetween(String value1, String value2) {
            addCriterion("E_COUNT between", value1, value2, "eCount");
            return (Criteria) this;
        }

        public Criteria andECountNotBetween(String value1, String value2) {
            addCriterion("E_COUNT not between", value1, value2, "eCount");
            return (Criteria) this;
        }

        public Criteria andERemarkIsNull() {
            addCriterion("E_REMARK is null");
            return (Criteria) this;
        }

        public Criteria andERemarkIsNotNull() {
            addCriterion("E_REMARK is not null");
            return (Criteria) this;
        }

        public Criteria andERemarkEqualTo(String value) {
            addCriterion("E_REMARK =", value, "eRemark");
            return (Criteria) this;
        }

        public Criteria andERemarkNotEqualTo(String value) {
            addCriterion("E_REMARK <>", value, "eRemark");
            return (Criteria) this;
        }

        public Criteria andERemarkGreaterThan(String value) {
            addCriterion("E_REMARK >", value, "eRemark");
            return (Criteria) this;
        }

        public Criteria andERemarkGreaterThanOrEqualTo(String value) {
            addCriterion("E_REMARK >=", value, "eRemark");
            return (Criteria) this;
        }

        public Criteria andERemarkLessThan(String value) {
            addCriterion("E_REMARK <", value, "eRemark");
            return (Criteria) this;
        }

        public Criteria andERemarkLessThanOrEqualTo(String value) {
            addCriterion("E_REMARK <=", value, "eRemark");
            return (Criteria) this;
        }

        public Criteria andERemarkLike(String value) {
            addCriterion("E_REMARK like", value, "eRemark");
            return (Criteria) this;
        }

        public Criteria andERemarkNotLike(String value) {
            addCriterion("E_REMARK not like", value, "eRemark");
            return (Criteria) this;
        }

        public Criteria andERemarkIn(List<String> values) {
            addCriterion("E_REMARK in", values, "eRemark");
            return (Criteria) this;
        }

        public Criteria andERemarkNotIn(List<String> values) {
            addCriterion("E_REMARK not in", values, "eRemark");
            return (Criteria) this;
        }

        public Criteria andERemarkBetween(String value1, String value2) {
            addCriterion("E_REMARK between", value1, value2, "eRemark");
            return (Criteria) this;
        }

        public Criteria andERemarkNotBetween(String value1, String value2) {
            addCriterion("E_REMARK not between", value1, value2, "eRemark");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table form_hb_jsxm_gyxm_sb
     *
     * @mbggenerated do_not_delete_during_merge Tue Jul 12 16:20:39 CST 2016
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table form_hb_jsxm_gyxm_sb
     *
     * @mbggenerated Tue Jul 12 16:20:39 CST 2016
     */
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