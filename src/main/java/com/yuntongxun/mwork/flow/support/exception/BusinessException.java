package com.yuntongxun.mwork.flow.support.exception;

/**
 * @program: mwork
 * @description:
 * @author: liugang
 * @create: 2020-05-15 14:50
 **/
public class BusinessException extends BaseException {

    public BusinessException(){}

    public BusinessException(int code){
        super(code);
    }
}
