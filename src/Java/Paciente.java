package Java;
// Herencia y Polimorfismo
public class Paciente extends Persona {
    private String nroHistoriaClinica;

    // Constructor
    public Paciente(String dni, String nombreCompleto, String nroHistoriaClinica) {
        super(dni, nombreCompleto);
        this.nroHistoriaClinica = nroHistoriaClinica;
    }

    //Metodos Getters
    public String getNroHistoriaClinica(){ 
        return nroHistoriaClinica; 
    }

    @Override
    public String obtenerRol() {
        return "Paciente (HC: " + nroHistoriaClinica + ")";
    }
 
}