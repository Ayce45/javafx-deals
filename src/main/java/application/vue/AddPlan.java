package application.vue;

import application.controleur.Controleur;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import tornadofx.control.DateTimePicker;

import java.io.IOException;
import java.net.URL;

public class AddPlan {

    public JFXTextField theme;
    public JFXTextField description;
    public DateTimePicker date;
    public JFXTextField lien;
    private Controleur controleur;

    public static AddPlan creerEtAfficher(Controleur c, Stage s) {
        URL location = AddPlan.class.getResource("/vue/addPlan.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AddPlan vue = fxmlLoader.getController();
        s.setTitle("Ajouter un bon plan");
        s.setScene(new Scene(root, 620,430 ));
        s.show();
        vue.setControleur(c);
        return vue;
    }

    public void setControleur(Controleur controleur) {
        this.controleur = controleur;
    }

    public void btn_creer(ActionEvent actionEvent) {
        if (theme.getText().length() >= 2 && description.getText().length() >= 2 && date.getValue() != null) {
            controleur.enregistrerBonPlan(theme.getText(), description.getText(), date.getDateTimeValue(), lien.getText());
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR,"Les champs theme/description/date sont obligatoires et dois etre superieure a deux caractéres!", ButtonType.OK);
            a.show();
        }
    }

    public void btn_annuler(ActionEvent actionEvent) {
        controleur.goToPlan();
    }
}
