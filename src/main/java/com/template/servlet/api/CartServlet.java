package com.template.servlet.api;

import com.template.dto.CartBookDTO;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "cart",value = {"/api/cart/*"})
public class CartServlet extends HttpServlet {

    private BookService bookService = new BookServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Result list = list(req);
        FastJsonUtils.writeJson(list,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("uri: "+req.getRequestURI());
        String requestURI = req.getRequestURI();
        int i = requestURI.lastIndexOf("/");
        String substring = requestURI.substring(i);

        Result res = Result.fail();

        if("/add".equals(substring)){
            res = add(req);
        }else if("/del".equals(substring)){
            res = del(req);
        }else if("/settle".equals(substring)){
            res = del(req);
            res.setMessage("结算成功!");
        }

        FastJsonUtils.writeJson(res,resp);
    }

    private Result list(HttpServletRequest request){
        HttpSession session = request.getSession();
        List cartList = (List)session.getAttribute("cartList");
        if (cartList == null) {
            return Result.fail().message("购物车为空");
        }
        return  Result.ok(cartList);
    }

    private Result add(HttpServletRequest request){
        String idStr = request.getParameter("id");
        String numsStr = request.getParameter("nums");
        Integer id = Integer.valueOf(idStr);
        Integer nums = Integer.valueOf(numsStr);




        HttpSession session = request.getSession();
        List cartList = (List)session.getAttribute("cartList");
        if (cartList == null) {
            cartList = new ArrayList();
            session.setAttribute("cartList",cartList);
        }

        for (int i = 0; i < cartList.size(); i++) {
            CartBookDTO cartBookDTO1= (CartBookDTO) cartList.get(i);
            if(cartBookDTO1.getId().equals(id)){
                cartBookDTO1.setNums(cartBookDTO1.getNums()+nums);
                return Result.ok();
            }
        }

        Book book = bookService.getBookById(id);
        CartBookDTO cartBookDTO = new CartBookDTO();
        cartBookDTO.setId(book.getId());
        cartBookDTO.setTitle(book.getTitle());
        cartBookDTO.setAuthor(book.getAuthor());
        cartBookDTO.setPrice(book.getPrice());
        cartBookDTO.setNums(nums);
        cartList.add(cartBookDTO);
        return Result.ok().message("增加成功");
    }

    private Result del(HttpServletRequest request){
        String idStr = request.getParameter("id");
        Integer id = Integer.valueOf(idStr);

        HttpSession session = request.getSession();
        List cartList = (List)session.getAttribute("cartList");
        if (cartList == null || cartList.isEmpty()) {
            return Result.fail().message("非法请求");
        }
        for (int i = 0; i < cartList.size(); i++) {
            CartBookDTO cartBookDTO = (CartBookDTO) cartList.get(i);
            if(cartBookDTO.getId().equals(id)) {
                cartList.remove(i);
                break;
            }
        }
        return Result.ok().message("移除成功");
    }
}
