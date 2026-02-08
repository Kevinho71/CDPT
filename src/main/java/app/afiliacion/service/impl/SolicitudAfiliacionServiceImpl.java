package app.afiliacion.service.impl;

import app.afiliacion.dto.SolicitudAfiliacionCreateDTO;
import app.afiliacion.dto.SolicitudAfiliacionResponseDTO;
import app.afiliacion.dto.SolicitudAfiliacionUpdateDTO;
import app.afiliacion.entity.SolicitudAfiliacionEntity;
import app.afiliacion.entity.SolicitudInicialAfiliacionEntity;
import app.afiliacion.repository.SolicitudAfiliacionRepository;
import app.afiliacion.repository.SolicitudInicialAfiliacionRepository;
import app.afiliacion.service.SolicitudAfiliacionService;
import app.common.exception.ResourceNotFoundException;
import app.common.util.ArchivoService;
import app.core.entity.PersonaEntity;
import app.core.repository.PersonaRepository;
import app.finanza.entity.ConfiguracionCuotasEntity;
import app.finanza.entity.EstadoCuentaSocioEntity;
import app.finanza.repository.ConfiguracionCuotasRepository;
import app.finanza.repository.EstadoCuentaSocioRepository;
import app.perfil.entity.PerfilSocioEntity;
import app.perfil.repository.PerfilSocioRepository;
import app.socio.entity.SocioEntity;
import app.socio.repository.SocioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SolicitudAfiliacionServiceImpl implements SolicitudAfiliacionService {

    @Autowired
    private SolicitudAfiliacionRepository repository;

    @Autowired
    private SolicitudInicialAfiliacionRepository solicitudInicialRepository;

    @Autowired
    private ArchivoService archivoService;

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private SocioRepository socioRepository;

    @Autowired
    private PerfilSocioRepository perfilSocioRepository;

    @Autowired
    private EstadoCuentaSocioRepository estadoCuentaSocioRepository;

    @Autowired
    private ConfiguracionCuotasRepository configuracionCuotasRepository;

    @Override
    @Transactional(readOnly = true)
    public List<SolicitudAfiliacionResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SolicitudAfiliacionResponseDTO> findByEstadoAfiliacion(String estadoAfiliacion) {
        return repository.findByEstadoAfiliacion(estadoAfiliacion).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public SolicitudAfiliacionResponseDTO findById(Integer id) {
        SolicitudAfiliacionEntity entity = repository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Solicitud de afiliación no encontrada con ID: " + id));
        return toResponseDTO(entity);
    }

    @Override
    public SolicitudAfiliacionResponseDTO create(SolicitudAfiliacionCreateDTO dto,
            MultipartFile fotoCarnetAnverso,
            MultipartFile fotoCarnetReverso,
            MultipartFile fotoTituloProvisicion,
            MultipartFile cv) throws IOException {
        SolicitudAfiliacionEntity entity = new SolicitudAfiliacionEntity();

        // Relación con solicitud inicial si existe
        if (dto.getFkSolicitudInicialAfiliacion() != null) {
            SolicitudInicialAfiliacionEntity solicitudInicial = solicitudInicialRepository
                    .findById(dto.getFkSolicitudInicialAfiliacion())
                    .orElseThrow(() -> new ResourceNotFoundException("Solicitud inicial no encontrada"));
            entity.setSolicitudInicial(solicitudInicial);
        }

        // Datos personales
        entity.setNombres(dto.getNombres());
        entity.setApellidos(dto.getApellidos());
        entity.setCi(dto.getCi());
        entity.setCiExpedido(dto.getCiExpedido());
        entity.setFechaNacimiento(dto.getFechaNacimiento());
        entity.setGenero(dto.getGenero());
        entity.setDireccionDomicilio(dto.getDireccionDomicilio());

        // Subir archivos o usar URLs proporcionadas
        if (fotoCarnetAnverso != null && !fotoCarnetAnverso.isEmpty()) {
            entity.setUrlFotoCarnetAnverso(archivoService.uploadFile(fotoCarnetAnverso, "SOCIO_DOCUMENTOS_AFILIACION"));
        } else if (dto.getUrlFotoCarnetAnverso() != null) {
            entity.setUrlFotoCarnetAnverso(dto.getUrlFotoCarnetAnverso());
        }

        if (fotoCarnetReverso != null && !fotoCarnetReverso.isEmpty()) {
            entity.setUrlFotoCarnetReverso(archivoService.uploadFile(fotoCarnetReverso, "SOCIO_DOCUMENTOS_AFILIACION"));
        } else if (dto.getUrlFotoCarnetReverso() != null) {
            entity.setUrlFotoCarnetReverso(dto.getUrlFotoCarnetReverso());
        }

        if (fotoTituloProvisicion != null && !fotoTituloProvisicion.isEmpty()) {
            entity.setUrlFotoTituloProvisicion(
                    archivoService.uploadFile(fotoTituloProvisicion, "SOCIO_DOCUMENTOS_AFILIACION"));
        } else if (dto.getUrlFotoTituloProvisicion() != null) {
            entity.setUrlFotoTituloProvisicion(dto.getUrlFotoTituloProvisicion());
        }

        if (cv != null && !cv.isEmpty()) {
            entity.setUrlCv(archivoService.uploadFile(cv, "SOCIO_DOCUMENTOS_AFILIACION"));
        } else if (dto.getUrlCv() != null) {
            entity.setUrlCv(dto.getUrlCv());
        }

        entity.setEstadoAfiliacion("EN_REVISION");

        SolicitudAfiliacionEntity saved = repository.save(entity);
        return toResponseDTO(saved);
    }

    @Override
    public SolicitudAfiliacionResponseDTO update(Integer id, SolicitudAfiliacionUpdateDTO dto) {
        SolicitudAfiliacionEntity entity = repository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Solicitud de afiliación no encontrada con ID: " + id));

        if (dto.getEstadoAfiliacion() != null) {
            entity.setEstadoAfiliacion(dto.getEstadoAfiliacion());
        }

        SolicitudAfiliacionEntity updated = repository.save(entity);
        return toResponseDTO(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Solicitud de afiliación no encontrada con ID: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public SolicitudAfiliacionResponseDTO createConToken(String token,
            SolicitudAfiliacionCreateDTO dto,
            MultipartFile fotoCarnetAnverso,
            MultipartFile fotoCarnetReverso,
            MultipartFile fotoTituloProvisicion,
            MultipartFile cv) throws IOException {
        // Validar que el token existe
        SolicitudInicialAfiliacionEntity solicitudInicial = solicitudInicialRepository
                .findByTokenAcceso(token)
                .orElseThrow(() -> new ResourceNotFoundException("Token inválido o no encontrado"));

        // Validar que el token no ha expirado
        if (solicitudInicial.getTokenExpiracion() != null &&
                LocalDateTime.now().isAfter(solicitudInicial.getTokenExpiracion())) {
            throw new IllegalStateException("El token ha expirado. Solicite uno nuevo al administrador.");
        }

        // Validar que el estado es LINK_ENVIADO
        if (!"LINK_ENVIADO".equals(solicitudInicial.getEstado())) {
            throw new IllegalStateException("El token no está en estado válido para crear la solicitud");
        }

        // Crear la solicitud vinculándola a la solicitud inicial
        SolicitudAfiliacionEntity entity = new SolicitudAfiliacionEntity();
        entity.setSolicitudInicial(solicitudInicial);

        // Datos personales
        entity.setNombres(dto.getNombres());
        entity.setApellidos(dto.getApellidos());
        entity.setCi(dto.getCi());
        entity.setCiExpedido(dto.getCiExpedido());
        entity.setFechaNacimiento(dto.getFechaNacimiento());
        entity.setGenero(dto.getGenero());
        entity.setDireccionDomicilio(dto.getDireccionDomicilio());

        // Subir archivos
        if (fotoCarnetAnverso != null && !fotoCarnetAnverso.isEmpty()) {
            entity.setUrlFotoCarnetAnverso(archivoService.uploadFile(fotoCarnetAnverso, "SOCIO_DOCUMENTOS_AFILIACION"));
        }

        if (fotoCarnetReverso != null && !fotoCarnetReverso.isEmpty()) {
            entity.setUrlFotoCarnetReverso(archivoService.uploadFile(fotoCarnetReverso, "SOCIO_DOCUMENTOS_AFILIACION"));
        }

        if (fotoTituloProvisicion != null && !fotoTituloProvisicion.isEmpty()) {
            entity.setUrlFotoTituloProvisicion(
                    archivoService.uploadFile(fotoTituloProvisicion, "SOCIO_DOCUMENTOS_AFILIACION"));
        }

        if (cv != null && !cv.isEmpty()) {
            entity.setUrlCv(archivoService.uploadFile(cv, "SOCIO_DOCUMENTOS_AFILIACION"));
        }

        entity.setEstadoAfiliacion("EN_REVISION");

        SolicitudAfiliacionEntity saved = repository.save(entity);

        // Actualizar estado de la solicitud inicial
        solicitudInicial.setEstado("FINALIZADO");
        solicitudInicialRepository.save(solicitudInicial);

        return toResponseDTO(saved);
    }

    @Override
    @Transactional
    public void aprobarSolicitud(Integer solicitudId) {
        // Obtener la solicitud
        SolicitudAfiliacionEntity solicitud = repository.findById(solicitudId)
                .orElseThrow(() -> new ResourceNotFoundException("Solicitud no encontrada con ID: " + solicitudId));

        // Validar que está en estado EN_REVISION
        if (!"EN_REVISION".equals(solicitud.getEstadoAfiliacion())) {
            throw new IllegalStateException("La solicitud no está en estado EN_REVISION");
        }

        // 1. CREAR LA PERSONA
        PersonaEntity persona = new PersonaEntity();
        persona.setId(personaRepository.getIdPrimaryKey());
        persona.setCi(solicitud.getCi());
        persona.setNombrecompleto(solicitud.getNombres() + " " + solicitud.getApellidos());

        // Obtener email y celular de la solicitud inicial si existe
        if (solicitud.getSolicitudInicial() != null) {
            persona.setEmail(solicitud.getSolicitudInicial().getCorreo());
            try {
                persona.setCelular(Integer.parseInt(solicitud.getSolicitudInicial().getCelular()));
            } catch (NumberFormatException e) {
                persona.setCelular(null);
            }
        }
        persona.setEstado(1);
        persona = personaRepository.save(persona);

        // 2. CREAR EL SOCIO
        SocioEntity socio = new SocioEntity();
        socio.setId(socioRepository.getIdPrimaryKey());
        socio.setCodigo(socioRepository.getCodigo());
        socio.setNrodocumento(solicitud.getCi());
        socio.setNombresocio(persona.getNombrecompleto());
        socio.setPersona(persona);
        socio.setSolicitudAfiliacion(solicitud);
        socio.setFechaemision(LocalDate.now());
        socio.setFechaexpiracion(LocalDate.now().plusYears(1));
        socio.setEstado(1);
        socioRepository.save(socio);

        // 3. CREAR PERFIL PÚBLICO (vacío inicialmente)
        PerfilSocioEntity perfil = new PerfilSocioEntity();
        perfil.setSocio(socio);
        perfil.setNombreCompleto(persona.getNombrecompleto());
        perfil.setEmail(persona.getEmail());
        perfil.setPerfilPublico(true);
        perfil.setPermiteContacto(true);
        perfil.setEstado(1);
        perfilSocioRepository.save(perfil);

        // 4. CREAR ESTADO DE CUENTA (Matrícula inicial)
        Integer gestionActual = LocalDate.now().getYear();

        // Verificar que no exista ya una matrícula para esta gestión
        boolean yaExisteMatricula = estadoCuentaSocioRepository.existeObligacion(
            socio.getId(), gestionActual, null, "MATRICULA");

        if (!yaExisteMatricula) {
            ConfiguracionCuotasEntity config = configuracionCuotasRepository.findByGestion(gestionActual)
                .orElseThrow(() -> new IllegalStateException(
                    "No existe configuración de cuotas para la gestión " + gestionActual +
                    ". No se puede aprobar la solicitud sin definir los montos de matrícula."));

            EstadoCuentaSocioEntity estadoCuenta = new EstadoCuentaSocioEntity();
            estadoCuenta.setSocio(socio);
            estadoCuenta.setTipoObligacion("MATRICULA");
            estadoCuenta.setGestion(gestionActual);
            estadoCuenta.setMes(null); // Matrícula no tiene mes
            estadoCuenta.setConcepto("Matrícula Gestión " + gestionActual);
            estadoCuenta.setMontoOriginal(config.getMontoMatricula());
            estadoCuenta.setFechaEmision(LocalDate.now());

            // Fecha de vencimiento: 30 días desde la aprobación
            estadoCuenta.setFechaVencimiento(LocalDate.now().plusDays(30));
            estadoCuenta.setEstadoPago("PENDIENTE");
            estadoCuenta.setMontoPagadoAcumulado(BigDecimal.ZERO);

            estadoCuentaSocioRepository.save(estadoCuenta);
        }

        // 5. ACTUALIZAR LA SOLICITUD A APROBADO
        solicitud.setEstadoAfiliacion("APROBADO");
        solicitud.setFechaRevision(LocalDateTime.now());
        repository.save(solicitud);
    }

    private SolicitudAfiliacionResponseDTO toResponseDTO(SolicitudAfiliacionEntity entity) {
        SolicitudAfiliacionResponseDTO dto = new SolicitudAfiliacionResponseDTO();
        dto.setId(entity.getId());
        dto.setFkSolicitudInicialAfiliacion(entity.getSolicitudInicial() != null
                ? entity.getSolicitudInicial().getId()
                : null);
        dto.setNombres(entity.getNombres());
        dto.setApellidos(entity.getApellidos());
        dto.setCi(entity.getCi());
        dto.setCiExpedido(entity.getCiExpedido());
        dto.setFechaNacimiento(entity.getFechaNacimiento());
        dto.setGenero(entity.getGenero());
        dto.setDireccionDomicilio(entity.getDireccionDomicilio());
        dto.setUrlFotoCarnetAnverso(entity.getUrlFotoCarnetAnverso());
        dto.setUrlFotoCarnetReverso(entity.getUrlFotoCarnetReverso());
        dto.setUrlFotoTituloProvisicion(entity.getUrlFotoTituloProvisicion());
        dto.setUrlCv(entity.getUrlCv());
        dto.setEstadoAfiliacion(entity.getEstadoAfiliacion());
        dto.setFechaRevision(entity.getFechaRevision());
        return dto;
    }
}
