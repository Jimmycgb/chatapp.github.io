package Utils;

import java.sql.*;

/**
 *  登录工具
 */

public class Check {
    public boolean CheckUser(String username, char[] pwd) {
        //将传入的pwd转化为String型
        String pwd_s = new String(pwd);
        /*
        连接数据库所需的基本设置
         */
        Connection con;
        ResultSet rs = null;
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
                System.out.println("连接数据库成功（登录程序）");
            //设置statement
            statement = con.createStatement();
            //创建一个操作指令，获取user_info里的数据
            String sql = "select * from user_info";
            //获取数据
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                String username_t = rs.getString(1);//(读取表格中的第一列)
                String pwd_t = rs.getString(2);//(读取表格中的第二列)
                //当发现相同数据时（即已注册的用户）
                if (username.equals(username_t) && pwd_s.equals(pwd_t)) {
                    return true;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        //无论是否登录成功，最后都要关闭statement和rs
        }finally {
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
        //若没有发现已注册用户，最终返回false
        return false;
    }
}
