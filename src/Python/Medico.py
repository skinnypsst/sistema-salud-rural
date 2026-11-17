from Persona import Persona

# HERENCIA: Medico hereda atributos y métodos de la superclase Persona (Relación "Es un")
class Medico(Persona):
    def __init__(self, dni: str, nombreCompleto: str, especialidad: str):
        super().__init__(dni, nombreCompleto)
        self.__especialidad = especialidad  #ENCAPSULAMIENTO

    def getEspecialidad(self) -> str:
        return self.__especialidad

    def obtenerRol(self) -> str:
        return "Médico"

    # ASOCIACIÓN: El médico interactúa con el objeto Cita validando su asignación
    def atenderCita(self, cita, diagnostico: str, tratamiento: str) -> None:
        if cita.getMedico() != self:
            raise PermissionError(f"El médico {self.getNombreCompleto()} no está asignado a esta cita.")
        cita.registrarAtencion(diagnostico, tratamiento)