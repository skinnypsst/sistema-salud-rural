import sys
from Paciente import Paciente
from Medico import Medico
from CitaMedica import CitaMedica
from CitaFactory import CitaFactory
from GestorCentroSalud import GestorCentroSalud

def main():
    try:
        gestor = GestorCentroSalud.getInstancia()

        p1 = Paciente("72849102", "Luis Gutierrez Rodriguez", "HC-001")
        p2 = Paciente("41928374", "María Mendoza Tirado", "HC-002")
        m1 = Medico("10293847", "Dr. Jorge Salazar Castillo", "Medicina General")

        gestor.registrarPaciente(p1)
        gestor.registrarPaciente(p2)

        c1 = CitaFactory.crearCita("C-101", "Consulta Externa", p1, m1)
        c2 = CitaFactory.crearCita("C-102", "Control", p2, m1)

        gestor.agendarCita(c1)
        gestor.agendarCita(c2)

        c1.registrarAtencion("Faringitis aguda", "Paracetamol 500mg cada 8h")

        print("ROLES (POLIMORFISMO): ")
        print(p1.getNombreCompleto() + " -> " + p1.obtenerRol())
        print(m1.getNombreCompleto() + " -> " + m1.obtenerRol())
        print("----------------------------------------")

        print("REPORTE ANONIMIZADO (MAP): ")
        for resumen in gestor.obtenerResumenAnonimizado():
            print(resumen)
        print("----------------------------------------")
        print("CITAS PENDIENTES (FILTER):")
        print("Pendientes: " + str(len(gestor.obtenerCitasPorEstado("PENDIENTE"))))
        print("----------------------------------------")
        print("TOTAL ATENDIDAS (REDUCE): ")
        print("Atendidas: " + str(gestor.contarTotalAtendidas()))
    except ValueError as e:
        print("Error de validación: " + str(e), file=sys.stderr)

if __name__ == "__main__":
    main()