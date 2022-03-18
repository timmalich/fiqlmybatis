package de.tmalich.example.fiqlmybatis.tables;

import java.sql.JDBCType;
import java.util.Map;

import org.mybatis.dynamic.sql.SqlColumn;

public class UserTable extends CustomSqlTable {
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_LAST_NAME = "last_name";
    public static final String COLUMN_EMAIL_ADDRESS = "email_address";
    SqlColumn<String> id = column(COLUMN_ID, JDBCType.INTEGER);
    SqlColumn<String> firstName = column(COLUMN_FIRST_NAME, JDBCType.VARCHAR);
    SqlColumn<String> lastName = column(COLUMN_LAST_NAME, JDBCType.VARCHAR);
    SqlColumn<String> emailAddress = column(COLUMN_EMAIL_ADDRESS, JDBCType.VARCHAR);

    Map<String, SqlColumn<?>> columnMap = Map.of("id", id, "firstName", firstName, "lastName", lastName, "emailAddress", emailAddress);

    public UserTable() {
        super("public.users");
    }

    public SqlColumn findColumn(String propertyName) {
        return columnMap.get(propertyName);
    }
}
