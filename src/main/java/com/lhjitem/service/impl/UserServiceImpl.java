package com.lhjitem.service.impl;

import com.lhjitem.dao.UserDao;
import com.lhjitem.dao.impl.UserDaoImpl;
import com.lhjitem.pojo.User;
import com.lhjitem.service.UserService;

public class UserServiceImpl implements UserService {
    //private UserDao userDao = new UserServiceImpl();
    private  UserDao userDao = new UserDaoImpl();
    @Override
    public void registerUser(User user) {
        userDao.saveUser(user);
    }

    @Override
    public User login(User user) {
        return userDao.queryUserByUsernameAndPassword(user.getUsername(),user.getPassword());
    }

    @Override
    public boolean existsUsername(String username) {
        if(userDao.queryUserByUsername(username) == null){
            //等于null，就是没查到，所以用户名可用,这里返回false就是没查到
            return false;
        }
        return true;
    }

}
