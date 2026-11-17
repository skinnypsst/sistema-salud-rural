from typing import List
from functools import reduce
from Paciente import Paciente
from Cita import Cita

# PATRÓN DE DISEÑO SINGLETON: Garantiza una única instancia centralizada del gestor en todo el sistema.
class GestorCentroSalud:
    # RELACIÓN REFLEXIVA: La clase mantiene una referencia estática privada a sí misma.
    __instancia = None

    def __init__(self):
        if GestorCentroSalud.__instancia is not None:
            raise Exception("Esta clase es un Singleton. Use getInstancia().")
        
        # RELACIÓN AGREGACIÓN (1 a 0..*): El gestor agrupa colecciones de Pacientes y Citas que pueden existir independientemente.
        self.__pacientes: List[Paciente] = []
        self.__citas: List[Cita] = []

    # ACCESO GLOBAL SINGLETON: Método de clase que controla la creación y retorno de la instancia única.
    @classmethod
    def getInstancia(cls) -> 'GestorCentroSalud':
        if cls.__instancia is None:
            cls.__instancia = cls()
        return cls.__instancia

    def registrarPaciente(self, p: Paciente) -> None:
        self.__pacientes.append(p)

    def agendarCita(self, c: Cita) -> None:
        self.__citas.append(c)

    # FILTER + LAMBDA): Filtra la colección de citas de forma declarativa sin bucles for
    def obtenerCitasPorEstado(self, estado: str) -> List[Cita]:
        return list(filter(lambda c: c.getEstado().upper() == estado.upper(), self.__citas))
    
    def obtenerResumenAnonimizado(self) -> List[str]:
        return list(map(lambda c: c.getResumenSeguro(), self.__citas))

    # REDUCE + LAMBDA): Acumula y reduce la lista de citas atendidas a un único entero total
    def contarTotalAtendidas(self) -> int:
        return reduce(
            lambda acc, c: acc + (1 if c.getEstado().upper() == "ATENDIDA" else 0),
            self.__citas,
            0
        )

    