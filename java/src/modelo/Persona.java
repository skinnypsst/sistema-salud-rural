package modelo;

import excepciones.DniInvalidoException;

public abstract class Persona {
    private String dni;
    private String nombres;
    private String apellidos;

    public Persona(String dni, String nombres, String apellidos) throws DniInvalidoException {
        setDni(dni);
        setNombres(nombres);
        setApellidos(apellidos);
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

    public String getDniEnmascarado() {
        return "****" + dni.substring(4);
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        if (nombres == null || nombres.isBlank()) {
            throw new IllegalArgumentException("Los nombres no pueden estar vacíos.");
        }
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        if (apellidos == null || apellidos.isBlank()) {
            throw new IllegalArgumentException("Los apellidos no pueden estar vacíos.");
        }
        this.apellidos = apellidos;
    }

    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }

    public abstract String obtenerRol();
}