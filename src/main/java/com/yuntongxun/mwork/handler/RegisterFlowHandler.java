package com.yuntongxun.mwork.handler;

import com.yuntongxun.mwork.flow.support.IFlow;
import com.yuntongxun.mwork.flow.support.IFlowDispatch;
import com.yuntongxun.mwork.flow.support.annotation.FLOW;
import com.yuntongxun.mwork.util.AopTargetUtils;
import com.yuntongxun.mwork.util.SpringContextHelper;
import lombok.extern.slf4j.Slf4j;
import org.reflections.ReflectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    /**
     * 1 不显式指定flow,则根据入参对象，出参对象 动态生产 flowId
     * 2   显式指定flow,则直接获取flowId
     */
    public void init(){
        Map<String, IFlow> objectMap = SpringContextHelper.getBeansOfType(IFlow.class);
        for( Map.Entry<String, IFlow> entry : objectMap.entrySet() ){
            IFlow flow = entry.getValue();
            try {
                // AOP代理的对象获取不到注解，此处用AopTargetUtils.getTarget方法获取全局对象
                Class<IFlow> clazz = (Class<IFlow>) AopTargetUtils.getTarget(flow).getClass();
                FLOW flowAnnotation = clazz.getAnnotation(FLOW.class);
                String flowId = flowAnnotation.flowId();
                String flowIdWithName = buildFlowId(clazz);

                // 显式指定的flowId 和 根据入参生成的flowId 同时注册
                if ( !StringUtils.isEmpty( flowId )){
                    flowDispatch.register(flowId, flow);
                }
                flowDispatch.register(flowIdWithName, flow);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 根据 入参 生成 flowId
     * @param clazz
     * @return
     */
    private String buildFlowId(Class<IFlow> clazz){
        Set<Method> methods = ReflectionUtils.getMethods(clazz, ReflectionUtils.withName("process"));
        methods =  methods.stream().filter( method -> !method.getParameterTypes()[0].isInterface()).collect(Collectors.toSet());
        Assert.notEmpty(methods, "generator flowId error, don't find process method");
        return methods.iterator().next().getParameterTypes()[0].getName();
    }
}
