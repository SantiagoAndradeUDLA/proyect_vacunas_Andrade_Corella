import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class RegistrarVacunacionDialog extends JDialog {
    private PacienteService pacienteService;
    private VacunacionService vacunacionService;

    private JTextField tfCedulaPaciente;
    private JComboBox<String> cbVacunas;
    private JTextField tfFecha;
    private JTextField tfNombreProf, tfCedulaProf, tfDireccionProf, tfEdadProf;
    private JComboBox<String> cbTipoProf;

    public RegistrarVacunacionDialog(JFrame parent, PacienteService pacienteService, VacunacionService vacunacionService) {
        super(parent, "Registrar Vacunación", true);
        this.pacienteService = pacienteService;
        this.vacunacionService = vacunacionService;

        setLayout(new GridLayout(11, 2, 5, 5));
        setSize(400, 450);
        setLocationRelativeTo(parent);

        add(new JLabel("Cédula del paciente:"));
        tfCedulaPaciente = new JTextField();
        add(tfCedulaPaciente);

        add(new JLabel("Vacuna:"));
        cbVacunas = new JComboBox<>();
        for (Vacuna v : vacunacionService.getCatalogoVacunas()) {
            cbVacunas.addItem(v.getNombre());
        }
        add(cbVacunas);

        add(new JLabel("Fecha (yyyy-MM-dd):"));
        tfFecha = new JTextField(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now()));
        add(tfFecha);

        add(new JLabel("Nombre del profesional:"));
        tfNombreProf = new JTextField();
        add(tfNombreProf);

        add(new JLabel("Cédula profesional:"));
        tfCedulaProf = new JTextField();
        add(tfCedulaProf);

        add(new JLabel("Dirección profesional:"));
        tfDireccionProf = new JTextField();
        add(tfDireccionProf);

        add(new JLabel("Edad profesional:"));
        tfEdadProf = new JTextField();
        add(tfEdadProf);

        add(new JLabel("Tipo profesional:"));
        cbTipoProf = new JComboBox<>(new String[]{"Médico", "Enfermero"});
        add(cbTipoProf);

        JButton btnRegistrar = new JButton("Registrar");
        JButton btnCancelar = new JButton("Cancelar");
        add(btnRegistrar);
        add(btnCancelar);

        btnRegistrar.addActionListener(e -> registrarVacunacion());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void registrarVacunacion() {
        String cedulaPaciente = tfCedulaPaciente.getText().trim();
        if (!cedulaPaciente.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "Cédula paciente inválida (10 dígitos).", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Paciente paciente = pacienteService.buscarPorCedula(cedulaPaciente);
        if (paciente == null) {
            JOptionPane.showMessageDialog(this, "Paciente no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nombreVacuna = (String) cbVacunas.getSelectedItem();
        String fechaStr = tfFecha.getText().trim();
        LocalDate fecha;
        try {
            fecha = LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nombreProf = tfNombreProf.getText().trim();
        String cedulaProf = tfCedulaProf.getText().trim();
        String direccionProf = tfDireccionProf.getText().trim();
        String edadProfStr = tfEdadProf.getText().trim();
        String tipoProf = (String) cbTipoProf.getSelectedItem();

        if (nombreProf.isEmpty() || cedulaProf.isEmpty() || direccionProf.isEmpty() || edadProfStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los datos del profesional.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int edadProf;
        try {
            edadProf = Integer.parseInt(edadProfStr);
            if (edadProf <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Edad del profesional inválida.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ProfesionalSalud profesional = new ProfesionalSalud(nombreProf, cedulaProf, direccionProf, edadProf, tipoProf);

        int dosisActual = vacunacionService.obtenerSiguienteDosis(cedulaPaciente, nombreVacuna);

        Vacunacion vacunacion = new Vacunacion(cedulaPaciente, fecha, nombreVacuna, dosisActual, profesional);

        vacunacionService.registrarVacunacion(vacunacion);

        JOptionPane.showMessageDialog(this, "Vacunación registrada correctamente.");
        dispose();
    }
}

