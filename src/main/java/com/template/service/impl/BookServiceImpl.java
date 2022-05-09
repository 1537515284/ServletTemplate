package com.template.service.impl;

import com.template.dao.BookDAO;
import com.template.pojo.Book;
import com.template.service.BookService;

import java.util.List;

public class BookServiceImpl implements BookService {

    private BookDAO bookDAO = new BookDAO();

    @Override
    public List<Book> list() {
        return bookDAO.list();
    }

    @Override
    public Book getBookById(Integer id) {
        return bookDAO.findById(id);
    }
}
