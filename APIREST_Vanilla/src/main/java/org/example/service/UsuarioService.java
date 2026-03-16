package org.example.service;

import org.example.dao.UsuarioDAO;
import org.example.model.Usuario;
import org.example.validator.UsuarioValidator;
import org.mindrot.jbcrypt.BCrypt;

public class UsuarioService {
    private final UsuarioDAO dao = new UsuarioDAO();

    public boolean registrarUsuario(Usuario usuario) {
        // Validar datos
        if (!UsuarioValidator.validar(usuario)) {
            return false;
        }

        // Hash de contraseña
        String hashedPassword = BCrypt.hashpw(usuario.getPassword(), BCrypt.gensalt());
        Usuario usuarioSeguro = new Usuario(usuario.getUsername(), hashedPassword);

        // Guardar en la base
        dao.registrarUsuario(usuarioSeguro);
        return true;
    }
}

