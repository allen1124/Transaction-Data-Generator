import java.sql.SQLException;

public class Generator {
    private static JDBC jdbc;
    public static void main(String [] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        System.out.println("TEST");
        jdbc = new JDBC();

    }
}
