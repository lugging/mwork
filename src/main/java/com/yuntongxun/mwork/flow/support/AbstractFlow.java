package com.yuntongxun.mwork.flow.support;

import com.yuntongxun.mwork.flow.support.exception.BaseException;
import com.yuntongxun.mwork.vo.support.IReq;
import com.yuntongxun.mwork.vo.support.IRsp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import java.util.Set;

import static com.yuntongxun.mwork.util.JsonUtils.gsonObjectToJson;
/**
 * @program: mwork
 * @description: flow
 * @author: liugang
 * @create: 2020-05-12 18:23
 **/
public abstract class AbstractFlow<RSP extends IRsp, REQ extends IReq> implements IFlowSupport<RSP, REQ> {

    protected Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private Validator validator;

    /**
     * 前置 效验
     * @param req
     */
    @Override
    public void validation(REQ req){
        Set<ConstraintViolation<REQ>> set = validator.validate(req);
        if ( !ObjectUtils.isEmpty(set)){
            throw new ConstraintViolationException(set);
        }
        validationHandler(req);
    }

    @Override
    public void validationHandler(REQ req){}

    /**
     * 业务执行
     * @param req
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RSP execute(REQ req) {
        long begin = System.currentTimeMillis();
        RSP rsp = null;
        try{
            printLog("PreHandler", req);
            preHandler(req);
            printLog("validation", req);
            validation(req);
            rsp = process(req);
            printLog("afterHandler", req);
            afterHandler(rsp, req);
        }catch (BaseException e) {
            exceptionHandler(e, req);
        }
        long end = System.currentTimeMillis();
        log.info("flow cost {}ms ", end - begin);
        return rsp;
    }

    /**
     * 业务执行
     * @param req
     * @return
     */
    protected abstract RSP process(REQ req);

    private void printLog(String tag, Object obj){
        if ( log.isTraceEnabled() ){
            log.trace("@{} : {}", tag, gsonObjectToJson(obj));
        }
    }
}
