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
                    case 4 -> registrarMedico();
                    case 5 -> listarRoles();
                    case 6 -> gestor.obtenerResumenAnonimizado().forEach(System.out::println);
                    case 7 -> mostrarEstadisticas();
                    case 0 -> System.out.println("Saliendo del sistema...");
                    default -> System.out.println("Opción inválida (0 al 7).");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: ingrese un número entero.");
            }
        }
        scanner.close();
    }

    private static String leer(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    private static void mostrarMenu() {
        System.out.println("\n=== SISTEMA DE SALUD | POSTA MEDICA MANUEL ARÉVALO ===");
        System.out.println("1. Registrar paciente");
        System.out.println("2. Agendar cita");
        System.out.println("3. Registrar atención");
        System.out.println("4. Registrar médico");
        System.out.println("5. Listar roles");
        System.out.println("6. Reporte anonimizado");
        System.out.println("7. Filtrar citas y total atendidas");
        System.out.println("0. Salir");
    }

    private static void cargarDatosIniciales() {
        try {
            Medico m1 = new Medico("10293847", "Dr. Jorge", "Salazar Castillo", "Medicina General");
            Medico m2 = new Medico("20458912", "Dra. Carmen", "Quispe Vargas", "Pediatría");

            gestor.registrarMedico(m1);
            gestor.registrarMedico(m2);
        } catch (DniInvalidoException | IllegalArgumentException e) {
            System.err.println("Error en datos iniciales: " + e.getMessage());
        }
    }

    private static void registrarPaciente() {
        String dni;
        while (true) {
            try {
                dni = leer("DNI (8 dígitos): ");
                if (!dni.matches("\\d{8}")) {
                    throw new DniInvalidoException("El DNI debe contener exactamente 8 dígitos numéricos.");
                }
                if (gestor.buscarPacientePorDni(dni) != null) {
                    throw new IllegalArgumentException("Ya existe un paciente registrado con ese DNI.");
                }
                break;
            } catch (DniInvalidoException | IllegalArgumentException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        }

        String nombres;
        while (true) {
            try {
                nombres = leer("Nombres: ");
                if (nombres == null || nombres.isBlank()) {
                    throw new IllegalArgumentException("Los nombres no pueden estar vacíos.");
                }
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        }

        String apellidos;
        while (true) {
            try {
                apellidos = leer("Apellidos: ");
                if (apellidos == null || apellidos.isBlank()) {
                    throw new IllegalArgumentException("Los apellidos no pueden estar vacíos.");
                }
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        }

        try {
            String hc = gestor.generarCodigoHistoriaClinica();
            Paciente p = new Paciente(dni, nombres, apellidos, hc);
            gestor.registrarPaciente(p);
            System.out.println("REGISTRO EXITOSO: Paciente " + p.getNombreCompleto()
                    + " (HC asignada: " + p.getNroHistoriaClinica()
                    + " | DNI protegido: " + p.getDniEnmascarado() + ").");
        } catch (DniInvalidoException | IllegalArgumentException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    private static void agendarCita() {
        String tipo;
        while (true) {
            try {
                tipo = leer("Tipo de cita (ej. Consulta Externa): ");
                if (tipo == null || tipo.isBlank()) {
                    throw new IllegalArgumentException("El tipo de cita es obligatorio.");
                }
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        }

        Paciente p;
        while (true) {
            try {
                String dniPaciente = leer("DNI del paciente: ");
                if (!dniPaciente.matches("\\d{8}")) {
                    throw new DniInvalidoException("El DNI debe contener exactamente 8 dígitos numéricos.");
                }
                p = gestor.buscarPacientePorDni(dniPaciente);
                if (p == null) {
                    throw new IllegalArgumentException("No existe un paciente registrado con ese DNI.");
                }
                break;
            } catch (DniInvalidoException | IllegalArgumentException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        }

        Medico m;
        while (true) {
            try {
                String dniMedico = leer("DNI del médico (ej. 10293847): ");
                if (!dniMedico.matches("\\d{8}")) {
                    throw new DniInvalidoException("El DNI debe contener exactamente 8 dígitos numéricos.");
                }
                m = gestor.buscarMedicoPorDni(dniMedico);
                if (m == null) {
                    throw new IllegalArgumentException("No existe un médico registrado con ese DNI.");
                }
                break;
            } catch (DniInvalidoException | IllegalArgumentException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        }

        try {
            CitaMedica nuevaCita = CitaFactory.crearCita(tipo, p, m);
            gestor.agendarCita(nuevaCita);
            System.out.println("REGISTRO EXITOSO: Cita " + nuevaCita.getIdCita() + " (" + tipo
                    + ") agendada para el paciente " + p.getNombreCompleto() + " (" + p.getDniEnmascarado()
                    + ") con el/la " + m.getNombreCompleto() + " (" + m.getEspecialidad() + ").");
        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    private static void registrarAtencion() {
        CitaMedica cita;
        while (true) {
            String id = leer("ID de la cita (ej. C-101): ");
            cita = gestor.buscarCitaPorId(id);
            if (cita == null) {
                System.out.println("[ERROR] Cita no encontrada. Intente nuevamente.");
                continue;
            }
            if ("ATENDIDA".equals(cita.getEstado())) {
                System.out.println("[ERROR] La cita " + cita.getIdCita() + " ya fue atendida previamente.");
                return;
            }
            break;
        }

        String diag;
        while (true) {
            try {
                diag = leer("Diagnóstico: ");
                if (diag == null || diag.isBlank()) {
                    throw new IllegalArgumentException("El diagnóstico no puede estar vacío.");
                }
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        }

        if (leer("¿Ingresar tratamiento específico? (S/N): ").equalsIgnoreCase("S")) {
            while (true) {
                try {
                    cita.registrarAtencion(diag, leer("Tratamiento: "));
                    break;
                } catch (IllegalArgumentException | IllegalStateException e) {
                    System.out.println("[ERROR] " + e.getMessage());
                }
            }
        } else {
            cita.registrarAtencion(diag);
        }
        System.out.println("REGISTRO EXITOSO: Atención médica completada -> " + cita.getResumenSeguro());
    }

    private static void registrarMedico() {
        String dni;
        while (true) {
            try {
                dni = leer("DNI del médico (8 dígitos): ");
                if (!dni.matches("\\d{8}")) {
                    throw new DniInvalidoException("El DNI debe contener exactamente 8 dígitos numéricos.");
                }
                if (gestor.buscarMedicoPorDni(dni) != null) {
                    throw new IllegalArgumentException("Ya existe un médico registrado con ese DNI.");
                }
                break;
            } catch (DniInvalidoException | IllegalArgumentException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        }

        String nombres;
        while (true) {
            try {
                nombres = leer("Nombres: ");
                if (nombres == null || nombres.isBlank()) {
                    throw new IllegalArgumentException("Los nombres no pueden estar vacíos.");
                }
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        }

        String apellidos;
        while (true) {
            try {
                apellidos = leer("Apellidos: ");
                if (apellidos == null || apellidos.isBlank()) {
                    throw new IllegalArgumentException("Los apellidos no pueden estar vacíos.");
                }
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        }

        while (true) {
            try {
                String especialidad = leer("Especialidad (ej. Cardiología): ");
                Medico m = new Medico(dni, nombres, apellidos, especialidad);
                gestor.registrarMedico(m);
                System.out.println("REGISTRO EXITOSO: " + m.getNombreCompleto()
                        + " (" + m.obtenerRol() + " | DNI protegido: " + m.getDniEnmascarado() + ") incorporado al plantel médico.");
                break;
            } catch (DniInvalidoException | IllegalArgumentException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
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