package application.vue;

import application.controleur.Controleur;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import tornadofx.control.DateTimePicker;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UpdatePlan {
    public long id;
    public JFXTextField theme;
    public JFXTextField description;
    public DateTimePicker date;
    public JFXTextField lien;
    public CheckBox checkbox;
    private Controleur controleur;

    public static UpdatePlan creerEtAfficher(Controleur c, Stage s) {
        URL location = UpdatePlan.class.getResource("/vue/updatePlan.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        UpdatePlan vue = fxmlLoader.getController();
        s.setTitle("Ajouter un bon plan");
        s.setScene(new Scene(root, 620,430 ));
        s.show();
        vue.setControleur(c);
        return vue;
    }

    public void setControleur(Controleur controleur) {
        this.controleur = controleur;
    }

    public void initData(String dataTheme, String dataDescription, String dataDate, String dataLien, long dataId) {
        theme.setText(dataTheme);
        description.setText(dataDescription);
        date.setDateTimeValue(LocalDateTime.parse(dataDate, DateTimeFormatter.ofPattern("dd-MM-yy HH:mm")));
        lien.setText(dataLien);
        id = dataId;
    }

    public void btn_modif(ActionEvent actionEvent) {
        controleur.modifierLienBonPlan(id, lien.getText());
    }

    public void btn_annuler(ActionEvent actionEvent) {
        controleur.goToPlan();
    }
}
