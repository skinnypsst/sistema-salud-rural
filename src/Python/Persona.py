from abc import ABC, abstractmethod
import re

# Clase Abstracta y Encapsulamiento
class Persona(ABC):
    # Constructor
    def __init__(self, dni: str, nombreCompleto: str):
        if dni is None or not re.match(r"^\d{8}$", dni):
            raise ValueError("El DNI debe contener exactamente 8 digitos numéricos.")
        self.__dni = dni
        self.__nombreCompleto = nombreCompleto

    # Metodos Getters
    def getDniEnmascarado(self) -> str:
        return "****" + self.__dni[4:]

    def getDni(self) -> str:
        return self.__dni

    def getNombreCompleto(self) -> str:
        return self.__nombreCompleto

    @abstractmethod
    def obtenerRol(self) -> str:
        pass