package com.yuntongxun.mwork.flow;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yuntongxun.mwork.constant.FlowServiceConstants;
import com.yuntongxun.mwork.flow.support.AbstractFlow;
import com.yuntongxun.mwork.flow.support.annotation.FLOW;
import com.yuntongxun.mwork.flow.support.exception.ValidationException;
import com.yuntongxun.mwork.repository.entity.YtxUserInfoEntity;
import com.yuntongxun.mwork.service.IYtxUserInfoService;
import com.yuntongxun.mwork.vo.converter.YtxUserInfoConverter;
import com.yuntongxun.mwork.vo.req.LoginReq;
import com.yuntongxun.mwork.vo.rsp.LoginRsp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

/**
 * @program: mwork
 * @description: login
 * @author: liugang
 * @create: 2020-05-12 18:01
 **/
@Slf4j
@FLOW(flowId = FlowServiceConstants.F0001, description = "login flow")
public class LoginFlow extends AbstractFlow<LoginRsp, LoginReq> {

    @Autowired
    private IYtxUserInfoService ytxUserInfoService;

    /**
     * 规则效验
     * @param req
     */
    @Override
    public void validation(LoginReq req) {
        if ( req.getType() == 1 ){
            if (ObjectUtils.isEmpty(req.getName())) {
                throw new ValidationException("name 不能为空");
            }
        }
    }

    @Override
    public LoginRsp process(LoginReq req) {
        YtxUserInfoEntity entity = ytxUserInfoService.getOne(Wrappers.lambdaQuery(YtxUserInfoEntity.class)
                .eq(YtxUserInfoEntity::getPhoneNum, req.getName()));
        LoginRsp rsp = YtxUserInfoConverter.INSTANCE.entity2Loginrsp(entity);
        return rsp;
    }
}
