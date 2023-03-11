import java.sql.*;

public class CallableStatement01 {
    /*
    Java da methotlar return type sahibi olsada olmasada method olarak adlandırılır
    SQL de ise data return ediyorsa "Function" denir return yapmıyorsa "Procedure" olarak adlandırıllır.
     */

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/JdbcApp","postgres","654321,0");
        Statement st = con.createStatement();

        // CallableStatement ile function çagırmayı parametrelendireceğiz
        //  1adım : function kodunu yaz.
        String sql1 ="Create or replace function toplamaF(x numeric,y numeric)\n" +
                "returns numeric \n" +
                "language plpgsql\n" +
                "as \n" +
                "$$-- () yerine gecer\n" +
                "BEGIN\n" +
                "return x+y;\n" +
                "end\n" +
                "$$";

        // 2 adım :function u çalıştır
        st.execute(sql1);
        // 3 adım function u çağır
        CallableStatement cst1 =con.prepareCall("{? =call toplamaF(?,?)}");// 1. ? = return type,  2 ve 3 ? = girilecek değer
        //  4 adım :Return için registerOutParameter()  metodunu parametreler için ise setInt() .. metodlarını kullanıcağız
        cst1.registerOutParameter(1,Types.NUMERIC);
        cst1.setInt(2,6);
        cst1.setInt(3,4);
        //  5 adım : execute ile CallableStatement ı çalıştır
       cst1.execute();
        // 6 adım sonucu çağırmak için return data type tipine göre
         System.out.println(cst1.getBigDecimal(1));

        //2. Örnek: Koninin hacmini hesaplayan bir function yazın.
        String sql2 ="Create or replace function koni_hacimF(r numeric,h numeric)\n" +
                "returns numeric \n" +
                "language plpgsql\n" +
                "as \n" +
                "$$-- () yerine gecer\n" +
                "BEGIN\n" +
                "return 3.14*r*r*h/3;\n" +
                "end\n" +
                "$$";
        st.execute(sql2);
        CallableStatement cst2 =con.prepareCall("{? =call koni_hacimF(?,?)}");
        cst2.registerOutParameter(1,Types.NUMERIC);
        cst2.setInt(2,1);
        cst2.setInt(3,6);
        cst2.execute();
        System.out.printf("%.2f",cst2.getBigDecimal(1));// %.2f ==> %. ->herhangi bir tamsayı  , 2f 2 rakam lı olarak yazdır
        System.out.println();
        System.out.printf("%.3f",2.12354653);// ==> 2,124 olarak cıktı verir


    }
}
