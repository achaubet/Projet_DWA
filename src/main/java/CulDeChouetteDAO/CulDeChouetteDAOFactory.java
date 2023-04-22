/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CulDeChouetteDAO;

/**
 *
 * @author Arnaud
 */
public abstract class CulDeChouetteDAOFactory {
    /**
     * Permet d'initialiser le DAO pour la classe/table Joueur
     * @return le DAO pour la classe/table Joueur
     * @throws DAOException en cas de problème
     */
    public abstract IJoueur getDAOJoueur() throws DAOException;
    /**
     * Permet d'initialiser le DAO pour la classe/table Partie
     * @return  le DAO pour la classe/table Partie
     * @throws DAOException en cas de problème
     */
    public abstract IPartie getDAOPartie() throws DAOException;
    /**
     * Permet d'initialiser le DAO pour la classe/table JoueursPartie
     * @return le DAO pour la classe/table JoueursPartie
     * @throws DAOException en cas de problème
     */
    public abstract IJoueursPartie getDAOJoueursPartie() throws DAOException;
}
