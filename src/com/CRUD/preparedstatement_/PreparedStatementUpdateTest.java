package com.CRUD.preparedstatement_;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Properties;

import com.CRUD.utils.JDBCUtils;
import org.junit.Test;


/*
 * 使用PreparedStatement来替换Statement,实现对数据表的【增删改】操作
 *
 * 增删改：不需要返回信息
 * 查：需要返回信息
 *
 */
public class PreparedStatementUpdateTest {

    /**
     * 测试通用的增删改操作
     * 都有连接数据库，关闭资源等操作
     * 不同点在于 SQL 语句
     */
    @Test
    public void testCommonUpdate(){
		String sql = "delete from customers where id = ?";
		commonUpdate(sql,4);

//        String sql = "update `order` set order_name = ? where order_id = ?";
//        commonUpdate(sql,"DD","2");

    }

    /**
     * 通用的增删改操作
     * @param sql 带有占位符的 SQL 语句
     * @param args 替代占位符的具体内容
     */
    public void commonUpdate(String sql, Object ...args){//sql 中占位符的个数与可变形参 args 的长度相同！
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            //1.获取数据库的连接
            conn = JDBCUtils.getConnection();
            //2.预编译sql语句，返回PreparedStatement的实例
            ps = conn.prepareStatement(sql);
            //3.填充占位符
            for(int i = 0; i < args.length; i++){
                ps.setObject(i + 1, args[i]);//小心参数声明错误！！，SQL 的占位符是从 1 开始的，数组是从 0 开始的
            }
            //4.执行
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            //5.资源的关闭
            JDBCUtils.closeResource(conn, ps);

        }


    }


    /**
     * 修改customers表的一条记录
     * 使用 JCUDtils
     */
    @Test
    public void testUpdate(){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            //1.获取数据库的连接
            conn = JDBCUtils.getConnection();
            //2.预编译sql语句，返回PreparedStatement的实例
            String sql = "update customers set name = ? where id = ?";
            ps = conn.prepareStatement(sql);
            //3.填充占位符
            ps.setObject(1,"莫扎特");//ps.setString(1,"莫扎特"); 可以用其顶级父类 Objet
            ps.setObject(2, 18);
            //4.执行
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            //5.资源的关闭
            JDBCUtils.closeResource(conn, ps);

        }
    }


    /**
     * 向customers表中添加一条记录
     * 未使用JDBCUtils
     */
    @Test
    public void testInsert() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            // 1.读取配置文件中的4个基本信息
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");

            Properties pros = new Properties();
            pros.load(is);

            String user = pros.getProperty("user");
            String password = pros.getProperty("password");
            String url = pros.getProperty("url");
            String driverClass = pros.getProperty("driverClass");

            // 2.加载驱动
            Class.forName(driverClass);

            // 3.获取连接
            conn = DriverManager.getConnection(url, user, password);


            //4.预编译sql语句，返回PreparedStatement的实例
            String sql = "insert into customers(name,email,birth)values(?,?,?)";//?:占位符
            ps = conn.prepareStatement(sql);
            //5.填充占位符
            ps.setString(1, "哪吒");
            ps.setString(2, "nezha@gmail.com");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = sdf.parse("1000-01-01");
            ps.setDate(3, new Date(date.getTime()));

            //6.执行操作
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            //7.资源的关闭
            try {
                if(ps != null)
                    ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if(conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }

}
