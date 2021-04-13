package com.example.crud_react_js.configuration;

public enum UserPermission {
    STUDENT_READ("student:read"),
    STUDENT_WRITE("student:write"),
    COURSES_READ("courses:read"),
    COURSES_WRITE("courses:write");

    private final String permission;

    UserPermission(String permission){
        this.permission = permission;
    }

    public String getPermission(){
        return this.permission;
    }
}
