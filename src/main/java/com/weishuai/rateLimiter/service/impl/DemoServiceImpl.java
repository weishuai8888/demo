package com.weishuai.rateLimiter.service.impl;

import com.alibaba.fastjson.JSON;
import com.weishuai.common.CommonResult;
import com.weishuai.rateLimiter.controller.demo.SysUser;
import com.weishuai.rateLimiter.entity.po.Demo;
import com.weishuai.rateLimiter.mapper.DemoMapper;
import com.weishuai.rateLimiter.service.DemoService;
import com.weishuai.util.request.HttpClient;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description :
 * @Author : Future Buddha
 * @Date: 2021-12-15 11:31
 */
@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private DemoMapper demoMapper;

    @Override
    public int batchDel(List<Long> list) {
        return demoMapper.batchDel(list);
    }

    @Transactional(rollbackFor = Exception.class)
    @ShardingTransactionType(TransactionType.XA)
    @Override
    public CommonResult<Integer> insert(SysUser sysUser) {
        int i = demoMapper.insert(sysUser);
        Demo demo = new Demo();
//        demo.setAge(sysUser.getId().intValue());
        String s = HttpClient.doPost("http://127.0.0.1:80/demo/insert", JSON.toJSONString(demo));
        return CommonResult.success(i);
    }
}
