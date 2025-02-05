package com.nhnacademy.shoppingmall.controller.page;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RequestMapping(method = RequestMapping.Method.GET, value = "/change_password.do")
public class ChangePasswordFormController implements BaseController {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {

        HttpSession session = req.getSession(false);
        if(session==null || session.getAttribute("user")==null){
            return "redirect:/login.do";
        }

        return "shop/user/changepassword";

    }
}
