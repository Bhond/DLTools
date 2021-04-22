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
        this.series.getData().add(new XYChart.Data<>(0, 12d));
        this.series.getData().add(new XYChart.Data<>(0.25, 7d));
        this.series.getData().add(new XYChart.Data<>(0.5, 3d));
        this.series.getData().add(new XYChart.Data<>(0.75, 9d));
        this.series.getData().add(new XYChart.Data<>(1, 18));


        XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
        series2.getData().add(new XYChart.Data<>(0, 8d));
        series2.getData().add(new XYChart.Data<>(0.5, 17d));
        series2.getData().add(new XYChart.Data<>(0.15, 13d));
        series2.getData().add(new XYChart.Data<>(0.65, 5d));
        series2.getData().add(new XYChart.Data<>(1, 3d));

        XYChart.Series<Number, Number> series3 = new XYChart.Series<>();
        series3.getData().add(new XYChart.Data<>(0, 1d));
        series3.getData().add(new XYChart.Data<>(0.5, 8d));
        series3.getData().add(new XYChart.Data<>(0.15, 7d));
        series3.getData().add(new XYChart.Data<>(0.65, 2d));
        series3.getData().add(new XYChart.Data<>(1, 12d));

        // Build hierarchy
        this.chart.getData().addAll(this.series, series2, series3);
    }

    /*****************************************
     *
     * Setters
     *
     *****************************************/

}
