package com.template.service.impl;

import com.template.dao.UserDAO;
import com.template.pojo.User;
import com.template.service.UserService;

public class UserServiceImpl implements UserService {

    private UserDAO userDAO = new UserDAO();

    @Override
    public User getUserByUsername(String username) {
        return userDAO.findUserByUsername(username);
    }

    @Override
    public User login(String username, String password) {
        return userDAO.findUserByUsernameAndPassword(username,password);
    }

}
