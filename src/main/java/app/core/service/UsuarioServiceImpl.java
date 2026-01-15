 package app.core.service;
 
 import app.core.entity.PersonaEntity;
 import app.core.entity.UsuarioEntity;
 import app.common.util.GenericRepositoryNormal;
 import app.core.repository.UsuarioRepository;
 import app.common.util.GenericServiceImplNormal;
 import app.core.service.PersonaService;
 import app.core.service.UsuarioService;
 import java.io.Serializable;
 import java.util.List;
 import java.util.Optional;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;
 
 @Service
 public class UsuarioServiceImpl
   extends GenericServiceImplNormal<UsuarioEntity, Integer>
   implements UsuarioService
 {
   @Autowired
   private UsuarioRepository usuarioRepository;
   
   UsuarioServiceImpl(GenericRepositoryNormal<UsuarioEntity, Integer> genericRepository) {
     super(genericRepository);
   }
   
   @Autowired
   private PersonaService personaService;
   @Autowired
   private BCryptPasswordEncoder passwordEncoder;
   
   @Transactional
   public List<UsuarioEntity> findAll(int estado, String search, int length, int start) throws Exception {
     try {
       List<UsuarioEntity> entities = this.usuarioRepository.findAll(estado, search, length, start);
       return entities;
     } catch (Exception e) {
       System.out.println(e.getMessage());
       throw new Exception(e.getMessage());
     } 
   }


   @Transactional
   public void updateStatus(int status, int id) throws Exception {
     try {
       System.out.println("estado:" + status + " id:" + id);
       this.usuarioRepository.updateStatus(status, id);
     }
     catch (Exception e) {
       System.out.println(e.getMessage());
       e.printStackTrace();
       throw new Exception(e.getMessage());
     } 
   }


   public int getIdPrimaryKey() throws Exception {
     try {
       int id = this.usuarioRepository.getIdPrimaryKey();
       return id;
     } catch (Exception e) {
       System.out.println(e.getMessage());
       
       return 0;
     } 
   }
   
   @Transactional
   public UsuarioEntity save(UsuarioEntity entity) throws Exception {
     try {
       System.out.println("EntitySAVE_Servicio:" + entity.toString());


       PersonaEntity persona = new PersonaEntity();
       persona.setId(Integer.valueOf(this.personaService.getIdPrimaryKey()));
       persona.setCi(entity.getPersona().getCi());
       persona.setNombrecompleto(entity.getPersona().getNombrecompleto());
       persona.setEmail(entity.getPersona().getEmail());
       persona.setCelular(entity.getPersona().getCelular());
       persona.setEstado(Integer.valueOf(1));
       PersonaEntity persona2 = (PersonaEntity)this.personaService.save(persona);
       
       entity.setPersona(persona2);
       entity.setId(Integer.valueOf(this.usuarioRepository.getIdPrimaryKey()));
       entity.setPassword(this.passwordEncoder.encode(entity.getPassword()));
       System.out.println("EntityUFVPost:" + entity.toString());
       entity = (UsuarioEntity)this.usuarioRepository.save(entity);
       return entity;
     } catch (Exception e) {
       throw new Exception(e.getMessage());
     } 
   }


   @Transactional
   public UsuarioEntity update(Integer id, UsuarioEntity entidad) throws Exception {
     try {
       System.out.println("UsuarioModificar:" + entidad.toString());
       
       Optional<UsuarioEntity> entitiOptional = this.genericRepository.findById(id);
       UsuarioEntity usuario = entitiOptional.get();
       
       if (!entidad.getPassword().equals(usuario.getPassword())) {
         entidad.setPassword(this.passwordEncoder.encode(entidad.getPassword()));
       }
       
       usuario = (UsuarioEntity)this.genericRepository.save(entidad);
       return usuario;
     } catch (Exception e) {
       throw new Exception(e.getMessage());
     } 
   }


   public Integer getTotAll(String search, int estado) throws Exception {
     try {
       int total = this.usuarioRepository.getTotAll(search, estado).intValue();
       return Integer.valueOf(total);
     } catch (Exception e) {
       System.out.println(e.getMessage());
       
       return Integer.valueOf(0);
     } 
   }
   
   public UsuarioEntity getUserByLogin(String valor) throws Exception {
     UsuarioEntity entity = new UsuarioEntity();
     try {
       entity = this.usuarioRepository.getUserByLogin(valor);
       return entity;
     } catch (Exception e) {
       System.out.println(e.getMessage());
       
       return null;
     } 
   }


   public UsuarioEntity findByEmail(String username) throws Exception {
     UsuarioEntity entity = new UsuarioEntity();
     try {
       entity = this.usuarioRepository.findByUsername(username);
       return entity;
     } catch (Exception e) {
       System.out.println(e.getMessage());
       return null;
     } 
   }
 }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\service\UsuarioServiceImpl.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */