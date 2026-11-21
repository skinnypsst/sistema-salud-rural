import excepciones.DniInvalidoException;
import java.util.List;
import java.util.Scanner;
import modelo.*;
import servicio.*;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final GestorCentroSalud gestor = GestorCentroSalud.getInstancia();

    public static void main(String[] args) {
        cargarDatosIniciales();
        int opcion = -1;

        while (opcion != 0) {
            mostrarMenu();
            try {
                opcion = Integer.parseInt(leer("Seleccione una opción: "));
                switch (opcion) {
                    case 1 -> registrarPaciente();
                    case 2 -> agendarCita();
                    case 3 -> registrarAtencion();
                    case 4 -> listarRoles();
                    case 5 -> gestor.obtenerResumenAnonimizado().forEach(System.out::println);
                    case 6 -> mostrarEstadisticas();
                    case 0 -> System.out.println("Saliendo del sistema...");
                    default -> System.out.println("Opción inválida (0 al 6).");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: ingrese un número entero.");
            }
        }
        scanner.close();
    }

    private static String leer(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    private static void mostrarMenu() {
        System.out.println("\n=== SISTEMA DE SALUD RURAL ==="
                + "\n1. Registrar paciente"
                + "\n2. Agendar cita"
                + "\n3. Registrar atención (sobrecarga)"
                + "\n4. Listar roles (polimorfismo)"
                + "\n5. Reporte anonimizado (map)"
                + "\n6. Filtrar citas y total atendidas (filter / reduce)"
                + "\n0. Salir");
    }

    private static void cargarDatosIniciales() {
        try {
            Paciente p1 = new Paciente("72849102", "Luis Gutierrez Rodriguez", "HC-001");
            Paciente p2 = new Paciente("41928374", "María Mendoza Tirado", "HC-002");
            Medico m1 = new Medico("10293847", "Dr. Jorge Salazar Castillo", "Medicina General");
            Medico m2 = new Medico("20458912", "Dra. Carmen Quispe Vargas", "Pediatría");

            gestor.registrarPaciente(p1);
            gestor.registrarPaciente(p2);
            gestor.registrarMedico(m1);
            gestor.registrarMedico(m2);

            CitaMedica c1 = CitaFactory.crearCita("C-101", "Consulta Externa", p1, m1);
            CitaMedica c2 = CitaFactory.crearCita("C-102", "Control", p2, m1);
            gestor.agendarCita(c1);
            gestor.agendarCita(c2);

            c1.registrarAtencion("Faringitis aguda", "Paracetamol 500mg cada 8h");
        } catch (DniInvalidoException | IllegalArgumentException e) {
            System.err.println("Error en datos iniciales: " + e.getMessage());
        }
    }

    private static void registrarPaciente() {
        try {
            Paciente p = new Paciente(
                    leer("DNI (8 dígitos): "),
                    leer("Nombre completo: "),
                    leer("Historia Clínica (ej. HC-003): "));
            gestor.registrarPaciente(p);
            System.out.println("Registrado. DNI protegido: " + p.getDniEnmascarado());
        } catch (DniInvalidoException | IllegalArgumentException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    private static void agendarCita() {
        try {
            String id = leer("ID de cita (ej. C-103): ");
            String tipo = leer("Tipo de cita (ej. Consulta Externa): ");
            Paciente p = gestor.buscarPacientePorDni(leer("DNI del paciente: "));
            Medico m = gestor.buscarMedicoPorDni(leer("DNI del médico (ej. 10293847): "));

            gestor.agendarCita(CitaFactory.crearCita(id, tipo, p, m));
            System.out.println("Cita agendada para " + p.getNombreCompleto() + " (" + p.getDniEnmascarado() + ").");
        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    private static void registrarAtencion() {
        try {
            CitaMedica cita = gestor.buscarCitaPorId(leer("ID de la cita (ej. C-102): "));
            if (cita == null) {
                System.out.println("Cita no encontrada.");
                return;
            }

            String diag = leer("Diagnóstico: ");
            if (leer("¿Ingresar tratamiento específico? (S/N): ").equalsIgnoreCase("S")) {
                cita.registrarAtencion(diag, leer("Tratamiento: "));
            } else {
                cita.registrarAtencion(diag);
            }
            System.out.println("Atención registrada:\n" + cita.getResumenSeguro());
        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    private static void listarRoles() {
        for (Persona p : gestor.obtenerTodasLasPersonas()) {
            System.out.println(p.getNombreCompleto() + " (" + p.getDniEnmascarado() + ") -> " + p.obtenerRol());
        }
    }

    private static void mostrarEstadisticas() {
        String estado = leer("Estado a filtrar (PENDIENTE / ATENDIDA): ");
        List<CitaMedica> filtradas = gestor.obtenerCitasPorEstado(estado);
        System.out.println("Encontradas (" + estado.toUpperCase() + "): " + filtradas.size());
        filtradas.forEach(c -> System.out.println(" - " + c.getResumenSeguro()));
        System.out.println("Total atendidas (reduce): " + gestor.contarTotalAtendidas());
    }
}