package com.xx.xxtest.influxdb.tool;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author xuxiang
 * 2020/7/7
 */
public class QueryParser implements SqlParser {
    private static final Logger logger = LoggerFactory.getLogger(QueryParser.class);

    /**
     * retention policy
     */
    private String rp;
    /**
     * 表名
     */
    private String measurement;
    /**
     * where 条件表达式
     */
    private List<ConditionExp> conditionExps;
    /**
     * 查询列
     */
    private List<String> columns;
    /**
     * order by 列
     */
    private List<String> orderBy;
    /**
     * group by 列
     */
    private List<String> groupBy;
    /**
     * 是否倒序
     */
    private boolean isDesc;
    /**
     * 是否仅作 count
     */
    private boolean isForCount;
    /**
     * count 时是否去重
     */
    private boolean distinct;
    /**
     * count 的列
     */
    private String countField;
    /**
     * limit 值
     */
    private int limit;
    /**
     * 分页的 offset
     */
    private int offset = -1;

    private QueryParser() {
    }

    public QueryParser(String rp, String measurement) {
        this.rp = rp;
        this.measurement = measurement;
    }

    @Override
    public String parse() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");

        // columns
        if (isForCount) {
            countField = countField == null ? "*" : countField;
            sb.append("COUNT(");
            if (distinct) {
                sb.append("DISTINCT(").append(countField).append("))");
            } else {
                sb.append(countField).append(")");
            }
            sb.append(" AS _count");
        } else {
            sb.append((columns == null || columns.isEmpty()) ? "*" : String.join(",", columns));
        }

        // 需要加上同步的保留策略进行查询 autogen.xx,autogen_sync.xx
        sb.append(" FROM ")
                .append(rp).append(".").append(measurement)
                .append(",").append(rp).append("_sync.").append(measurement);

        // where
        sb.append(parseWhere());
        // group by
        sb.append(parseGroupBy());

        if (!isForCount) {
            // order by
            sb.append(parseOrderBy());
            // offset
            sb.append(parseOffset());
            // limit
            sb.append(parseLimit());
        }

        return sb.toString();
    }


    public QueryParser offset(int limit, int offset) {
        this.limit = limit;
        this.offset = offset;
        return this;
    }

    public QueryParser limit(int limit) {
        this.limit = limit;
        return this;
    }

    public QueryParser desc() {
        isDesc = true;
        return this;
    }

    public QueryParser orderBy(String orderBy) {
        if (this.orderBy == null) {
            this.orderBy = new ArrayList<>();
        }
        this.orderBy.add(orderBy);
        return this;
    }

    public QueryParser groupBy(String... groupBy) {
        if (this.groupBy == null) {
            this.groupBy = new ArrayList<>();
        }
        this.groupBy.addAll(Arrays.asList(groupBy));
        return this;
    }

    public QueryParser groupByTime(int interval, String literal) {
        return groupBy("time(" + interval + literal + ")");
    }

    public QueryParser where(ConditionExp conditionExp) {
        if (this.conditionExps == null) {
            this.conditionExps = new ArrayList<>();
        }
        this.conditionExps.add(conditionExp);
        return this;
    }

    public QueryParser column(String column) {
        return column(column, null);
    }

    public QueryParser column(String column, String alias) {
        if (this.columns == null) {
            this.columns = new ArrayList<>();
        }
        this.columns.add(column + (alias == null ? "" : " AS " + alias));
        return this;
    }

    public QueryParser max(String column, String alias) {
        return column("MAX(" + column + ")", alias);
    }

    public QueryParser last(String column, String alias) {
        return column("LAST(" + column + ")", alias);
    }

    public QueryParser forCount(boolean isForCount) {
        return forCount(isForCount, null, false);
    }

    public QueryParser forCount(boolean isForCount, String field, boolean distinct) {
        this.isForCount = isForCount;
        this.countField = field;
        this.distinct = distinct;
        return this;
    }

    private String parseWhere() {
        StringBuilder sb = new StringBuilder();
        if (conditionExps != null && !conditionExps.isEmpty()) {
            List<String> wheres = new ArrayList<>();
            String tmp = "";
            for (ConditionExp conditionExp : conditionExps) {
                if (StringUtils.isEmpty(tmp = conditionExp.getConditionString())) {
                    continue;
                }
                wheres.add(tmp);
            }
            if (!wheres.isEmpty()) {
                sb.append(" WHERE ").append(String.join(" AND ", wheres));
            }
        }
        return sb.toString();
    }

    private String parseColumn() {
        StringBuilder sb = new StringBuilder();
        if (columns != null && !columns.isEmpty()) {
            sb.append(String.join(",", columns));
        } else {
            sb.append("*");
        }
        return sb.toString();
    }

    private String parseOrderBy() {
        StringBuilder sb = new StringBuilder();
        if (orderBy != null && !orderBy.isEmpty()) {
            sb.append(" ORDER BY ").append(String.join(",", orderBy));
            if (isDesc) {
                sb.append(" DESC ");
            }
        }
        return sb.toString();
    }

    private String parseGroupBy() {
        StringBuilder sb = new StringBuilder();
        if (groupBy != null && !groupBy.isEmpty()) {
            sb.append(" GROUP BY ").append(String.join(",", groupBy));
        }
        return sb.toString();
    }

    private String parseOffset() {
        if (offset > -1 && limit > 0) {
            return " LIMIT " + limit + " OFFSET " + offset;
        }
        return "";
    }

    private String parseLimit() {
        // if offset not provided
        if (limit > 0 && offset == -1) {
            return " LIMIT " + limit;
        }
        return "";
    }
}
