import com.google.gson.Gson;

import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeoutException;

public class BO {

    private String db_name;

    public BO(String db_name) {
        this.db_name = db_name;
    }

    public void exec() throws IOException, TimeoutException {
        //Sender rabbitmq connection
        String QUEUE_NAME = "Insert";
        RabbitMqConnection rconn = new RabbitMqConnection(QUEUE_NAME);
        long millis=System.currentTimeMillis();
        Sale sale = new Sale(new Date(millis), "Paper",66.2,
                1.011,70,940,"East", 12.2);
        //Json
        Gson gson = new Gson();
        String json = gson.toJson(sale);

        rconn.getChannel().basicPublish("", QUEUE_NAME, null, json.getBytes());
        System.out.println(" [x] Sent '" + json + "'");


        // jdbc connection

        try {
            JDBCConnection jconn = new JDBCConnection(db_name);
            //Execute a query
            String sql1 = "insert into sales values (?,?,?,?,?,?,?,?)";
            PreparedStatement ps= jconn.getConn().prepareStatement(sql1);
            jconn.queryexec(sale, ps);

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();

        }
        //end try
        System.out.println("Goodbye!");
    }
}
