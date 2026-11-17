from Persona import Persona
# HERENCIA: Paciente hereda de la clase abstracta Persona (Relación "Es un")
class Paciente(Persona):
    def __init__(self, dni: str, nombreCompleto: str, nroHistoriaClinica: str):
        super().__init__(dni, nombreCompleto)
        self.__nroHistoriaClinica = nroHistoriaClinica

    def getNroHistoriaClinica(self) -> str:
        return self.__nroHistoriaClinica

    def obtenerRol(self) -> str:
        return "Paciente"