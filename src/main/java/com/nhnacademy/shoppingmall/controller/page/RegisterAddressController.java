package com.nhnacademy.shoppingmall.controller.page;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.address.repository.impl.AddressRepositoryImpl;
import com.nhnacademy.shoppingmall.address.service.AddressService;
import com.nhnacademy.shoppingmall.address.service.impl.AddressServiceImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.transaction.Transactional;
import java.util.UUID;

@Transactional
@RequestMapping(method =  RequestMapping.Method.POST, value = "/address/register.do")
public class RegisterAddressController implements BaseController {

    AddressService addressService = new AddressServiceImpl(new AddressRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        if(session == null || session.getAttribute("user")==null){
            return "redirect:/login.do";
        }

        User user = (User) session.getAttribute("user");

        Address address = new Address(
                UUID.randomUUID().toString(),
                user.getUserId(),
                req.getParameter("recipientName"),
                req.getParameter("recipientPhone"),
                req.getParameter("address"),
                "true".equals(req.getParameter("isDefault"))
        );

        addressService.saveAddress(address);


        return "redirect:/mypage.do";

    }
}
