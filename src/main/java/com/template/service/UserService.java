package com.template.service;

import com.template.entity.User;

public interface UserService {
    User findUserByUsernameAndPassword(String username,String password);
    boolean checkUser(String username,String password);

}
