/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp1.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import tp1.controllers.jpa_controllers.exceptions.NonexistentEntityException;
import tp1.dao.personDao;
import tp1.helpers.dialogs;
import tp1.models.database_models.Person;

/**
 *
 * @author drissa
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    TextField nom;
    @FXML
    TextField prenom;
    @FXML
    Button enregistrer;
    @FXML
    Button modifier;
    @FXML
    Button annuler_mod;
    @FXML
    Button supprimer;
    @FXML
    TableView<Person> table;
    @FXML
    TableColumn<Person, String> nom_col;
    @FXML
    TableColumn<Person, String> prenom_col;
    
    personDao personDao = new personDao();
    
    Boolean isInModification = false;
    int personneInModificationId;
    ObservableList<Person> data = FXCollections.observableArrayList();

    @FXML
    private void enregistrer() throws IOException {
        if (!"".equals(nom.getText()) && !"".equals(prenom.getText())) {
            if(isInModification){
                data.stream().filter((personne) -> (personne.getId() == personneInModificationId)).map((personne) -> {
                    personne.setLastName(nom.getText());
                    personne.setFirstName(prenom.getText());
                    try {
                        personDao.modifyPerson(personne);
                    } catch (Exception ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return personne;
                }).forEach((personne) -> {
                    personne.setFirstName(prenom.getText());
                });
                isInModification = false;
                table.setItems(data);
                table.refresh();
                annuler_mod.setVisible(false);
                Alert info_box = dialogs.information("information", "Modification", "Person modified with success!");
                info_box.showAndWait();
            }else{
                Person p = new Person(nom.getText(), prenom.getText());
                personDao.addPerson(p);
                data.add(p);
                table.getItems().add(p);
                Alert info_box = dialogs.information("information", "Registration", "Person save with success!");
                info_box.showAndWait();
            }
            nom.setText("");
            prenom.setText("");
        }
    }
    @FXML
    private void modifier() throws IOException {
        Person selectedPersonne = table.getSelectionModel().getSelectedItem();
        if (selectedPersonne != null) {
            annuler_mod.setVisible(true);
            isInModification = true;
            nom.setText(selectedPersonne.getLastName());
            prenom.setText(selectedPersonne.getFirstName());
            personneInModificationId = selectedPersonne.getId();
        }
    }
    @FXML
    private void annuler_modification() throws IOException {
        isInModification = false;
        nom.setText("");
        prenom.setText("");
        annuler_mod.setVisible(false);
    }
    @FXML
    private void supprimer() throws IOException, NonexistentEntityException {
        Person selectedPersonne = table.getSelectionModel().getSelectedItem();
        if (selectedPersonne != null && !isInModification) {
            Alert confirm_box = dialogs.confirmation("confirmation", "Registration", "Etes vous sure de vouloir supprimer "+selectedPersonne.toHuman());
            Optional<ButtonType> result = confirm_box.showAndWait();
            if (result.get() == ButtonType.OK){
                personDao.deletePerson(selectedPersonne);
                data.remove(selectedPersonne);
                table.getItems().remove(selectedPersonne);
                Alert info_box = dialogs.information("information", "Suppression", "Suppression de "+selectedPersonne.toHuman());
                info_box.showAndWait();
            }
        }else{
            Alert war_box = dialogs.warning("warninig", "Avertissement", "vous etes en mode modification");
            war_box.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            for (Person p : personDao.getAllPerson()) {
                data.add(p);
            }
        } catch (Exception ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        nom_col.setCellValueFactory(new PropertyValueFactory<Person, String>("nom"));
        prenom_col.setCellValueFactory(new PropertyValueFactory<Person, String>("prenom"));
        table.setItems(data);
    } 
    
}
