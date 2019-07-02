package com.spider.pub.conf.elasticsearch;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class ElasticsearchConf {

    @Value("${elasticsearch.cluster.name}")
    private String clusterName;

    @Value("${elasticsearch.address}")
    private String address;

//    @Bean
//    public TransportClient getTransportClient() {
//        try {
//            // 设置集群名称,并自动嗅探整个集群的状态，把集群中其它机器的ip地址加到客户端中
//            // settings
//            Settings settings = Settings.builder().put("cluster.name", clusterName)
////                    .put("index.number_of_shards", 1)
////                    .put("index.number_of_replicas", 0)
//                    .build();
//            //地址列表
//            List<TransportAddress> transportAddressList = new ArrayList<>();
//            if (StringUtils.isNotBlank(address)) {
//                String[] addresses = address.split(",");
//                String[] hostAndPort = null;
//                //组装地址
//                for (String str : addresses) {
//                    hostAndPort = str.split(":");
//                    if (hostAndPort != null && hostAndPort.length > 1) {
//                        transportAddressList.add(new TransportAddress(InetAddress.getByName(hostAndPort[0].trim()), Integer.valueOf(hostAndPort[1].trim())));
//                    } else {
//                        transportAddressList.add(new TransportAddress(InetAddress.getByName(hostAndPort[0].trim()), 9300));
//                    }
//                }
//            }
//            //转换
//            TransportAddress[] transportAddresses = transportAddressList.toArray(new TransportAddress[transportAddressList.size()]);
//            //返回连接
//            return new PreBuiltTransportClient(settings).addTransportAddresses(transportAddresses);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

}
