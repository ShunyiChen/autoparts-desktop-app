package com.shunyi.autoparts.ui.dashboard;

import com.shunyi.autoparts.ui.MainApp;
import com.shunyi.autoparts.ui.main.BaseContainer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Dashboard extends VBox implements BaseContainer {

    private MainApp application;


    /**
     *
     * @param application
     */
    public Dashboard(MainApp application) {
        this.application = application;
        initComponents();
    }

    private void initComponents() {
        NumberAxis xAxis;
        NumberAxis yAxis;
        StackedAreaChart chart;
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
        this.getChildren().add(chart);

        HBox hLayout = new HBox();

        PieChart chart2;
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList(
                new PieChart.Data("Sun", 20),
                new PieChart.Data("IBM", 12),
                new PieChart.Data("HP", 25),
                new PieChart.Data("Dell", 22),
                new PieChart.Data("Apple", 30));
        chart2 = new PieChart(data);
        chart2.setClockwise(false);
        hLayout.getChildren().add(chart2);

        BarChart chart3;
        CategoryAxis xAxis3;
        NumberAxis yAxis3;
        final String imageBarChartCss =
                getClass().getResource("/css/ImageBarChart.css").toExternalForm();
        xAxis3 = new CategoryAxis();
        yAxis3 = new NumberAxis();
        chart3 = new BarChart(xAxis3, yAxis3);
        chart3.setLegendVisible(false);
        chart3.getStylesheets().add(imageBarChartCss);

        chart3.getData().add(
                new XYChart.Series<>("Sales Per Product",
                        FXCollections.observableArrayList(
                                new XYChart.Data<>("SUV", 120),
                                new XYChart.Data<>("Sedan", 50),
                                new XYChart.Data<>("Truck", 180),
                                new XYChart.Data<>("Van", 20)
                        )
                )
        );

        hLayout.getChildren().add(chart3);


        String[] years = {"2007", "2008", "2009"};
        CategoryAxis xAxis4 = new CategoryAxis();
        xAxis4.setCategories(FXCollections.<String>observableArrayList(years));
        NumberAxis yAxis4 = new NumberAxis("Units Sold", 0.0d, 3000.0d, 1000.0d);
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
        BarChart chart4 = new BarChart(xAxis4, yAxis4, barChartData, 25.0d);

        hLayout.getChildren().add(chart4);

        this.getChildren().add(hLayout);
    }

    @Override
    public void willOpen() {
    }

    @Override
    public void willClose() {

    }
}
