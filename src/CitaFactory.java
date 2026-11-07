public abstract class CitaFactory {
    public Cita crearCita(String id, String tipo, String estado, Paciente paciente, Medico medico, AtencionMedica atencionMedica){
        switch (tipo.toLowerCase().trim()) {
            case "emergencia":  
                return new CitaEmergencia(id, tipo, estado, paciente, medico, atencionMedica);
            case "ambulatoria":
                return new CitaAmbulatoria(id, tipo, estado, paciente, medico, atencionMedica);
            case "preventiva":
                return new CitaPreventiva(id, tipo, estado, paciente, medico, atencionMedica);        
            default:
                throw new IllegalArgumentException("Tipo de cita no válido: " + tipo);
        }
    }
    
}