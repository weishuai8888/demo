package com.weishuai.rateLimiter.controller.demo;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description :
 * @Author : Future Buddha
 * @Date: 2021-12-15 11:09
 */
@RestController
@RequestMapping("/demo")
public class TestRateLimiter {
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();
    /*** 模拟RateLimiter限流 */
    public static void main(String[] args) {
        //0.5代表一秒最多多少个
        RateLimiter rateLimiter = RateLimiter.create(3);
        List<Runnable> tasks = new ArrayList<Runnable>();
        for (int i = 0; i < 10; i++) {
            tasks.add(new UserRequest(i));
        }
        ExecutorService threadPool = Executors.newCachedThreadPool();
        for (Runnable runnable : tasks) {
            System.out.println("等待时间：" + rateLimiter.acquire());
            threadPool.execute(runnable);
        }
    }

    private static class UserRequest implements Runnable {
        private int id;

        public UserRequest(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            System.out.println("userQuestID:" + id);
        }
    }

}
