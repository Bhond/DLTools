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
 * Name: CandleStickPlot.java
 *
 * Description: Class defining the candlestick chart
 *              to display stock data
 *
 * Author: Charles MERINO
 *
 * Date: 27/04/2021
 *
 ******************************************************************************/
package fr.pops.customnodes.plot.candlestickplot;

import fr.pops.cst.DblCst;
import fr.pops.cst.StrCst;
import fr.pops.utils.Utils;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Region;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CandlestickPlot extends XYChart<String, Number> {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    // Components
    private NumberAxis yAxis;
    private CategoryAxis xAxis;
    private CandleData currentBar;
    // Parameters
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    private double lineWidth = DblCst.CANDLE_LINE_WIDTH_DEFAULT;
    private double candleWidth = DblCst.CANDLE_BODY_WIDTH_DEFAULT;
    private final boolean xAxisAutoRanging = true;
    private final boolean yAxisAutoRanging = true;
    private final boolean isAnimated = true;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Standard ctor
     */
    public CandlestickPlot(){
        // Creation from scratch
        this(new CategoryAxis(), new NumberAxis());
    }

    /**
     * Ctor
     * @param xAxis The x axis
     * @param yAxis The y axis
     */
    public CandlestickPlot(CategoryAxis xAxis, NumberAxis yAxis){
        // Parent
        super(xAxis, yAxis);
        // Style
        this.getStylesheets().add(Utils.getResource(StrCst.PATH_CANDLESTICK_PLOT_CSS));
        // Fields
        this.xAxis = xAxis;
        this.xAxis.setAnimated(this.isAnimated);
        this.xAxis.setAutoRanging(this.xAxisAutoRanging);
        this.yAxis = yAxis;
        this.yAxis.setAnimated(this.isAnimated);
        this.yAxis.setAutoRanging(this.yAxisAutoRanging);
        this.yAxis.forceZeroInRangeProperty().setValue(Boolean.FALSE);
        // Misc
        this.setAnimated(this.isAnimated);
        // Data
        this.setData(FXCollections.observableArrayList(new Series<>()));
    }

    /*****************************************
     *
     * Bar data shandling
     *
     *****************************************/
    /**
     * Add a new bar on to the end of the chart
     * @param bar The bar to add
     */
    public void addCandle(CandleData bar){
        XYChart.Series<String, Number> series = new Series<>();
        this.currentBar = new CandleData(bar.getDateTime(), bar.getOpen(), bar.getHigh(), bar.getLow(), bar.getClose(), bar.getVolume());
        series.getData().add(new Data<>(sdf.format(bar.getDateTime().getTime()), this.currentBar.getOpen(), this.currentBar));
        this.getData().add(series);
    }

    /**
     * Update the last price of the most recent bar
     * @param price The Last price of the most recent bar.
     */
    public void updateCurrentCandle(double price) {
        if (this.currentBar != null) {
            this.currentBar.update(price);
            int nbSeries = this.getData().size();
            if (nbSeries > 0){
                Series<String, Number> currentSeries = this.getData().get(nbSeries - 1);
                int dataLength = currentSeries.getData().size();
                if (dataLength > 0){
                    currentSeries.getData().get(dataLength - 1).setYValue(this.currentBar.getOpen());
                    currentSeries.getData().get(dataLength - 1).setExtraValue(this.currentBar);
                }
            }
        }
    }


    /*****************************************
     *
     * Parent
     *
     *****************************************/
    /**
     * Called when a data item has been added to a series.
     * This is where implementations of XYChart can create/add new nodes
     * to getPlotChildren to represent this data item.
     * They also may animate that data add with a fade in or similar if animated = true
     * @param series The series the data item was added to
     * @param itemIndex The index of the new item within the series
     * @param item The new data item that was added
     */
    @Override
    protected void dataItemAdded(Series<String, Number> series, int itemIndex, Data<String, Number> item) {
        System.out.println("Data item added, not implemented");
    }

    /**
     * Called when a data item has been removed from data model but it is still visible on the chart
     * @param item The item that has been removed from the series
     * @param series The series the item was removed from
     */
    @Override
    protected void dataItemRemoved(Data<String, Number> item, Series<String, Number> series) {
        System.out.println("Data item removed, not implemented");
    }

    /**
     * Called when a data item has changed
     * @param data The data that changed
     */
    @Override
    protected void dataItemChanged(Data<String, Number> data) {
    }

    /**
     *
     * TODO: Check animation
     *
     * A series has been added to the charts data model
     * @param series The series to add
     * @param seriesIndex The index of the series to add
     */
    @Override
    protected void seriesAdded(Series<String, Number> series, int seriesIndex) {
        // Loop over all items already in the series
        for (int i = 0; i < series.getData().size(); i++){
            Data item = series.getData().get(i);
            Node candle = this.createCandle(item);
            // Animate new series creation
            if (this.shouldAnimate()){
                candle.setOpacity(0);
                this.getPlotChildren().add(candle);
                // Fade animation
                FadeTransition ft = new FadeTransition(Duration.seconds(50), candle);
                ft.setToValue(1);
                ft.play();
            } else {
                this.getPlotChildren().add(candle);
            }

        }
    }

    /**
     * A series has been removed from the data model but it is still visible on the chart
     * @param series The series to remove
     */
    @Override
    protected void seriesRemoved(Series<String, Number> series) {
        System.out.println("Series removed, not implemented");
    }

    /**
     * Called to update and layout the plot children.
     */
    @Override
    protected void layoutPlotChildren() {
        // If there is no data, return
        if (this.getData() == null) return;
        // Loop over the candles / series
        for (Series<String, Number> candleSeries : this.getData()) {
            // Iterator to loop over all data in the series
            Iterator<Data<String, Number>> iter = this.getDisplayedDataIterator(candleSeries);

            // Loop over the data per candle
            while(iter.hasNext()){
                // Retrieve data contained in the candle
                Data<String, Number> item = iter.next();
                // Retrieve the coordinates of the value in the candle
                double x = getXAxis().getDisplayPosition(getCurrentDisplayedXValue(item));
                double y = getYAxis().getDisplayPosition(getCurrentDisplayedYValue(item));
                // Retrieve the node (representation of the candle)
                Node itemNode = item.getNode();
                // Cast item's extraValue to barData
                CandleData bar = (CandleData) item.getExtraValue();
                // Check class and update candle's position
                if (itemNode instanceof Candle && item.getYValue() != null){
                    // Cast item node to candle to update it
                    Candle candle = (Candle) itemNode;
                    // Update candle
                    double closeOffset = this.getYAxis().getDisplayPosition(bar.getClose());
                    double highOffset = getYAxis().getDisplayPosition(bar.getHigh());
                    double lowOffset = getYAxis().getDisplayPosition(bar.getLow());
                    candle.update(closeOffset - y, highOffset - y, lowOffset - y, this.lineWidth, this.candleWidth);
                    // Move candle
                    candle.setLayoutX(x);
                    candle.setLayoutY(y);
                }
            }
        }
    }

    /**
     * This is called when the range has been invalidated and we need to update
     * it. If the axis are auto ranging then we compile a list of all data that
     * the given axis has to plot and call invalidateRange() on the axis passing
     * it that data.
     */
    @Override
    protected void updateAxisRange() {
        final Axis<String> xa = this.getXAxis();
        final Axis<Number> ya = this.getYAxis();
        List<String> xData = null;
        List<Number> yData = null;
        if (xa.isAutoRanging()) {
            xData = new ArrayList<>();
        }
        if (ya.isAutoRanging()) {
            yData = new ArrayList<>();
        }
        if (xData != null || yData != null) {
            for (Series<String, Number> series : this.getData()) {
                for (Data<String, Number> data : series.getData()) {
                    if (xData != null) {
                        xData.add(data.getXValue());
                    }
                    if (yData != null) {
                        CandleData extras = (CandleData) data.getExtraValue();
                        if (extras != null) {
                            yData.add(extras.getHigh());
                            yData.add(extras.getLow());
                        } else {
                            yData.add(data.getYValue());
                        }
                    }
                }
            }
            if (xData != null) {
                xa.invalidateRange(xData);
            }
            if (yData != null) {
                ya.invalidateRange(yData);
            }
        }
    }

    /*****************************************
     *
     * Create candle
     *
     *****************************************/
    /**
     * Create a new Candle node to represent a single data item
     * @param item The data item to create node for
     * @return New candle node to represent the given data item
     */
    private Node createCandle(Data item) {
        Node node = item.getNode();
        if (node == null){
            node = new Candle();
            item.setNode(node);
        }
        return node;
    }

    /*****************************************
     *
     * Candle class
     *
     *****************************************/
    private class Candle extends Group {

        // Children
        private final Line highLowLine;
        private final Region body ;
        // Test
        private boolean openAboveClose = true;

        /**
         * Ctor
         */
        private Candle() {
            // Style classes
            this.setAutoSizeChildren(false);
            // Children
            this.highLowLine = new Line();
            this.body = new Region();
            // Build hierarchy
            this.getChildren().addAll(this.highLowLine, this.body);
        }

        /**
         * Update the candle children
         * @param closeOffset The offset used to draw the closing price of the candle
         * @param highOffset The offset used to draw the highest price of the candle
         * @param lowOffset The offset used to draw the lowest price of the candle
         * @param lineWidth The line width
         * @param bodyWidth The candle width
         */
        public void update(double closeOffset, double highOffset, double lowOffset, double lineWidth, double bodyWidth) {
            // Is open price above close price ?
            this.openAboveClose = closeOffset > 0;

            // Update style classes
            this.updateStyleClasses();

            // High low bar
            this.highLowLine.setStartY(highOffset);
            this.highLowLine.setEndY(lowOffset);

            // Body
            if (this.openAboveClose){
                this.body.resizeRelocate(- bodyWidth / 2, 0, bodyWidth, closeOffset);
            } else {
                this.body.resizeRelocate(- bodyWidth / 2, closeOffset, bodyWidth, - closeOffset);
            }
        }

        /**
         * Update style classes
         */
        private void updateStyleClasses() {
            this.highLowLine.getStyleClass().setAll(StrCst.STYLE_CLASS_CANDLESTICK_LINE, this.openAboveClose ? StrCst.STYLE_CLASS_OPEN_ABOVE_CLOSE : StrCst.STYLE_CLASS_CLOSE_ABOVE_OPEN);
            this.body.getStyleClass().setAll(StrCst.STYLE_CLASS_CANDLESTICK_BODY,  this.openAboveClose ? StrCst.STYLE_CLASS_OPEN_ABOVE_CLOSE : StrCst.STYLE_CLASS_CLOSE_ABOVE_OPEN);
        }
    }
}