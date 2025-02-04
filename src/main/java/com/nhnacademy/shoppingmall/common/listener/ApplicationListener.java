package com.nhnacademy.shoppingmall.common.listener;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import java.time.LocalDateTime;


@Slf4j
public class ApplicationListener implements ServletContextListener {
    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //todo#12 application 시작시 테스트 계정인 admin,user 등록합니다. 만약 존재하면 등록하지 않습니다.
        try{
            DbConnectionThreadLocal.initialize();

            User admin = userService.getUser("admin");
            User user = userService.getUser("user");
            LocalDateTime now = LocalDateTime.now();

            if (admin == null) {
                User newAdmin = new User(
                        "admin",         // userId
                        "관리자",        // userName
                        "123",        // userPassword
                        "2000-01-01",   // userBirth
                        User.Auth.ROLE_ADMIN,  // userAuth
                        1000,           // userPoint
                        now,            // createdAt
                        null            // latestLoginAt
                );
                userService.saveUser(newAdmin);
                log.info("Admin account created successfully");
            }

            if (user == null) {
                User newUser = new User(
                        "user",         // userId
                        "사용자",        // userName
                        "123",        // userPassword
                        "2000-01-01",   // userBirth
                        User.Auth.ROLE_USER,   // userAuth
                        1000,           // userPoint
                        now,            // createdAt
                        null            // latestLoginAt
                );
                userService.saveUser(newUser);
                log.info("User account created successfully");
            }


        } catch (Exception e) {
            DbConnectionThreadLocal.setSqlError(true);
            throw new RuntimeException(e);
        }finally {
            DbConnectionThreadLocal.reset();
        }

    }
}
