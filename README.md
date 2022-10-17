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

#### jdbc.properties

```java
user=root
password=802613wzl
url=jdbc:mysql://localhost:3306/test
driverClass=com.mysql.jdbc.Driver
```



#### com.crud

* datas/ 

    对应 src/com/CRUD/preparedstatement_ 中查询操作需要的类

* preparedstatement_/

    各种操作

    * CustomerForQuery ：仅针对 customer 表的查询
    * OrderForQuery ：仅针对 order 表的查询（有 class 类属性和表中属性名不一致的处理方法）
    * PreparedStatementQueryTest ： 使用PreparedStatement实现针对于不同表的通用的**查询**操作
    * PreparedStatementTest ：演示使用PreparedStatement替换Statement，解决SQL注入问题
    * PreparedStatementUpdateTest ：使用PreparedStatement来替换Statement,实现对数据表的【增删改】操作

* utils/JDBCUtils.java

    编写了常用静态方法：通用增删改；通用查询

#### com.advanced.

* dao
    * datas/ : 存放对应数据库表的类
    * junit/ :  测试类
    * BaseDAO：封装了针对于数据表的通用的操作
    * CustomerDAO：规范接口
    * CustomerDAOImpl：继承 BaseDAO，实现 CustomerDAO 接口
* transaction
    * TransactionTest ： 
        * 未考虑数据库事务情况下的转账操作
        * 考虑事务后的转账操作
        * 演示 java 中的数据库隔离
    * User : 对应数据库中的表，被 TransactionTest 使用到

## 3. 核心知识

### 3.1 CRUD 操作

> 对应 com.crud 包

1. try catch 和 throws 怎么选择

    在功能函数中使用 throws，在使用功能函数的时候使用 try catch，这样功能函数抛出异常后使用功能函数的语句就会及时中断

    

### 3.2 事务

> 对应 com.advanced.transaction 包

```java
哪些操作会导致数据的自动提交（commit）？
*     >DDL（数据定义语言，定义表、视图）操作一旦执行，都会自动提交。
*        >set autocommit = false 对DDL操作失效
*     >DML默认情况下，一旦执行，就会自动提交。
*        >我们可以通过set autocommit = false的方式取消DML操作的自动提交。
*     >默认在关闭连接时，会自动的提交数据
*        >在之前 crud.utils 的时候我们的增删改操作都是每执行一次 SQL 就会打开和关闭连接一次，需要改进
		 >增删改操作就直接把连接传进去
*        >打开连接 -> 执行 sql1 -> 执行 sql2 -> 关闭连接
```



**JDBC**程序中为了让多个**SQL** **语句作为一个事务执行：**

1. 调用 Connection 对象的 **setAutoCommit(false);** 以取消自动提交事务

2. 在所有的 SQL 语句都成功执行后，调用 **commit();** 方法提交事务

3. 在出现异常时，在 catch 中调用 **rollback();** 方法回滚事务



 **事务的ACID属性**

1. **原子性（Atomicity）** 原子性是指事务是一个不可分割的工作单位，事务中的操作要么都发生，要么都不发生。

2. **一致性**（**Consistency**） 事务必须使数据库从一个一致性状态变换到另外一个一致性状态。

3. **隔离性**（**Isolation**） 事务的隔离性是指一个事务的执行不能被其他事务干扰，即一个事务内部的操作及使用的数据对并发的其他事务是隔离的，并发执行的各个事务之间不能互相干扰。

4. **持久性**（**Durability**）持久性是指一个事务一旦被提交，它对数据库中数据的改变就是永久性的，接下来的其他操作和数据库故障不应该对其有任何影响。

