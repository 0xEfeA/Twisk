# Twisk
Projet universitaire de simulation à événements discrets combinant une interface JavaFX et un moteur natif en C.

## Présentation
> Twisk permet de construire graphiquement un système de files d’attente (activités, guichets, transitions) puis de générer et d’exécuter automatiquement une simulation native en C, visualisée en temps réel dans l’interface Java.

## Fonctionnalités
- Éditeur graphique pour créer un monde (activités, guichets, entrées, sorties)
- Définition et gestion des transitions entre étapes
- Génération automatique du code C correspondant au monde
- Simulation multiprocessus en C (un processus par client)
- Utilisation de sémaphores POSIX pour la gestion des guichets
- Visualisation en temps réel de l’évolution des clients
- Architecture Observateur / Observable assurant la synchronisation

## Technologies
- Java 17
- Jni
- JavaFX
- C 
- Librairies dynamiques

## Exécution
    1. Lancer l’interface graphique
    2. Construire le monde (activités, guichets, transitions) via l'UI
    3. Lancer la simulation et observer les déplacements des clients
