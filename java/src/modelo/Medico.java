package modelo;

import excepciones.DniInvalidoException;

public class Medico extends Persona {
    private String especialidad;

    public Medico(String dni, String nombres, String apellidos, String especialidad) throws DniInvalidoException {
        super(dni, nombres, apellidos);
        setEspecialidad(especialidad);
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        if (especialidad == null || especialidad.isEmpty()) {
            throw new IllegalArgumentException("La especialidad del médico es obligatoria.");
        }
        this.especialidad = especialidad;
    }

    @Override
    public String obtenerRol() {
        return "Médico Especialista en " + especialidad;
    }
}