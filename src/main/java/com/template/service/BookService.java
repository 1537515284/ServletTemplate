package com.template.service;

import com.template.pojo.Book;

import java.util.List;

public interface BookService {

    List<Book> list();

    Book getBookById(Integer id);
}
