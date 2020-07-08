package com.xx.xxtest.influxdb.tool;

/**
 * @author xuxiang
 */
public class GtConditionExp extends ConditionExp {
    private String key;
    private Object value;

    public GtConditionExp(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String getConditionString() {
        if (value == null) {
            return "";
        }
        value = value instanceof String ? "'" + value + "'" : value;
        return key + " > " + value;
    }
}
