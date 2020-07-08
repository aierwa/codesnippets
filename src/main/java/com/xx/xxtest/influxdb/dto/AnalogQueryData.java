package com.xx.xxtest.influxdb.dto;

import lombok.Data;

import java.util.List;

/**
 * 模拟量详情列表
 *
 * @author xuxiang
 * 2020/7/7
 */
@Data
public class AnalogQueryData {
    private List<AnalogInfo> data;
    private int total;
}
