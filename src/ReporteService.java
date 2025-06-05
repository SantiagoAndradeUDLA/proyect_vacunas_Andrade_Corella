import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReporteService {
    private PacienteService pacienteService;
    private VacunacionService vacunacionService;

    public ReporteService(PacienteService pacienteService, VacunacionService vacunacionService) {
        this.pacienteService = pacienteService;
        this.vacunacionService = vacunacionService;
    }

    // Simula alertas por dosis faltantes (ejemplo simple)
    public void alertasDosisFaltantes() {
        for (Paciente p : pacienteService.listarPacientes()) {
            List<Vacunacion> historial = vacunacionService.historialPorPaciente(p.getCedula());
            if (historial.isEmpty()) {
                System.out.println("Alerta: Paciente " + p.getNombre() + " sin vacunaciones registradas.");
            } else {
                int maxDosis = historial.stream().mapToInt(Vacunacion::getDosis).max().orElse(0);
                if (maxDosis < 2) { // Ejemplo: 2 dosis mínimo
                    System.out.println("Alerta: Paciente " + p.getNombre() + " con dosis faltantes (última dosis: " + maxDosis + ")");
                }
            }
        }
    }

    // Reporte mensual simulado en consola
    public void reporteMensual(int año, Month mes) {
        List<Vacunacion> vacunacionesDelMes = vacunacionService.vacunaciones.stream()
                .filter(v -> v.getFecha().getYear() == año && v.getFecha().getMonth() == mes)
                .toList();

        Map<String, Long> conteoPorVacuna = vacunacionesDelMes.stream()
                .collect(Collectors.groupingBy(Vacunacion::getTipoVacuna, Collectors.counting()));

        System.out.println("Reporte de vacunaciones para " + mes + " " + año);
        for (Map.Entry<String, Long> entrada : conteoPorVacuna.entrySet()) {
            System.out.println("Vacuna: " + entrada.getKey() + " - Cantidad: " + entrada.getValue());
        }
    }
}
