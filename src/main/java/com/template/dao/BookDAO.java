package com.template.dao;

import com.template.pojo.Book;

import java.util.List;

public class BookDAO extends BasicDAO<Book>{

    public List<Book> list(){
        String sql = "SELECT id,title,author,price FROM books";
        return this.queryMulti(sql, Book.class);
    }

    public Book findById(Integer id) {
        String sql = "SELECT id,title,author,price FROM books WHERE id = ?";
        return this.querySingle(sql,Book.class,id);
    }
}
