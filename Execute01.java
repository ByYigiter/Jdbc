import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Execute01 {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        // 1 adım : driver a kaydol
        Class.forName("org.postgresql.Driver");
        // 2 adım : database e baglan
        Connection con = DriverManager.
                getConnection("jdbc:postgresql://localhost:5432/JdbcApp","postgres","654321,0");
        // 3 adım:  statement oluştur
        Statement st = con.createStatement();



        // 4 adım  Query çalıştır
        /**
        execute() metodu DDL(CRUD) ve DQL(Select) için kullanılabilr
         1- eğer execute () metodu DDL için kullanılırsa 'false' return eder
         2- eğer execute () metodu DQL için kullanılırsa ResultSet alındığında 'true' aksi halde 'false' verir
         */

        // //3.Örnek: Drop workers table
        String sql3 ="DROP TABLE  if exists workers;";
        st.execute(sql3);
        System.out.println("Table silindi");

        //1.Örnek: "workers" adında bir table oluşturup "worker_id,worker_name, worker_salary" sütunlarını ekleyin.
        // alt satırdaki kod run edildiği zaman pgadmin tablolara ekleme yapar 2 defa run da  already exists hatsı verir.
        boolean sql1 =st.execute("CREATE TABLE workers(worker_id VARCHAR(20), worker_name VARCHAR(20), worker_salary INT)");

        System.out.println("sql1 = " + sql1);// false return eder çünku  data çağırma işlemi yapmıyoruz
        //System.out.println("Connection Success");

        // //2.Örnek: Table'a worker_address sütunu ekleyerek alter yapın.
        String sql2 ="ALTER TABLE workers ADD worker_address VARCHAR(80);";
        st.execute(sql2);
        System.out.println("Column ekleme yapıldı");

        // 5 adım: bağlantı ve statement i kapat;
        con.close();
        st.close();


    }
}