/*******************************************************************************
 *
 *                         PPPP     OOOO     PPPP    SSSS
 *                        PP  PP   OO  OO   PP  PP  SS
 *                        PP  PP  OO    OO  PP  PP  SS
 *                        PP  PP  OO    OO  PP  PP   SSSS
 *                        PPPP    OO    OO  PPPP        SS
 *                        PP       OO  OO   PP          SS
 *                        PP        OOOO    PP       SSSS
 *
 * Name: StockInfo.java
 *
 * Description: Class regrouping info about a stock
 *
 * Author: Charles MERINO
 *
 * Date: 11/03/2021
 *
 ******************************************************************************/
package fr.pops.stockinfo;

import org.json.JSONObject;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;
import yahoofinance.quotes.stock.StockDividend;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class StockInfo {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    // Stock
    private Stock stock;

    // String values
    private String symbol;
    private String name;
    private String currency;

    // Current info
    private long lastAccessedTime;
    private GregorianCalendar calendar;
    private BigDecimal price;
    private BigDecimal change;
    private BigDecimal peg;
    private BigDecimal bid;
    private BigDecimal ask;
    private StockDividend dividend;

    // Historic
    List<HistoricalQuote> historicalQuoteList;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standar ctor
     * Nothing to be done
     */
    private StockInfo(){
        // Nothing to be done
    }

    /**
     * Ctor
     * @param symbol The symbol defining the stock
     *               to retrieve from the Yahoo finance API
     */
    public StockInfo(String symbol){
        // Initialisation
        this.symbol = symbol;
        this.calendar = new GregorianCalendar();
        this.retrieveStock();
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Retrieve the stock associated to the symbol
     */
    private void retrieveStock(){
        try {
            this.stock = YahooFinance.get(this.symbol);
        } catch (IOException e){
            System.out.println("Can't find stock for symbol: " + this.symbol);
        }
    }

    /**
     * Update stock info
     */
    public void updateStockInfo(){
        this.retrieveStock();
        this.lastAccessedTime = System.currentTimeMillis();
        this.change = this.stock.getQuote().getChange();
        this.price = this.stock.getQuote().getPrice();
        this.bid = this.stock.getQuote().getBid();
        this.ask = this.stock.getQuote().getAsk();
        this.peg = this.stock.getStats().getPeg();
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return The time of the last update
     */
    public long getLastAccessedTime() {
        System.out.println("Last accessed time: " + lastAccessedTime);
        return this.lastAccessedTime;
    }

    /**
     * @return Current price
     */
    public double getPrice() {
        return this.price.doubleValue();
    }

    /**
     * Get historical data
     * @param from Starting calendar date
     * @param field Static value of calendar like Calendar.YEAR
     * @param amount The amount of field to retrieve
     * @param to Ending calendar date
     * @param interval The interval between two quotes
     * @return The historical quotes between during the specified time period
     */
    public List<HistoricalQuote> getHistoricalData(Calendar from, int field, int amount, Calendar to, Interval interval) {
        try {
            from.add(field, amount);
            Stock historicalStock = YahooFinance.get(this.symbol, from, to, interval);
            this.historicalQuoteList = historicalStock.getHistory();
        } catch (IOException e){
            System.out.println("Can't get historical data for: " + this.symbol);
        }
        return this.historicalQuoteList;
    }

    /*****************************************
     *
     * Cast
     *
     *****************************************/
    /**
     * Cast StockInfo to string
     * @return A string representation of the class StockInfo
     */
    @Override
    public String toString(){
        return this.stock != null ? this.stock.toString() : "";
    }

    /**
     * Print already retrieved historical data to json format
     */
    public String historicalToJSON(){
        // Create maps
        Map<String, Object> rootBrace = new LinkedHashMap<>() {
        };
        // Standard info
        rootBrace.put("symbol", this.symbol);
        rootBrace.put("size", this.historicalQuoteList.size());
        // Loop over all the quotes
        int id = 0;
        for (HistoricalQuote quote : this.historicalQuoteList){
            Map<String, Object> secondBrace = new LinkedHashMap<>();
            secondBrace.put("date_ms", quote.getDate().getTime().getTime());
            secondBrace.put("high", quote.getHigh());
            secondBrace.put("low", quote.getLow());
            secondBrace.put("open", quote.getOpen());
            secondBrace.put("close", quote.getClose());
            rootBrace.put("quoteId:" + id, secondBrace);
            id++;
        }

        JSONObject jsonObject = new JSONObject(rootBrace);
        return jsonObject.toString();
    }

    /**
     * Print new historical data to json format
     * @param from Starting calendar date
     * @param field Static value of calendar like Calendar.YEAR
     * @param amount The amount of field to retrieve
     * @param to Ending calendar date
     * @param interval The interval between two quotes
     */
    public String historicalToJSON(Calendar from, int field, int amount, Calendar to, Interval interval){
        this.getHistoricalData(from, field, amount, to, interval);
        return this.historicalToJSON();
    }
}
