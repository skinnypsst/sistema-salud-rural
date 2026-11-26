package modelo;

import java.util.Base64;

public class AtencionMedica {
    private String diagnosticoCifrado;
    private String tratamiento;

    public AtencionMedica(String diagnostico, String tratamiento) {
        setDiagnostico(diagnostico);
        setTratamiento(tratamiento);
    }

    public String getDiagnosticoSeguro() {
        return diagnosticoCifrado;
    }

    public void setDiagnostico(String diagnostico) {
        if (diagnostico == null || diagnostico.isEmpty()) {
            throw new IllegalArgumentException("El diagnóstico no puede estar vacío.");
        }
        this.diagnosticoCifrado = Base64.getEncoder().encodeToString(diagnostico.trim().getBytes());
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        if (tratamiento == null || tratamiento.isBlank()) {
            throw new IllegalArgumentException("El tratamiento no puede estar vacío.");
        }
        this.tratamiento = tratamiento;
    }
}