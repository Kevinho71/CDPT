package app.cms.service;

import app.cms.dto.ContentCreateDTO;
import app.cms.dto.ContentDTO;

import java.util.List;
import java.util.Map;

/**
 * Service interface para gestión de contenidos estáticos
 */
public interface WebStaticContentService {

    /**
     * Crea un nuevo contenido estático
     * @param createDTO Datos del contenido a crear
     * @param usuarioEmail Email del usuario que crea
     * @return DTO del contenido creado
     */
    ContentDTO createContent(ContentCreateDTO createDTO, String usuarioEmail);

    /**
     * Actualiza múltiples contenidos en lote
     * @param updates Map con clave-valor a actualizar
     * @param usuarioEmail Email del usuario que modifica
     * @return Lista de contenidos actualizados
     */
    List<ContentDTO> batchUpdateContent(Map<String, String> updates, String usuarioEmail);

    /**
     * Obtiene todos los contenidos como lista (para panel admin)
     * @return Lista de todos los contenidos
     */
    List<ContentDTO> getAllContentList();

    /**
     * Obtiene todos los contenidos como Map clave-valor (para frontend público)
     * @return Map con clave-valor plano
     */
    Map<String, String> getAllContentMap();

    /**
     * Busca un contenido por su clave
     * @param clave Clave única del contenido
     * @return DTO del contenido encontrado
     */
    ContentDTO getContentByClave(String clave);
}
