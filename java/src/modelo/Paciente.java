package modelo;

import excepciones.DniInvalidoException;

public class Paciente extends Persona {
    private String nroHistoriaClinica;

    public Paciente(String dni, String nombres, String apellidos, String nroHistoriaClinica) throws DniInvalidoException {
        super(dni, nombres, apellidos);
        setNroHistoriaClinica(nroHistoriaClinica);
    }

    public String getNroHistoriaClinica() {
        return nroHistoriaClinica;
    }

   public void setNroHistoriaClinica(String nroHistoriaClinica) {
        if (nroHistoriaClinica == null || !nroHistoriaClinica.matches("HC-\\d{3}")) {
            throw new IllegalArgumentException("El formato de la historia clínica debe ser HC-XXX (ej. HC-003).");
        }
        this.nroHistoriaClinica = nroHistoriaClinica;
    }

    @Override
    public String obtenerRol() {
        return "Paciente (HC: " + nroHistoriaClinica + ")";
    }
}