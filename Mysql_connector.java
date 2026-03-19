package mysql_connector;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

	public class Mysql_connector {

	    // MySQL details
	    private static final String URL  = "jdbc:mysql://localhost:3306/smsdb";
	    private static final String USER = "root";      // change if needed
	    private static final String PASS = "sa123456";          // change if needed

	    public static void main(String[] args) {
	        Connection conn = null;
	        try {
	            // 1. Load MySQL driver (optional in newer JDBC)
	            Class.forName("com.mysql.cj.jdbc.Driver");

	            // 2. Connect to MySQL
	            conn = DriverManager.getConnection(URL, USER, PASS);
	            System.out.println("✅ Connected to MySQL database!");

	            // 3. Example: fetch all students
	            String sql = "SELECT Id, name, email, gender FROM students";
	            PreparedStatement stmt = conn.prepareStatement(sql);
	            ResultSet rs = stmt.executeQuery();

	            System.out.println("\n--- Students ---");
	            while (rs.next()) {
	                int id = rs.getInt("Id");
	                String name = rs.getString("name");
	                String email = rs.getString("email");
	                String gender = rs.getString("gender");
	                System.out.printf("%d | %s | %s | %s\n", id, name, email, gender);
	            }

	            rs.close();
	            stmt.close();
	        } catch (ClassNotFoundException e) {
	            System.err.println("❌ MySQL JDBC driver not found.");
	            e.printStackTrace();
	        } catch (SQLException e) {
	            System.err.println("❌ SQL error: " + e.getMessage());
	            e.printStackTrace();
	        } finally {
	            // 4. Close connection
	            if (conn != null) {
	                try {
	                    conn.close();
	                    System.out.println("✅ Connection closed.");
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	    }
	}

