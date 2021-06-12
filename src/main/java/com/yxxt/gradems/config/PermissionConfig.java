package com.yxxt.gradems.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PermissionConfig {
    // 角色名称
    public final String role_admin   = "ROLE_ADMIN";
    public final String role_student = "ROLE_STUDENT";
    public final String role_teacher = "ROLE_TEACHER";
    public final String role_visitor = "ROLE_VISITOR";
    
    public static final int LOGIN = 1;
    // 登陆后拥有的权限，否则不能访问所有页面
    public static final int SELECT_COURSE = 2;
    // 选课权限，只有学生有权限

    public static final int TEACH_COURSE = 4;
    // 教课程 可以拉取某一个班级的课程，老师和助教都行
    // 可以登记成绩 老师和助教都行

    public static final int APPEAL = 16;
    // 成绩申诉 学生可以发起

    public static final int UPLOAD = 32;
    // 提交成绩和修改成绩 老师可以助教不行

    public static final int VERIFY = 64;
    // 确定修改 教务人员身份可以

    public static final int ADMIN = 128;
    // 查看所有课程的信息

    public static HashMap<Integer, String> permissionMap = new HashMap<>();
    static {
        permissionMap.put(LOGIN, "login");
        permissionMap.put(SELECT_COURSE, "select_course");
        permissionMap.put(TEACH_COURSE, "teach_course");
        permissionMap.put(APPEAL, "appeal");
        permissionMap.put(UPLOAD, "upload");
        permissionMap.put(VERIFY, "verify");
        permissionMap.put(ADMIN, "admin");
    }



    public static List<String> getPermissions(Integer permission){
        List<String> permissionList = new ArrayList<>();
        permissionMap.forEach((k,v)->{
            if((permission & k)!=0){
                permissionList.add(v);
            }
        });
        return permissionList;
    }
}
