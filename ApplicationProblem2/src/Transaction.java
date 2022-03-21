
/**
 * Transaction class for storing our transaction strings as an object
 */

public class Transaction{
    public String action;
    public int amount;
    public String stock;
    public double price;

    public Transaction(String action, int amount, String stock, double price){
        this.action = action;
        this.amount = amount;
        this.stock = stock;
        this.price = price;
    }

}