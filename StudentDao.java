package mysql_connector;

import java.sql.*;

public class StudentDao {

    // MySQL details (adjust if you set a password)
    private static final String URL  = "jdbc:mysql://localhost:3306/smsdb";
    private static final String USER = "root";
    private static final String PASS = "sa123456";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // 1. Create – Add a student
    public void addStudent(String name, String email, String dob, String gender, String phone) {
        String sql = """
            INSERT INTO students (name, email, dob, gender, phone)
            VALUES (?, ?, ?, ?, ?)
            """;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, dob);
            ps.setString(4, gender);
            ps.setString(5, phone);

            int rows = ps.executeUpdate();
            System.out.printf("✅ Added %d student(s): %s\n", rows, name);
        } catch (SQLException e) {
            System.err.println("❌ Error adding student: " + e.getMessage());
        }
    }

    // 2. Read – List all students
    public void listStudents() {
        String sql = """
            SELECT id, name, email, gender, phone
            FROM students
            """;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n--- Students ---");
            while (rs.next()) {
                int id = rs.getInt("Id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String gender = rs.getString("gender");
                String phone = rs.getString("phone");
                System.out.printf("%d | %s | %s | %s | %s\n", id, name, email, gender, phone);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error listing students: " + e.getMessage());
        }
    }

    // 3. Update – Update a student by ID
    public void updateStudent(int id, String name, String email, String dob, String gender, String phone) {
        String sql = """
            UPDATE students
            SET name = ?, email = ?, dob = ?, gender = ?, phone = ?
            WHERE Id = ?
            """;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, dob);
            ps.setString(4, gender);
            ps.setString(5, phone);
            ps.setInt(6, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.printf("✅ Updated student ID %d\n", id);
            } else {
                System.out.printf("❌ No student found with ID %d\n", id);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error updating student: " + e.getMessage());
        }
    }

    // 4. Delete – Delete a student by ID
    public void deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE Id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.printf("✅ Deleted student ID %d\n", id);
            } else {
                System.out.printf("❌ No student found with ID %d\n", id);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error deleting student: " + e.getMessage());
        }
    }

    // ================== Main test ================== //
    public static void main(String[] args) {
        StudentDao dao = new StudentDao();

        // Optional: first list existing students
        dao.listStudents();

        // 1. Add a student
        dao.addStudent("Rahul", "rahul@example.com", "2000-08-15", "M", "9988776655");

        // 2. List again to see new student
        dao.listStudents();

        // 3. Update student ID = 2 (change to your actual ID if needed)
        dao.updateStudent(2, "Rahul Kumar", "rahul.k@example.com", "2000-08-15", "M", "9988776655");

        // 4. Delete student ID = 2 (demo; comment out if you want to keep it)
        //dao.deleteStudent(2);
    }
}

