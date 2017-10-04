/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.distjava.khj.bookwebapp.model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
public class MySqlDataAccess implements DataAccess {
    private Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private String driverClass;
    private String url;
    private String userName;
    private String password;
    
        
    private final int ALL_RECORDS = 0;
    
    public MySqlDataAccess(String driverClass,
        String url, String userName, String password) {
        
        setDriverClass(driverClass);
        setUrl(url);
        setUsername(userName);
        setPassword(password);
    }
    
    public void openConnection()
            throws ClassNotFoundException, SQLException {
        
        Class.forName (driverClass);
        conn = DriverManager.getConnection(url, userName, password);
    }
    
    public void closeConnection() throws SQLException {
        if(conn !=null) conn.close();
    }
    
    public int createRecord(String tableName, List<String> colnames, List<Object> colValues) {
        
    }
    
    public int deleteRecordById(String tableName, String pkColName, Object pkValue) throws ClassNotFoundException, SQLException {
        
        String sql = "DELETE FROM" + tableName + "WHERE " + pkColName + " = ";
        
        if(pkValue instanceof String) {
            sql += "'" + pkValue.toString() + "'";
        } else {
            sql += Long.parseLong(pkValue.toString());
        }
        
        openConnection();
        pstmt = conn.prepareStatement(sql);
        pstmt.setObject(1, pkValue);
        int recsDeleted = pstmt.executeUpdate();
        closeConnection();
        
        return recsDeleted;
    }
    
    public List<Map<String, Object>> getAllRecords(String tableName, int maxRecords) throws SQLException, ClassNotFoundException {
        List<Map<String, Object>> rawData = new Vector<>();
        String sql = "";
        
        if(maxRecords > ALL_RECORDS) {
        sql = "select * from " + tableName + " limit " + maxRecords;
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

    public String getDriverClass() {
        return driverClass;
    }

    public final void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getUrl() {
        return url;
    }

    public final void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return userName;
    }

    public final void setUsername(String username) {
        this.userName = username;
    }

    public String getPassword() {
        return password;
    }

    public final void setPassword(String password) {
        this.password = password;
    }
    
    public static void main(String[] args) throws SQLException, ClassNotFoundException { 
        MySqlDataAccess db = new MySqlDataAccess(
        "com.mysql.jdbc.Driver",
        "jdbc:mysql://localhost:3306/book",
        "root", "admin"
        );
        
        int recsDeleted = db.deleteRecordById("author", "author_Id", new Integer(50));
        
        List<Map<String, Object>> list = db.getAllRecords("author", 0);
        
        for(Map<String, Object> rec : list) {
            System.out.println(rec);
        }
    }
    
}

