from typing import Optional
from Paciente import Paciente
from Medico import Medico
from AtencionMedica import AtencionMedica

# Asociación y Composición
class CitaMedica:
    def __init__(self, idCita: str, tipoCita: str, paciente: Paciente, medico: Medico):
        self.__idCita = idCita
        self.__tipoCita = tipoCita
        self.__estado = "PENDIENTE"
        self.__paciente = paciente
        self.__medico = medico
        self.__atencion: Optional[AtencionMedica] = None

    def registrarAtencion(self, diagnostico: str, tratamiento: str) -> None:
        self.__atencion = AtencionMedica(diagnostico, tratamiento)
        self.__estado = "ATENDIDA"

    # Metodos Getters
    def getEstado(self) -> str:
        return self.__estado

    def getPaciente(self) -> Paciente:
        return self.__paciente

    def getMedico(self) -> Medico:
        return self.__medico

    def getResumenSeguro(self) -> str:
        diag = self.__atencion.getDiagnosticoSeguro() if self.__atencion is not None else "PENDIENTE_ATENCION"
        return (
            f"[{self.__idCita} - {self.__tipoCita}] Paciente: {self.__paciente.getNombreCompleto()} "
            f"(DNI: {self.__paciente.getDniEnmascarado()}) | Médico: {self.__medico.getNombreCompleto()} | "
            f"Estado: {self.__estado} | Dx(Base64): {diag}"
        )