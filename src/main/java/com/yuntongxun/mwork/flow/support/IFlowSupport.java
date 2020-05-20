package com.yuntongxun.mwork.flow.support;

import com.yuntongxun.mwork.flow.support.exception.BaseException;

/**
* @Description:
* @Param:  
* @return:  
* @Author: liugang
* @Date: 2020/5/12 19:13
*/
public interface IFlowSupport<RSP, REQ> extends IFlow<RSP, REQ> {

    /**
     * 前置处理
     * @param req
     */
    default void preHandler(REQ req){ }

    /**
     * 前置 效验
     * @param req
     */
    void validation(REQ req);

    /**
     * 参数效验执行类
     * @param req
     */
    void validationHandler(REQ req);

    /**
     * 后置处理
     * @param rsp
     * @param req
     */
    default void afterHandler(RSP rsp, REQ req){}

    /**
     * 异常处理
     * @param e
     * @param req
     */
    default void exceptionHandler(BaseException e, REQ req){
        throw e;
    }
}
