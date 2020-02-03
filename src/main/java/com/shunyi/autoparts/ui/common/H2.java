package com.shunyi.autoparts.ui.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.shunyi.autoparts.ui.model.RemoteConnection;
import org.h2.tools.DeleteDbFiles;

/** 远程连接H2存储 */
public class H2 {
    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_CONNECTION = "jdbc:h2:~/test";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";

    /**
     * 批处理
     *
     * @param connections 连接对象集合
     * @throws SQLException 异常
     */
    public static void batch(List<RemoteConnection> connections) throws SQLException {
        List<Long> updatedId = new ArrayList<>();
        List<RemoteConnection> exists = retrieveAll();
        for(int i = 0; i < connections.size(); i++) {
            connections.get(i).setaOrder(i);
            if(connections.get(i).getId() == 0) {
                create(connections.get(i));
            } else {
                update(connections.get(i));
            }
            updatedId.add(connections.get(i).getId());
        }
        exists.forEach(e -> {
            if(!updatedId.contains(e.getId())) {
                try {
                    delete(e.getId());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    /**
     * 插入新记录
     *
     * @param rc 连接对象
     * @throws SQLException 异常
     */
    private static void create(RemoteConnection rc) throws SQLException {
        try (Connection connection = getDBConnection()) {
            PreparedStatement createPreparedStatement;
            PreparedStatement insertPreparedStatement;
            String createQuery = "CREATE TABLE IF NOT EXISTS REMOTE_CONNECTIONS(ID INT AUTO_INCREMENT PRIMARY KEY,NAME VARCHAR(255),PROTOCOL VARCHAR(255),HOSTNAME VARCHAR(255),PORT VARCHAR(255),_DEFAULT BOOLEAN,AORDER INT)";
            String insertQuery = "INSERT INTO REMOTE_CONNECTIONS" + "(NAME,PROTOCOL,HOSTNAME,PORT,_DEFAULT,AORDER) VALUES" + "(?,?,?,?,?,?)";
            connection.setAutoCommit(false);
            createPreparedStatement = connection.prepareStatement(createQuery);
            createPreparedStatement.executeUpdate();
            createPreparedStatement.close();
            insertPreparedStatement = connection.prepareStatement(insertQuery);
            insertPreparedStatement.setString(1, rc.getName());
            insertPreparedStatement.setString(2, rc.getProtocol());
            insertPreparedStatement.setString(3, rc.getHostName());
            insertPreparedStatement.setString(4, rc.getPort());
            insertPreparedStatement.setBoolean(5, rc.is_default());
            insertPreparedStatement.setInt(6, rc.getaOrder());
            insertPreparedStatement.executeUpdate();
            insertPreparedStatement.close();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更改
     *
     * @param rc 连接对象
     * @throws SQLException 异常
     */
    private static void update(RemoteConnection rc) throws SQLException {
        try (Connection connection = getDBConnection()) {
            PreparedStatement updatePreparedStatement;
            String updateQuery = "UPDATE REMOTE_CONNECTIONS SET _DEFAULT=?, AORDER=? WHERE ID=?";
            connection.setAutoCommit(false);
            updatePreparedStatement = connection.prepareStatement(updateQuery);
            updatePreparedStatement.setBoolean(1, rc.is_default());
            updatePreparedStatement.setInt(2, rc.getaOrder());
            updatePreparedStatement.setLong(3, rc.getId());
            updatePreparedStatement.executeUpdate();
            updatePreparedStatement.close();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除连接对象
     *
     * @param id ID
     * @throws SQLException 异常
     */
    public static void delete(long id) throws SQLException {
        Connection connection = getDBConnection();
        PreparedStatement updatePreparedStatement;
        String updateQuery = "DELETE FROM REMOTE_CONNECTIONS WHERE ID=?";
        try {
            connection.setAutoCommit(false);
            updatePreparedStatement = connection.prepareStatement(updateQuery);
            updatePreparedStatement.setLong(1, id);
            updatePreparedStatement.executeUpdate();
            updatePreparedStatement.close();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }

    /**
     * 检索所有记录
     *
     * @return
     * @throws SQLException
     */
    public static List<RemoteConnection> retrieveAll() throws SQLException {
        List<RemoteConnection> list = new ArrayList<>();
        Connection connection = getDBConnection();
        PreparedStatement selectPreparedStatement = null;
        String selectQuery = "SELECT * FROM REMOTE_CONNECTIONS ORDER BY AORDER ASC";
        try {
            connection.setAutoCommit(false);
            selectPreparedStatement = connection.prepareStatement(selectQuery);
            ResultSet rs = selectPreparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String protocol = rs.getString("protocol");
                String hostName = rs.getString("hostName");
                String port = rs.getString("port");
                Boolean _default = rs.getBoolean("_default");
                int aOrder = rs.getInt("aOrder");
                RemoteConnection rc = new RemoteConnection(id, name, protocol, hostName, port, _default, aOrder);
                list.add(rc);
            }
            selectPreparedStatement.close();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return list;
    }

    /**
     * 删除数据库文件
     *
     * @throws SQLException
     */
    public static void DeleteDbFiles() throws SQLException {
        DeleteDbFiles.execute("~", "test", true);
    }

    /**
     * 建立数据库连接
     *
     * @return 数据库连接
     */
    private static Connection getDBConnection() {
        Connection dbConnection = null;
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER,
                    DB_PASSWORD);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }

}

