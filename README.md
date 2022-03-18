# Mybatis RSQL Visitor
Just an example project that combines Mybatis with a RSQL/FIQL parser.
Makes use of dynamic queries by using a visitor pattern.

Start with ```mvn spring-boot:run```. 
Example requests:
- List all endpoints: http://localhost:8271/
- List all users: http://localhost:8271/users
- Filter user by id: http://localhost:8271/users?filter=id==1
- Filter user with and: http://localhost:8271/users?filter=firstName==Linus;lastName==Torvalds
- Filter with nested ands: http://localhost:8271/users?filter=firstName==AA;(lastName==AB;emailAddress==AB)
- Filter with or: http://localhost:8271/users?filter=id==0,id==1
- Filter with nested or: http://localhost:8271/users?filter=(id==1,id==2,id==3),id==0

Most of these requests are also covered within the UserMapperTest.

### Reference Documentation

Please note: the referenced rsql parser library, written by jirutka, is currently dead.
I'll use it anyway, since it's a pretty stable, straight forward and simply amazing piece of software. 
Furthermore I'm not aware of any real alternatives. 

For further reference, please consider the following sections:
* [Jirutka RSQL-Parser](https://github.com/jirutka/rsql-parser)
* [MyBatis Framework](https://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/)
* [MyBatis Dynamic SQL](https://github.com/mybatis/mybatis-dynamic-sql)
* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.6.4/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.6.4/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.6.4/reference/htmlsingle/#boot-features-developing-web-applications)

### Guides

The following guides illustrate how to use some features concretely:
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [MyBatis Quick Start](https://github.com/mybatis/spring-boot-starter/wiki/Quick-Start)

