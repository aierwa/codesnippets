package com.xx.xxtest.influxdb.dto;

import lombok.Data;
import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;
import org.influxdb.annotation.TimeColumn;

import java.time.Instant;

/**
 * 模拟量详情列表查询 bean
 *
 * @author xuxiang
 * 2020/7/7
 */
@Measurement(name = "device_analog")
@Data
public class AnalogInfo {
    /**
     * 时间
     */
    @TimeColumn
    @Column(name = "time")
    private Instant time;
    /**
     * 模拟量 code
     */
    @Column(name = "analog_code", tag = true)
    private String analogCode;
    /**
     * 设备 id
     */
    @Column(name = "device_id", tag = true)
    private String deviceId;
    /**
     * 模拟量值
     */
    @Column(name = "value")
    private Double value;
    @Column(name = "_count")
    private Integer count;
}
