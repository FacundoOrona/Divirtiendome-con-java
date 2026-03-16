package org.example.validaciones;

import org.example.model.Usuario;

public class UsuarioValidator {
    public static boolean validar(Usuario usuario) {
        String username = usuario.getUsername();
        String password = usuario.getPassword();

        if (username == null || username.length() < 3 || username.length() > 50) {
            return false;
        }
        if (password == null || password.length() < 6) {
            return false;
        }
        // Podés agregar regex para caracteres válidos
        if (!username.matches("^[a-zA-Z0-9_]+$")) {
            return false;
        }
        return true;
    }
}
