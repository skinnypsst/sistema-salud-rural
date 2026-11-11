class AtencionMedica:
    def __init__(self, diagnostico: str, tratamiento: str):
        self.__diagnosticoCifrado = f"[CIFRADO: {abs(hash(diagnostico))}]"
        self.__tratamiento = tratamiento

    def getDiagnosticoSeguro(self) -> str:
        return self.__diagnosticoCifrado

    def getTratamiento(self) -> str:
        return self.__tratamiento