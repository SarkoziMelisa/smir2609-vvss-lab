package inventory.service;

import inventory.model.Part;
import inventory.repository.InventoryRepository;
import inventory.validator.ValidatorException;
import inventory.validator.ValidatorProduct;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.*;


import static org.junit.jupiter.api.Assertions.*;

class InventoryServiceTest {

    private InventoryService service;

    @BeforeEach
    void setUp() {

        //mock repo
        InventoryRepository repo= new InventoryRepository();
        this.service = new InventoryService(repo);
    }

    @Test
    public void addProductValid(){
        //mock Product
    }

    @Test
    public void addProductNonValid(){
        //mock Product
    }
}