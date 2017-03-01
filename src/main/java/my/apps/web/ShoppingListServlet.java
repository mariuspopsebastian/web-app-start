package my.apps.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/shoppingList")
public class ShoppingListServlet extends HttpServlet {

    private int counter;
    private static final String LIST_ACTION = "list";

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("my shopping list service called now.");

        String action = request.getParameter("action");
        counter++;

        if (action != null && action.equals(LIST_ACTION)) {
            listAction(request, response);
        }

        System.out.println("I was used " + counter + " times!");
    }

    private void listAction(HttpServletRequest request, HttpServletResponse response) {
        String jsonResponse = "[\n" +
                "  {\"nume\": \"Paine\", \"cantitate\": 1},\n" +
                "  {\"nume\": \"Suc\", \"cantitate\": 3},\n" +
                "  {\"nume\": \"Mere\", \"cantitate\": 10},\n" +
                "  {\"nume\": \"Pasta de dinti\", \"cantitate\": 2}\n" +
                "]";
        returnJsonResponse(response, jsonResponse);
    }

    @Override
    public void init() throws ServletException {
        super.init();
        System.out.println("init() called. Counter is: " + counter);
    }

    @Override
    public void destroy() {
        System.out.println("Destroying Servlet! Counter is:" + counter);
        super.destroy();
    }

    /**/
    private void returnJsonResponse(HttpServletResponse response, String jsonResponse) {
        response.setContentType("application/json");
        PrintWriter pr = null;
        try {
            pr = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert pr != null;
        pr.write(jsonResponse);
        pr.close();
    }
}