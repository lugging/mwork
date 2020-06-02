package com.yuntongxun.mwork.controller.support;

import com.yuntongxun.mwork.flow.support.IFlowDispatch;
import com.yuntongxun.mwork.vo.support.IReq;
import com.yuntongxun.mwork.vo.support.IRsp;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @program: mwork
 * @description: base
 * @author: liugang
 * @create: 2020-05-13 09:12
 **/
public abstract class BaseController{

    @Autowired
    private IFlowDispatch dispatch;

    /**
     * 根据手工配置flowId 调用
     * @param flowId
     * @param req
     * @param <P>
     * @param <R>
     * @return
     */
    public <P extends IRsp, R extends IReq> P dispatch(String flowId, R req){
        return dispatch.dispatch(flowId, req);
    }

    /**
     * 根据入参类型 调用
     * @param req
     * @param <P>
     * @param <R>
     * @return
     */
    public <P extends IRsp, R extends IReq> P dispatch(R req){
        return dispatch.dispatch(req);
    }
}
