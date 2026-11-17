from abc import ABC, abstractmethod
from typing import Optional
from Paciente import Paciente
from Medico import Medico
from Persona import Persona
from AtencionMedica import AtencionMedica

#ABSTRACCIÓN: Clase base abstracta para los distintos tipos de citas médicas
class Cita(ABC):
    def __init__(self, idCita: str, tipoCita: str, paciente: Paciente, medico: Medico):
        # ENCAPSULAMIENTO PROTEGIDO: Atributos protegidos (_) accesibles por las subclases hijas
        self._idCita = idCita
        self._tipoCita = tipoCita
        self._estado = "PROGRAMADA"

        # RELACIÓN BINARIA: Relación estructural entre Cita y Paciente (1 a 1) y entre Cita y Medico (1 a 1)
        self._paciente = paciente
        self._medico = medico

        # COMPOSICIÓN (0..1)]: Referencia a AtencionMedica que nace vacía y vive únicamente dentro de Cita
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

    # REPORTE ANONIMIZADO: diagnóstico protegido
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


class CitaAmbulatoria(Cita):
    def __init__(self, idCita: str, paciente: Paciente, medico: Medico):
        super().__init__(idCita, "AMBULATORIA", paciente, medico)

    def obtenerPrioridad(self) -> str:
        return "Prioridad Media - Consulta Externa"


class CitaEmergencia(Cita):
    def __init__(self, idCita: str, paciente: Paciente, medico: Medico):
        super().__init__(idCita, "EMERGENCIA", paciente, medico)

    def obtenerPrioridad(self) -> str:
        return "Prioridad Alta - Atención Inmediata"


class CitaPreventiva(Cita):
    def __init__(self, idCita: str, paciente: Paciente, medico: Medico):
        super().__init__(idCita, "PREVENTIVA", paciente, medico)

    def obtenerPrioridad(self) -> str:
        return "Prioridad Baja - Control y Prevención"