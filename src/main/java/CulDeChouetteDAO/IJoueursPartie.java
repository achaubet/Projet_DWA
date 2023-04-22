/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CulDeChouetteDAO;

import POJO.JoueursPartie;
import java.util.ArrayList;

/**
 *
 * @author Arnaud
 */
public interface IJoueursPartie {
    /**
     * Ajoute un JoueursPartie à la base de données
     * @param joueursPartie
     * @return L'id du JoueursPartie attribué par la base de données
     */
    public int ajouterJoueursPartie(JoueursPartie joueursPartie);
    /**
     * Modifie le JoueursPartie
     * @param joueursPartie
     * @return L'id du JoueursPartie attribué par la base de données
     */
    public int modifierJoueursPartie(JoueursPartie joueursPartie);
    /**
     * Supprime le JoueursPartie de la base de données
     * @param idJoueursPartie
     * @return Vrai si pas d'erreur, sinon Faux
     */
    public boolean supprimerJoueurPartie(int idJoueursPartie);
    /**
     * Recherche le JoueursPartie
     * @param idJoueursPartie
     * @return Le JoueursPartie si trouvé, sinon null
     */
    public JoueursPartie RechercherJoueursPartieParId(int idJoueursPartie);
    /**
     * Recherche tous les JoueursPartie existants
     * @return La liste de tous les JoueursPartie
     */
    public ArrayList<JoueursPartie> rechercherTousLesJoueursParties();    
}
