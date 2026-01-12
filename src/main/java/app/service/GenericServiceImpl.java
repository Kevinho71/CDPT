/*    */ package BOOT-INF.classes.app.service;
/*    */ 
/*    */ import app.entity.GenericEntity;
/*    */ import app.repository.GenericRepository;
/*    */ import app.service.GenericService;
/*    */ import java.io.Serializable;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import org.springframework.data.domain.Page;
/*    */ import org.springframework.data.domain.Pageable;
/*    */ import org.springframework.transaction.annotation.Transactional;
/*    */ 
/*    */ 
/*    */ public abstract class GenericServiceImpl<E extends GenericEntity, ID extends Serializable>
/*    */   implements GenericService<E, ID>
/*    */ {
/*    */   protected GenericRepository<E, ID> genericRepository;
/*    */   
/*    */   public GenericServiceImpl(GenericRepository<E, ID> genericRepository) {
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
/* 60 */       return (E)this.genericRepository.save(entidad);
/*    */     }
/* 62 */     catch (Exception e) {
/* 63 */       throw new Exception(e.getMessage());
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Transactional
/*    */   public E update(ID id, E entidad) throws Exception {
/*    */     try {
/* 72 */       Optional<E> entitiOptional = this.genericRepository.findById(id);
/* 73 */       GenericEntity genericEntity = (GenericEntity)entitiOptional.get();
/*    */       
/* 75 */       genericEntity = (GenericEntity)this.genericRepository.save(entidad);
/* 76 */       return (E)genericEntity;
/* 77 */     } catch (Exception e) {
/* 78 */       throw new Exception(e.getMessage());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   @Transactional
/*    */   public boolean delete(ID id) throws Exception {
/*    */     try {
/* 86 */       if (this.genericRepository.existsById(id)) {
/* 87 */         this.genericRepository.deleteById(id);
/* 88 */         return true;
/*    */       } 
/* 90 */       throw new Exception();
/*    */     }
/* 92 */     catch (Exception e) {
/* 93 */       throw new Exception(e.getMessage());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\service\GenericServiceImpl.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */