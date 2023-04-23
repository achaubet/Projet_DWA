/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CulDeChouetteDAO;

/**
 *
 * @author Arnaud
 */
public class AbstractDAOFactory {
    public static CulDeChouetteDAOFactory getDAOFactory(PersistenceKind type) {
        if (type.equals(PersistenceKind.JPA)) {
            return new CulDeChouetteDAO_JPA_Factory();
        } else {
            return null;
        }
    }
}
