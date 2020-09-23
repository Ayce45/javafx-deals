package application.modele;


import java.time.LocalDate;

public class Etudiant {
    private String noEtudiant;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;

    public Etudiant(String noEtudiant, String nom, String prenom, LocalDate dateNaissance) {
        this.noEtudiant = noEtudiant;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getNoEtudiant() {
        return noEtudiant;
    }

    public void setNoEtudiant(String noEtudiant) {
        this.noEtudiant = noEtudiant;
    }

    @Override
    public String toString() {
        return "Etudiant{" +
                "noEtudiant='" + noEtudiant + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", dateNaissance=" + dateNaissance +
                '}';
    }
}
