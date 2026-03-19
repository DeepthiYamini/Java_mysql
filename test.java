package mysql_connector;

import java.sql.*;

public class test {
    public static void main(String[] args) {
        String url  = "jdbc:mysql://localhost:3306/smsdb";
        String user = "root";
        String pass = "sa123456";   // change if you set a password

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, pass);
            System.out.println("✅ Connected to MySQL!");
            conn.close();
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("❌ SQL error: " + e.getMessage());
        }
    }
}
