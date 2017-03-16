package my.apps.web;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by condor on 26/02/15.
 * FastTrackIT, 2015
 * <p/>
 * DEMO ONLY PURPOSES, IT MIGHT CONTAINS INTENTIONALLY ERRORS OR ESPECIALLY BAD PRACTICES
 *
 * make sure you refactor it and remove lots of bad practices like loading the driver multiple times or
 * repeating the same common code multiple times
 *
 * BTW, exercise 1: how we reorg this/refactor in small pieces
 */
public class DemoCRUDOperations {

    // 1. define connection params to db
    final static String URL = "jdbc:postgresql://54.93.65.5:5432/5Florin";
    final static String USERNAME = "fasttrackit_dev";
    final static String PASSWORD = "fasttrackit_dev";

    public static void main(String[] args) {
        System.out.println("Hello database users! We are going to call DB from Java");
        try {
            //demo CRUD operations
    //        demoCreate();
            readShoppingItems();
      //      demoUpdate();
      //      demoDelete();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void writeShoppingItem(Item item) throws SQLException, ClassNotFoundException {

        // 1. load the driver
        Class.forName("org.postgresql.Driver");

        // 2. obtain a connection
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        // 3. create a query statement
        PreparedStatement pSt = conn.prepareStatement("INSERT INTO shoppingItem (nume, cantitate) VALUES (?,?)");
        pSt.setString(1, item.getNume());
        pSt.setInt(2, item.getCantitate());

        // 4. execute a prepared statement
        int rowsInserted = pSt.executeUpdate();
        System.out.println("Inserted " + rowsInserted + " rows.");


        // 5. close the objects
        pSt.close();
        conn.close();
    }

    public static List<Item>  readShoppingItems() throws ClassNotFoundException, SQLException {

        // 1. load the driver
        Class.forName("org.postgresql.Driver");

        // 2. obtain a connection
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        // 3. create a query statement
        Statement st = conn.createStatement();

        // 4. execute a query
        ResultSet rs = st.executeQuery("SELECT nume ,cantitate FROM shoppingItem");

        // 5. iterate the result set and print the values
        List<Item> items =  new ArrayList<>();
        while (rs.next()) {
            String nume = rs.getString("nume");
            int cantitate = rs.getInt("cantitate");
            Item item = new Item(nume, cantitate);
            items.add(item);
            System.out.print(rs.getString("nume").trim());
            System.out.print("---");
            System.out.println(rs.getInt("cantitate"));
        }

        // 6. close the objects
        rs.close();
        st.close();
        conn.close();
        return items;
    }

    private static void demoUpdate() throws ClassNotFoundException, SQLException {

        // 1. load the driver
        Class.forName("org.postgresql.Driver");

        // 2. obtain a connection
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        // 3. create a query statement
        PreparedStatement pSt = conn.prepareStatement("UPDATE socialuser SET username=?, PASSWORD=? WHERE username=?"); //so we have 3 params
        pSt.setString(1, "marcel");
        pSt.setString(2, "password1");
        pSt.setString(3, "george");

        // 4. execute a prepared statement
        int rowsUpdated = pSt.executeUpdate();
        System.out.println("Updated " + rowsUpdated + " rows.");

        // 5. close the objects
        pSt.close();
        conn.close();
    }


    private static void demoDelete() throws ClassNotFoundException, SQLException {

        // 1. load the driver
        Class.forName("org.postgresql.Driver");

        // 2. obtain a connection
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        // 3. create a query statement
        PreparedStatement pSt = conn.prepareStatement("DELETE FROM socialuser WHERE username=?");
        pSt.setString(1, "marcel");

        // 4. execute a prepared statement
        int rowsDeleted = pSt.executeUpdate();
        System.out.println(rowsDeleted + " rows were deleted.");
        // 5. close the objects
        pSt.close();
        conn.close();
    }
}