import base64
from Persona import Persona
# COMPOSICIÓN: Clase que representa el detalle clínico y depende del ciclo de vida de Cita
class AtencionMedica:
    def __init__(self, diagnostico: str, tratamiento: str):
        self.__diagnosticoCifrado = base64.b64encode(diagnostico.encode('utf-8')).decode('utf-8')
        self.__tratamiento = tratamiento

    # ENCAPSULAMIENTO y LEY N.° 29733: Codificación en Base64 para no guardar ni exponer el diagnóstico en texto plano
    def getDiagnosticoSeguro(self) -> str:
        return f"[PROTEGIDO-LEY29733: {self.__diagnosticoCifrado[:10]}...]"

    # POLIMORFISMO Y CONTROL DE ACCESO: Verifica polimórficamente el rol antes de decodificar datos sensibles.
    def getDetalleClinicoAutorizado(self, usuario: Persona) -> str:
        if usuario.obtenerRol().upper() in ["MÉDICO", "MEDICO"]:
            diag_real = base64.b64decode(self.__diagnosticoCifrado.encode('utf-8')).decode('utf-8')
            return f"Diagnóstico: {diag_real} | Tratamiento: {self.__tratamiento}"
        raise PermissionError("Acceso denegado: Solo personal médico puede ver datos clínicos.")

    def getTratamiento(self) -> str:
        return self.__tratamiento