package com.xx.xxtest.influxdb.tool;

/**
 * @author xuxiang
 * 2020/7/7
 */
public interface SqlParser {
    /**
     * 解析 sql 语句
     *
     * @return 解析后的 sql
     */
    String parse();
}
