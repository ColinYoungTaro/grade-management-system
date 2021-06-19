package com.yxxt.gradems.resp;

import com.yxxt.gradems.domain.Course;
import com.yxxt.gradems.domain.CourseSchedule;
import com.yxxt.gradems.domain.SchoolUser;;

public class CourseScheduleResp {
    Course course;
    SchoolUser teacher;
    CourseSchedule schedule;

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public SchoolUser getTeacher() {
        return teacher;
    }

    public void setTeacher(SchoolUser teacher) {
        this.teacher = teacher;
    }

    public CourseSchedule getSchedule() {
        return schedule;
    }

    public void setSchedule(CourseSchedule schedule) {
        this.schedule = schedule;
    }


}
