package com.example.crud_react_js.auth;

import java.util.Optional;

public interface UserDetailDao {
    UserDetail selectUserDetailByUsername(String username);
}
