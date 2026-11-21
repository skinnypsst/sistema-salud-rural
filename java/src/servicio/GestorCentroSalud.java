package servicio;

import java.util.ArrayList;
import java.util.List;
import modelo.CitaMedica;
import modelo.Medico;
import modelo.Paciente;
import modelo.Persona;

// Patrón Singleton: garantiza una única instancia del gestor para mantener centralizados todos los registros del centro de salud
public class GestorCentroSalud {
    private static GestorCentroSalud instancia;

    // Agregación: el gestor agrupa pacientes, médicos y citas que existen de forma independiente
    private List<Paciente> pacientes;
    private List<Medico> medicos;
    private List<CitaMedica> citas;

    private GestorCentroSalud() {
        this.pacientes = new ArrayList<>();
        this.medicos = new ArrayList<>();
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

    public void registrarMedico(Medico m) {
        medicos.add(m);
    }

    public void agendarCita(CitaMedica c) {
        citas.add(c);
    }

    // Métodos de apoyo para el menú de consola
    public Paciente buscarPacientePorDni(String dni) {
        for (Paciente p : pacientes) {
            if (p.getDni().equals(dni)) {
                return p;
            }
        }
        return null;
    }

    public Medico buscarMedicoPorDni(String dni) {
        for (Medico m : medicos) {
            if (m.getDni().equals(dni)) {
                return m;
            }
        }
        return null;
    }

    public CitaMedica buscarCitaPorId(String idCita) {
        for (CitaMedica c : citas) {
            if (c.getIdCita().equalsIgnoreCase(idCita)) {
                return c;
            }
        }
        return null;
    }

    // Tema 4 (Colección inmutable) y Tema 2 (Polimorfismo desde lista del tipo base Persona)
    public List<Persona> obtenerTodasLasPersonas() {
        List<Persona> personas = new ArrayList<>();
        personas.addAll(pacientes);
        personas.addAll(medicos);
        return List.copyOf(personas); // Colección inmutable: impide modificar la lista desde fuera
    }

    // filter + lambda (devuelve lista inmutable con .toList())
    public List<CitaMedica> obtenerCitasPorEstado(String estado) {
        return citas.stream()
                    .filter(c -> c.getEstado().equalsIgnoreCase(estado))
                    .toList();
    }

    // map + lambda
    public List<String> obtenerResumenAnonimizado() {
        return citas.stream()
                    .map(c -> c.getResumenSeguro())
                    .toList();
    }

    // filter + map + reduce + lambda
    public int contarTotalAtendidas() {
        return citas.stream()
                    .filter(c -> c.getEstado().equalsIgnoreCase("ATENDIDA"))
                    .map(c -> 1)
                    .reduce(0, (acumulado, valor) -> acumulado + valor);
    }
}