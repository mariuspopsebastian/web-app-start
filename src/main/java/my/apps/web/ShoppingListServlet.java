package my.apps.web;

import com.oracle.javafx.jmx.json.JSONDocument;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringJoiner;

@WebServlet("/shoppingList")
public class ShoppingListServlet extends HttpServlet {

    private int counter;
    private static final String LIST_ACTION = "list";

    private ShoppingItem[] shoppingList = new ShoppingItem[5];
    private int index = 0;

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("my shopping list service called now.");

        String action = request.getParameter("action");
        counter++;
        if(action == null) {
            System.out.println("Null action not supported");
            return;
        }

        if (action.equals(LIST_ACTION)) {
            listAction(request, response);
        } else if (action.equals("add")) {
            addAction(request, response);
        }
        System.out.println("I was used " + counter + " times!");
    }

    private void addAction(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Adding a new shopping item");
        String produs = request.getParameter("produs");
        String cantitate = request.getParameter("cantitate");

        shoppingList[index] = new ShoppingItem(produs, cantitate);
        index++;

        try {
            response.sendRedirect("/shopping-list.html");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void listAction(HttpServletRequest request, HttpServletResponse response) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (int i = 0; i < index; i++) {
            ShoppingItem item = shoppingList[i];
            arrayBuilder.add(Json.createObjectBuilder()
                    .add("nume", item.getNume())
                    .add("cantitate", item.getCantitate())
                    .build()
            );
        }
        returnJsonResponse(response, arrayBuilder.build().toString());
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