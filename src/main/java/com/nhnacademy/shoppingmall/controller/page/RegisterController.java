package com.nhnacademy.shoppingmall.controller.page;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Transactional
@RequestMapping(method =  RequestMapping.Method.POST, value = "/signup.do")
public class RegisterController implements BaseController {
    UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();

        String userid = req.getParameter("userId");
        String userpassword = req.getParameter("userPassword");
        String username = req.getParameter("userName");
        String userbirth = req.getParameter("userBirth");

        User user = new User(
                userid,
                username,
                userpassword,
                userbirth,
                User.Auth.ROLE_USER,
                1000000,  // 초기 포인트 100만
                LocalDateTime.now(),  // 생성 시간
                null  // 최근 로그인 시간은 null
        );

        userService.saveUser(user);

        return "redirect:/index.do";
    }
}
