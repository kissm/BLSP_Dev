package com.lpcode.modules.blsp.entity;

import java.util.ArrayList;
import java.util.List;

public class FormZjJsgcZljdzcbSgExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table form_zj_jsgc_zljdzcb_sg
     *
     * @mbggenerated Mon Jun 27 11:00:32 CST 2016
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table form_zj_jsgc_zljdzcb_sg
     *
     * @mbggenerated Mon Jun 27 11:00:32 CST 2016
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table form_zj_jsgc_zljdzcb_sg
     *
     * @mbggenerated Mon Jun 27 11:00:32 CST 2016
     */
    protected List<Criteria> oredCriteria;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table form_zj_jsgc_zljdzcb_sg
     *
     * @mbggenerated Mon Jun 27 11:00:32 CST 2016
     */
    protected int start = -1;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table form_zj_jsgc_zljdzcb_sg
     *
     * @mbggenerated Mon Jun 27 11:00:32 CST 2016
     */
    protected int pageSize = -1;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_jsgc_zljdzcb_sg
     *
     * @mbggenerated Mon Jun 27 11:00:32 CST 2016
     */
    public FormZjJsgcZljdzcbSgExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_jsgc_zljdzcb_sg
     *
     * @mbggenerated Mon Jun 27 11:00:32 CST 2016
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_jsgc_zljdzcb_sg
     *
     * @mbggenerated Mon Jun 27 11:00:32 CST 2016
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_jsgc_zljdzcb_sg
     *
     * @mbggenerated Mon Jun 27 11:00:32 CST 2016
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_jsgc_zljdzcb_sg
     *
     * @mbggenerated Mon Jun 27 11:00:32 CST 2016
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_jsgc_zljdzcb_sg
     *
     * @mbggenerated Mon Jun 27 11:00:32 CST 2016
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_jsgc_zljdzcb_sg
     *
     * @mbggenerated Mon Jun 27 11:00:32 CST 2016
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_jsgc_zljdzcb_sg
     *
     * @mbggenerated Mon Jun 27 11:00:32 CST 2016
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_jsgc_zljdzcb_sg
     *
     * @mbggenerated Mon Jun 27 11:00:32 CST 2016
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
     * This method corresponds to the database table form_zj_jsgc_zljdzcb_sg
     *
     * @mbggenerated Mon Jun 27 11:00:32 CST 2016
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_jsgc_zljdzcb_sg
     *
     * @mbggenerated Mon Jun 27 11:00:32 CST 2016
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_jsgc_zljdzcb_sg
     *
     * @mbggenerated Mon Jun 27 11:00:32 CST 2016
     */
    public void setStart(int start) {
        this.start=start;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_jsgc_zljdzcb_sg
     *
     * @mbggenerated Mon Jun 27 11:00:32 CST 2016
     */
    public int getStart() {
        return start;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_jsgc_zljdzcb_sg
     *
     * @mbggenerated Mon Jun 27 11:00:32 CST 2016
     */
    public void setPageSize(int pageSize) {
        this.pageSize=pageSize;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_zj_jsgc_zljdzcb_sg
     *
     * @mbggenerated Mon Jun 27 11:00:32 CST 2016
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table form_zj_jsgc_zljdzcb_sg
     *
     * @mbggenerated Mon Jun 27 11:00:32 CST 2016
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

        public Criteria andCIdIsNull() {
            addCriterion("C_ID is null");
            return (Criteria) this;
        }

        public Criteria andCIdIsNotNull() {
            addCriterion("C_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCIdEqualTo(Long value) {
            addCriterion("C_ID =", value, "cId");
            return (Criteria) this;
        }

        public Criteria andCIdNotEqualTo(Long value) {
            addCriterion("C_ID <>", value, "cId");
            return (Criteria) this;
        }

        public Criteria andCIdGreaterThan(Long value) {
            addCriterion("C_ID >", value, "cId");
            return (Criteria) this;
        }

        public Criteria andCIdGreaterThanOrEqualTo(Long value) {
            addCriterion("C_ID >=", value, "cId");
            return (Criteria) this;
        }

        public Criteria andCIdLessThan(Long value) {
            addCriterion("C_ID <", value, "cId");
            return (Criteria) this;
        }

        public Criteria andCIdLessThanOrEqualTo(Long value) {
            addCriterion("C_ID <=", value, "cId");
            return (Criteria) this;
        }

        public Criteria andCIdIn(List<Long> values) {
            addCriterion("C_ID in", values, "cId");
            return (Criteria) this;
        }

        public Criteria andCIdNotIn(List<Long> values) {
            addCriterion("C_ID not in", values, "cId");
            return (Criteria) this;
        }

        public Criteria andCIdBetween(Long value1, Long value2) {
            addCriterion("C_ID between", value1, value2, "cId");
            return (Criteria) this;
        }

        public Criteria andCIdNotBetween(Long value1, Long value2) {
            addCriterion("C_ID not between", value1, value2, "cId");
            return (Criteria) this;
        }

        public Criteria andJIdIsNull() {
            addCriterion("J_ID is null");
            return (Criteria) this;
        }

        public Criteria andJIdIsNotNull() {
            addCriterion("J_ID is not null");
            return (Criteria) this;
        }

        public Criteria andJIdEqualTo(Long value) {
            addCriterion("J_ID =", value, "jId");
            return (Criteria) this;
        }

        public Criteria andJIdNotEqualTo(Long value) {
            addCriterion("J_ID <>", value, "jId");
            return (Criteria) this;
        }

        public Criteria andJIdGreaterThan(Long value) {
            addCriterion("J_ID >", value, "jId");
            return (Criteria) this;
        }

        public Criteria andJIdGreaterThanOrEqualTo(Long value) {
            addCriterion("J_ID >=", value, "jId");
            return (Criteria) this;
        }

        public Criteria andJIdLessThan(Long value) {
            addCriterion("J_ID <", value, "jId");
            return (Criteria) this;
        }

        public Criteria andJIdLessThanOrEqualTo(Long value) {
            addCriterion("J_ID <=", value, "jId");
            return (Criteria) this;
        }

        public Criteria andJIdIn(List<Long> values) {
            addCriterion("J_ID in", values, "jId");
            return (Criteria) this;
        }

        public Criteria andJIdNotIn(List<Long> values) {
            addCriterion("J_ID not in", values, "jId");
            return (Criteria) this;
        }

        public Criteria andJIdBetween(Long value1, Long value2) {
            addCriterion("J_ID between", value1, value2, "jId");
            return (Criteria) this;
        }

        public Criteria andJIdNotBetween(Long value1, Long value2) {
            addCriterion("J_ID not between", value1, value2, "jId");
            return (Criteria) this;
        }

        public Criteria andCIsDeleteIsNull() {
            addCriterion("C_IS_DELETE is null");
            return (Criteria) this;
        }

        public Criteria andCIsDeleteIsNotNull() {
            addCriterion("C_IS_DELETE is not null");
            return (Criteria) this;
        }

        public Criteria andCIsDeleteEqualTo(String value) {
            addCriterion("C_IS_DELETE =", value, "cIsDelete");
            return (Criteria) this;
        }

        public Criteria andCIsDeleteNotEqualTo(String value) {
            addCriterion("C_IS_DELETE <>", value, "cIsDelete");
            return (Criteria) this;
        }

        public Criteria andCIsDeleteGreaterThan(String value) {
            addCriterion("C_IS_DELETE >", value, "cIsDelete");
            return (Criteria) this;
        }

        public Criteria andCIsDeleteGreaterThanOrEqualTo(String value) {
            addCriterion("C_IS_DELETE >=", value, "cIsDelete");
            return (Criteria) this;
        }

        public Criteria andCIsDeleteLessThan(String value) {
            addCriterion("C_IS_DELETE <", value, "cIsDelete");
            return (Criteria) this;
        }

        public Criteria andCIsDeleteLessThanOrEqualTo(String value) {
            addCriterion("C_IS_DELETE <=", value, "cIsDelete");
            return (Criteria) this;
        }

        public Criteria andCIsDeleteLike(String value) {
            addCriterion("C_IS_DELETE like", value, "cIsDelete");
            return (Criteria) this;
        }

        public Criteria andCIsDeleteNotLike(String value) {
            addCriterion("C_IS_DELETE not like", value, "cIsDelete");
            return (Criteria) this;
        }

        public Criteria andCIsDeleteIn(List<String> values) {
            addCriterion("C_IS_DELETE in", values, "cIsDelete");
            return (Criteria) this;
        }

        public Criteria andCIsDeleteNotIn(List<String> values) {
            addCriterion("C_IS_DELETE not in", values, "cIsDelete");
            return (Criteria) this;
        }

        public Criteria andCIsDeleteBetween(String value1, String value2) {
            addCriterion("C_IS_DELETE between", value1, value2, "cIsDelete");
            return (Criteria) this;
        }

        public Criteria andCIsDeleteNotBetween(String value1, String value2) {
            addCriterion("C_IS_DELETE not between", value1, value2, "cIsDelete");
            return (Criteria) this;
        }

        public Criteria andCNameIsNull() {
            addCriterion("C_NAME is null");
            return (Criteria) this;
        }

        public Criteria andCNameIsNotNull() {
            addCriterion("C_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andCNameEqualTo(String value) {
            addCriterion("C_NAME =", value, "cName");
            return (Criteria) this;
        }

        public Criteria andCNameNotEqualTo(String value) {
            addCriterion("C_NAME <>", value, "cName");
            return (Criteria) this;
        }

        public Criteria andCNameGreaterThan(String value) {
            addCriterion("C_NAME >", value, "cName");
            return (Criteria) this;
        }

        public Criteria andCNameGreaterThanOrEqualTo(String value) {
            addCriterion("C_NAME >=", value, "cName");
            return (Criteria) this;
        }

        public Criteria andCNameLessThan(String value) {
            addCriterion("C_NAME <", value, "cName");
            return (Criteria) this;
        }

        public Criteria andCNameLessThanOrEqualTo(String value) {
            addCriterion("C_NAME <=", value, "cName");
            return (Criteria) this;
        }

        public Criteria andCNameLike(String value) {
            addCriterion("C_NAME like", value, "cName");
            return (Criteria) this;
        }

        public Criteria andCNameNotLike(String value) {
            addCriterion("C_NAME not like", value, "cName");
            return (Criteria) this;
        }

        public Criteria andCNameIn(List<String> values) {
            addCriterion("C_NAME in", values, "cName");
            return (Criteria) this;
        }

        public Criteria andCNameNotIn(List<String> values) {
            addCriterion("C_NAME not in", values, "cName");
            return (Criteria) this;
        }

        public Criteria andCNameBetween(String value1, String value2) {
            addCriterion("C_NAME between", value1, value2, "cName");
            return (Criteria) this;
        }

        public Criteria andCNameNotBetween(String value1, String value2) {
            addCriterion("C_NAME not between", value1, value2, "cName");
            return (Criteria) this;
        }

        public Criteria andCDutyIsNull() {
            addCriterion("C_DUTY is null");
            return (Criteria) this;
        }

        public Criteria andCDutyIsNotNull() {
            addCriterion("C_DUTY is not null");
            return (Criteria) this;
        }

        public Criteria andCDutyEqualTo(String value) {
            addCriterion("C_DUTY =", value, "cDuty");
            return (Criteria) this;
        }

        public Criteria andCDutyNotEqualTo(String value) {
            addCriterion("C_DUTY <>", value, "cDuty");
            return (Criteria) this;
        }

        public Criteria andCDutyGreaterThan(String value) {
            addCriterion("C_DUTY >", value, "cDuty");
            return (Criteria) this;
        }

        public Criteria andCDutyGreaterThanOrEqualTo(String value) {
            addCriterion("C_DUTY >=", value, "cDuty");
            return (Criteria) this;
        }

        public Criteria andCDutyLessThan(String value) {
            addCriterion("C_DUTY <", value, "cDuty");
            return (Criteria) this;
        }

        public Criteria andCDutyLessThanOrEqualTo(String value) {
            addCriterion("C_DUTY <=", value, "cDuty");
            return (Criteria) this;
        }

        public Criteria andCDutyLike(String value) {
            addCriterion("C_DUTY like", value, "cDuty");
            return (Criteria) this;
        }

        public Criteria andCDutyNotLike(String value) {
            addCriterion("C_DUTY not like", value, "cDuty");
            return (Criteria) this;
        }

        public Criteria andCDutyIn(List<String> values) {
            addCriterion("C_DUTY in", values, "cDuty");
            return (Criteria) this;
        }

        public Criteria andCDutyNotIn(List<String> values) {
            addCriterion("C_DUTY not in", values, "cDuty");
            return (Criteria) this;
        }

        public Criteria andCDutyBetween(String value1, String value2) {
            addCriterion("C_DUTY between", value1, value2, "cDuty");
            return (Criteria) this;
        }

        public Criteria andCDutyNotBetween(String value1, String value2) {
            addCriterion("C_DUTY not between", value1, value2, "cDuty");
            return (Criteria) this;
        }

        public Criteria andCTitleIsNull() {
            addCriterion("C_TITLE is null");
            return (Criteria) this;
        }

        public Criteria andCTitleIsNotNull() {
            addCriterion("C_TITLE is not null");
            return (Criteria) this;
        }

        public Criteria andCTitleEqualTo(String value) {
            addCriterion("C_TITLE =", value, "cTitle");
            return (Criteria) this;
        }

        public Criteria andCTitleNotEqualTo(String value) {
            addCriterion("C_TITLE <>", value, "cTitle");
            return (Criteria) this;
        }

        public Criteria andCTitleGreaterThan(String value) {
            addCriterion("C_TITLE >", value, "cTitle");
            return (Criteria) this;
        }

        public Criteria andCTitleGreaterThanOrEqualTo(String value) {
            addCriterion("C_TITLE >=", value, "cTitle");
            return (Criteria) this;
        }

        public Criteria andCTitleLessThan(String value) {
            addCriterion("C_TITLE <", value, "cTitle");
            return (Criteria) this;
        }

        public Criteria andCTitleLessThanOrEqualTo(String value) {
            addCriterion("C_TITLE <=", value, "cTitle");
            return (Criteria) this;
        }

        public Criteria andCTitleLike(String value) {
            addCriterion("C_TITLE like", value, "cTitle");
            return (Criteria) this;
        }

        public Criteria andCTitleNotLike(String value) {
            addCriterion("C_TITLE not like", value, "cTitle");
            return (Criteria) this;
        }

        public Criteria andCTitleIn(List<String> values) {
            addCriterion("C_TITLE in", values, "cTitle");
            return (Criteria) this;
        }

        public Criteria andCTitleNotIn(List<String> values) {
            addCriterion("C_TITLE not in", values, "cTitle");
            return (Criteria) this;
        }

        public Criteria andCTitleBetween(String value1, String value2) {
            addCriterion("C_TITLE between", value1, value2, "cTitle");
            return (Criteria) this;
        }

        public Criteria andCTitleNotBetween(String value1, String value2) {
            addCriterion("C_TITLE not between", value1, value2, "cTitle");
            return (Criteria) this;
        }

        public Criteria andCMajorIsNull() {
            addCriterion("C_MAJOR is null");
            return (Criteria) this;
        }

        public Criteria andCMajorIsNotNull() {
            addCriterion("C_MAJOR is not null");
            return (Criteria) this;
        }

        public Criteria andCMajorEqualTo(String value) {
            addCriterion("C_MAJOR =", value, "cMajor");
            return (Criteria) this;
        }

        public Criteria andCMajorNotEqualTo(String value) {
            addCriterion("C_MAJOR <>", value, "cMajor");
            return (Criteria) this;
        }

        public Criteria andCMajorGreaterThan(String value) {
            addCriterion("C_MAJOR >", value, "cMajor");
            return (Criteria) this;
        }

        public Criteria andCMajorGreaterThanOrEqualTo(String value) {
            addCriterion("C_MAJOR >=", value, "cMajor");
            return (Criteria) this;
        }

        public Criteria andCMajorLessThan(String value) {
            addCriterion("C_MAJOR <", value, "cMajor");
            return (Criteria) this;
        }

        public Criteria andCMajorLessThanOrEqualTo(String value) {
            addCriterion("C_MAJOR <=", value, "cMajor");
            return (Criteria) this;
        }

        public Criteria andCMajorLike(String value) {
            addCriterion("C_MAJOR like", value, "cMajor");
            return (Criteria) this;
        }

        public Criteria andCMajorNotLike(String value) {
            addCriterion("C_MAJOR not like", value, "cMajor");
            return (Criteria) this;
        }

        public Criteria andCMajorIn(List<String> values) {
            addCriterion("C_MAJOR in", values, "cMajor");
            return (Criteria) this;
        }

        public Criteria andCMajorNotIn(List<String> values) {
            addCriterion("C_MAJOR not in", values, "cMajor");
            return (Criteria) this;
        }

        public Criteria andCMajorBetween(String value1, String value2) {
            addCriterion("C_MAJOR between", value1, value2, "cMajor");
            return (Criteria) this;
        }

        public Criteria andCMajorNotBetween(String value1, String value2) {
            addCriterion("C_MAJOR not between", value1, value2, "cMajor");
            return (Criteria) this;
        }

        public Criteria andCPhoneIsNull() {
            addCriterion("C_PHONE is null");
            return (Criteria) this;
        }

        public Criteria andCPhoneIsNotNull() {
            addCriterion("C_PHONE is not null");
            return (Criteria) this;
        }

        public Criteria andCPhoneEqualTo(String value) {
            addCriterion("C_PHONE =", value, "cPhone");
            return (Criteria) this;
        }

        public Criteria andCPhoneNotEqualTo(String value) {
            addCriterion("C_PHONE <>", value, "cPhone");
            return (Criteria) this;
        }

        public Criteria andCPhoneGreaterThan(String value) {
            addCriterion("C_PHONE >", value, "cPhone");
            return (Criteria) this;
        }

        public Criteria andCPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("C_PHONE >=", value, "cPhone");
            return (Criteria) this;
        }

        public Criteria andCPhoneLessThan(String value) {
            addCriterion("C_PHONE <", value, "cPhone");
            return (Criteria) this;
        }

        public Criteria andCPhoneLessThanOrEqualTo(String value) {
            addCriterion("C_PHONE <=", value, "cPhone");
            return (Criteria) this;
        }

        public Criteria andCPhoneLike(String value) {
            addCriterion("C_PHONE like", value, "cPhone");
            return (Criteria) this;
        }

        public Criteria andCPhoneNotLike(String value) {
            addCriterion("C_PHONE not like", value, "cPhone");
            return (Criteria) this;
        }

        public Criteria andCPhoneIn(List<String> values) {
            addCriterion("C_PHONE in", values, "cPhone");
            return (Criteria) this;
        }

        public Criteria andCPhoneNotIn(List<String> values) {
            addCriterion("C_PHONE not in", values, "cPhone");
            return (Criteria) this;
        }

        public Criteria andCPhoneBetween(String value1, String value2) {
            addCriterion("C_PHONE between", value1, value2, "cPhone");
            return (Criteria) this;
        }

        public Criteria andCPhoneNotBetween(String value1, String value2) {
            addCriterion("C_PHONE not between", value1, value2, "cPhone");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table form_zj_jsgc_zljdzcb_sg
     *
     * @mbggenerated do_not_delete_during_merge Mon Jun 27 11:00:32 CST 2016
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table form_zj_jsgc_zljdzcb_sg
     *
     * @mbggenerated Mon Jun 27 11:00:32 CST 2016
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