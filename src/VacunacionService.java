import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class VacunacionService {
    private Map<String, Vacuna> catalogoVacunas = new HashMap<>();
    private Map<String, Map<String, Integer>> registroVacunas = new HashMap<>();
    private List<Vacunacion> vacunaciones = new ArrayList<>();

    public VacunacionService() {
        // Vacunas por defecto
        catalogoVacunas.put("Covid", new Vacuna("Covid", 3));
        catalogoVacunas.put("Influenza", new Vacuna("Influenza", 1));
        catalogoVacunas.put("Fiebre Amarilla", new Vacuna("Fiebre Amarilla", 1));
        catalogoVacunas.put("Gripe", new Vacuna("Gripe", 1));
        catalogoVacunas.put("Tosferina", new Vacuna("Tosferina", 1));
    }

    public void agregarVacunaAlCatalogo(String nombre, int dosisRequeridas) {
        if (!catalogoVacunas.containsKey(nombre)) {
            catalogoVacunas.put(nombre, new Vacuna(nombre, dosisRequeridas));
            System.out.println("Vacuna '" + nombre + "' agregada al catálogo con " + dosisRequeridas + " dosis.");
        } else {
            System.out.println("La vacuna ya existe en el catálogo.");
        }
    }

    public void administrarVacuna(String cedulaPaciente, String nombreVacuna) {
        Vacuna vacuna = catalogoVacunas.get(nombreVacuna);
        if (vacuna == null) {
            System.out.println("La vacuna '" + nombreVacuna + "' no está en el catálogo.");
            return;
        }

        Map<String, Integer> vacunasPaciente = registroVacunas.computeIfAbsent(cedulaPaciente, k -> new HashMap<>());
        int dosisActuales = vacunasPaciente.getOrDefault(nombreVacuna, 0);

        if (dosisActuales >= vacuna.getDosisRequeridas()) {
            System.out.println("El paciente ya completó las dosis de " + nombreVacuna);
        } else {
            vacunasPaciente.put(nombreVacuna, dosisActuales + 1);
            System.out.println("Vacuna " + nombreVacuna + " registrada. Dosis " + (dosisActuales + 1) + "/" + vacuna.getDosisRequeridas());

            vacunaciones.add(new Vacunacion(cedulaPaciente, LocalDate.now(), nombreVacuna, dosisActuales + 1, null));


            if (dosisActuales + 1 == vacuna.getDosisRequeridas()) {
                System.out.println("El paciente ha completado el esquema de vacunación para " + nombreVacuna + ".");
            }
        }
    }

    public void mostrarHistorial(String cedulaPaciente) {
        List<Vacunacion> historial = vacunaciones.stream()
                .filter(v -> v.getCedulaPaciente().equals(cedulaPaciente))
                .sorted((v1, v2) -> v1.getFecha().compareTo(v2.getFecha()))
                .toList();

        if (historial.isEmpty()) {
            System.out.println("No hay vacunaciones registradas para este paciente.");
            return;
        }

        System.out.println("\nHistorial de vacunas para paciente con cédula: " + cedulaPaciente);
        for (Vacunacion v : historial) {
            System.out.println("Fecha: " + v.getFecha() +
                    " | Vacuna: " + v.getTipoVacuna() +
                    " | Dosis: " + v.getDosis() +
                    " | Profesional: " + v.getProfesional().getNombre());
        }
    }


    public void mostrarCatalogoVacunas() {
        System.out.println("\n Catálogo de vacunas:");
        for (Vacuna vacuna : catalogoVacunas.values()) {
            System.out.println(" - " + vacuna);
        }
    }

    public List<Vacunacion> historialPorPaciente(String cedula) {
        return vacunaciones.stream()
                .filter(v -> v.getCedulaPaciente().equals(cedula))
                .collect(Collectors.toList());
    }

    public List<Vacunacion> getVacunaciones() {
        return vacunaciones;
    }

    public void registrarVacunacion(Vacunacion vacunacion) {
        String nombreVacuna = vacunacion.getTipoVacuna();
        String cedulaPaciente = vacunacion.getCedulaPaciente();

        Vacuna vacuna = catalogoVacunas.get(nombreVacuna);
        if (vacuna == null) {
            System.out.println("La vacuna '" + nombreVacuna + "' no está en el catálogo.");
            return;
        }

        Map<String, Integer> vacunasPaciente = registroVacunas.computeIfAbsent(cedulaPaciente, k -> new HashMap<>());
        int dosisActuales = vacunasPaciente.getOrDefault(nombreVacuna, 0);

        if (dosisActuales >= vacuna.getDosisRequeridas()) {
            System.out.println("El paciente ya completó las dosis de " + nombreVacuna);
            return;
        }

        vacunasPaciente.put(nombreVacuna, dosisActuales + 1);
        vacunaciones.add(vacunacion);

        System.out.println("Vacuna " + nombreVacuna + " registrada. Dosis " + (dosisActuales + 1) + "/" + vacuna.getDosisRequeridas());

        if (dosisActuales + 1 == vacuna.getDosisRequeridas()) {
            System.out.println("El paciente ha completado el esquema de vacunación para " + nombreVacuna + ".");
        }
    }

    public List<Vacuna> getCatalogoVacunas() {
        return new ArrayList<>(catalogoVacunas.values());
    }

    public int obtenerSiguienteDosis(String cedulaPaciente, String nombreVacuna) {
        List<Vacunacion> historial = historialPorPaciente(cedulaPaciente);
        int maxDosis = 0;
        for (Vacunacion v : historial) {
            if (v.getTipoVacuna().equalsIgnoreCase(nombreVacuna)) {
                if (v.getDosis() > maxDosis) {
                    maxDosis = v.getDosis();
                }
            }
        }
        return maxDosis + 1;
    }


}
