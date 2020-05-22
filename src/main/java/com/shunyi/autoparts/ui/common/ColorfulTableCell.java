package com.shunyi.autoparts.ui.common;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.paint.Color;
import javafx.util.Callback;

/**
 * @Description: 设置单元格字体颜色
 * @Author: Shunyi
 * @CreateDate: 2020/5/21
 */
public class ColorfulTableCell<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

    public ColorfulTableCell() {
        super();
    }

    @Override
    public TableCell<S, T> call(TableColumn<S, T> param) {
        TableCell cell = new EditingCell() {
            @Override
            public void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                if(!empty && item != null) {
                    if (Integer.parseInt(item.toString()) > 5) {
                        this.setText(item.toString());
                        this.setTextFill(Color.RED);
                    } else {
                        this.setText(item.toString());
                        this.setTextFill(Color.BLACK);
                    }
                }
            }
        };
        return cell;
    }
}
