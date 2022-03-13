import java.io.*;
import java.net.URL;
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
     * Stock class - Stock class that encapsulates our 'Stock' data including the symbol, and its transactions
     */
    private class Stock{
        private String symbol;
        private Queue<Transaction> transactions = new LinkedList<>();

        /**
         *
         * @param symbol - The '$STOCK' symbol that is stored for being checked later to make sure we don't have duplicate stocks being stored
         *               in our storage array
         */
        public Stock(String symbol) {
            this.symbol = symbol;

        }

        /**
         * Getter method for retrieving a stock's symbol
         * @return
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

            System.out.println("SELL " + t.amount + " " + t.stock + String.format(" %.2f", t.price));
            while(t.amount != 0){
                Transaction r = transactions.remove();
                if(r.amount <= t.amount){
                    t.amount -= r.amount;
                    purchaseAmount += r.amount * r.price;
                    System.out.println("\tSELL " + r.amount + " " + t.stock + String.format(" %.2f", r.price));
                } else {
                    r.amount -= t.amount;
                    purchaseAmount += t.amount * r.price;
                    System.out.println("\tSELL " + t.amount + " " + t.stock + String.format(" %.2f", r.price));
                    t.amount = 0;
                }
                sellAmount += r.amount * t.price;

            }

            gains = sellAmount - purchaseAmount;

            if(gains >= 0d){
                System.out.printf("GAINS: %.2f %n", gains);
            } else {
                System.out.printf("LOSSES: %.2f %n", gains);
            }

        }

        /**
         * Parses our 'BUY' transaction objects
         * @param t - transaction object to be parsed
         */

        public void buy(Transaction t){
            transactions.add(t);

            System.out.println("BUY " + t.amount + " " + t.stock + String.format(" %.2f", t.price));
        }

    }

    /**
     * Transaction class for storing our transaction strings as an object
     */

    private class Transaction{
        private String action;
        private int amount;
        private String stock;
        private double price;

        public Transaction(String action, int amount, String stock, double price){
            this.action = action;
            this.amount = amount;
            this.stock = stock;
            this.price = price;
        }

    }

    /**
     *
     * @param file - file name from user input
     */
    public void readTransactions(String file){
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

                if(!this.stockNames.contains(brokenLine[2])){
                    this.stocks.add(new Stock(brokenLine[2]));
                    this.stockNames.add(brokenLine[2]);
                }

                for (Stock s : this.stocks){
                    if(brokenLine[2].equals(s.getSym())){
                        if(brokenLine[0].equals("BUY")){
                            s.buy(t);
                        } else if (brokenLine[0].equals("SELL")){
                            s.sell(t);
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
        p.readTransactions(str);

    }
}
