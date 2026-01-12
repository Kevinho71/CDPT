/*     */ package BOOT-INF.classes.app.service;
/*     */ 
/*     */ import app.entity.PersonaEntity;
/*     */ import app.entity.UsuarioEntity;
/*     */ import app.repository.GenericRepositoryNormal;
/*     */ import app.repository.UsuarioRepository;
/*     */ import app.service.GenericServiceImplNormal;
/*     */ import app.service.PersonaService;
/*     */ import app.service.UsuarioService;
/*     */ import java.io.Serializable;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
/*     */ import org.springframework.stereotype.Service;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ 
/*     */ @Service
/*     */ public class UsuarioServiceImpl
/*     */   extends GenericServiceImplNormal<UsuarioEntity, Integer>
/*     */   implements UsuarioService
/*     */ {
/*     */   @Autowired
/*     */   private UsuarioRepository usuarioRepository;
/*     */   
/*     */   UsuarioServiceImpl(GenericRepositoryNormal<UsuarioEntity, Integer> genericRepository) {
/*  27 */     super(genericRepository);
/*     */   }
/*     */   
/*     */   @Autowired
/*     */   private PersonaService personaService;
/*     */   @Autowired
/*     */   private BCryptPasswordEncoder passwordEncoder;
/*     */   
/*     */   @Transactional
/*     */   public List<UsuarioEntity> findAll(int estado, String search, int length, int start) throws Exception {
/*     */     try {
/*  38 */       List<UsuarioEntity> entities = this.usuarioRepository.findAll(estado, search, length, start);
/*  39 */       return entities;
/*  40 */     } catch (Exception e) {
/*  41 */       System.out.println(e.getMessage());
/*  42 */       throw new Exception(e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Transactional
/*     */   public void updateStatus(int status, int id) throws Exception {
/*     */     try {
/*  53 */       System.out.println("estado:" + status + " id:" + id);
/*  54 */       this.usuarioRepository.updateStatus(status, id);
/*     */     }
/*  56 */     catch (Exception e) {
/*  57 */       System.out.println(e.getMessage());
/*  58 */       e.printStackTrace();
/*  59 */       throw new Exception(e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIdPrimaryKey() throws Exception {
/*     */     try {
/*  67 */       int id = this.usuarioRepository.getIdPrimaryKey();
/*  68 */       return id;
/*  69 */     } catch (Exception e) {
/*  70 */       System.out.println(e.getMessage());
/*     */       
/*  72 */       return 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   @Transactional
/*     */   public UsuarioEntity save(UsuarioEntity entity) throws Exception {
/*     */     try {
/*  79 */       System.out.println("EntitySAVE_Servicio:" + entity.toString());
/*     */ 
/*     */ 
/*     */       
/*  83 */       PersonaEntity persona = new PersonaEntity();
/*  84 */       persona.setId(Integer.valueOf(this.personaService.getIdPrimaryKey()));
/*  85 */       persona.setCi(entity.getPersona().getCi());
/*  86 */       persona.setNombrecompleto(entity.getPersona().getNombrecompleto());
/*  87 */       persona.setEmail(entity.getPersona().getEmail());
/*  88 */       persona.setCelular(entity.getPersona().getCelular());
/*  89 */       persona.setEstado(Integer.valueOf(1));
/*  90 */       PersonaEntity persona2 = (PersonaEntity)this.personaService.save(persona);
/*     */       
/*  92 */       entity.setPersona(persona2);
/*  93 */       entity.setId(Integer.valueOf(this.usuarioRepository.getIdPrimaryKey()));
/*  94 */       entity.setPassword(this.passwordEncoder.encode(entity.getPassword()));
/*  95 */       System.out.println("EntityUFVPost:" + entity.toString());
/*  96 */       entity = (UsuarioEntity)this.usuarioRepository.save(entity);
/*  97 */       return entity;
/*  98 */     } catch (Exception e) {
/*  99 */       throw new Exception(e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Transactional
/*     */   public UsuarioEntity update(Integer id, UsuarioEntity entidad) throws Exception {
/*     */     try {
/* 107 */       System.out.println("UsuarioModificar:" + entidad.toString());
/*     */       
/* 109 */       Optional<UsuarioEntity> entitiOptional = this.genericRepository.findById(id);
/* 110 */       UsuarioEntity usuario = entitiOptional.get();
/*     */       
/* 112 */       if (!entidad.getPassword().equals(usuario.getPassword())) {
/* 113 */         entidad.setPassword(this.passwordEncoder.encode(entidad.getPassword()));
/*     */       }
/*     */       
/* 116 */       usuario = (UsuarioEntity)this.genericRepository.save(entidad);
/* 117 */       return usuario;
/* 118 */     } catch (Exception e) {
/* 119 */       throw new Exception(e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getTotAll(String search, int estado) throws Exception {
/*     */     try {
/* 127 */       int total = this.usuarioRepository.getTotAll(search, estado).intValue();
/* 128 */       return Integer.valueOf(total);
/* 129 */     } catch (Exception e) {
/* 130 */       System.out.println(e.getMessage());
/*     */       
/* 132 */       return Integer.valueOf(0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public UsuarioEntity getUserByLogin(String valor) throws Exception {
/* 137 */     UsuarioEntity entity = new UsuarioEntity();
/*     */     try {
/* 139 */       entity = this.usuarioRepository.getUserByLogin(valor);
/* 140 */       return entity;
/* 141 */     } catch (Exception e) {
/* 142 */       System.out.println(e.getMessage());
/*     */       
/* 144 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public UsuarioEntity findByEmail(String username) throws Exception {
/* 151 */     UsuarioEntity entity = new UsuarioEntity();
/*     */     try {
/* 153 */       entity = this.usuarioRepository.findByUsername(username);
/* 154 */       return entity;
/* 155 */     } catch (Exception e) {
/* 156 */       System.out.println(e.getMessage());
/* 157 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\service\UsuarioServiceImpl.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */