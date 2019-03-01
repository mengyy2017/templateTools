package com.templateTools.pub.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.Executor;

@Configuration
@EnableScheduling
@ComponentScan("com.templateTools.pub.config")
public class ScheduledConfig {

    @Autowired
    ThreadConfig threadConfig;

    @Autowired
    @Qualifier(value = "getTaskExecutor")
    Executor poolExecutor;

//    @Scheduled(fixedRate = 600000)
    @Scheduled(fixedRate = 15000)
    public void checkIdleConnSchedule() {
        System.out.println("======================================================checkIdleConnSchedule");

        threadConfig.checkIdleConnTask();
//        threadConfig.checkIdleConnTask();

        ThreadPoolTaskExecutor executor = (ThreadPoolTaskExecutor) poolExecutor;
        System.out.println("线程池中总共的线程数： " + executor.getPoolSize());
        System.out.println("线程池中活跃的线程数[activeCount]====" + executor.getActiveCount());
        System.out.println("======================================================");
    }



//    @Scheduled(fixedRate = 20000)
//    public void checkIdleConn() {
//        System.out.println("schedule==================");
//
//        for(int i = 0; i < 11; i++) {
//            threadConfig.threadTaskTest();
//            threadConfig.threadTaskTest1();
//            System.out.println("---------------");
//            ThreadPoolTaskExecutor executor = (ThreadPoolTaskExecutor) poolExecutor;
//            System.out.println("线程池中总共的线程数： " + executor.getPoolSize());
//            System.out.println("线程池中活跃的线程数[activeCount]====" + executor.getActiveCount());
//        }
//    }

}
