package de.tmalich.example.fiqlmybatis.mapper;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mybatis.dynamic.sql.SqlCriterion;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import de.tmalich.example.fiqlmybatis.MyBatisFilterVisitor;
import de.tmalich.example.fiqlmybatis.domain.User;
import de.tmalich.example.fiqlmybatis.tables.CustomSqlTable;
import de.tmalich.example.fiqlmybatis.tables.UserTable;

@SpringBootTest
class UserMapperTest {
    @Autowired
    UsersMapper usersMapper;

    @Test
    void findAll() {
        List<User> users = usersMapper.findAll();
        assertThat(users, hasSize(greaterThanOrEqualTo(2)));
        assertThat(users.get(0).getFirstName(), is("Linus"));
        assertThat(users.get(0).getLastName(), is("Torvalds"));
        assertThat(users.get(0).getEmailAddress(), is("torvalds@linux-foundation.org"));
        assertThat(users.get(1).getFirstName(), is("Bill"));
        assertThat(users.get(1).getLastName(), is("Gates"));
        assertThat(users.get(1).getEmailAddress(), is("bill.gates@microsoft.com"));

    }

    @Test
    void filterReturnsAllProperties() throws Exception {
        List<User> filteredResult = getFilteredResult("id==0");
        User user = filteredResult.get(0);
        assertThat(user.getId(), is(0));
        assertThat(user.getEmailAddress(), is("torvalds@linux-foundation.org"));
        assertThat(user.getFirstName(), is("Linus"));
        assertThat(user.getLastName(), is("Torvalds"));
    }

    @Test
    void visitorAcceptsIntegers() throws Exception {
        List<User> filteredResult = getFilteredResult("id==1");
        assertThat(filteredResult, hasSize(1));
        assertThat(filteredResult.get(0).getId(), is(1));
    }

    @Test
    void visitorAcceptsAnd() throws Exception {
        List<User> filteredResult = getFilteredResult("id==0 and firstName==Linus");
        assertThat(filteredResult, hasSize(1));
        assertThat(filteredResult.get(0).getId(), is(0));

        filteredResult = getFilteredResult("id==0;id==1");
        assertThat(filteredResult, hasSize(0));
    }

    @Test
    void visitorAcceptsAndWithNestedComparators() throws Exception {
        List<User> filteredResult = getFilteredResult("firstName==AA and (lastName==AB and emailAddress==AB)");
        assertThat(filteredResult, hasSize(1));
        assertThat(filteredResult.get(0).getId(), is(3));
    }

    @Test
    void visitorAcceptsAndWithAllProperties() throws Exception {
        List<User> filteredResult = getFilteredResult("id==0;emailAddress=='torvalds@linux-foundation.org';firstName==Linus;lastName==Torvalds");
        assertThat(filteredResult, hasSize(1));
        assertThat(filteredResult.get(0).getId(), is(0));
    }

    @Test
    void visitorAcceptsOr() throws Exception {
        List<User> filteredResult = getFilteredResult("id==1 or id==0");
        assertThat(filteredResult, hasSize(2));
        assertThat(filteredResult.get(0).getId(), is(0));
        assertThat(filteredResult.get(1).getId(), is(1));
    }

    private List<User> getFilteredResult(String filter) throws Exception {
        CustomSqlTable table = new UserTable();
        MyBatisFilterVisitor visitor = new MyBatisFilterVisitor(table);
        Node rootNode = new RSQLParser().parse(filter);
        SqlCriterion criterion = rootNode.accept(visitor);
        SelectStatementProvider selectStatement = select(table.allColumns())
                .from(table).where(criterion).build()
                .render(RenderingStrategies.MYBATIS3);
        return usersMapper.findByFilter(selectStatement);
    }
}
