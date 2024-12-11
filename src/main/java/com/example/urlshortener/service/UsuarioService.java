package com.example.urlshortener.service;

import com.example.urlshortener.model.Role;
import com.example.urlshortener.model.Usuario;
import com.example.urlshortener.repository.RoleRepository;
import com.example.urlshortener.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario createUsuario(Usuario usuario){
        Role role = roleRepository.findByName("USER");
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.getRoles().add(role);
        usuario.setCreatedAt(LocalDate.now());
        return usuarioRepository.save(usuario);
    }

    public Usuario updateUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public void deleteUsuario(Long id){
        usuarioRepository.deleteById(id);
    }

    public Usuario getUsuario(Long id){
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario getUsuarioByEmail(String email){
        return usuarioRepository.findByEmail(email);
    }

    public List<Usuario> getAllUsuarios(){
        return usuarioRepository.findAll();
    }
}
