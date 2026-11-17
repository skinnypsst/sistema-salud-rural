from Paciente import Paciente
from Medico import Medico
from CitaMedica import CitaMedica

# Patron Factory Method
class CitaFactory:
    @staticmethod
    def crearCita(id: str, tipo: str, p: Paciente, m: Medico) -> CitaMedica:
        if p is None or m is None:
            raise ValueError("El paciente y el médico son obligatorios.")
        return CitaMedica(id, tipo, p, m)