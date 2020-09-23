package application.modele;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Service implements IService {
    // utilisateurs par noEtudiant
    private static Map<String, Etudiant> utilisateurs = new HashMap<>();
    // bons plans par thème
    private static Map<String, List<BonPlan>> bonsplansParTheme = new HashMap<>();
    // bons plans par id
    private static Map<Long, BonPlan> bonsplans = new HashMap<>();

    @Override
    public Etudiant enregistrer(String noEtudiant, String nom, String prenom, LocalDate dateNaissance) throws EtudiantDejaEnregistreException {
        if (utilisateurs.containsKey(noEtudiant)) {
            throw new EtudiantDejaEnregistreException(nom);
        }
        Etudiant etudiant = new Etudiant(noEtudiant,nom,prenom,dateNaissance);
        utilisateurs.put(etudiant.getNoEtudiant(), etudiant);
        return etudiant;
    }

    @Override
    public Etudiant login(String username, String password) throws UtilisateurNonTrouveException, MauvaisPasswordException {
        if (!utilisateurs.containsKey(username)) {
            throw new UtilisateurNonTrouveException(username);
        }
        Etudiant etudiant = utilisateurs.get(username);
        if (!etudiant.getDateNaissance().toString().equals(password)) {
            throw new MauvaisPasswordException();
        }
        return etudiant;
    }

    // sans lien
    @Override
    public long enregistrerBonPlan(String noEtudiant, String theme, String description, LocalDateTime dateHeureFin) throws MauvaisBonPlanException, UtilisateurNonTrouveException {
        return enregistrerBonPlan(noEtudiant, theme, description, dateHeureFin, null);
    }
    // avec lien
    @Override
    public long enregistrerBonPlan(String noEtudiant, String theme, String description, LocalDateTime dateHeureFin, String lien) throws MauvaisBonPlanException, UtilisateurNonTrouveException {
        Objects.requireNonNull(noEtudiant);

        if (theme==null||theme.equals("")) {
            throw new MauvaisBonPlanException("pas de theme");
        }
        if (description==null||description.equals("")) {
            throw new MauvaisBonPlanException("pas de description");
        }
        if (dateHeureFin==null) {
            throw new MauvaisBonPlanException("pas de date de fin");
        }
        if (!utilisateurs.containsKey(noEtudiant)) {
            throw new UtilisateurNonTrouveException(noEtudiant);
        }
        Etudiant etudiant = utilisateurs.get(noEtudiant);

        BonPlan bonPlan = new BonPlan(theme,description,dateHeureFin, Optional.ofNullable(lien), etudiant);
        bonsplans.put(bonPlan.getId(), bonPlan);
        List<BonPlan> liste = bonsplansParTheme.get(theme);
        if (liste==null) {
            liste = new ArrayList<>();
        }
        liste.add(bonPlan);
        bonsplansParTheme.put(theme,liste);

        return bonPlan.getId();
    }

    @Override
    public void modifierLienBonPlan(String noEtudiant, long idBonPlan, String nouveauLien) throws BonPlanNonTrouveException, ModificationNonAuthoriseException {
        BonPlan bonPlan = bonsplans.get(idBonPlan);
        if (bonPlan==null) {
            throw new BonPlanNonTrouveException(idBonPlan+ " non trouvé");
        }
        if (!bonPlan.getEtudiant().getNoEtudiant().equals(noEtudiant)) {
            throw new ModificationNonAuthoriseException(noEtudiant+ " ne peut pas modifier le bon plan "+idBonPlan);
        }
        bonPlan.setLien(Optional.ofNullable(nouveauLien));
    }

    @Override
    public Set<String> listeDesThemes() {
        return bonsplansParTheme.keySet();
    }
    @Override
    public List<BonPlan> listeBonsPlansParTheme(String theme) {
        Objects.requireNonNull(theme);
        return bonsplansParTheme.get(theme);
    }
    @Override
    public List<BonPlan> listeBonsPlansParEtudiant(String noEtudiant) {
        Objects.requireNonNull(noEtudiant);
        return bonsplans.values().stream()
                .filter(bp->bp.getEtudiant().getNoEtudiant().equals(noEtudiant))
                .collect(Collectors.toList());
    }
    @Override
    public BonPlan getBonPlanParId(long id) {
        return bonsplans.get(id);
    }

    @Override
    public void supprimerBonPlan(String noEtudiant, long idBonPlan) throws BonPlanNonTrouveException, ModificationNonAuthoriseException {
        BonPlan bonPlan = bonsplans.get(idBonPlan);
        if (bonPlan==null) {
            throw new BonPlanNonTrouveException(idBonPlan+ " non trouvé");
        }
        if (!bonPlan.getEtudiant().getNoEtudiant().equals(noEtudiant)) {
            throw new ModificationNonAuthoriseException(noEtudiant+ " ne peut pas modifier le bon plan "+idBonPlan);
        }
        bonsplans.remove(idBonPlan);
        bonsplansParTheme.get(bonPlan.getTheme()).remove(bonPlan);
    }

    // les erreurs
    public class EtudiantDejaEnregistreException extends Exception {
        public EtudiantDejaEnregistreException(String noEtudiant) {
            super(noEtudiant);
        }
    }

    public class UtilisateurNonTrouveException extends Exception {
        public UtilisateurNonTrouveException(String noEtudiant) {
            super(noEtudiant);
        }
    }

    public class MauvaisBonPlanException extends Exception {
        public MauvaisBonPlanException(String erreur) {
            super(erreur);
        }
    }

    public class BonPlanNonTrouveException extends Exception {
        public BonPlanNonTrouveException(String erreur) {
            super(erreur);
        }
    }

    public class MauvaisPasswordException extends Exception {
    }

    public class ModificationNonAuthoriseException extends Exception {
        public ModificationNonAuthoriseException(String erreur) {
            super(erreur);
        }
    }


    // démo utilisation du service

    public static void main(String[] args) {
        Service service = new Service();

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

}
