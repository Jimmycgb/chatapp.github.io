package Utils;

import java.sql.*;

public class Check {
    public boolean CheckUser(String username, char[] pwd) {
        Connection con;
        ResultSet rs = null;
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
                System.out.println("Succeeded connecting to the Database!(L)");

            statement = con.createStatement();

            String sql = "select * from manage_info";

            rs = statement.executeQuery(sql);

            while (rs.next()) {
                String username_t = rs.getString(1);
                String pwd_t = rs.getString(2);
                if (username.equals(username_t) && pwd_s.equals(pwd_t)) {
                    return true;
                }
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
