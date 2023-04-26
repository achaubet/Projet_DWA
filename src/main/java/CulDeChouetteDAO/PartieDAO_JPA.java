/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CulDeChouetteDAO;

import POJO.Partie;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author Arnaud
 */
public class PartieDAO_JPA implements IPartie {
    
    private EntityManagerFactory emf = null;

    @Override
    public void ajouterPartie(Partie partie) throws DAOException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            TypedQuery<Integer> queryAdresse = em.createQuery("SELECT MAX(p.codePartie) FROM Partie p", Integer.class);
            Integer maxCodePartie = queryAdresse.getSingleResult();
            if(maxCodePartie != null) {
                partie.setCodePartie(maxCodePartie);
            } else {
                partie.setCodePartie(1);
            }
            em.persist(partie);
            em.getTransaction().commit();
        } catch(Exception e) {
            em.getTransaction().rollback();
            throw new DAOException("Erreur lors de la création de la partie " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public void modifierPartie(Partie partie) throws DAOException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(partie);
            em.getTransaction().commit();
        } catch(Exception e) {
            em.getTransaction().rollback();
            throw new DAOException("Erreur lors de la mise à jour de la partie " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public boolean supprimerPartie(int idPartie) throws DAOException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Partie partie = em.find(Partie.class, idPartie);
            em.remove(partie);
            em.getTransaction().commit();
        } catch(Exception e) {
            em.getTransaction().rollback();
            throw new DAOException("Erreur lors de la suppression de la partie " + e.getMessage());
        } finally {
            em.close();
        }
        return true;
    }

    @Override
    public Partie rechercherPartieParId(int idPartie) throws DAOException {
        EntityManager em = emf.createEntityManager();
        Partie partie = null;
        try {
            em.getTransaction().begin();
            partie = em.find(Partie.class, idPartie);
        } catch(Exception e) {
            em.getTransaction().rollback();
            throw new DAOException("Erreur lors de la recherche de la partie " + e.getMessage());
        } finally {
            em.close();
        }
        return partie;
    }

    @Override
    public List<Partie> rechercherToutesLesParties() throws DAOException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            TypedQuery<Partie> query = em.createQuery("SELECT p FROM Partie p", Partie.class);
            return query.getResultList();
        } catch(Exception e) {
            throw new DAOException("Erreur lors de la récupération de la liste des Parties");
        } finally {
            em.close();
        }   
    }
    
    public PartieDAO_JPA() throws DAOException {
        this.emf = ConnexionBDD.getEMF();
    }
    
}
