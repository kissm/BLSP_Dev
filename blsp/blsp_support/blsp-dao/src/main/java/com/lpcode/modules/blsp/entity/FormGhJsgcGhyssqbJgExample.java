package com.lpcode.modules.blsp.entity;

import java.util.ArrayList;
import java.util.List;

public class FormGhJsgcGhyssqbJgExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table form_gh_jsgc_ghyssqb_jg
     *
     * @mbggenerated Mon Jun 20 14:23:31 CST 2016
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table form_gh_jsgc_ghyssqb_jg
     *
     * @mbggenerated Mon Jun 20 14:23:31 CST 2016
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table form_gh_jsgc_ghyssqb_jg
     *
     * @mbggenerated Mon Jun 20 14:23:31 CST 2016
     */
    protected List<Criteria> oredCriteria;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table form_gh_jsgc_ghyssqb_jg
     *
     * @mbggenerated Mon Jun 20 14:23:31 CST 2016
     */
    protected int start = -1;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table form_gh_jsgc_ghyssqb_jg
     *
     * @mbggenerated Mon Jun 20 14:23:31 CST 2016
     */
    protected int pageSize = -1;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_gh_jsgc_ghyssqb_jg
     *
     * @mbggenerated Mon Jun 20 14:23:31 CST 2016
     */
    public FormGhJsgcGhyssqbJgExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_gh_jsgc_ghyssqb_jg
     *
     * @mbggenerated Mon Jun 20 14:23:31 CST 2016
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_gh_jsgc_ghyssqb_jg
     *
     * @mbggenerated Mon Jun 20 14:23:31 CST 2016
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_gh_jsgc_ghyssqb_jg
     *
     * @mbggenerated Mon Jun 20 14:23:31 CST 2016
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_gh_jsgc_ghyssqb_jg
     *
     * @mbggenerated Mon Jun 20 14:23:31 CST 2016
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_gh_jsgc_ghyssqb_jg
     *
     * @mbggenerated Mon Jun 20 14:23:31 CST 2016
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_gh_jsgc_ghyssqb_jg
     *
     * @mbggenerated Mon Jun 20 14:23:31 CST 2016
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_gh_jsgc_ghyssqb_jg
     *
     * @mbggenerated Mon Jun 20 14:23:31 CST 2016
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_gh_jsgc_ghyssqb_jg
     *
     * @mbggenerated Mon Jun 20 14:23:31 CST 2016
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
     * This method corresponds to the database table form_gh_jsgc_ghyssqb_jg
     *
     * @mbggenerated Mon Jun 20 14:23:31 CST 2016
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_gh_jsgc_ghyssqb_jg
     *
     * @mbggenerated Mon Jun 20 14:23:31 CST 2016
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_gh_jsgc_ghyssqb_jg
     *
     * @mbggenerated Mon Jun 20 14:23:31 CST 2016
     */
    public void setStart(int start) {
        this.start=start;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_gh_jsgc_ghyssqb_jg
     *
     * @mbggenerated Mon Jun 20 14:23:31 CST 2016
     */
    public int getStart() {
        return start;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_gh_jsgc_ghyssqb_jg
     *
     * @mbggenerated Mon Jun 20 14:23:31 CST 2016
     */
    public void setPageSize(int pageSize) {
        this.pageSize=pageSize;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table form_gh_jsgc_ghyssqb_jg
     *
     * @mbggenerated Mon Jun 20 14:23:31 CST 2016
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table form_gh_jsgc_ghyssqb_jg
     *
     * @mbggenerated Mon Jun 20 14:23:31 CST 2016
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

        public Criteria andGIdIsNull() {
            addCriterion("G_ID is null");
            return (Criteria) this;
        }

        public Criteria andGIdIsNotNull() {
            addCriterion("G_ID is not null");
            return (Criteria) this;
        }

        public Criteria andGIdEqualTo(Long value) {
            addCriterion("G_ID =", value, "gId");
            return (Criteria) this;
        }

        public Criteria andGIdNotEqualTo(Long value) {
            addCriterion("G_ID <>", value, "gId");
            return (Criteria) this;
        }

        public Criteria andGIdGreaterThan(Long value) {
            addCriterion("G_ID >", value, "gId");
            return (Criteria) this;
        }

        public Criteria andGIdGreaterThanOrEqualTo(Long value) {
            addCriterion("G_ID >=", value, "gId");
            return (Criteria) this;
        }

        public Criteria andGIdLessThan(Long value) {
            addCriterion("G_ID <", value, "gId");
            return (Criteria) this;
        }

        public Criteria andGIdLessThanOrEqualTo(Long value) {
            addCriterion("G_ID <=", value, "gId");
            return (Criteria) this;
        }

        public Criteria andGIdIn(List<Long> values) {
            addCriterion("G_ID in", values, "gId");
            return (Criteria) this;
        }

        public Criteria andGIdNotIn(List<Long> values) {
            addCriterion("G_ID not in", values, "gId");
            return (Criteria) this;
        }

        public Criteria andGIdBetween(Long value1, Long value2) {
            addCriterion("G_ID between", value1, value2, "gId");
            return (Criteria) this;
        }

        public Criteria andGIdNotBetween(Long value1, Long value2) {
            addCriterion("G_ID not between", value1, value2, "gId");
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

        public Criteria andComPrjNameIsNull() {
            addCriterion("COM_PRJ_NAME is null");
            return (Criteria) this;
        }

        public Criteria andComPrjNameIsNotNull() {
            addCriterion("COM_PRJ_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andComPrjNameEqualTo(String value) {
            addCriterion("COM_PRJ_NAME =", value, "comPrjName");
            return (Criteria) this;
        }

        public Criteria andComPrjNameNotEqualTo(String value) {
            addCriterion("COM_PRJ_NAME <>", value, "comPrjName");
            return (Criteria) this;
        }

        public Criteria andComPrjNameGreaterThan(String value) {
            addCriterion("COM_PRJ_NAME >", value, "comPrjName");
            return (Criteria) this;
        }

        public Criteria andComPrjNameGreaterThanOrEqualTo(String value) {
            addCriterion("COM_PRJ_NAME >=", value, "comPrjName");
            return (Criteria) this;
        }

        public Criteria andComPrjNameLessThan(String value) {
            addCriterion("COM_PRJ_NAME <", value, "comPrjName");
            return (Criteria) this;
        }

        public Criteria andComPrjNameLessThanOrEqualTo(String value) {
            addCriterion("COM_PRJ_NAME <=", value, "comPrjName");
            return (Criteria) this;
        }

        public Criteria andComPrjNameLike(String value) {
            addCriterion("COM_PRJ_NAME like", value, "comPrjName");
            return (Criteria) this;
        }

        public Criteria andComPrjNameNotLike(String value) {
            addCriterion("COM_PRJ_NAME not like", value, "comPrjName");
            return (Criteria) this;
        }

        public Criteria andComPrjNameIn(List<String> values) {
            addCriterion("COM_PRJ_NAME in", values, "comPrjName");
            return (Criteria) this;
        }

        public Criteria andComPrjNameNotIn(List<String> values) {
            addCriterion("COM_PRJ_NAME not in", values, "comPrjName");
            return (Criteria) this;
        }

        public Criteria andComPrjNameBetween(String value1, String value2) {
            addCriterion("COM_PRJ_NAME between", value1, value2, "comPrjName");
            return (Criteria) this;
        }

        public Criteria andComPrjNameNotBetween(String value1, String value2) {
            addCriterion("COM_PRJ_NAME not between", value1, value2, "comPrjName");
            return (Criteria) this;
        }

        public Criteria andComRidgepoleNumIsNull() {
            addCriterion("COM_RIDGEPOLE_NUM is null");
            return (Criteria) this;
        }

        public Criteria andComRidgepoleNumIsNotNull() {
            addCriterion("COM_RIDGEPOLE_NUM is not null");
            return (Criteria) this;
        }

        public Criteria andComRidgepoleNumEqualTo(String value) {
            addCriterion("COM_RIDGEPOLE_NUM =", value, "comRidgepoleNum");
            return (Criteria) this;
        }

        public Criteria andComRidgepoleNumNotEqualTo(String value) {
            addCriterion("COM_RIDGEPOLE_NUM <>", value, "comRidgepoleNum");
            return (Criteria) this;
        }

        public Criteria andComRidgepoleNumGreaterThan(String value) {
            addCriterion("COM_RIDGEPOLE_NUM >", value, "comRidgepoleNum");
            return (Criteria) this;
        }

        public Criteria andComRidgepoleNumGreaterThanOrEqualTo(String value) {
            addCriterion("COM_RIDGEPOLE_NUM >=", value, "comRidgepoleNum");
            return (Criteria) this;
        }

        public Criteria andComRidgepoleNumLessThan(String value) {
            addCriterion("COM_RIDGEPOLE_NUM <", value, "comRidgepoleNum");
            return (Criteria) this;
        }

        public Criteria andComRidgepoleNumLessThanOrEqualTo(String value) {
            addCriterion("COM_RIDGEPOLE_NUM <=", value, "comRidgepoleNum");
            return (Criteria) this;
        }

        public Criteria andComRidgepoleNumLike(String value) {
            addCriterion("COM_RIDGEPOLE_NUM like", value, "comRidgepoleNum");
            return (Criteria) this;
        }

        public Criteria andComRidgepoleNumNotLike(String value) {
            addCriterion("COM_RIDGEPOLE_NUM not like", value, "comRidgepoleNum");
            return (Criteria) this;
        }

        public Criteria andComRidgepoleNumIn(List<String> values) {
            addCriterion("COM_RIDGEPOLE_NUM in", values, "comRidgepoleNum");
            return (Criteria) this;
        }

        public Criteria andComRidgepoleNumNotIn(List<String> values) {
            addCriterion("COM_RIDGEPOLE_NUM not in", values, "comRidgepoleNum");
            return (Criteria) this;
        }

        public Criteria andComRidgepoleNumBetween(String value1, String value2) {
            addCriterion("COM_RIDGEPOLE_NUM between", value1, value2, "comRidgepoleNum");
            return (Criteria) this;
        }

        public Criteria andComRidgepoleNumNotBetween(String value1, String value2) {
            addCriterion("COM_RIDGEPOLE_NUM not between", value1, value2, "comRidgepoleNum");
            return (Criteria) this;
        }

        public Criteria andComFloorsOnIsNull() {
            addCriterion("COM_FLOORS_ON is null");
            return (Criteria) this;
        }

        public Criteria andComFloorsOnIsNotNull() {
            addCriterion("COM_FLOORS_ON is not null");
            return (Criteria) this;
        }

        public Criteria andComFloorsOnEqualTo(String value) {
            addCriterion("COM_FLOORS_ON =", value, "comFloorsOn");
            return (Criteria) this;
        }

        public Criteria andComFloorsOnNotEqualTo(String value) {
            addCriterion("COM_FLOORS_ON <>", value, "comFloorsOn");
            return (Criteria) this;
        }

        public Criteria andComFloorsOnGreaterThan(String value) {
            addCriterion("COM_FLOORS_ON >", value, "comFloorsOn");
            return (Criteria) this;
        }

        public Criteria andComFloorsOnGreaterThanOrEqualTo(String value) {
            addCriterion("COM_FLOORS_ON >=", value, "comFloorsOn");
            return (Criteria) this;
        }

        public Criteria andComFloorsOnLessThan(String value) {
            addCriterion("COM_FLOORS_ON <", value, "comFloorsOn");
            return (Criteria) this;
        }

        public Criteria andComFloorsOnLessThanOrEqualTo(String value) {
            addCriterion("COM_FLOORS_ON <=", value, "comFloorsOn");
            return (Criteria) this;
        }

        public Criteria andComFloorsOnLike(String value) {
            addCriterion("COM_FLOORS_ON like", value, "comFloorsOn");
            return (Criteria) this;
        }

        public Criteria andComFloorsOnNotLike(String value) {
            addCriterion("COM_FLOORS_ON not like", value, "comFloorsOn");
            return (Criteria) this;
        }

        public Criteria andComFloorsOnIn(List<String> values) {
            addCriterion("COM_FLOORS_ON in", values, "comFloorsOn");
            return (Criteria) this;
        }

        public Criteria andComFloorsOnNotIn(List<String> values) {
            addCriterion("COM_FLOORS_ON not in", values, "comFloorsOn");
            return (Criteria) this;
        }

        public Criteria andComFloorsOnBetween(String value1, String value2) {
            addCriterion("COM_FLOORS_ON between", value1, value2, "comFloorsOn");
            return (Criteria) this;
        }

        public Criteria andComFloorsOnNotBetween(String value1, String value2) {
            addCriterion("COM_FLOORS_ON not between", value1, value2, "comFloorsOn");
            return (Criteria) this;
        }

        public Criteria andComFloorsDownIsNull() {
            addCriterion("COM_FLOORS_DOWN is null");
            return (Criteria) this;
        }

        public Criteria andComFloorsDownIsNotNull() {
            addCriterion("COM_FLOORS_DOWN is not null");
            return (Criteria) this;
        }

        public Criteria andComFloorsDownEqualTo(String value) {
            addCriterion("COM_FLOORS_DOWN =", value, "comFloorsDown");
            return (Criteria) this;
        }

        public Criteria andComFloorsDownNotEqualTo(String value) {
            addCriterion("COM_FLOORS_DOWN <>", value, "comFloorsDown");
            return (Criteria) this;
        }

        public Criteria andComFloorsDownGreaterThan(String value) {
            addCriterion("COM_FLOORS_DOWN >", value, "comFloorsDown");
            return (Criteria) this;
        }

        public Criteria andComFloorsDownGreaterThanOrEqualTo(String value) {
            addCriterion("COM_FLOORS_DOWN >=", value, "comFloorsDown");
            return (Criteria) this;
        }

        public Criteria andComFloorsDownLessThan(String value) {
            addCriterion("COM_FLOORS_DOWN <", value, "comFloorsDown");
            return (Criteria) this;
        }

        public Criteria andComFloorsDownLessThanOrEqualTo(String value) {
            addCriterion("COM_FLOORS_DOWN <=", value, "comFloorsDown");
            return (Criteria) this;
        }

        public Criteria andComFloorsDownLike(String value) {
            addCriterion("COM_FLOORS_DOWN like", value, "comFloorsDown");
            return (Criteria) this;
        }

        public Criteria andComFloorsDownNotLike(String value) {
            addCriterion("COM_FLOORS_DOWN not like", value, "comFloorsDown");
            return (Criteria) this;
        }

        public Criteria andComFloorsDownIn(List<String> values) {
            addCriterion("COM_FLOORS_DOWN in", values, "comFloorsDown");
            return (Criteria) this;
        }

        public Criteria andComFloorsDownNotIn(List<String> values) {
            addCriterion("COM_FLOORS_DOWN not in", values, "comFloorsDown");
            return (Criteria) this;
        }

        public Criteria andComFloorsDownBetween(String value1, String value2) {
            addCriterion("COM_FLOORS_DOWN between", value1, value2, "comFloorsDown");
            return (Criteria) this;
        }

        public Criteria andComFloorsDownNotBetween(String value1, String value2) {
            addCriterion("COM_FLOORS_DOWN not between", value1, value2, "comFloorsDown");
            return (Criteria) this;
        }

        public Criteria andComBaseAreaIsNull() {
            addCriterion("COM_BASE_AREA is null");
            return (Criteria) this;
        }

        public Criteria andComBaseAreaIsNotNull() {
            addCriterion("COM_BASE_AREA is not null");
            return (Criteria) this;
        }

        public Criteria andComBaseAreaEqualTo(String value) {
            addCriterion("COM_BASE_AREA =", value, "comBaseArea");
            return (Criteria) this;
        }

        public Criteria andComBaseAreaNotEqualTo(String value) {
            addCriterion("COM_BASE_AREA <>", value, "comBaseArea");
            return (Criteria) this;
        }

        public Criteria andComBaseAreaGreaterThan(String value) {
            addCriterion("COM_BASE_AREA >", value, "comBaseArea");
            return (Criteria) this;
        }

        public Criteria andComBaseAreaGreaterThanOrEqualTo(String value) {
            addCriterion("COM_BASE_AREA >=", value, "comBaseArea");
            return (Criteria) this;
        }

        public Criteria andComBaseAreaLessThan(String value) {
            addCriterion("COM_BASE_AREA <", value, "comBaseArea");
            return (Criteria) this;
        }

        public Criteria andComBaseAreaLessThanOrEqualTo(String value) {
            addCriterion("COM_BASE_AREA <=", value, "comBaseArea");
            return (Criteria) this;
        }

        public Criteria andComBaseAreaLike(String value) {
            addCriterion("COM_BASE_AREA like", value, "comBaseArea");
            return (Criteria) this;
        }

        public Criteria andComBaseAreaNotLike(String value) {
            addCriterion("COM_BASE_AREA not like", value, "comBaseArea");
            return (Criteria) this;
        }

        public Criteria andComBaseAreaIn(List<String> values) {
            addCriterion("COM_BASE_AREA in", values, "comBaseArea");
            return (Criteria) this;
        }

        public Criteria andComBaseAreaNotIn(List<String> values) {
            addCriterion("COM_BASE_AREA not in", values, "comBaseArea");
            return (Criteria) this;
        }

        public Criteria andComBaseAreaBetween(String value1, String value2) {
            addCriterion("COM_BASE_AREA between", value1, value2, "comBaseArea");
            return (Criteria) this;
        }

        public Criteria andComBaseAreaNotBetween(String value1, String value2) {
            addCriterion("COM_BASE_AREA not between", value1, value2, "comBaseArea");
            return (Criteria) this;
        }

        public Criteria andComCoveredAreaOnIsNull() {
            addCriterion("COM_COVERED_AREA_ON is null");
            return (Criteria) this;
        }

        public Criteria andComCoveredAreaOnIsNotNull() {
            addCriterion("COM_COVERED_AREA_ON is not null");
            return (Criteria) this;
        }

        public Criteria andComCoveredAreaOnEqualTo(String value) {
            addCriterion("COM_COVERED_AREA_ON =", value, "comCoveredAreaOn");
            return (Criteria) this;
        }

        public Criteria andComCoveredAreaOnNotEqualTo(String value) {
            addCriterion("COM_COVERED_AREA_ON <>", value, "comCoveredAreaOn");
            return (Criteria) this;
        }

        public Criteria andComCoveredAreaOnGreaterThan(String value) {
            addCriterion("COM_COVERED_AREA_ON >", value, "comCoveredAreaOn");
            return (Criteria) this;
        }

        public Criteria andComCoveredAreaOnGreaterThanOrEqualTo(String value) {
            addCriterion("COM_COVERED_AREA_ON >=", value, "comCoveredAreaOn");
            return (Criteria) this;
        }

        public Criteria andComCoveredAreaOnLessThan(String value) {
            addCriterion("COM_COVERED_AREA_ON <", value, "comCoveredAreaOn");
            return (Criteria) this;
        }

        public Criteria andComCoveredAreaOnLessThanOrEqualTo(String value) {
            addCriterion("COM_COVERED_AREA_ON <=", value, "comCoveredAreaOn");
            return (Criteria) this;
        }

        public Criteria andComCoveredAreaOnLike(String value) {
            addCriterion("COM_COVERED_AREA_ON like", value, "comCoveredAreaOn");
            return (Criteria) this;
        }

        public Criteria andComCoveredAreaOnNotLike(String value) {
            addCriterion("COM_COVERED_AREA_ON not like", value, "comCoveredAreaOn");
            return (Criteria) this;
        }

        public Criteria andComCoveredAreaOnIn(List<String> values) {
            addCriterion("COM_COVERED_AREA_ON in", values, "comCoveredAreaOn");
            return (Criteria) this;
        }

        public Criteria andComCoveredAreaOnNotIn(List<String> values) {
            addCriterion("COM_COVERED_AREA_ON not in", values, "comCoveredAreaOn");
            return (Criteria) this;
        }

        public Criteria andComCoveredAreaOnBetween(String value1, String value2) {
            addCriterion("COM_COVERED_AREA_ON between", value1, value2, "comCoveredAreaOn");
            return (Criteria) this;
        }

        public Criteria andComCoveredAreaOnNotBetween(String value1, String value2) {
            addCriterion("COM_COVERED_AREA_ON not between", value1, value2, "comCoveredAreaOn");
            return (Criteria) this;
        }

        public Criteria andComCoveredAreaDownIsNull() {
            addCriterion("COM_COVERED_AREA_DOWN is null");
            return (Criteria) this;
        }

        public Criteria andComCoveredAreaDownIsNotNull() {
            addCriterion("COM_COVERED_AREA_DOWN is not null");
            return (Criteria) this;
        }

        public Criteria andComCoveredAreaDownEqualTo(String value) {
            addCriterion("COM_COVERED_AREA_DOWN =", value, "comCoveredAreaDown");
            return (Criteria) this;
        }

        public Criteria andComCoveredAreaDownNotEqualTo(String value) {
            addCriterion("COM_COVERED_AREA_DOWN <>", value, "comCoveredAreaDown");
            return (Criteria) this;
        }

        public Criteria andComCoveredAreaDownGreaterThan(String value) {
            addCriterion("COM_COVERED_AREA_DOWN >", value, "comCoveredAreaDown");
            return (Criteria) this;
        }

        public Criteria andComCoveredAreaDownGreaterThanOrEqualTo(String value) {
            addCriterion("COM_COVERED_AREA_DOWN >=", value, "comCoveredAreaDown");
            return (Criteria) this;
        }

        public Criteria andComCoveredAreaDownLessThan(String value) {
            addCriterion("COM_COVERED_AREA_DOWN <", value, "comCoveredAreaDown");
            return (Criteria) this;
        }

        public Criteria andComCoveredAreaDownLessThanOrEqualTo(String value) {
            addCriterion("COM_COVERED_AREA_DOWN <=", value, "comCoveredAreaDown");
            return (Criteria) this;
        }

        public Criteria andComCoveredAreaDownLike(String value) {
            addCriterion("COM_COVERED_AREA_DOWN like", value, "comCoveredAreaDown");
            return (Criteria) this;
        }

        public Criteria andComCoveredAreaDownNotLike(String value) {
            addCriterion("COM_COVERED_AREA_DOWN not like", value, "comCoveredAreaDown");
            return (Criteria) this;
        }

        public Criteria andComCoveredAreaDownIn(List<String> values) {
            addCriterion("COM_COVERED_AREA_DOWN in", values, "comCoveredAreaDown");
            return (Criteria) this;
        }

        public Criteria andComCoveredAreaDownNotIn(List<String> values) {
            addCriterion("COM_COVERED_AREA_DOWN not in", values, "comCoveredAreaDown");
            return (Criteria) this;
        }

        public Criteria andComCoveredAreaDownBetween(String value1, String value2) {
            addCriterion("COM_COVERED_AREA_DOWN between", value1, value2, "comCoveredAreaDown");
            return (Criteria) this;
        }

        public Criteria andComCoveredAreaDownNotBetween(String value1, String value2) {
            addCriterion("COM_COVERED_AREA_DOWN not between", value1, value2, "comCoveredAreaDown");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table form_gh_jsgc_ghyssqb_jg
     *
     * @mbggenerated do_not_delete_during_merge Mon Jun 20 14:23:31 CST 2016
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table form_gh_jsgc_ghyssqb_jg
     *
     * @mbggenerated Mon Jun 20 14:23:31 CST 2016
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