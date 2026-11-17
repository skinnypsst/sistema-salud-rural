package Java;
import java.util.ArrayList;
import java.util.List;

public class GestorCentroSalud {
    private static GestorCentroSalud instancia;
    private List<Paciente> pacientes;
    private List<CitaMedica> citas;

    private GestorCentroSalud() {
        this.pacientes = new ArrayList<>();
        this.citas = new ArrayList<>();
    }

    public static GestorCentroSalud getInstancia() {
        if (instancia == null) {
            instancia = new GestorCentroSalud();
        }
        return instancia;
    }

    public void registrarPaciente(Paciente p) {
        pacientes.add(p);
    }

    public void agendarCita(CitaMedica c) {
        citas.add(c);
    }

    // Tema 4: filter + lambda
    public List<CitaMedica> obtenerCitasPorEstado(String estado) {
        return citas.stream()
                    .filter(c -> c.getEstado().equalsIgnoreCase(estado))
                    .toList();
    }

    // Tema 4: map + lambda
    public List<String> obtenerResumenAnonimizado() {
        return citas.stream()
                    .map(c -> c.getResumenSeguro())
                    .toList();
    }

    // Tema 4: filter + map + reduce + lambda
    public int contarTotalAtendidas() {
        return citas.stream()
                    .filter(c -> c.getEstado().equalsIgnoreCase("ATENDIDA"))
                    .map(c -> 1)
                    .reduce(0, (acumulado, valor) -> acumulado + valor);
    }
}