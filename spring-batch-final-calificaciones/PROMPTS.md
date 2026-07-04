**** Generando el pom.xml solicitado ****

Genera un pom.xml para un proyecto Spring Boot 3.2.2 con Java 17 y estas dependencias:
spring-boot-starter-batch, mysql-connector-j (scope runtime), spring-boot-starter-data-mongodb, springboot-
starter-web, spring-boot-starter-data-jpa y spring-boot-starter-test (scope test). groupId
com.academia, artifactId spring-batch-final-calificaciones, versión 1.0.0. Incluye el spring-boot-mavenplugin. Verifica que las dependencias abran y cierren correctamente, no mezcles etiquetas de cierre con etiquetas de apertura, además antes de generar verifica que tanto las librerias como los componentes se declaren correctamente, en las 6 dependencias que te solicite genera estos cambios

**** Correcciones en el prompt ****

verifica que existen los <groupId> de las depencias y generalos. toma el scrip generado anteriormente y has las correcciones solicitadas.

**** Creación de la clase estudiante ****

// Clase modelo Estudiante con los campos: nombre (String), grupo (String),
// nota1, nota2, nota3 y promedio (todos double).
// Incluye constructor vacio, getters y setters de todos los campos,
// y un toString que muestre nombre, grupo y promedio.    


**** Creación de EstudianteProcessor *****

// Processor de Spring Batch que implementa ItemProcessor<Estudiante, Estudiante>.
// En el metodo process: calcula el promedio como (nota1 + nota2 + nota3) / 3,
// asigna el promedio al estudiante con setPromedio, registra un log con SLF4J
// "Step 1 - Procesando: {estudiante}" y devuelve el estudiante.

**** Creación de EstudianteReporte ****

// Documento de MongoDB (@Document collection = "reportes_estudiantes") con:
// id (String, anotado con @Id), nombre, grupo, promedio (double) y estado (String).
// Constructor vacio, getters, setters y toString.

**** Creación ReporteEstudianteProcessor ****

// Processor que implementa ItemProcessor<Estudiante, EstudianteReporte>.
// Convierte un Estudiante en un EstudianteReporte copiando nombre, grupo y promedio,
// y asigna estado "APROBADO" si el promedio es >= 70, o "REPROBADO" si es menor.
// Loguea "Step 2 - Reporte: {reporte}" y devuelve el reporte.

**** Creación de BatchConfig ****

Step 1 "paso1": FlatFileItemReader que lee "estudiantes.csv" del classpath (delimitado, columnas
nombre,grupo,nota1,nota2,nota3, salta 1 linea, targetType Estudiante), procesa con EstudianteProcessor y escribe
en MySQL con JdbcBatchItemWriter (INSERT en estudiantes_procesados con
nombre,grupo,nota1,nota2,nota3,promedio, beanMapped).
Step 2 "paso2": JdbcCursorItemReader que hace SELECT nombre,grupo,promedio de estudiantes_procesados,
procesa con ReporteEstudianteProcessor y escribe en MongoDB con MongoItemWriter en la coleccion
"reportes_estudiantes".
Ambos steps con chunk de 3.
Un Job "procesarCalificacionesJob" con RunIdIncremente

Aquí Copilot cometio algunos erroes fueron minimos en la sintaxis y lógica dentro del código pero fueron corregidos con el scrip del proyecto anterior

**** Creación de SpringBatchApplication *****

// Clase principal de Spring Boot con @SpringBootApplication y el metodo main
// que arranca la aplicacion con SpringApplication.run.

**** Creación de application.properties ****

Genera un application.properties para Spring Boot que se conecte a MySQL en
jdbc:mysql://localhost:3306/academia (usuario alumno, password alumno123), inicialice el esquema de Spring
Batch siempre, ejecute el Job al arrancar, y se conecte a MongoDB en
mongodb://root:root123@localhost:27018/academia?authSource=admin.

Hubo que corregir los puertos y el usuario root

**** Creación de EstudianteEntity ****

// Entidad JPA (@Entity, @Table name="estudiantes_procesados") que mapea la tabla existente.
// id Long con @Id y @GeneratedValue(IDENTITY); campos nombre, grupo, nota1, nota2, nota3,
// promedio; getters y setters.

**** Creacón de EstudianteRepository y ReporteRepository ****

// Interfaz EstudianteRepository que extiende JpaRepository<EstudianteEntity, Long>
// con un metodo findByGrupo(String grupo) que devuelve List<EstudianteEntity>.

// Interfaz ReporteRepository que extiende MongoRepository<EstudianteReporte, String>
// con un metodo findByEstado(String estado) que devuelve List<EstudianteReporte>.

**** Creación de EstudianteController ****

Genera un @RestController en /api/estudiantes que use
EstudianteRepository y EstudianteService (inyectados por constructor) con: GET / (listar todos), GET /{id}
(200 o 404), GET /aprobados/total (devuelve un Map con el conteo del servicio), POST / (crea, 201 Created),
PUT /{id} (reemplaza, 200 o 404), PATCH /{id} (cambia solo el grupo desde un Map, 200 o 404), DELETE /{id}
(204 o 404). Usa ResponseEntity para los codigos de respuesta.

Faltaron algunas importaciones pero se agregaron y se corrigieron errores de sintaxis en los metodos PATCH y DELETE.

**** Creación de ReporteController ****

// @RestController en /api/reportes que usa ReporteRepository:
// GET / lista todos los reportes; GET /estado/{estado} devuelve los que tengan ese estado
// (convertido a mayusculas) usando findByEstado.

**** Creación de pruebas unitarias con Junit *****

Genera pruebas unitarias con JUnit 5 para EstudianteProcessor y
ReporteEstudianteProcessor: que verifiquen que el promedio se calcula bien, y que el estado es APROBADO con
promedio 70 y REPROBADO con 69.9.

Fallaron en la sitaxis y lógicas de las pruebas unitarias se tuvo que corregir haciendo un prompt tomando el código del proyecto pasado y guiando a copilot
para que generara correctamente las pruebas unitarias.

**** Creación de pruebas unitarias con Mockito *****

Genera una prueba unitaria de EstudianteService con Mockito: mockea EstudianteRepository con
@Mock, inyecta el servicio con @InjectMocks, usa @ExtendWith(MockitoExtension.class), simula findAll()
devolviendo 2 estudiantes aprobados y 1 reprobado, y verifica que contarAprobados() devuelve 2.

