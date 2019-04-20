package com.mao.exception;


/*
@description: 权限异常
 @author mao
 */
public class PermissionException extends RuntimeException {
    public PermissionException(){
        super();
    }
    public PermissionException(String msg,Throwable cause){
        super(msg,cause);
    }
    public PermissionException(String msg){
        super(msg);
    }
    public PermissionException(String message, Throwable cause,
                           boolean enableSuppression,
                           boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
