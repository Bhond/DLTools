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
 * Name: QuoteData.java
 *
 * Description: Class defining the node containing the quote data displayed
 *              by the listview on the StockInfoView
 *
 * Author: Charles MERINO
 *
 * Date: 28/04/2021
 *
 ******************************************************************************/
package fr.pops.views.stock;

import fr.pops.cst.StrCst;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class QuoteInfo extends HBox implements Cloneable {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    // Children
    private Label symbolLabel;
    private HBox centralBox;
    private HBox arrow;
    private Label price;

    // State
    private String symbol = "";
    private double lastPrice = 0.0d;
    private long lastAccessedTime = 0L;
    private boolean isPlotted = false;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     * Body is temporary, for tests
     */
    public QuoteInfo(){
        // Global style class
        this.getStyleClass().add(StrCst.STYLE_CLASS_QUOTE_DATA);

        // Symbol
        this.symbol = "GME";
        this.symbolLabel = new Label("GME");
        this.symbolLabel.getStyleClass().add(StrCst.STYLE_CLASS_QUOTE_DATA_SYMBOL);
        HBox.setHgrow(this.symbolLabel, Priority.ALWAYS);

        // Central box
        this.centralBox = new HBox();
        HBox.setHgrow(this.centralBox, Priority.ALWAYS);

        // Arrow
        this.arrow = new HBox();
        this.arrow.setPrefWidth(10);
        HBox.setHgrow(this.arrow, Priority.NEVER);


        // Price
        this.lastPrice = 0.0d;
        this.price = new Label("8163");
        this.updateStyle(true);
        HBox.setHgrow(this.price, Priority.ALWAYS);

        // Build hierarchy
        this.getChildren().addAll(this.symbolLabel, this.centralBox, this.arrow, this.price);
    }

    /**
     * Ctor
     * @param symbol The stock's symbol
     * @param price The current price of the quote
     */
    public QuoteInfo(String symbol, double price, long lastAccessedTime) {
        // Fields
        this.symbol = symbol;
        this.lastPrice = price;
        this.lastAccessedTime = lastAccessedTime;

        // Global style class
        this.getStyleClass().add(StrCst.STYLE_CLASS_QUOTE_DATA);

        // Symbol
        this.symbolLabel = new Label(symbol);
        this.symbolLabel.getStyleClass().add(StrCst.STYLE_CLASS_QUOTE_DATA_SYMBOL);
        HBox.setHgrow(this.symbolLabel, Priority.ALWAYS);

        // Central box
        this.centralBox = new HBox();
        HBox.setHgrow(this.centralBox, Priority.ALWAYS);

        // Arrow
        this.arrow = new HBox();
        this.arrow.setPrefWidth(10);
        HBox.setHgrow(this.arrow, Priority.NEVER);

        // Price
        this.price = new Label(String.valueOf(price));
        this.updateStyle(true);
        HBox.setHgrow(this.price, Priority.ALWAYS);

        // Build hierarchy
        this.getChildren().addAll(this.symbolLabel, this.centralBox, this.arrow, this.price);
    }

    /*****************************************
     *
     * Methods
     *
     *****************************************/
    /**
     * Update price style class depending on the current price
     * of the quote
     * @param openAboveClose State to define which style class
     *                       to use
     */
    private void updateStyle(boolean openAboveClose){
        this.arrow.getStyleClass().setAll(StrCst.STYLE_CLASS_QUOTE_DATA_ARROW, openAboveClose ? StrCst.STYLE_CLASS_OPEN_ABOVE_CLOSE : StrCst.STYLE_CLASS_CLOSE_ABOVE_OPEN);
        this.price.getStyleClass().setAll(StrCst.STYLE_CLASS_QUOTE_DATA_PRICE, openAboveClose ? StrCst.STYLE_CLASS_OPEN_ABOVE_CLOSE : StrCst.STYLE_CLASS_CLOSE_ABOVE_OPEN);
    }

    /*****************************************
     *
     * Getter
     *
     *****************************************/
    /**
     * @return True if the info is displayed on a candlestick chart
     */
    public boolean isPlotted() {
        return this.isPlotted;
    }

    /**
     * @return The price displayed
     */
    public double getPrice() {
        return this.lastPrice;
    }

    /**
     * @return The time the current price was retrieve
     */
    public long getLastAccessedTime() {
        return lastAccessedTime;
    }
    /*****************************************
     *
     * Setter
     *
     *****************************************/
    /**
     * @return The symbol of the quote data displayed
     */
    public String getSymbol() {
        return this.symbol;
    }

    /**
     * Display the input price
     * @param price The price to display
     */
    public void setPrice(double price) {
        this.price.setText(String.format("%6.2f", price));
        if (this.lastPrice != price) this.updateStyle(this.lastPrice < price);
        this.lastPrice = price;
    }

    /**
     * Set it to true when the data is displayed on a candlestick
     * chart
     * @param isPlotted True if it is plotted
     */
    public void setPlotted(boolean isPlotted){
        this.isPlotted = isPlotted;
    }

    /*****************************************
     *
     * Clone
     *
     *****************************************/
    @Override
    protected QuoteInfo clone() {
        QuoteInfo clone = null;
        try {
            clone = (QuoteInfo) super.clone();
        } catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        return clone;
    }
}
