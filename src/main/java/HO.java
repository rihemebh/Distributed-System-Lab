import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rabbitmq.client.DeliverCallback;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HO {
    private String db_name;

    public HO(String db_name) {
        this.db_name = db_name;
    }
    public void exec(){
        String QUEUE_NAME = "Insert";


        try {
            JDBCConnection jconn = new JDBCConnection(db_name);

            //rabbitmq receiver connection
            RabbitMqConnection rconn = new RabbitMqConnection(QUEUE_NAME);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                JsonParser parser = new JsonParser();
                JsonObject json = (JsonObject) parser.parse(message);
                Gson gson = new Gson();
                Sale sale = gson.fromJson(json, Sale.class);

                String sql1 = "insert into sales values (?,?,?,?,?,?,?,?)";
                PreparedStatement ps= null;
                try { ps = jconn.getConn().prepareStatement(sql1);

                    jconn.queryexec(sale, ps);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                System.out.println(" [x] Received '" + message + "'");
            };

            rconn.getChannel().basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });

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
