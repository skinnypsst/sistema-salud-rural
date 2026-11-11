from Paciente import Paciente
from Medico import Medico
from CitaFactory import CitaFactory
from GestorCentroSalud import GestorCentroSalud

def main():
    # 1. Instacia única de GestorCentroSalud (Singleton)
    gestor = GestorCentroSalud.getInstancia()

    # 2. Registro de Médicos y Pacientes
    med_general = Medico("40112233", "Dr. Alberto Ríos", "Medicina General")
    med_urgencias = Medico("45667788", "Dra. Lucía Paredes", "Emergencias")

    pac1 = Paciente("71234567", "Luis Torres", "HC-001")
    pac2 = Paciente("76543210", "Ana Vargas", "HC-002")
    pac3 = Paciente("48991122", "Pedro Quispe", "HC-003")

    gestor.registrarPaciente(pac1)
    gestor.registrarPaciente(pac2)
    gestor.registrarPaciente(pac3)

    # 3. Creación de los 3 tipos de citas mediante CitaFactory
    cita1 = CitaFactory.crearCita("C-101", "AMBULATORIA", pac1, med_general)
    cita2 = CitaFactory.crearCita("C-102", "EMERGENCIA", pac2, med_urgencias)
    cita3 = CitaFactory.crearCita("C-103", "PREVENTIVA", pac3, med_general)

    # 4. Agendamiento en el Gestor
    gestor.agendarCita(cita1)
    gestor.agendarCita(cita2)
    gestor.agendarCita(cita3)

    # 5. Registro de Atención Médica (Composición) en dos de las citas
    cita1.registrarAtencion("Faringitis aguda", "Paracetamol 500mg cada 8h")
    cita2.registrarAtencion("Cuadro de deshidratación severa", "Rehidratación intravenosa inmediata")

    # 6. Reporte por consola
    print("=== REPORTE ANONIMIZADO DEL CENTRO DE SALUD ===")
    for resumen in gestor.obtenerResumenAnonimizado():
        print(resumen)

    print("\n=== ESTADÍSTICAS ===")
    print(f"Citas Atendidas  : {gestor.contarTotalAtendidas()}")
    print(f"Citas Programadas: {len(gestor.obtenerCitasPorEstado('PROGRAMADA'))}")

if __name__ == "__main__":
    main()


    