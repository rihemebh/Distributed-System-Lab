import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rabbitmq.client.DeliverCallback;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.Queue;
import java.util.concurrent.TimeoutException;

public class HO {
    private String db_name;
    Sale sale = new Sale();
    String query;
    DefaultTableModel tableModel = new DefaultTableModel();
    public HO(String db_name) throws SQLException, ClassNotFoundException {
        this.db_name = db_name;
        JFrame frame= new JFrame(db_name);
        JTable table = new JTable(tableModel);
        table.setLocation(0,500);
        tableModel.addColumn("Date");
        tableModel.addColumn("Product");
        tableModel.addColumn("tax");
        tableModel.addColumn("total");
        tableModel.addColumn("quantity");
        tableModel.addColumn("amt");
        tableModel.addColumn("region");
        tableModel.addColumn("cost");
        frame.add(new JScrollPane(table));
        frame.setSize(1000,500);
        frame.setVisible(true);
        JDBCConnection jconn = new JDBCConnection(db_name);
        String sql = "Select * from sales";
        Statement stmt=jconn.getConn().createStatement();
        display(sql, stmt);

    }
    public void exec() throws IOException, TimeoutException {
        String QUEUE_NAME =  "Insert";
        String QUEUE_NAME1 = "Delete";

        try {

            JDBCConnection jconn = new JDBCConnection(db_name);
            RabbitMqConnection rconn = new RabbitMqConnection(QUEUE_NAME,QUEUE_NAME1);
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
                tableModel.insertRow(tableModel.getRowCount(),
                        new Object[]  {sale.date, sale.Product, sale.tax, sale.total,
                                sale.qty, sale.amt,sale.region,sale.cost} );
            };


            DeliverCallback deliverCallback1 = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                JsonParser parser = new JsonParser();
                JsonObject json = (JsonObject) parser.parse(message);
                Gson gson = new Gson();
                Sale sale = gson.fromJson(json, Sale.class);

                String sql1 = "DELETE From sales Where sales.product= ? AND sales.region= ? ";
                PreparedStatement ps= null;
                try { ps = jconn.getConn().prepareStatement(sql1);
                    ps.setString(2,sale.getRegion());
                    ps.setString(1,sale.getProduct());
                    ps.executeUpdate();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                System.out.println(" [x] Received '" + message + "'");
                System.out.println(tableModel.getRowCount());
                tableModel.setRowCount(0);
                try {
                String sql2 = "Select * from sales";
                Statement stmt1 = jconn.getConn().createStatement();
                    display(sql2, stmt1);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            };

            rconn.getChannel().basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
            rconn.getChannel().basicConsume(QUEUE_NAME1, true, deliverCallback1, consumerTag -> { });

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();

    } catch (ClassNotFoundException classNotFoundException) {
        classNotFoundException.printStackTrace();
    }
        catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    private void display(String sql2, Statement stmt1) throws SQLException {
        ResultSet rs1=stmt1.executeQuery(sql2);

        while(rs1.next()){
            System.out.println(rs1);
            Sale s = new Sale();
            s.setDate(rs1.getDate(1));
            s.setRegion(rs1.getString(2));
            s.setProduct(rs1.getString(3));
            s.setQty(rs1.getInt(4));
            s.setCost(rs1.getDouble(5));
            s.setAmt(rs1.getDouble(6));
            s.setTax(rs1.getDouble(7));
            s.setTotal(rs1.getDouble(8));
            tableModel.insertRow(tableModel.getRowCount(),
                    new Object[]  {s.date, s.Product, s.tax, s.total, s.qty, s.amt,s.region,s.cost} );

        }
    }
}
