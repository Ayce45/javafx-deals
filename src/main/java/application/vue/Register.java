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

import java.io.IOException;
import java.net.URL;

public class Register {
    public DatePicker date;
    public JFXTextField numero;
    public JFXTextField nom;
    public JFXTextField prenom;

    private Controleur controleur;

    public static Register creerEtAfficher(Controleur c, Stage s) {
        URL location = Register.class.getResource("/vue/register.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Register vue = fxmlLoader.getController();
        s.setTitle("Inscription");
        s.setScene(new Scene(root, 620,430 ));
        s.show();
        vue.setControleur(c);
        return vue;
    }

    public void setControleur(Controleur controleur) {
        this.controleur = controleur;
    }

    public boolean isNumero(String numero) {
        return numero.matches("o[0-9][0-9][0-9][0-9][0-9][0-9][0-9]");
    }

    public void btn_creer(ActionEvent actionEvent) {
        if (numero.getText().length() >= 2 && nom.getText().length() >= 2 && prenom.getText().length() >= 2 && date.getValue() != null) {
            if (isNumero(numero.getText())) {
                controleur.inscription(numero.getText(), nom.getText(), prenom.getText(), date.getValue());
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR,"Le numéro etudiant dois etre sous la forme oxxxxxxxx et x sont des chiffres", ButtonType.OK);
                a.show();
            }
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR,"Les champs numero/nom/prenom/date de naissance sont obligatoires! et dois etre supperieur a 2 caractere", ButtonType.OK);
            a.show();
        }
    }

    public void btn_annuler(ActionEvent actionEvent) {
        controleur.goToLogin();
    }
}
