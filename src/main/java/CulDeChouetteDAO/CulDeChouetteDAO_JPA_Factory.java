/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CulDeChouetteDAO;

/**
 *
 * @author Arnaud
 */
public class CulDeChouetteDAO_JPA_Factory extends CulDeChouetteDAOFactory  {
    
    private IJoueur daoJoueur = null;
    
    private IPartie daoPartie = null;
    
    private IJoueursPartie daoJoueursPartie = null;

    @Override
    public IJoueur getDAOJoueur() throws DAOException {
        if (daoJoueur == null) daoJoueur = new JoueurDAO_JPA();
            return daoJoueur;
    }

    @Override
    public IPartie getDAOPartie() throws DAOException {
        if (daoPartie == null) daoPartie = new PartieDAO_JPA();
            return daoPartie;
    }

    @Override
    public IJoueursPartie getDAOJoueursPartie() throws DAOException {
        if (daoJoueursPartie == null) daoJoueursPartie = new JoueursPartieDAO_JPA();
            return daoJoueursPartie;
    }
    
}
