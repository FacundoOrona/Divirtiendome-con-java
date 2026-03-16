package org.example.service;

import org.example.dao.UsuarioDAO;
import org.example.model.RegistroResultado;
import org.example.model.Usuario;
import org.example.validator.UsuarioValidator;
import org.mindrot.jbcrypt.BCrypt;

public class UsuarioService {
    private final UsuarioDAO dao = new UsuarioDAO();

    public RegistroResultado registrarUsuario(Usuario usuario) {
        if (!UsuarioValidator.validar(usuario)) {
            return RegistroResultado.DATOS_INVALIDOS;
        }

        // Hash de contraseña
        String hashedPassword = BCrypt.hashpw(usuario.getPassword(), BCrypt.gensalt());
        Usuario usuarioSeguro = new Usuario(usuario.getUsername(), hashedPassword);

        try {
            boolean insertado = dao.registrarUsuario(usuarioSeguro);
            return insertado ? RegistroResultado.CREADO : RegistroResultado.USUARIO_EXISTE;
        } catch (Exception e) {
            return RegistroResultado.ERROR;
        }
    }
}


