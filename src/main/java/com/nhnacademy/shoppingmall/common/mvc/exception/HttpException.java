package com.nhnacademy.shoppingmall.common.mvc.exception;

public class HttpException extends RuntimeException {
    public int getStatusCode() {
        return statusCode;
    }

    private final int statusCode;

    public HttpException(final int statusCode){
        this.statusCode =statusCode;
    }
    public HttpException(final int statusCode, String message){
        super(message);
        this.statusCode=statusCode;
    }
}
