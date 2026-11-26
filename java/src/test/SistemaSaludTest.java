package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import excepciones.DniInvalidoException;
import modelo.CitaMedica;
import modelo.Medico;
import modelo.Paciente;
import servicio.CitaFactory;
import servicio.GestorCentroSalud;

public class SistemaSaludTest {

    private Paciente pacientePrueba;
    private Medico medicoPrueba;

    @BeforeEach
    void setUp() throws DniInvalidoException {
        pacientePrueba = new Paciente("87654321", "Ana", "Paredes Rojas", "HC-010");
        medicoPrueba = new Medico("12345678", "Dr. Carlos", "Vega Luna", "Medicina General");
    }

    @Test
    @DisplayName("1. Validar lanzamiento de DniInvalidoException con DNI menor a 8 dígitos")
    void testDniInvalidoLanzaExcepcion() {
        DniInvalidoException ex = assertThrows(DniInvalidoException.class, () -> {
            new Paciente("7626", "Pedro", "Soto", "HC-011");
        });
        assertEquals("El DNI debe contener exactamente 8 dígitos numéricos.", ex.getMessage());
    }

    @Test
    @DisplayName("2. Verificar protección de datos personales (DNI enmascarado y diagnóstico Base64 - Ley N.° 29733)")
    void testProteccionDatosPersonales() {
        CitaMedica cita = CitaFactory.crearCita("C-201", "Consulta Externa", pacientePrueba, medicoPrueba);
        cita.registrarAtencion("Gastritis aguda", "Omeprazol 20mg");

        assertEquals("****4321", pacientePrueba.getDniEnmascarado());
        assertNotEquals("Gastritis aguda", cita.getAtencion().getDiagnosticoSeguro());
        assertFalse(cita.getResumenSeguro().contains("87654321"));
        assertFalse(cita.getResumenSeguro().contains("Gastritis aguda"));
    }

    @Test
    @DisplayName("3. Verificar patrón Singleton en GestorCentroSalud")
    void testPatronSingleton() {
        GestorCentroSalud instancia1 = GestorCentroSalud.getInstancia();
        GestorCentroSalud instancia2 = GestorCentroSalud.getInstancia();

        assertSame(instancia1, instancia2, "Ambas referencias deben apuntar a la misma instancia única.");
    }

    @Test
    @DisplayName("4. Verificar filtrado y conteo funcional (filter, map, reduce) de citas atendidas")
    void testEstadisticasFuncionalesCitas() {
        GestorCentroSalud gestor = GestorCentroSalud.getInstancia();
        int atendidasAntes = gestor.contarTotalAtendidas();

        CitaMedica cita1 = CitaFactory.crearCita("C-301", "Control", pacientePrueba, medicoPrueba);
        CitaMedica cita2 = CitaFactory.crearCita("C-302", "Urgencia", pacientePrueba, medicoPrueba);

        cita1.registrarAtencion("Control sano");
        gestor.agendarCita(cita1);
        gestor.agendarCita(cita2);

        assertEquals(atendidasAntes + 1, gestor.contarTotalAtendidas());
        assertTrue(gestor.obtenerCitasPorEstado("PENDIENTE").stream()
                .anyMatch(c -> c.getIdCita().equals("C-302")));
    }
}