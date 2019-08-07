package com.shunyi.spareparts.ui.purchase;

import com.shunyi.spareparts.ui.LoginController;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import java.io.IOException;

public class PurchaseOrderForm extends BorderPane {

    public PurchaseOrderForm() {
        initComponents();
    }

    private void initComponents() {
        ToolBar toolBar = new ToolBar();
        Button btnNew = new Button("新建");
        Button btnSave = new Button("保存");
        Button btnCancel = new Button("作废");
        Button btnSubmit = new Button("结算");

        btnNew.setFont(Font.font(16));
        btnSave.setFont(Font.font(16));
        btnCancel.setFont(Font.font(16));
        btnSubmit.setFont(Font.font(16));

        toolBar.getItems().addAll(btnNew, new Separator(),btnSave, btnCancel, btnSubmit, new Separator());
        this.setTop(toolBar);
        this.setCenter(form());
    }

    private BorderPane form() {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/fxml/purchase_input_form.fxml"
                )
        );
        GridPane root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        root.setPadding(new Insets(10));
        BorderPane mainForm = new BorderPane();
        mainForm.setTop(root);
        mainForm.setCenter(new Button("1221"));

        return mainForm;
    }
}
