import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class JDBC {
    private static final String DB_HOST = "localhost";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "allen44129277";
    private static final String DB_NAME = "TDG";
    private Connection conn;

    public JDBC() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        conn = DriverManager.getConnection("jdbc:mysql://"+DB_HOST+
                "/"+DB_NAME+
                "?user="+DB_USER+
                "&password="+DB_PASS);
        System.out.println("Database connection successful.");
    }

    public void insert(Object data) {
        String insertSQL = "";
        PreparedStatement pstmt = null;
        try {
            if(data instanceof Stock) {
                insertSQL = "INSERT INTO Stocks (isinCode, name, code, category, boardLot) VALUES (?, ?, ?, ?, ?)";
                pstmt = conn.prepareStatement(insertSQL);
                pstmt.setString(1, ((Stock) data).getISINCode());
                pstmt.setString(2, ((Stock) data).getStockName());
                pstmt.setString(3, ((Stock) data).getStockCode());
                pstmt.setString(4, ((Stock) data).getCategory());
                pstmt.setInt(5, ((Stock) data).getBoardLot());
            }
            pstmt.execute();
            System.out.println("Record created");
        } catch (SQLException  e) {
            System.err.println("Error inserting record: "+e);
        }
    }

    public void read(String name) {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT birthday FROM c0402_2017_t4 WHERE name = ?");
            stmt.setString(1, name);

            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                System.out.println("Birthday of "+name+" is on "+rs.getDate(1).toString());
            } else {
                System.out.println(name+" not found!");
            }
        } catch (SQLException e) {
            System.err.println("Error reading record: "+e);
        }

    }

    public void list() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT name, birthday FROM c0402_2017_t4");
            while(rs.next()) {
                System.out.println("Birthday of "+rs.getString(1)+" is on "+rs.getDate(2).toString());
            }
        } catch (SQLException e) {
            System.err.println("Error listing records: "+e);
        }

    }

    public void update(String name, String birthday) {
        try {
            PreparedStatement stmt = conn.prepareStatement("UPDATE c0402_2017_t4 SET birthday = ? WHERE name = ?");
            stmt.setDate(1, java.sql.Date.valueOf(birthday));
            stmt.setString(2, name);

            int rows = stmt.executeUpdate();
            if(rows > 0) {
                System.out.println("Birthday of "+name+" updated");
            } else {
                System.out.println(name+" not found!");
            }
        } catch (SQLException e) {
            System.err.println("Error reading record: "+e);
        }

    }

    public void delete(String name) {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM c0402_2017_t4 WHERE name = ?");
            stmt.setString(1, name);
            int rows = stmt.executeUpdate();
            if(rows > 0) {
                System.out.println("Record of "+name+" removed");
            } else {
                System.out.println(name+" not found!");
            }
        } catch (SQLException e) {
            System.err.println("Error inserting record: "+e);
        }
    }
}
