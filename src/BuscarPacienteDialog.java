import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BuscarPacienteDialog extends JDialog {
    private PacienteService pacienteService;

    private JTextField tfBusqueda;
    private JTextArea taResultados;
    private JComboBox<String> cbTipoBusqueda;

    public BuscarPacienteDialog(JFrame parent, PacienteService pacienteService) {
        super(parent, "Buscar Paciente", true);
        this.pacienteService = pacienteService;

        setLayout(new BorderLayout(10, 10));
        setSize(400, 350);
        setLocationRelativeTo(parent);

        JPanel panelTop = new JPanel();
        cbTipoBusqueda = new JComboBox<>(new String[]{"Nombre", "Cédula"});
        tfBusqueda = new JTextField(20);
        JButton btnBuscar = new JButton("Buscar");
        panelTop.add(new JLabel("Buscar por:"));
        panelTop.add(cbTipoBusqueda);
        panelTop.add(tfBusqueda);
        panelTop.add(btnBuscar);

        taResultados = new JTextArea();
        taResultados.setEditable(false);
        JScrollPane scroll = new JScrollPane(taResultados);

        add(panelTop, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        btnBuscar.addActionListener(e -> realizarBusqueda());
    }

    private void realizarBusqueda() {
        String texto = tfBusqueda.getText().trim();
        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un texto para buscar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        taResultados.setText("");
        int tipo = cbTipoBusqueda.getSelectedIndex();
        if (tipo == 0) { // Nombre
            List<Paciente> resultados = pacienteService.buscarPorNombre(texto);
            if (resultados.isEmpty()) {
                taResultados.setText("No se encontraron pacientes.");
            } else {
                for (Paciente p : resultados) {
                    taResultados.append(p.toString() + "\n\n");
                }
            }
        } else { // Cédula
            Paciente p = pacienteService.buscarPorCedula(texto);
            if (p == null) {
                taResultados.setText("Paciente no encontrado.");
            } else {
                taResultados.setText(p.toString());
            }
        }
    }
}
