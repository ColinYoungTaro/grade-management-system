package com.yxxt.gradems.domain;

import java.util.ArrayList;
import java.util.List;

public class ScoreAppealExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ScoreAppealExample() {
        oredCriteria = new ArrayList<>();
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
            criteria = new ArrayList<>();
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

        public Criteria andRowIdIsNull() {
            addCriterion("row_id is null");
            return (Criteria) this;
        }

        public Criteria andRowIdIsNotNull() {
            addCriterion("row_id is not null");
            return (Criteria) this;
        }

        public Criteria andRowIdEqualTo(Long value) {
            addCriterion("row_id =", value, "rowId");
            return (Criteria) this;
        }

        public Criteria andRowIdNotEqualTo(Long value) {
            addCriterion("row_id <>", value, "rowId");
            return (Criteria) this;
        }

        public Criteria andRowIdGreaterThan(Long value) {
            addCriterion("row_id >", value, "rowId");
            return (Criteria) this;
        }

        public Criteria andRowIdGreaterThanOrEqualTo(Long value) {
            addCriterion("row_id >=", value, "rowId");
            return (Criteria) this;
        }

        public Criteria andRowIdLessThan(Long value) {
            addCriterion("row_id <", value, "rowId");
            return (Criteria) this;
        }

        public Criteria andRowIdLessThanOrEqualTo(Long value) {
            addCriterion("row_id <=", value, "rowId");
            return (Criteria) this;
        }

        public Criteria andRowIdIn(List<Long> values) {
            addCriterion("row_id in", values, "rowId");
            return (Criteria) this;
        }

        public Criteria andRowIdNotIn(List<Long> values) {
            addCriterion("row_id not in", values, "rowId");
            return (Criteria) this;
        }

        public Criteria andRowIdBetween(Long value1, Long value2) {
            addCriterion("row_id between", value1, value2, "rowId");
            return (Criteria) this;
        }

        public Criteria andRowIdNotBetween(Long value1, Long value2) {
            addCriterion("row_id not between", value1, value2, "rowId");
            return (Criteria) this;
        }

        public Criteria andStudentIdIsNull() {
            addCriterion("student_id is null");
            return (Criteria) this;
        }

        public Criteria andStudentIdIsNotNull() {
            addCriterion("student_id is not null");
            return (Criteria) this;
        }

        public Criteria andStudentIdEqualTo(Long value) {
            addCriterion("student_id =", value, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdNotEqualTo(Long value) {
            addCriterion("student_id <>", value, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdGreaterThan(Long value) {
            addCriterion("student_id >", value, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdGreaterThanOrEqualTo(Long value) {
            addCriterion("student_id >=", value, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdLessThan(Long value) {
            addCriterion("student_id <", value, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdLessThanOrEqualTo(Long value) {
            addCriterion("student_id <=", value, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdIn(List<Long> values) {
            addCriterion("student_id in", values, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdNotIn(List<Long> values) {
            addCriterion("student_id not in", values, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdBetween(Long value1, Long value2) {
            addCriterion("student_id between", value1, value2, "studentId");
            return (Criteria) this;
        }

        public Criteria andStudentIdNotBetween(Long value1, Long value2) {
            addCriterion("student_id not between", value1, value2, "studentId");
            return (Criteria) this;
        }

        public Criteria andCourseUidIsNull() {
            addCriterion("course_uid is null");
            return (Criteria) this;
        }

        public Criteria andCourseUidIsNotNull() {
            addCriterion("course_uid is not null");
            return (Criteria) this;
        }

        public Criteria andCourseUidEqualTo(String value) {
            addCriterion("course_uid =", value, "courseUid");
            return (Criteria) this;
        }

        public Criteria andCourseUidNotEqualTo(String value) {
            addCriterion("course_uid <>", value, "courseUid");
            return (Criteria) this;
        }

        public Criteria andCourseUidGreaterThan(String value) {
            addCriterion("course_uid >", value, "courseUid");
            return (Criteria) this;
        }

        public Criteria andCourseUidGreaterThanOrEqualTo(String value) {
            addCriterion("course_uid >=", value, "courseUid");
            return (Criteria) this;
        }

        public Criteria andCourseUidLessThan(String value) {
            addCriterion("course_uid <", value, "courseUid");
            return (Criteria) this;
        }

        public Criteria andCourseUidLessThanOrEqualTo(String value) {
            addCriterion("course_uid <=", value, "courseUid");
            return (Criteria) this;
        }

        public Criteria andCourseUidLike(String value) {
            addCriterion("course_uid like", value, "courseUid");
            return (Criteria) this;
        }

        public Criteria andCourseUidNotLike(String value) {
            addCriterion("course_uid not like", value, "courseUid");
            return (Criteria) this;
        }

        public Criteria andCourseUidIn(List<String> values) {
            addCriterion("course_uid in", values, "courseUid");
            return (Criteria) this;
        }

        public Criteria andCourseUidNotIn(List<String> values) {
            addCriterion("course_uid not in", values, "courseUid");
            return (Criteria) this;
        }

        public Criteria andCourseUidBetween(String value1, String value2) {
            addCriterion("course_uid between", value1, value2, "courseUid");
            return (Criteria) this;
        }

        public Criteria andCourseUidNotBetween(String value1, String value2) {
            addCriterion("course_uid not between", value1, value2, "courseUid");
            return (Criteria) this;
        }

        public Criteria andClassIndexIsNull() {
            addCriterion("class_index is null");
            return (Criteria) this;
        }

        public Criteria andClassIndexIsNotNull() {
            addCriterion("class_index is not null");
            return (Criteria) this;
        }

        public Criteria andClassIndexEqualTo(Integer value) {
            addCriterion("class_index =", value, "classIndex");
            return (Criteria) this;
        }

        public Criteria andClassIndexNotEqualTo(Integer value) {
            addCriterion("class_index <>", value, "classIndex");
            return (Criteria) this;
        }

        public Criteria andClassIndexGreaterThan(Integer value) {
            addCriterion("class_index >", value, "classIndex");
            return (Criteria) this;
        }

        public Criteria andClassIndexGreaterThanOrEqualTo(Integer value) {
            addCriterion("class_index >=", value, "classIndex");
            return (Criteria) this;
        }

        public Criteria andClassIndexLessThan(Integer value) {
            addCriterion("class_index <", value, "classIndex");
            return (Criteria) this;
        }

        public Criteria andClassIndexLessThanOrEqualTo(Integer value) {
            addCriterion("class_index <=", value, "classIndex");
            return (Criteria) this;
        }

        public Criteria andClassIndexIn(List<Integer> values) {
            addCriterion("class_index in", values, "classIndex");
            return (Criteria) this;
        }

        public Criteria andClassIndexNotIn(List<Integer> values) {
            addCriterion("class_index not in", values, "classIndex");
            return (Criteria) this;
        }

        public Criteria andClassIndexBetween(Integer value1, Integer value2) {
            addCriterion("class_index between", value1, value2, "classIndex");
            return (Criteria) this;
        }

        public Criteria andClassIndexNotBetween(Integer value1, Integer value2) {
            addCriterion("class_index not between", value1, value2, "classIndex");
            return (Criteria) this;
        }

        public Criteria andOriginalScoreIsNull() {
            addCriterion("original_score is null");
            return (Criteria) this;
        }

        public Criteria andOriginalScoreIsNotNull() {
            addCriterion("original_score is not null");
            return (Criteria) this;
        }

        public Criteria andOriginalScoreEqualTo(Short value) {
            addCriterion("original_score =", value, "originalScore");
            return (Criteria) this;
        }

        public Criteria andOriginalScoreNotEqualTo(Short value) {
            addCriterion("original_score <>", value, "originalScore");
            return (Criteria) this;
        }

        public Criteria andOriginalScoreGreaterThan(Short value) {
            addCriterion("original_score >", value, "originalScore");
            return (Criteria) this;
        }

        public Criteria andOriginalScoreGreaterThanOrEqualTo(Short value) {
            addCriterion("original_score >=", value, "originalScore");
            return (Criteria) this;
        }

        public Criteria andOriginalScoreLessThan(Short value) {
            addCriterion("original_score <", value, "originalScore");
            return (Criteria) this;
        }

        public Criteria andOriginalScoreLessThanOrEqualTo(Short value) {
            addCriterion("original_score <=", value, "originalScore");
            return (Criteria) this;
        }

        public Criteria andOriginalScoreIn(List<Short> values) {
            addCriterion("original_score in", values, "originalScore");
            return (Criteria) this;
        }

        public Criteria andOriginalScoreNotIn(List<Short> values) {
            addCriterion("original_score not in", values, "originalScore");
            return (Criteria) this;
        }

        public Criteria andOriginalScoreBetween(Short value1, Short value2) {
            addCriterion("original_score between", value1, value2, "originalScore");
            return (Criteria) this;
        }

        public Criteria andOriginalScoreNotBetween(Short value1, Short value2) {
            addCriterion("original_score not between", value1, value2, "originalScore");
            return (Criteria) this;
        }

        public Criteria andAlteredScoreIsNull() {
            addCriterion("altered_score is null");
            return (Criteria) this;
        }

        public Criteria andAlteredScoreIsNotNull() {
            addCriterion("altered_score is not null");
            return (Criteria) this;
        }

        public Criteria andAlteredScoreEqualTo(Short value) {
            addCriterion("altered_score =", value, "alteredScore");
            return (Criteria) this;
        }

        public Criteria andAlteredScoreNotEqualTo(Short value) {
            addCriterion("altered_score <>", value, "alteredScore");
            return (Criteria) this;
        }

        public Criteria andAlteredScoreGreaterThan(Short value) {
            addCriterion("altered_score >", value, "alteredScore");
            return (Criteria) this;
        }

        public Criteria andAlteredScoreGreaterThanOrEqualTo(Short value) {
            addCriterion("altered_score >=", value, "alteredScore");
            return (Criteria) this;
        }

        public Criteria andAlteredScoreLessThan(Short value) {
            addCriterion("altered_score <", value, "alteredScore");
            return (Criteria) this;
        }

        public Criteria andAlteredScoreLessThanOrEqualTo(Short value) {
            addCriterion("altered_score <=", value, "alteredScore");
            return (Criteria) this;
        }

        public Criteria andAlteredScoreIn(List<Short> values) {
            addCriterion("altered_score in", values, "alteredScore");
            return (Criteria) this;
        }

        public Criteria andAlteredScoreNotIn(List<Short> values) {
            addCriterion("altered_score not in", values, "alteredScore");
            return (Criteria) this;
        }

        public Criteria andAlteredScoreBetween(Short value1, Short value2) {
            addCriterion("altered_score between", value1, value2, "alteredScore");
            return (Criteria) this;
        }

        public Criteria andAlteredScoreNotBetween(Short value1, Short value2) {
            addCriterion("altered_score not between", value1, value2, "alteredScore");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("`status` is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("`status` is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("`status` =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("`status` <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("`status` >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("`status` >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("`status` <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("`status` <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("`status` in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("`status` not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("`status` between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("`status` not between", value1, value2, "status");
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