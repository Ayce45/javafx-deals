package application.vue;

import application.controleur.Controleur;
import application.jfxproperty.PlanJfx;
import application.modele.Etudiant;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Set;

public class Plan {
    public Label numero;
    public JFXComboBox theme;
    public TableView plans;
    private Controleur controleur;

    public static Plan creerEtAfficher(Controleur c, Stage s) {
        URL location = Plan.class.getResource("/vue/plan.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Parent root = null;
        try {
            root = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Plan vue = fxmlLoader.getController();
        s.setTitle("Bon Plan");
        s.setScene(new Scene(root, 620,490 ));
        s.show();
        vue.setControleur(c);
        return vue;
    }

    public void setControleur(Controleur controleur) {
        this.controleur = controleur;
    }


    public void btn_cree(ActionEvent actionEvent) {
        controleur.goToAddPlan();
    }

    public void initData(Etudiant etudiant, Set<String> listeDesThemes) {
        numero.setText(etudiant.getNoEtudiant());
        theme.setItems(FXCollections.observableArrayList(listeDesThemes.toArray()));
    }

    public void initTable(String theme) {

        plans.getColumns().clear();

        ObservableList<PlanJfx> data = FXCollections.observableArrayList(controleur.getPlans(theme));
        plans.itemsProperty().setValue(data);
        TableColumn<PlanJfx, String> descriptionColumn = new TableColumn<>("Description");
        TableColumn<PlanJfx, String> dateColumn = new TableColumn<>("Date de fin de validité");
        TableColumn<PlanJfx, String> lienColumn = new TableColumn<>("Lien / email de contact");
        TableColumn<PlanJfx, String> userColumn = new TableColumn<>("Utilisateur");

        descriptionColumn.setCellValueFactory(e->e.getValue().descriptionProperty());
        dateColumn.setCellValueFactory(e->e.getValue().dateProperty());
        lienColumn.setCellValueFactory(e->e.getValue().lienProperty());
        userColumn.setCellValueFactory(e->e.getValue().usernameProperty());
        plans.getColumns().addAll(descriptionColumn, dateColumn, lienColumn, userColumn);

        plans.setOnMousePressed(event -> {
                    PlanJfx plan=null;
                    if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                        Node node = ((Node) event.getTarget()).getParent();
                        TableRow<PlanJfx> row=null;
                        if (node instanceof TableRow) {
                            row = (TableRow<PlanJfx>) node;
                        } else {
                            if (node.getParent() instanceof TableRow) {
                                // clicking on text part
                                row = (TableRow<PlanJfx>) node.getParent();
                            }
                        }
                        plan = row.getItem();
                        if (plan != null) {
                            try {
                                URI link;
                                if (plan.getLien().contains("@")) {
                                    link = new URI("mailto:" + plan.getLien() +"?subject=" + plan.getDescription());
                                } else {
                                    link = new URI(plan.getLien());
                                }
                                Desktop.getDesktop().browse(link);
                            } catch (IOException | URISyntaxException e) {
                                Alert a = new Alert(Alert.AlertType.ERROR,"Le lien ou l'adresse mail est incorrect", ButtonType.OK);
                                a.show();
                            }
                        }
                    }
                }
        );

        plans.setRowFactory(new Callback<TableView<PlanJfx>, TableRow<PlanJfx>>() {
            @Override
            public TableRow<PlanJfx> call(TableView<PlanJfx> tableView) {
                final TableRow<PlanJfx> row = new TableRow<>();
                final ContextMenu contextMenu = new ContextMenu();
                final MenuItem editMenuItem = new MenuItem("Modifier");
                final MenuItem removeMenuItem = new MenuItem("Supprimer");
                editMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                    PlanJfx plan=null;
                    @Override
                    public void handle(ActionEvent event) {
                        plan = row.getItem();
                        if (plan.getUsername().equals(controleur.getEtudiant().getNoEtudiant())) {
                            controleur.goToUpdatePlan(plan);
                        } else {
                            Alert a = new Alert(Alert.AlertType.ERROR,"Ce bon plan ne vous appartient pas", ButtonType.OK);
                            a.show();
                        }
                    }
                });
                removeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                    PlanJfx plan=null;
                    @Override
                    public void handle(ActionEvent event) {
                        plan = row.getItem();
                        if (plan.getUsername().equals(controleur.getEtudiant().getNoEtudiant())) {
                            controleur.supprimerBonPlan(plan);
                            plans.getItems().remove(row.getItem());
                        } else {
                            Alert a = new Alert(Alert.AlertType.ERROR,"Ce bon plan ne vous appartient pas", ButtonType.OK);
                            a.show();
                        }
                    }
                });
                contextMenu.getItems().addAll(editMenuItem, removeMenuItem);
                // Set context menu on row, but use a binding to make it only show for non-empty rows:
                row.contextMenuProperty().bind(
                        Bindings.when(row.emptyProperty())
                                .then((ContextMenu)null)
                                .otherwise(contextMenu)
                );
                return row ;
            }
        });

    }

    public void select_theme(ActionEvent actionEvent) {
        initTable(theme.getSelectionModel().getSelectedItem().toString());
    }

    public void btn_deco(ActionEvent actionEvent) {
        controleur.deconnexion();
    }
}
