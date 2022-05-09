package com.template.servlet.api;

import com.template.pojo.Book;
import com.template.service.BookService;
import com.template.service.impl.BookServiceImpl;
import com.template.utils.FastJsonUtils;
import com.template.utils.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "book",value = {"/api/books"})
public class BookServlet extends HttpServlet {

    private BookService bookService = new BookServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Book> list = bookService.list();
        FastJsonUtils.writeJson(Result.ok(list),resp);
    }

}
