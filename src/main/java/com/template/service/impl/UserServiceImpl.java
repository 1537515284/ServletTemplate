package com.template.service.impl;


import com.template.dao.UserDAO;
import com.template.entity.User;
import com.template.service.UserService;

public class UserServiceImpl implements UserService {

    UserDAO userDAO = new UserDAO();

    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        String sql = "SELECT id,username,password,nickname FROM user WHERE username = ? AND password = ?";
        return userDAO.querySingle(sql, User.class, username,password);
    }

    @Override
    public boolean checkUser(String username, String password) {
        User user = findUserByUsernameAndPassword(username, password);
        return user != null;
    }
}
