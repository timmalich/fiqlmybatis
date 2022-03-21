package de.tmalich.example.fiqlmybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;

import de.tmalich.example.fiqlmybatis.domain.User;
import de.tmalich.example.fiqlmybatis.tables.UserTable;

@Mapper
public interface UsersMapper {
    @Select("select * from public.users")
    @Results(value = {
            @Result(column = UserTable.COLUMN_ID, property = User.PROPERTY_ID, id = true),
            @Result(column = UserTable.COLUMN_FIRST_NAME, property = User.PROPERTY_FIRST_NAME),
            @Result(column = UserTable.COLUMN_LAST_NAME, property = User.PROPERTY_LAST_NAME),
            @Result(column = UserTable.COLUMN_EMAIL_ADDRESS, property = User.PROPERTY_EMAIL_ADDRESS)
    })
    List<User> findAll();

    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    @Results(value = {
            @Result(column = UserTable.COLUMN_ID, property = User.PROPERTY_ID, id = true),
            @Result(column = UserTable.COLUMN_FIRST_NAME, property = User.PROPERTY_FIRST_NAME),
            @Result(column = UserTable.COLUMN_LAST_NAME, property = User.PROPERTY_LAST_NAME),
            @Result(column = UserTable.COLUMN_EMAIL_ADDRESS, property = User.PROPERTY_EMAIL_ADDRESS)
    })
    List<User> findByFilter(SelectStatementProvider selectStatement);
}
