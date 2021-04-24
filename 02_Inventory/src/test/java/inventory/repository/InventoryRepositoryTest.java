package inventory.repository;

import inventory.model.Part;
import inventory.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InventoryRepositoryTest {
    private InventoryRepository repository;

    @BeforeEach
    void setUp() {
        InventoryRepository repo= new InventoryRepository();
        this.repository = repo;
    }

    @Test
    public void addProductValid() {
        //mock Product
    }
    @Test
    public void addProductNonValid(){
        //mock Product
    }
}
