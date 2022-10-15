# JDBC
## 1. 环境信息
* **Navicat** 16.1.3     

* **IntelliJ IDEA**  JDK 18.0.2.1

* **MySQL** 8.0.30 for macos12 on arm64 (MySQL Community Server - GPL)

    * ```java
        user=root
        password=802613wzl
        ```

* **JDBC Driver** mysql-connector-java-8.0.30.jar

    在项目文件夹下新建 **lib** 文件夹，放入 **JDBC Driver**，然后右键：**add to library**

* 数据库表信息：见 **jdbc_data.sql**

> MySQL 和 JDBC Driver 的版本号要对应一致

## 2. 文件说明

* src/com/CRUD/datas 

    对应 src/com/CRUD/preparedstatement_ 中查询操作需要的类

* src/com/CRUD/preparedstatement_

    各种操作

    * CustomerForQuery ：仅针对 customer 表的查询
    * OrderForQuery ：仅针对 order 表的查询（有 class 类属性和表中属性名不一致的处理方法）
    * PreparedStatementQueryTest ： 使用PreparedStatement实现针对于不同表的通用的**查询**操作
    * PreparedStatementTest ：演示使用PreparedStatement替换Statement，解决SQL注入问题
    * PreparedStatementUpdateTest ：使用PreparedStatement来替换Statement,实现对数据表的【增删改】操作

* src/com/CRUD/

* src/com/CRUD/utils/JDBCUtils.java

    编写了常用静态方法：通用增删改；通用查询

* jdbc.properties

    ```java
    user=root
    password=802613wzl
    url=jdbc:mysql://localhost:3306/test
    driverClass=com.mysql.jdbc.Driver
    ```
