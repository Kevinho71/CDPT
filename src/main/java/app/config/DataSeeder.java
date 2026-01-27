package app.config;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import app.core.entity.InstitucionEntity;
import app.core.entity.PersonaEntity;
import app.core.entity.RolEntity;
import app.core.entity.UsuarioEntity;
import app.core.repository.InstitucionRepository;
import app.core.repository.PersonaRepository;
import app.core.repository.ProvinciaRepository;
import app.core.repository.RolRepository;
import app.core.repository.UsuarioRepository;

@Component
public class DataSeeder implements CommandLineRunner {
    @Autowired
    private InstitucionRepository institucionRepository;
    @Autowired
    private PersonaRepository personaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ProvinciaRepository provinciaRepository;
    @Autowired
    private RolRepository rolRepository;

    @Value("${app.public-url:http//:localhost:8080}")
    private String currentHost;

    @Value("${security.admin.password}")
    private String adminPassword;

    private void cargarDatosIniciales() {
        if (institucionRepository.count() == 0) {
            InstitucionEntity institucion = new InstitucionEntity(
                    1, "123456789", "CADET", "COLEGIO DE ADMINISTRADORES DE EMPRESAS DE TARIJA", "cadet",
                    "cadet.tarija@gmail.com", "direccion", "12345678", "12345678", currentHost, "8080", 1,
                    provinciaRepository.findById(1).get());
            institucionRepository.save(institucion);
        }
        ;

        if (usuarioRepository.count() == 0)

        {
            Collection<RolEntity> roles = Stream.of(rolRepository.findAll().getFirst()).toList();
            PersonaEntity persona = new PersonaEntity(1, "123456789", "cadet", "cadet.tarija@gmail.com", 12345678, 1);
            UsuarioEntity usuario = new UsuarioEntity(1, "cadet", passwordEncoder.encode(adminPassword), 1, roles,
                    persona);
            personaRepository.save(persona);
            usuarioRepository.save(usuario);
        }
        ;
    }

    @Override
    public void run(String... args) throws Exception {
        cargarDatosIniciales();
    }

}
