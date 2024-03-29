package com.lpcode.modules.blsp.entity;

import java.util.ArrayList;
import java.util.List;

public class PrjFetchViewExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table prj_fetch_view
     *
     * @mbggenerated Thu May 11 16:54:43 CST 2017
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table prj_fetch_view
     *
     * @mbggenerated Thu May 11 16:54:43 CST 2017
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table prj_fetch_view
     *
     * @mbggenerated Thu May 11 16:54:43 CST 2017
     */
    protected List<Criteria> oredCriteria;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table prj_fetch_view
     *
     * @mbggenerated Thu May 11 16:54:43 CST 2017
     */
    protected int start = -1;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table prj_fetch_view
     *
     * @mbggenerated Thu May 11 16:54:43 CST 2017
     */
    protected int pageSize = -1;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_fetch_view
     *
     * @mbggenerated Thu May 11 16:54:43 CST 2017
     */
    public PrjFetchViewExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_fetch_view
     *
     * @mbggenerated Thu May 11 16:54:43 CST 2017
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_fetch_view
     *
     * @mbggenerated Thu May 11 16:54:43 CST 2017
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_fetch_view
     *
     * @mbggenerated Thu May 11 16:54:43 CST 2017
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_fetch_view
     *
     * @mbggenerated Thu May 11 16:54:43 CST 2017
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_fetch_view
     *
     * @mbggenerated Thu May 11 16:54:43 CST 2017
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_fetch_view
     *
     * @mbggenerated Thu May 11 16:54:43 CST 2017
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_fetch_view
     *
     * @mbggenerated Thu May 11 16:54:43 CST 2017
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_fetch_view
     *
     * @mbggenerated Thu May 11 16:54:43 CST 2017
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
     * This method corresponds to the database table prj_fetch_view
     *
     * @mbggenerated Thu May 11 16:54:43 CST 2017
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_fetch_view
     *
     * @mbggenerated Thu May 11 16:54:43 CST 2017
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_fetch_view
     *
     * @mbggenerated Thu May 11 16:54:43 CST 2017
     */
    public void setStart(int start) {
        this.start=start;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_fetch_view
     *
     * @mbggenerated Thu May 11 16:54:43 CST 2017
     */
    public int getStart() {
        return start;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_fetch_view
     *
     * @mbggenerated Thu May 11 16:54:43 CST 2017
     */
    public void setPageSize(int pageSize) {
        this.pageSize=pageSize;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table prj_fetch_view
     *
     * @mbggenerated Thu May 11 16:54:43 CST 2017
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table prj_fetch_view
     *
     * @mbggenerated Thu May 11 16:54:43 CST 2017
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

        public Criteria andFetchCertNumIsNull() {
            addCriterion("fetch_cert_num is null");
            return (Criteria) this;
        }

        public Criteria andFetchCertNumIsNotNull() {
            addCriterion("fetch_cert_num is not null");
            return (Criteria) this;
        }

        public Criteria andFetchCertNumEqualTo(Long value) {
            addCriterion("fetch_cert_num =", value, "fetchCertNum");
            return (Criteria) this;
        }

        public Criteria andFetchCertNumNotEqualTo(Long value) {
            addCriterion("fetch_cert_num <>", value, "fetchCertNum");
            return (Criteria) this;
        }

        public Criteria andFetchCertNumGreaterThan(Long value) {
            addCriterion("fetch_cert_num >", value, "fetchCertNum");
            return (Criteria) this;
        }

        public Criteria andFetchCertNumGreaterThanOrEqualTo(Long value) {
            addCriterion("fetch_cert_num >=", value, "fetchCertNum");
            return (Criteria) this;
        }

        public Criteria andFetchCertNumLessThan(Long value) {
            addCriterion("fetch_cert_num <", value, "fetchCertNum");
            return (Criteria) this;
        }

        public Criteria andFetchCertNumLessThanOrEqualTo(Long value) {
            addCriterion("fetch_cert_num <=", value, "fetchCertNum");
            return (Criteria) this;
        }

        public Criteria andFetchCertNumIn(List<Long> values) {
            addCriterion("fetch_cert_num in", values, "fetchCertNum");
            return (Criteria) this;
        }

        public Criteria andFetchCertNumNotIn(List<Long> values) {
            addCriterion("fetch_cert_num not in", values, "fetchCertNum");
            return (Criteria) this;
        }

        public Criteria andFetchCertNumBetween(Long value1, Long value2) {
            addCriterion("fetch_cert_num between", value1, value2, "fetchCertNum");
            return (Criteria) this;
        }

        public Criteria andFetchCertNumNotBetween(Long value1, Long value2) {
            addCriterion("fetch_cert_num not between", value1, value2, "fetchCertNum");
            return (Criteria) this;
        }

        public Criteria andUploadNumIsNull() {
            addCriterion("upload_num is null");
            return (Criteria) this;
        }

        public Criteria andUploadNumIsNotNull() {
            addCriterion("upload_num is not null");
            return (Criteria) this;
        }

        public Criteria andUploadNumEqualTo(Long value) {
            addCriterion("upload_num =", value, "uploadNum");
            return (Criteria) this;
        }

        public Criteria andUploadNumNotEqualTo(Long value) {
            addCriterion("upload_num <>", value, "uploadNum");
            return (Criteria) this;
        }

        public Criteria andUploadNumGreaterThan(Long value) {
            addCriterion("upload_num >", value, "uploadNum");
            return (Criteria) this;
        }

        public Criteria andUploadNumGreaterThanOrEqualTo(Long value) {
            addCriterion("upload_num >=", value, "uploadNum");
            return (Criteria) this;
        }

        public Criteria andUploadNumLessThan(Long value) {
            addCriterion("upload_num <", value, "uploadNum");
            return (Criteria) this;
        }

        public Criteria andUploadNumLessThanOrEqualTo(Long value) {
            addCriterion("upload_num <=", value, "uploadNum");
            return (Criteria) this;
        }

        public Criteria andUploadNumIn(List<Long> values) {
            addCriterion("upload_num in", values, "uploadNum");
            return (Criteria) this;
        }

        public Criteria andUploadNumNotIn(List<Long> values) {
            addCriterion("upload_num not in", values, "uploadNum");
            return (Criteria) this;
        }

        public Criteria andUploadNumBetween(Long value1, Long value2) {
            addCriterion("upload_num between", value1, value2, "uploadNum");
            return (Criteria) this;
        }

        public Criteria andUploadNumNotBetween(Long value1, Long value2) {
            addCriterion("upload_num not between", value1, value2, "uploadNum");
            return (Criteria) this;
        }

        public Criteria andPrjIdIsNull() {
            addCriterion("prj_id is null");
            return (Criteria) this;
        }

        public Criteria andPrjIdIsNotNull() {
            addCriterion("prj_id is not null");
            return (Criteria) this;
        }

        public Criteria andPrjIdEqualTo(Long value) {
            addCriterion("prj_id =", value, "prjId");
            return (Criteria) this;
        }

        public Criteria andPrjIdNotEqualTo(Long value) {
            addCriterion("prj_id <>", value, "prjId");
            return (Criteria) this;
        }

        public Criteria andPrjIdGreaterThan(Long value) {
            addCriterion("prj_id >", value, "prjId");
            return (Criteria) this;
        }

        public Criteria andPrjIdGreaterThanOrEqualTo(Long value) {
            addCriterion("prj_id >=", value, "prjId");
            return (Criteria) this;
        }

        public Criteria andPrjIdLessThan(Long value) {
            addCriterion("prj_id <", value, "prjId");
            return (Criteria) this;
        }

        public Criteria andPrjIdLessThanOrEqualTo(Long value) {
            addCriterion("prj_id <=", value, "prjId");
            return (Criteria) this;
        }

        public Criteria andPrjIdIn(List<Long> values) {
            addCriterion("prj_id in", values, "prjId");
            return (Criteria) this;
        }

        public Criteria andPrjIdNotIn(List<Long> values) {
            addCriterion("prj_id not in", values, "prjId");
            return (Criteria) this;
        }

        public Criteria andPrjIdBetween(Long value1, Long value2) {
            addCriterion("prj_id between", value1, value2, "prjId");
            return (Criteria) this;
        }

        public Criteria andPrjIdNotBetween(Long value1, Long value2) {
            addCriterion("prj_id not between", value1, value2, "prjId");
            return (Criteria) this;
        }

        public Criteria andIsWithCertIsNull() {
            addCriterion("is_with_cert is null");
            return (Criteria) this;
        }

        public Criteria andIsWithCertIsNotNull() {
            addCriterion("is_with_cert is not null");
            return (Criteria) this;
        }

        public Criteria andIsWithCertEqualTo(String value) {
            addCriterion("is_with_cert =", value, "isWithCert");
            return (Criteria) this;
        }

        public Criteria andIsWithCertNotEqualTo(String value) {
            addCriterion("is_with_cert <>", value, "isWithCert");
            return (Criteria) this;
        }

        public Criteria andIsWithCertGreaterThan(String value) {
            addCriterion("is_with_cert >", value, "isWithCert");
            return (Criteria) this;
        }

        public Criteria andIsWithCertGreaterThanOrEqualTo(String value) {
            addCriterion("is_with_cert >=", value, "isWithCert");
            return (Criteria) this;
        }

        public Criteria andIsWithCertLessThan(String value) {
            addCriterion("is_with_cert <", value, "isWithCert");
            return (Criteria) this;
        }

        public Criteria andIsWithCertLessThanOrEqualTo(String value) {
            addCriterion("is_with_cert <=", value, "isWithCert");
            return (Criteria) this;
        }

        public Criteria andIsWithCertLike(String value) {
            addCriterion("is_with_cert like", value, "isWithCert");
            return (Criteria) this;
        }

        public Criteria andIsWithCertNotLike(String value) {
            addCriterion("is_with_cert not like", value, "isWithCert");
            return (Criteria) this;
        }

        public Criteria andIsWithCertIn(List<String> values) {
            addCriterion("is_with_cert in", values, "isWithCert");
            return (Criteria) this;
        }

        public Criteria andIsWithCertNotIn(List<String> values) {
            addCriterion("is_with_cert not in", values, "isWithCert");
            return (Criteria) this;
        }

        public Criteria andIsWithCertBetween(String value1, String value2) {
            addCriterion("is_with_cert between", value1, value2, "isWithCert");
            return (Criteria) this;
        }

        public Criteria andIsWithCertNotBetween(String value1, String value2) {
            addCriterion("is_with_cert not between", value1, value2, "isWithCert");
            return (Criteria) this;
        }

        public Criteria andPrjTypeIsNull() {
            addCriterion("prj_type is null");
            return (Criteria) this;
        }

        public Criteria andPrjTypeIsNotNull() {
            addCriterion("prj_type is not null");
            return (Criteria) this;
        }

        public Criteria andPrjTypeEqualTo(String value) {
            addCriterion("prj_type =", value, "prjType");
            return (Criteria) this;
        }

        public Criteria andPrjTypeNotEqualTo(String value) {
            addCriterion("prj_type <>", value, "prjType");
            return (Criteria) this;
        }

        public Criteria andPrjTypeGreaterThan(String value) {
            addCriterion("prj_type >", value, "prjType");
            return (Criteria) this;
        }

        public Criteria andPrjTypeGreaterThanOrEqualTo(String value) {
            addCriterion("prj_type >=", value, "prjType");
            return (Criteria) this;
        }

        public Criteria andPrjTypeLessThan(String value) {
            addCriterion("prj_type <", value, "prjType");
            return (Criteria) this;
        }

        public Criteria andPrjTypeLessThanOrEqualTo(String value) {
            addCriterion("prj_type <=", value, "prjType");
            return (Criteria) this;
        }

        public Criteria andPrjTypeLike(String value) {
            addCriterion("prj_type like", value, "prjType");
            return (Criteria) this;
        }

        public Criteria andPrjTypeNotLike(String value) {
            addCriterion("prj_type not like", value, "prjType");
            return (Criteria) this;
        }

        public Criteria andPrjTypeIn(List<String> values) {
            addCriterion("prj_type in", values, "prjType");
            return (Criteria) this;
        }

        public Criteria andPrjTypeNotIn(List<String> values) {
            addCriterion("prj_type not in", values, "prjType");
            return (Criteria) this;
        }

        public Criteria andPrjTypeBetween(String value1, String value2) {
            addCriterion("prj_type between", value1, value2, "prjType");
            return (Criteria) this;
        }

        public Criteria andPrjTypeNotBetween(String value1, String value2) {
            addCriterion("prj_type not between", value1, value2, "prjType");
            return (Criteria) this;
        }

        public Criteria andPrjNameIsNull() {
            addCriterion("prj_name is null");
            return (Criteria) this;
        }

        public Criteria andPrjNameIsNotNull() {
            addCriterion("prj_name is not null");
            return (Criteria) this;
        }

        public Criteria andPrjNameEqualTo(String value) {
            addCriterion("prj_name =", value, "prjName");
            return (Criteria) this;
        }

        public Criteria andPrjNameNotEqualTo(String value) {
            addCriterion("prj_name <>", value, "prjName");
            return (Criteria) this;
        }

        public Criteria andPrjNameGreaterThan(String value) {
            addCriterion("prj_name >", value, "prjName");
            return (Criteria) this;
        }

        public Criteria andPrjNameGreaterThanOrEqualTo(String value) {
            addCriterion("prj_name >=", value, "prjName");
            return (Criteria) this;
        }

        public Criteria andPrjNameLessThan(String value) {
            addCriterion("prj_name <", value, "prjName");
            return (Criteria) this;
        }

        public Criteria andPrjNameLessThanOrEqualTo(String value) {
            addCriterion("prj_name <=", value, "prjName");
            return (Criteria) this;
        }

        public Criteria andPrjNameLike(String value) {
            addCriterion("prj_name like", value, "prjName");
            return (Criteria) this;
        }

        public Criteria andPrjNameNotLike(String value) {
            addCriterion("prj_name not like", value, "prjName");
            return (Criteria) this;
        }

        public Criteria andPrjNameIn(List<String> values) {
            addCriterion("prj_name in", values, "prjName");
            return (Criteria) this;
        }

        public Criteria andPrjNameNotIn(List<String> values) {
            addCriterion("prj_name not in", values, "prjName");
            return (Criteria) this;
        }

        public Criteria andPrjNameBetween(String value1, String value2) {
            addCriterion("prj_name between", value1, value2, "prjName");
            return (Criteria) this;
        }

        public Criteria andPrjNameNotBetween(String value1, String value2) {
            addCriterion("prj_name not between", value1, value2, "prjName");
            return (Criteria) this;
        }

        public Criteria andPrjCodeIsNull() {
            addCriterion("prj_code is null");
            return (Criteria) this;
        }

        public Criteria andPrjCodeIsNotNull() {
            addCriterion("prj_code is not null");
            return (Criteria) this;
        }

        public Criteria andPrjCodeEqualTo(String value) {
            addCriterion("prj_code =", value, "prjCode");
            return (Criteria) this;
        }

        public Criteria andPrjCodeNotEqualTo(String value) {
            addCriterion("prj_code <>", value, "prjCode");
            return (Criteria) this;
        }

        public Criteria andPrjCodeGreaterThan(String value) {
            addCriterion("prj_code >", value, "prjCode");
            return (Criteria) this;
        }

        public Criteria andPrjCodeGreaterThanOrEqualTo(String value) {
            addCriterion("prj_code >=", value, "prjCode");
            return (Criteria) this;
        }

        public Criteria andPrjCodeLessThan(String value) {
            addCriterion("prj_code <", value, "prjCode");
            return (Criteria) this;
        }

        public Criteria andPrjCodeLessThanOrEqualTo(String value) {
            addCriterion("prj_code <=", value, "prjCode");
            return (Criteria) this;
        }

        public Criteria andPrjCodeLike(String value) {
            addCriterion("prj_code like", value, "prjCode");
            return (Criteria) this;
        }

        public Criteria andPrjCodeNotLike(String value) {
            addCriterion("prj_code not like", value, "prjCode");
            return (Criteria) this;
        }

        public Criteria andPrjCodeIn(List<String> values) {
            addCriterion("prj_code in", values, "prjCode");
            return (Criteria) this;
        }

        public Criteria andPrjCodeNotIn(List<String> values) {
            addCriterion("prj_code not in", values, "prjCode");
            return (Criteria) this;
        }

        public Criteria andPrjCodeBetween(String value1, String value2) {
            addCriterion("prj_code between", value1, value2, "prjCode");
            return (Criteria) this;
        }

        public Criteria andPrjCodeNotBetween(String value1, String value2) {
            addCriterion("prj_code not between", value1, value2, "prjCode");
            return (Criteria) this;
        }

        public Criteria andCompanyIsNull() {
            addCriterion("company is null");
            return (Criteria) this;
        }

        public Criteria andCompanyIsNotNull() {
            addCriterion("company is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyEqualTo(String value) {
            addCriterion("company =", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotEqualTo(String value) {
            addCriterion("company <>", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyGreaterThan(String value) {
            addCriterion("company >", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyGreaterThanOrEqualTo(String value) {
            addCriterion("company >=", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyLessThan(String value) {
            addCriterion("company <", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyLessThanOrEqualTo(String value) {
            addCriterion("company <=", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyLike(String value) {
            addCriterion("company like", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotLike(String value) {
            addCriterion("company not like", value, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyIn(List<String> values) {
            addCriterion("company in", values, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotIn(List<String> values) {
            addCriterion("company not in", values, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyBetween(String value1, String value2) {
            addCriterion("company between", value1, value2, "company");
            return (Criteria) this;
        }

        public Criteria andCompanyNotBetween(String value1, String value2) {
            addCriterion("company not between", value1, value2, "company");
            return (Criteria) this;
        }

        public Criteria andPrjFetchStatusIsNull() {
            addCriterion("prj_fetch_status is null");
            return (Criteria) this;
        }

        public Criteria andPrjFetchStatusIsNotNull() {
            addCriterion("prj_fetch_status is not null");
            return (Criteria) this;
        }

        public Criteria andPrjFetchStatusEqualTo(String value) {
            addCriterion("prj_fetch_status =", value, "prjFetchStatus");
            return (Criteria) this;
        }

        public Criteria andPrjFetchStatusNotEqualTo(String value) {
            addCriterion("prj_fetch_status <>", value, "prjFetchStatus");
            return (Criteria) this;
        }

        public Criteria andPrjFetchStatusGreaterThan(String value) {
            addCriterion("prj_fetch_status >", value, "prjFetchStatus");
            return (Criteria) this;
        }

        public Criteria andPrjFetchStatusGreaterThanOrEqualTo(String value) {
            addCriterion("prj_fetch_status >=", value, "prjFetchStatus");
            return (Criteria) this;
        }

        public Criteria andPrjFetchStatusLessThan(String value) {
            addCriterion("prj_fetch_status <", value, "prjFetchStatus");
            return (Criteria) this;
        }

        public Criteria andPrjFetchStatusLessThanOrEqualTo(String value) {
            addCriterion("prj_fetch_status <=", value, "prjFetchStatus");
            return (Criteria) this;
        }

        public Criteria andPrjFetchStatusLike(String value) {
            addCriterion("prj_fetch_status like", value, "prjFetchStatus");
            return (Criteria) this;
        }

        public Criteria andPrjFetchStatusNotLike(String value) {
            addCriterion("prj_fetch_status not like", value, "prjFetchStatus");
            return (Criteria) this;
        }

        public Criteria andPrjFetchStatusIn(List<String> values) {
            addCriterion("prj_fetch_status in", values, "prjFetchStatus");
            return (Criteria) this;
        }

        public Criteria andPrjFetchStatusNotIn(List<String> values) {
            addCriterion("prj_fetch_status not in", values, "prjFetchStatus");
            return (Criteria) this;
        }

        public Criteria andPrjFetchStatusBetween(String value1, String value2) {
            addCriterion("prj_fetch_status between", value1, value2, "prjFetchStatus");
            return (Criteria) this;
        }

        public Criteria andPrjFetchStatusNotBetween(String value1, String value2) {
            addCriterion("prj_fetch_status not between", value1, value2, "prjFetchStatus");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table prj_fetch_view
     *
     * @mbggenerated do_not_delete_during_merge Thu May 11 16:54:43 CST 2017
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table prj_fetch_view
     *
     * @mbggenerated Thu May 11 16:54:43 CST 2017
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