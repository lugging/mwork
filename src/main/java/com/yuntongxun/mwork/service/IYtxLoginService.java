package com.yuntongxun.mwork.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuntongxun.mwork.repository.entity.YtxLoginEntity;

/**
 * <p>
 * 机构更名表 服务类
 * </p>
 *
 * @author liugang
 * @since 2019-12-25
 */
public interface IYtxLoginService extends IService<YtxLoginEntity> {

    int insert(YtxLoginEntity entity);
}
