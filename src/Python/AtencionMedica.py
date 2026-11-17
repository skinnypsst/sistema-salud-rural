import base64

# Composición y Ley N.° 29733
class AtencionMedica:
    # Constructor
    def __init__(self, diagnostico: str, tratamiento: str):
        self.__diagnosticoCifrado = base64.b64encode(diagnostico.encode('utf-8')).decode('utf-8')
        self.__tratamiento = tratamiento

    # Metodos Getters
    def getDiagnosticoSeguro(self) -> str:
        return self.__diagnosticoCifrado

    def getTratamiento(self) -> str:
        return self.__tratamiento