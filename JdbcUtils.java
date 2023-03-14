import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JdbcUtils {

    private static Connection con;
    private static Statement st;
    private static ResultSet resultSet=null;

    public static Connection connectToDataBase(String hostname, String dbname, String username, String password) {
        // 1 adım : driver a kaydol
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        // 2 adım : database e baglan
        try {
            con = DriverManager.
                    getConnection("jdbc:postgresql://" + hostname + ":5432/" + dbname, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (con != null) {
            System.out.println("Database bağlandı");
        } else System.out.println("Bağlantı Başarısız");
        return con;
    }

    // 3 adım :Statement oluştur
    public static Statement createStatement() {

        try {
            st = con.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return st;
    }

    // 4 adım  Query çalıştır
    public static Boolean execute(String sql) {
        boolean isExecute;
        try {
            isExecute = st.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return isExecute;
    }

    // Table oluşturan method
    // -- ornek : "CREATE TABLE tablename(sutunadi datatipi,sutunadi datatipi,sutunadi datatipi,....)"
    public static void createTable(String tableName, String... columnName_dataType) {
        StringBuilder columnName_dataValue = new StringBuilder("");
        for (String w : columnName_dataType) {
            columnName_dataValue.append(w).append(",");
        }
        columnName_dataValue.deleteCharAt(columnName_dataValue.length() - 1);

        try {
            st.execute("CREATE TABLE " + tableName + "(" + columnName_dataValue + ")");
            System.out.println(tableName.toUpperCase() + " has been created");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    // Tabloya sutun veri ekleme insert into
    public static void insertTable(String tableName, String... columnName_Value) {

        StringBuilder columnNames = new StringBuilder("");
        StringBuilder values = new StringBuilder("");

        for (String w : columnName_Value) {
            columnNames.append(w.split(" ")[0]).append(",");
            values.append(w.split(" ")[1]).append(",");
        }

        columnNames.deleteCharAt(columnNames.lastIndexOf(","));
        values.deleteCharAt(values.lastIndexOf(","));

        String sql = "INSERT INTO " + tableName + "(" + columnNames + ") VALUES(" + values + ")";
        execute(sql);//execute methodu üstte oluşturuldu, query'yi çalıştırıyor.
        System.out.println("Sutun veri ekleme yapıldı");

    }
        // executeQuery metodu

        public static ResultSet executeQuery(String query) {
            try {
                resultSet = st.executeQuery(query);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return resultSet;
        }

    //ExecuteUpdate methodu
    public static int executeUpdate(String query) {
        int guncellenenSatirSayisi;

        try {
            guncellenenSatirSayisi = st.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return guncellenenSatirSayisi;
    }

    // sutun verilerini liste olarak alma;
    public static List<Object> columnList(String columnName, String tableName) {
        List<Object> column = new ArrayList<>();
        String sql = "SELECT " + columnName + " FROM " + tableName;
        resultSet =JdbcUtils.executeQuery(sql);
        try {
            while (resultSet.next()){
                column.add(resultSet.getObject(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return column;
    }
    public static List<Object> allColumnList (String tableName,String columnName){
        // tum satırları getirme
        String sql3 = "SELECT " +columnName+" FROM "+ tableName;

        resultSet =JdbcUtils.executeQuery(sql3);

        // sutun sayısını bulma
        ResultSet columnSayisi =JdbcUtils.executeQuery(sql3);
        System.out.println(columnSayisi);
        ResultSetMetaData metadata = null;
        try {
            metadata = columnSayisi.getMetaData();
            System.out.println("Column count : " + metadata.getColumnCount());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // column names bulma
        try {
            for (int i = 1; i < metadata.getColumnCount() + 1; i++) {
                System.out.println(metadata.getColumnName(i) + " " + metadata.getColumnTypeName(i));
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    //  tüm sutunları liste halinde getirme;
        List<Object> allColumn = new ArrayList<>();
        String str ="";
        try {
            while (resultSet.next()) {
                //burası bilinen sutun sayıları yerine bilinmeyen sutun sayısı için kullanılır
                for (int i = 1; i <= metadata.getColumnCount(); i++) {
                    System.out.print(resultSet.getObject(i)+" ");
                    str+=" "+resultSet.getObject(i)+" -";
                }
                System.out.println();

            }
            str =str.substring(0,str.length()-2).trim()+",";
            allColumn.add(str);
            System.out.println();

        } catch (SQLException e) {
            throw new RuntimeException();

        }
        //System.out.println(allColumn.get(allColumn.size()-1));
        List<Object> sonSira =new ArrayList<>();
        sonSira.add(allColumn.get(allColumn.size()-1));
        return allColumn;
        //System.out.println(sonSira);


//        /**  database sutun sayısını bulma */
//        ResultSet rst1 =JdbcUtils.executeQuery("Select * from ccc");
//        ResultSetMetaData metadata=rst1.getMetaData();
//        System.out.println("Column count : "+metadata.getColumnCount());
//
//        /** sutun adlarını ve data turlerini bulma öğrenme */
//        for(int i=1;i<metadata.getColumnCount()+1;i++){
//            System.out.println(metadata.getColumnName(i)+" "+metadata.getColumnTypeName(i));
//        }
//        /*
//        * Statement stmt=connection.createStatement();
//    ResultSet rs=stmt.executeQuery("select * from BankAccount");
//    ResultSetMetaData metadata=rs.getMetaData();
//    System.out.println("Column count : "+metadata.getColumnCount());
//    for(int i=1;i<metadata.getColumnCount()+1;i++){
//        System.out.println(metadata.getColumnName(i)+" "+metadata.getColumnTypeName(i));
//    }    */


    }


    // Tablo silen method
    //
    public static void dropTable(String tableName) {
        try {
            st.execute("DROP TABLE " + tableName);
            System.out.println("Table " + tableName + " Silindi");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    // 5 adım: bağlantı ve statement i kapat;
    public static void closeConnectionAndStatement() {
        try {
            con.close();
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            System.out.println(con.isClosed() && st.isClosed() ? "Connection and Statement closed" : "Connection and Statement NOT closed");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
