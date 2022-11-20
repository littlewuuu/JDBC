package com.crud.ex.ex2;

import com.crud.utils.JDBCUtils;
import org.junit.Test;

import java.util.Scanner;

public class ex2 {

    /**
     * 第一题
     * 插入信息
     */
    @Test
    public void test1(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("type");
        int type = scanner.nextInt();

        System.out.println("idcard");
        String ID = scanner.next();

        System.out.println("cart");
        String cart = scanner.next();

        System.out.println("name");
        String name = scanner.next();

        System.out.println("city");
        String city = scanner.next();

        System.out.println("score");
        int score = scanner.nextInt();

        String sql = "insert into examstudent(Type,IDCard,examCard,studentName,location,Grade) values(?,?,?,?,?,?)";
        JDBCUtils.commonUpdate(sql,type,ID,cart,name,city,score);
    }


    /**
     * 第二题
     * 输入 身份证号 或 者准考证号 查询信息
     */
    @Test
    public void test2(){
        System.out.println("a:IDCArd,b:examCard");
        Scanner scanner = new Scanner(System.in);
        String selection = scanner.next();
        if("a".equalsIgnoreCase(selection)){
            System.out.println("IDCard");
            String IDCard = scanner.next();
                                //起的别名和 class 类里面的一样
            String sql = "select FlowID flowID,Type type,IDCard,ExamCard examCard, StudentName name, Location location, Grade grade from examstudent where IDCard = ?";
            Student student = JDBCUtils.getInstance(Student.class,sql,IDCard);
            if (student != null) {
            System.out.println(student);
            }else {
                System.out.println("wrong information");
            }
        }else if("b".equalsIgnoreCase(selection)){
            System.out.println("examCard");
            String examID = scanner.next();
            String sql = "select FlowID flowID,Type type,IDCard,ExamCard examCard, StudentName name, Location location, Grade grade from examstudent where ExamCard = ?";
            Student student = JDBCUtils.getInstance(Student.class,sql,examID);
            if (student != null) {
                System.out.println(student);
            }else {
                System.out.println("wrong information");
            }
        }else {
            System.out.println("try again");
        }
    }

    /**
     * 第三题
     * 删除指定学生学习，通过 examCard
     */
    @Test
    public void test3(){
        System.out.println("examCard");
        Scanner scanner = new Scanner(System.in);
        String examCard = scanner.next();
           String sql = "delete from examStudent where examCard = ?";
            int deleteCount = JDBCUtils.commonUpdate(sql,examCard);
            if(deleteCount > 0){
                System.out.println("successfully");
            }else {
                System.out.println("no such person");
            }
        }



}
