import java.sql.*;
import java.util.Calendar;
import java.util.TimeZone;

public class DatabaseConection {

// I changed original information because of confidential
    private final String url = "jdbc:postgresql://132.576.189.98/PDS";
    private final String user = "user";
    private final String password = "password";


    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public long insertToDataBase(FileInfo back_up) {
        String SQL = "INSERT INTO backup_report(machine, version, modify_date, sizes, paths) "
                + "VALUES(?,?,?,?,?)";

        long id = 0;

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL,
                     Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, back_up.getName());
            pstmt.setString(2, back_up.getVersion());
            pstmt.setTimestamp(3, back_up.getDateOfMod());
            pstmt.setLong(4, back_up.getSize());
            pstmt.setString(5, back_up.getAbsolutePath());

            int affectedRows = pstmt.executeUpdate();
            // check the affected rows
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getLong(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return id;
    }

    public int deleteAll() {
        String SQL = "DELETE FROM backup_report";

        int affectedrows = 0;

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            affectedrows = pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return affectedrows;
    }

}
