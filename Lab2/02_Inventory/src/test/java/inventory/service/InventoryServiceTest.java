package inventory.service;

import inventory.model.Part;
import inventory.repository.InventoryRepository;
import inventory.validator.ValidatorException;
import inventory.validator.ValidatorProduct;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.*;


import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InventoryServiceTest {

    private InventoryService service;

    @BeforeEach
    void setUp() {
        InventoryRepository repo= new InventoryRepository();
        ValidatorProduct validatorProduct = new ValidatorProduct();
        this.service = new InventoryService(repo, validatorProduct);
    }

    @RepeatedTest(1)
    @Order(1)
    @Tag("price")
    @DisplayName("Test ECP: Produs valid (pret valid)")
    public void addProductValidPriceECP(){
        ObservableList<Part> lista = FXCollections.observableArrayList();
        try {
            service.addProduct("Produs 1", 5, 15, 10, 25, lista);
            assert true;
            service.deleteProduct(service.lookupProduct("Produs 1"));
        } catch (ValidatorException ex) {
            assert false;
        }
    }

    @RepeatedTest(2)
    @Order(8)
    @Tag("price")
    @DisplayName("Test ECP: Produs non-valid (pret 0)")
    public void addProductNonValidPriceECP_1(){
        ObservableList<Part> lista = FXCollections.observableArrayList();
        try {
            service.addProduct("Produs 2", 0, 15, 10, 25, lista);
            assert false;
        } catch (ValidatorException ex) {
            assertEquals("Pretul nu poate sa fie zero\n", ex.getErrorMessage());
        }
    }

    @Test
    @Tag("price")
    @Order(9)
    @DisplayName("Test ECP: Produs non-valid (pret negativ)")
    public void addProductNonValidPriceECP_2() {
        ObservableList<Part> lista = FXCollections.observableArrayList();

        try {
            service.addProduct("Produs 3", -5, 15, 10, 25, lista);
            assert false;
        } catch (ValidatorException ex) {
            assertEquals("Pretul nu poate sa fie negativ\n", ex.getErrorMessage());
        }
    }

    @RepeatedTest(1)
    @Tag("stock")
    @Order(2)
    @DisplayName("Test ECP: Produs valid (inStock in interval)")
    public void addProductValidInStockECP(){
        ObservableList<Part> lista = FXCollections.observableArrayList();
        try {
            service.addProduct("Produs 4", 45, 15, 10, 25, lista);
            assert true;
            service.deleteProduct(service.lookupProduct("Produs 4"));
        } catch (ValidatorException ex) {
            assert false;
        }
    }


    @Test
    @Tag("stock")
    @Order(10)
    @DisplayName("Test ECP: Produs non-valid (inStock >  max)")
    public void addProductNonValidInStockECP_1(){
        ObservableList<Part> lista = FXCollections.observableArrayList();
        try {
            service.addProduct("Produs 5", 45, 55, 10, 25, lista);
            assert false;
        } catch (ValidatorException ex) {
            assertEquals("Stocul nu poate sa fie mai mare decat stocul maxim\n", ex.getErrorMessage());
        }

    }

    @Test
    @Order(11)
    @DisplayName("Test ECP: Produs non-valid (inStock < min)")
    public void addProductNonValidInStockECP_2() {
        ObservableList<Part> lista = FXCollections.observableArrayList();
        try {
            service.addProduct("Produs 6", 45, 5, 10, 25, lista);
            assert false;
        } catch (ValidatorException ex) {
            assertEquals("Stocul nu poate sa fie mai mic decat stocul minim\n", ex.getErrorMessage());
        }
    }

    @Test
    @Order(3)
    @DisplayName("Test BVA: Produs valid (pret > 0)")
    public void addProductValidPriceBVA(){
        ObservableList<Part> lista = FXCollections.observableArrayList();
        try {
            service.addProduct("Produs 7", 1, 15, 10, 25, lista);
            assert true;
            service.deleteProduct(service.lookupProduct("Produs 7"));
        } catch (ValidatorException ex) {
            assert false;
        }
    }

    @Test
    @Order(12)
    @DisplayName("Test BVA: Produs non-valid (pret = 0)")
    public void addProductNonValidProceBVA_1(){
        ObservableList<Part> lista = FXCollections.observableArrayList();
        try {
            service.addProduct("Produs 8", 0, 15, 10, 25, lista);
            assert false;
        } catch (ValidatorException ex) {
            assertEquals("Pretul nu poate sa fie zero\n", ex.getErrorMessage());
        }

    }

    @Test
    @Order(13)
    @DisplayName("Test BVA: Produs non-valid (pret < 0)")
    public void addProductNonValidProceBVA_2() {
        ObservableList<Part> lista = FXCollections.observableArrayList();
        try {
            service.addProduct("Produs 9", -1, 15, 10, 25, lista);
            assert false;
        } catch (ValidatorException ex) {
            assertEquals("Pretul nu poate sa fie negativ\n", ex.getErrorMessage());
        }
    }

    @Test
    @Order(4)
    @DisplayName("Test ECP: Produs valid (inStock = min)")
    public void addProductValidInStockBVA_1(){
        ObservableList<Part> lista = FXCollections.observableArrayList();
        try {
            service.addProduct("Produs 10", 45, 10, 10, 25, lista);
            assert true;
            service.deleteProduct(service.lookupProduct("Produs 10"));
        } catch (ValidatorException ex) {
            assert false;
        }
    }

    @Test
    @Order(5)
    @DisplayName("Test ECP: Produs valid (inStock = min + 1)")
    public void addProductValidInStockBVA_2(){
        ObservableList<Part> lista = FXCollections.observableArrayList();
        try {
            service.addProduct("Produs 11", 45, 11, 10, 25, lista);
            assert true;
            service.deleteProduct(service.lookupProduct("Produs 11"));
        } catch (ValidatorException ex) {
            assert false;
        }
    }

    @Test
    @Order(14)
    @DisplayName("Test BVA: Produs non-valid (inStock = min - 1)")
    public void addProductNonValidInStockBVA_1(){
        ObservableList<Part> lista = FXCollections.observableArrayList();
        try {
            service.addProduct("Produs 12", 45, 9, 10, 25, lista);
            assert false;
        } catch (ValidatorException ex) {
            assertEquals("Stocul nu poate sa fie mai mic decat stocul minim\n", ex.getErrorMessage());
        }
    }

    @Test
    @Order(6)
    @DisplayName("Test BVA: Produs valid (inStock = max - 1)")
    public void addProductValidInStockBVA_3(){
        ObservableList<Part> lista = FXCollections.observableArrayList();
        try {
            service.addProduct("Produs 13", 45, 24, 10, 25, lista);
            assert true;
            service.deleteProduct(service.lookupProduct("Produs 13"));
        } catch (ValidatorException ex) {
            assert false;
        }
    }

    @Test
    @Order(7)
    @DisplayName("Test BVA: Produs valid (inStock = max)")
    public void addProductValidInStockBVA_4(){
        ObservableList<Part> lista = FXCollections.observableArrayList();
        try {
            service.addProduct("Produs 14", 45, 25, 10, 25, lista);
            assert true;
            service.deleteProduct(service.lookupProduct("Produs 14"));
        } catch (ValidatorException ex) {
            assert false;
        }
    }

    @Test
    @Order(15)
    @DisplayName("Test BVA: Produs non-valid (inStock = max + 1)")
    public void addProductNonValidInStockBVA_2(){
        ObservableList<Part> lista = FXCollections.observableArrayList();
        try {
            service.addProduct("Produs 15", 45, 26, 10, 25, lista);
            assert false;
        } catch (ValidatorException ex) {
            assertEquals("Stocul nu poate sa fie mai mare decat stocul maxim\n", ex.getErrorMessage());
        }
    }

    @AfterEach
    void tearDown() {
    }
}