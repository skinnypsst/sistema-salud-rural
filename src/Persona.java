public abstract class Persona {
    protected String dni;
    protected String nombreCompleto;

    Persona(String dni, String nombreCompleto){
        this.dni = dni;
        this.nombreCompleto = nombreCompleto;
    }

    //Metodos Getters
    public String getDni() { return dni; }
    public String getNombreCompleto() { return nombreCompleto; }

    //Editar el tipo de retorno al añadir la logica
    public void DniEnmascarado(){}

    //Se debe reeescribir en las clases hijas.
    public abstract String obtenerRol();

}
