package com.yuntongxun.mwork.filter.support;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: mwork
 * @description: 基础请求参数
 * @author: liugang
 * @create: 2020-05-25 10:10
 **/
@Data
public class BaseBody implements Serializable {

    /**
     * 签名 MD5
     */
    private String sign;

    /**
     * 实际数据域
     */
    private String body;


}
