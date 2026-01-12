/*    */ package BOOT-INF.classes.app.service;
/*    */ 
/*    */ import app.repository.GenericRepositoryNormal;
/*    */ import app.service.GenericServiceNormal;
/*    */ import java.io.Serializable;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import org.springframework.data.domain.Page;
/*    */ import org.springframework.data.domain.Pageable;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class GenericServiceImplNormal<E, ID extends Serializable>
/*    */   implements GenericServiceNormal<E, ID>
/*    */ {
/*    */   protected GenericRepositoryNormal<E, ID> genericRepository;
/*    */   
/*    */   public GenericServiceImplNormal(GenericRepositoryNormal<E, ID> genericRepository) {
/* 20 */     this.genericRepository = genericRepository;
/*    */   }
/*    */   
/*    */   @Transactional
/*    */   public List<E> findAll() throws Exception {
/*    */     try {
/* 26 */       List<E> entities = this.genericRepository.findAll();
/* 27 */       return entities;
/* 28 */     } catch (Exception e) {
/*    */       
/* 30 */       throw new Exception(e.getMessage());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @Transactional
/*    */   public Page<E> findAll(Pageable pageable) throws Exception {
/*    */     try {
/* 38 */       Page<E> entities = this.genericRepository.findAll(pageable);
/* 39 */       return entities;
/* 40 */     } catch (Exception e) {
/*    */       
/* 42 */       throw new Exception(e.getMessage());
/*    */     } 
/*    */   }
/*    */   
/*    */   @Transactional
/*    */   public E findById(ID id) throws Exception {
/*    */     try {
/* 49 */       Optional<E> entitiOptional = this.genericRepository.findById(id);
/* 50 */       return entitiOptional.get();
/* 51 */     } catch (Exception e) {
/* 52 */       throw new Exception(e.getMessage());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @Transactional
/*    */   public E save(E entidad) throws Exception {
/*    */     try {
/* 60 */       entidad = (E)this.genericRepository.save(entidad);
/* 61 */       return entidad;
/* 62 */     } catch (Exception e) {
/* 63 */       e.printStackTrace();
/* 64 */       throw new Exception(e.getMessage());
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Transactional
/*    */   public E update(ID id, E entidad) throws Exception {
/*    */     try {
/* 73 */       Optional<E> entitiOptional = this.genericRepository.findById(id);
/* 74 */       E persona = entitiOptional.get();
/*    */       
/* 76 */       persona = (E)this.genericRepository.save(entidad);
/* 77 */       return persona;
/* 78 */     } catch (Exception e) {
/* 79 */       throw new Exception(e.getMessage());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @Transactional
/*    */   public boolean delete(ID id) throws Exception {
/*    */     try {
/* 87 */       if (this.genericRepository.existsById(id)) {
/* 88 */         this.genericRepository.deleteById(id);
/* 89 */         return true;
/*    */       } 
/* 91 */       throw new Exception();
/*    */     }
/* 93 */     catch (Exception e) {
/* 94 */       throw new Exception(e.getMessage());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\service\GenericServiceImplNormal.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */