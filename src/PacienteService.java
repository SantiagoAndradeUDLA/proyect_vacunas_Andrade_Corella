import java.util.ArrayList;
import java.util.List;

public class PacienteService {
    private List<Paciente> pacientes = new ArrayList<>();

    public void registrarPaciente(Paciente p) {
        pacientes.add(p);
    }

    public List<Paciente> listarPacientes() {
        return pacientes;
    }

    public Paciente buscarPorCedula(String cedula) {
        for (Paciente p : pacientes) {
            if (p.getCedula().equals(cedula)) return p;
        }
        return null;
    }

    public List<Paciente> buscarPorNombre(String nombre) {
        List<Paciente> resultados = new ArrayList<>();
        for (Paciente p : pacientes) {
            if (p.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                resultados.add(p);
            }
        }
        return resultados;
    }

    public boolean actualizarPaciente(String cedula, String direccion, int edad, String condicionMedica) {
        Paciente p = buscarPorCedula(cedula);
        if (p != null) {
            p.setDireccion(direccion);
            p.setEdad(edad);
            p.setCondicionMedica(condicionMedica);
            return true;
        }
        return false;
    }
}
