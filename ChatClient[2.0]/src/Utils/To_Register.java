package Utils;

import javax.swing.*;
import java.sql.*;

/**
 *  注册工具
 */

public class To_Register {
    /**
     *  注册方法
     */
    public void Register(String username, char[] pwd) {
        //将传入的pwd转化为String型
        String pwd_s = new String(pwd);
        /*
        连接数据库所需的基本设置
         */
        Connection con;
        Statement statement = null;
        //Mysql8.0以上固定
        String driver = "com.mysql.cj.jdbc.Driver";
        // url = jdbc:mysql://localhost: + 数据库端口 + 库名称 + ?serverTimezone=GMT
        String url = "jdbc:mysql://localhost:3306/chat?serverTimezone=GMT";
        String user = "root";
        String password = "100200";
        try {
            //设置驱动
            Class.forName(driver);
            //连接数据库
            con = DriverManager.getConnection(url, user, password);
            //连接成功时
            if (!con.isClosed())
                System.out.println("连接数据库成功（注册程序）");
            //设置statement
            statement = con.createStatement();
            //创建一个操作指令，当没有user_info表格时就创建它
            String sql = "create table if not exists user_info(username varchar(45),password varchar(45))";
            //执行指令
            statement.executeUpdate(sql);
            //创建一个操作指令，将获取的(username和pwd_s)插入到表格中的(username,password)
            sql = "insert into user_info(username,password) values('"+username+"','"+pwd_s+"')";
            //执行指令并返回一个整数
            int rw = statement.executeUpdate(sql);
            //判断数据是否插入成功
            if(rw <= 0){
                JOptionPane.showMessageDialog(null,"注册失败");
            }
            else{
                JOptionPane.showMessageDialog(null, "注册成功");
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        //无论是否登录成功，最后都要关闭statement
        }finally {
            try {
                if(statement!=null){
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  判断是否为重复用户的方法
     */
    public boolean CheckUserName(String username) {
        Connection con;
        ResultSet rs = null;
        Statement statement = null;
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/chat?serverTimezone=GMT";
        String user = "root";
        String password = "100200";
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, password);

            if (!con.isClosed())
                System.out.println("连接数据库成功（判断是否重复程序）");

            statement = con.createStatement();

            String sql = "select * from user_info";

            rs = statement.executeQuery(sql);

            while (rs.next()) {
                String username_t = rs.getString(1);
                if (username.equals(username_t)) {
                    return true;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(statement!=null){
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if(rs!=null){
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
