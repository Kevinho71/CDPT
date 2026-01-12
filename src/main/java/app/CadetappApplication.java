/*     */ package BOOT-INF.classes.app;
/*     */ 
/*     */ import app.entity.DepartamentoEntity;
/*     */ import app.entity.InstitucionEntity;
/*     */ import app.entity.PaisEntity;
/*     */ import app.entity.PersonaEntity;
/*     */ import app.entity.ProfesionEntity;
/*     */ import app.entity.ProvinciaEntity;
/*     */ import app.entity.RolEntity;
/*     */ import app.entity.UsuarioEntity;
/*     */ import app.repository.DepartamentoRepository;
/*     */ import app.repository.InstitucionRepository;
/*     */ import app.repository.PaisRepository;
/*     */ import app.repository.PersonaRepository;
/*     */ import app.repository.ProfesionRepository;
/*     */ import app.repository.ProvinciaRepository;
/*     */ import app.repository.RolRepository;
/*     */ import app.repository.UsuarioRepository;
/*     */ import app.service.GoogleDriveService;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.boot.ApplicationArguments;
/*     */ import org.springframework.boot.ApplicationRunner;
/*     */ import org.springframework.boot.SpringApplication;
/*     */ import org.springframework.boot.autoconfigure.SpringBootApplication;
/*     */ import org.springframework.boot.builder.SpringApplicationBuilder;
/*     */ import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
/*     */ import org.springframework.context.annotation.Bean;
/*     */ import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @SpringBootApplication
/*     */ public class CadetappApplication
/*     */   extends SpringBootServletInitializer
/*     */ {
/*     */   @Autowired
/*     */   private ProfesionRepository profesionRepository;
/*     */   @Autowired
/*     */   private PaisRepository paisRepository;
/*     */   @Autowired
/*     */   private DepartamentoRepository departamentoRepository;
/*     */   @Autowired
/*     */   private ProvinciaRepository provinciaRepository;
/*     */   @Autowired
/*     */   private InstitucionRepository institucionRepository;
/*     */   @Autowired
/*     */   private RolRepository rolRepository;
/*     */   @Autowired
/*     */   private PersonaRepository personaRepository;
/*     */   @Autowired
/*     */   private UsuarioRepository usuarioRepository;
/*     */   @Autowired
/*     */   private GoogleDriveService googleDriveService;
/*     */   
/*     */   public static void main(String[] args) {
/*  65 */     SpringApplication.run(app.CadetappApplication.class, args);
/*     */   }
/*     */   
/*     */   @Bean
/*     */   public ApplicationRunner initializer() {
/*  70 */     return args -> {
/*     */         ProfesionEntity profesionEntity = new ProfesionEntity(Integer.valueOf(1), "ADMINISTRADOR DE EMPRESAS", Integer.valueOf(1));
/*     */ 
/*     */         
/*     */         ProfesionEntity profesionsave = (ProfesionEntity)this.profesionRepository.save(profesionEntity);
/*     */ 
/*     */         
/*     */         PaisEntity paisEntity = new PaisEntity(Integer.valueOf(1), "BOLIVIA", Integer.valueOf(1));
/*     */ 
/*     */         
/*     */         PaisEntity paissave = (PaisEntity)this.paisRepository.save(paisEntity);
/*     */         
/*     */         DepartamentoEntity departamentoEntity = new DepartamentoEntity(Integer.valueOf(1), null, "TARIJA", Integer.valueOf(1), paissave);
/*     */         
/*     */         DepartamentoEntity departamentosave = (DepartamentoEntity)this.departamentoRepository.save(departamentoEntity);
/*     */         
/*     */         ProvinciaEntity proviniciaEntity = new ProvinciaEntity(Integer.valueOf(1), "CERCADO", Integer.valueOf(1), departamentosave);
/*     */         
/*     */         ProvinciaEntity provinciasave = (ProvinciaEntity)this.provinciaRepository.save(proviniciaEntity);
/*     */         
/*     */         System.out.println("**************INIT PRE INSTI****");
/*     */         
/*     */         InstitucionEntity institucionEntitybd = null;
/*     */         
/*     */         try {
/*     */           institucionEntitybd = this.institucionRepository.findById(Integer.valueOf(1)).get();
/*  96 */         } catch (Exception e) {
/*     */           e.printStackTrace();
/*     */           System.out.println(e.getMessage());
/*     */           institucionEntitybd = null;
/*     */         } 
/*     */         System.out.println("**************INIT INSTITUCION:" + institucionEntitybd);
/*     */         if (institucionEntitybd == null) {
/*     */           System.out.println("****************AGREGANDO INSTITUCION NUEVA");
/*     */           InstitucionEntity institucionEntity = new InstitucionEntity(Integer.valueOf(1), "123456789", "CADET", "COLEGIO DE ADMINISTRADOR DE EMPRESAS DE TARIJA", "cadet", "cadet.tarija@gmail.com", "direccion", "75119900", null, "192.168.100.2:8080", null, Integer.valueOf(1), provinciasave);
/*     */           this.institucionRepository.save(institucionEntity);
/*     */         } 
/*     */         Collection<RolEntity> rolesarray = new ArrayList<>();
/*     */         RolEntity rolEntity = new RolEntity("ROLE_ADMIN", 1);
/*     */         RolEntity rolsave = (RolEntity)this.rolRepository.save(rolEntity);
/*     */         rolesarray.add(rolsave);
/*     */         PersonaEntity persona = new PersonaEntity(Integer.valueOf(1), "1234567891", "cadet", "cadet.tarija@gmail.com", Integer.valueOf(75119900), Integer.valueOf(1));
/*     */         this.personaRepository.save(persona);
/*     */         BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
/*     */         String password = bCryptPasswordEncoder.encode("cadet.2024");
/*     */         System.out.println(password);
/*     */         UsuarioEntity usuarioEntity = new UsuarioEntity(Integer.valueOf(1), "cadet", password, Integer.valueOf(1), rolesarray, persona);
/*     */         this.usuarioRepository.save(usuarioEntity);
/*     */         System.out.println("Usuarios agregados a la base de datos.");
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
/* 131 */     return builder.sources(new Class[] { app.CadetappApplication.class });
/*     */   }
/*     */ }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\CadetappApplication.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */