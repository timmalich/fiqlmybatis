package de.tmalich.example.fiqlmybatis.web;

import static org.mybatis.dynamic.sql.SqlBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.mybatis.dynamic.sql.SqlCriterion;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.QueryExpressionDSL;
import org.mybatis.dynamic.sql.select.SelectModel;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import de.tmalich.example.fiqlmybatis.MyBatisFilterVisitor;
import de.tmalich.example.fiqlmybatis.tables.CustomSqlTable;
import de.tmalich.example.fiqlmybatis.domain.User;
import de.tmalich.example.fiqlmybatis.mapper.UsersMapper;
import de.tmalich.example.fiqlmybatis.tables.UserTable;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;

@RestController
public class UserController {
    private final RequestMappingHandlerMapping requestMappingHandlerMapping;
    private final UsersMapper usersMapper;

    public UserController(UsersMapper usersMapper, RequestMappingHandlerMapping requestMappingHandlerMapping) {
        this.usersMapper = usersMapper;
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
    }

    @GetMapping("/")
    public ResponseEntity<List<String>> listAllEndpoints() {
        return new ResponseEntity<>(
                requestMappingHandlerMapping
                        .getHandlerMethods()
                        .keySet()
                        .stream()
                        .map(this::requestMappingInfoToString).collect(Collectors.toList()),
                HttpStatus.OK
        );
    }

    @GetMapping("/users")
    List<User> users(@RequestParam(required = false) String filter) {
        if (filter == null) {
            return usersMapper.findAll();
        }
        CustomSqlTable table = new UserTable();
        QueryExpressionDSL<SelectModel> builder = select(table.allColumns()).from(table);
        Node rootNode = new RSQLParser().parse(filter);
        SqlCriterion criterion = rootNode.accept(new MyBatisFilterVisitor(builder, table));
        SelectStatementProvider selectStatement = builder.where(criterion).build()
                .render(RenderingStrategies.MYBATIS3);
        return usersMapper.findByFilter(selectStatement);
    }

    private String requestMappingInfoToString(RequestMappingInfo requestMappingInfo) {
        return requestMappingInfo.toString();
    }
}
