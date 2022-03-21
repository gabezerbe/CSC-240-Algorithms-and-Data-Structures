import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Stock class - Stock class that encapsulates our 'Stock' data including the symbol, and its transactions
 */
public class Stock{
    private String symbol;
    private Queue<Transaction> transactions = new LinkedList<>();
    private ArrayList<String> sellList = new ArrayList<>();

    /**
     *
     * @param symbol - The '$STOCK' symbol that is stored for being checked later to make sure we don't have duplicate stocks being stored
     *               in our storage array
     */
    public Stock(String symbol) {
        this.symbol = symbol;

    }

    public ArrayList<String> getSellList(){
        return this.sellList;
    }

    /**
     * Getter method for retrieving a stock's symbol
     * @return String symbol
     */
    public String getSym(){
        return this.symbol;
    }

    /**
     * Parses our 'SELL' transaction objects and performs some fun math to make sure the stocks are being sold in the correct order
     * @param t - transaction object to be parsed
     */
    public void sell(Transaction t){
        double purchaseAmount = 0d;
        double sellAmount = 0d;
        double gains = 0d;

        sellList.add("SELL " + t.amount + " " + t.stock + String.format(" %.2f", t.price));

        while(t.amount != 0){
            Transaction r = transactions.remove();
            if(r.amount <= t.amount){
                t.amount -= r.amount;
                purchaseAmount += r.amount * r.price;
                sellList.add("\tSELL " + r.amount + " " + t.stock + String.format(" %.2f", r.price));
            } else {
                r.amount -= t.amount;
                purchaseAmount += t.amount * r.price;
                sellList.add("\tSELL " + t.amount + " " + t.stock + String.format(" %.2f", r.price));
                t.amount = 0;
            }
            sellAmount += r.amount * t.price;

        }

        gains = sellAmount - purchaseAmount;

        if(gains >= 0d){
            sellList.add(String.format("GAINS: %.2f %n", gains));
        } else {
            sellList.add(String.format("LOSSES: %.2f %n", gains));
        }

    }

    /**
     * Parses our 'BUY' transaction objects
     * @param t - transaction object to be parsed
     */

    public void buy(Transaction t){
        transactions.add(t);
    }

}