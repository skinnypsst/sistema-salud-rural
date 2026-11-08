public abstract class Cita {
    private String id;
    private String tipo;
    private String estado;
    private Paciente paciente;
    private Medico medico;
    private AtencionMedica atencionMedica;

    Cita(String id, String tipo, String estado, Paciente paciente, Medico medico, AtencionMedica atencionMedica){
        this.id = id;
        this.tipo = tipo;
        this.estado = estado;
        this.paciente = paciente;
        this.medico = medico;
        this.atencionMedica = atencionMedica;
    }

    //Metodos Getters
    public String getTipo(){ return tipo; }
    public String getEstado() { return estado; }
    public Paciente getPaciente() { return paciente; }
    public Medico getMedico() { return medico; }
    public abstract void getResumenSeguro();

    //Metodos Setters
    public void setEstado(String nuevoEstado){
        this.estado = nuevoEstado;
    }

    public void registrarAtencion(String diagnostico, String tratamiento){} 

}

