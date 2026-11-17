from Persona import Persona

# Herencia y Polimorfismo
class Paciente(Persona):
    # Constructor
    def __init__(self, dni: str, nombreCompleto: str, nroHistoriaClinica: str):
        super().__init__(dni, nombreCompleto)
        self.__nroHistoriaClinica = nroHistoriaClinica

    # Metodos Getters
    def getNroHistoriaClinica(self) -> str:
        return self.__nroHistoriaClinica

    def obtenerRol(self) -> str:
        return "Paciente (HC: " + self.__nroHistoriaClinica + ")"