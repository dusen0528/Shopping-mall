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

import javax.transaction.Transactional;


@Transactional
@RequestMapping(method = RequestMapping.Method.POST, value = "/address/update.do")
public class UpdateAddressController implements BaseController {

    AddressService addressService = new AddressServiceImpl(new AddressRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        if(session==null || session.getAttribute("user")==null){
            return "redirect:/login.do";
        }

        String addressId = req.getParameter("addressId");
        Address address = addressService.getAddress(addressId);

        address.setRecipientName(req.getParameter("recipientName"));
        address.setRecipientPhone(req.getParameter("recipientPhone"));
        address.setAddress(req.getParameter("address"));
        address.setDefault("true".equals((req.getParameter("isDefault"))));

        addressService.updateAddress(address);
        return "redirect:/mypage.do";
    }
}
