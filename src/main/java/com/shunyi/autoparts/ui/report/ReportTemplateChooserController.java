package com.shunyi.autoparts.ui.report;

import com.shunyi.autoparts.ui.common.Constants;
import com.shunyi.autoparts.ui.common.Env;
import com.shunyi.autoparts.ui.common.GoogleJson;
import com.shunyi.autoparts.ui.common.HttpClient;
import com.shunyi.autoparts.ui.common.vo.Report;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @Description: 报表模板选择器
 * @Author: Shunyi
 * @CreateDate: 2020/5/24
 */
public class ReportTemplateChooserController {

    private Stage dialog;
    private String orderNo;
    @FXML
    private ComboBox<String> comboBoxTemplate;
    @FXML
    private Button btnStartPrinting;

    public void initialize(Stage dialog, String orderNo) {
        this.dialog = dialog;
        this.orderNo = orderNo;
        try {
            String[] templates = HttpClient.GET("/report/templates", String[].class);
            comboBoxTemplate.getItems().addAll(templates);
            comboBoxTemplate.getSelectionModel().select(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        comboBoxTemplate.setStyle("-fx-font-size: 14px;");
        btnStartPrinting.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
    }

    @FXML
    private void cancel() {
        dialog.close();
    }

    @FXML
    private void startPrinting() {
        Report report = new Report();
        report.setCreator(Env.getInstance().currentUser());
        report.setOrderNo(orderNo);
        report.setTemplateName(comboBoxTemplate.getValue());
        report.setReportFileType(Constants.PDF);
        String json = GoogleJson.GET().toJson(report);
        try {
            String reportIdStr = HttpClient.POST("/reports", json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        dialog.close();
    }

}
