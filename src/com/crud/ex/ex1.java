package com.crud.ex;

import com.crud.utils.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ex1 {
    @Test
    public void insertCustomer(){
        String name = "sdfdg";
        String email = "owadfwda@awdsea";

        String birth = "1002-2-12";

        String sql = "insert into customers(name,email,birth) values (?,?,?)";
        if(insertInto(sql,name,email,birth)!=0){
            System.out.printf("succesfully");
        }else {
            System.out.printf("failed");
        }

    }

    public int insertInto(String sql,Object...args)  {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = JDBCUtils.getConnection();
            ps = connection.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }
            //这里使用的不是 ps.execute()，因为我们想知道插入是否成功，executeUpdate 可以返回相应的信息
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection,ps);
        }
        return 0;
    }
}
