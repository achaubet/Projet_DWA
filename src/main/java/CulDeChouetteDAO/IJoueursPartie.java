/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CulDeChouetteDAO;

import POJO.JoueursPartie;
import java.util.List;

/**
 *
 * @author Arnaud
 */
public interface IJoueursPartie {
    /**
     * Ajoute un JoueursPartie à la base de données
     * @param joueursPartie
     * @throws CulDeChouetteDAO.DAOException en cas de problème
     */
    public void ajouterJoueursPartie(JoueursPartie joueursPartie) throws DAOException;
    /**
     * Modifie le JoueursPartie
     * @param joueursPartie
     * @throws CulDeChouetteDAO.DAOException en cas de problème
     */
    public void modifierJoueursPartie(JoueursPartie joueursPartie) throws DAOException;
    /**
     * Supprime le JoueursPartie de la base de données
     * @param joueursPartie
     * @return Vrai si pas d'erreur, sinon Faux
     * @throws CulDeChouetteDAO.DAOException en cas de problème
     */
    public boolean supprimerJoueurPartie(JoueursPartie joueursPartie) throws DAOException;
    /**
     * Recherche le JoueursPartie
     * @param codeJoueur Le code du joueur
     * @param codePartie Le code de la partie
     * @return Le JoueursPartie si trouvé, sinon null
     * @throws CulDeChouetteDAO.DAOException en cas de problème
     */
    public JoueursPartie rechercherJoueursPartieParId(int codeJoueur, int codePartie) throws DAOException;
    /**
     * Rechercher toutes les JoueursPartie avec le code de la partie
     * @param codePartie Le code de la partie
     * @return la liste contenant tous les JoueursPartie par Partie
     * @throws DAOException en cas de problème
     */
    public List<JoueursPartie> rechercherJoueursPartieParCodePartie(int codePartie) throws DAOException;
    /**
     * Recherche tous les JoueursPartie existants
     * @return La liste de tous les JoueursPartie
     * @throws CulDeChouetteDAO.DAOException en cas de problème
     */
    public List<JoueursPartie> rechercherTousLesJoueursParties() throws DAOException;
}
