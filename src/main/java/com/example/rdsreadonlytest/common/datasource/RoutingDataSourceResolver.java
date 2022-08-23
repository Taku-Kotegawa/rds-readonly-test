package com.example.rdsreadonlytest.common.datasource;

import com.example.rdsreadonlytest.domain.enums.DataSourceType;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class RoutingDataSourceResolver extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {

        if (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
            return DataSourceType.ReadOnly;
        } else {
            return DataSourceType.Updatable;
        }

    }
}