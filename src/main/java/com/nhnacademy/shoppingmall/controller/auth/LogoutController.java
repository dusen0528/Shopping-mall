package com.nhnacademy.shoppingmall.controller.auth;


import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RequestMapping(method =  RequestMapping.Method.GET, value = "/logout.do")
public class LogoutController implements BaseController {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        //todo#13-3 로그아웃 구현
        HttpSession session = req.getSession(false);

        if(session!=null){
            session.invalidate();
        }
        return "redirect:/index.do";
    }


}
