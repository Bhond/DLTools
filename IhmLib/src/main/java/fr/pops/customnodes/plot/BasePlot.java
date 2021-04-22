package fr.pops.customnodes.plot;

import fr.pops.cst.DblCst;
import fr.pops.cst.StrCst;
import fr.pops.utils.Utils;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;

public class BasePlot extends AnchorPane {

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    // Chart
    private NumberAxis xAxis;
    private NumberAxis yAxis;
    private LineChart<Number, Number> chart;

    // Data
    private XYChart.Series<Number, Number> series;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Ctor
     */
    public BasePlot() {
        // Initialisation
        this.onInit();
    }

    /*****************************************
     *
     * Initialization
     *
     *****************************************/
    /**
     * Initialize the view
     */
    protected void onInit() {
        // Configure content
        this.configureContentPane();
    }

    /**
     * Configure the content pane
     */
    protected void configureContentPane() {
        // Configure chart
        this.configureChart();
        this.getStyleClass().add("rootPane");
        this.getStylesheets().add(Utils.getResource(StrCst.PATH_BASE_PLOT_CSS));

        // Set alignment
        AnchorPane.setTopAnchor(this.chart, DblCst.SIZE_ANCHOR_ZERO);
        AnchorPane.setBottomAnchor(this.chart, DblCst.SIZE_ANCHOR_ZERO);
        AnchorPane.setLeftAnchor(this.chart, DblCst.SIZE_ANCHOR_ZERO);
        AnchorPane.setRightAnchor(this.chart, DblCst.SIZE_ANCHOR_ZERO);

        // Build hierarchy
        this.getChildren().add(this.chart);
    }

    private void configureChart(){
        // Axis
        this.xAxis = new NumberAxis();
        this.yAxis = new NumberAxis();
        this.chart = new LineChart<>(this.xAxis, this.yAxis);
        this.chart.legendVisibleProperty().setValue(false);

        // Series
        this.series = new XYChart.Series<>();

        // Build hierarchy
        this.chart.getData().addAll(this.series);
    }

    /*****************************************
     *
     * Getters
     *
     *****************************************/
    /**
     * @return The displayed series
     */
    public XYChart.Series<Number, Number> getSeries() {
        return this.series;
    }
    /*****************************************
     *
     * Setters
     *
     *****************************************/
}
