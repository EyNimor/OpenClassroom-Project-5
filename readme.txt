
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

  --------------
  Fonctionnement
  --------------

  L'application est divisée en quatre partie principale :
   - Persons, accessible sur le Port 9090 ;
   - Firestations, accesible sur le Port 9060 ;
   - MedicalRecords, accessible sur le Port 9030 ;
   - URLsMultiInfo, accessible sur le Port 9000 ;
  Chacune de ces parties sont des Microservices indépendants les uns des autres, et chacun ont des fonctions spécifiques, à des
  URLs spécifiques. Pour pouvoir effectuer une requête, le microservice correspondant à la requête doit être lancé.
  Pour cela, lancez un terminal de commande dans le dossier correspondant ( safetynet-alerts-endpoint-persons pour la
  partie Persons par exemple ) et faites la commande "mvn spring-boot:run". Le terminal doit rester ouvert pour garder
  le microservice lancé. Une liste des requêtes et URLs disponibles pour chaque microservices est fourni dans le fichier urls.txt.
   