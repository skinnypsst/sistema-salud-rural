package Java;
// Patron Factory Method
public class CitaFactory {
    public static CitaMedica crearCita(String id, String tipo, Paciente p, Medico m) {
        if (p == null || m == null) {
            throw new IllegalArgumentException("El paciente y el médico son obligatorios.");
        }
        return new CitaMedica(id, tipo, p, m);
    }
}