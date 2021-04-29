import com.google.gson.Gson;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;
import java.util.concurrent.TimeoutException;

public class BO {

    private String db_name;
    Sale sale = null;
    String Query;
    JTextField pname;
    JTextField cost;
    JTextField qty;
    ButtonGroup gengp;
    JTextField tax;
    JTextField amt;
    JTextField totale;
    ButtonGroup gp;
    JButton sub;
    DefaultTableModel tableModel = new DefaultTableModel();
    public BO(String db_name) throws SQLException, ClassNotFoundException {
        this.db_name = db_name;


        JFrame frame= new JFrame(db_name);
        JPanel panel = new JPanel();
        panel.setBackground(Color.LIGHT_GRAY);
        frame.add(panel);
        JLabel title = new JLabel("Add product");
        title.setFont(new Font("Arial", Font.PLAIN, 20));
        title.setSize(300, 30);
        title.setLocation(200, 230);
        frame.add(title);

        JLabel name = new JLabel("Product");
        name.setFont(new Font("Arial", Font.PLAIN, 20));
        name.setSize(100, 20);
        name.setLocation(100, 300);
        frame.add(name);

        pname = new JTextField();
        pname.setFont(new Font("Arial", Font.PLAIN, 15));
        pname.setSize(190, 20);
        pname.setLocation(200, 300);
        frame.add(pname);

        JLabel mno = new JLabel("Cost");
        mno.setFont(new Font("Arial", Font.PLAIN, 20));
        mno.setSize(100, 20);
        mno.setLocation(100, 350);
        frame.add(mno);

        cost = new JTextField();
        cost.setFont(new Font("Arial", Font.PLAIN, 15));
        cost.setSize(150, 20);
        cost.setLocation(200, 350);
        frame.add(cost);



        JLabel region = new JLabel("Region");
        region.setFont(new Font("Arial", Font.PLAIN, 20));
        region.setSize(100, 20);
        region.setLocation(100, 400);
        frame.add(region);

        JRadioButton north = new JRadioButton("North");
        north.setFont(new Font("Arial", Font.PLAIN, 15));
        north.setSelected(true);
        north.setSize(75, 20);
        north.setLocation(200, 400);
        frame.add(north);

        JRadioButton west = new JRadioButton("West");
        west.setFont(new Font("Arial", Font.PLAIN, 15));
        west.setSelected(false);
        west.setSize(80, 20);
        west.setLocation(275, 400);
        frame.add(west);

        JRadioButton east = new JRadioButton("East");
        east.setFont(new Font("Arial", Font.PLAIN, 15));
        east.setSelected(false);
        east.setSize(80, 20);
        east.setLocation(350, 400);
        frame.add(east);

        JRadioButton south = new JRadioButton("South");
        south.setFont(new Font("Arial", Font.PLAIN, 15));
        south.setSelected(false);
        south.setSize(80, 20);
        south.setLocation(425, 400);
        frame.add(south);

        gengp = new ButtonGroup();
        gengp.add(north);
        gengp.add(west);
        gengp.add(east);
        gengp.add(south);


        JLabel taxx = new JLabel("Tax");
        taxx.setFont(new Font("Arial", Font.PLAIN, 20));
        taxx.setSize(100, 20);
        taxx.setLocation(100, 450);
        frame.add(taxx);

        tax = new JTextField();
        tax.setFont(new Font("Arial", Font.PLAIN, 15));
        tax.setSize(150, 20);
        tax.setLocation(200, 450);
        frame.add(tax);

        JLabel a = new JLabel("Amt");
        a.setFont(new Font("Arial", Font.PLAIN, 20));
        a.setSize(100, 20);
        a.setLocation(100, 500);
        frame.add(a);

        amt = new JTextField();
        amt.setFont(new Font("Arial", Font.PLAIN, 15));
        amt.setSize(150, 20);
        amt.setLocation(200, 500);
        frame.add(amt);

        JLabel tot = new JLabel("Totale");
        tot.setFont(new Font("Arial", Font.PLAIN, 20));
        tot.setSize(100, 20);
        tot.setLocation(100, 550);
        frame.add(tot);

        totale = new JTextField();
        totale.setFont(new Font("Arial", Font.PLAIN, 15));
        totale.setSize(150, 20);
        totale.setLocation(200, 550);
        frame.add(totale);

        JLabel qt = new JLabel("Quantity");
        qt.setFont(new Font("Arial", Font.PLAIN, 20));
        qt.setSize(100, 20);
        qt.setLocation(100, 600);
        frame.add(qt);

        qty = new JTextField();
        qty.setFont(new Font("Arial", Font.PLAIN, 15));
        qty.setSize(150, 20);
        qty.setLocation(200, 600);
        frame.add(qty);

        JLabel query = new JLabel("Query");
        query.setFont(new Font("Arial", Font.PLAIN, 20));
        query.setSize(100, 20);
        query.setLocation(100, 650);
        frame.add(query);

        JRadioButton insert = new JRadioButton("Insert");
        insert.setFont(new Font("Arial", Font.PLAIN, 15));
        insert.setSelected(true);
        insert.setSize(75, 20);
        insert.setLocation(200, 650);
        frame.add(insert);

        JRadioButton delete = new JRadioButton("Delete");
        delete.setFont(new Font("Arial", Font.PLAIN, 15));
        delete.setSelected(false);
        delete.setSize(80, 20);
        delete.setLocation(275, 650);
        frame.add(delete);



        gp = new ButtonGroup();
        gp.add(insert);
        gp.add(delete);


        sub = new JButton("Submit");
        sub.setFont(new Font("Arial", Font.PLAIN, 15));
        sub.setSize(100, 20);
        sub.setLocation(150, 750);
        sub.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
           System.out.println("qty= "+qty.getText());
           sale= new Sale();

            if(insert.isSelected()){
                Query = "Insert";
            }else if(delete.isSelected()){
                Query="Delete";
            }

                sale.setProduct(pname.getText());
                if(east.isSelected()){
                    sale.setRegion("east");
                }else if(west.isSelected()){
                    sale.setRegion("west");
                }else if(north.isSelected()){
                    sale.setRegion("north");
                }else if(south.isSelected()){
                    sale.setRegion("south");
                }
                long millis = System.currentTimeMillis();
                sale.setDate(new Date(millis));
                if(Query.equals("Insert")) {
                    System.out.println(sale.getDate());
                    sale.setAmt(Double.parseDouble(amt.getText()));
                    sale.setQty(Integer.parseInt(qty.getText()));
                    sale.setCost(Double.parseDouble(cost.getText()));
                    sale.setTax(Double.parseDouble(tax.getText()));
                    sale.setTotal(Double.parseDouble(totale.getText()));
                }
                try {
                    exec();
                } catch (IOException | TimeoutException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        frame.add(sub);
        JTable table = new JTable(tableModel);
        table.setLocation(100,550);
        tableModel.addColumn("Date");
        tableModel.addColumn("Product");
        tableModel.addColumn("tax");
        tableModel.addColumn("total");
        tableModel.addColumn("quantity");
        tableModel.addColumn("amt");
        tableModel.addColumn("region");
        tableModel.addColumn("cost");
        frame.add(new JScrollPane(table));
        frame.setSize(600,850);
        frame.setVisible(true);

        JDBCConnection jconn = new JDBCConnection(db_name);
        //Execute a query
        String sql = "Select * from sales";
        Statement stmt=jconn.getConn().createStatement();
        ResultSet rs=stmt.executeQuery(sql);
        while(rs.next()){
            Sale s = new Sale();
            s.setDate(rs.getDate(1));
            s.setRegion(rs.getString(2));
            s.setProduct(rs.getString(3));
            s.setQty(rs.getInt(4));
            s.setCost(rs.getDouble(5));
            s.setAmt(rs.getDouble(6));
            s.setTax(rs.getDouble(7));
            s.setTotal(rs.getDouble(8));

            tableModel.insertRow(tableModel.getRowCount(),
                    new Object[]  {s.date, s.Product, s.tax, s.total, s.qty, s.amt,s.region,s.cost} );
        }
    }

    public void exec() throws IOException, TimeoutException {
        //Sender rabbitmq connection
        String QUEUE_NAME = "Insert";
        String QUEUE_NAME1 = "Delete";

        RabbitMqConnection rconn = new RabbitMqConnection(QUEUE_NAME,QUEUE_NAME1);

        //Json
        Gson gson = new Gson();
        if(sale!= null){
            String json = gson.toJson(sale);

            if(Query.equals(QUEUE_NAME)){
                rconn.getChannel().basicPublish("", QUEUE_NAME, null, json.getBytes());
            }else if(Query.equals(QUEUE_NAME1)){
                rconn.getChannel().basicPublish("", QUEUE_NAME1, null, json.getBytes());
            }
            System.out.println(" [x] Sent '" + json + "'");


            try {
                JDBCConnection jconn = new JDBCConnection(db_name);
                //Execute a query


                String sql1;
                if(Query.equals("Insert")){
                    sql1 = "insert into sales values (?,?,?,?,?,?,?,?)";
                    PreparedStatement ps= jconn.getConn().prepareStatement(sql1);
                    jconn.queryexec(sale, ps);
                    tableModel.insertRow(tableModel.getRowCount(),
                            new Object[]  {sale.date, sale.Product, sale.tax, sale.total, sale.qty,
                                    sale.amt,sale.region,sale.cost} );
                }else if(Query.equals("Delete")){

                    sql1 = "DELETE From sales Where product= ? AND region= ? ";
                    PreparedStatement ps= null;
                    try { ps = jconn.getConn().prepareStatement(sql1);
                        ps.setString(2,sale.getRegion());
                        ps.setString(1,sale.getProduct());
                        ps.executeUpdate();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    tableModel.setRowCount(0);
                 String  sql = "Select * from sales";
                  Statement   stmt=jconn.getConn().createStatement();
                 ResultSet   rs=stmt.executeQuery(sql);

                    display(rs);
                }


            } catch (Exception se) {
                //Handle errors for JDBC
                se.printStackTrace();
            }//Handle errors for Class.forName


        }

    }

    private void display(ResultSet rs) throws SQLException {
        while(rs.next()){
            Sale s = new Sale();
            s.setDate(rs.getDate(1));
            s.setRegion(rs.getString(2));
            s.setProduct(rs.getString(3));
            s.setQty(rs.getInt(4));
            s.setCost(rs.getDouble(5));
            s.setAmt(rs.getDouble(6));
            s.setTax(rs.getDouble(7));
            s.setTotal(rs.getDouble(8));

            tableModel.insertRow(tableModel.getRowCount(),
                    new Object[]  {s.date, s.Product, s.tax, s.total, s.qty, s.amt,s.region,s.cost} );
        }
    }
}
