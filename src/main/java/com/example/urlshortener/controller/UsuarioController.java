package com.example.urlshortener.controller;

import com.example.urlshortener.model.Usuario;
import com.example.urlshortener.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<?> getAllUsuarios(){
        return ResponseEntity.ok(usuarioService.getAllUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUsuario(@PathVariable Long id){
        return ResponseEntity.ok(usuarioService.getUsuario(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getUsuarioByEmail(@PathVariable String email){
        return ResponseEntity.ok(usuarioService.getUsuarioByEmail(email));
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUsuario(@RequestBody Usuario usuario){
        return ResponseEntity.ok(usuarioService.createUsuario(usuario));
    }

    @PutMapping
    public ResponseEntity<?> updateUsuario(@RequestBody Usuario usuario){
        return ResponseEntity.ok(usuarioService.updateUsuario(usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable Long id){
        usuarioService.deleteUsuario(id);
        return ResponseEntity.ok().build();
    }
}
