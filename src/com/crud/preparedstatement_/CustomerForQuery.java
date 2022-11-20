package com.crud.preparedstatement_;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import com.crud.datas.Customer;
import com.crud.utils.JDBCUtils;
import org.junit.Test;


/**
 * 
 * @Description 针对于 Customers 表的查询操作
 *
 */
public class CustomerForQuery {
	
	@Test
	public void testQueryForCustomers(){
		String sql = "select id,name,birth,email from customers where id = ?";
		Customer customer = queryForCustomers(sql, 13);
		System.out.println(customer);
		
		sql = "select name,email from customers where name = ?";
		Customer customer1 = queryForCustomers(sql,"周杰伦");
		System.out.println(customer1);
	}
	
	/**
	 * 
	 * @Description 针对于customers表的通用的查询操作，返回查询到的一个结果
	 */
	public Customer queryForCustomers(String sql,Object...args){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//1. 获取连接
			conn = JDBCUtils.getConnection();

			//2. 预编译 sql
			ps = conn.prepareStatement(sql);

			//3. 填充占位符
			for(int i = 0;i < args.length;i++){
				ps.setObject(i + 1, args[i]);
			}

			//4. 获取结果集
			rs = ps.executeQuery();

			//5. 获取结果集的元数据 :ResultSetMetaData
			ResultSetMetaData rsmd = rs.getMetaData();

			//6. 通过ResultSetMetaData获取结果集中的【列数】
			int columnCount = rsmd.getColumnCount();
			
			if(rs.next()){
				Customer cust = new Customer();//为什么调用默认构造器：因为查询出来的结果不一定有对应的构造器，先把对象构造出来，后面使用 set 方法赋值

				// 7. 处理结果集一行数据中的每一个列
				for(int i = 0;i <columnCount;i++){
					//7.1 获取列值
					Object columValue = rs.getObject(i + 1);
					
					//7.2 获取每个列的列名
//					String columnName = rsmd.getColumnName(i + 1);
					String columnLabel = rsmd.getColumnLabel(i + 1);
					
					//7.3 给cust对象指定的columnName属性，赋值为columValue：通过反射
					Field field = Customer.class.getDeclaredField(columnLabel); //拿到属性；getDeclaredField 得到所有属性
					field.setAccessible(true); //应对属性可能私有的情况
						//    对象， 对象属性
					field.set(cust, columValue);
				}
				return cust;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JDBCUtils.closeResource(conn, ps, rs);
		}
		return null;
	}


	/**
	 * @Description 针对 customers 表的一般查询操作
	 */
	@Test
	public void testQuery1() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		try {
			//1. 获取连接
			conn = JDBCUtils.getConnection();
			//2. 预编译 sql
			String sql = "select id,name,email,birth from customers where id = ?";
			ps = conn.prepareStatement(sql);
			//3. 填充占位符
			ps.setObject(1, 1);
			
			//4. 执行,并返回结果集；增删改：ps.execute()
			resultSet = ps.executeQuery();

			//5. 处理结果集
			if(resultSet.next()){//next():判断结果集的下一条是否有数据，如果有数据返回true,并指针下移；如果返回false,指针不会下移。
				//获取当前这条数据的各个字段值
				int id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				String email = resultSet.getString(3);
				Date birth = resultSet.getDate(4);
				
				//方式一：
//				System.out.println("id = " + id + ",name = " + name + ",email = " + email + ",birth = " + birth);
				
				//方式二：
//				Object[] data = new Object[]{id,name,email,birth};

				//方式三：将数据封装为一个对象（推荐）
				Customer customer = new Customer(id, name, email, birth);
				System.out.println(customer);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//关闭资源
			JDBCUtils.closeResource(conn, ps, resultSet);
			
		}
		
	}
	
}
