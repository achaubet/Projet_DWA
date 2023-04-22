/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CulDeChouetteDAO;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Arnaud
 */
public class ConnexionBDD { 
    /**
     * Nom de l'unité de persistance (doit correspondre au nom dans le persistence.xml
     */
    private static final String persistenceName = "CulDeChouettePU";
    /**
     * Permet d'initialiser l'EntityManagerFactory
     * @return L'EMF
     * @throws DAOException en cas de problème
     */
    public static EntityManagerFactory getEMF() throws DAOException{
        EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory(persistenceName);
        return emf;
    }
}
