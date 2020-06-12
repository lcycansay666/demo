/**
 * @Auther: PC-8
 * @Date: 2019/11/19 16:41
 * @Description:
 */
package com.example.config;

import lombok.Value;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Author: lcy
 * @Date: 2019/11/19 16:41
 * @Description:
 */
@Configuration
public class ESConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(ESConfig.class);
    /**
     * elk集群地址
     */
    private String hostName ="localhost";
    /**
     * 端口
     */
    private String port = "9300";
    /**
     * 集群名称
     */
    private String clusterName = "es";

    /**
     * 连接池
     */
    private String poolSize = "5";

    @Bean
    public TransportClient init() {
        LOGGER.info("初始化开始。。。。。");
        TransportClient transportClient = null;
        try {
            // 配置信息
            Settings esSetting = Settings.builder()
                    .put("client.transport.sniff", true)//增加嗅探机制，找到ES集群
                    .put("thread_pool.search.size", Integer.parseInt(poolSize))//增加线程池个数，暂时设为5
                    .put("cluster.name",clusterName)
                    .build();
            //配置信息Settings自定义,下面设置为EMPTY
            transportClient = new PreBuiltTransportClient(esSetting);
            TransportAddress transportAddress = new TransportAddress(InetAddress.getByName(hostName), Integer.valueOf(port));
            transportClient.addTransportAddresses(transportAddress);


        } catch (Exception e) {
            LOGGER.error("elasticsearch TransportClient create error!!!", e);
        }

        return transportClient;
    }
}
