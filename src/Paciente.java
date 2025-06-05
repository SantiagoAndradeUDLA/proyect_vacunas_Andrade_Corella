public class Paciente extends Persona {
    public Paciente(String nombre, String cedula, String direccion, int edad, String condicionMedica) {
        super(nombre, cedula, direccion, edad, condicionMedica);
    }

    @Override
    public String toString() {
        return "Paciente{" +
                "nombre='" + nombre + '\'' +
                ", cedula='" + cedula + '\'' +
                ", direccion='" + direccion + '\'' +
                ", edad=" + edad +
                ", condicionMedica='" + condicionMedica + '\'' +
                '}';
    }
}
