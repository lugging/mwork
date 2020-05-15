package com.yuntongxun.mwork.vo.converter;

import com.yuntongxun.mwork.repository.entity.YtxUserInfoEntity;
import com.yuntongxun.mwork.vo.rsp.InfoRsp;
import com.yuntongxun.mwork.vo.rsp.LoginRsp;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @program: mwork
 * @description: 转换
 * @author: liugang
 * @create: 2020-05-12 16:26
 **/
@Mapper
public interface YtxUserInfoConverter {

    YtxUserInfoConverter INSTANCE = Mappers.getMapper(YtxUserInfoConverter.class);

    LoginRsp entity2Loginrsp(YtxUserInfoEntity entity);
    InfoRsp entity2Inforsp(YtxUserInfoEntity entity);
}
