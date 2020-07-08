package com.xx.xxtest.influxdb.dto;

import lombok.Data;

/**
 * 模拟量历史趋势查询 bean
 *
 * @author xuxiang
 * 2020/7/7
 */
@Data
public class AnalogTendencyQuery {
    /**
     * 开始区间，ns
     */
    private Long start;
    private Long end;
    /**
     * 模拟量 code
     */
    private String analogCode;
    /**
     * 设备 id
     */
    private String deviceId;


    /**
     * 趋势统计间隔
     */
    private int interval = 1;
    /**
     * 趋势统计类型：秒，分，时，日，周
     * 1d就代表按天进行统计，取每天最后一条
     */
    private String type = "d";
}
