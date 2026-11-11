from abc import ABC, abstractmethod

class Persona(ABC):
    def __init__(self, dni: str, nombreCompleto: str):
        self.__dni = dni
        self.__nombreCompleto = nombreCompleto

    def getDniEnmascarado(self) -> str:
            if len(self.__dni) > 3:
                return "*" * (len(self.__dni) - 3) + self.__dni[-3:]
            return "***"
    def getDni(self) -> str:
        return self.__dni

    def getNombreCompleto(self) -> str:
        return self.__nombreCompleto

    @abstractmethod
    def obtenerRol(self) -> str:
        pass