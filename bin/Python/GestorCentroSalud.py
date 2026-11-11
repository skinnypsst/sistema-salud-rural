from typing import List
from Paciente import Paciente
from Cita import Cita

class GestorCentroSalud:
    __instancia = None

    def __init__(self):
        if GestorCentroSalud.__instancia is not None:
            raise Exception("Esta clase es un Singleton. Use getInstancia().")
        self.__pacientes: List[Paciente] = []
        self.__citas: List[Cita] = []

    @classmethod
    def getInstancia(cls) -> 'GestorCentroSalud':
        if cls.__instancia is None:
            cls.__instancia = cls()
        return cls.__instancia

    def registrarPaciente(self, p: Paciente) -> None:
        self.__pacientes.append(p)

    def agendarCita(self, c: Cita) -> None:
        self.__citas.append(c)

    def obtenerCitasPorEstado(self, estado: str) -> List[Cita]:
        return [c for c in self.__citas if c.getEstado().upper() == estado.upper()]

    def obtenerResumenAnonimizado(self) -> List[str]:
        return [c.getResumenSeguro() for c in self.__citas]

    def contarTotalAtendidas(self) -> int:
        return len(self.obtenerCitasPorEstado("ATENDIDA"))

    