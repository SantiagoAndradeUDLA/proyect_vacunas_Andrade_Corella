# Sistema de Gestión de Vacunación para Zonas Rurales

## Descripción del Proyecto

Este proyecto tiene como objetivo principal desarrollar un sistema digital para optimizar el control del esquema de vacunación en zonas rurales de difícil acceso. Actualmente, la falta de un sistema digital eficiente representa un riesgo para la salud pública, especialmente en comunidades donde el acceso a servicios médicos es limitado, afectando a poblaciones vulnerables como niños, adultos mayores y personas con enfermedades crónicas.

El sistema abordará los siguientes problemas clave:
* No existe un sistema digital para registrar quiénes han sido vacunados ni qué vacunas han recibido.
* Se desconoce cuántas personas requieren refuerzo o dosis específicas.
* Las campañas de vacunación se realizan sin planificación eficiente.
* La generación de reportes para el Ministerio de Salud se hace de manera manual o incompleta.

## Funcionalidades del Sistema

El sistema proveerá las siguientes funcionalidades clave:

* *Registrar Pacientes*: Permite ingresar y modificar datos básicos de los pacientes.
* *Registrar Vacunación*: Facilita el registro de la aplicación de vacunas.
* *Consultar Historial*: Permite consultar el historial completo de vacunación por paciente.
* *Alertas Automáticas*: Genera alertas para dosis faltantes o refuerzos necesarios.
* *Generación de Reportes*: Permite exportar reportes mensuales para las autoridades sanitarias.

## Datos y Procesos Involucrados

Para la gestión del sistema, se manejarán los siguientes datos y procesos:

### Datos
* *Datos del Paciente*: Nombre, número de cédula, dirección, edad, condición médica especial (si aplica).
* *Datos de Vacunación*: Fecha, tipo de vacuna (ej. COVID-19, influenza, tétanos), dosis (primera, refuerzo), responsable de la aplicación.

### Procesos
* Registro de nuevos pacientes.
* Registro de vacunas aplicadas.
* Consulta de historial de vacunación por paciente.
* Generación de alertas de refuerzo.
* Reportes para autoridades sanitarias.

## Entidades Involucradas

Las principales entidades y actores relacionados con el problema y el sistema son:

| Entidad | Descripción |
| :------------------ | :-------------------------------------------------------------------------- |
| *Cliente* | Ministerio de Salud Pública o Gobierno Local. |
| *Empresa* | Puesto de salud rural o brigada de vacunación móvil. |
| *Usuario* | Personal médico y de enfermería de la comunidad. |
| *Quien lo va a usar* | Enfermeros/as, doctores, brigadistas. |
| *Paciente* | Persona registrada en el sistema con sus datos personales y médicos. |
| *Vacuna* | Tipo de vacuna aplicada, fecha, dosis, lote y responsable. |
| *Aplicación* | Registro del evento donde se aplicó una vacuna a un paciente. |
| *Profesional de Salud* | Personal autorizado que registra y aplica las vacunas. |
| *Administrador* | Encargado de generar reportes y mantener actualizada la base de datos. |

## Arquitectura del Sistema (Diagrama de Clases)

A continuación, se presenta el diagrama de clases del sistema en formato PlantUML, mostrando las principales entidades de datos, servicios y componentes de la interfaz de usuario.

plantuml
@startuml
' Definición de Clases y Relaciones del Sistema de Gestión de Vacunación

skinparam classAttributeIconSize 0
hide empty members

class Persona {
# nombre: String
# cedula: String
# direccion: String
# edad: int
# condicionMedica: String
+ Persona(nombre, cedula, direccion, edad, condicionMedica)
+ getNombre(): String
+ getCedula(): String
+ getDireccion(): String
+ getEdad(): int
+ getCondicionMedica(): String
+ setNombre(nombre: String)
+ setDireccion(direccion: String)
+ setEdad(edad: int)
+ setCondicionMedica(condicionMedica: String)
  }

class Paciente extends Persona {
+ Paciente(nombre, cedula, direccion, edad, condicionMedica)
+ toString(): String
  }

class ProfesionalSalud extends Persona {
+ ProfesionalSalud(nombre, cedula, direccion, edad, condicionMedica)
+ toString(): String
  }

class Vacuna {
- nombre: String
- dosisRequeridas: int
+ Vacuna(nombre, dosisRequeridas)
+ getNombre(): String
+ getDosisRequeridas(): int
+ toString(): String
  }

class Vacunacion {
- cedulaPaciente: String
- fecha: LocalDate
- tipoVacuna: String
- dosis: int
- profesionalSalud: ProfesionalSalud
+ Vacunacion(cedulaPaciente, fecha, tipoVacuna, dosis, profesionalSalud)
+ toString(): String
+ toStringConProfesional(): String
+ getCedulaPaciente(): String
+ getFecha(): LocalDate
+ getTipoVacuna(): String
+ getDosis(): int
+ getProfesionalSalud(): ProfesionalSalud
+ getProfesional(): ProfesionalSalud
  }

class PacienteService {
- pacientes: List<Paciente>
+ registrarPaciente(p: Paciente)
+ listarPacientes(): List<Paciente>
+ buscarPorCedula(cedula: String): Paciente
+ buscarPorNombre(nombre: String): List<Paciente>
+ actualizarPaciente(cedula, direccion, edad, condicionMedica): boolean
  }

class VacunacionService {
- catalogoVacunas: Map<String, Vacuna>
- registroVacunas: Map<String, Map<String, Integer>>
- vacunaciones: List<Vacunacion>
+ VacunacionService()
+ agregarVacunaAlCatalogo(nombre: String, dosisRequeridas: int)
+ administrarVacuna(cedulaPaciente: String, nombreVacuna: String)
+ mostrarHistorial(cedulaPaciente: String)
+ mostrarCatalogoVacunas()
+ historialPorPaciente(cedula: String): List<Vacunacion>
+ getVacunaciones(): List<Vacunacion>
+ registrarVacunacion(vacunacion: Vacunacion)
+ getCatalogoVacunas(): List<Vacuna>
+ obtenerSiguienteDosis(cedulaPaciente: String, nombreVacuna: String): int
  }

class ReporteService {
- pacienteService: PacienteService
- vacunacionService: VacunacionService
+ ReporteService(pacienteService, vacunacionService)
+ alertasDosisFaltantes()
+ reporteMensual(anio: int, mes: Month)
+ generarReporteMensual(anio: int, mes: int): String
  }

class Main {
- sc: Scanner
- pacienteService: PacienteService
- vacunacionService: VacunacionService
- reporteService: ReporteService
- dtf: DateTimeFormatter
+ main(args: String[])
- pedirCedulaValida(): String
- registrarPaciente()
- buscarPaciente()
- actualizarPaciente()
- registrarVacunacion()
- verHistorial()
- reporteMensual()
- agregarVacuna()
  }

class AppGUI extends JFrame {
- pacienteService: PacienteService
- vacunacionService: VacunacionService
- reporteService: ReporteService
+ AppGUI()
- getAlertasText(): String
- getCatalogoText(): String
+ main(args: String[])
  }

class VentanaPrincipal extends JFrame {
- pacienteService: PacienteService
- vacunacionService: VacunacionService
- reporteService: ReporteService
+ VentanaPrincipal()
- mostrarAlertas()
- mostrarCatalogo()
+ main(args: String[])
  }

class ActualizarPacienteDialog extends JDialog {
- pacienteService: PacienteService
- tfCedulaBusqueda: JTextField
- btnBuscar: JButton
- tfDireccion: JTextField
- tfEdad: JTextField
- tfCondicion: JTextField
- btnActualizar: JButton
- pacienteActual: Paciente
+ ActualizarPacienteDialog(parent, pacienteService)
- buscarPaciente()
- actualizarPaciente()
  }

class AgregarVacunaDialog extends JDialog {
- vacunacionService: VacunacionService
- tfNombre: JTextField
- tfDosisRequeridas: JTextField
+ AgregarVacunaDialog(owner, vacunacionService)
- agregarVacuna()
  }

class BuscarPacienteDialog extends JDialog {
- pacienteService: PacienteService
- tfBusqueda: JTextField
- taResultados: JTextArea
- cbTipoBusqueda: JComboBox<String>
+ BuscarPacienteDialog(parent, pacienteService)
- realizarBusqueda()
  }

class RegistrarPacienteDialog extends JDialog {
- tfNombre: JTextField
- tfCedula: JTextField
- tfDireccion: JTextField
- tfEdad: JTextField
- tfCondicion: JTextField
- pacienteService: PacienteService
+ RegistrarPacienteDialog(parent, pacienteService)
- registrarPaciente()
  }

class RegistrarVacunacionDialog extends JDialog {
- pacienteService: PacienteService
- vacunacionService: VacunacionService
- tfCedulaPaciente: JTextField
- cbVacunas: JComboBox<String>
- tfFecha: JTextField
- tfNombreProf: JTextField
- tfCedulaProf: JTextField
- tfDireccionProf: JTextField
- tfEdadProf: JTextField
- cbTipoProf: JComboBox<String>
+ RegistrarVacunacionDialog(parent, pacienteService, vacunacionService)
- registrarVacunacion()
  }

class ReporteMensualDialog extends JDialog {
- reporteService: ReporteService
- tfMesAnio: JTextField
- taReporte: JTextArea
+ ReporteMensualDialog(owner, reporteService)
- generarReporte()
  }

class VerHistorialDialog extends JDialog {
- vacunacionService: VacunacionService
- tfCedula: JTextField
- taHistorial: JTextArea
+ VerHistorialDialog(owner, vacunacionService)
- mostrarHistorial()
  }


' Relaciones
PacienteService "1" *-- "0..*" Paciente : gestiona >
VacunacionService "1" *-- "0..*" Vacunacion : gestiona >
VacunacionService "1" *-- "0..*" Vacuna : gestiona catálogo >
Vacunacion "1" *-- "1" ProfesionalSalud : aplicada por >
Vacunacion "1" -- "1" Paciente : para >
ReporteService ..> PacienteService : utiliza
ReporteService ..> VacunacionService : utiliza

Main ..> PacienteService
Main ..> VacunacionService
Main ..> ReporteService

AppGUI ..> PacienteService
AppGUI ..> VacunacionService
AppGUI ..> ReporteService
AppGUI ..> RegistrarPacienteDialog
AppGUI ..> ActualizarPacienteDialog
AppGUI ..> RegistrarVacunacionDialog
AppGUI ..> VerHistorialDialog
AppGUI ..> ReporteMensualDialog
AppGUI ..> AgregarVacunaDialog

VentanaPrincipal ..> PacienteService
VentanaPrincipal ..> VacunacionService
VentanaPrincipal ..> ReporteService
VentanaPrincipal ..> RegistrarPacienteDialog
VentanaPrincipal ..> BuscarPacienteDialog
VentanaPrincipal ..> ActualizarPacienteDialog
VentanaPrincipal ..> RegistrarVacunacionDialog
VentanaPrincipal ..> VerHistorialDialog
VentanaPrincipal ..> ReporteMensualDialog
VentanaPrincipal ..> AgregarVacunaDialog

ActualizarPacienteDialog ..> PacienteService
AgregarVacunaDialog ..> VacunacionService
BuscarPacienteDialog ..> PacienteService
RegistrarPacienteDialog ..> PacienteService
RegistrarVacunacionDialog ..> PacienteService
RegistrarVacunacionDialog ..> VacunacionService
ReporteMensualDialog ..> ReporteService
VerHistorialDialog ..> VacunacionService

@enduml


##  Funcionamiento Detallado de los Módulos del Sistema

El sistema se organiza en varios módulos o servicios principales que gestionan la lógica de negocio y la interacción con los datos. La interfaz de usuario, implementada con Swing, interactúa con estos servicios para realizar las operaciones.

### Módulos de Servicio:

*   *PacienteService*:
    *   *Responsabilidad*: Gestiona toda la información relacionada con los pacientes.
    *   *Funciones Clave*:
        *   registrarPaciente(Paciente p): Añade un nuevo paciente a la lista de pacientes.
        *   listarPacientes(): Devuelve la lista completa de pacientes registrados.
        *   buscarPorCedula(String cedula): Busca un paciente específico por su número de cédula.
        *   buscarPorNombre(String nombre): Busca pacientes cuyos nombres coincidan parcial o totalmente con el término de búsqueda.
        *   actualizarPaciente(String cedula, String direccion, int edad, String condicionMedica): Modifica los datos de un paciente existente.
    *   *Interacción*: Es utilizado por las clases de diálogo como RegistrarPacienteDialog, BuscarPacienteDialog y ActualizarPacienteDialog para manejar los datos de los pacientes.

*   *VacunacionService*:
    *   *Responsabilidad*: Administra el catálogo de vacunas, el registro de vacunaciones y el historial de vacunación de los pacientes.
    *   *Funciones Clave*:
        *   agregarVacunaAlCatalogo(String nombre, int dosisRequeridas): Permite añadir nuevas vacunas al sistema, especificando el número de dosis necesarias.
        *   registrarVacunacion(Vacunacion vacunacion): Registra una nueva aplicación de vacuna a un paciente, incluyendo detalles como la fecha, tipo de vacuna, dosis y el profesional de salud que la administró.
        *   mostrarHistorial(String cedulaPaciente): Muestra el historial de vacunación de un paciente específico (utilizado principalmente en la versión de consola).
        *   historialPorPaciente(String cedula): Devuelve una lista de todas las vacunaciones registradas para un paciente.
        *   getCatalogoVacunas(): Devuelve la lista de todas las vacunas disponibles en el catálogo.
        *   obtenerSiguienteDosis(String cedulaPaciente, String nombreVacuna): Calcula cuál es la siguiente dosis que corresponde para una vacuna específica para un paciente, basándose en su historial.
    *   *Interacción*: Es fundamental para RegistrarVacunacionDialog (para registrar nuevas vacunaciones y seleccionar vacunas del catálogo), VerHistorialDialog (para mostrar el historial), y AgregarVacunaDialog (para añadir nuevas vacunas). También es usado por AppGUI y VentanaPrincipal para mostrar el catálogo.

*   *ReporteService*:
    *   *Responsabilidad*: Genera reportes y alertas basados en la información de pacientes y vacunaciones.
    *   *Funciones Clave*:
        *   alertasDosisFaltantes(): Identifica y reporta pacientes que tienen dosis pendientes para completar sus esquemas de vacunación (utilizado en la versión de consola y adaptado para la GUI).
        *   reporteMensual(int anio, Month mes): Genera un resumen de las vacunaciones realizadas en un mes y año específicos (versión de consola).
        *   generarReporteMensual(int anio, int mes): Versión para la GUI que devuelve el reporte mensual como una cadena de texto.
    *   *Interacción*: Utiliza PacienteService y VacunacionService para obtener los datos necesarios. Es invocado por ReporteMensualDialog para mostrar los reportes y por AppGUI/VentanaPrincipal para las alertas.

### Interfaz de Usuario (Clases Swing):

*   *AppGUI / VentanaPrincipal*:
    *   Son las ventanas principales de la aplicación que contienen el menú de opciones (botones).
    *   Actúan como puntos de entrada para acceder a las diferentes funcionalidades.
    *   Instancian y muestran los diversos diálogos (JDialog) según la opción seleccionada por el usuario.
    *   Interactúan con los servicios (PacienteService, VacunacionService, ReporteService) para obtener datos a mostrar directamente (ej. catálogo de vacunas, alertas) o para pasar las instancias de servicio a los diálogos.

*   *Diálogos (ActualizarPacienteDialog, AgregarVacunaDialog, BuscarPacienteDialog, RegistrarPacienteDialog, RegistrarVacunacionDialog, ReporteMensualDialog, VerHistorialDialog)*:
    *   Cada diálogo es una ventana específica para una tarea particular (ej. registrar un nuevo paciente, buscar un paciente, etc.).
    *   Contienen los campos de texto, botones y otros componentes necesarios para la interacción del usuario.
    *   Reciben instancias de los servicios correspondientes en sus constructores para poder invocar la lógica de negocio (ej. RegistrarPacienteDialog usa PacienteService para guardar el nuevo paciente).
    *   Manejan la validación de entradas y muestran mensajes de error o confirmación al usuario mediante JOptionPane.

### Flujo General:

1.  El usuario interactúa con AppGUI o VentanaPrincipal seleccionando una opción del menú.
2.  La ventana principal instancia y muestra el diálogo correspondiente a la opción seleccionada, pasándole las instancias de los servicios necesarios.
3.  El usuario ingresa datos en el diálogo.
4.  Al realizar una acción (ej. presionar "Guardar"), el diálogo invoca los métodos apropiados del servicio correspondiente (ej. pacienteService.registrarPaciente(...)).
5.  El servicio procesa la solicitud, actualizando sus datos internos (listas, mapas).
6.  El diálogo informa al usuario sobre el resultado de la operación.
7.  Para consultas o reportes, los servicios recuperan y procesan la información, que luego es mostrada por los diálogos o la ventana principal.

Este diseño separa la lógica de la interfaz de usuario de la lógica de negocio, lo que facilita el mantenimiento y la escalabilidad del sistema.

### Capacidades del Usuario en el Sistema

El sistema está diseñado para ser utilizado por personal de salud autorizado, quienes podrán realizar una variedad de gestiones esenciales para el control de la vacunación. A través de la interfaz gráfica, un usuario puede:

*   *Gestionar Pacientes:*
    *   *Registrar Nuevos Pacientes:* Ingresar la información personal y médica relevante de individuos que requieren seguimiento de vacunación. Esto incluye nombre, cédula, dirección, edad y condiciones médicas preexistentes.
    *   *Buscar Pacientes Existentes:* Localizar rápidamente registros de pacientes utilizando su número de cédula o nombre para acceder a su información o historial.
    *   *Actualizar Información de Pacientes:* Modificar datos de pacientes previamente registrados, como cambios de dirección, edad o actualización de su condición médica.

*   *Gestionar Vacunaciones:*
    *   *Registrar Aplicación de Vacunas:* Documentar cada evento de vacunación, especificando el paciente, el tipo de vacuna administrada (seleccionada del catálogo), la fecha de aplicación, la dosis correspondiente y los datos del profesional de salud responsable.
    *   *Consultar Historial de Vacunación:* Visualizar el registro completo de vacunas recibidas por un paciente específico, incluyendo fechas, tipos de vacuna y dosis.

*   *Administrar el Catálogo de Vacunas:*
    *   *Agregar Nuevos Tipos de Vacunas:* Incorporar nuevas vacunas al catálogo del sistema, definiendo su nombre y el número total de dosis requeridas para completar el esquema.
    *   *Visualizar Catálogo de Vacunas:* Consultar la lista de todas las vacunas disponibles en el sistema con sus respectivas dosis requeridas.

*   *Generar Reportes y Alertas:*
    *   *Obtener Alertas de Dosis Faltantes:* Identificar y listar aquellos pacientes que aún no han completado su esquema de vacunación para una o más vacunas, facilitando el seguimiento proactivo.
    *   *Generar Reportes Mensuales:* Producir informes consolidados sobre la cantidad y tipos de vacunas administradas durante un mes específico, útiles para la gestión interna y para reportar a autoridades sanitarias.

Estas capacidades permiten al personal de salud mantener un control detallado y eficiente del proceso de vacunación en la comunidad, asegurando que los pacientes reciban las inmunizaciones necesarias de manera oportuna.