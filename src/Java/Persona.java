package Java;
// Clase Abstracta y Encapsulamiento
public abstract class Persona {
    private String dni;
    private String nombreCompleto;

    // Constructor
    public Persona(String dni, String nombreCompleto){
        if (dni == null || !dni.matches("\\d{8}")) {
            throw new IllegalArgumentException("El DNI debe contener exactamente 8 digitos numéricos.");
        }
        this.dni = dni;
        this.nombreCompleto = nombreCompleto;
    }
        
    // Metodos Getters
    public String getDniEnmascarado() {
        return "****" + dni.substring(4);
    }

    public String getDni() {
        return dni;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public abstract String obtenerRol();

}
