from Paciente import Paciente
from Medico import Medico
from Cita import Cita
from CitaAmbulatoria import CitaAmbulatoria
from CitaEmergencia import CitaEmergencia
from CitaPreventiva import CitaPreventiva

class CitaFactory:
    @staticmethod
    def crearCita(id: str, tipo: str, p: Paciente, m: Medico) -> Cita:
        tipo_normalizado = tipo.strip().upper()

        if tipo_normalizado == "AMBULATORIA":
            return CitaAmbulatoria(id, p, m)
        elif tipo_normalizado == "EMERGENCIA":
            return CitaEmergencia(id, p, m)
        elif tipo_normalizado == "PREVENTIVA":
            return CitaPreventiva(id, p, m)
        else:
            raise ValueError(f"Tipo de cita no válido: {tipo}. Use AMBULATORIA, EMERGENCIA o PREVENTIVA.")


        