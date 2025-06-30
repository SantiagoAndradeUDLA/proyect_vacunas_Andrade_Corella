import javax.swing.*;
import java.awt.*;

public class AgregarVacunaDialog extends JDialog {

    private VacunacionService vacunacionService;
    private JTextField tfNombre, tfDosisRequeridas;

    public AgregarVacunaDialog(Frame owner, VacunacionService vacunacionService) {
        super(owner, "Agregar Nueva Vacuna", true);
        this.vacunacionService = vacunacionService;

        setSize(350, 250);
        setLocationRelativeTo(owner);
        setLayout(new GridLayout(4, 2, 5, 5));

        add(new JLabel("Nombre Vacuna:"));
        tfNombre = new JTextField();
        add(tfNombre);


        add(new JLabel("Dosis requeridas (número):"));
        tfDosisRequeridas = new JTextField();
        add(tfDosisRequeridas);

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.addActionListener(e -> agregarVacuna());
        add(btnAgregar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        add(btnCancelar);
    }

    private void agregarVacuna() {
        String nombre = tfNombre.getText().trim();
        String dosisStr = tfDosisRequeridas.getText().trim();

        if (nombre.isEmpty() || dosisStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int dosis;
        try {
            dosis = Integer.parseInt(dosisStr);
            if (dosis <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Número de dosis inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        vacunacionService.agregarVacunaAlCatalogo(nombre, dosis);

        JOptionPane.showMessageDialog(this, "Vacuna agregada correctamente.");
        dispose();
    }
}
