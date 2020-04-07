package com.shunyi.autoparts.ui.common;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 * @Description: 表格行号单元格
 * @Author: Shunyi Chen
 * @CreateDate: 2020/4/6 20:12
 * @Version: 1.0
 */
public class RowNumberTableCell<S,T> implements Callback<TableColumn<S,T>, TableCell<S,T>> {

    @Override
    public TableCell<S, T> call(TableColumn<S, T> param) {
        TableCell cell=new TableCell() {
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                this.setText(null);
                this.setGraphic(null);
                if (!empty) {
                    int rowIndex = this.getIndex() + 1;
                    this.setText(String.valueOf(rowIndex));
                }
            }
        };
        return cell;
    }
}
