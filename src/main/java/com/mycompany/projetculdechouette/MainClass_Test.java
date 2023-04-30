/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projetculdechouette;

import CulDeChouetteDAO.*;
import POJO.*;
import java.util.Date;

/**
 *
 * @author Arnaud
 */
public class MainClass_Test {
     public static void main(String[] args){
         try {
             System.out.println("Classe de test pour les DAO");
             CulDeChouetteDAOFactory factory = AbstractDAOFactory.getDAOFactory(PersistenceKind.JPA);
             IJoueur daoJoueur = factory.getDAOJoueur();
             IPartie daoPartie = factory.getDAOPartie();
             IJoueursPartie daoJoueursPartie = factory.getDAOJoueursPartie();
             // Creation et modification d'un joueur
             Joueur j1 = new Joueur();
             j1.setPseudo("test2");
             j1.setMdp("azerty");
             j1.setAge(18);
             daoJoueur.ajouterJoueur(j1);
             j1.setSexe('M');
             j1.setVille("Pau");
             daoJoueur.modifierJoueur(j1);
             // Creation d'une partie
             Partie p1 = new Partie();
             p1.setDatePartie(new Date());
             daoPartie.ajouterPartie(p1);
             // Creation d'un lien entre j1 et p1
             JoueursPartie jp1 = new JoueursPartie(j1.getCodeJoueur(), p1.getCodePartie());
             jp1.setCvPerdues(1);
             jp1.setScore(343);
             jp1.setSuiteGagnees(3);
             daoJoueursPartie.ajouterJoueursPartie(jp1);
             // Ajout deuxieme joueur par connexion
             Joueur j2 = daoJoueur.connexionJoueur("test", "test");
             JoueursPartie jp2 = new JoueursPartie(j2.getCodeJoueur(), p1.getCodePartie());
             jp2.setCvPerdues(4);
             jp2.setScore(100);
             jp2.setSuiteGagnees(1);
             daoJoueursPartie.ajouterJoueursPartie(jp2);
             
             daoJoueur.updateAllJoueursStats();
             
             // Connexion d'un joueur
             
             //System.out.println("Joueur connecté: " + j2.toString());
             System.out.println("Test joueur existant: " + daoJoueur.rechercherPseudoExistant("test")); // Doit retourner true
             System.out.println("Test joueur existant: " + daoJoueur.rechercherPseudoExistant("test12")); // Doit retourner false
             
             // Suppression d'un joueur
             // daoJoueursPartie.supprimerJoueurPartie(jp2);
             // daoJoueursPartie.supprimerJoueurPartie(jp1);
             // daoJoueur.supprimerJoueur(j1);
             // daoPartie.supprimerPartie(p1.getCodePartie());
             
         } catch (DAOException ex) {
             ex.printStackTrace();
         }
     }
}
