package com.mao.exception;

public class ParamsException extends RuntimeException {
    public ParamsException(){
        super();
    }
    public ParamsException(String message){
         super(message);

    }
    public ParamsException(String message,Throwable casuse){
        super(message,casuse);
    }
    public ParamsException(Throwable cause){
        super(cause);
    }
    public ParamsException(String message, Throwable cause,
                           boolean enableSuppression,
                           boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        }

}
