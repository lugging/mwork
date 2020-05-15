package com.yuntongxun.mwork.controller;

import com.yuntongxun.mwork.constant.FlowServiceConstants;
import com.yuntongxun.mwork.controller.support.BaseController;
import com.yuntongxun.mwork.vo.req.InfoReq;
import com.yuntongxun.mwork.vo.req.LoginReq;
import com.yuntongxun.mwork.vo.rsp.InfoRsp;
import com.yuntongxun.mwork.vo.rsp.LoginRsp;
import com.yuntongxun.mwork.vo.support.Result;
import com.yuntongxun.mwork.vo.support.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @program: mwork
 * @description: test
 * @author: liugang
 * @create: 2020-05-12 16:22
 **/
@Slf4j
@RestController
public class UserInfoController extends BaseController {

    @PostMapping(value = "login")
    public Result<LoginRsp> login(@RequestBody LoginReq loginReq){
        LoginRsp rsp = dispatch(FlowServiceConstants.F0001, loginReq);
        return ResultUtil.ok(rsp);
    }

    @GetMapping(value = "info")
    public Result<InfoRsp> info(InfoReq req){
        InfoRsp rsp = dispatch(FlowServiceConstants.F0002, req);
        return ResultUtil.ok(rsp);
    }
}
