/*    */ package BOOT-INF.classes.app.service;
/*    */ 
/*    */ import app.entity.GenericEntity;
/*    */ import app.entity.RolEntity;
/*    */ import app.repository.GenericRepository;
/*    */ import app.repository.RolRepository;
/*    */ import app.service.GenericServiceImpl;
/*    */ import app.service.RolService;
/*    */ import java.util.List;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.data.domain.Page;
/*    */ import org.springframework.data.domain.Pageable;
/*    */ import org.springframework.stereotype.Service;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ @Service
/*    */ public class RolServiceImpl
/*    */   extends GenericServiceImpl<RolEntity, Long> implements RolService {
/*    */   @Autowired
/*    */   private RolRepository rolRepository;
/*    */   
/*    */   public RolServiceImpl(GenericRepository<RolEntity, Long> genericRepository) {
/* 23 */     super(genericRepository);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Transactional
/*    */   public List<RolEntity> findAll(String clave, int estado) throws Exception {
/*    */     try {
/* 32 */       List<RolEntity> entities = this.rolRepository.findAll(clave, estado);
/* 33 */       return entities;
/* 34 */     } catch (Exception e) {
/* 35 */       System.out.println(e.getMessage());
/* 36 */       throw new Exception(e.getMessage());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @Transactional(noRollbackFor = {Exception.class})
/*    */   public Page<RolEntity> findAll(int estado, String search, Pageable pageable) throws Exception {
/*    */     try {
/* 44 */       Page<RolEntity> entities = this.rolRepository.findAll(estado, search, pageable);
/* 45 */       return entities;
/* 46 */     } catch (Exception e) {
/* 47 */       System.out.println(e.getMessage());
/* 48 */       throw new Exception(e.getMessage());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @Transactional
/*    */   public void updateStatus(int status, long id) throws Exception {
/*    */     try {
/* 56 */       System.out.println("estado:" + status + " id:" + id);
/* 57 */       this.rolRepository.updateStatus(status, Long.valueOf(id));
/*    */     }
/* 59 */     catch (Exception e) {
/* 60 */       System.out.println(e.getMessage());
/* 61 */       e.printStackTrace();
/* 62 */       throw new Exception(e.getMessage());
/*    */     } 
/*    */   }
/*    */   
/*    */   @Transactional
/*    */   public RolEntity save(RolEntity entidad) throws Exception {
/* 68 */     String nombre = entidad.getNombre();
/* 69 */     nombre = "ROLE_" + nombre;
/* 70 */     entidad.setNombre(nombre);
/*    */     try {
/* 72 */       entidad = (RolEntity)this.genericRepository.save(entidad);
/* 73 */       return entidad;
/* 74 */     } catch (Exception e) {
/* 75 */       throw new Exception(e.getMessage());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\service\RolServiceImpl.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */