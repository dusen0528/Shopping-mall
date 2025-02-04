package com.nhnacademy.shoppingmall.controller.page;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@RequestMapping(method = RequestMapping.Method.GET, value = "mypage.do")
public class MyPageController implements BaseController {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);

        if(session == null){
            return "redirect:/login.do";
        }
        User user = (User)session.getAttribute("user");



        return "/shop/user/mypage";
    }
}
