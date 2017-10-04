/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.distjava.khj.bookwebapp.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *
 * @author kevinjerke
 */
public class AuthorService {
        private IAuthorDao authorDao;
        private final String AUTHOR_TBL = "author";
        private final String AUTHOR_PK = "author_id";
    
    public AuthorService(IAuthorDao authorDao) {
        setAuthorDao(authorDao);
    }
    
    public final int removeAuthorById(Integer id) throws ClassNotFoundException, SQLException, NumberFormatException {
        if(id == null) {
            throws new IllegalArgumentException("id must be a Integer greater than 0");
        }
        
        Integer value = Integer.parseInt(id);
        return authorDao.removeAuthorById(id);
    }
    
    public List<Author> getAuthorList() 
            throws SQLException, ClassNotFoundException {
        
        return authorDao.getListOfAuthors();
    }

    public IAuthorDao getAuthorDao() {
        return authorDao;
    }

    public void setAuthorDao(IAuthorDao authorDao) {
        this.authorDao = authorDao;
    }
    
    public static void main(String[] args) 
            throws SQLException, ClassNotFoundException {
        
        IAuthorDao dao = new AuthorDao(
            "com.mysql.jdbc.Driver",
            "jdbc:mysql://localhost:3306/book",
            "root", "admin",
            new MySqlDataAccess("com.mysql.jdbc.Driver",
            "jdbc:mysql://localhost:3306/book",
            "root", "admin")
        );
        
        AuthorService authorService = 
                new AuthorService(dao);
        
        int recsDeleted = authorService.removeAuthorById("53");
        
        List<Author> list = authorService.getAuthorList();
        
        for(Author a: list) {
            System.out.println(a.getAuthorId() + ", "
                + a.getAuthorName() + ", " + a.getDateAdded() + "\n");
        }
    }
}