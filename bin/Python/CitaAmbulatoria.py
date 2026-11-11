from Cita import Cita
from Paciente import Paciente
from Medico import Medico

class CitaAmbulatoria(Cita):
    def __init__(self, idCita: str, paciente: Paciente, medico: Medico):
        super().__init__(idCita, "AMBULATORIA", paciente, medico)

    def obtenerPrioridad(self) -> str:
        return "Prioridad Media - Consulta Externa"

    def getResumenSeguro(self) -> str:
        return f"Cita Ambulatoria [{self._idCita}] - Paciente: {self._paciente.getDniEnmascarado()} | Médico: {self._medico.getNombreCompleto()} | Estado: {self._estado}"