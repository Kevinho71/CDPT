/*    */ package BOOT-INF.classes.app.service;
/*    */ 
/*    */ import app.entity.PersonaEntity;
/*    */ import app.repository.GenericRepositoryNormal;
/*    */ import app.repository.PersonaRepository;
/*    */ import app.service.GenericServiceImplNormal;
/*    */ import app.service.PersonaService;
/*    */ import java.util.List;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ 
/*    */ @Service
/*    */ public class PersonaServiceImpl
/*    */   extends GenericServiceImplNormal<PersonaEntity, Integer>
/*    */   implements PersonaService
/*    */ {
/*    */   @Autowired
/*    */   private PersonaRepository personaRepository;
/*    */   
/*    */   PersonaServiceImpl(GenericRepositoryNormal<PersonaEntity, Integer> genericRepository) {
/* 23 */     super(genericRepository);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Transactional
/*    */   public List<PersonaEntity> findAll(int estado, String search, int length, int start) throws Exception {
/*    */     try {
/* 34 */       List<PersonaEntity> entities = this.personaRepository.findAll(estado, search, length, start);
/* 35 */       return entities;
/* 36 */     } catch (Exception e) {
/* 37 */       System.out.println(e.getMessage());
/* 38 */       throw new Exception(e.getMessage());
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Transactional
/*    */   public void updateStatus(int status, int id) throws Exception {
/*    */     try {
/* 49 */       System.out.println("estado:" + status + " id:" + id);
/* 50 */       this.personaRepository.updateStatus(status, id);
/*    */     }
/* 52 */     catch (Exception e) {
/* 53 */       System.out.println(e.getMessage());
/* 54 */       e.printStackTrace();
/* 55 */       throw new Exception(e.getMessage());
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getIdPrimaryKey() throws Exception {
/*    */     try {
/* 63 */       int id = this.personaRepository.getIdPrimaryKey();
/* 64 */       return id;
/* 65 */     } catch (Exception e) {
/* 66 */       System.out.println(e.getMessage());
/*    */       
/* 68 */       return 0;
/*    */     } 
/*    */   }
/*    */   
/*    */   @Transactional
/*    */   public PersonaEntity save(PersonaEntity entity) throws Exception {
/*    */     try {
/* 75 */       System.out.println("EntitySave:" + entity.toString());
/* 76 */       int id = this.personaRepository.getIdPrimaryKey();
/* 77 */       System.out.println("id:" + id);
/* 78 */       entity.setId(Integer.valueOf(id));
/*    */       
/* 80 */       System.out.println("EntityPost:" + entity.toString());
/* 81 */       entity = (PersonaEntity)this.personaRepository.save(entity);
/* 82 */       return entity;
/* 83 */     } catch (Exception e) {
/* 84 */       throw new Exception(e.getMessage());
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Integer getTotAll(String search, int estado) throws Exception {
/*    */     try {
/* 92 */       int total = this.personaRepository.getTotAll(search, estado).intValue();
/* 93 */       return Integer.valueOf(total);
/* 94 */     } catch (Exception e) {
/* 95 */       System.out.println(e.getMessage());
/*    */       
/* 97 */       return Integer.valueOf(0);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\service\PersonaServiceImpl.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */