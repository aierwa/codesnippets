package com.xx.xxtest.influxdb;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author xuxiang
 * 2020/7/6
 */
@ConfigurationProperties(prefix = "influxdb.server")
@Component
@Data
public class InfluxdbConfig {
    private String username;
    private String password;
    private String url;
}
