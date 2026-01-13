package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericRepositoryNormal<E, ID extends java.io.Serializable> extends JpaRepository<E, ID> {}


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\repository\GenericRepositoryNormal.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */