package application.vue;

import application.controleur.Controleur;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Login {
    public JFXTextField pseudo;
    public JFXPasswordField password;

    private Controleur controleur;

    public static Login creerEtAfficher(Controleur c, Stage s) {
        URL location = Login.class.getResource("/vue/login.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Login vue = fxmlLoader.getController();
        s.setTitle("Connexion");
        s.setScene(new Scene(root, 427,255 ));
        s.show();
        vue.setControleur(c);
        return vue;
    }

    public void setControleur(Controleur controleur) {
        this.controleur = controleur;
    }

    public void btn_ok(ActionEvent actionEvent) {
        if (pseudo.getText().length() <= 2 || password.getText().length() <= 2) {
            Alert a = new Alert(Alert.AlertType.ERROR,"Les champs login/password sont obligatoires et dois etre superieure a deux caractéres!", ButtonType.OK);
            a.show();
        } else {
            controleur.connexion(pseudo.getText(), password.getText());
        }
    }

    public void btn_register(ActionEvent actionEvent) {
        controleur.goToRegister();
    }
}
