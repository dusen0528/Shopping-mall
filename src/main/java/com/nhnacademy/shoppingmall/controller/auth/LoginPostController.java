package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;


@Slf4j
@Transactional
@RequestMapping(method = RequestMapping.Method.POST, value = "/loginAction.do")
public class LoginPostController implements BaseController {

    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String userId = req.getParameter("user_id");
        String userPassword = req.getParameter("user_password");

        Optional<User> optionalUser = Optional.ofNullable(userService.doLogin(userId, userPassword));

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            HttpSession session = req.getSession(true);
            session.setMaxInactiveInterval(3600);
            session.setAttribute("user", user);
            session.setAttribute("userId", user.getUserId());
            log.info("Login successful for user: {} with role: {}", userId, user.getUserAuth());

            userService.updateLatestLoginAtByUserId(userId, LocalDateTime.now());
            return "redirect:/index.do";
        }else {
            log.warn("Login failed for user: {}", userId);
            req.setAttribute("loginFailed", true);
            return "shop/login/login_form";
        }
    }
}
