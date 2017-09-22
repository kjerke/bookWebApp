/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.distjava.khj.bookwebapp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *
 * @author kevinjerke
 */
public class AuthorService {
    
    public List<Author> getAuthorList() {
     return Arrays.asList(
            new Author(1,"Mark Twain", new Date()),
            new Author(2,"Stephen King", new Date()),
            new Author(3,"George Orwell", new Date()) 
     );
     
    }
}
