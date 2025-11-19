package com.jcaa.hexagonal.core.service;

import com.jcaa.hexagonal.core.domin.exception.NotFoundException;
import com.jcaa.hexagonal.core.domin.exception.ValidationException;
import com.jcaa.hexagonal.core.domin.estudiantes.Apellido;
import com.jcaa.hexagonal.core.domin.estudiantes.Estudiante;
import com.jcaa.hexagonal.core.domin.estudiantes.EstudianteEmail;
import com.jcaa.hexagonal.core.domin.estudiantes.EstudianteId;
import com.jcaa.hexagonal.core.domin.estudiantes.FechaNacimiento;
import com.jcaa.hexagonal.core.domin.estudiantes.Genero;
import com.jcaa.hexagonal.core.domin.estudiantes.Nombre;
import com.jcaa.hexagonal.core.domin.estudiantes.Programa;
import com.jcaa.hexagonal.core.domin.estudiantes.Semestre;
import com.jcaa.hexagonal.core.domin.estudiantes.Telefono;
import com.jcaa.hexagonal.core.domin.estudiantes.Universidad;
import com.jcaa.hexagonal.core.port.EstudianteRepository;
import com.jcaa.hexagonal.core.service.dto.EstudianteDto;
import com.jcaa.hexagonal.core.service.mappers.EstudianteServiceMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
@Transactional
public class EstudianteService {
    
    private final EstudianteRepository estudianteRepository;
    
    public EstudianteService(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }
    
    public EstudianteDto registrarEstudiante(String nombre,
                                             String apellido,
                                             LocalDate fechaNacimiento,
                                             Integer semestre,
                                             String email,
                                             String genero,
                                             String telefono,
                                             String programa,
                                             String universidad) {
        
        Nombre nombreVo = new Nombre(nombre);
        Apellido apellidoVo = new Apellido(apellido);
        FechaNacimiento fechaNacimientoVo = new FechaNacimiento(fechaNacimiento);
        Semestre semestreVo = new Semestre(semestre);
        EstudianteEmail emailVo = new EstudianteEmail(email);
        Genero generoVo = new Genero(genero);
        Telefono telefonoVo = new Telefono(telefono);
        Programa programaVo = new Programa(programa);
        Universidad universidadVo = new Universidad(universidad);
        
        if (estudianteRepository.existsByEmail(emailVo)) {
            throw new ValidationException("El email del estudiante ya está registrado");
        }
        
        Estudiante estudiante = Estudiante.registrar(
                nombreVo,
                apellidoVo,
                fechaNacimientoVo,
                semestreVo,
                emailVo,
                generoVo,
                telefonoVo,
                programaVo,
                universidadVo
        );
        
        estudiante = estudianteRepository.save(estudiante);
        return EstudianteServiceMapper.INSTANCE.toDto(estudiante);
    }
    
    @Transactional(readOnly = true)
    public EstudianteDto obtenerPorId(UUID id) {
        Estudiante estudiante = estudianteRepository.findById(EstudianteId.from(id))
                .orElseThrow(() -> new NotFoundException("Estudiante", id));
        return EstudianteServiceMapper.INSTANCE.toDto(estudiante);
    }
    
    @Transactional(readOnly = true)
    public Page<EstudianteDto> listar(Pageable pageable) {
        Page<Estudiante> estudiantes = estudianteRepository.findAll(pageable);
        return estudiantes.map(EstudianteServiceMapper.INSTANCE::toDto);
    }
    
    public EstudianteDto actualizarEstudiante(UUID id,
                                              String nombre,
                                              String apellido,
                                              LocalDate fechaNacimiento,
                                              Integer semestre,
                                              String email,
                                              String genero,
                                              String telefono,
                                              String programa,
                                              String universidad) {
        Estudiante estudiante = estudianteRepository.findById(EstudianteId.from(id))
                .orElseThrow(() -> new NotFoundException("Estudiante", id));
        
        if (nombre != null) {
            estudiante.actualizarNombre(new Nombre(nombre));
        }
        if (apellido != null) {
            estudiante.actualizarApellido(new Apellido(apellido));
        }
        if (fechaNacimiento != null) {
            estudiante.actualizarFechaNacimiento(new FechaNacimiento(fechaNacimiento));
        }
        if (semestre != null) {
            estudiante.actualizarSemestre(new Semestre(semestre));
        }
        if (email != null) {
            EstudianteEmail nuevoEmail = new EstudianteEmail(email);
            if (estudianteRepository.existsByEmail(nuevoEmail) &&
                !estudiante.getEmail().equals(nuevoEmail)) {
                throw new ValidationException("El email del estudiante ya está registrado");
            }
            estudiante.actualizarEmail(nuevoEmail);
        }
        if (genero != null) {
            estudiante.actualizarGenero(new Genero(genero));
        }
        if (telefono != null) {
            estudiante.actualizarTelefono(new Telefono(telefono));
        }
        if (programa != null) {
            estudiante.actualizarPrograma(new Programa(programa));
        }
        if (universidad != null) {
            estudiante.actualizarUniversidad(new Universidad(universidad));
        }
        
        estudiante = estudianteRepository.save(estudiante);
        return EstudianteServiceMapper.INSTANCE.toDto(estudiante);
    }
    
    public void eliminarEstudiante(UUID id) {
        Estudiante estudiante = estudianteRepository.findById(EstudianteId.from(id))
                .orElseThrow(() -> new NotFoundException("Estudiante", id));
        estudianteRepository.delete(estudiante.getId());
    }
}

