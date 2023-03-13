import java.sql.*;

public class ExecuteUpdate01 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // 3 adım herzaman yapılmalı unutma
        Class.forName("org.postgresql.Driver"); // forname için except attama yapmayı unutma
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/JdbcApp","postgres","654321,0");
        Statement st = con.createStatement();

        //1. Örnek: number_of_employees değeri ortalama çalışan sayısından az olan number_of_employees değerlerini 16000 olarak UPDATE edin.
        String sql1 ="update companies set number_of_employees =16000 where number_of_employees <(select Avg(number_of_employees) from companies);\n";
        int updayeEdilenRowSaysi =st.executeUpdate(sql1);// değişen satır sayısını verir veri döndurmez
        System.out.println("updayeEdilenRowSaysi = " + updayeEdilenRowSaysi);
        String sql2 ="SELECT * FROM companies;";
        ResultSet resultSet1 =st.executeQuery(sql2);
        while (resultSet1.next()){
            System.out.println(resultSet1.getInt(1)+"--"+resultSet1.getString(2)+"--"+ resultSet1.getInt(3));
        }
        con.close();
        st.close();
        resultSet1.close();


    }
}
