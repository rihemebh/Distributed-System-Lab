import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

public class Test {

    public static void main(String[] args) throws IOException, TimeoutException {
     HO ho = new HO("ho");
     BO bo1 = new BO("bo1");
   //  BO bo2 = new BO("bo2");

     ho.exec();
     bo1.exec();
    }
}
