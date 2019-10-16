package com.shunyi.autoparts.ui.h2;

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
     *
     * @param list
     * @throws SQLException
     */
    public static void batch(List<RemoteConnection> list) throws SQLException {
        List<Long> updatedId = new ArrayList<>();
        List<RemoteConnection> exists = retrieveAll();
        for(int i = 0; i < list.size(); i++) {
            list.get(i).setaOrder(i);
            if(list.get(i).getId() == 0) {
                create(list.get(i));
            } else {
                update(list.get(i));
            }
            updatedId.add(list.get(i).getId());
        }
        exists.stream().forEach(e -> {
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
     * @param rc
     * @throws SQLException
     */
    public static void create(RemoteConnection rc) throws SQLException {
        Connection connection = getDBConnection();
        PreparedStatement createPreparedStatement = null;
        PreparedStatement insertPreparedStatement = null;

        String createQuery = "CREATE TABLE IF NOT EXISTS REMOTE_CONNECTIONS(ID INT AUTO_INCREMENT PRIMARY KEY,NAME VARCHAR(255),PROTOCOL VARCHAR(255),HOSTNAME VARCHAR(255),PORT VARCHAR(255),_DEFAULT BOOLEAN,AORDER INT)";
        String insertQuery = "INSERT INTO REMOTE_CONNECTIONS" + "(NAME,PROTOCOL,HOSTNAME,PORT,_DEFAULT,AORDER) VALUES" + "(?,?,?,?,?,?)";
        try {
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
        } finally {
            connection.close();
        }
    }

    /**
     * 更改
     *
     * @param rc
     * @throws SQLException
     */
    public static void update(RemoteConnection rc) throws SQLException {
        Connection connection = getDBConnection();
        PreparedStatement updatePreparedStatement = null;
        PreparedStatement updatePreparedStatement2 = null;
        String updateQuery = "UPDATE REMOTE_CONNECTIONS SET _DEFAULT=?, AORDER=? WHERE ID=?";
        try {
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
        } finally {
            connection.close();
        }
    }

    /**
     * 删除
     *
     * @param id
     * @throws SQLException
     */
    public static void delete(long id) throws SQLException {
        Connection connection = getDBConnection();
        PreparedStatement updatePreparedStatement = null;
        PreparedStatement selectPreparedStatement = null;

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
            System.out.println("H2 Database inserted through PreparedStatement");
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

//    /**
//     * 删除数据库文件
//     *
//     * @throws SQLException
//     */
//    public static void DeleteDbFiles() throws SQLException {
//        DeleteDbFiles.execute("~", "test", true);
////        RemoteConnection rc = new RemoteConnection(0,"默认服务(http://localhost:8080)", "http", "localhost", "8080", true, 0);
////        create(rc);
//    }

    /**
     * 建立数据库连接
     *
     * @return
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


    public static void main(String[] args) throws Exception {
        try {
            // delete the H2 database named 'test' in the user home directory
//            DeleteDbFiles.execute("~", "test", true);
//            insertWithStatement();
//            DeleteDbFiles.execute("~", "test", true);

            RemoteConnection rc = new RemoteConnection();
            rc.setName("hello(http://4545.0.75.1:8080)");
            rc.setProtocol("http");
            rc.setHostName("10.0.75.1");
            rc.setPort("4545");
            rc.set_default(false);
            rc.setaOrder(3);

//            insert(rc);

//            delete(9);
//            delete(10);
//            delete(11);
//            delete(12);


            List<RemoteConnection> lst = retrieveAll();
            lst.stream().forEach(e -> {
                System.out.println(e);
            });

//


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

