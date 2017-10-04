/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.distjava.khj.bookwebapp.model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 *
 * @author kevinjerke
 */
public class MsSqlServerDataAccess implements DataAccess {
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    private String driverClass;
    private String url;
    private String userName;
    private String password;
    
        
    private final int ALL_RECORDS = 0;
    
    public MsSqlServerDataAccess(String driverClass,
        String url, String userName, String password) {
        
        setDriverClass(driverClass);
        setUrl(url);
        setUsername(userName);
        setPassword(password);
    }
    
    @Override
    public void openConnection()
            throws ClassNotFoundException, SQLException {
        
        Class.forName (driverClass);
        conn = DriverManager.getConnection(url, userName, password);
    }
    
    @Override
    public void closeConnection() throws SQLException {
        if(conn !=null) conn.close();
    }
    
    @Override
    public List<Map<String, Object>> getAllRecords(String tableName, int maxRecords) throws SQLException, ClassNotFoundException {
        List<Map<String, Object>> rawData = new Vector<>();
        String sql = "";
        
        if(maxRecords > ALL_RECORDS) {
        sql = "select TOP " + maxRecords + " * from " + tableName;
        } else {
            sql = "select * from " + tableName;
        }
        
        openConnection();
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);
        
        ResultSetMetaData rsmd = rs.getMetaData();
        int colCount = rsmd.getColumnCount();
        Map<String,Object> record = null;
        
        while( rs.next() ) {
            record = new LinkedHashMap<>();
            for(int colNum= 1; colNum <= colCount; colNum++){
                record.put(rsmd.getColumnName(colNum), rs.getObject(colNum));
            }
            rawData.add(record);
        }
        closeConnection();
        
        return rawData;
    
    }

    @Override
    public String getDriverClass() {
        return driverClass;
    }

    @Override
    public final void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public final void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public final void setUsername(String username) {
        this.userName = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public final void setPassword(String password) {
        this.password = password;
    }
    
    public static void main(String[] args) throws SQLException, ClassNotFoundException { 
        MsSqlServerDataAccess db = new MsSqlServerDataAccess(
        "corg.apache.derby.jdbc.ClientDriver",
        "jdbc:derby://localhost:1527/sample",
        "app", "app"
        );
        
        List<Map<String, Object>> list = db.getAllRecords("CUSTOMER", 0);
        
        for(Map<String, Object> rec : list) {
            System.out.println(rec);
        }
    }
    
}

