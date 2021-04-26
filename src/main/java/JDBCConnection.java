import com.google.gson.*;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import org.json.simple.JSONObject;
import java.io.IOException;
import java.sql.*;
import java.util.concurrent.TimeoutException;

public class JDBCConnection {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    final String DB_URL;
    static final String USER = "root";
    static final String PASS = "";
    private  java.sql.Connection conn =null;

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public JDBCConnection(String db_name) throws ClassNotFoundException, SQLException {
        this.DB_URL = "jdbc:mysql://localhost/"+db_name;
        // jdbc connection

            //Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            //Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(this.DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");
    }




    public void queryexec(Sale sale, PreparedStatement ps) throws SQLException {
        ps.setDate(1, (java.sql.Date) sale.getDate());
        ps.setString(2,sale.getRegion());
        ps.setString(3,sale.getProduct());
        ps.setInt(4,sale.getQty());
        ps.setDouble(5,sale.getCost());
        ps.setDouble(6,sale.getAmt());
        ps.setDouble(7,sale.getTax());
        ps.setDouble(8,sale.getTotal());
        ps.executeUpdate();
    }

}
