package com.yuntongxun.mwork.runner;

import com.yuntongxun.mwork.flow.support.IFlow;
import com.yuntongxun.mwork.flow.support.IFlowDispatch;
import com.yuntongxun.mwork.flow.support.annotation.FLOW;
import com.yuntongxun.mwork.util.AopTargetUtils;
import com.yuntongxun.mwork.util.SpringContextHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @program: mwork
 * @description: register
 * @author: liugang
 * @create: 2020-05-13 11:27
 **/
@Slf4j
@Component
public class RegisterFlowHandler {

    private final IFlowDispatch flowDispatch;

    @Autowired
    public RegisterFlowHandler(IFlowDispatch flowDispatch) {
        this.flowDispatch = flowDispatch;
    }

    public void init(){
        Map<String, IFlow> objectMap = SpringContextHelper.getBeansOfType(IFlow.class);

        for( Map.Entry<String, IFlow> entry : objectMap.entrySet() ){
            IFlow flow = entry.getValue();
            try {
                FLOW flowAnnotation = AopTargetUtils.getTarget(flow).getClass().getAnnotation(FLOW.class);
                flowDispatch.register(flowAnnotation.flowId(), flow);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
