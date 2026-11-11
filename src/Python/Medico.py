from Persona import Persona

class Medico(Persona):
    def __init__(self, dni: str, nombreCompleto: str, especialidad: str):
        super().__init__(dni, nombreCompleto)
        self.__especialidad = especialidad

    def getEspecialidad(self) -> str:
        return self.__especialidad

    def obtenerRol(self) -> str:
        return "Médico"