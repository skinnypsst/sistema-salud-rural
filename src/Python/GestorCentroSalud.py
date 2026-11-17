from typing import List
from functools import reduce
from Paciente import Paciente
from CitaMedica import CitaMedica

class GestorCentroSalud:
    __instancia = None

    def __init__(self):
        if GestorCentroSalud.__instancia is not None:
            raise Exception("Esta clase es un Singleton. Use getInstancia().")
        self.__pacientes: List[Paciente] = []
        self.__citas: List[CitaMedica] = []

    @classmethod
    def getInstancia(cls) -> 'GestorCentroSalud':
        if cls.__instancia is None:
            cls.__instancia = cls()
        return cls.__instancia

    def registrarPaciente(self, p: Paciente) -> None:
        self.__pacientes.append(p)

    def agendarCita(self, c: CitaMedica) -> None:
        self.__citas.append(c)

    # Tema 4: filter + lambda
    def obtenerCitasPorEstado(self, estado: str) -> List[CitaMedica]:
        return list(filter(lambda c: c.getEstado().upper() == estado.upper(), self.__citas))

    # Tema 4: map + lambda
    def obtenerResumenAnonimizado(self) -> List[str]:
        return list(map(lambda c: c.getResumenSeguro(), self.__citas))

    # Tema 4: filter + map + reduce + lambda
    def contarTotalAtendidas(self) -> int:
        citas_atendidas = filter(lambda c: c.getEstado().upper() == "ATENDIDA", self.__citas)
        valores = map(lambda c: 1, citas_atendidas)
        return reduce(lambda acumulado, valor: acumulado + valor, valores, 0)