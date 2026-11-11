import java.util.ArrayList;
import java.util.List;

public class GestorCentroSalud {
    private static  GestorCentroSalud instancia;
    private List<Paciente> pacientes = new ArrayList<>();
    private List<Cita> citas = new ArrayList<>();

    private GestorCentroSalud(){
        System.out.println("Gestor del centro de salud instanciado correctamente.");
    }

    public static GestorCentroSalud getInstancia(){
        if (instancia == null) {
            instancia = new GestorCentroSalud();
        }
        return instancia;
    }

    public void registrarPaciente(Paciente nuevoPaciente){
        pacientes.add(nuevoPaciente);
    }

    public void agendarCita(Cita nuevaCita){
        citas.add(nuevaCita);
    }

    public List<Cita> getCitasPorEstado(String estado){
       List<Cita> citasPorEstado = new ArrayList<>();
        for(Cita cit: citas){
            if (cit.getEstado().toUpperCase() == estado.toUpperCase()) {
                citasPorEstado.add(cit);
            }  
        }
        return citasPorEstado; 
    }

    //Editar el tipo de retorno al añadir la logica    
    public void getResumenAnonimo(){
        System.out.println("---------------DATOS DE LOS PACIENTES--------------------");
        for (Paciente pac: pacientes){
            System.out.println(pac.getDni());
            System.out.println(pac.getNombreCompleto());
            System.out.println(pac.getnHistoriaClinica());
        }

        System.out.println("--------------- DATOS DE LAS CITAS --------------------");
        for(Cita cit: citas){
            System.out.println("---------------MEDICO--------------------");
            System.out.println( "Dr.(a) " + cit.getMedico().getNombreCompleto() );
            System.out.println("Especialidad: " + cit.getMedico().getEspecialidad());
            System.out.println("---------------PACIENTE-------------------");
            System.out.println("N° de Hsitoria Clinica: " + cit.getPaciente().getnHistoriaClinica());
            System.out.println( "Nombres y Apellidos: " + cit.getPaciente().getNombreCompleto() );
        }
    }

    public List<Cita> citasAtendidas(){
        List<Cita> atendidas = new ArrayList<>();
        for(Cita cit: citas){
            if (cit.getEstado().toUpperCase() == "ATENDIDA" ) {
                atendidas.add(cit);
            }  
        }
        return atendidas; 
    }  
}

