/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CulDeChouetteDAO;

import POJO.Joueur;
import java.util.List;

/**
 *
 * @author Arnaud
 */
public interface IJoueur {
    /**
     * Ajoute un joueur à la base de données (création d'un compte Joueur)
     * @param joueur Le Joueur
     * @throws CulDeChouetteDAO.DAOException en cas de problème
     */
    public void ajouterJoueur(Joueur joueur) throws DAOException;
    /**
     * Modifie un joueur à la base de données (modification du pseudo par exemple)
     * @param joueur Le Joueur
     * @throws CulDeChouetteDAO.DAOException en cas de problème
     */
    public void modifierJoueur(Joueur joueur) throws DAOException;
    /**
     * Supprime un joueur de la base de données
     * @param joueur Le joueur à suppriler
     * @return Vrai si supression ok, sinon Faux
     * @throws CulDeChouetteDAO.DAOException en cas de problème
     */
    public boolean supprimerJoueur(Joueur joueur) throws DAOException;
    /**
     * Recherche un joueur avec l'id
     * @param idJoueur l'identifiant du joueur
     * @return Le joueur si il existe, sinon null
     * @throws CulDeChouetteDAO.DAOException en cas de problème
     */
    public Joueur rechercherJoueurParId(int idJoueur) throws DAOException;
    /**
     * Recherche tous les joueurs existants dans la base de données
     * @return La liste des joueurs
     * @throws CulDeChouetteDAO.DAOException en cas de problème
     */
    public List<Joueur> rechercherTousLesJoueurs()  throws DAOException;
    /**
     * Permet à un joueur de se connecter
     * @param pseudo Le pseudo du joueur
     * @param motDePasse Le mot de passe du joueur
     * @return Le joueur si pseudo et mot de passe correct, sinon null
     */
    public Joueur connexionJoueur(String pseudo, String motDePasse);
    /**
     * Permet de mettre à jour les statistiques des joueurs à la fin d'une partie
     * @throws CulDeChouetteDAO.DAOException en cas de problème
     */
    public void updateAllJoueursStats() throws DAOException;
}
