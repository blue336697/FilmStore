package com.lhjitem.service;

import com.lhjitem.pojo.User;

public interface UserService {
    //业务层
    /*
     * 方法描述 注册用户
     * @param: user
     * @return:
     */
    public void registerUser(User user);

    //用户登录
    //返回null等于登录失败，返回有值是登陆成功
    public User login(User user);



    /*
     * 方法描述 检查用户名是否可用
     * @param: username
     * @return: 返回true表示用户名已存在，返回false表示用户名可用
     */
    public boolean existsUsername(String username);
}
