package com.xx.xxtest.influxdb.tool;

/**
 * @author xuxiang
 */
public class EqualConditionExp extends ConditionExp {
    private String key;
    private Object value;

    public EqualConditionExp(String key, Object value) {
        this.key = key;
        this.value = value;
    }
//    private String expression;

    public String getConditionString() {
        if (value == null) {
            return "";
        }
        return key + " = " + (value instanceof String ? "'" + value + "'" : value);
    }
}
