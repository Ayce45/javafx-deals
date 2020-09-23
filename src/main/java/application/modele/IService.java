package application.modele;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface IService {
    /**
     * Enregistre un nouveau compte étudiant
     * @param noEtudiant    numéro de l'étudiant
     * @param nom           nom de l'étudiant
     * @param prenom        prénom de l'étudiant
     * @param dateNaissance date de naissance de l'étudiant
     * @return l'objet Etudiant enregistré
     * @throws Service.EtudiantDejaEnregistreException si ce numéro d'étudiant est déjà enregistré
     */
    Etudiant enregistrer(String noEtudiant, String nom, String prenom, LocalDate dateNaissance) throws Service.EtudiantDejaEnregistreException;

    /**
     * Connection d'un étudiant enregistré
     * @param username  le numéro d'étudiant
     * @param password  la date de naissance
     * @return  l'objet Etudiant
     * @throws Service.UtilisateurNonTrouveException si le numéro d'étudiant n'est pas enregistré
     * @throws Service.MauvaisPasswordException si le mot de passe ne correspond pas
     */
    Etudiant login(String username, String password) throws Service.UtilisateurNonTrouveException, Service.MauvaisPasswordException;

    /**
     * Enregistre un nouveau bon plan sans lien
     * @param noEtudiant    le no de l'étudiant connecté
     * @param theme         le nom du thème de ce bon plan
     * @param description   la description du bon plan
     * @param dateHeureFin  la date/heure de fin de ce bon plan
     * @return  l'ID du bon plan enregistré
     * @throws Service.MauvaisBonPlanException  si les données sont mauvaises
     * @throws Service.UtilisateurNonTrouveException si l'étudiant n'est pas enregistré
     */
    long enregistrerBonPlan(String noEtudiant, String theme, String description, LocalDateTime dateHeureFin) throws Service.MauvaisBonPlanException, Service.UtilisateurNonTrouveException;

    /**
     * Enregistre un nouveau bon plan avec un lien
     * @param noEtudiant    le no de l'étudiant connecté
     * @param theme         le nom du thème de ce bon plan
     * @param description   la description du bon plan
     * @param dateHeureFin  la date/heure de fin de ce bon plan
     * @param lien          lien web ou email pour profiter du bon plan
     * @return  l'ID du bon plan enregistré
     * @throws Service.MauvaisBonPlanException  si les données sont mauvaises
     * @throws Service.UtilisateurNonTrouveException si l'étudiant n'est pas enregistré
     */
    long enregistrerBonPlan(String noEtudiant, String theme, String description, LocalDateTime dateHeureFin, String lien) throws Service.MauvaisBonPlanException, Service.UtilisateurNonTrouveException;

    /**
     * Modifie le lien d'un bon plan déjà enregistré
     * @param noEtudiant    le no de l'étudiant connecté
     * @param idBonPlan     l'id du bon plan à modifier
     * @param nouveauLien   le nouveau lien (web,email) de ce bon plan
     * @throws Service.BonPlanNonTrouveException si l'id du bon plan n'existe pas
     * @throws Service.ModificationNonAuthoriseException si l'étudiant qui veut modifier n'est pas celui qui a enregistré le bon plan
     */
    void modifierLienBonPlan(String noEtudiant, long idBonPlan, String nouveauLien) throws Service.BonPlanNonTrouveException, Service.ModificationNonAuthoriseException;

    /**
     * Renvoie les thèmes des bons plans enregistrés
     * @return Set des noms des thèmes
     */
    Set<String> listeDesThemes();

    /**
     * Renvoie la liste des bons plans enregistrés pour un thème
     * @param theme le thème des bons plans
     * @return la liste des bons plans de ce thème
     */
    List<BonPlan> listeBonsPlansParTheme(String theme);

    /**
     * Renvoie la liste des bons plans enregistrés par l'étudiant connecté
     * @param noEtudiant le no de l'étudiant connecté
     * @return la liste de ses bons plans
     */
    List<BonPlan> listeBonsPlansParEtudiant(String noEtudiant);

    /**
     * Renvoie un bon plan par son id
     * @param id l'id du bon plan demandé
     * @return le bon plan correspondant ou null si cet id n'existe pas
     */
    BonPlan getBonPlanParId(long id);

    /**
     * Supprime un bon plan
     * @param noEtudiant    le no de l'étudiant connecté
     * @param idBonPlan     l'id du bon plan à modifier
     * @throws Service.BonPlanNonTrouveException si l'id du bon plan n'existe pas
     * @throws Service.ModificationNonAuthoriseException si l'étudiant qui veut modifier n'est pas celui qui a enregistré le bon plan
     */
    void supprimerBonPlan(String noEtudiant, long idBonPlan) throws Service.BonPlanNonTrouveException, Service.ModificationNonAuthoriseException;;
}
