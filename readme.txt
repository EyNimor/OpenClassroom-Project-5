
			SafetyNet Alerts'

  ---------------------
  Qu'est-ce que c'est ?
  ---------------------

  SafetyNet Alerts' est une API permettant l'envoie d'informations aux services d'urgence, notamment les 
  informations personnelles et médicales des personnes habitant une ville, ou un état par exemple. L'API
  peut également enregistrer les informations de nouvelles personne, ainsi que les numéros de caserne de
  pompier et les adresses qu'elles couvrent.

  --------------------------------
  Configuration système nécessaire
  --------------------------------

  JDK:
   1.8 ou plus ;
  Maven:
   Apache Maven 3.8.1 et plus - le bon fonctionnement de l'application n'est pas garantie avec une version
   inférieur de Maven ;
  Espace Disque:
   Environ 10Mo pour l'application elle-même - Maven et le JDK prendront également de l'espace disque, se
   référer à la documentation Maven et Java pour plus d'informations;

  ------------
  Installation
  ------------

  1 - Télécharger le code via le bouton "Code", puis "Download ZIP" sur le Repository GitHub. Une Archive .zip
  devrait se télécharger ;

  2 - Une fois l'archive téléchargé, décompressez-là à l'endroit que vous voulez. Un dossier devrait y apparaître.

  3 - Allez dans le dossier en question, puis dans le dossier "safetynet-alerts-model". À l'intérieur, ouvrez un
  terminal Windows, puis entrez la commande "mvn clean install".