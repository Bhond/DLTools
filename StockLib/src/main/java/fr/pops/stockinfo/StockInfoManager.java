package fr.pops.stockinfo;

import java.util.HashMap;

public class StockInfoManager {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private static StockInfoManager instance = new StockInfoManager();
    private HashMap<String, StockInfo> infos = new HashMap<>();

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Nothing to be done
     */
    private StockInfoManager(){
        // Nothing to be done
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Manage new stock info
     * @param info The info to manage
     */
    public void addStockInfo(StockInfo info){
        this.infos.put(info.getSymbol(), info);
    }

    /**
     * @param symbol The symbol to find
     * @return True if the symbol is managed
     */
    public boolean containsInfo(String symbol){
        return this.infos.containsKey(symbol);
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * Get stock info corresponding to the input symbol
     * @param symbol The symbol to retrieve stock info from
     * @return The corresponding stock info
     */
    public StockInfo getStockInfo(String symbol) {
        return this.infos.get(symbol);
    }

    /**
     * Singleton
     * @return The instance
     */
    public static StockInfoManager getInstance() {
        return instance;
    }
}
