package com.xx.xxtest.influxdb.dto;

import lombok.Data;

/**
 * 模拟量详情列表查询 bean
 *
 * @author xuxiang
 * 2020/7/7
 */
@Data
public class AnalogDetailQuery {
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

    private Pagination pagination = new Pagination();
}
