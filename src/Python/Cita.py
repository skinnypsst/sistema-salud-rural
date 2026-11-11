from abc import ABC, abstractmethod
from typing import Optional
from Paciente import Paciente
from Medico import Medico
from AtencionMedica import AtencionMedica

class Cita(ABC):
    def __init__(self, idCita: str, tipoCita: str, paciente: Paciente,
                 medico: Medico):
        self._idCita = idCita
        self._tipoCita = tipoCita
        self._estado = "PROGRAMADA"
        self._paciente = paciente
        self._medico = medico
        self._atencionMedica: Optional[AtencionMedica] = None

    def registrarAtencion(self, diagnostico: str, tratamiento: str):
        self._atencion = AtencionMedica(diagnostico, tratamiento)
        self._estado = "ATENDIDA"

    def getEstado(self) -> str:
        return self._estado

    def getPaciente(self) -> Paciente:
        return self._paciente

    def getMedico(self) -> Medico:
        return self._medico

    @abstractmethod
    def obtenerPrioridad(self) -> str:
        pass

    def ResumenSeguro(self) -> str:
        diagnostico_info = (
            self._atencion.getDiagnosticoSeguro()
            if self._atencion else "Pendiente de atención"
        )
        return (
            f"Cita [{self._idCita}] | Tipo: {self._tipoCita} ({self.obtenerPrioridad()}) | "
            f"Estado: {self._estado} | Paciente DNI: {self._paciente.getDniEnmascarado()} "
            f"(HC: {self._paciente.getNroHistoriaClinica()}) | "
            f"Médico: {self._medico.getNombreCompleto()} ({self._medico.getEspecialidad()}) | "
            f"Diagnóstico: {diagnostico_info} "
        )