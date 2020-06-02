package com.yuntongxun.mwork.flow;

import com.yuntongxun.mwork.constant.CodeConstants;
import com.yuntongxun.mwork.constant.FlowServiceConstants;
import com.yuntongxun.mwork.flow.support.AbstractFlow;
import com.yuntongxun.mwork.flow.support.annotation.FLOW;
import com.yuntongxun.mwork.flow.support.exception.BaseException;
import com.yuntongxun.mwork.flow.support.exception.BusinessException;
import com.yuntongxun.mwork.repository.entity.YtxUserInfoEntity;
import com.yuntongxun.mwork.service.IYtxUserInfoService;
import com.yuntongxun.mwork.vo.converter.YtxUserInfoConverter;
import com.yuntongxun.mwork.vo.req.InfoReq;
import com.yuntongxun.mwork.vo.rsp.InfoRsp;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @program: mwork
 * @description: info
 * @author: liugang
 * @create: 2020-05-15 10:50
 **/
@FLOW
public class InfoFlow extends AbstractFlow<InfoRsp, InfoReq> {

    private final IYtxUserInfoService infoService;

    @Autowired
    public InfoFlow(IYtxUserInfoService infoService) {
        this.infoService = infoService;
    }

    @Override
    public void preHandler(InfoReq req) {
        log.info("前置处理: {}", req);
    }

    @Override
    public void validationHandler(InfoReq req) {
        log.info("入参效验: REQ : {}", req);
        if ( req.getId() == 999 ){
            throw new BusinessException(CodeConstants.CODE_601);
        }
    }

    /**
     * @param rsp
     * @param req
     */
    @Override
    public void afterHandler(InfoRsp rsp, InfoReq req) {
        log.info("后置处理 REQ {}, RSP {}", req, rsp);
    }

    @Override
    public void exceptionHandler(BaseException e, InfoReq req) {
        log.info("异常处理");
        super.exceptionHandler(e, req);
    }

    /**
     * 业务流程实现
     * @param req
     * @return
     */
    @Override
    protected InfoRsp process(InfoReq req) {
        if ( req.getId() == Integer.MIN_VALUE){
            throw new BusinessException(req.getId());
        }
        YtxUserInfoEntity entity = infoService.getById(req.getId());
        InfoRsp rsp = YtxUserInfoConverter.INSTANCE.entity2Inforsp(entity);
        return rsp;
    }
}
