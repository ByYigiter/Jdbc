import java.sql.*;

public class PreparedStatement01 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        /*
PreparedStatement interface, birden cok kez calistirilabilen onceden derlenmis bir SQL kodunu temsil eder.
Parametrelendirilmis(Parameterised) SQL query(sorgu)'leri ile calisir. Bir sorguyu 0 veya daha fazla parametre ile kullanabiliriz
 */
        // 3 adım herzaman yapılmalı unutma
        Class.forName("org.postgresql.Driver"); // forname için except attama yapmayı unutma
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/JdbcApp","postgres","654321,0");
        Statement st = con.createStatement();

        //Connection con =JdbcUtils.connectToDataBase();
        //Statement st=JdbcUtils.createStatement();

        //1. Örnek: Prepared statement kullanarak company adı IBM olan number_of_employees değerini 9999 olarak güncelleyin.
        // 1 adım PreparedStatement query sini oluştur  __> yani dinamik yapılacak yerleri ? koyuyoruz amaç bu
        String sql1 =" update companies set number_of_employees= ? where company =?;";
        // 2 adım : PreparedStatement objesini oluştur
        PreparedStatement pst1 = con.prepareStatement(sql1);
        // 3 adım : setInt(),setString() ...  methodlarını kullanarak soru işaretleri yerlerine deger ata.
        pst1.setInt(1,9999);
        pst1.setString(2,"IBM");
        // 4 adim : execute query i çalıştır
        int updaterowsayisi = pst1.executeUpdate();
        System.out.println("updaterowsayisi = " + updaterowsayisi);
        String sql2 = "SELECT * FROM companies;";
        ResultSet resultSet1 =st.executeQuery(sql2);
        while (resultSet1.next()){
            System.out.println(resultSet1.getInt(1) + "--" + resultSet1.getString(2) + "--" + resultSet1.getInt(3));
        }
        //2. Örnek: Prepared statement kullanarak company adı GOOGLE olan number_of_employees değerini 5555 olarak güncelleyin.
        pst1.setInt(1,5555);
        pst1.setString(2,"GOOGLE");
        int updaterowsayisi2 = pst1.executeUpdate();
        System.out.println("updaterowsayisi = " + updaterowsayisi2);
        String sql3 = "SELECT * FROM companies;";
        ResultSet resultSet2 =st.executeQuery(sql3);
        while (resultSet2.next()){
            System.out.println(resultSet2.getInt(1) + "--" + resultSet2.getString(2) + "--" + resultSet2.getInt(3));
        }

        con.close();
        st.close();
        resultSet1.close();
        resultSet2.close();
        pst1.close();


    }
}
