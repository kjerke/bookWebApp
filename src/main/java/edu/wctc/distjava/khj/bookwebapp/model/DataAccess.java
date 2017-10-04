/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.distjava.khj.bookwebapp.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author kevinjerke
 */
public interface DataAccess {

    void closeConnection() throws SQLException;

    
    /
    List<Map<String, Object>> getAllRecords(String tableName, int maxRecords) throws SQLException, ClassNotFoundException;

    String getDriverClass();

    String getPassword();

    String getUrl();

    String getUsername();

    void openConnection() throws ClassNotFoundException, SQLException;

    void setDriverClass(String driverClass);

    void setPassword(String password);

    void setUrl(String url);

    void setUsername(String username);
    
}
