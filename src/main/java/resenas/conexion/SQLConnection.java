package resenas.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import resenas.utils.Utils;

public class SQLConnection {

    String url = "jdbc:mysql://" + Utils.MYSQL_HOST + ":" + Utils.MYSQL_PORT + "/" + Utils.MYSQL_DATABASE_NAME;
    Connection con = null;

    public Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url,
                    Utils.MYSQL_USERNAME, Utils.MYSQL_PASSWORD);
            System.out.println("Conexión exitosa");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    public void closeConnection() {
        try {
            con.close();
            System.out.println("Conexión cerrada");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
