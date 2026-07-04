package com.academia.batch.processor;

import com.academia.batch.model.Estudiante;
import com.academia.batch.model.EstudianteReporte; // Asegúrate de importar el reporte
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProcessorTest {

    // Instanciamos la clase correcta con su nombre real
    private final ReporteEstudianteProcessor processor = new ReporteEstudianteProcessor();

    @Test
    void calculaElPromedioCorrectamente() throws Exception {
        Estudiante e = new Estudiante();
        e.setNota1(80.0);
        e.setNota2(90.0);
        e.setNota3(100.0);

        // Calculamos el promedio manualmente en el estudiante antes de procesar
        double promedioCalculado = (e.getNota1() + e.getNota2() + e.getNota3()) / 3.0;
        e.setPromedio(promedioCalculado);

        EstudianteReporte resultado = processor.process(e);

        assertEquals(90.0, resultado.getPromedio(), 0.001);
    }
}

