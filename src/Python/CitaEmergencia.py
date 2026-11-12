from Cita import Cita
from Paciente import Paciente
from Medico import Medico

class CitaEmergencia(Cita):
    def __init__(self, idCita: str, paciente: Paciente, medico: Medico):
        super().__init__(idCita, "EMERGENCIA", paciente, medico)

    def obtenerPrioridad(self) -> str:
        return "Prioridad Alta - Atención Inmediata"
   