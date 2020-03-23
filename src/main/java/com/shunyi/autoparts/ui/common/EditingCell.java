package com.shunyi.autoparts.ui.common;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;

/**
 * Editing cell for TableView
 *
 * @param <S> item type
 * @param <T> vo type
 */
public class EditingCell<S, T> extends TableCell<S, T> {

    private TextField textField;

    public EditingCell() {
        super();
        this.setAlignment(Pos.CENTER);
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createTextField();
            setText(null);
            setGraphic(textField);
            textField.selectAll();
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();

        setText((String) getItem());
        setGraphic(null);
    }

    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                setText(null);
                setGraphic(textField);
            } else {
                setText(getString());
                setGraphic(null);
            }
        }
    }

    @Override
    public void commitEdit(T item) {
        // This block is necessary to support commit on losing focus, because
        // the baked-in mechanism sets our editing state to false before we can
        // intercept the loss of focus. The default commitEdit(...) method
        // simply bails if we are not editing...
        if (!isEditing() && !item.equals(getItem())) {
            TableView<S> table = getTableView();
            if (table != null) {
                TableColumn<S, T> column = getTableColumn();
                TableColumn.CellEditEvent<S, T> event = new TableColumn.CellEditEvent<>(
                        table, new TablePosition<S, T>(table, getIndex(), column),
                        TableColumn.editCommitEvent(), item
                );
                Event.fireEvent(column, event);

                updateItem(item, false);
            }
        }
        super.commitEdit(item);
    }

    private void createTextField() {
        textField = new TextField(getString());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue) {
                    commitEdit((T) textField.getText());
                }
            }
        });
        textField.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                commitEdit((T) textField.getText());
            } else if (e.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            }
        });
        Platform.runLater(() -> textField.requestFocus());
    }

    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }
}