 package app.core.service;
 
 import app.common.entity.GenericEntity;
 import app.core.entity.RolEntity;
 import app.common.util.GenericRepository;
 import app.core.repository.RolRepository;
 import app.common.util.GenericServiceImpl;
 import app.core.service.RolService;
 import java.util.List;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.domain.Page;
 import org.springframework.data.domain.Pageable;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 public class RolServiceImpl
   extends GenericServiceImpl<RolEntity, Long> implements RolService {
   @Autowired
   private RolRepository rolRepository;
   
   public RolServiceImpl(GenericRepository<RolEntity, Long> genericRepository) {
     super(genericRepository);
   }


   @Transactional
   public List<RolEntity> findAll(String clave, int estado) throws Exception {
     try {
       List<RolEntity> entities = this.rolRepository.findAll(clave, estado);
       return entities;
     } catch (Exception e) {
       System.out.println(e.getMessage());
       throw new Exception(e.getMessage());
     } 
   }


   @Transactional(noRollbackFor = {Exception.class})
   public Page<RolEntity> findAll(int estado, String search, Pageable pageable) throws Exception {
     try {
       Page<RolEntity> entities = this.rolRepository.findAll(estado, search, pageable);
       return entities;
     } catch (Exception e) {
       System.out.println(e.getMessage());
       throw new Exception(e.getMessage());
     } 
   }


   @Transactional
   public void updateStatus(int status, long id) throws Exception {
     try {
       System.out.println("estado:" + status + " id:" + id);
       this.rolRepository.updateStatus(status, Long.valueOf(id));
     }
     catch (Exception e) {
       System.out.println(e.getMessage());
       e.printStackTrace();
       throw new Exception(e.getMessage());
     } 
   }
   
   @Transactional
   public RolEntity save(RolEntity entidad) throws Exception {
     String nombre = entidad.getNombre();
     nombre = "ROLE_" + nombre;
     entidad.setNombre(nombre);
     try {
       entidad = (RolEntity)this.genericRepository.save(entidad);
       return entidad;
     } catch (Exception e) {
       throw new Exception(e.getMessage());
     } 
   }
 }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\service\RolServiceImpl.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */