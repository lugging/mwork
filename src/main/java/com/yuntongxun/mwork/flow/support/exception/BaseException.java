package com.yuntongxun.mwork.flow.support.exception;

/**
 * @program: mwork
 * @description:
 * @author: liugang
 * @create: 2020-05-13 09:28
 **/
public class BaseException extends RuntimeException{

    protected int code;

    public BaseException(){ }

    public BaseException(int code){
        this.code = code;
    }

    public BaseException(int code, String message){
        super(message);
        this.code = code;
    }

    public BaseException(int code, Throwable e){
        super(e);
        this.code = code;
    }

    public BaseException(String message){
        super(message);
    }

    public int getCode(){
        return code;
    }
}
