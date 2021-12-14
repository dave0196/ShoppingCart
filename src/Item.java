public class Item {
    private String name;
    private String description;
    private double pricePerUnit;
    private double grossPrice;
    private int quantity;

    Item(String name, String description, double pricePerUnit, int quantity) {
        this.name = name;
        this.description = description;
        this.pricePerUnit = pricePerUnit;
        this.quantity = quantity;
        this.grossPrice = pricePerUnit * quantity;
    }

    // getters

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public double getPricePerUnit() {
        return this.pricePerUnit;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public double getGrossPrice() {
        return this.grossPrice;
    }

    // setters

    public void setDescription(String newDescription) {
        this.description = newDescription;
    }

    public void setPricePerUnit(double newPrice) {
        this.pricePerUnit = newPrice;
    }

    public void setQuantity(int newQuantity) {
        this.quantity = newQuantity;
    }

    public void setGrossPrice(double newPrice) {
        this.grossPrice = newPrice * quantity;
    }

    @Override
    public String toString() {
        return String.format(
                "\nItem Description: %s\nItem Price per Unit: $%.2f\nItem Quantity: %d\nGross Price: $%.2f\n\n",
                this.description,
                this.pricePerUnit, this.quantity, this.grossPrice);
    }
}
