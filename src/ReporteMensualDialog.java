import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReporteMensualDialog extends JDialog {

    private ReporteService reporteService;
    private JTextField tfMesAnio;
    private JTextArea taReporte;

    public ReporteMensualDialog(Frame owner, ReporteService reporteService) {
        super(owner, "Reporte Mensual", true);
        this.reporteService = reporteService;

        setSize(400, 350);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(5, 5));

        JPanel panelTop = new JPanel();
        panelTop.add(new JLabel("Mes y Año (MM-yyyy):"));
        tfMesAnio = new JTextField(10);
        panelTop.add(tfMesAnio);

        JButton btnGenerar = new JButton("Generar");
        btnGenerar.addActionListener(e -> generarReporte());
        panelTop.add(btnGenerar);

        add(panelTop, BorderLayout.NORTH);

        taReporte = new JTextArea();
        taReporte.setEditable(false);
        add(new JScrollPane(taReporte), BorderLayout.CENTER);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        add(btnCerrar, BorderLayout.SOUTH);
    }

    private void generarReporte() {
        String mesAnio = tfMesAnio.getText().trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-yyyy");
        LocalDate fecha;
        try {
            fecha = LocalDate.parse("01-" + mesAnio, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Formato inválido. Use MM-yyyy.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String reporte = reporteService.generarReporteMensual(fecha.getYear(), fecha.getMonthValue());
        taReporte.setText(reporte);
    }
}
