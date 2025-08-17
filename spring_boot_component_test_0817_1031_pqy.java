// 代码生成时间: 2025-08-17 10:31:56
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- 定义组件 -->
    <bean id="exampleComponent" class="com.example.ExampleComponent"/>

    <!-- 配置单元测试框架 -->
    <bean id="exampleTest" class="org.springframework.boot.test.context.SpringBootTest">
        <property name="webEnvironment">MOCK</property>
    </bean>

</beans>