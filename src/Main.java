
public class Main {
    public static void main(String[] args) {

        //Instaciamos el objeto que almacenara las citas y pacientes
        GestorCentroSalud centromedico =  GestorCentroSalud.getInstancia();

        //Fábrica de citas.
        CitaFactory fabricaDeCitas = new CitaFactory();

        //Se intancia un nuevo médico.
        Medico drLuis = new Medico("78458596", "luis hinostroza martinez", "dermatologia");

        //Se instancia un nuevo paciente.
        Paciente alvaro = new Paciente("75501547", "alvaro vasquez castillo");

        //Instancia de una nueva atencion médica.
        AtencionMedica  atencion = new AtencionMedica("alergia al polen y derivados", "hisaler 1 diario por 7 dias");

        //Se intancia una nueva cita de tipo "Emergencia", con los datos instanciados anteriormente.
        Cita nuevaCita = fabricaDeCitas.crearCita("CT-001", "emergencia", "programada", alvaro, drLuis, atencion);


        centromedico.registrarPaciente(alvaro);
        centromedico.agendarCita(nuevaCita);
        centromedico.getResumenAnonimo();
    }
    
}