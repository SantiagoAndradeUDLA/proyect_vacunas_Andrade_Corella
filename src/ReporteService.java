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

    public void alertasDosisFaltantes() {
        for (Paciente p : pacienteService.listarPacientes()) {
            List<Vacunacion> historial = vacunacionService.historialPorPaciente(p.getCedula());
            if (historial.isEmpty()) {
                System.out.println("Alerta: Paciente " + p.getNombre() + " sin vacunaciones registradas.");
            } else {
                int maxDosis = historial.stream().mapToInt(Vacunacion::getDosis).max().orElse(0);
                if (maxDosis < 2) {
                    System.out.println("Alerta: Paciente " + p.getNombre() + " con dosis faltantes (última dosis: " + maxDosis + ")");
                }
            }
        }
    }

    public void reporteMensual(int anio, Month mes) {
        List<Vacunacion> vacunacionesDelMes = vacunacionService.getVacunaciones().stream()
                .filter(v -> v.getFecha().getYear() == anio && v.getFecha().getMonth() == mes)
                .toList();

        Map<String, Long> conteoPorVacuna = vacunacionesDelMes.stream()
                .collect(Collectors.groupingBy(Vacunacion::getTipoVacuna, Collectors.counting()));

        System.out.println("Reporte de vacunaciones para " + mes + " " + anio);
        for (Map.Entry<String, Long> entrada : conteoPorVacuna.entrySet()) {
            System.out.println("Vacuna: " + entrada.getKey() + " - Cantidad: " + entrada.getValue());
        }
    }

    // NUEVO método para interfaz gráfica:
    public String generarReporteMensual(int anio, int mes) {
        List<Vacunacion> vacunacionesDelMes = vacunacionService.getVacunaciones().stream()
                .filter(v -> v.getFecha().getYear() == anio && v.getFecha().getMonthValue() == mes)
                .toList();

        if (vacunacionesDelMes.isEmpty()) {
            return "No hay vacunaciones registradas para " + mes + "/" + anio;
        }

        Map<String, Long> conteoPorVacuna = vacunacionesDelMes.stream()
                .collect(Collectors.groupingBy(Vacunacion::getTipoVacuna, Collectors.counting()));

        StringBuilder sb = new StringBuilder();
        sb.append("Reporte de vacunaciones para ").append(mes).append("/").append(anio).append(":\n\n");
        for (Map.Entry<String, Long> entry : conteoPorVacuna.entrySet()) {
            sb.append("Vacuna: ").append(entry.getKey())
                    .append(" - Cantidad: ").append(entry.getValue())
                    .append("\n");
        }
        return sb.toString();
    }
}
