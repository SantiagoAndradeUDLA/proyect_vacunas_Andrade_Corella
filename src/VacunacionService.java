import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VacunacionService {
    public List<Vacunacion> vacunaciones = new ArrayList<>();

    public void registrarVacunacion(Vacunacion v) {
        vacunaciones.add(v);
    }

    public List<Vacunacion> historialPorPaciente(String cedula) {
        List<Vacunacion> historial = new ArrayList<>();
        for (Vacunacion v : vacunaciones) {
            if (v.getCedulaPaciente().equals(cedula)) {
                historial.add(v);
            }
        }
        return historial;
    }
}
