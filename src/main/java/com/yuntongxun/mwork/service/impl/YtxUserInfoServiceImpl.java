package com.yuntongxun.mwork.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuntongxun.mwork.flow.support.exception.BusinessException;
import com.yuntongxun.mwork.repository.entity.YtxUserInfoEntity;
import com.yuntongxun.mwork.repository.mapper.YtxUserInfoMapper;
import com.yuntongxun.mwork.service.IYtxUserInfoService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @program: mwork
 * @description: serivce impl
 * @author: liugang
 * @create: 2020-05-12 16:21
 **/
@Service
public class YtxUserInfoServiceImpl extends ServiceImpl<YtxUserInfoMapper, YtxUserInfoEntity> implements IYtxUserInfoService {

    @Override
    public int update(long id) {
        if (id > 0){
            throw new BusinessException(999);
        }
        YtxUserInfoEntity entity = new YtxUserInfoEntity();
        entity.setUpdateTime(LocalDateTime.now());
        Wrapper wrapper = Wrappers.<YtxUserInfoEntity>update().eq(YtxUserInfoEntity.USER_ID, id);
        return baseMapper.update(entity, wrapper);
    }
}
