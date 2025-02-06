package com.nhnacademy.shoppingmall.controller.page;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.address.repository.impl.AddressRepositoryImpl;
import com.nhnacademy.shoppingmall.address.service.AddressService;
import com.nhnacademy.shoppingmall.address.service.impl.AddressServiceImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;

@Slf4j
@Transactional
@RequestMapping(method = RequestMapping.Method.GET, value = "/address/update.do")
public class UpdateAddressFormController implements BaseController {
    AddressService addressService = new AddressServiceImpl(new AddressRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        if(session == null || session.getAttribute("user")==null){
            return "redirect:/login.do";
        }

        String addressId=  req.getParameter("addressId");
        log.debug("Received addressId: {}", addressId);  // 로그 추가

        Address address = addressService.getAddress(addressId);
        log.debug("Found address: {}", address);  // 로그 추가


        req.setAttribute("address",address);

        return "/shop/address/update";
    }
}
