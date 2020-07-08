package com.xx.xxtest.influxdb;

import com.xx.xxtest.influxdb.dto.AnalogDetailQuery;
import com.xx.xxtest.influxdb.dto.AnalogInfo;
import com.xx.xxtest.influxdb.dto.AnalogQueryData;
import com.xx.xxtest.influxdb.dto.AnalogTendencyQuery;
import com.xx.xxtest.influxdb.tool.ConditionExp;
import com.xx.xxtest.influxdb.tool.QueryParser;
import org.apache.commons.lang.StringUtils;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xuxiang
 * 2020/7/6
 */
@Service
public class InfluxdbServiceImpl implements InfluxdbService {
    private static final Logger logger = LoggerFactory.getLogger(InfluxdbServiceImpl.class);
    private static final String DATABASE = "fsis";
    private static final String MEASURE_DEVICE_ANALOG = "device_analog";
    private static final String RP = "autogen";
    @Autowired
    private InfluxdbInstance influxdbInstance;
    private InfluxDBResultMapper influxDBResultMapper = new InfluxDBResultMapper();

    @Override
    public List<AnalogInfo> analogTendency(AnalogTendencyQuery analogTendencyQuery) {
        String deviceId = analogTendencyQuery.getDeviceId();
        String analogCode = analogTendencyQuery.getAnalogCode();
        if (StringUtils.isEmpty(deviceId) || StringUtils.isEmpty(analogCode)) {
            throw new IllegalArgumentException("deviceId and analogCode should not be null.");
        }
        QueryParser sqlParser = new QueryParser(RP, MEASURE_DEVICE_ANALOG)
                .column("device_id").column("analog_code").last("value", "value")
                .where(ConditionExp.eq("device_id", deviceId))
                .where(ConditionExp.eq("analog_code", analogCode))
                .where(ConditionExp.gt("time", analogTendencyQuery.getStart()))
                .where(ConditionExp.lt("time", analogTendencyQuery.getEnd()))
                .groupByTime(analogTendencyQuery.getInterval(), analogTendencyQuery.getType())
                .orderBy("time")
                .desc();

        String queryString = sqlParser.parse();
        logger.info("query string={}", queryString);
        QueryResult queryResult = influxdbInstance.get().query(new Query(queryString, DATABASE));
        return influxDBResultMapper.toPOJO(queryResult, AnalogInfo.class);
    }

    @Override
    public AnalogQueryData analogDetailList(AnalogDetailQuery analogDetailQuery) {
        AnalogQueryData resultData = new AnalogQueryData();
        QueryParser sqlParser = new QueryParser(RP, MEASURE_DEVICE_ANALOG)
                .where(ConditionExp.eq("device_id", analogDetailQuery.getDeviceId()))
                .where(ConditionExp.eq("analog_code", analogDetailQuery.getAnalogCode()))
                .where(ConditionExp.gt("time", analogDetailQuery.getStart()))
                .where(ConditionExp.lt("time", analogDetailQuery.getEnd()))
                .offset(analogDetailQuery.getPagination().getPageSize(), analogDetailQuery.getPagination().getPageIndex())
                .orderBy("time")
                .desc();

        String countString = sqlParser.forCount(true, "value", false).parse();
        logger.info("count string ={}", countString);
        QueryResult countResult = influxdbInstance.get().query(new Query(countString, DATABASE));
        int total = influxDBResultMapper.toPOJO(countResult, AnalogInfo.class).get(0).getCount();
        logger.info("total = {}", total);
        if (total == 0) {
            return resultData;
        }

        String queryString = sqlParser.forCount(false).parse();
        logger.info("query string={}", queryString);
        QueryResult queryResult = influxdbInstance.get().query(new Query(queryString, DATABASE));
        List<AnalogInfo> infoList = influxDBResultMapper.toPOJO(queryResult, AnalogInfo.class);

        resultData.setTotal(total);
        resultData.setData(infoList);

        return resultData;
    }


}
