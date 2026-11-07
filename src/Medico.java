public class Medico extends Persona {
    private String especialidad;
    Medico(String dni, String nombreCompleto, String especialidad){
        super(dni, nombreCompleto);
        this.especialidad = especialidad;
    }

    //Metodos Getters
    public String getEspecialidad(){ return especialidad; }

    @Override
    //Editar el tipo de retorno al añadir la logica
    public void obtenerRol() {
         super.obtenerRol();
    } 


}

    
