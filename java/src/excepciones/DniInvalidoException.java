package excepciones;

// Excepción personalizada para validar el formato del DNI
public class DniInvalidoException extends Exception {

    public DniInvalidoException(String mensaje) {
        super(mensaje);
    }
}