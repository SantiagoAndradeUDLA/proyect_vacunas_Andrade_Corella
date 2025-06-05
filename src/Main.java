import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final PacienteService pacienteService = new PacienteService();
    private static final VacunacionService vacunacionService = new VacunacionService();
    private static final ReporteService reporteService = new ReporteService(pacienteService, vacunacionService);
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void main(String[] args) {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- Sistema de Gestión de Vacunación ---");
            System.out.println("1. Registrar Paciente");
            System.out.println("2. Buscar Paciente");
            System.out.println("3. Actualizar Paciente");
            System.out.println("4. Registrar Vacunación");
            System.out.println("5. Ver historial de vacunación");
            System.out.println("6. Alertas de dosis faltantes");
            System.out.println("7. Reporte mensual");
            System.out.println("0. Salir");
            System.out.print("Seleccione opción: ");
            int opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1 -> registrarPaciente();
                case 2 -> buscarPaciente();
                case 3 -> actualizarPaciente();
                case 4 -> registrarVacunacion();
                case 5 -> verHistorial();
                case 6 -> reporteService.alertasDosisFaltantes();
                case 7 -> reporteMensual();
                case 0 -> salir = true;
                default -> System.out.println("Opción inválida.");
            }
        }
        System.out.println("Fin del sistema.");
    }

    private static void registrarPaciente() {
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Cédula: ");
        String cedula = sc.nextLine();
        System.out.print("Dirección: ");
        String direccion = sc.nextLine();
        System.out.print("Edad: ");
        int edad = Integer.parseInt(sc.nextLine());
        System.out.print("Condición médica (dejar vacío si no aplica): ");
        String condicion = sc.nextLine();

        Paciente p = new Paciente(nombre, cedula, direccion, edad, condicion);
        pacienteService.registrarPaciente(p);
        System.out.println("Paciente registrado con éxito.");
    }

    private static void buscarPaciente() {
        System.out.print("Buscar por (1) Nombre o (2) Cédula? ");
        int tipo = Integer.parseInt(sc.nextLine());
        if (tipo == 1) {
            System.out.print("Nombre: ");
            String nombre = sc.nextLine();
            List<Paciente> resultados = pacienteService.buscarPorNombre(nombre);
            if (resultados.isEmpty()) System.out.println("No se encontraron pacientes.");
            else resultados.forEach(System.out::println);
        } else if (tipo == 2) {
            System.out.print("Cédula: ");
            String cedula = sc.nextLine();
            Paciente p = pacienteService.buscarPorCedula(cedula);
            if (p == null) System.out.println("Paciente no encontrado.");
            else System.out.println(p);
        }
    }

    private static void actualizarPaciente() {
        System.out.print("Cédula del paciente a actualizar: ");
        String cedula = sc.nextLine();
        Paciente p = pacienteService.buscarPorCedula(cedula);
        if (p == null) {
            System.out.println("Paciente no encontrado.");
            return;
        }
        System.out.println("Datos actuales: " + p);
        System.out.print("Nueva dirección: ");
        String direccion = sc.nextLine();
        System.out.print("Nueva edad: ");
        int edad = Integer.parseInt(sc.nextLine());
        System.out.print("Nueva condición médica (vacío si no aplica): ");
        String condicion = sc.nextLine();

        boolean actualizado = pacienteService.actualizarPaciente(cedula, direccion, edad, condicion);
        if (actualizado) System.out.println("Paciente actualizado correctamente.");
        else System.out.println("Error al actualizar.");
    }

    private static void registrarVacunacion() {
        System.out.print("Cédula del paciente: ");
        String cedula = sc.nextLine();
        Paciente p = pacienteService.buscarPorCedula(cedula);
        if (p == null) {
            System.out.println("Paciente no encontrado.");
            return;
        }
        System.out.print("Fecha (yyyy-MM-dd): ");
        LocalDate fecha = LocalDate.parse(sc.nextLine(), dtf);
        System.out.print("Tipo de vacuna: ");
        String tipoVacuna = sc.nextLine();
        System.out.print("Número de dosis: ");
        int dosis = Integer.parseInt(sc.nextLine());

        System.out.println("Registrar datos del profesional de salud:");
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Cédula: ");
        String cedulaProf = sc.nextLine();
        System.out.print("Dirección: ");
        String direccion = sc.nextLine();
        System.out.print("Edad: ");
        int edad = Integer.parseInt(sc.nextLine());
        System.out.print("Condición médica (vacío si no aplica): ");
        String condicion = sc.nextLine();

        ProfesionalSalud prof = new ProfesionalSalud(nombre, cedulaProf, direccion, edad, condicion);

        Vacunacion v = new Vacunacion(cedula, fecha, tipoVacuna, dosis, prof);
        vacunacionService.registrarVacunacion(v);
        System.out.println("Vacunación registrada con éxito.");
    }

    private static void verHistorial() {
        System.out.print("Cédula del paciente: ");
        String cedula = sc.nextLine();
        List<Vacunacion> historial = vacunacionService.historialPorPaciente(cedula);
        if (historial.isEmpty()) System.out.println("No hay vacunaciones registradas para este paciente.");
        else historial.forEach(System.out::println);
    }

    private static void reporteMensual() {
        System.out.print("Año (ej: 2025): ");
        int año = Integer.parseInt(sc.nextLine());
        System.out.print("Mes (1-12): ");
        int mes = Integer.parseInt(sc.nextLine());
        reporteService.reporteMensual(año, java.time.Month.of(mes));
    }
}
