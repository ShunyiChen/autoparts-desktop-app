package com.shunyi.autoparts.ui.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/** 远程连接DTO */
public class RemoteConnection {
    /** ID */
    private SimpleLongProperty id = new SimpleLongProperty();
    /** 连接名称 */
    private SimpleStringProperty name = new SimpleStringProperty();
    /** 协议 */
    private SimpleStringProperty protocol = new SimpleStringProperty();
    /** 主机名 */
    private SimpleStringProperty hostName = new SimpleStringProperty();
    /** 端口  */
    private SimpleStringProperty port = new SimpleStringProperty();
    /** 是否默认的 */
    private SimpleBooleanProperty _default = new SimpleBooleanProperty();
    /** 顺序 */
    private SimpleIntegerProperty aOrder = new SimpleIntegerProperty();

    public RemoteConnection() {}

    public RemoteConnection(long id, String name, String protocol, String hostName, String port, boolean _default, int aOrder) {
        this.id.set(id);
        this.name.set(name);
        this.protocol.set(protocol);
        this.hostName.set(hostName);
        this.port.set(port);
        this._default.set(_default);
        this.aOrder.set(aOrder);
    }

    public long getId() {
        return id.get();
    }

    public SimpleLongProperty idProperty() {
        return id;
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getProtocol() {
        return protocol.get();
    }

    public SimpleStringProperty protocolProperty() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol.set(protocol);
    }

    public String getHostName() {
        return hostName.get();
    }

    public SimpleStringProperty hostNameProperty() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName.set(hostName);
    }

    public String getPort() {
        return port.get();
    }

    public SimpleStringProperty portProperty() {
        return port;
    }

    public void setPort(String port) {
        this.port.set(port);
    }

    public boolean is_default() {
        return _default.get();
    }

    public SimpleBooleanProperty _defaultProperty() {
        return _default;
    }

    public void set_default(boolean _default) {
        this._default.set(_default);
    }

    public int getaOrder() {
        return aOrder.get();
    }

    public SimpleIntegerProperty aOrderProperty() {
        return aOrder;
    }

    public void setaOrder(int aOrder) {
        this.aOrder.set(aOrder);
    }

    @Override
    public String toString() {
//        return "RemoteConnection{" +
//                "id=" + id +
//                ", name=" + name +
//                ", protocol=" + protocol +
//                ", hostName=" + hostName +
//                ", port=" + port +
//                ", _default=" + _default +
//                ", aOrder=" + aOrder +
//                '}';
        return name.get();
    }
}
