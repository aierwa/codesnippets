package com.xx.xxtest.influxdb.tool;

/**
 * @author xuxiang
 */
public abstract class ConditionExp {
    public abstract String getConditionString();

    public static ConditionExp eq(String key, Object value){
        return new EqualConditionExp(key, value);
    }

    public static ConditionExp gt(String key, Object value){
        return new GtConditionExp(key, value);
    }

    public static ConditionExp lt(String key, Object value){
        return new LtConditionExp(key, value);
    }
}
