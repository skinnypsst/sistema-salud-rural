package Vista;

import excepciones.DniInvalidoException;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import modelo.*;
import servicio.CitaFactory;
import servicio.GestorCentroSalud;

public class VentanaSistemaSalud extends JFrame {

    private GestorCentroSalud gestor = GestorCentroSalud.getInstancia();

    public VentanaSistemaSalud() {
        setTitle("SISTEMA DE SALUD | POSTA MEDICA MANUEL ARÉVALO");
        setSize(560, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cargarDatosIniciales();

        // Panel principal con márgenes para que no quede pegado a los bordes
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 1, 10, 10));
        panel.setBackground(new Color(235, 242, 248));
        panel.setBorder(new EmptyBorder(20, 40, 20, 40));

        // Título estilizado
        JLabel titulo = new JLabel("SISTEMA DE SALUD | POSTA MEDICA MANUEL ARÉVALO");
        titulo.setHorizontalAlignment(JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setForeground(new Color(25, 75, 120));

        // Botones del menú
        JButton btnPaciente = new JButton("Registrar paciente");
        JButton btnCita = new JButton("Agendar cita");
        JButton btnAtencion = new JButton("Registrar atención");
        JButton btnMedico = new JButton("Registrar médico");
        JButton btnRoles = new JButton("Listar roles");
        JButton btnReporte = new JButton("Reporte anonimizado");
        JButton btnEstadisticas = new JButton("Filtrar citas y total atendidas");
        JButton btnSalir = new JButton("Salir");

        // Colores y fuente para los botones
        Color azul = new Color(52, 130, 190);
        Color rojo = new Color(200, 65, 65);

        darEstiloBoton(btnPaciente, azul);
        darEstiloBoton(btnCita, azul);
        darEstiloBoton(btnAtencion, azul);
        darEstiloBoton(btnMedico, azul);
        darEstiloBoton(btnRoles, azul);
        darEstiloBoton(btnReporte, azul);
        darEstiloBoton(btnEstadisticas, azul);
        darEstiloBoton(btnSalir, rojo);

        panel.add(titulo);
        panel.add(btnPaciente);
        panel.add(btnCita);
        panel.add(btnAtencion);
        panel.add(btnMedico);
        panel.add(btnRoles);
        panel.add(btnReporte);
        panel.add(btnEstadisticas);
        panel.add(btnSalir);

        add(panel);

        // Cada listener llama al método de su opción.
        btnPaciente.addActionListener(e -> registrarPaciente());
        btnCita.addActionListener(e -> agendarCita());
        btnAtencion.addActionListener(e -> registrarAtencion());
        btnMedico.addActionListener(e -> registrarMedico());
        btnRoles.addActionListener(e -> listarRoles());
        btnReporte.addActionListener(e -> mostrarReporteAnonimizado());
        btnEstadisticas.addActionListener(e -> mostrarEstadisticas());
        btnSalir.addActionListener(e -> System.exit(0));
    }

    // Método sencillo para aplicar color y fuente a cada botón.
    private void darEstiloBoton(JButton boton, Color colorFondo) {
        boton.setBackground(colorFondo);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setFocusPainted(false);
    }

    // Solicita un dato obligatorio.
    private String pedirDato(String mensaje) {
        while (true) {
            String dato = JOptionPane.showInputDialog(this, mensaje);

            if (dato == null) {
                return null;
            }

            if (dato.trim().isEmpty()) {
                mostrarError("Este campo es obligatorio.");
            } else {
                return dato.trim();
            }
        }
    }

    // Solicita un DNI y comprueba que tenga exactamente 8 números.
    private String pedirDni(String mensaje) {
        while (true) {
            String dni = JOptionPane.showInputDialog(this, mensaje);

            if (dni == null) {
                return null;
            }

            dni = dni.trim();

            try {
                if (!dni.matches("\\d{8}")) {
                    throw new DniInvalidoException(
                            "El DNI debe contener exactamente 8 dígitos numéricos.");
                }
                return dni;
            } catch (DniInvalidoException ex) {
                mostrarError(ex.getMessage());
            }
        }
    }

    // Lista los médicos registrados en el sistema para facilitar elegir el DNI.
    private String listarMedicosDisponibles() {
        StringBuilder sb = new StringBuilder();
        boolean hayMedicos = false;

        for (Persona persona : gestor.obtenerTodasLasPersonas()) {
            if (persona instanceof Medico medico) {
                hayMedicos = true;
                sb.append("• ")
                  .append(medico.getNombreCompleto())
                  .append(" | Esp.: ").append(medico.getEspecialidad())
                  .append(" | DNI: ").append(medico.getDni())
                  .append("\n");
            }
        }

        if (!hayMedicos) {
            return "No hay médicos registrados aún.";
        }
        return sb.toString();
    }

    // OPCIÓN 1: REGISTRAR PACIENTE
    private void registrarPaciente() {
        try {
            String dni = pedirDni("Ingrese el DNI del paciente (8 dígitos):");
            if (dni == null) return;

            if (gestor.buscarPacientePorDni(dni) != null) {
                throw new IllegalArgumentException(
                        "Ya existe un paciente registrado con ese DNI.");
            }

            String nombres = pedirDato("Ingrese los nombres:");
            if (nombres == null) return;

            String apellidos = pedirDato("Ingrese los apellidos:");
            if (apellidos == null) return;

            // Se genera automáticamente (ej. HC-001, HC-002...)
            String historia = gestor.generarCodigoHistoriaClinica();

            Paciente paciente = new Paciente(dni, nombres, apellidos, historia);
            gestor.registrarPaciente(paciente);

            JOptionPane.showMessageDialog(this,
                    "REGISTRO EXITOSO\nPaciente: " + paciente.getNombreCompleto()
                    + "\nHistoria clínica: " + paciente.getNroHistoriaClinica()
                    + "\nDNI protegido: " + paciente.getDniEnmascarado(),
                    "Paciente registrado",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (DniInvalidoException | IllegalArgumentException ex) {
            mostrarError(ex.getMessage());
        }
    }

    // OPCIÓN 2: AGENDAR CITA
    private void agendarCita() {
        try {
            String tipo = pedirDato("Ingrese el tipo de cita:");
            if (tipo == null) return;

            String dniPaciente = pedirDni("Ingrese el DNI del paciente:");
            if (dniPaciente == null) return;

            Paciente paciente = gestor.buscarPacientePorDni(dniPaciente);
            if (paciente == null) {
                throw new IllegalArgumentException(
                        "No existe un paciente registrado con ese DNI.");
            }

            String mensajeMedico = "Médicos registrados:\n"
                    + listarMedicosDisponibles()
                    + "\nIngrese el DNI del médico:";

            String dniMedico = pedirDni(mensajeMedico);
            if (dniMedico == null) return;

            Medico medico = gestor.buscarMedicoPorDni(dniMedico);
            if (medico == null) {
                throw new IllegalArgumentException(
                        "No existe un médico registrado con ese DNI.");
            }

            CitaMedica cita = CitaFactory.crearCita(tipo, paciente, medico);
            gestor.agendarCita(cita);

            JOptionPane.showMessageDialog(this,
                    "CITA AGENDADA CORRECTAMENTE\n"
                    + "ID: " + cita.getIdCita()
                    + "\nTipo: " + cita.getTipoCita()
                    + "\nPaciente: " + paciente.getNombreCompleto()
                    + "\nMédico: " + medico.getNombreCompleto(),
                    "Cita registrada",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (IllegalArgumentException ex) {
            mostrarError(ex.getMessage());
        }
    }

    // OPCIÓN 3: REGISTRAR ATENCIÓN
    private void registrarAtencion() {
        String id = pedirDato("Ingrese el ID de la cita (ej. C-101):");
        if (id == null) return;

        CitaMedica cita = gestor.buscarCitaPorId(id);

        if (cita == null) {
            mostrarError("Cita no encontrada.");
            return;
        }

        if ("ATENDIDA".equals(cita.getEstado())) {
            mostrarError("La cita " + cita.getIdCita()
                    + " ya fue atendida previamente.");
            return;
        }

        try {
            String diagnostico = pedirDato("Ingrese el diagnóstico:");
            if (diagnostico == null) return;

            int respuesta = JOptionPane.showConfirmDialog(this,
                    "¿Desea ingresar un tratamiento específico?",
                    "Tratamiento",
                    JOptionPane.YES_NO_OPTION);

            if (respuesta == JOptionPane.YES_OPTION) {
                String tratamiento = pedirDato("Ingrese el tratamiento:");
                if (tratamiento == null) return;
                cita.registrarAtencion(diagnostico, tratamiento);
            } else if (respuesta == JOptionPane.NO_OPTION) {
                cita.registrarAtencion(diagnostico);
            } else {
                return;
            }

            JOptionPane.showMessageDialog(this,
                    "ATENCIÓN REGISTRADA CORRECTAMENTE\n"
                    + cita.getResumenSeguro(),
                    "Atención completada",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (IllegalArgumentException | IllegalStateException ex) {
            mostrarError(ex.getMessage());
        }
    }

    // OPCIÓN 4: REGISTRAR MÉDICO
    private void registrarMedico() {
        try {
            String dni = pedirDni("Ingrese el DNI del médico (8 dígitos):");
            if (dni == null) return;

            if (gestor.buscarMedicoPorDni(dni) != null) {
                throw new IllegalArgumentException(
                        "Ya existe un médico registrado con ese DNI.");
            }

            String nombres = pedirDato("Ingrese los nombres:");
            if (nombres == null) return;

            String apellidos = pedirDato("Ingrese los apellidos:");
            if (apellidos == null) return;

            String especialidad = pedirDato("Ingrese la especialidad:");
            if (especialidad == null) return;

            Medico medico = new Medico(dni, nombres, apellidos, especialidad);
            gestor.registrarMedico(medico);

            JOptionPane.showMessageDialog(this,
                    "MÉDICO REGISTRADO CORRECTAMENTE\n"
                    + "Nombre: " + medico.getNombreCompleto()
                    + "\nEspecialidad: " + medico.getEspecialidad()
                    + "\nDNI protegido: " + medico.getDniEnmascarado(),
                    "Médico registrado",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (DniInvalidoException | IllegalArgumentException ex) {
            mostrarError(ex.getMessage());
        }
    }

    // OPCIÓN 5: LISTAR ROLES
    private void listarRoles() {
        String texto = "=== PERSONAS REGISTRADAS ===\n\n";

        for (Persona persona : gestor.obtenerTodasLasPersonas()) {
            texto += persona.getNombreCompleto()
                    + " (" + persona.getDniEnmascarado() + ")"
                    + " -> " + persona.obtenerRol() + "\n";
        }

        if (gestor.obtenerTodasLasPersonas().isEmpty()) {
            texto += "No hay personas registradas.";
        }

        mostrarResultado(texto);
    }

    // OPCIÓN 6: REPORTE ANONIMIZADO
    private void mostrarReporteAnonimizado() {
        String texto = "=== REPORTE ANONIMIZADO DE CITAS ===\n\n";

        for (String linea : gestor.obtenerResumenAnonimizado()) {
            texto += linea + "\n";
        }

        if (gestor.obtenerResumenAnonimizado().isEmpty()) {
            texto += "No hay citas registradas.";
        }

        mostrarResultado(texto);
    }

    // OPCIÓN 7: FILTRAR CITAS Y TOTAL ATENDIDAS
    private void mostrarEstadisticas() {
        String estado = pedirDato("Estado a filtrar (PENDIENTE / ATENDIDA):");
        if (estado == null) return;

        if (!estado.equalsIgnoreCase("PENDIENTE")
                && !estado.equalsIgnoreCase("ATENDIDA")) {
            mostrarError("Ingrese PENDIENTE o ATENDIDA.");
            return;
        }

        String texto = "=== CITAS " + estado.toUpperCase() + " ===\n\n";

        for (CitaMedica cita : gestor.obtenerCitasPorEstado(estado)) {
            texto += cita.getResumenSeguro() + "\n\n";
        }

        texto += "Encontradas: "
                + gestor.obtenerCitasPorEstado(estado).size() + "\n";
        texto += "Total de citas atendidas: "
                + gestor.contarTotalAtendidas();

        mostrarResultado(texto);
    }

    // Muestra mensajes de error.
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje,
                "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Muestra resultados en una ventana con área de texto.
    private void mostrarResultado(String texto) {
        JTextArea area = new JTextArea(texto, 15, 45);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(new Font("Monospaced", Font.PLAIN, 13));

        JOptionPane.showMessageDialog(this,
                new JScrollPane(area),
                "Resultado",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // Carga los médicos iniciales sin pacientes ni citas precargadas.
    private void cargarDatosIniciales() {
        try {
            if (gestor.buscarMedicoPorDni("10293847") == null) {
                Medico m1 = new Medico("10293847",
                        "Dr. Jorge", "Salazar Castillo", "Medicina General");
                Medico m2 = new Medico("20458912",
                        "Dra. Carmen", "Quispe Vargas", "Pediatría");

                gestor.registrarMedico(m1);
                gestor.registrarMedico(m2);
            }
        } catch (DniInvalidoException | IllegalArgumentException ex) {
            System.out.println("Error al cargar datos iniciales: "
                    + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaSistemaSalud ventana = new VentanaSistemaSalud();
            ventana.setVisible(true);
        });
    }
}