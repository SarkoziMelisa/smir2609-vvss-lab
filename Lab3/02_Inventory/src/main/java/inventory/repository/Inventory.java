
package inventory.repository;

import inventory.model.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory<T extends Item> {
    
    // Declare fields
    private ObservableList<T> items;
    private int autoId;


    public Inventory(){
        this.items = FXCollections.observableArrayList();
        this.autoId = 0;
    }


    // Declare methods
    /**
     * Add new item to observable list items
     * @param item
     */
    public void addItem(T item) {
        items.add(item);
    }
    
    /**
     * Remove item from observable list items
     * @param item
     */
    public void removeItem(T item) {
        items.remove(item);
    }
    
    /**
     * Accepts search parameter and if an ID or name matches input, that item is returned
     * @param searchItem
     * @return 
     */
    public T lookupItem(String searchItem) {
        for(T item: items) {
            String name = item.getName();
            if(name.startsWith(searchItem)) {
                if(name.length() == searchItem.length()) {
                    return item; //sirurile coincid
                }
                else {
                    return item; //numele incepe cu searchIndex
                }
            }
            else {
                if(name.endsWith(searchItem)) {
                    return item; //numele se sfarseste cu searchIndex
                }
                else {
                    if(name.contains(searchItem)) {
                        return item; //numele contine searchIndex (in mijloc)
                    }
                }
            }
        }
        return null;
    }
    
    /**
     * Update item at given index
     * @param index
     * @param item
     */
    public void updateItem(int index, T item) {
        items.set(index, item);
    }
    
    /**
     * Getter for Item Observable List
     * @return 
     */
    public ObservableList<T> getItems() {
        return items;
    }

    public void setItems(ObservableList<T> list) {
        items = list;
    }
    
    /**
     * Method for incrementing item ID to be used to automatically
     * assign ID numbers to products
     * @return 
     */
    public int getAutoId() {
        autoId++;
        return autoId;
    }

    public void setAutoId(int id){
        autoId = id;
    }
    
}
