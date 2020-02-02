package com.shunyi.autoparts.ui.system;

import com.shunyi.autoparts.ui.common.NumberValidationUtils;
import com.shunyi.autoparts.ui.model.VFS;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

public class EditVFSController {

    private Stage dialog;
    private Callback<VFS, String> callback;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtPort;
    @FXML
    private TextField txtUserName;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtHost;
    @FXML
    private TextField txtHome;
    @FXML
    private ComboBox<String> comboxProtocol;
    @FXML
    private CheckBox checkboxCanRead;
    @FXML
    private CheckBox checkboxCanWrite;
    @FXML
    private Button btnOK;


    @FXML
    private void cancel() {
        dialog.close();
    }

    @FXML
    private void ok() {
        if(validate()) {
            VFS vfs = new VFS(txtName.getText(), comboxProtocol.getValue(), txtHost.getText(), Integer.parseInt(txtPort.getText()), txtHome.getText(), txtUserName.getText(), txtPassword.getText(), checkboxCanRead.isSelected(), checkboxCanWrite.isSelected(), 0L);
            callback.call(vfs);
            dialog.close();
        }
    }

    @FXML
    private void testConnection() {
        if(validate()) {
            VFS vfs = new VFS(txtName.getText(), comboxProtocol.getValue(), txtHost.getText(), Integer.parseInt(txtPort.getText()), txtHome.getText(), txtUserName.getText(), txtPassword.getText(), checkboxCanRead.isSelected(), checkboxCanWrite.isSelected(), 0L);

        }
    }

    private boolean validate() {
        if(txtName.getText().equals("")
        || comboxProtocol.getValue().equals("")
        || txtHost.getText().equals("")
        || txtPort.getText().equals("")
        || txtHome.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("请输入必填项");
            alert.show();
            return false;
        }
        else if(!NumberValidationUtils.isPositiveInteger(txtPort.getText())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.CLOSE);
            alert.setHeaderText("请在端口号输入数字(非0)");
            alert.show();
            return false;
        }
        return true;
    }

    public void prepare(Stage dialog, Callback<VFS, String> callback, VFS selectedVFS) {
        this.dialog = dialog;
        this.callback = callback;
        comboxProtocol.getItems().addAll("BZIP2","File","FTP","FTPS","GZIP","HDFS","HTTP","HTTPS","Jar","RAM","RES","SFTP","Tar","Temp","WebDAV","Zip","CIFS","mime");
        comboxProtocol.setValue("File");
        btnOK.setStyle(String.format("-fx-base: %s;", "rgb(63,81,181)"));
        if(selectedVFS != null) {
            txtName.setText(selectedVFS.getName());
            txtHost.setText(selectedVFS.getHost());
            txtHome.setText(selectedVFS.getHome());
            txtUserName.setText(selectedVFS.getUserName());
            txtPassword.setText(selectedVFS.getPassword());
            comboxProtocol.setValue(selectedVFS.getProtocol());
            txtPort.setText(selectedVFS.getPort()+"");
            checkboxCanRead.setSelected(selectedVFS.getCanRead());
            checkboxCanWrite.setSelected(selectedVFS.getCanWrite());
        }
    }
}
