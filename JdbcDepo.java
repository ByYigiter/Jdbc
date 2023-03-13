import java.sql.*;

public class JdbcDepo {
    private  static Connection connection;
    private static Statement statement;
    ResultSet resultSet;

    public static Connection getConnection(String localhost,String database,String username,String password ){
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            connection= DriverManager.getConnection("jdbc:postgresql://" + localhost + ":5432/" + database, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (connection != null) {
            System.out.println("Database bağlandı");
        } else System.out.println("Bağlantı Başarısız");

        return connection;
    }
    public static Statement statementCreated(){
        try {
            statement=connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return statement;
    }
}
