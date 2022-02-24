import java.io.*;
import java.net.URL;
import java.sql.Array;
import java.util.*;


/**
 * Capital gains calculator
 *
 * This class must take an input of strings that act as transactions for stocks in the format of
 * <action> <amount> <stock symbol> <price>
 * The stock class should maintain its symbol, the amount purchased and at what price
 * The portfolio class will have an array of these stocks that it 'controls'
 * and should have methods to transact with them such as sell or buy
 */
public class Portfolio {
    private ArrayList<String> stockNames = new ArrayList<String>();
    private ArrayList<Stock> stocks = new ArrayList<Stock>();

    /**
     * Our stock class that will store its symbol and transaction history
     */

    private class Stock{
        private String symbol;
        private Queue<Transaction> transactions = new LinkedList<>();

        public Stock(String symbol) {
            this.symbol = symbol;

        }

        public String getSym(){
            return this.symbol;
        }

        public void Sell(Transaction t){
            float purchaseAmount = 0f;
            float sellAmount = 0f;
            float gains = 0f;

            System.out.println("SELL " + t.amount + " " + t.stock + " " + t.price);
            while(t.amount != 0){
                Transaction r = transactions.remove();
                if(r.amount <= t.amount){
                    t.amount -= r.amount;
                    purchaseAmount += r.amount * r.price;
                    System.out.println("\tSELL " + r.amount + " " + t.stock + " " + r.price);
                } else {
                    r.amount -= t.amount;
                    purchaseAmount += t.amount * r.price;
                    System.out.println("\tSELL " + t.amount + " " + t.stock + " " + r.price);
                    t.amount = 0;
                }
                sellAmount += r.amount * t.price;

            }
            /**
             * I have no idea why, but it's setting the decimal points both to zero despite being
             * two floats that are getting multiplied
             */
            gains = sellAmount - purchaseAmount;

            if(gains >= 0f){
                System.out.printf("GAINS: %.2f %n", gains);
            } else {
                System.out.printf("LOSSES: %.2f %n", gains);
            }

        }

        public void Buy(Transaction t){
            transactions.add(t);
            System.out.println("BUY " + t.amount + " " + t.stock + " " + t.price);
        }

    }

    /**
     * Transaction class for storing our transactions
     */

    private class Transaction{
        private String action;
        private int amount;
        private String stock;
        private float price;

        public Transaction(String action, int amount, String stock, float price){
            this.action = action;
            this.amount = amount;
            this.stock = stock;
            this.price = price;
        }

    }

    /**;
     * Reads in our transactions.txt
     * @return
     */
    public void readTransactions(String file, ArrayList<String> stockNames){
        BufferedReader reader;
        try{
            URL path = Portfolio.class.getResource(file);
            File f = new File(path.getFile());
            reader = new BufferedReader(new FileReader(f));
            String line = reader.readLine();
            while(line != null ){

                if(line.equals("")){
                    line = reader.readLine();
                }
                String[] brokenLine = line.split(" ");

                Transaction t = new Transaction(brokenLine[0], Integer.parseInt(brokenLine[1]),
                        brokenLine[2], Float.parseFloat(brokenLine[3]));

                if(!stockNames.contains(brokenLine[2])){
                    stocks.add(new Stock(brokenLine[2]));
                    stockNames.add(brokenLine[2]);
                }

                for (Stock s : stocks){
                    if(brokenLine[2].equals(s.getSym())){
                        if(brokenLine[0].equals("BUY")){
                            s.Buy(t);
                        } else if (brokenLine[0].equals("SELL")){
                            s.Sell(t);
                        } else {
                            System.out.println("ERROR: Please use either SELL or BUY for your action!");
                            System.exit(0);
                        }
                    }
                }

                line = reader.readLine();
            }
            reader.close();

        } catch(IOException e){
            e.printStackTrace();
        }

    }



    public static void main(String[] args) throws FileNotFoundException {

        Scanner s = new Scanner(System.in);
        System.out.println("Please enter the name of the file you'd like to parse:");
        String str = s.nextLine();
        System.out.println("One moment please while we process " + str);

        Portfolio p = new Portfolio();
        PrintStream o = new PrintStream(new File("transaction_record.txt"));
        System.setOut(o);
        p.readTransactions(str, p.stockNames);

    }
}
