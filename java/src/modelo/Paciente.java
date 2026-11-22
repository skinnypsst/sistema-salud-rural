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
        if (nroHistoriaClinica == null || nroHistoriaClinica.length() < 6) {
            throw new IllegalArgumentException("El número de historia clínica es obligatorio.");
        }
        this.nroHistoriaClinica = nroHistoriaClinica;
    }

    @Override
    public String obtenerRol() {
        return "Paciente (HC: " + nroHistoriaClinica + ")";
    }
}
