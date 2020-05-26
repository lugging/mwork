package com.yuntongxun.mwork.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuntongxun.mwork.repository.entity.YtxLoginEntity;
import com.yuntongxun.mwork.repository.mapper.YtxLoginMapper;
import com.yuntongxun.mwork.service.IYtxLoginService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: mwork
 * @description: serivce impl
 * @author: liugang
 * @create: 2020-05-12 16:21
 **/
@Service
public class YtxLoginServiceImpl extends ServiceImpl<YtxLoginMapper, YtxLoginEntity> implements IYtxLoginService {

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public int insert(YtxLoginEntity entity) {
       return baseMapper.insert(entity);
    }
}
