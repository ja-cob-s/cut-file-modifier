/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cutfilemodifier;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Jacob Schaible
 */
public class MainScreenController implements Initializable {

    @FXML
    private Button addButton;
    @FXML
    private Label label;
    @FXML
    private Button exitButton;
    @FXML
    private Button runButton;
    @FXML
    private Button removeButton;
    @FXML
    private TableView<File> fileTable;
    @FXML
    private TableColumn<File, String> fileColumn;
    
    private ObservableList<File> files;
    private FileModifier fileModifier;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fileModifier = new FileModifier();
        files = FXCollections.observableArrayList();
        //files.add(new File("frame2.UC"));
        this.populateTable();
    }    
    
    public void populateTable() {
        // Populates the table view with the file names
        fileColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        fileTable.setItems(files);
    }

    @FXML
    private void addButtonHandler(ActionEvent event) {
        // Can add multiple files at once
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        List<File> list = fileChooser.showOpenMultipleDialog(stage);
        if (list != null) {
            for (File file : list) {
               files.add(file); 
            }
        }
        this.populateTable();
    }

    @FXML
    private void exitButtonHandler(ActionEvent event) {
        // Exits the program with a confirmation dialog
        if (this.showConfirmDialog("Are you sure you want to exit?")) {
            System.exit(0);
        }
    }

    @FXML
    private void runButtonHandler(ActionEvent event) {
        // Loops through each file in the list
        for (int i = 0; i < files.size(); i++) {
            String oldFileString = "";
            // Reads the file and assigns to a string
            oldFileString = fileModifier.readFile(files.get(i));
            // Checks if string already exists and skips this file if so
            if (fileModifier.stringExists(oldFileString)) {
                this.showAlertDialog("File '" + files.get(i).getName() + 
                        "' already contains '" + fileModifier.getAddition() + "'.");
                continue;
            }
            // Makes the replacement
            String newFileString = fileModifier.stringReplace(oldFileString);
            // Saves (overwrites) the file
            fileModifier.writeFile(files.get(i), newFileString);
        }
        // Friendly dialog box to inform user that the replace is complete
        this.showAlertDialog("Done!");
    }

    @FXML
    private void removeButtonHandler(ActionEvent event) {
        // Removes a file from the list of files to be modified
        // with a confirmation dialog first
        if (fileTable.getSelectionModel().getSelectedItem() != null) {
            if (this.showConfirmDialog("Are you sure you want to remove this file?")){
                // ... user chose OK
                File file = fileTable.getSelectionModel().getSelectedItem();
                files.remove(file);
            } else {
                // ... user chose CANCEL or closed the dialog
            }    
        }
    }
    
    public boolean showConfirmDialog(String s) {
        // Generic confirmation dialog with settable message
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText(null);
        alert.setContentText(s);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            return true;
        } else {
            return false;
        }
    }
    
    public void showAlertDialog(String s) {
        // Generic alert dialog with settable message
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(s);

        alert.showAndWait();
    }
}
