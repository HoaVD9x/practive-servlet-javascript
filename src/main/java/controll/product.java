package controll;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Product;
import org.hibernate.Criteria;
import org.hibernate.Session;
import util.HibernateUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/product")
public class product extends HttpServlet {

    Session session;
    @Override
    public void init() throws ServletException {
       try {
           session= HibernateUtil.getSessionFactory().openSession();
       } catch (Exception exception) {
           exception.printStackTrace();
       }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out =  resp.getWriter();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = builder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);

        try {
            criteriaQuery.select(root);
            List<Product> products = session.createQuery(criteriaQuery).getResultList();
             for (Product product:products){
                 out.println("<div class=\"card m-3\" >\n" +
                         "                <img class=\"card-img-top\" src=\""+product.getProductImageLink()+"\" alt=\"Card image\" >\n" +
                         "                <div class=\"card-body\">\n" +
                         "                  <h4 class=\"card-title\">"+product.getProductName()+"</h4>\n" +
                         "                  <p class=\"card-text\">"+product.getProductDescription()+"</p>\n" +
                         "                  <p class=\"card-price\"> "+product.getBuyPrice()+"</p>\n" +
                         "                  <a href=\"#\" class=\"btn btn-primary\"> See details</a>\n" +
                         "                </div>\n" +
                         "            </div>");
             }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
