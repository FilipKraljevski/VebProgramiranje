package mk.ukim.finki.av1wp.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "hello", urlPatterns = "/hello")
public class HelloWorldServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name  = req.getParameter("name");
        String surname = req.getParameter("surname");
        if(name == null){
            name = "my";
        }
        if(surname == null){
            surname = "man";
        }
        PrintWriter writer = resp.getWriter();
        writer.write("<html><head></head><body><h1>Hi " + name + " " + surname + "!</h1></body></html>");
        writer.flush();
    }
}
