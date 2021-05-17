package com.lhjitem.dao;

import com.lhjitem.pojo.User;

public interface UserDao{

    //根据用户名查询用户信息，返回null就证明没有
    public User queryUserByUsername(String username);

    //查询用户根据用户名和密码，返回null就证明没有
    public User queryUserByUsernameAndPassword(String username,String password);


    /*
     * 方法描述:保存用户信息
     * @param:  user
     * @return: 返回-1是操作失败，其他是sql影响行数
     */
    public int saveUser(User user);



}
