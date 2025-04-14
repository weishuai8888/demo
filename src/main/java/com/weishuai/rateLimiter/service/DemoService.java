package com.weishuai.rateLimiter.service;


import com.weishuai.common.CommonResult;
import com.weishuai.rateLimiter.controller.demo.SysUser;

import java.util.List;

/**
 * @Description :
 * @Author : Future Buddha
 * @Date: 2021-12-15 11:30
 */
public interface DemoService {
    /**
     * 批量删除
     * @param list
     * @return
     */
    int batchDel(List<Long> list);

    /**
     * 插入用户
     * @param sysUser
     * @return
     */
    CommonResult<Integer> insert(SysUser sysUser);
}
