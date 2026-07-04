package com.academia.batch.controller;

import com.academia.batch.repository.EstudianteEntity;
import com.academia.batch.repository.EstudianteRepository;
import com.academia.batch.service.EstudianteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/estudiantes")   // Base URI for the resource
public class EstudianteController {

    private final EstudianteRepository repository;
    private final EstudianteService service;

    public EstudianteController(EstudianteRepository repository, EstudianteService service) {
        this.repository = repository;
        this.service = service;
    }

    // GET /api/estudiantes -> List all students (200)
    @GetMapping
    public List<EstudianteEntity> listar() {
        return repository.findAll();
    }

    // GET /api/estudiantes/{id} -> Get one student; 200 or 404
    @GetMapping("/{id}")
    public ResponseEntity<EstudianteEntity> obtener(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)                      // 200 OK
                .orElse(ResponseEntity.notFound().build());   // 404 Not Found
    }

    // GET /api/estudiantes/aprobados/total -> Calculated data by the service
    @GetMapping("/aprobados/total")
    public Map<String, Long> totalAprobados() {
        return Map.of("aprobados", service.contarAprobados());
    }

    // POST /api/estudiantes -> Create a new student; 201 Created
    @PostMapping
    public ResponseEntity<EstudianteEntity> crear(@RequestBody EstudianteEntity nuevo) {
        EstudianteEntity guardado = repository.save(nuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);   // 201
    }

    // PUT /api/estudiantes/{id} -> Replace a student completely; 200 or 404
    @PutMapping("/{id}")
    public ResponseEntity<EstudianteEntity> actualizar(@PathVariable Long id,
                                                       @RequestBody EstudianteEntity datos) {
        return repository.findById(id)
                .map(e -> {
                    e.setNombre(datos.getNombre());
                    e.setGrupo(datos.getGrupo());
                    e.setNota1(datos.getNota1());
                    e.setNota2(datos.getNota2());
                    e.setNota3(datos.getNota3());
                    e.setPromedio(datos.getPromedio());
                    return ResponseEntity.ok(repository.save(e));   // 200
                })
                .orElse(ResponseEntity.notFound().build());          // 404
    }

    // PATCH /api/estudiantes/{id} -> Partial update (only group); 200 or 404
    @PatchMapping("/{id}")
    public ResponseEntity<EstudianteEntity> cambiarGrupo(@PathVariable Long id,
                                                         @RequestBody Map<String, String> cambios) {
        return repository.findById(id)
                .map(e -> {
                    if (cambios.containsKey("grupo")) {
                        e.setGrupo(cambios.get("grupo"));
                    }
                    return ResponseEntity.ok(repository.save(e));   // 200
                })
                .orElse(ResponseEntity.notFound().build());          // 404
    }

    // DELETE /api/estudiantes/{id} -> Delete a student; 204 or 404
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();                // 404
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();                   // 204 No Content
    }
}
