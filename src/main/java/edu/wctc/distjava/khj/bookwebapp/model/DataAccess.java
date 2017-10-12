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

    /**
     * Returns records from a table. Requires and open connection.
     * @param tableName
     * @param maxRecords
     * @return
     * @throws SQLException
     */
    
     public abstract int createRecord(String tableName, List<String> colNames, 
            List<Object> colValues) throws SQLException;
     
    public int deleteRecordById(String tableName, String pkColName, 
            Object pkValue) throws ClassNotFoundException, 
            SQLException;
    
    List<Map<String, Object>> getAllRecords(String tableName, int maxRecords) throws SQLException, ClassNotFoundException;

  public abstract void openConnection(String driverClass, 
            String url, String userName, String password) 
            throws ClassNotFoundException, SQLException;


    
}
