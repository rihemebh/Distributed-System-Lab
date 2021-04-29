import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

public class Test {

    public static void main(String[] args) throws IOException, TimeoutException, SQLException, ClassNotFoundException {
     HO ho = new HO("ho");
     new BO("bo1");
     new BO("bo2");

   ho.exec();

   // bo2.exec();
    }
}
