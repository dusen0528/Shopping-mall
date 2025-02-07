package com.nhnacademy.shoppingmall.common.mvc.exception;

public class BadRequestException extends HttpException {

    private static final int STATUS_CODE = 400;

    public BadRequestException(){
        super(STATUS_CODE);
    }
    public BadRequestException(String message){
        super(STATUS_CODE, message);
    }
}
