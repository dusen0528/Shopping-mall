package com.nhnacademy.shoppingmall.common.mvc.controller;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.transaction.Transactional;


public class TransactionalControllerProxy implements BaseController {

    private final BaseController controller;

    public TransactionalControllerProxy(BaseController baseController){
        controller =  baseController;

    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        if(controller.getClass().isAnnotationPresent(Transactional.class)){
            try{
                DbConnectionThreadLocal.initialize();
                return controller.execute(req, resp);
            } catch (Exception e) {
                DbConnectionThreadLocal.setSqlError(true);
                throw e;
            }finally {
                DbConnectionThreadLocal.reset();
            }
        }
        return controller.execute(req, resp);
    }
}
