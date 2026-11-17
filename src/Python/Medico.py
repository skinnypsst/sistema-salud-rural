from Persona import Persona

class Medico(Persona):
    # Constructor
    def __init__(self, dni: str, nombreCompleto: str, especialidad: str):
        super().__init__(dni, nombreCompleto)
        self.__especialidad = especialidad

    # Metodos Getters
    def getEspecialidad(self) -> str:
        return self.__especialidad

    def obtenerRol(self) -> str:
        return "Médico Especialista en " + self.__especialidad