import java.util.Scanner;

/**
 * Simulates a shopping cart. Allows to add, delete items, check all items on
 * the cart, and check totals (taxes included).
 * 
 * @author David Medrano
 * @version 1.2
 */
public class Main implements PublicConstants {

    public static void main(String[] args) throws Exception {
        ShoppingCart mycart = new ShoppingCart();
        displayMenu(mycart);
    }

    /**
     * 
     * @param mycart a reference to an object that represents a shopping cart
     */
    public static void displayMenu(ShoppingCart mycart) {
        String response;
        Scanner scanner = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        sb.append("=============================================================\n")
                .append("\t\tWelcome to SuperMart\n \nPlease choose an option from the menu below: \n")
                .append("\tA. Add Item to cart.\n\tM. Modify item\n\tD. Delete Item from Cart.\n\tP. Print subtotal, taxes and total combined.\n\tL. List all items in the cart.\n\tE. Exit. ")
                .append("\n=============================================================\n");

        do {
            System.out.println(sb.toString());
            System.out.print("Choose an option: ");
            response = scanner.nextLine();
            switch (response) {
                case "A", "a":
                    mycart.addItem();
                    break;
                case "M", "m":
                    mycart.modifyItemFromMenu();
                    break;
                case "D", "d":
                    mycart.deleteItem();
                    break;
                case "L", "l":
                    System.out.println(mycart);
                    break;
                case "P", "p":
                    System.out.println(mycart.computeTotals());
                    break;
                case "E", "e":
                    System.out.println("\nShutting down. Thanks for doing business with us.\n");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("\nYou chose an invalid option! Try again.\n");
                    break;
            }
        } while (!response.equals(SENTINEL_1) || !response.equals(SENTINEL_2));
    } // end displayMenu method
} // end Main class
