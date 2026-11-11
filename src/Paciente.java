public class Paciente extends Persona{
    private String nHistoriaClinica;
    Paciente(String dni, String nombreCompleto){
        super(dni, nombreCompleto);
        this.nHistoriaClinica = "H.C. N° " + getDni();
    }

    //Metodos Getters
    public String getnHistoriaClinica(){ return nHistoriaClinica; }
    public Paciente getPaciente(){ return this; }

    @Override
    public String obtenerRol() {
        return "Paciente";
    }

    
}