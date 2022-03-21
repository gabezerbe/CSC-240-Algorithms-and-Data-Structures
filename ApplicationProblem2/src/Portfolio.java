import java.io.*;
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
     *
     * @param file - file name from user input
     */
    public void readTransactions(String file){
        BufferedReader reader;
        try{
            InputStream input = Portfolio.class.getClassLoader().getResourceAsStream(file);
            reader = new BufferedReader(new InputStreamReader(input));
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
                            s.buy(t);
                            System.out.println("BUY " + t.amount + " " + t.stock + String.format(" %.2f", t.price));
                        } else if (brokenLine[0].equals("SELL")){
                            s.sell(t);

                            ArrayList<String> sells = s.getSellList();
                            for(String str : sells){
                                System.out.println(str);
                            }
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

        Portfolio p = new Portfolio();
        PrintStream o = new PrintStream(new File("transaction_record.txt"));
        System.setOut(o);
        p.readTransactions("transactions.txt");

    }
}
