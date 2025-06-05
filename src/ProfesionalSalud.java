public class ProfesionalSalud extends Persona {
    public ProfesionalSalud(String nombre, String cedula, String direccion, int edad, String condicionMedica) {
        super(nombre, cedula, direccion, edad, condicionMedica);
    }

    @Override
    public String toString() {
        return "ProfesionalSalud{" +
                "nombre='" + nombre + '\'' +
                ", cedula='" + cedula + '\'' +
                ", direccion='" + direccion + '\'' +
                ", edad=" + edad +
                ", condicionMedica='" + condicionMedica + '\'' +
                '}';
    }
}
