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
import org.apache.commons.lang3.StringUtils;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;


@Transactional
@RequestMapping(method = RequestMapping.Method.POST,value = "/loginAction.do")
public class LoginPostController implements BaseController {

    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
//        // TODO 2 아래와 같이 파라미터 검증하기
//        String id = req.getParameter("user_id");
//        String password = req.getParameter("user_password");
//        if (StringUtils.isEmpty(id) || StringUtils.isEmpty(password)) {
//            return "redirect:/login.do";
//        }

        //todo#13-2 로그인 구현, session은 60분동안 유지됩니다.
        User user = userService.doLogin(req.getParameter("user_id"), req.getParameter("user_password"));
        if(user==null){
            return "redirect:/login.do";
        }


        user.setLatestLoginAt(LocalDateTime.now());
        userService.updateUser(user);

        HttpSession session = req.getSession(true);
        session.setMaxInactiveInterval(3600);
        session.setAttribute("user", user);


        return "redirect:/index.do";
    }
}
