# Système de Gestion de Réservations de Salles

Ce projet est une application basée sur une architecture microservices pour gérer les réservations de salles. Il inclut des services pour la gestion des salles, des utilisateurs, des réservations, une passerelle API, un serveur de découverte (Eureka), et un système d'authentification.

## Table des matières
1. [Architecture du Projet](#architecture-du-projet)
2. [Services Principaux](#services-principaux)
    - [Service Salles](#service-salles)
    - [Service Réservations](#service-réservations)
    - [Service Utilisateurs](#service-utilisateurs)
3. [API Gateway](#api-gateway)
4. [Eureka Server](#eureka-server)
5. [Authentification](#authentification)
6. [Démarrage](#démarrage)
7. [Endpoints](#endpoints)
8. [Technologies Utilisées](#technologies-utilisées)

---

## Architecture du Projet
L'application est basée sur une architecture microservices composée des éléments suivants :
- **Service Salles** : Gère les salles et leurs détails.
- **Service Réservations** : Gère les réservations liées aux salles.
- **Service Utilisateurs** : Gère les utilisateurs et leurs rôles.
- **API Gateway** : Fournit un point d'entrée unique pour accéder aux microservices.
- **Eureka Server** : Permet la découverte et l'enregistrement des services.
- **Service d'Authentification** : Gère la connexion et la sécurité des utilisateurs.

---

## Services Principaux

### Service Salles
Responsable de la gestion des salles.
- **Port** : 8082
- **Base de données** : cc_salledb
- **Endpoints principaux** :
    - `POST /api/salle/add` : Ajouter une nouvelle salle.
    - `GET /api/salle/{id}` : Obtenir les détails d'une salle par ID.
    - `GET /api/salle/salles` : Liste de toutes les salles.
    - `PUT /api/salle/{id}` : Mettre à jour une salle.
    - `DELETE /api/salle/{id}` : Supprimer une salle.
    - `GET /api/salle/SalleReservation/{id}` :  Obtenir une salle et ses réservations.

---

### Service Réservations
Gère les réservations des salles.
- **Port** : 8081
- **Base de données** : cc_reservationdb
- **Endpoints principaux** :
    - `POST /api/reservation/add` : Ajouter une nouvelle réservation.
    - `GET /api/reservation/{id}` : Obtenir les détails d'une réservation par ID.
    - `GET /api/reservation/reservations` : Liste de toutes les réservations.
    - `PUT /api/reservation/{id}` : Mettre à jour une réservation.
    - `DELETE /api/reservation/{id}` : Supprimer une réservation.
    - `GET /api/reservation/reservationbyidsalle/{id}` : Obtenir les réservations par salle.
    - `GET /api/reservation/reservationbyiduser/{id}` : Obtenir les réservations par utilisateur.

---

### Service Utilisateurs
Gère les informations des utilisateurs et leur rôle.
- **Port** : 8083
- **Base de données** : cc_userdb
- **Endpoints principaux** :
    - `POST /api/utilisateur/add` : Ajouter un nouvel utilisateur.
    - `GET /api/utilisateur/{id}` : Obtenir les détails d'un utilisateur par ID.
    - `GET /api/utilisateur/utilisateurs` : Liste de tous les utilisateurs.
    - `PUT /api/utilisateur/{id}` : Mettre à jour un utilisateur.
    - `DELETE /api/utilisateur/{id}` : Supprimer un utilisateur.
    - `GET /api/utilisateur/userReservation/{id}` : Obtenir un utilisateur et ses réservations.

---

## API Gateway
L'API Gateway agit comme point d'entrée unique pour tous les services. Il offre les fonctionnalités suivantes :
- **Port**: 8762
- Routage intelligent des requêtes.
- Agrégation des données entre plusieurs services.
- Gestion centralisée des erreurs et de la sécurité.

---

## Eureka Server
Le serveur Eureka fournit la découverte des services, permettant une communication dynamique entre les microservices. Chaque service s'enregistre auprès d'Eureka pour être accessible par les autres services.

---

## Authentification
Un système de sécurité basé sur **JWT (JSON Web Token)** est utilisé pour authentifier et autoriser les utilisateurs.
- **Port** : 8095
- **Base de données** : cc_authdb
- **Endpoints principaux** :
    - `POST /api/auth/register` : Inscription d'un utilisateur.
    - `POST /api/auth/authenticate` : Connexion et obtention du token JWT.
    - Middleware pour valider le token avant de permettre l'accès aux services sécurisés.

## Exchange Rate API
- **Description** : Intégré comme service tiers, il fournit les taux de change en temps réel pour diverses devises.
- **Utilisation** : Ce service peut être utilisé pour convertir des valeurs monétaires dans différents contextes d'utilisation, notamment pour des utilisateurs internationaux.

---

## Démarrage
### Prérequis
- Java 17+
- Maven 3.8+
- PostgreSQL ou MySQL pour les bases de données des services.

### Étapes
1. Clonez le projet :
   ```bash
   git clone https://github.com/votre-repo.git
   cd votre-repo
   ```
2. Lancez le serveur Eureka 
3. Lancez chaque service individuellement
4. Lancez l'API Gateway
5. Accédez aux endpoints via l'API Gateway (exemple : `http://localhost:8762/salle/salles`).

---

## Endpoints
| Service          | Endpoint                            | Description                      |
|------------------|-------------------------------------|----------------------------------|
| Salle            | `GET /api/salle/salles`             | Liste des salles                |
| Réservation      | `GET /api/reservation/reservations` | Liste des réservations          |
| Utilisateur      | `GET /api/utilisateur/utilisateurs` | Liste des utilisateurs          |
| Authentification | `POST /api/auth/authenticate`       | Connexion                       |
| Register         | `POST /api/auth/register`           | Connexion                       |

---

## Technologies Utilisées
- **Spring Boot** : Framework principal.
- **Spring Cloud Gateway** : API Gateway.
- **Spring Cloud Eureka** : Découverte des services.
- **JWT** : Authentification et sécurité.
- **Lombok** : Réduction du code boilerplate.
- **PostgreSQL/MySQL** : Base de données relationnelle.
- **React** : Frontend (optionnel).
- **Exchange Rate API** : Tiers pour les taux de change.

---
