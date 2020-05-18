package com.shunyi.autoparts.ui.sales;

import com.shunyi.autoparts.ui.MainApp;
import com.shunyi.autoparts.ui.common.Constants;
import com.shunyi.autoparts.ui.main.TabContent;
import javafx.beans.binding.DoubleBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.FlowPane;

/**
 * @Description: 销售视图
 * @Author: Shunyi Chen
 * @CreateDate: 2020/4/3
 * @Version: 1.0
 */
public class SalesView extends TabContent {
    private MainApp application;

    public SalesView(MainApp application) {
        this.application = application;
        initComponents();
    }

    private void initComponents() {
        FlowPane flowPane = new FlowPane();
        flowPane.getChildren().addAll(top31(), top3(), top10(), img(), stackedBarChart(), categoryLineChart());

        //主面板右侧宽度
        DoubleBinding wBinding = application.getStage().widthProperty().subtract(application.getMainFrame().getNavigation().widthProperty().add(35));
        flowPane.prefWidthProperty().bind(wBinding);
        DoubleBinding hBinding = application.getStage().heightProperty().subtract(120);
        flowPane.prefHeightProperty().bind(hBinding);
        this.setLeft(flowPane);
    }

    private StackedAreaChart top31() {
        StackedAreaChart chart;
        NumberAxis xAxis;
        NumberAxis yAxis;

        xAxis = new NumberAxis("X Values", 1.0d, 9.0d, 2.0d);
        yAxis = new NumberAxis("Y Values", 0.0d, 30.0d, 2.0d);

        ObservableList<StackedAreaChart.Series> areaChartData =
                FXCollections.observableArrayList(
                        new StackedAreaChart.Series("Series 1",
                                FXCollections.observableArrayList(
                                        new StackedAreaChart.Data(0,4),
                                        new StackedAreaChart.Data(2,5),
                                        new StackedAreaChart.Data(4,4),
                                        new StackedAreaChart.Data(6,2),
                                        new StackedAreaChart.Data(8,6),
                                        new StackedAreaChart.Data(10,8)
                                )),
                        new StackedAreaChart.Series("Series 2",
                                FXCollections.observableArrayList(
                                        new StackedAreaChart.Data(0,8),
                                        new StackedAreaChart.Data(2,2),
                                        new StackedAreaChart.Data(4,9),
                                        new StackedAreaChart.Data(6,7),
                                        new StackedAreaChart.Data(8,5),
                                        new StackedAreaChart.Data(10,7)
                                )),
                        new StackedAreaChart.Series("Series 3",
                                FXCollections.observableArrayList(
                                        new StackedAreaChart.Data(0,2),
                                        new StackedAreaChart.Data(2,5),
                                        new StackedAreaChart.Data(4,8),
                                        new StackedAreaChart.Data(6,6),
                                        new StackedAreaChart.Data(8,9),
                                        new StackedAreaChart.Data(10,7)
                                ))
                );
        chart = new StackedAreaChart(xAxis, yAxis, areaChartData);
        chart.setTitle("销售金额前三年排行");
        return chart;
    }

    private BarChart top3() {
        BarChart chart;
        CategoryAxis xAxis;
        NumberAxis yAxis;
        String[] years = {"2007", "2008", "2009"};
        xAxis = new CategoryAxis();
        xAxis.setCategories(FXCollections.<String>observableArrayList(years));
        yAxis = new NumberAxis("Units Sold", 0.0d, 3000.0d, 1000.0d);
        ObservableList<BarChart.Series> barChartData =
                FXCollections.observableArrayList(
                        new BarChart.Series("Apples",
                                FXCollections.observableArrayList(
                                        new BarChart.Data(years[0], 567d),
                                        new BarChart.Data(years[1], 1292d),
                                        new BarChart.Data(years[2], 1292d))),
                        new BarChart.Series("Lemons",
                                FXCollections.observableArrayList(
                                        new BarChart.Data(years[0], 956),
                                        new BarChart.Data(years[1], 1665),
                                        new BarChart.Data(years[2], 2559))),
                        new BarChart.Series("Oranges",
                                FXCollections.observableArrayList(
                                        new BarChart.Data(years[0], 1154),
                                        new BarChart.Data(years[1], 1927),
                                        new BarChart.Data(years[2], 2774)))
                );
        chart = new BarChart(xAxis, yAxis, barChartData, 25.0d);
        chart.setTitle("前三年销售额排行");
        return chart;
    }

    private PieChart top10() {
        ObservableList<PieChart.Data> list = FXCollections.observableArrayList(
                new PieChart.Data("Sun", 20),
                new PieChart.Data("IBM", 12),
                new PieChart.Data("HP", 25),
                new PieChart.Data("Dell", 22),
                new PieChart.Data("Apple", 30));
        PieChart chart = new PieChart(list);
        chart.setClockwise(false);
        chart.setTitle("销售前10配件");
        return chart;
    }

    private BarChart img() {
        BarChart chart;
        CategoryAxis xAxis;
        NumberAxis yAxis;
        final String imageBarChartCss =
                getClass().getResource("/css/ImageBarChart.css").toExternalForm();
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis();
        chart = new BarChart(xAxis, yAxis);
        chart.setLegendVisible(false);
        chart.getStylesheets().add(imageBarChartCss);

        chart.getData().add(
                new XYChart.Series<>("销售额 Per Product",
                        FXCollections.observableArrayList(
                                new XYChart.Data<>("SUV", 120),
                                new XYChart.Data<>("轿车", 50),
                                new XYChart.Data<>("皮卡", 180),
                                new XYChart.Data<>("厢式货车", 20)
                        )
                )
        );
        chart.setTitle("各车型销售额排行");
        return chart;
    }

    private StackedBarChart stackedBarChart() {
        StackedBarChart chart;
        CategoryAxis xAxis;
        NumberAxis yAxis;
        final String[] years = {"2007", "2008", "2009"};
        xAxis = new CategoryAxis(FXCollections.observableArrayList(years));
        yAxis = new NumberAxis("Units Sold", 0.0d, 10000.0d, 1000.0d);

        final ObservableList<StackedBarChart.Series> barChartData =
                FXCollections.observableArrayList(
                        new StackedBarChart.Series("Region 1",
                                FXCollections.observableArrayList(
                                        new StackedBarChart.Data(years[0], 567d),
                                        new StackedBarChart.Data(years[1], 1292d),
                                        new StackedBarChart.Data(years[2], 1292d)
                                )
                        ),
                        new StackedBarChart.Series("Region 2",
                                FXCollections.observableArrayList(
                                        new StackedBarChart.Data(years[0], 956),
                                        new StackedBarChart.Data(years[1], 1665),
                                        new StackedBarChart.Data(years[2], 2559)
                                )
                        ),
                        new StackedBarChart.Series("Region 3",
                                FXCollections.observableArrayList(
                                        new StackedBarChart.Data(years[0], 1154),
                                        new StackedBarChart.Data(years[1], 1927),
                                        new StackedBarChart.Data(years[2], 2774)
                                )
                        )
                );

        chart = new StackedBarChart(xAxis, yAxis, barChartData, 25.0d);
        return chart;
    }

    private LineChart categoryLineChart() {
        String[] CATEGORIES = { "Alpha", "Beta", "RC1", "RC2",
                "1.0", "1.1" };
        LineChart<String, Number> chart;
        CategoryAxis xAxis;
        NumberAxis yAxis;
        xAxis = new CategoryAxis();
        yAxis = new NumberAxis();
        chart = new LineChart<>(xAxis, yAxis);
        // setup chart
        chart.setTitle("LineChart with Category Axis");
        xAxis.setLabel("X Axis");
        yAxis.setLabel("Y Axis");
        // add starting data
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Data Series 1");
        series.getData().add(new XYChart.Data<String, Number>(CATEGORIES[0], 50d));
        series.getData().add(new XYChart.Data<String, Number>(CATEGORIES[1], 80d));
        series.getData().add(new XYChart.Data<String, Number>(CATEGORIES[2], 90d));
        series.getData().add(new XYChart.Data<String, Number>(CATEGORIES[3], 30d));
        series.getData().add(new XYChart.Data<String, Number>(CATEGORIES[4], 122d));
        series.getData().add(new XYChart.Data<String, Number>(CATEGORIES[5], 10d));
        chart.getData().add(series);
        return chart;
    }

    @Override
    protected void reload() {
    }

    @Override
    protected void dispose() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"", ButtonType.YES, ButtonType.NO);
        alert.setTitle("是否保持更改");
        alert.setHeaderText("是否保持更改?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {

        } else {

        }
    }

    @Override
    protected String getTitle() {
        return Constants.SALES_VIEW;
    }
}
