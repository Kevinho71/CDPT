package app.auth.security;

import app.core.entity.RolEntity;
import app.core.entity.UsuarioEntity;
import app.core.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioEntity usuario;
        
        // Si el username contiene @, es un email (usado por JWT)
        if (username.contains("@")) {
            usuario = usuarioRepository.findByPersonaEmail(username);
        } else {
            // Si no, es un username (usado por login normal)
            usuario = usuarioRepository.findByUsername(username);
        }
        
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario o password Invalido");
        }
        return new User(usuario.getUsername(), usuario.getPassword(), mapearAutoridadesRoles(usuario.getRoles()));
    }
    
    private Collection<? extends GrantedAuthority> mapearAutoridadesRoles(Collection<RolEntity> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getNombre()))
                .collect(Collectors.toList());
    }
}