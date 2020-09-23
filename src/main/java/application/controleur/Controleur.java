package application.controleur;

import application.jfxproperty.PlanJfx;
import application.modele.Etudiant;
import application.modele.Service;
import application.vue.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Controleur {
    private  Login login;
    private Stage stage;
    private Service service;
    private Etudiant etudiant;
    private Plan plan;

    public Controleur(Stage stage) {
        this.stage = stage;
        this.service = new Service();
        this.login = Login.creerEtAfficher(this,stage);
        initTest();
    }

    private void initTest() {
        try {
            // enregistrement d'un nouvel étudiant
            service.enregistrer("o0123456","yo", "yo", LocalDate.of(2010,02,02));
            // connexion de cet étudiant etu1
            Etudiant etu1 = service.login("o0123456","2010-02-02");
            // enregistrement d'un bon plan par etu1
            service.enregistrerBonPlan(
                    etu1.getNoEtudiant(),
                    "jeux",
                    "Jeu Grand Theft Auto V (GTA 5) - Premium Édition sur PC GRATUIT",
                    LocalDateTime.of(2020,05,21,17,00),
                    "https://www.epicgames.com/store/fr/free-games"
            );
            // enregistrement d'un nouvel étudiant
            service.enregistrer("o1234567","fred", "fred", LocalDate.of(2000,01,01));
            // connexion du deuxième étudiant etu2
            Etudiant etu2 = service.login("o1234567","2000-01-01");
            // enregistrement de bons plans sans lien par etu2
            long idBonPlan = service.enregistrerBonPlan(
                    etu2.getNoEtudiant(),
                    "jeux",
                    "Sid Meier's Civilization VI Gratuit sur PC",
                    LocalDateTime.of(2020,05,28,17,00)
            );
            service.enregistrerBonPlan(
                    etu2.getNoEtudiant(),
                    "vacances",
                    "INTEX Licorne gonflable - 198x140x97cm - 12,34€",
                    LocalDateTime.of(2020,06,27,12,00),
                    "https://www.amazon.fr/Intex-Licorne-Gonflable-Multicolore-201x140x97cm/dp/B0756ZN4J7"
            );
            // affichage de la liste de tous les bons plans par theme
            service.listeDesThemes().forEach( theme -> {
                System.out.println("+ theme " + theme + " :");
                service.listeBonsPlansParTheme(theme).forEach(System.out::println);
            });
            // modification du lien sur un bon plan de etu2
            service.modifierLienBonPlan(etu2.getNoEtudiant(), idBonPlan,"https://www.epicgames.com/store/fr/product/sid-meiers-civilization-vi/home");
            // affichage des bons plans (avec la mise à jour) de l'étudiant etu2
            System.out.println("+ après mise à jour, bons plans de "+etu2.getNom());
            service.listeBonsPlansParEtudiant(etu2.getNoEtudiant()).forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goToRegister() {
        Register register = Register.creerEtAfficher(this,stage);

    }

    public void connexion(String username, String password) {
        try {
            etudiant = service.login(username, password);
            goToPlan();
        } catch (Service.UtilisateurNonTrouveException | Service.MauvaisPasswordException e) {
            Alert a = new Alert(Alert.AlertType.ERROR,"Le nom d'utilisateur ou/et le mot de passe est incorrect !", ButtonType.OK);
            a.show();
        }
    }

    public void inscription(String numero, String nom, String prenom, LocalDate date) {
        try {
            etudiant = service.enregistrer(numero,nom,prenom,date);
            goToPlan();
        } catch (Service.EtudiantDejaEnregistreException e) {
            Alert a = new Alert(Alert.AlertType.ERROR,"Les l'étudiant est déjà enregisté !", ButtonType.OK);
            a.show();
        }
    }

    public List<PlanJfx> getPlans(String theme){
        List<PlanJfx> attestationJfxes = new ArrayList<>();
        List<application.modele.BonPlan> lesBonPlan = service.listeBonsPlansParTheme(theme);
        for (application.modele.BonPlan a : lesBonPlan){
            PlanJfx at = new PlanJfx(a.getDescription(), a.getDateHeureFin(), a.getLien(), a.getEtudiant().getNoEtudiant(), a.getId(), a.getTheme());
            attestationJfxes.add(at);
        }
        return attestationJfxes;
    }

    public void enregistrerBonPlan(String theme, String description, LocalDateTime date, String lien) {
        try {
            if (lien.isBlank()) {
                service.enregistrerBonPlan(etudiant.getNoEtudiant(), theme, description, date);
            } else {
                service.enregistrerBonPlan(etudiant.getNoEtudiant(), theme, description, date, lien);
            }
            goToPlan();
        } catch (Service.MauvaisBonPlanException | Service.UtilisateurNonTrouveException e) {
            e.printStackTrace();
        }
    }

    public void goToPlan() {
        plan = Plan.creerEtAfficher(this,stage);
        plan.initData(etudiant, service.listeDesThemes());

    }

    public void goToAddPlan() {
        AddPlan.creerEtAfficher(this,stage);
    }

    public void goToUpdatePlan(PlanJfx dataPlan) {
        UpdatePlan updatePlan = UpdatePlan.creerEtAfficher(this,stage);
        updatePlan.initData(dataPlan.getTheme(), dataPlan.getDescription(), dataPlan.getDate(), dataPlan.getLien(), dataPlan.getId());
    }

    public void modifierLienBonPlan(long id, String lien) {
        try {
            service.modifierLienBonPlan(etudiant.getNoEtudiant(), id, lien);
            goToPlan();
        } catch (Service.BonPlanNonTrouveException | Service.ModificationNonAuthoriseException e) {
            e.printStackTrace();
        }
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void goToLogin() {
        Login.creerEtAfficher(this,stage);
    }

    public void deconnexion() {
        etudiant = null;
        goToLogin();
    }

    public void supprimerBonPlan(PlanJfx plan) {
        long id = plan.getId();
        try {
            service.supprimerBonPlan(etudiant.getNoEtudiant(), id);
        } catch (Service.BonPlanNonTrouveException | Service.ModificationNonAuthoriseException e) {
            e.printStackTrace();
        }
    }
}
