// Importamos nuestra excepción personalizada y los paquetes del modelo y servicio
import excepciones.DniInvalidoException;
import java.util.List;
import java.util.Scanner;
import modelo.*;
import servicio.*;

public class Main {

    // Scanner global para leer entradas del teclado durante toda la ejecución
    private static final Scanner scanner = new Scanner(System.in);
    
    // Patrón Singleton: Obtenemos la única instancia del gestor centralizado
    private static final GestorCentroSalud gestor = GestorCentroSalud.getInstancia();

    public static void main(String[] args) {
        cargarDatosIniciales();
        int opcion = -1;

        // Estructuras de control: Bucle que mantiene activo el menú hasta elegir 0
        while (opcion != 0) {
            mostrarMenu();
            try {
                // Convertimos la entrada de texto a entero; si se escriben letras salta NumberFormatException
                opcion = Integer.parseInt(leer("Seleccione una opción: "));
                
                // Switch que delega cada funcionalidad a su método correspondiente
                switch (opcion) {
                    case 1 -> registrarPaciente();
                    case 2 -> agendarCita();
                    case 3 -> registrarAtencion();
                    case 4 -> listarRoles();
                    // Programación funcional: Iteramos la lista transformada con map usando referencia a método
                    case 5 -> gestor.obtenerResumenAnonimizado().forEach(System.out::println);
                    case 6 -> mostrarEstadisticas();
                    case 0 -> System.out.println("Saliendo del sistema...");
                    default -> System.out.println("Opción inválida (0 al 6).");
                }
            } catch (NumberFormatException e) {
                // Manejo de excepciones: Evita que el programa se caiga si ingresan texto en el menú
                System.out.println("Error: ingrese un número entero.");
            }
        }
        scanner.close();
    }

    private static String leer(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    // Muestra las opciones numeradas en consola
    private static void mostrarMenu() {
        System.out.println("=== SISTEMA DE SALUD RURAL ===");
        System.out.println("1. Registrar paciente");
        System.out.println("2. Agendar cita");
        System.out.println("3. Registrar atención (sobrecarga)");
        System.out.println("4. Listar roles (polimorfismo)");
        System.out.println("5. Reporte anonimizado (map)");
        System.out.println("6. Filtrar citas y total atendidas (filter / reduce)");
        System.out.println("0. Salir");
    }

    // Inicializa pacientes, médicos y citas de prueba usando datos ficticios
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

            // Patrón Factory: Delegamos la creación y validación de las citas a CitaFactory
            CitaMedica c1 = CitaFactory.crearCita("C-101", "Consulta Externa", p1, m1);
            CitaMedica c2 = CitaFactory.crearCita("C-102", "Control", p2, m1);
            gestor.agendarCita(c1);
            gestor.agendarCita(c2);

            // Registramos una atención inicial para que ya exista una cita ATENDIDA en los reportes
            c1.registrarAtencion("Faringitis aguda", "Paracetamol 500mg cada 8h");
        } catch (DniInvalidoException | IllegalArgumentException e) {
            System.err.println("Error en datos iniciales: " + e.getMessage());
        }
    }

    // Opción 1: Solicita datos, valida el DNI mediante DniInvalidoException y registra al paciente
    private static void registrarPaciente() {
        try {
            Paciente p = new Paciente(
                    leer("DNI (8 dígitos): "),
                    leer("Nombre completo: "),
                    leer("Historia Clínica (ej. HC-003): "));
            gestor.registrarPaciente(p);
            // Ley N.° 29733: Solo mostramos el DNI enmascarado (****XXXX), nunca el número completo
            System.out.println("Registrado. DNI protegido: " + p.getDniEnmascarado());
        } catch (DniInvalidoException | IllegalArgumentException e) {
            // Capturamos la excepción propia sin detener la ejecución del menú
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    // Opción 2: Busca paciente y médico por DNI y crea la cita usando CitaFactory
    private static void agendarCita() {
        try {
            String id = leer("ID de cita (ej. C-103): ");
            String tipo = leer("Tipo de cita (ej. Consulta Externa): ");
            Paciente p = gestor.buscarPacientePorDni(leer("DNI del paciente: "));
            Medico m = gestor.buscarMedicoPorDni(leer("DNI del médico (ej. 10293847): "));

            // Si p o m son null (no existen), CitaFactory lanzará IllegalArgumentException
            gestor.agendarCita(CitaFactory.crearCita(id, tipo, p, m));
            System.out.println("Cita agendada para " + p.getNombreCompleto() + " (" + p.getDniEnmascarado() + ").");
        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    // Opción 3: Demuestra la sobrecarga de métodos al registrar la atención médica
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
            System.out.println("Atención registrada:" + cita.getResumenSeguro());
        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    // Opción 4: Demuestra polimorfismo recorriendo una lista del tipo padre Persona
    private static void listarRoles() {
        // Java ejecuta dinámicamente el obtenerRol() de Paciente o de Medico según el objeto real
        for (Persona p : gestor.obtenerTodasLasPersonas()) {
            System.out.println(p.getNombreCompleto() + " (" + p.getDniEnmascarado() + ") -> " + p.obtenerRol());
        }
    }

    // Opción 6: Ejecuta operaciones funcionales filter y reduce sobre las citas
    private static void mostrarEstadisticas() {
        String estado = leer("Estado a filtrar (PENDIENTE / ATENDIDA): ");
        List<CitaMedica> filtradas = gestor.obtenerCitasPorEstado(estado);
        System.out.println("Encontradas (" + estado.toUpperCase() + "): " + filtradas.size());
        filtradas.forEach(c -> System.out.println(" - " + c.getResumenSeguro()));
        System.out.println("Total atendidas (reduce): " + gestor.contarTotalAtendidas());
    }
}