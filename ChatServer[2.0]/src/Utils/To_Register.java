package Utils;

import javax.swing.*;
import java.sql.*;

public class To_Register {
    public void Register(String username, char[] pwd) {
        Connection con;
        Statement statement = null;
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/chat?serverTimezone=GMT";
        String user = "root";
        String password = "100200";
        String pwd_s = new String(pwd);
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, password);

            if (!con.isClosed())
                System.out.println("Succeeded connecting to the Database!(R)");

            statement = con.createStatement();

            String sql = "create table if not exists manage_info(username varchar(45),password varchar(45))";

            statement.executeUpdate(sql);

            sql = "insert into manage_info(manage_name,manage_pwd) values('"+username+"','"+pwd_s+"')";

            int rw = statement.executeUpdate(sql);

            if(rw <= 0){//判断数据是否插入成功
                JOptionPane.showMessageDialog(null,"注册失败");
            }
            else{
                JOptionPane.showMessageDialog(null, "注册成功");
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
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
                System.out.println("Succeeded connecting to the Database!(RN)");

            statement = con.createStatement();

            String sql = "select * from manage_info";

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
