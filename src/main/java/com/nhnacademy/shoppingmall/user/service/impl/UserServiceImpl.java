package com.nhnacademy.shoppingmall.user.service.impl;

import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;

import java.time.LocalDateTime;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(String userId){
        // #4-1 회원조회
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public void saveUser(User user) {
        // #4-2 회원등록
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        // #4-3 회원수정
        userRepository.update(user);
    }

    @Override
    public void deleteUser(String userId) {
        // #4-4 회원삭제
        userRepository.deleteByUserId(userId);
    }

    @Override
    public User doLogin(String userId, String userPassword) {
        // #4-5 로그인 구현, userId, userPassword로 일치하는 회원 조회
        return userRepository.findByUserIdAndUserPassword(userId, userPassword).orElse(null);
    }

    @Override
    public int updateLatestLoginAtByUserId(String userId, LocalDateTime latestLoginAt) {
        return userRepository.updateLatestLoginAtByUserId(userId, latestLoginAt);
    }


}
