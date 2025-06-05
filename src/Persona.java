public class Persona {
    protected String nombre;
    protected String cedula;
    protected String direccion;
    protected int edad;
    protected String condicionMedica;

    public Persona(String nombre, String cedula, String direccion, int edad, String condicionMedica) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.direccion = direccion;
        this.edad = edad;
        this.condicionMedica = condicionMedica;
    }

    // Getters y setters
    public String getNombre() { return nombre; }
    public String getCedula() { return cedula; }
    public String getDireccion() { return direccion; }
    public int getEdad() { return edad; }
    public String getCondicionMedica() { return condicionMedica; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public void setEdad(int edad) { this.edad = edad; }
    public void setCondicionMedica(String condicionMedica) { this.condicionMedica = condicionMedica; }
}
