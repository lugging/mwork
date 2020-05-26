package com.yuntongxun.mwork.filter.support;

import com.yuntongxun.mwork.flow.support.exception.BusinessException;

/**
 * @program: mwork
 * @description: 鉴权失败
 * @author: liugang
 * @create: 2020-05-25 17:05
 **/
public class SignatureException extends BusinessException {

    public SignatureException(int code){
        super(code);
    }
}
