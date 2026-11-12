from typing import List
from functools import reduce
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
        return list(filter(lambda c: c.getEstado().upper() == estado.upper(), self.__citas))
    
    def obtenerResumenAnonimizado(self) -> List[str]:
        return list(map(lambda c: c.getResumenSeguro(), self.__citas))

    def contarTotalAtendidas(self) -> int:
        return reduce(
            lambda acc, c: acc + (1 if c.getEstado().upper() == "ATENDIDA" else 0),
            self.__citas,
            0
        )

    