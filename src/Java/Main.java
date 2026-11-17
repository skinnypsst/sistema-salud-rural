package Java;
public class Main {
    public static void main(String[] args) {
        try {
            GestorCentroSalud gestor = GestorCentroSalud.getInstancia();

            Paciente p1 = new Paciente("72849102", "Luis Gutierrez Rodriguez", "HC-001");
            Paciente p2 = new Paciente("41928374", "María Mendoza Tirado", "HC-002");
            Medico m1 = new Medico("10293847", "Dr. Jorge Salazar Castillo", "Medicina General");

            gestor.registrarPaciente(p1);
            gestor.registrarPaciente(p2);

            CitaMedica c1 = CitaFactory.crearCita("C-101", "Consulta Externa", p1, m1);
            CitaMedica c2 = CitaFactory.crearCita("C-102", "Control", p2, m1);

            gestor.agendarCita(c1);
            gestor.agendarCita(c2);

            c1.registrarAtencion("Faringitis aguda", "Paracetamol 500mg cada 8h");

            System.out.println("ROLES (POLIMORFISMO): ");
            System.out.println(p1.getNombreCompleto() + " -> " + p1.obtenerRol());
            System.out.println(m1.getNombreCompleto() + " -> " + m1.obtenerRol());
             System.out.println("----------------------------------------");

            System.out.println("REPORTE ANONIMIZADO (MAP): ");
            gestor.obtenerResumenAnonimizado().forEach(System.out::println);
             System.out.println("----------------------------------------");
            System.out.println("CITAS PENDIENTES (FILTER):");
            System.out.println("Pendientes: " + gestor.obtenerCitasPorEstado("PENDIENTE").size());
             System.out.println("----------------------------------------");
            System.out.println("TOTAL ATENDIDAS (REDUCE): ");
            System.out.println("Atendidas: " + gestor.contarTotalAtendidas());
        } catch (IllegalArgumentException e) {
            System.err.println("Error de validación: " + e.getMessage());
        }
    }
}