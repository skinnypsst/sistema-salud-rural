package modelo;

import excepciones.DniInvalidoException;

public class Medico extends Persona {
    private String especialidad;

    public Medico(String dni, String nombreCompleto, String especialidad) throws DniInvalidoException {
        super(dni, nombreCompleto);
        setEspecialidad(especialidad);
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        if (especialidad == null || especialidad.trim().isEmpty()) {
            throw new IllegalArgumentException("La especialidad del médico es obligatoria.");
        }
        this.especialidad = especialidad.trim();
    }

    @Override
    public String obtenerRol() {
        return "Médico Especialista en " + especialidad;
    }
}