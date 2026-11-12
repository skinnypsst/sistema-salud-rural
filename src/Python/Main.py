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

    # 5. El Médico realiza la Atención Médica sobre la Cita
    med_general.atenderCita(cita1, "Faringitis aguda", "Paracetamol 500mg cada 8h")
    med_urgencias.atenderCita(cita2, "Cuadro de deshidratación severa", "Rehidratación intravenosa inmediata")

   # 6. Reporte Anonimizado por consola
    print(" " + "=" * 85)
    print("                REPORTE ANONIMIZADO DEL CENTRO DE SALUD")
    print("=" * 85)
    for resumen in gestor.obtenerResumenAnonimizado():
        print(resumen)
        print("-" * 85)  

    # 7. Vista clínica autorizada (Exclusiva para médicos)
    print(" " + "=" * 85)
    print("          VISTA CLÍNICA AUTORIZADA (EXCLUSIVA PARA MÉDICOS)")
    print("=" * 85)
    try:
        print(cita1.verFichaClinicaMedica(med_general))
        print()  # Espacio en blanco entre fichas
        print(cita2.verFichaClinicaMedica(med_urgencias))
        print()
        print(cita1.verFichaClinicaMedica(pac1))
    except PermissionError as e:
        print(f"[Bloqueo de Seguridad Ley 29733]: {e}")

    print(" " + "=" * 85)
    print("                 ESTADÍSTICAS DEL CENTRO DE SALUD")
    print("=" * 85)
    print(f"  • Citas Atendidas  : {gestor.contarTotalAtendidas()}")
    print(f"  • Citas Programadas: {len(gestor.obtenerCitasPorEstado('PROGRAMADA'))}")
    print("=" * 85 + " ")

if __name__ == "__main__":
    main()