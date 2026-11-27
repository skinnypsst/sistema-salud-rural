package Vista;

import excepciones.DniInvalidoException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import modelo.CitaMedica;
import modelo.Medico;
import modelo.Paciente;
import servicio.CitaFactory;
import servicio.GestorCentroSalud;

public class VentanaSistemaSalud extends JFrame {

    private final GestorCentroSalud gestor = GestorCentroSalud.getInstancia();

    private JTextField txtDni, txtNombres, txtApellidos, txtHistoria;
    private JTextArea areaSalida;

    public VentanaSistemaSalud() {
        super("Posta Médica de Manuel Arévalo | Gestión de Salud");
        cargarDatosBase();
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(680, 480);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panelForm = new JPanel(new GridLayout(5, 2, 8, 8));
        panelForm.setBorder(BorderFactory.createTitledBorder("Registrar Nuevo Paciente"));

        panelForm.add(new JLabel("DNI (8 dígitos):"));
        txtDni = new JTextField();
        panelForm.add(txtDni);

        panelForm.add(new JLabel("Nombres:"));
        txtNombres = new JTextField();
        panelForm.add(txtNombres);

        panelForm.add(new JLabel("Apellidos:"));
        txtApellidos = new JTextField();
        panelForm.add(txtApellidos);

        panelForm.add(new JLabel("Historia Clínica (ej. HC-003):"));
        txtHistoria = new JTextField();
        panelForm.add(txtHistoria);

        JButton btnRegistrar = new JButton("Registrar Paciente");
        JButton btnLimpiar = new JButton("Limpiar Campos");
        panelForm.add(btnRegistrar);
        panelForm.add(btnLimpiar);

        areaSalida = new JTextArea();
        areaSalida.setEditable(false);
        areaSalida.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(areaSalida);
        scroll.setBorder(BorderFactory.createTitledBorder("Salida del Sistema"));

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 8));
        JButton btnReporte = new JButton("Ver Reporte Anonimizado");
        JButton btnEstadisticas = new JButton("Ver Estadísticas (Streams)");
        panelBotones.add(btnReporte);
        panelBotones.add(btnEstadisticas);

        add(panelForm, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manejarRegistroPaciente();
            }
        });
        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnReporte.addActionListener(e -> manejarReporteAnonimizado());
        btnEstadisticas.addActionListener(e -> manejarEstadisticas());
    }

    private void manejarRegistroPaciente() {
        try {
            String dni = txtDni.getText().trim();
            String nombres = txtNombres.getText().trim();
            String apellidos = txtApellidos.getText().trim();
            String historia = txtHistoria.getText().trim();

            if (gestor.buscarPacientePorDni(dni) != null) {
                throw new IllegalArgumentException("Ya existe un paciente registrado con ese DNI.");
            }

            boolean historiaExiste = gestor.obtenerTodasLasPersonas().stream()
                    .filter(per -> per instanceof Paciente)
                    .map(per -> (Paciente) per)
                    .anyMatch(pac -> pac.getNroHistoriaClinica().equalsIgnoreCase(historia));

            if (historiaExiste) {
                throw new IllegalArgumentException("Ya existe un paciente registrado con la Historia Clínica " + historia + ".");
            }

            Paciente p = new Paciente(dni, nombres, apellidos, historia);
            gestor.registrarPaciente(p);

            areaSalida.append("[OK] Paciente registrado: " + p.getNombreCompleto()
                    + " | DNI protegido: " + p.getDniEnmascarado()
                    + " | HC: " + p.getNroHistoriaClinica() + "\n");
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Paciente registrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (DniInvalidoException | IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de validación", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void manejarReporteAnonimizado() {
        areaSalida.setText("=== REPORTE ANONIMIZADO DE CITAS ===\n");
        gestor.obtenerResumenAnonimizado().forEach(linea -> areaSalida.append(linea + "\n"));
    }

    private void manejarEstadisticas() {
        int atendidas = gestor.contarTotalAtendidas();
        int pendientes = gestor.obtenerCitasPorEstado("PENDIENTE").size();
        areaSalida.setText("=== ESTADÍSTICAS DEL CENTRO DE SALUD ===\n");
        areaSalida.append("Citas ATENDIDAS (reduce): " + atendidas + "\n");
        areaSalida.append("Citas PENDIENTES (filter): " + pendientes + "\n");
    }

    private void limpiarCampos() {
        txtDni.setText("");
        txtNombres.setText("");
        txtApellidos.setText("");
        txtHistoria.setText("");
        txtDni.requestFocus();
    }

    private void cargarDatosBase() {
        try {
            if (gestor.buscarPacientePorDni("72849102") == null) {
                Paciente p1 = new Paciente("72849102", "Luis", "Gutierrez Rodriguez", "HC-001");
                Medico m1 = new Medico("10293847", "Dr. Jorge", "Salazar Castillo", "Medicina General");
                gestor.registrarPaciente(p1);
                gestor.registrarMedico(m1);

                CitaMedica c1 = CitaFactory.crearCita("C-101", "Consulta Externa", p1, m1);
                c1.registrarAtencion("Faringitis aguda", "Paracetamol 500mg cada 8h");
                gestor.agendarCita(c1);
            }
        } catch (DniInvalidoException | IllegalArgumentException | IllegalStateException e) {
            System.err.println("Error al cargar datos iniciales: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaSistemaSalud().setVisible(true));
    }
}