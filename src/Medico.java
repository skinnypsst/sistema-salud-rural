public class Medico extends Persona {
    private String especialidad;
    Medico(String dni, String nombreCompleto, String especialidad){
        super(dni, nombreCompleto);
        this.especialidad = especialidad;
    }

    //Metodos Getters
    public String getEspecialidad(){ return especialidad; }

    @Override
    public String obtenerRol() {
         return "Médico";
    } 
}

    
