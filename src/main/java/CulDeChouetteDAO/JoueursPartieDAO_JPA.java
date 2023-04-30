/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CulDeChouetteDAO;

import POJO.JoueursPartie;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author Arnaud
 */
public class JoueursPartieDAO_JPA implements IJoueursPartie {
    
    private EntityManagerFactory emf = null;

    @Override
    public void ajouterJoueursPartie(JoueursPartie joueursPartie) throws DAOException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(joueursPartie);
            em.getTransaction().commit();
        } catch(Exception e) {
            em.getTransaction().rollback();
            throw new DAOException("Erreur lors de l'ajout de la liaison joueur-partie " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public void modifierJoueursPartie(JoueursPartie joueursPartie) throws DAOException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(joueursPartie);
            em.getTransaction().commit();
        } catch(Exception e) {
            em.getTransaction().rollback();
            throw new DAOException("Erreur lors de la modification de liaison joueur-partie " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public boolean supprimerJoueurPartie(JoueursPartie joueursPartie) throws DAOException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.merge(joueursPartie));
            em.getTransaction().commit();
        } catch(Exception e) {
            em.getTransaction().rollback();
            throw new DAOException("Erreur lors de la suppression de la liaison joueur-partie " + e.getMessage());
        } finally {
            em.close();
        }
        return true;
    }

    @Override
    public JoueursPartie rechercherJoueursPartieParId(int codeJoueur, int codePartie) throws DAOException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            TypedQuery<JoueursPartie> query = em.createQuery("SELECT jp FROM JoueursPartie jp WHERE jp.joueursPartiePK.codeJoueur = :codeJoueur AND jp.joueursPartiePK.codePartie = :codePartie", JoueursPartie.class);
            query.setParameter("codeJoueur", codeJoueur);
            query.setParameter("codePartie", codePartie);
            return query.getSingleResult();
        } catch(Exception e) {
            throw new DAOException("Erreur lors de la récupération de la liste des Joueurs Partie");
        } finally {
            em.close();
        }  
    }
    
    @Override
    public List<JoueursPartie> rechercherJoueursPartieParCodePartie(int codePartie) throws DAOException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            TypedQuery<JoueursPartie> query = em.createQuery("SELECT jp FROM JoueursPartie jp WHERE jp.joueursPartiePK.codePartie = :codePartie", JoueursPartie.class);
            query.setParameter("codePartie", codePartie);
            return query.getResultList();
        } catch(Exception e) {
            throw new DAOException("Erreur lors de la récupération de la liste des Joueurs Partie");
        } finally {
            em.close();
        }      
    }

    @Override
    public List<JoueursPartie> rechercherTousLesJoueursParties() throws DAOException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            TypedQuery<JoueursPartie> query = em.createQuery("SELECT jp FROM JoueursPartie jp", JoueursPartie.class);
            return query.getResultList();
        } catch(Exception e) {
            throw new DAOException("Erreur lors de la récupération de la liste des Joueurs Partie");
        } finally {
            em.close();
        }   
    }
    
    public JoueursPartieDAO_JPA() throws DAOException {
        this.emf = ConnexionBDD.getEMF();
    }

}
