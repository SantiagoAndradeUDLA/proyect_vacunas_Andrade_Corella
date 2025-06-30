import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AppGUI extends JFrame {

    private PacienteService pacienteService = new PacienteService();
    private VacunacionService vacunacionService = new VacunacionService();
    private ReporteService reporteService = new ReporteService(pacienteService, vacunacionService);

    public AppGUI() {
        setTitle("Sistema de Gestión de Vacunación");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelMenu = new JPanel(new GridLayout(10, 1, 5, 5));

        JButton btnRegistrarPaciente = new JButton("1. Registrar Paciente");
        JButton btnBuscarPaciente = new JButton("2. Buscar Paciente");
        JButton btnActualizarPaciente = new JButton("3. Actualizar Paciente");
        JButton btnRegistrarVacunacion = new JButton("4. Registrar Vacunación");
        JButton btnVerHistorial = new JButton("5. Ver historial de vacunación");
        JButton btnAlertas = new JButton("6. Alertas de dosis faltantes");
        JButton btnReporteMensual = new JButton("7. Reporte mensual");
        JButton btnAgregarVacuna = new JButton("8. Agregar nueva vacuna");
        JButton btnMostrarCatalogo = new JButton("9. Mostrar catálogo de vacunas");
        JButton btnSalir = new JButton("0. Salir");

        panelMenu.add(btnRegistrarPaciente);
        panelMenu.add(btnBuscarPaciente);
        panelMenu.add(btnActualizarPaciente);
        panelMenu.add(btnRegistrarVacunacion);
        panelMenu.add(btnVerHistorial);
        panelMenu.add(btnAlertas);
        panelMenu.add(btnReporteMensual);
        panelMenu.add(btnAgregarVacuna);
        panelMenu.add(btnMostrarCatalogo);
        panelMenu.add(btnSalir);

        add(panelMenu);

        // Listeners de botones:
        btnRegistrarPaciente.addActionListener(e -> new RegistrarPacienteDialog(this, pacienteService).setVisible(true));
        btnBuscarPaciente.addActionListener(e -> new BuscarPacienteDialog(this, pacienteService).setVisible(true));
        btnActualizarPaciente.addActionListener(e -> new ActualizarPacienteDialog(this, pacienteService).setVisible(true));
        btnRegistrarVacunacion.addActionListener(e -> new RegistrarVacunacionDialog(this, pacienteService, vacunacionService).setVisible(true));
        btnVerHistorial.addActionListener(e -> new VerHistorialDialog(this, vacunacionService).setVisible(true));
        btnAlertas.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, getAlertasText(), "Alertas de dosis faltantes", JOptionPane.INFORMATION_MESSAGE);
        });
        btnReporteMensual.addActionListener(e -> new ReporteMensualDialog(this, reporteService).setVisible(true));
        btnAgregarVacuna.addActionListener(e -> new AgregarVacunaDialog(this, vacunacionService).setVisible(true));
        btnMostrarCatalogo.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, getCatalogoText(), "Catálogo de Vacunas", JOptionPane.INFORMATION_MESSAGE);
        });
        btnSalir.addActionListener(e -> System.exit(0));
    }

    private String getAlertasText() {
        StringBuilder sb = new StringBuilder();
        for (Paciente p : pacienteService.listarPacientes()) {
            var historial = vacunacionService.historialPorPaciente(p.getCedula());
            if (historial.isEmpty()) {
                sb.append("Alerta: Paciente ").append(p.getNombre()).append(" sin vacunaciones registradas.\n");
            } else {
                int maxDosis = historial.stream().mapToInt(v -> v.getDosis()).max().orElse(0);
                if (maxDosis < 2) {
                    sb.append("Alerta: Paciente ").append(p.getNombre())
                            .append(" con dosis faltantes (última dosis: ").append(maxDosis).append(")\n");
                }
            }
        }
        if (sb.isEmpty()) sb.append("No hay alertas de dosis faltantes.");
        return sb.toString();
    }

    private String getCatalogoText() {
        StringBuilder sb = new StringBuilder();
        for (Vacuna v : vacunacionService.getCatalogoVacunas()) {
            sb.append("- ").append(v.toString()).append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AppGUI().setVisible(true);
        });
    }
}
