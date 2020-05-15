package com.yuntongxun.mwork.vo.support;

import lombok.Data;

import java.io.Serializable;

/**
* @Description: 标准输出
* @Author: liugang
* @Date:
*/
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 失败消息
     */
    private String message;

    /**
     * 返回代码
     */
    private Integer code;

    /**
     * 时间戳
     */
    private long timestamp = System.currentTimeMillis();

    /**
     * 结果对象
     */
    private T data;
}
