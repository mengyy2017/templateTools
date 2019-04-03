package com.template.pub.config;

import com.template.pub.commModel.ConnHolder;
import com.template.utils.CreateConnUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Configuration
@ComponentScan("com.template.pub.config")
@EnableAsync
public class ThreadPoolConf {

    private Lock lock = new ReentrantLock();

    @Bean
    public Executor getTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setQueueCapacity(5);
        taskExecutor.initialize();
        return taskExecutor;
    }

    @Async(value = "getTaskExecutor")
    public void checkIdleConnTask() {
        System.out.println("线程名称：" + Thread.currentThread().getName());
        ConcurrentHashMap<String, LinkedList<ConnHolder>> conMap = CreateConnUtil.getDataSourceConHM();

        conMap.keySet().forEach(key -> {
            LinkedList<ConnHolder> linkedList = conMap.get(key);
            int i = 0; Iterator<ConnHolder> iter; lock.lock();
            try{
                for(iter = linkedList.iterator(); iter.hasNext(); ){
                    ConnHolder connHolder = iter.next();

                    Duration duration = Duration.between(connHolder.getLastUseTime(), LocalDateTime.now());
                    long unUseSeconds = duration.getSeconds();

                    System.out.println("线程名称：" + Thread.currentThread().getName());
                    System.out.println("检测前连接个数：" + linkedList.size());
                    System.out.println("索引：" + i);
                    System.out.println("是否关闭：" + connHolder.get().isClosed());
                    System.out.println("上次使用时间：" + connHolder.getLastUseTime());
                    System.out.println("现在现在时间：" + LocalDateTime.now());
                    System.out.println("多长时间未使用（秒）：" + unUseSeconds);
                    System.out.println("本次是否会关闭：" + (unUseSeconds > ConnHolder.getIdleTime()));

                    if(unUseSeconds > ConnHolder.getIdleTime()){
                        connHolder.get().close();
                        iter.remove();
                    }

                    System.out.println("检测后连接个数：" + linkedList.size());
                    System.out.println("----------------------------");
                    i++;
                }
                if(linkedList.size() == 0)
                    conMap.remove(key);
//                Thread.sleep(2000);
            } catch (Exception e) {

            } finally {
                lock.unlock();
            }

        });
    }


    @Async(value = "getTaskExecutor")
    public void threadTaskTest() {
        try {
            System.out.println("threadTaskTest : " + Thread.currentThread().getName());
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Async
    public void threadTaskTest1() {
        try {
            System.out.println("threadTaskTest1 : " + Thread.currentThread().getName());
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}
