package mk.ukim.finki.av1wp.web.servlet;

import mk.ukim.finki.av1wp.service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "category - servlet", urlPatterns = "/servlet/category")
public class CategoryServlet extends HttpServlet {
    private final CategoryService categoryService;

    public CategoryServlet(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ipAddress = req.getRemoteAddr();
        String clientAgent = req.getHeader("User-Agent");
        PrintWriter writer = resp.getWriter();
        writer.println("<html><head></head><body><h3>User Info</h3>IP Address: " + ipAddress + "</br>Client Agent: "
                + clientAgent);
        writer.println("<h3>Category List</h3><ul>");
        categoryService.listCategories().forEach(r -> writer.println("<li>"+ r.getName() + " (" + r.getDescription() +
                ")</li>"));
        writer.println("</ul><h3>Add Category</h3>");
        writer.println("<form method='POST' action='/servlet/category'>" +
                "<label for='name'>Name:</label>" +
                "<input id='name' type='text' name='name'/>" +
                "<label for='desc'>Description:</label>" +
                "<input id='desc' type='text' name='desc'/>" +
                "<input type='submit' value='Submit'/>");
        writer.println("</form></body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String categoryName = req.getParameter("name");
        String desc = req.getParameter("desc");
        categoryService.create(categoryName, desc);
        resp.sendRedirect("/servlet/category");
    }
}
