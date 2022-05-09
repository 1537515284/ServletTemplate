package com.template.dao;

import com.template.pojo.User;

public class UserDAO extends BasicDAO<User> {

    public User findUserByUsername(String username){
        String sql = "SELECT id,username,password FROM user WHERE username = ?";
        return this.querySingle(sql, User.class, username);
    }

    public User findUserByUsernameAndPassword(String username, String password) {
        String sql = "SELECT id,username,password FROM user WHERE username = ? AND password = ?";
        return this.querySingle(sql, User.class, username,password);
    }
}
