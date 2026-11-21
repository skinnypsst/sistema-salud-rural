package modelo;

import excepciones.DniInvalidoException;

// Clase abstracta base: encapsulamiento y validación de datos personales
public abstract class Persona {
    private String dni;
    private String nombreCompleto;

    public Persona(String dni, String nombreCompleto) throws DniInvalidoException {
        setDni(dni);
        setNombreCompleto(nombreCompleto);
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) throws DniInvalidoException {
        if (dni == null || !dni.matches("\\d{8}")) {
            throw new DniInvalidoException("El DNI debe contener exactamente 8 dígitos numéricos.");
        }
        this.dni = dni;
    }

    // Ley N.° 29733: nunca se expone el DNI completo en pantalla ni reportes
    public String getDniEnmascarado() {
        return "****" + dni.substring(4);
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        if (nombreCompleto == null || nombreCompleto.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre completo no puede estar vacío.");
        }
        this.nombreCompleto = nombreCompleto.trim();
    }

    public abstract String obtenerRol();
}