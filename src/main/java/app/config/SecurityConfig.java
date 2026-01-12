/*    */ package BOOT-INF.classes.app.config;
/*    */ 
/*    */ import app.config.CustomUserDetailsService;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.context.annotation.Bean;
/*    */ import org.springframework.context.annotation.Configuration;
/*    */ import org.springframework.security.authentication.AuthenticationProvider;
/*    */ import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
/*    */ import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
/*    */ import org.springframework.security.config.annotation.web.builders.HttpSecurity;
/*    */ import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
/*    */ import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
/*    */ import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
/*    */ import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
/*    */ import org.springframework.security.core.userdetails.UserDetailsService;
/*    */ import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
/*    */ import org.springframework.security.crypto.password.PasswordEncoder;
/*    */ import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
/*    */ import org.springframework.security.web.util.matcher.RequestMatcher;
/*    */ 
/*    */ @Configuration
/*    */ @EnableWebSecurity
/*    */ public class SecurityConfig
/*    */   extends WebSecurityConfigurerAdapter {
/*    */   @Bean
/*    */   public BCryptPasswordEncoder passwordEncoder() {
/* 27 */     return new BCryptPasswordEncoder();
/*    */   } @Autowired
/*    */   private CustomUserDetailsService customUserDetailsService;
/*    */   @Bean
/*    */   public DaoAuthenticationProvider authenticationProvider() {
/* 32 */     DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
/* 33 */     auth.setUserDetailsService((UserDetailsService)this.customUserDetailsService);
/* 34 */     auth.setPasswordEncoder((PasswordEncoder)passwordEncoder());
/* 35 */     return auth;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
/* 41 */     auth.authenticationProvider((AuthenticationProvider)authenticationProvider());
/*    */   }
/*    */ 
/*    */   
/*    */   protected void configure(HttpSecurity http) throws Exception {
/* 46 */     ((HttpSecurity)((FormLoginConfigurer)((HttpSecurity)((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)((HttpSecurity)((HttpSecurity)http
/* 47 */       .headers().disable())
/* 48 */       .csrf().disable())
/*    */       
/* 50 */       .authorizeRequests().antMatchers(new String[] { "/registro**", "/js/**", "/css/**", "/img/**", "/logos/**", "/socioslogos/**", "/catalogosimg/**", "/catalogoscrt/**", "/socios/estadosocio/**", "/socios/empresas/**", "/RestSocios/findByNrodocumento/**", "/RestSocios/logo_socio/**", "/RestCatalogos/buscar/**", "/RestCatalogos/logo_empresa/**", "/RestCatalogos/img_catalogos_empresa/**"
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 66 */         })).permitAll()
/*    */       
/* 68 */       .anyRequest()).authenticated()
/* 69 */       .and())
/* 70 */       .formLogin()
/* 71 */       .loginPage("/login")
/* 72 */       .permitAll())
/* 73 */       .and())
/* 74 */       .logout()
/* 75 */       .invalidateHttpSession(true)
/* 76 */       .clearAuthentication(true)
/* 77 */       .logoutRequestMatcher((RequestMatcher)new AntPathRequestMatcher("/logout"))
/* 78 */       .logoutSuccessUrl("/login?logout")
/* 79 */       .permitAll();
/*    */   }
/*    */ }


/* Location:              C:\Users\Usuario\Desktop\CADET.jar!\BOOT-INF\classes\app\config\SecurityConfig.class
 * Java compiler version: 11 (55.0)
 * JD-Core Version:       1.1.3
 */