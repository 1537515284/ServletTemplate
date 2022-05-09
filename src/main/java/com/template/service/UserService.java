package com.template.service;

import com.template.pojo.User;

public interface UserService {


    User getUserByUsername(String username);


    User login(String username, String password);
}
