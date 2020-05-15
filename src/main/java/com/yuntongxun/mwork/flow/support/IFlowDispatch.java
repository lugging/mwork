package com.yuntongxun.mwork.flow.support;

/**
 * @program: mwork
 * @description: 服务流程处理类
 * @author: liugang
 * @create: 2020-05-12 17:44
 **/
public interface IFlowDispatch {

    /**
     * 调用业务流程, 请求响应模型
     * @param flowId 流程ID
     * @param req 请求参数
     * @return p
     */
   <RSP, REQ> RSP dispatch(String flowId, REQ req);

    /**
     * 注册 FLOW
     * @param flowId
     * @param flowHandler
     */
   void register(String flowId, IFlow flowHandler);
}
