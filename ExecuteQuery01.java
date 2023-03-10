import java.sql.*;

public class ExecuteQuery01 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // 1 adım : driver a kaydol
        Class.forName("org.postgresql.Driver"); // forname için except attama yapmayı unutma
        // 2 adım : database e baglan
        Connection con = DriverManager.
                getConnection("jdbc:postgresql://localhost:5432/JdbcApp","postgres","654321,0");
        // 3 adım:  statement oluştur
        Statement st = con.createStatement();



        ////1. Örnek:  region id'si 1 olan "country name" değerlerini çağırın.
        String str1="select country_name from countries where region_id=1;";
        boolean r1=st.execute(str1);// execute bize boolean verir sadece alıp almadığını söyler
        System.out.println(r1);
        // Recordları görmek için ExecuteQuery() metodu kullanmalıyız.
        ResultSet resultSet1 = st.executeQuery(str1);
        while (resultSet1.next()){
            System.out.println(resultSet1.getString("country_name")); // yada 1 yazınca 1 sutunu alır 1 ="country_name" olur
        }
        System.out.println("==".repeat(15));

        //2.Örnek: "region_id"nin 2'den büyük olduğu "country_id" ve "country_name" değerlerini çağırın.

        String str2 ="select country_name,country_id  from countries where region_id>2;";
        ResultSet resultSet2= st.executeQuery(str2);
        while (resultSet2.next()){
            System.out.println(resultSet2.getString(1)+"\t--\t"+ resultSet2.getString(2));
        }

        System.out.println("==".repeat(15));

        //3.Örnek: "number_of_employees" değeri en düşük olan satırın tüm değerlerini çağırın.
        String str3 ="select * from companies where number_of_employees =(select min(number_of_employees)number from companies);";
        ResultSet resultSet3 =st.executeQuery(str3);
        while (resultSet3.next()){
            System.out.println(resultSet3.getInt(1)+"-"+resultSet3.getString(2)+"-"+resultSet3.getInt(3));
        }
        con.close();
        st.close();


    }
}
