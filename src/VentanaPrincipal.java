import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    private PacienteService pacienteService = new PacienteService();
    private VacunacionService vacunacionService = new VacunacionService();
    private ReporteService reporteService = new ReporteService(pacienteService, vacunacionService);

    public VentanaPrincipal() {
        setTitle("Sistema de Gestión de Vacunación");
        setSize(500, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelBotones = new JPanel(new GridLayout(10, 1, 10, 10));

        JButton btnRegistrarPaciente = new JButton("1. Registrar Paciente");
        JButton btnBuscarPaciente = new JButton("2. Buscar Paciente");
        JButton btnActualizarPaciente = new JButton("3. Actualizar Paciente");
        JButton btnRegistrarVacunacion = new JButton("4. Registrar Vacunación");
        JButton btnVerHistorial = new JButton("5. Ver Historial de Vacunación");
        JButton btnAlertas = new JButton("6. Alertas de Dosis Faltantes");
        JButton btnReporteMensual = new JButton("7. Reporte Mensual");
        JButton btnAgregarVacuna = new JButton("8. Agregar Vacuna al Catálogo");
        JButton btnMostrarCatalogo = new JButton("9. Mostrar Catálogo de Vacunas");
        JButton btnSalir = new JButton("0. Salir");

        panelBotones.add(btnRegistrarPaciente);
        panelBotones.add(btnBuscarPaciente);
        panelBotones.add(btnActualizarPaciente);
        panelBotones.add(btnRegistrarVacunacion);
        panelBotones.add(btnVerHistorial);
        panelBotones.add(btnAlertas);
        panelBotones.add(btnReporteMensual);
        panelBotones.add(btnAgregarVacuna);
        panelBotones.add(btnMostrarCatalogo);
        panelBotones.add(btnSalir);

        add(panelBotones, BorderLayout.CENTER);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        btnRegistrarPaciente.addActionListener(e -> new RegistrarPacienteDialog(this, pacienteService).setVisible(true));
        btnBuscarPaciente.addActionListener(e -> new BuscarPacienteDialog(this, pacienteService).setVisible(true));
        btnActualizarPaciente.addActionListener(e -> new ActualizarPacienteDialog(this, pacienteService).setVisible(true));
        btnRegistrarVacunacion.addActionListener(e -> new RegistrarVacunacionDialog(this, pacienteService, vacunacionService).setVisible(true));
        btnVerHistorial.addActionListener(e -> new VerHistorialDialog(this, vacunacionService).setVisible(true));
        btnAlertas.addActionListener(e -> mostrarAlertas());
        btnReporteMensual.addActionListener(e -> new ReporteMensualDialog(this, reporteService).setVisible(true));
        btnAgregarVacuna.addActionListener(e -> new AgregarVacunaDialog(this, vacunacionService).setVisible(true));
        btnMostrarCatalogo.addActionListener(e -> mostrarCatalogo());
        btnSalir.addActionListener(e -> System.exit(0));
    }

    private void mostrarAlertas() {
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
        if (sb.length() == 0) sb.append("No hay alertas.");
        JOptionPane.showMessageDialog(this, sb.toString(), "Alertas Dosis Faltantes", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarCatalogo() {
        var catalogo = vacunacionService.getCatalogoVacunas();
        StringBuilder sb = new StringBuilder("Catálogo de Vacunas:\n");
        for (Vacuna v : catalogo) {
            sb.append("- ").append(v.toString()).append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString(), "Catálogo de Vacunas", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaPrincipal().setVisible(true);
        });
    }
}
