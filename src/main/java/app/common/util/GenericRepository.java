package app.common.util;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericRepository<E extends app.common.entity.GenericEntity, ID extends java.io.Serializable> extends JpaRepository<E, ID> {}


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\repository\GenericRepository.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */