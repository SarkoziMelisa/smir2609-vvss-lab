
package inventory.model;

import javafx.collections.ObservableList;


public class Product extends Item{
    
    // Declare fields
    private ObservableList<Part> associatedParts;

    // Constructor
    public Product(int productId, String name, double price, int inStock, int min, int max, ObservableList<Part> partList) {
        super(productId, name, price, inStock, min, max);
        this.associatedParts = partList;
    }
    
    // Getters
    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }

    // Setters
    public void setAssociatedParts(ObservableList<Part> associatedParts) {
        this.associatedParts = associatedParts;
    }
    
    // Other methods
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }
    
    public void removeAssociatedPart(Part part) {
        associatedParts.remove(part);
    }
    
    public Item lookupAssociatedPart(String searchPart) {
        for(Part p:associatedParts) {
            if(p.getName().contains(searchPart) || Integer.toString(p.getId()).equals(searchPart)) {
                return p;
            }
        }
        return null;
    }
    
    /**
     * Generate an error message for invalid values in a product
     * and evaluate whether the sum of the price of associated parts
     * is less than the price of the resulting product.
     * A valid product will return an empty error message string.
     * @param name
     * @param min
     * @param max
     * @param inStock
     * @param price
     * @param parts
     * @param errorMessage
     * @return 
     */
    public static String isValidProduct(String name, double price, int inStock, int min, int max, ObservableList<Part> parts, String errorMessage) {
        double priceOfParts = 0.00;
        for (Part part : parts) {
            priceOfParts += part.getPrice();
        }
        if (name.equals("")) {
            errorMessage += "A name has not been entered. ";
        }
        if (min < 0) {
            errorMessage += "The inventory level must be greater than 0. ";
        }
        if (price < 0.01) {
            errorMessage += "The price must be greater than $0. ";
        }
        if (min > max) {
            errorMessage += "The Min value must be less than the Max value. ";
        }
        if(inStock < min) {
            errorMessage += "Inventory level is lower than minimum value. ";
        }
        if(inStock > max) {
            errorMessage += "Inventory level is higher than the maximum value. ";
        }
        if (parts.isEmpty()) {
            errorMessage += "Product must contain at least 1 part. ";
        }
        if (priceOfParts > price) {
            errorMessage += "Product price must be greater than cost of parts. ";
        }
        return errorMessage;
    }

    @Override
    public String toString() {
        return "P," + this.getId() + "," + this.getName() + "," + this.getPrice() + "," +
                this.getInStock() + "," + this.getMin() + "," + this.getMax();
    }
}
