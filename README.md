# Projet_Technologique
Projet technologique du S6.

# Protocole
Voir [ici](http://norips.github.io/slate) ou [ici](http://raphael.druon.emi.u-bordeaux1.fr/ProjetTechno/slate/index.html) pour la documentation du protocole.

Voir [ici](http://github.com/norips/slate) pour les sources de la documentation.

# Logiciel

## BusAPI

Une API permettant d'interroger le bus. A installer en premier

Pour ce faire :

> `mvn install`

## Interface
L'interface permettant la visualisation des capteurs se trouve dans le dossier `GUI`
Pour compiler faire:
> `cd BusAPI && mvn install && cd ../GUI && mvn install`

Puis pour executer:
> `mvn exec:java`

## Bus

Le bus est simulé par les sources situé dans `Bus`.

Pour compiler faire:

> `cd Bus && mvn install`

Pour l'executer :

> `mvn exec:java`

## DriverGyro

Le driver gyroscope, permet de relier l'applicaton Arduino avec le bus, pour le demarrer ouvrez le projet dans eclipse.
N'oubliez pas d'indiquer le chemin de la bibliotheque rxtxSerial
