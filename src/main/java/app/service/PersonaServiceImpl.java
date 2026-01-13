 package app.service;
 
 import app.entity.PersonaEntity;
 import app.repository.GenericRepositoryNormal;
 import app.repository.PersonaRepository;
 import app.service.GenericServiceImplNormal;
 import app.service.PersonaService;
 import java.util.List;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;
 import org.springframework.transaction.annotation.Transactional;


 @Service
 public class PersonaServiceImpl
   extends GenericServiceImplNormal<PersonaEntity, Integer>
   implements PersonaService
 {
   @Autowired
   private PersonaRepository personaRepository;
   
   PersonaServiceImpl(GenericRepositoryNormal<PersonaEntity, Integer> genericRepository) {
     super(genericRepository);
   }


   @Transactional
   public List<PersonaEntity> findAll(int estado, String search, int length, int start) throws Exception {
     try {
       List<PersonaEntity> entities = this.personaRepository.findAll(estado, search, length, start);
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
       this.personaRepository.updateStatus(status, id);
     }
     catch (Exception e) {
       System.out.println(e.getMessage());
       e.printStackTrace();
       throw new Exception(e.getMessage());
     } 
   }


   public int getIdPrimaryKey() throws Exception {
     try {
       int id = this.personaRepository.getIdPrimaryKey();
       return id;
     } catch (Exception e) {
       System.out.println(e.getMessage());
       
       return 0;
     } 
   }
   
   @Transactional
   public PersonaEntity save(PersonaEntity entity) throws Exception {
     try {
       System.out.println("EntitySave:" + entity.toString());
       int id = this.personaRepository.getIdPrimaryKey();
       System.out.println("id:" + id);
       entity.setId(Integer.valueOf(id));
       
       System.out.println("EntityPost:" + entity.toString());
       entity = (PersonaEntity)this.personaRepository.save(entity);
       return entity;
     } catch (Exception e) {
       throw new Exception(e.getMessage());
     } 
   }


   public Integer getTotAll(String search, int estado) throws Exception {
     try {
       int total = this.personaRepository.getTotAll(search, estado).intValue();
       return Integer.valueOf(total);
     } catch (Exception e) {
       System.out.println(e.getMessage());
       
       return Integer.valueOf(0);
     } 
   }
 }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\service\PersonaServiceImpl.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */