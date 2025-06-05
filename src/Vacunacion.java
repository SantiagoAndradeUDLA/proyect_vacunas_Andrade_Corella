import java.time.LocalDate;

public class Vacunacion {
    private String cedulaPaciente;
    private LocalDate fecha;
    private String tipoVacuna;
    private int dosis; // Ej: 1, 2, refuerzo
    private ProfesionalSalud profesional;

    public Vacunacion(String cedulaPaciente, LocalDate fecha, String tipoVacuna, int dosis, ProfesionalSalud profesional) {
        this.cedulaPaciente = cedulaPaciente;
        this.fecha = fecha;
        this.tipoVacuna = tipoVacuna;
        this.dosis = dosis;
        this.profesional = profesional;
    }

    // Getters
    public String getCedulaPaciente() { return cedulaPaciente; }
    public LocalDate getFecha() { return fecha; }
    public String getTipoVacuna() { return tipoVacuna; }
    public int getDosis() { return dosis; }
    public ProfesionalSalud getProfesional() { return profesional; }

    @Override
    public String toString() {
        return "Vacunacion{" +
                "fecha=" + fecha +
                ", tipoVacuna='" + tipoVacuna + '\'' +
                ", dosis=" + dosis +
                ", profesional=" + profesional.getNombre() +
                '}';
    }
}
