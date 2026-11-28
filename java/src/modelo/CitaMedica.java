package modelo;

public class CitaMedica {
    private static int contadorCitas = 100;

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

    // Constructor 2: Genera el ID automáticamente (C-101, C-102, ...)
    public CitaMedica(String tipoCita, Paciente paciente, Medico medico) {
        this(generarIdAutomatico(), tipoCita, paciente, medico);
    }

    private static String generarIdAutomatico() {
        contadorCitas++;
        return "C-" + contadorCitas;
    }

    public void registrarAtencion(String diagnostico, String tratamiento) {
        if ("ATENDIDA".equals(this.estado)) {
            throw new IllegalStateException("La cita " + idCita + " ya fue atendida previamente.");
        }
        this.atencion = new AtencionMedica(diagnostico, tratamiento);
        this.estado = "ATENDIDA";
    }

    public void registrarAtencion(String diagnostico) {
        registrarAtencion(diagnostico, "Observación y reposo (sin medicación)");
    }

    public String getIdCita() {
        return idCita;
    }

    public void setIdCita(String idCita) {
        if (idCita == null || !idCita.matches("C-\\d{3}")) {
            throw new IllegalArgumentException("El formato del ID de la cita debe ser C-XXX (ej. C-103).");
        }
        this.idCita = idCita;
    }

    public String getTipoCita() {
        return tipoCita;
    }

    public void setTipoCita(String tipoCita) {
        if (tipoCita == null || tipoCita.isBlank()) {
            throw new IllegalArgumentException("El tipo de cita es obligatorio.");
        }
        this.tipoCita = tipoCita.trim();
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