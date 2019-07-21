package com.spider.pub.conf.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    private static final String FANOUT_EXCHANGE1 ="FanoutExchange1" ;

    private static final String QUEUE1_BIND_ON_FANOUT_EXCHANGE1 = "queue1BindOnFanoutExchange1";

    /**
     * 1.队列名字
     * 2.durable="true" 是否持久化 rabbitmq重启的时候不需要创建新的队列
     * 3.auto-delete    表示消息队列没有在使用时将被自动删除 默认是false
     * 4.exclusive      表示该消息队列是否只在当前connection生效,默认是false
     */
    @Bean
    public Queue fanoutQueue() {
        return new Queue(QUEUE1_BIND_ON_FANOUT_EXCHANGE1,true,false,false);
    }

    /**
     * 1.交换机名字
     * 2.durable="true" 是否持久化 rabbitmq重启的时候不需要创建新的交换机
     * 3.autoDelete    当所有消费客户端连接断开后，是否自动删除队列
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE1,true,false);
    }

    @Bean
    public Binding bindingFanout() {
        return BindingBuilder.bind(fanoutQueue()).to(fanoutExchange());
    }

}
