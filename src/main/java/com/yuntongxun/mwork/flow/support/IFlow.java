package com.yuntongxun.mwork.flow.support;


/**
* @Description: 流程处理类
* @Param:  
* @return:  
* @Author: liugang
* @Date: 2020/5/12 18:21
*/
public interface IFlow<RSP , REQ> {

    /**
     * 流程执行
     * @param req
     * @return rsp
     */
    RSP execute(REQ req);
}
