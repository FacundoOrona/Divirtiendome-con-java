package org.example.dao;

import org.example.config.DatabaseConnection;
import org.example.model.Usuario;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public Usuario buscarPorUsername(String username) throws SQLException {
        String sql = "SELECT id, username, password FROM usuarios WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Usuario usuario = new Usuario(rs.getString("username"), rs.getString("password"));
                usuario.setId(rs.getInt("id"));
                return usuario;
            }
        }
        return null; // no encontrado
    }

    public List<Usuario> listarUsuarios() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT id, username FROM usuarios";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario(rs.getString("username"), null);
                usuario.setId(rs.getInt("id"));
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }


}