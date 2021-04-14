package fr.pops.views;

import fr.pops.cst.StrCst;
import fr.pops.utils.Utils;
import fr.pops.viewmodels.ServerInfoModel;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class PlotView extends BaseView<PlotView, ServerInfoModel> { // TODO: change model

    /*****************************************
     *
     * Attributes
     *
     *****************************************/
    private NumberAxis xAxis;
    private NumberAxis yAxis;
    private LineChart<Number, Number> chart;

    private XYChart.Series<?, ?> series;

    /*****************************************
     *
     * Ctor
     *
     *****************************************/
    /**
     * Ctor
     * @param stage The stage to draw the view
     */
    public PlotView(Stage stage, double height, double width) {
        // Parent
        super(stage, height, width);

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
    @Override
    protected void onInit() {
        // Root style sheet
        this.root.getStylesheets().add(Utils.getResource(StrCst.PATH_PLOT_VIEW_CSS));

        // Configure content
        this.configureContentPane();
    }

    /**
     * Configure the content pane
     */
    @Override
    protected void configureContentPane() {
        // Build main pane
//        this.mainPane = new AnchorPane();
//        VBox.setVgrow(this.mainPane, Priority.ALWAYS);
//        HBox.setHgrow(this.mainPane, Priority.ALWAYS);

        // Configure chart
        this.configureChart();

        // Build hierarchy
//        this.mainPane.getChildren().add()
        this.rootLayout.getChildren().add(this.chart);
    }

    private void configureChart(){
        this.xAxis = new NumberAxis();
        this.yAxis = new NumberAxis();
        this.chart = new LineChart<>(this.xAxis, this.yAxis);
        this.chart.legendVisibleProperty().setValue(false);

        this.series = new XYChart.Series<Number, Number>();
        this.series.getData().add(new XYChart.Data(0, 12d));
        this.series.getData().add(new XYChart.Data(0.25, 7d));
        this.series.getData().add(new XYChart.Data(0.5, 3d));
        this.series.getData().add(new XYChart.Data(0.75, 9d));
        this.series.getData().add(new XYChart.Data(1, 18));
        this.chart.getData().addAll((XYChart.Series<Number, Number>) this.series);
    }

    /*****************************************
     *
     * Initialization
     *
     *****************************************/
}
