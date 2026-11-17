# Abstracción y clase base
from abc import ABC, abstractmethod

class Persona(ABC):
    def __init__(self, dni: str, nombreCompleto: str):
        # ENCAPSULAMIENTO: Atributos privados (__)
        self.__dni = dni
        self.__nombreCompleto = nombreCompleto

    # LEY N.° 29733 - PROTECCIÓN DE DATOS PERSONALES: DNI -> enmascaramiento
    def getDniEnmascarado(self) -> str:
            if len(self.__dni) > 3:
                return "*" * (len(self.__dni) - 3) + self.__dni[-3:]
            return "***"

    # MÉTODOS GETTERS
    def getDni(self) -> str:
        return self.__dni

    def getNombreCompleto(self) -> str:
        return self.__nombreCompleto
    
    # POLIMORFISMO: Método abstracto que obliga a las subclases a definir su propio comportamiento
    @abstractmethod
    def obtenerRol(self) -> str:
        pass