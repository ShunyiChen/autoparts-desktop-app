package com.shunyi.autoparts.ui.model;

import javafx.beans.property.*;

public class GatewayAddr {
    private StringProperty name;
    private StringProperty protocol;
    private StringProperty host;
    private IntegerProperty port;
    private BooleanProperty default2;

    public GatewayAddr(String name, String protocol, String host, int port, boolean default2) {
        this.name = new SimpleStringProperty(name);
        this.protocol = new SimpleStringProperty(protocol);
        this.host = new SimpleStringProperty(host);
        this.port = new SimpleIntegerProperty(port);
        this.default2 = new SimpleBooleanProperty(default2);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getProtocol() {
        return protocol.get();
    }

    public StringProperty protocolProperty() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol.set(protocol);
    }

    public String getHost() {
        return host.get();
    }

    public StringProperty hostProperty() {
        return host;
    }

    public void setHost(String host) {
        this.host.set(host);
    }

    public int getPort() {
        return port.get();
    }

    public IntegerProperty portProperty() {
        return port;
    }

    public void setPort(int port) {
        this.port.set(port);
    }

    public boolean isDefault2() {
        return default2.get();
    }

    public BooleanProperty default2Property() {
        return default2;
    }

    public void setDefault2(boolean default2) {
        this.default2.set(default2);
    }

    @Override
    public String toString() {
        return "GatewayAddr{" +
                "name=" + name +
                ", protocol=" + protocol +
                ", host=" + host +
                ", port=" + port +
                ", default2=" + default2 +
                '}';
    }
}
