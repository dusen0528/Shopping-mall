package com.nhnacademy.shoppingmall.user.service;

import com.nhnacademy.shoppingmall.user.domain.User;

import java.time.LocalDateTime;

public interface UserService {

    User getUser(String userId);

    void saveUser(User user);

    void updateUser(User user);

    void deleteUser(String userId);

    User doLogin(String userId, String userPassword);

    int updateLatestLoginAtByUserId(String userId, LocalDateTime latestLoginAt);
}
