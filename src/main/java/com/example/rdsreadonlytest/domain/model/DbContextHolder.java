package com.example.rdsreadonlytest.domain.model;

import com.example.rdsreadonlytest.domain.enums.DataSourceType;

public class DbContextHolder {

    private static final ThreadLocal<DataSourceType> contextHolder = new ThreadLocal<>();

    public static DataSourceType getDataSourceType() {
        return contextHolder.get();
    }

    public static void setDataSourceType(DataSourceType type) {
        contextHolder.set(type);
    }

    public static void clear() {
        contextHolder.remove();
    }

}