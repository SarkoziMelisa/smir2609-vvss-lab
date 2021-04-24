package inventory.service;

import inventory.repository.InventoryRepository;
import inventory.validator.ValidatorProduct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SR_IntegrationTest {


    private InventoryService service;

    @BeforeEach
    void setUp() {
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
