package com.yuntongxun.mwork.flow.support.exception;

/**
 * @program: mwork
 * @description: flow not found exception
 * @author: liugang
 * @create: 2020-05-12 18:16
 **/
public class FlowNotFoundException extends BaseException{

    public FlowNotFoundException(){
        super();
    }

    public FlowNotFoundException(String message) {
        super(message);
    }
}
