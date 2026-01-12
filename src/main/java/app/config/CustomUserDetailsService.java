/*    */ package BOOT-INF.classes.app.config;
/*    */ 
/*    */ import app.entity.RolEntity;
/*    */ import app.entity.UsuarioEntity;
/*    */ import app.repository.UsuarioRepository;
/*    */ import java.util.Collection;
/*    */ import java.util.stream.Collectors;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.security.core.GrantedAuthority;
/*    */ import org.springframework.security.core.authority.SimpleGrantedAuthority;
/*    */ import org.springframework.security.core.userdetails.User;
/*    */ import org.springframework.security.core.userdetails.UserDetails;
/*    */ import org.springframework.security.core.userdetails.UserDetailsService;
/*    */ import org.springframework.security.core.userdetails.UsernameNotFoundException;
/*    */ import org.springframework.stereotype.Service;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Service
/*    */ public class CustomUserDetailsService
/*    */   implements UserDetailsService
/*    */ {
/*    */   @Autowired
/*    */   private UsuarioRepository usuarioRepository;
/*    */   
/*    */   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
/* 28 */     UsuarioEntity usuario = this.usuarioRepository.findByUsername(username);
/* 29 */     if (usuario == null) {
/* 30 */       throw new UsernameNotFoundException("Usuario o password Invalido");
/*    */     }
/* 32 */     return (UserDetails)new User(usuario.getUsername(), usuario.getPassword(), mapearAutoridadesRoles(usuario.getRoles()));
/*    */   }
/*    */   private Collection<? extends GrantedAuthority> mapearAutoridadesRoles(Collection<RolEntity> roles) {
/* 35 */     return (Collection<? extends GrantedAuthority>)roles.stream().map(role -> new SimpleGrantedAuthority(role.getNombre())).collect(Collectors.toList());
/*    */   }
/*    */ }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\config\CustomUserDetailsService.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */