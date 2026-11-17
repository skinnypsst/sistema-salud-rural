package Java;
// Asociación y Composición
public class CitaMedica {
    private String idCita;
    private String tipoCita;
    private String estado;
    private Paciente paciente;
    private Medico medico;
    private AtencionMedica atencion;

    public CitaMedica(String idCita, String tipoCita, Paciente paciente, Medico medico) {
        this.idCita = idCita;
        this.tipoCita = tipoCita;
        this.estado = "PENDIENTE";
        this.paciente = paciente;
        this.medico = medico;
        this.atencion = null;
    }

     public void registrarAtencion(String diagnostico, String tratamiento) {
        this.atencion = new AtencionMedica(diagnostico, tratamiento);
        this.estado ="ATENDIDA";
    }
    //Metodos Getters
    public String getEstado() { return estado; }
    public Paciente getPaciente() { return paciente; }
    public Medico getMedico() { return medico; }

    public String getResumenSeguro() {
            String diag = (atencion != null) ? atencion.getDiagnosticoSeguro() : "PENDIENTE_ATENCION";
            return String.format("[%s - %s] Paciente: %s (DNI: %s) | Médico: %s | Estado: %s | Dx(Base64): %s",
                    idCita, tipoCita, paciente.getNombreCompleto(), paciente.getDniEnmascarado(),
                    medico.getNombreCompleto(), estado, diag);
    }
}

