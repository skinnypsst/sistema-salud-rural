public class Paciente extends Persona{
    private String nHistoriaClinica;
    Paciente(String dni, String nombreCompleto, String nHistoriaClinica){
        super(dni, nombreCompleto);
        this.nHistoriaClinica = nHistoriaClinica;
    }

    //Metodos Getters
    public String getnHistoriaClinica(){ return nHistoriaClinica; }

    @Override
    //Editar el tipo de retorno al añadir la logica
    public void obtenerRol() {
        super.obtenerRol();
    }

    
}