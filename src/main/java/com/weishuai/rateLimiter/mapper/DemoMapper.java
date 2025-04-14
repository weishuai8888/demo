package com.weishuai.rateLimiter.mapper;

import com.weishuai.rateLimiter.controller.demo.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description :
 * @Author : Future Buddha
 * @Date: 2021-12-15 11:10
 */
@Mapper
@Repository
public interface DemoMapper {

    int batchDel(@Param("list") List<Long> list);

    /**
     * 插入用户
     * @param sysUser
     * @return
     */
    int insert(SysUser sysUser);
}
