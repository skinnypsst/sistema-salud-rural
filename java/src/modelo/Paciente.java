package modelo;

import excepciones.DniInvalidoException;

public class Paciente extends Persona {
    private String nroHistoriaClinica;

    public Paciente(String dni, String nombreCompleto, String nroHistoriaClinica) throws DniInvalidoException {
        super(dni, nombreCompleto);
        setNroHistoriaClinica(nroHistoriaClinica);
    }

    public String getNroHistoriaClinica() {
        return nroHistoriaClinica;
    }

    public void setNroHistoriaClinica(String nroHistoriaClinica) {
        if (nroHistoriaClinica == null || nroHistoriaClinica.trim().isEmpty()) {
            throw new IllegalArgumentException("El número de historia clínica es obligatorio.");
        }
        this.nroHistoriaClinica = nroHistoriaClinica.trim();
    }

    @Override
    public String obtenerRol() {
        return "Paciente (HC: " + nroHistoriaClinica + ")";
    }
}