from abc import ABC, abstractmethod
from typing import Optional
from Paciente import Paciente
from Medico import Medico
from Persona import Persona
from AtencionMedica import AtencionMedica

class Cita(ABC):
    def __init__(self, idCita: str, tipoCita: str, paciente: Paciente,
                 medico: Medico):
        self._idCita = idCita
        self._tipoCita = tipoCita
        self._estado = "PROGRAMADA"
        self._paciente = paciente
        self._medico = medico
        self._atencion: Optional[AtencionMedica] = None

    def registrarAtencion(self, diagnostico: str, tratamiento: str) -> None:
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

    def getResumenSeguro(self) -> str:
        if self._atencion is not None:
            info_clinica = (
                f"Diagnóstico: {self._atencion.getDiagnosticoSeguro()} | "
                f"Tratamiento: {self._atencion.getTratamiento()}"
            )
        else:
            info_clinica = "Diagnóstico: Pendiente de atención"

        return (
            f"Cita [{self._idCita}] | Tipo: {self._tipoCita} ({self.obtenerPrioridad()}) | "
            f"Estado: {self._estado} | Paciente DNI: {self._paciente.getDniEnmascarado()} "
            f"(HC: {self._paciente.getNroHistoriaClinica()}) | "
            f"Médico: {self._medico.getNombreCompleto()} ({self._medico.getEspecialidad()}) | "
            f"{info_clinica}"
        )

    def verFichaClinicaMedica(self, solicitante: Persona) -> str:
        if self._atencion is None:
            return f"Cita [{self._idCita}]: Sin atención registrada aún."
        detalle = self._atencion.getDetalleClinicoAutorizado(solicitante)
        return f"Cita [{self._idCita}] - Paciente: {self._paciente.getNombreCompleto()} -> {detalle}"