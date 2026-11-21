package modelo;

// Asociación (Paciente y Medico) y Composición (crea y posee su AtencionMedica)
public class CitaMedica {
    private String idCita;
    private String tipoCita;
    private String estado;
    private Paciente paciente;
    private Medico medico;
    private AtencionMedica atencion;

    public CitaMedica(String idCita, String tipoCita, Paciente paciente, Medico medico) {
        setIdCita(idCita);
        setTipoCita(tipoCita);
        setPaciente(paciente);
        setMedico(medico);
        this.estado = "PENDIENTE";
        this.atencion = null;
    }

    // Método 1: atención completa con diagnóstico y tratamiento
    public void registrarAtencion(String diagnostico, String tratamiento) {
        this.atencion = new AtencionMedica(diagnostico, tratamiento);
        this.estado = "ATENDIDA";
    }

    // Tema 2 (Sobrecarga): atención rápida solo con diagnóstico, asignando tratamiento por defecto
    public void registrarAtencion(String diagnostico) {
        registrarAtencion(diagnostico, "Observación y reposo (sin medicación)");
    }

    public String getIdCita() {
        return idCita;
    }

    public void setIdCita(String idCita) {
        if (idCita == null || idCita.length()==0) {
            throw new IllegalArgumentException("El ID de la cita es obligatorio.");
        }
        this.idCita = idCita;
    }

    public String getTipoCita() {
        return tipoCita;
    }

    public void setTipoCita(String tipoCita) {
        if (tipoCita == null || tipoCita.length()==0) {
            throw new IllegalArgumentException("El tipo de cita es obligatorio.");
        }
        this.tipoCita = tipoCita;
    }

    public String getEstado() {
        return estado;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        if (paciente == null) {
            throw new IllegalArgumentException("La cita debe tener un paciente asignado.");
        }
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        if (medico == null) {
            throw new IllegalArgumentException("La cita debe tener un médico asignado.");
        }
        this.medico = medico;
    }

    public AtencionMedica getAtencion() {
        return atencion;
    }

    public String getResumenSeguro() {
        String diag = (atencion != null) ? atencion.getDiagnosticoSeguro() : "PENDIENTE_ATENCION";
        return String.format("[%s - %s] Paciente: %s (DNI: %s) | Médico: %s | Estado: %s | Dx(Base64): %s",
                idCita, tipoCita, paciente.getNombreCompleto(), paciente.getDniEnmascarado(),
                medico.getNombreCompleto(), estado, diag);
    }
}