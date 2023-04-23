/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CulDeChouetteDAO;

import POJO.Joueur;
import java.util.ArrayList;

/**
 *
 * @author Arnaud
 */
public interface IJoueur {
    /**
     * Ajoute un joueur à la base de données (création d'un compte Joueur)
     * @param joueur Le Joueur
     * @return L'id du joueur attribué par la BDD
     */
    public int ajouterJoueur(Joueur joueur);
    /**
     * Modifie un joueur à la base de données (modification du pseudo par exemple)
     * @param joueur Le Joueur
     * @return L'id du joueur attribué par la BDD
     */
    public int modifierJoueur(Joueur joueur);
    /**
     * Supprime un joueur de la base de données
     * @param idJoueur l'identifiant du joueur
     * @return Vrai si supression ok, sinon Faux
     */
    public boolean supprimerJoueur(int idJoueur);
    /**
     * Recherche un joueur avec l'id
     * @param idJoueur l'identifiant du joueur
     * @return Le joueur si il existe, sinon null
     */
    public Joueur rechercherJoueurParId(int idJoueur);
    /**
     * Recherche tous les joueurs existants dans la base de données
     * @return La liste des joueurs
     */
    public ArrayList<Joueur> rechercherTousLesJoueurs();
    /**
     * Permet à un joueur de se connecter
     * @param pseudo Le pseudo du joueur
     * @param motDePasse Le mot de passe du joueur
     * @return Le joueur si pseudo et mot de passe correct, sinon null
     */
    public Joueur connexionJoueur(String pseudo, String motDePasse);
}
