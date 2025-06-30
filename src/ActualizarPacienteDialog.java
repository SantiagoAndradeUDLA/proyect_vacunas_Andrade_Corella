import javax.swing.*;
import java.awt.*;

public class ActualizarPacienteDialog extends JDialog {
    private PacienteService pacienteService;

    private JTextField tfCedulaBusqueda;
    private JButton btnBuscar;
    private JTextField tfDireccion, tfEdad, tfCondicion;
    private JButton btnActualizar;

    private Paciente pacienteActual;

    public ActualizarPacienteDialog(JFrame parent, PacienteService pacienteService) {
        super(parent, "Actualizar Paciente", true);
        this.pacienteService = pacienteService;

        setLayout(new BorderLayout(10, 10));
        setSize(400, 300);
        setLocationRelativeTo(parent);

        JPanel panelBuscar = new JPanel();
        panelBuscar.add(new JLabel("Cédula del paciente:"));
        tfCedulaBusqueda = new JTextField(10);
        btnBuscar = new JButton("Buscar");
        panelBuscar.add(tfCedulaBusqueda);
        panelBuscar.add(btnBuscar);

        add(panelBuscar, BorderLayout.NORTH);

        JPanel panelDatos = new JPanel(new GridLayout(4, 2, 5, 5));
        panelDatos.setBorder(BorderFactory.createTitledBorder("Datos para actualizar"));

        panelDatos.add(new JLabel("Nueva dirección:"));
        tfDireccion = new JTextField();
        panelDatos.add(tfDireccion);

        panelDatos.add(new JLabel("Nueva edad:"));
        tfEdad = new JTextField();
        panelDatos.add(tfEdad);

        panelDatos.add(new JLabel("Nueva condición médica:"));
        tfCondicion = new JTextField();
        panelDatos.add(tfCondicion);

        btnActualizar = new JButton("Actualizar");
        btnActualizar.setEnabled(false);
        panelDatos.add(new JLabel()); // vacía
        panelDatos.add(btnActualizar);

        add(panelDatos, BorderLayout.CENTER);

        btnBuscar.addActionListener(e -> buscarPaciente());
        btnActualizar.addActionListener(e -> actualizarPaciente());
    }

    private void buscarPaciente() {
        String cedula = tfCedulaBusqueda.getText().trim();
        if (!cedula.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "Ingrese una cédula válida de 10 dígitos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        pacienteActual = pacienteService.buscarPorCedula(cedula);
        if (pacienteActual == null) {
            JOptionPane.showMessageDialog(this, "Paciente no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            btnActualizar.setEnabled(false);
            tfDireccion.setText("");
            tfEdad.setText("");
            tfCondicion.setText("");
            return;
        }

        tfDireccion.setText(pacienteActual.getDireccion());
        tfEdad.setText(String.valueOf(pacienteActual.getEdad()));
        tfCondicion.setText(pacienteActual.getCondicionMedica());
        btnActualizar.setEnabled(true);
    }

    private void actualizarPaciente() {
        String direccion = tfDireccion.getText().trim();
        String edadStr = tfEdad.getText().trim();
        String condicion = tfCondicion.getText().trim();

        if (direccion.isEmpty() || edadStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int edad;
        try {
            edad = Integer.parseInt(edadStr);
            if (edad <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Edad inválida.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean actualizado = pacienteService.actualizarPaciente(pacienteActual.getCedula(), direccion, edad, condicion);
        if (actualizado) {
            JOptionPane.showMessageDialog(this, "Paciente actualizado correctamente.");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar paciente.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
