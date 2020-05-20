package com.yuntongxun.mwork.vo.req;

import com.yuntongxun.mwork.vo.support.BaseReq;
import lombok.Data;

import javax.validation.constraints.*;

/**
 * @program: mwork
 * @description:
 * @author: liugang
 * @create: 2020-05-13 16:22
 **/
@Data
public class InfoReq extends BaseReq {

    @NotNull(message = "{req.info.id.notnull}")
    private Integer id;
}
