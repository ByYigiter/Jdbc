import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Runner05 {

    public static void main(String[] args) {
       Connection connection=JdbcDepo.getConnection("localhost","JdbcApp","postgres","654321,0");
       Statement st =JdbcDepo.statementCreated();
        System.out.println(st);
    }
}
