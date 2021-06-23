package ru.geekbrains;

import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = "/product/*")
public class ProductServlet extends HttpServlet {

    private ProductRepository productRepository;

    @Override
    public void init() throws ServletException {
        productRepository = (ProductRepository) getServletContext().getAttribute("productRepository");
    }

    @Override
    protected void doGet(HttpServletRequest _req, HttpServletResponse _resp) throws ServletException, IOException {

        PrintWriter pw = _resp.getWriter();
        if (_req.getPathInfo() == null) {
            List<Product> products = productRepository.findAll();

            pw.println("<table>");
            pw.println("<tr><td>Id</td><td>Title</td><td>Price</td></tr>");

            for (Product p : products) {
                pw.println(String.format("<tr><td>%s</td><td><a href=\"product/%1$s\">%s</a></td><td>%10.2f</td></tr>", p.getId(), p.getTitel(), p.getPrice()));
            }

            pw.println("</table>");

        } else {
            String pathInfo =  _req.getPathInfo();

            try{
                Long id = Long.parseLong(pathInfo.substring(1));
                Product p = productRepository.getProductById(id);

                if (p != null) {
                    pw.println(String.format("<div><h3>Product Title - %s</h3><p>Vendor code - %s</p><p>Unit Price - %10.2f</p></div>", p.getTitel(), p.getId(), p.getPrice()));
                    pw.println("<a href=\"/servlet-app/product\">Back to products list</a>");

                } else {
                    pw.println("<p>Incorrect reference " + pathInfo + "</p>");
                    pw.println(formatBackReference(_req));

                }

            } catch (java.lang.NumberFormatException e){
                pw.println("<p>Incorrect reference " + pathInfo + "</p>");
                pw.println(formatBackReference(_req));
            }

        }
    }

    private String formatBackReference(HttpServletRequest _request){
        return String.format("<a href=\"%s%s\">Back to products list</a>", _request.getContextPath(), _request.getServletPath());
    }
}
