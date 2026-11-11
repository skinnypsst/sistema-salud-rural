from Cita import Cita
from Paciente import Paciente
from Medico import Medico

class CitaPreventiva(Cita):
    def __init__(self, idCita: str, paciente: Paciente, medico: Medico):
        super().__init__(idCita, "PREVENTIVA", paciente, medico)

    def obtenerPrioridad(self) -> str:
        return "Prioridad Baja - Control y Prevención"

    def getResumenSeguro(self) -> str:
            return f"Cita Preventiva [{self._idCita}] - Paciente: {self._paciente.getDniEnmascarado()} | Médico: {self._medico.getNombreCompleto()} | Estado: {self._estado}"