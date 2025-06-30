public class Paciente extends Persona {
    public Paciente(String nombre, String cedula, String direccion, int edad, String condicionMedica) {
        super(nombre, cedula, direccion, edad, condicionMedica);
    }

    @Override
    public String toString() {
        return "Paciente:\n" +
                "Nombre: " + nombre + "\n" +
                "Cédula: " + cedula + "\n" +
                "Dirección: " + direccion + "\n" +
                "Edad: " + edad + "\n" +
                "Condición Médica: " + (condicionMedica.isEmpty() ? "Ninguna" : condicionMedica);
    }
}

