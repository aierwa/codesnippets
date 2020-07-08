package com.xx.xxtest.influxdb;


import com.xx.xxtest.influxdb.dto.AnalogDetailQuery;
import com.xx.xxtest.influxdb.dto.AnalogInfo;
import com.xx.xxtest.influxdb.dto.AnalogQueryData;
import com.xx.xxtest.influxdb.dto.AnalogTendencyQuery;

import java.util.List;

/**
 * @author xuxiang
 * 2020/7/6
 */
public interface InfluxdbService {
    /**
     * 模拟量历史趋势统计
     *
     * @return list
     */
    List<AnalogInfo> analogTendency(AnalogTendencyQuery analogTendencyQuery);

    /**
     * 模拟量详情列表
     *
     * @param analogDetailQuery query
     */
    AnalogQueryData analogDetailList(AnalogDetailQuery analogDetailQuery);
}
