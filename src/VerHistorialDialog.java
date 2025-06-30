import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VerHistorialDialog extends JDialog {

    private VacunacionService vacunacionService;
    private JTextField tfCedula;
    private JTextArea taHistorial;

    public VerHistorialDialog(Frame owner, VacunacionService vacunacionService) {
        super(owner, "Ver Historial de Vacunación", true);
        this.vacunacionService = vacunacionService;

        setSize(400, 350);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(5, 5));

        JPanel panelTop = new JPanel();
        panelTop.add(new JLabel("Cédula paciente:"));
        tfCedula = new JTextField(15);
        panelTop.add(tfCedula);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> mostrarHistorial());
        panelTop.add(btnBuscar);

        add(panelTop, BorderLayout.NORTH);

        taHistorial = new JTextArea();
        taHistorial.setEditable(false);
        add(new JScrollPane(taHistorial), BorderLayout.CENTER);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        add(btnCerrar, BorderLayout.SOUTH);
    }

    private void mostrarHistorial() {
        String cedula = tfCedula.getText().trim();
        if (!cedula.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "Cédula inválida.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        List<Vacunacion> historial = vacunacionService.historialPorPaciente(cedula);
        if (historial.isEmpty()) {
            taHistorial.setText("No se encontraron vacunaciones para este paciente.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (Vacunacion v : historial) {
                sb.append(v.toString()).append("\n\n");
            }
            taHistorial.setText(sb.toString());
        }
    }
}
