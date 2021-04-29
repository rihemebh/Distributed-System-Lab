import java.util.Date;

public class Sale {

    java.sql.Date date;
    String Product;
    double tax;
    double total;
    int qty;
    double amt;
    String region;
    double cost;
public Sale(){

}
    public Sale(java.sql.Date date, String product, double tax, double total, int qty, double amt, String region, double cost) {
        this.date = date;
        Product = product;
        this.region = region;
        this.cost = cost;
        this.tax = tax;
        this.qty = qty;

        this.total = total;
        this.amt = amt;

    }

    public java.sql.Date getDate() {
        return date;
    }

    public void setDate(java.sql.Date date) {
        this.date = date;
    }

    public String getProduct() {
        return Product;
    }

    public void setProduct(String product) {
        Product = product;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getAmt() {
        return amt;
    }

    public void setAmt(double amt) {
        this.amt = amt;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}

