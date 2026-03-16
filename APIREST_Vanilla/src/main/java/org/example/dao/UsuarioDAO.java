package org.example.dao;

import org.example.config.DatabaseConnection;
import org.example.model.Usuario;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsuarioDAO {

    public boolean registrarUsuario(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (username, password) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getPassword());

            int filas = stmt.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                return false; // usuario ya existe
            }
            throw e; // otro error
        }
    }


}