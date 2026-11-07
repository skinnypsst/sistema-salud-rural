public class AtencionMedica {
    private String diagnostico;
    private String tratamiento;

    AtencionMedica(String diagnostico, String tratamiento){
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
    }

    //Metodos Getters
    public String getDiagnostico() { return diagnostico; }
    public String getTratamiento() { return tratamiento; } 
}