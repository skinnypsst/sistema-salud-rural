package Java;
// Composición y Ley N.° 29733
import java.util.Base64;
public class AtencionMedica {
    private String diagnosticoCifrado;
    private String tratamiento;

    // Constructor
    public AtencionMedica(String diagnostico, String tratamiento){
        this.diagnosticoCifrado = Base64.getEncoder() .encodeToString(diagnostico.getBytes());
        this.tratamiento = tratamiento;
    }

    //Metodos Getters
    public String getDiagnosticoSeguro() {
        return diagnosticoCifrado;
    }

    public String getTratamiento() {
        return tratamiento;
    } 
}