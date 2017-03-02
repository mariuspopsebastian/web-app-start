package my.apps.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/shoppingList")
public class ShoppingListServlet extends HttpServlet {

    private int counter;
    private static final String LIST_ACTION = "list";

//    Item[] items = {
//            new Item("Paine", "100"),
//            new Item("Suc", "3"),
//            new Item("Mere", "10"),
//            new Item("Pasta de dinti", "2"),
//            new Item("Pasta de icre", "2")
//    };
    List<Item> items = new ArrayList<>();

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("my shopping list service called now.");

        String action = request.getParameter("action");
        counter++;

        if (action != null && action.equals(LIST_ACTION)) {
            listAction(request, response);
        } else if (action != null && action.equals("add")) {
            addAction(request, response);
        }

        System.out.println("I was used " + counter + " times!");
    }

    private void addAction(HttpServletRequest request, HttpServletResponse response) {
        String produs = request.getParameter("produs");
        String cantitate = request.getParameter("cantitate");

        Item itemulNou = new Item(produs, cantitate);

        items.add(itemulNou);

        try {
            response.sendRedirect("/shopping-list.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listAction(HttpServletRequest request, HttpServletResponse response) {
        String jsonResponse = "[";
        for(int i= 0 ; i< items.size() ; i++) {
            String nume = items.get(i).getNume();
            String cantitate = items.get(i).getCantitate();
            String element = "{\"nume\": \"" + nume + "\", \"cantitate\": " + cantitate + "}";
            jsonResponse += element;
            if(i < items.size() - 1) {
                jsonResponse += ",";
            }
        }
        jsonResponse += "]";
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