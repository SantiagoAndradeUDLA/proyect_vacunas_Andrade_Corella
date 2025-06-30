import java.time.LocalDate;

public class Vacunacion {
    private String cedulaPaciente;
    private LocalDate fecha;
    private String tipoVacuna;
    private int dosis;
    private ProfesionalSalud profesionalSalud;

    // Constructor con parámetros
    public Vacunacion(String cedulaPaciente, LocalDate fecha, String tipoVacuna, int dosis, ProfesionalSalud profesionalSalud) {
        this.cedulaPaciente = cedulaPaciente;
        this.fecha = fecha;
        this.tipoVacuna = tipoVacuna;
        this.dosis = dosis;
        this.profesionalSalud = profesionalSalud;
    }

    // Constructor vacío (opcional, si lo necesitas)
    public Vacunacion() {}

    @Override
    public String toString() {
        return "Vacuna: " + tipoVacuna +
                ", Dosis: " + dosis +
                ", Fecha: " + fecha;
    }

    public String toStringConProfesional() {
        return toString() + "\nProfesional: " + profesionalSalud.toString();
    }

    public String getCedulaPaciente() {
        return cedulaPaciente;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getTipoVacuna() {
        return tipoVacuna;
    }

    public int getDosis() {
        return dosis;
    }

    public ProfesionalSalud getProfesionalSalud() {
        return profesionalSalud;
    }

    public ProfesionalSalud getProfesional() {
        return profesionalSalud;
    }




}
