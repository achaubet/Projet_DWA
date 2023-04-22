package CulDeChouetteDAO;
/**
 * Exception spécifique aux problèmes d'accès aux données via un DAO
 * @author Eric
 */
public class DAOException extends java.lang.Exception {
    
    public DAOException() {
        super();
    }
    
    public DAOException(String message) {
        super(message);
    }
}
