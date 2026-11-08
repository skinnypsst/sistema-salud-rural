

public class CitaAmbulatoria extends Cita {
    CitaAmbulatoria(String id, String tipo, String estado, Paciente paciente, Medico medico, AtencionMedica atencionMedica){
        super(id, tipo, estado, paciente, medico, atencionMedica);
    }
    
    @Override
    public void getResumenSeguro() {
        //Imprime el resumen completo de la cita
        System.out.println("--- RESUMEN DE CITA ---");
        System.out.println("Médico: " + getMedico().getNombreCompleto());  
        System.out.println("Paciente: " + getPaciente().getNombreCompleto());
        System.out.println("Tipo: " + getTipo());
        System.out.println("Estado: " + getEstado());
    }
    
    
    
}