package de.tmalich.example.fiqlmybatis.tables;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public abstract class CustomSqlTable extends SqlTable {
    protected CustomSqlTable(String tableName){
        super(tableName);
    }

    public abstract SqlColumn<Object> findColumn(String propertyName);
}
