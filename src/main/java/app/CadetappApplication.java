package app;

import app.core.entity.AnioEntity;
import app.core.entity.DepartamentoEntity;
import app.core.entity.InstitucionEntity;
import app.core.entity.PaisEntity;
import app.core.entity.PersonaEntity;
import app.core.entity.ProfesionEntity;
import app.core.entity.ProvinciaEntity;
import app.core.entity.RolEntity;
import app.core.entity.UsuarioEntity;
import app.core.repository.AnioRepository;
import app.core.repository.DepartamentoRepository;
import app.core.repository.InstitucionRepository;
import app.core.repository.PaisRepository;
import app.core.repository.ProfesionRepository;
import app.core.repository.ProvinciaRepository;
import app.core.repository.RolRepository;
import app.core.repository.PersonaRepository;
import app.core.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;

@SpringBootApplication
public class CadetappApplication extends SpringBootServletInitializer {
    
    @Autowired
    private ProfesionRepository profesionRepository;
    @Autowired
    private PaisRepository paisRepository;
    @Autowired
    private DepartamentoRepository departamentoRepository;
    @Autowired
    private ProvinciaRepository provinciaRepository;
    @Autowired
    private InstitucionRepository institucionRepository;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private PersonaRepository personaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    
    public static void main(String[] args) {
        SpringApplication.run(CadetappApplication.class, args);
    }
    
    @Bean
    public ApplicationRunner initializer() {
        return args -> {
            ProfesionEntity profesionEntity = new ProfesionEntity(1, "ADMINISTRADOR DE EMPRESAS", 1);
            ProfesionEntity profesionsave = this.profesionRepository.save(profesionEntity);
            
            PaisEntity paisEntity = new PaisEntity(1, "BOLIVIA", 1);
            PaisEntity paissave = this.paisRepository.save(paisEntity);
            
            DepartamentoEntity departamentoEntity = new DepartamentoEntity(1, null, "TARIJA", 1, paissave);
            DepartamentoEntity departamentosave = this.departamentoRepository.save(departamentoEntity);
            
            ProvinciaEntity proviniciaEntity = new ProvinciaEntity(1, "CERCADO", 1, departamentosave);
            ProvinciaEntity provinciasave = this.provinciaRepository.save(proviniciaEntity);
            
            System.out.println("**************INIT PRE INSTI****");
            
            InstitucionEntity institucionEntitybd = null;
            
            try {
                institucionEntitybd = this.institucionRepository.findById(1).orElse(null);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
                institucionEntitybd = null;
            }
            
            System.out.println("**************INIT INSTITUCION:" + institucionEntitybd);
            if (institucionEntitybd == null) {
                System.out.println("****************AGREGANDO INSTITUCION NUEVA");
                InstitucionEntity institucionEntity = new InstitucionEntity(1, "123456789", "CADET", 
                    "COLEGIO DE ADMINISTRADOR DE EMPRESAS DE TARIJA", "cadet", "cadet.tarija@gmail.com", 
                    "direccion", "75119900", null, "192.168.100.2:8080", null, 1, provinciasave);
                this.institucionRepository.save(institucionEntity);
            }
            
            Collection<RolEntity> rolesarray = new ArrayList<>();
            RolEntity rolEntity = new RolEntity("ROLE_ADMIN", 1);
            RolEntity rolsave = this.rolRepository.save(rolEntity);
            rolesarray.add(rolsave);
            
            PersonaEntity persona = new PersonaEntity(1, "1234567891", "cadet", "cadet.tarija@gmail.com", 75119900, 1);
            this.personaRepository.save(persona);
            
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String password = bCryptPasswordEncoder.encode("cadet.2024");
            System.out.println(password);
            
            UsuarioEntity usuarioEntity = new UsuarioEntity(1, "cadet", password, 1, rolesarray, persona);
            this.usuarioRepository.save(usuarioEntity);
            System.out.println("Usuarios agregados a la base de datos.");
        };
    }
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(CadetappApplication.class);
    }
}