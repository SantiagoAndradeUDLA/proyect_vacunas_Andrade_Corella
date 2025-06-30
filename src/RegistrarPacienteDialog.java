import javax.swing.*;
import java.awt.*;

public class RegistrarPacienteDialog extends JDialog {
    private JTextField tfNombre, tfCedula, tfDireccion, tfEdad, tfCondicion;
    private PacienteService pacienteService;

    public RegistrarPacienteDialog(JFrame parent, PacienteService pacienteService) {
        super(parent, "Registrar Paciente", true);
        this.pacienteService = pacienteService;

        setLayout(new GridLayout(6, 2, 5, 5));
        setSize(350, 250);
        setLocationRelativeTo(parent);

        add(new JLabel("Nombre:"));
        tfNombre = new JTextField();
        add(tfNombre);

        add(new JLabel("Cédula (10 dígitos):"));
        tfCedula = new JTextField();
        add(tfCedula);

        add(new JLabel("Dirección:"));
        tfDireccion = new JTextField();
        add(tfDireccion);

        add(new JLabel("Edad:"));
        tfEdad = new JTextField();
        add(tfEdad);

        add(new JLabel("Condición médica:"));
        tfCondicion = new JTextField();
        add(tfCondicion);

        JButton btnRegistrar = new JButton("Registrar");
        JButton btnCancelar = new JButton("Cancelar");
        add(btnRegistrar);
        add(btnCancelar);

        btnRegistrar.addActionListener(e -> registrarPaciente());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void registrarPaciente() {
        String nombre = tfNombre.getText().trim();
        String cedula = tfCedula.getText().trim();
        String direccion = tfDireccion.getText().trim();
        String edadStr = tfEdad.getText().trim();
        String condicion = tfCondicion.getText().trim();

        if (nombre.isEmpty() || cedula.isEmpty() || direccion.isEmpty() || edadStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!cedula.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "Cédula inválida. Debe tener 10 dígitos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int edad;
        try {
            edad = Integer.parseInt(edadStr);
            if (edad <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Edad inválida.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        pacienteService.registrarPaciente(new Paciente(nombre, cedula, direccion, edad, condicion));
        JOptionPane.showMessageDialog(this, "Paciente registrado correctamente.");
        dispose();
    }
}
