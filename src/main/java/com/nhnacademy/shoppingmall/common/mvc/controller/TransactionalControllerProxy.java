package com.nhnacademy.shoppingmall.common.mvc.controller;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;


@Slf4j
public class TransactionalControllerProxy implements BaseController {

    private final BaseController controller;

    public TransactionalControllerProxy(BaseController baseController){
        controller =  baseController;

    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        if(controller.getClass().isAnnotationPresent(Transactional.class)){
            DbConnectionThreadLocal.initialize();
            try{
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
