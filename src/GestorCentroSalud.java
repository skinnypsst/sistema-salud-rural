import java.util.List;

public class GestorCentroSalud {
    private static  GestorCentroSalud instancia;
    private List<Paciente> pacientes;
    private List<Cita> citas;

    private  GestorCentroSalud(){
        System.out.println("gestor del centro de salud instanciado.");

    }

    public GestorCentroSalud getInstancia(){
        if (instancia == null) {
            instancia = new GestorCentroSalud();
        }
        return instancia;
    }

    public void registrarPaciente(){

    }

    public void agendarCita(){

    }

    public List<Cita> getCitasPorEstado(){
        return citas;
    }

    //Editar el tipo de retorno al añadir la logica    
    public void getResumenAnonimo(){

    }

    public List<Cita> citasAtendidas(){
        return citas;
    }  
}

