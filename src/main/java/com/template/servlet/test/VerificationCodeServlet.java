package com.template.servlet.test;

import com.template.utils.VerificationCodeImage;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

@WebServlet("/test/getVerificationCode")
public class VerificationCodeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedImage verificationCodeImage = VerificationCodeImage.getVerificationCodeImage();
        String verificationCode = String.valueOf(VerificationCodeImage.getVerificationCode());
        HttpSession session = request.getSession();
        session.setAttribute("verificationCode",verificationCode);
        ImageIO.write(verificationCodeImage,"JPEG",response.getOutputStream());
    }
}
