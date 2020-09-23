package application.modele;

import java.time.LocalDateTime;
import java.util.Optional;

public class BonPlan {
    private static long nextId = 1;

    private long id;
    private String theme;
    private String description;
    private LocalDateTime dateHeureFin;
    private Optional<String> lien;
    private Etudiant etudiant;

    public BonPlan(String theme, String description, LocalDateTime dateHeureFin, Optional<String> lien, Etudiant etudiant) {
        this.id = nextId++;
        this.theme = theme;
        this.description = description;
        this.dateHeureFin = dateHeureFin;
        this.lien = lien;
        this.etudiant = etudiant;
    }

    public LocalDateTime getDateHeureFin() {
        return dateHeureFin;
    }

    public void setDateHeureFin(LocalDateTime dateHeureFin) {
        this.dateHeureFin = dateHeureFin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Optional<String> getLien() {
        return lien;
    }

    public void setLien(Optional<String> lien) {
        this.lien = lien;
    }

    @Override
    public String toString() {
        return "BonPlan{" +
                "id=" + id +
                ", theme='" + theme + '\'' +
                ", description='" + description + '\'' +
                ", dateFin=" + dateHeureFin +
                ", lien=" + lien +
                ", utilisateur=" + etudiant.getNom() +
                '}';
    }

    public long getId() {
        return id;
    }
}
