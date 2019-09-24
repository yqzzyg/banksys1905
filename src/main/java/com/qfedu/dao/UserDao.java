package com.qfedu.dao;

import com.qfedu.entity.User;

public interface UserDao {

    public User findByCode(String bankCode);

    //加钱
    void increaseToMoney(User user);
    //减钱
    void decreaseFromMoney(User user);
}
