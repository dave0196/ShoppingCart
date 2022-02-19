import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ShoppingCart implements PublicConstants {
    private double subtotal = 0.0;
    private double total = 0.0;
    private double tax = 0.0;
    private Scanner sc = new Scanner(System.in);
    HashMap<String, Item> itemList = new HashMap<>(); // holds all items contained within the shopping cart

    /**
     * Asks the user to add information about the item to be added. Creates an
     * object of type Item with that information.
     */
    public void addItem() {
        while (true) {
            try {
                System.out.println("\nType a unique name/identifier for this item: ");
                String name = sc.nextLine(); // to be used as the keys for our hashmap
                name = name.toUpperCase().trim(); // convert key to uppercase and remove trailing zeroes

                // check whether item exists already
                Boolean exists = searchItem(name);
                if (exists) { // item already exists
                    System.out.printf(
                            "\n***WARNING*** This item already exists as:\n\n%s%s\nWould you like to update/modify it? Y or N?\n",
                            name, itemList.get(name).toString());
                    String response = sc.nextLine();
                    switch (response) {
                        case "Yes", "YES", "yes", "Y", "y":
                            Item itemToModify = itemList.get(name); // create a pointer to the item that will be
                                                                    // modified
                            modifyExistingItem(itemToModify);
                            return;
                        case "No", "NO", "no", "N", "n":
                            return;
                        default:
                            System.out.println("\nThat's not a valid choice. Please Try again.\n");
                            return;
                    }
                } else {
                    System.out.println("Write a brief description for this item: ");
                    String description = sc.nextLine();

                    double price = checkPrice();

                    int quantity = checkQuantity();
                    Item newItem = new Item(name, description, price, quantity);
                    itemList.put(name, newItem);

                    // item was added, let the user know
                    System.out.println("\nSuccess! The item added to your cart.\n");
                    sc.nextLine();
                    subtotal += newItem.getGrossPrice(); // since a new item was added, update the subtotal
                    return;
                }
            } catch (InputMismatchException i) {
                System.out.println("You typed in invalid characters. Try again.\n");
                sc.next(); // clear bad input from scanner
                continue;
            } catch (Exception e) {
                System.out.println(ERROR_MESSAGE);
                sc.next(); // clear bad input from scanner
                continue;
            }
        }
    } // end addItem method
    /**
     * Allows user to modify the properties of an existing item directly from the main menu. 
     * This method makes a call to the private function modifyExistingItem(). 
     */
    public void modifyItemFromMenu() {
        if (itemList.size() == ZERO) {
            System.out.println(NO_ITEMS);
            return;
        } else {
            System.out.println("type in the item to be modified");
            String searchItem = sc.nextLine();
            searchItem = searchItem.toUpperCase().trim();
            Boolean exists = this.searchItem(searchItem);
            if (exists) {
                System.out.println("Item Found!\n");
                Item itemToModify = this.itemList.get(searchItem);
                this.modifyExistingItem(itemToModify);
                return;
            } else {
                System.out.println("\nSorry, it seems that item doesn't exist.\n");
                return;
            }
        }
    }
    
    /**
     * Allows the program to modify an item internally. Is called by addItem() and modifyItemFromMenu()
     * @param itemToModify a reference to the item that will be modified
     */
    private void modifyExistingItem(Item itemToModify) {
        double newPrice;
        while (true)
            try {
                // update description
                System.out.println("Write a new description for the item: ");
                String newDescription = sc.nextLine();
                itemToModify.setDescription(newDescription);

                // update price
                System.out.println("Type a new price for the item: ");
                newPrice = sc.nextDouble();

                // update quantity
                System.out.println("Type the new quantity: ");
                int newQuantity = sc.nextInt();
                itemToModify.setQuantity(newQuantity);
                sc.nextLine();

                // make adjustments to totals
                subtotal -= itemToModify.getGrossPrice(); // need to subtract the current item gross price from
                // subtotal
                itemToModify.setPricePerUnit(newPrice); // update the items price per unit
                itemToModify.setGrossPrice(newPrice); // update the items gross price
                subtotal += itemToModify.getGrossPrice(); // update the subtotal
                computeTotals();
                System.out.println("\nSuccess! The item was modified.\n");
                return;
            } catch (Exception e) {
                System.out.println(ERROR_MESSAGE);
                sc.next(); // clear the bad input from the scanner
                sc.nextLine(); // read new line in case the exeption happened while reading an integer
                continue;
            }

    }

    /**
     * Validates the price entered for a given item
     * 
     * @return the valid, non-negative price per unit of an item
     */
    private double checkPrice() {
        double price = ZERO;
        do {
            try {
                System.out.println("Type the price per unit in dollars: (must be a positive number)");
                price = sc.nextDouble();
            } catch (Exception e) {
                System.out.println(ERROR_MESSAGE);
                sc.next();
                continue;
            }
        } while (price < ONE); // items must have a positive nonzero-price, otherwise no profit is being made
        return price;
    }

    /**
     * Validates the quantity for a given item
     * 
     * @return the valid, non-negative quantity of a given item
     */
    private int checkQuantity() {
        int quantity = ZERO;
        do {
            try {
                System.out.println("Type the quantity: (must be >= 1) ");
                quantity = sc.nextInt();
            } catch (Exception e) {
                System.out.println(ERROR_MESSAGE);
                sc.next();
                continue;
            }
        } while (quantity < ONE); // cannot be negative or zero
        return quantity;
    }

    /**
     * precondition: the searchTerm value must be an uppercase string
     * 
     * Performs a simple linear search of all items contained in the cart
     * 
     * @param searchTerm the name/identifier of the item to be searched
     * @return true if the item was found. False is the item was not found
     */
    private Boolean searchItem(String searchTerm) {
        for (String s : itemList.keySet()) {
            if (searchTerm.equals(s)) {
                return true; // match found, the item exits in the list
            } else {
                continue; // keep searching
            }
        }
        return false; // no matches found, item does not exist in the list
    } // end searchItem method

    /**
     * Uses the searchItem function to identify and delete an item from the list, if
     * it exists
     */
    public void deleteItem() {
        if (itemList.size() == ZERO) { // check whether the map is empty or not
            System.out.println(NO_ITEMS);
            return;
        } else {
            System.out.println("Type in the name of the item to be deleted (ignores casing)");
            String toBeDeleted = sc.nextLine();
            toBeDeleted = toBeDeleted.toUpperCase(); // convert all item names to uppercase for simplicity
            Boolean itemExists = searchItem(toBeDeleted); // check whether the item exists (based on the name)
            if (itemExists) { // the item exists and must be deleted
                subtotal -= itemList.get(toBeDeleted).getGrossPrice(); // the subtotal must be updated before removal
                itemList.remove(toBeDeleted);
                System.out.printf("\nItem(s) %s successfully deleted\n\n", toBeDeleted); // let the user know
                return;
            } else { // itemExists must be false, let the user know no deletions occurred
                System.out.println("\nNo matches found. Item was not deleted.\n");
                return;
            }
        }
    } // end deleteitem method

    /**
     * Calculates the tax and total based on the current subtotal amount
     * 
     * @return a textual representation of subtotal, tax, and total
     */
    public String computeTotals() {
        tax = TAX_RATE * subtotal;
        total = subtotal + tax;
        return String.format("\nSubtotal is: $%.2f\nTax is: $%.2f\nGrand total is: $%.2f\n", subtotal, tax,
                total);
    } // end computeTotals method

    public double getSubtotal() {
        return subtotal;
    }

    public double getTax() {
        return tax;
    }

    public double getTotal() {
        return total;
    }

    @Override
    public String toString() {
        if (itemList.size() == ZERO) {
            return NO_ITEMS;
        } else
            return itemList.toString();
    }
}
