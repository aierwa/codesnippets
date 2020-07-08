package com.xx.xxtest.influxdb;

import org.apache.commons.lang.StringUtils;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author xuxiang
 * 2020/7/6
 */
@Component
public class InfluxdbInstance {
    private InfluxDB instance;
    private InfluxdbConfig config;

    @Autowired
    public InfluxdbInstance(InfluxdbConfig config) {
        this.config = config;
    }

    @PostConstruct
    private void init() {
        if (StringUtils.isEmpty(config.getUrl())) {
            throw new NullPointerException("influxdb server url is empty.");
        }
        if (!StringUtils.isEmpty(config.getUsername()) && !StringUtils.isEmpty(config.getPassword())) {
            instance = InfluxDBFactory.connect(config.getUrl(), config.getUsername(), config.getPassword());
        } else {
            instance = InfluxDBFactory.connect(config.getUrl());
        }
    }

    public InfluxDB get() {
        return this.instance;
    }
}
