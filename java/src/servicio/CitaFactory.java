package servicio;

import modelo.CitaMedica;
import modelo.Medico;
import modelo.Paciente;

// Patrón Factory: centraliza la creación y validación de citas médicas en un solo punto, desacoplando la instanciación del menú
public class CitaFactory {

    public static CitaMedica crearCita(String id, String tipo, Paciente p, Medico m) {
        if (p == null || m == null) {
            throw new IllegalArgumentException("El paciente y el médico son obligatorios.");
        }
        return new CitaMedica(id, tipo, p, m);
    }
}