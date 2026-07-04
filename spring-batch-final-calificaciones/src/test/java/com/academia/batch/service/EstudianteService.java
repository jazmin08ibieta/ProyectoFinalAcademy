// Java
package com.academia.batch.service;

import com.academia.batch.repository.EstudianteEntity;
import com.academia.batch.repository.EstudianteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EstudianteServiceTest {

    @Mock
    private EstudianteRepository estudianteRepository;

    @InjectMocks
    private EstudianteService estudianteService;

    @Test
    void testContarAprobados() {
        // Arrange: Simula findAll() devolviendo 2 aprobados y 1 reprobado con escala de 0 a 100
        EstudianteEntity estudiante1 = new EstudianteEntity();
        estudiante1.setPromedio(75.0); // Cambiado de 7.5 a 75.0 (Aprobado)

        EstudianteEntity estudiante2 = new EstudianteEntity();
        estudiante2.setPromedio(80.0); // Cambiado de 8.0 a 80.0 (Aprobado)

        EstudianteEntity estudiante3 = new EstudianteEntity();
        estudiante3.setPromedio(40.0); // Cambiado de 4.0 a 40.0 (Reprobado)

        List<EstudianteEntity> estudiantes = Arrays.asList(estudiante1, estudiante2, estudiante3);
        when(estudianteRepository.findAll()).thenReturn(estudiantes);

        // Act: Llama al método contarAprobados()
        long totalAprobados = estudianteService.contarAprobados();

        // Assert: Verifica que el resultado sea 2
        assertEquals(2, totalAprobados);
    }
}

