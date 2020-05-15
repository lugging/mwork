package com.yuntongxun.mwork.vo.req;

import com.yuntongxun.mwork.vo.support.BaseReq;
import lombok.Data;

/**
 * @program: mwork
 * @description: login
 * @author: liugang
 * @create: 2020-05-12 16:23
 **/
@Data
public class LoginReq extends BaseReq {

    /**
     * 1 name
     * 2 password
     */
    private int type;

    private String name;

    private String password;
}
