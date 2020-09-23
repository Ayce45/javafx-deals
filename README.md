<h1 align="center">Bienvenue dans javafx-deals 👋</h1>
<p>
  <img alt="Version" src="https://img.shields.io/badge/version-5-blue.svg?cacheSeconds=2592000" />
  <a href="https://twitter.com/aycefr">
    <img alt="Twitter: aycefr" src="https://img.shields.io/twitter/follow/aycefr.svg?style=social" target="_blank" />
  </a>
</p>

> L’application a été développer pour un examen de javafx en 2 heures
# Le sujet

Vous devez mettre en place une application JFX pour échanger des bons plans (promos, réductions, prêt de portables, distributions gratuites, ...)
entre étudiants.

Votre application JFX devra permettre au _minimum_ de :

### A partir de la fenêtre principale :

- créer un compte personnel en fournissant son numéro d'étudiant, son nom, son prénom et sa date de naissance
- ou de se connecter avec comme login son numéro d'étudiant et comme mot de passe sa date de naissance

### Après la connexion :

- d'enregistrer un bon plan en fournissant un thème, une description, la date de fin du bon plan et
éventuellement un email de contact ou un lien web (String) ;
si c'est le premier bon plan de ce thème, ce thème est créé.
- d'afficher la liste des bons plans pour un thème choisi dans la liste des thèmes
- de modifier le lien (email ou url) dans un bon plan
(autorisé uniquement pour l'étudiant qui l'a posté).

Vous devrez vous assurer de la validité des données saisies avant d'appeler les services
(numéro d'étudiant, nom/prénom/thème de taille 2 minimum, date ...)

# Le résultat

![2020-09-23 15_13_16-Connexion](https://user-images.githubusercontent.com/32338891/94018556-08638500-fdb1-11ea-88b2-8f18d8f3d807.png)
![2020-09-23 15_15_13-Bon Plan](https://user-images.githubusercontent.com/32338891/94018582-0f8a9300-fdb1-11ea-846d-6af47df2c459.png)
![2020-09-23 15_16_46-Ajouter un bon plan](https://user-images.githubusercontent.com/32338891/94018600-13b6b080-fdb1-11ea-95fc-8a9cecd8f7ad.png)
