import base64
from Persona import Persona

class AtencionMedica:
    def __init__(self, diagnostico: str, tratamiento: str):
        self.__diagnosticoCifrado = base64.b64encode(diagnostico.encode('utf-8')).decode('utf-8')
        self.__tratamiento = tratamiento

    def getDiagnosticoSeguro(self) -> str:
        return f"[PROTEGIDO-LEY29733: {self.__diagnosticoCifrado[:10]}...]"

    def getDetalleClinicoAutorizado(self, usuario: Persona) -> str:
        if usuario.obtenerRol().upper() in ["MÉDICO", "MEDICO"]:
            diag_real = base64.b64decode(self.__diagnosticoCifrado.encode('utf-8')).decode('utf-8')
            return f"Diagnóstico: {diag_real} | Tratamiento: {self.__tratamiento}"
        raise PermissionError("Acceso denegado: Solo personal médico puede ver datos clínicos.")

    def getTratamiento(self) -> str:
        return self.__tratamiento