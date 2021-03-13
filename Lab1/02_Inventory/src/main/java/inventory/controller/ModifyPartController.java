
package inventory.controller;

import inventory.model.InhousePart;
import inventory.model.Item;
import inventory.model.OutsourcedPart;
import inventory.model.Part;
import inventory.service.InventoryService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static inventory.controller.MainScreenController.getModifyPartIndex;


public class ModifyPartController implements Initializable, Controller {
    
    // Declare field
    private Stage stage;
    private Parent scene;
    private int partIndex= getModifyPartIndex();
    private String errorMessage = new String();
    private boolean isOutsourced;
    private int partId;

    private InventoryService service;
    
    @FXML
    private Label modifyPartDynamicLbl;
    
    @FXML
    private TextField modifyPartDynamicTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField inventoryTxt;

    @FXML
    private TextField priceTxt;

    @FXML
    private TextField maxTxt;

    @FXML
    private TextField minTxt;

    public ModifyPartController(){}

    public void setService(InventoryService service){
        this.service=service;
        fillWithData();
    }

    private void fillWithData(){
        Item part = service.getAllParts().get(partIndex);

        partId = service.getAllParts().get(partIndex).getId();
        nameTxt.setText(part.getName());
        inventoryTxt.setText(Integer.toString(part.getInStock()));
        priceTxt.setText(Double.toString(part.getPrice()));
        maxTxt.setText(Integer.toString(part.getMax()));
        minTxt.setText(Integer.toString(part.getMin()));

        if(part instanceof InhousePart) {
            modifyPartDynamicTxt.setText(Integer.toString(((InhousePart) service.getAllParts().get(partIndex)).getMachineId()));
            modifyPartDynamicLbl.setText("Machine ID");
            isOutsourced = false;
        } else {
            modifyPartDynamicTxt.setText(((OutsourcedPart) service.getAllParts().get(partIndex)).getCompanyName());
            modifyPartDynamicLbl.setText("Company Name");
            isOutsourced = true;
        }
    }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    /**
     * Method to add to button handler to switch to scene passed as source
     * @param event
     * @param source
     * @throws IOException
     */
    @FXML
    private void displayScene(ActionEvent event, String source) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(source));
        //scene = FXMLLoader.load(getClass().getResource(source));
        scene = loader.load();
        Controller ctrl = loader.getController();
        ctrl.setService(service);
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Seek user confirmation before canceling modifications and
     * switching scene to MainScreen
     * @param event
     * @throws IOException
     */
    @FXML
    void handleModifyPartCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirmation Needed");
        alert.setHeaderText("Confirm Cancellation");
        alert.setContentText("Are you sure you want to cancel modifying part " + nameTxt.getText() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            System.out.println("Ok selected. Part modification cancelled.");
            displayScene(event, "/fxml/MainScreen.fxml");
        } else {
            System.out.println("Cancel clicked. Please complete part modification.");
        }
    }

    /**
     * Validate part attributes and save modifications to chosen
     * Part object then switch scene to MainScreen
     * @param event
     * @throws IOException
     */
    @FXML
    void handleModifyPartSave(ActionEvent event) throws IOException {
        String name = nameTxt.getText();
        String price = priceTxt.getText();
        String inStock = inventoryTxt.getText();
        String min = minTxt.getText();
        String max = maxTxt.getText();
        String partDynamicValue = modifyPartDynamicTxt.getText();
        errorMessage = "";
        
        try {
            errorMessage = Part.isValidPart(name, Double.parseDouble(price), Integer.parseInt(inStock), Integer.parseInt(min), Integer.parseInt(max), errorMessage);
            if(errorMessage.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error Adding Part!");
                alert.setHeaderText("Error!");
                alert.setContentText(errorMessage);
                alert.showAndWait();
            } else {
                if(isOutsourced == true) {
                    service.updateOutsourcedPart(partIndex, partId, name, Double.parseDouble(price), Integer.parseInt(inStock), Integer.parseInt(min), Integer.parseInt(max), partDynamicValue);
                } else {
                    service.updateInhousePart(partIndex, partId, name, Double.parseDouble(price), Integer.parseInt(inStock), Integer.parseInt(min), Integer.parseInt(max), Integer.parseInt(partDynamicValue));
                }
                displayScene(event, "/fxml/MainScreen.fxml");
            }

        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error Adding Part!");
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

}
