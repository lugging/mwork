package com.yuntongxun.mwork.flow.support;
import com.yuntongxun.mwork.flow.support.exception.FlowNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @program: mwork
 * @description:
 * 调度 flow 流程
 * @author: liugang
 * @create: 2020-05-12 17:50
 **/
@Slf4j
@Component
public class AbstractDispatchFlow implements IFlowDispatch {

    /**
     * 业务执行流程缓存
     */
    private static ConcurrentMap<String, IFlow> FLOW_MAP = new ConcurrentHashMap(16);

    /**
     * 业务调度方法
     * 各阶段通过抛出异常来中断执行
     * @param flowId 流程ID
     * @param req 请求参数
     * @return
     */
    @Override
    public <RSP, REQ> RSP dispatch(String flowId, REQ req) {
        log.info("dispatch flowId [{}]", flowId);
        IFlow flow = FLOW_MAP.get(flowId);
        if ( null == flow ){
            throw new FlowNotFoundException();
        }
        return (RSP) flow.execute(req);
    }

    /**
     * 未指定flowId 则，默认使用入参对象全类名
     * @param req
     * @param <RSP>
     * @param <REQ>
     * @return
     */
    @Override
    public <RSP, REQ> RSP dispatch(REQ req) {
        String flowId = req.getClass().getName();
        log.info("dispatch flowId [{}]", flowId);
        IFlow flow = FLOW_MAP.get(flowId);
        if ( null == flow ){
            throw new FlowNotFoundException();
        }
        return (RSP) flow.execute(req);
    }


    /**
     * 注册flow
     * @param flowId
     * @param flowHandler
     */
    @Override
    public void register(String flowId, IFlow flowHandler ){
        IFlow flow = FLOW_MAP.putIfAbsent(flowId, flowHandler);
        log.info("@register flowID {}, {}", flowId, flowHandler);
        if( flow != null ){
            log.error("@register flowId already {}, {}", flowId, flow);
        }
    }
}
