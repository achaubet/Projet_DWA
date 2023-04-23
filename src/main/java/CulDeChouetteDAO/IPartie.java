/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CulDeChouetteDAO;

import POJO.Partie;
import java.util.List;

/**
 *
 * @author Arnaud
 */
public interface IPartie {
    /**
     * Ajoute un joueur à la base de données
     * @param partie La partie
     * @throws CulDeChouetteDAO.DAOException en cas de problème
     */
    public void ajouterPartie(Partie partie) throws DAOException;
    /**
     * Modifie un joueur à la base de données (modification du pseudo par exemple)
     * @param partie La partie
     * @throws CulDeChouetteDAO.DAOException en cas de problème
     */
    public void modifierPartie(Partie partie) throws DAOException;
    /**
     * Supprime un joueur de la base de données
     * @param idPartie l'identifiant de la partie
     * @return Vrai si supression ok, sinon Faux
     * @throws CulDeChouetteDAO.DAOException en cas de problème
     */
    public boolean supprimerPartie(int idPartie) throws DAOException;
    /**
     * Recherche un joueur avec l'id
     * @param idPartie l'identifiant de la partie
     * @return La partie si elle existe, sinon null
     * @throws CulDeChouetteDAO.DAOException en cas de problème
     */
    public Partie rechercherPartieParId(int idPartie) throws DAOException;
    /**
     * Recherche tous les joueurs existants dans la base de données
     * @return La liste des parties
     * @throws CulDeChouetteDAO.DAOException en cas de problème
     */
    public List<Partie> rechercherToutesLesParties() throws DAOException;
}
