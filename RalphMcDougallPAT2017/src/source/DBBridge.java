/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package source;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ralph McDougall 19/10/2017
 */
public class DBBridge {

    private static Connection connection;
    private static PreparedStatement statement;

    public static void setupDBBridge() throws SQLException {
        GameLogger.logInfo("Starting to load DBBridge");
        connection = DriverManager.getConnection("jdbc:ucanaccess://src/resources/ResourceInfo.accdb");
        GameLogger.logInfo("Connection successful");

        GameLogger.logInfo("DBBridge loading completed");
    }

    public static ResultSet query(String stmt) throws SQLException {
        GameLogger.logInfo("Performing database query: " + stmt);
        statement = connection.prepareStatement(stmt);
        return statement.executeQuery();
    }

    public static void update(String update) throws SQLException {
        GameLogger.logInfo("Performing database update: " + update);
        statement = connection.prepareStatement(update);
        statement.executeUpdate();
        statement.close();
    }

    public static String processResultSet(ResultSet rs, String delimiter) {
        String temp = "";

        try {
            ResultSetMetaData meta = rs.getMetaData();
            int size = meta.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= size; ++i) {
                    Object value = rs.getObject(i);
                    temp += value + delimiter;
                }
                temp += "\n";
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return temp;
    }

}
