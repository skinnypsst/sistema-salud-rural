package servicio;

import modelo.CitaMedica;
import modelo.Medico;
import modelo.Paciente;

public class CitaFactory {

    public static CitaMedica crearCita(String id, String tipo, Paciente p, Medico m) {
        if (p == null || m == null) {
            throw new IllegalArgumentException("El paciente y el médico son obligatorios.");
        }
        if (id == null || id.isBlank()) {
            return new CitaMedica(tipo, p, m);
        }
        return new CitaMedica(id, tipo, p, m);
    }

    public static CitaMedica crearCita(String tipo, Paciente p, Medico m) {
        return crearCita(null, tipo, p, m);
    }
}