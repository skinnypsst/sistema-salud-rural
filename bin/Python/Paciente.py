from Persona import Persona

class Paciente(Persona):
    def __init__(self, dni: str, nombreCompleto: str, nroHistoriaClinica: str):
        super().__init__(dni, nombreCompleto)
        self.__nroHistoriaClinica = nroHistoriaClinica

    def obtenerRol(self) -> str:
        return "Paciente"