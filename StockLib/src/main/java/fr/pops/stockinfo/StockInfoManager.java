package fr.pops.stockinfo;

public class StockInfoManager {

    private static StockInfoManager instance = new StockInfoManager();

    private StockInfo stockInfo = new StockInfo("GME");

    /**
     * Standard ctor
     * Nothing to be done
     */
    private StockInfoManager(){
        // Nothing to be done
    }

    /**
     *
     * @return
     */
    public static StockInfoManager getInstance() {
        return instance;
    }

    public StockInfo getStockInfo() {
        return stockInfo;
    }
}
