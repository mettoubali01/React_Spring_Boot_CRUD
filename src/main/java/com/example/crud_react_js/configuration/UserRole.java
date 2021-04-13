package com.example.crud_react_js.configuration;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.crud_react_js.configuration.UserPermission.*;

public enum UserRole {
    STUDENT(Sets.newHashSet(COURSES_READ, STUDENT_READ)),
    ADMIN(Sets.newHashSet(
            COURSES_READ,
            COURSES_WRITE,
            STUDENT_READ,
            STUDENT_WRITE)),

    ADMINTRAINEE(Sets.newHashSet(
            COURSES_READ,
          STUDENT_READ));

    private final Set<UserPermission> userPermissionSet;

    UserRole(Set<UserPermission> userPermissionSet) {
        this.userPermissionSet = userPermissionSet;
    }

    public Set<UserPermission> getUserPermissionSet() {
        return userPermissionSet;
    }

    public Set<SimpleGrantedAuthority> getSimpleGrantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getUserPermissionSet().stream()
                .map(userPermission -> new SimpleGrantedAuthority(userPermission.getPermission()))
                .collect(Collectors.toSet());

        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        for (SimpleGrantedAuthority s : permissions) {
            System.out.println("permission " + permissions);
        }
        return permissions;
    }
}
