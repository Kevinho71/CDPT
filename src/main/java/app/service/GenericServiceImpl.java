 package app.service;
 
 import app.entity.GenericEntity;
 import app.repository.GenericRepository;
 import app.service.GenericService;
 import java.io.Serializable;
 import java.util.List;
 import java.util.Optional;
 import org.springframework.data.domain.Page;
 import org.springframework.data.domain.Pageable;
 import org.springframework.transaction.annotation.Transactional;


 public abstract class GenericServiceImpl<E extends GenericEntity, ID extends Serializable>
   implements GenericService<E, ID>
 {
   protected GenericRepository<E, ID> genericRepository;
   
   public GenericServiceImpl(GenericRepository<E, ID> genericRepository) {
     this.genericRepository = genericRepository;
   }
   
   @Transactional
   public List<E> findAll() throws Exception {
     try {
       List<E> entities = this.genericRepository.findAll();
       return entities;
     } catch (Exception e) {
       
       throw new Exception(e.getMessage());
     } 
   }


   @Transactional
   public Page<E> findAll(Pageable pageable) throws Exception {
     try {
       Page<E> entities = this.genericRepository.findAll(pageable);
       return entities;
     } catch (Exception e) {
       
       throw new Exception(e.getMessage());
     } 
   }
   
   @Transactional
   public E findById(ID id) throws Exception {
     try {
       Optional<E> entitiOptional = this.genericRepository.findById(id);
       return entitiOptional.get();
     } catch (Exception e) {
       throw new Exception(e.getMessage());
     } 
   }


   @Transactional
   public E save(E entidad) throws Exception {
     try {
       return (E)this.genericRepository.save(entidad);
     }
     catch (Exception e) {
       throw new Exception(e.getMessage());
     } 
   }


   @Transactional
   public E update(ID id, E entidad) throws Exception {
     try {
       Optional<E> entitiOptional = this.genericRepository.findById(id);
       GenericEntity genericEntity = (GenericEntity)entitiOptional.get();
       
       genericEntity = (GenericEntity)this.genericRepository.save(entidad);
       return (E)genericEntity;
     } catch (Exception e) {
       throw new Exception(e.getMessage());
     } 
   }


   @Transactional
   public boolean delete(ID id) throws Exception {
     try {
       if (this.genericRepository.existsById(id)) {
         this.genericRepository.deleteById(id);
         return true;
       } 
       throw new Exception();
     }
     catch (Exception e) {
       throw new Exception(e.getMessage());
     } 
   }
 }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\service\GenericServiceImpl.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */