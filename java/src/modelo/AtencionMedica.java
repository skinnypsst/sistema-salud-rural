package modelo;

import java.util.Base64;

// Composición y protección de datos clínicos (Ley N.° 29733)
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
        if (diagnostico == null || diagnostico.length() == 0) {
            throw new IllegalArgumentException("El diagnóstico no puede estar vacío.");
        }
        this.diagnosticoCifrado = Base64.getEncoder().encodeToString(diagnostico.trim().getBytes());
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        if (tratamiento == null || tratamiento.length() == 0) {
            throw new IllegalArgumentException("El tratamiento no puede estar vacío.");
        }
        this.tratamiento = tratamiento;
    }
}